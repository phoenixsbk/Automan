package cn.lynx.automan.service.impl;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.repo.SessionTokenRepository;
import cn.lynx.automan.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;


@Service
public class TokenServiceImpl implements TokenService {
  private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

  private static final String CIPHER_ALGORITHM = "AES";

  private static final String TOKEN_FORMAT = "t=%s;u=%s";

  private Cipher encrypt;

  private Cipher decrypt;

  @Autowired
  private SessionTokenRepository tokenRepo;

  @Autowired
  private AuthConfig authConfig;

  @PostConstruct
  public void init() {
    try {
      KeyGenerator keygen = KeyGenerator.getInstance(CIPHER_ALGORITHM);
      keygen.init(128, new SecureRandom(Base64.getDecoder().decode(authConfig.getKey())));
      encrypt = Cipher.getInstance(CIPHER_ALGORITHM);
      decrypt = Cipher.getInstance(CIPHER_ALGORITHM);
      SecretKeySpec keySpec = new SecretKeySpec(keygen.generateKey().getEncoded(), CIPHER_ALGORITHM);
      encrypt.init(Cipher.ENCRYPT_MODE, keySpec);
      decrypt.init(Cipher.DECRYPT_MODE, keySpec);
    } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException("Can't initialize encryption/decryption.", e);
    }
  }

  @Override
  public SessionToken getToken(String username) {
    Instant tokenTime = Instant.now();
    return tokenRepo.findByUsernameAndSessionEndDateGreaterThanEqual(username, Timestamp.from(tokenTime));
  }

  @Override
  public SessionToken createToken(String username) {
    tokenRepo.deleteByUsername(username);
    Instant tokenTime = Instant.now();
    SessionToken newToken = new SessionToken();
    newToken.setUsername(username);
    newToken.setSessionStartDate(Timestamp.from(tokenTime));
    newToken.setAuthToken(generateToken(username, tokenTime));
    Instant endTime = tokenTime.plusSeconds(authConfig.getExpire());
    newToken.setSessionEndDate(Timestamp.from(endTime));

    return tokenRepo.saveAndFlush(newToken);
  }

  @Override
  public boolean isTokenValid(String authToken) {
    SessionToken token = tokenRepo.findByAuthToken(authToken);
    if (token == null) {
      return false;
    }

    if (token.getSessionEndDate().before(Timestamp.from(Instant.now()))) {
      return false;
    }

    String decryptToken = null;
    try {
      byte[] base64Dec = Base64.getDecoder().decode(authToken);
      decryptToken = new String(decrypt.doFinal(base64Dec), Charset.forName("UTF-8"));
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      return false;
    }

    String[] splitted = StringUtils.split(decryptToken, ";");
    String ts = splitted[0];
    if (ts.length() <= 2) {
      return false;
    }

    Instant exptime = Instant.from(FORMATTER.parse(ts.substring(2)));
    if (!token.getSessionEndDate().equals(Timestamp.from(exptime))) {
      return false;
    }

    String user = splitted[1];
    if (user.length() <= 2) {
      return false;
    }

    if (!token.getUsername().equals(user.substring(2))) {
      return false;
    }

    return true;
  }

  @Override
  public boolean invalidateToken(String authToken) {
    SessionToken token = tokenRepo.findByAuthToken(authToken);
    if (token != null) {
      tokenRepo.delete(token);
      return true;
    } else {
      return false;
    }
  }

  private String generateToken(String username, Instant instant) {
    String formattedToken = String.format(TOKEN_FORMAT, FORMATTER.format(instant), username);
    try {
      return Base64.getEncoder().encodeToString(encrypt.doFinal(formattedToken.getBytes(Charset.forName("UTF-8"))));
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      LOGGER.warn("Not gonna happen.", e);
      return null;
    }
  }
}
