package cn.lynx.automan.service.impl;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.repo.SessionTokenRepository;
import cn.lynx.automan.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;


@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("GMT+8"));

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
            encrypt = Cipher.getInstance(CIPHER_ALGORITHM);
            decrypt = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] keyBytes = authConfig.getKey().getBytes("UTF-8");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, CIPHER_ALGORITHM);
            encrypt.init(Cipher.ENCRYPT_MODE, keySpec);
            decrypt.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            LOGGER.warn("Not gonna happen.", e);
        }
    }

    @Override
    public SessionToken getOrCreateToken(String username) {
        SessionToken existedToken = tokenRepo.findByUsernameAndSessionEndDateGreaterThanEqual(username, Timestamp.from(Instant.now()));
        if (existedToken == null) {
            tokenRepo.deleteByUsername(username);

            SessionToken newToken = new SessionToken();
            Instant tokenTime = Instant.now();
            newToken.setSessionStartDate(Timestamp.from(tokenTime));
            newToken.setAuthToken(generateToken(username, tokenTime));
            Instant endTime = tokenTime.plusSeconds(authConfig.getExpire());
            newToken.setSessionEndDate(Timestamp.from(endTime));

            return tokenRepo.saveAndFlush(newToken);
        } else {
            return existedToken;
        }
    }

    private String generateToken(String username, Instant instant) {
        String formattedToken = String.format(TOKEN_FORMAT, username, FORMATTER.format(instant));
        try {
            return Base64.getEncoder().encodeToString(encrypt.doFinal(formattedToken.getBytes("UTF-8")));
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            LOGGER.warn("Not gonna happen.", e);
            return null;
        }
    }
}
