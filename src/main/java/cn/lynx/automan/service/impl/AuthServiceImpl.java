package cn.lynx.automan.service.impl;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.SessionTokenRepository;
import cn.lynx.automan.data.repo.UserRepository;
import cn.lynx.automan.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  private MessageDigest md;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionTokenRepository sessionTokenRepository;

  @Autowired
  private AuthConfig authConfig;

  @PostConstruct
  public void init() {
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Can't find the MD5 encryption, abort startup.", e);
    }
  }

  @Override
  public SessionToken login(String username, String password) {
    User user = userRepository.findByUsernameAndPassword(username, encrypt(password));
    if (user == null) {
      return null;
    } else {
      sessionTokenRepository.deleteByUsername(username);
      return sessionTokenRepository.save(createToken(username, Instant.now()));
    }
  }

  SessionToken createToken(String username, Instant instant) {
    SessionToken token = new SessionToken();
    Instant expire = instant.plus(authConfig.getExpire(), ChronoUnit.SECONDS);
    token.setUsername(username);
    token.setSessionStartDate(Timestamp.from(instant));
    token.setSessionEndDate(Timestamp.from(expire));
    token.setAuthToken(UUID.randomUUID().toString());
    return token;
  }

  @Override
  public boolean logout(SessionToken token) {
    if (token != null) {
      sessionTokenRepository.delete(token);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean isTokenValid(SessionToken token) {
    return token != null && token.getSessionEndDate().after(Timestamp.from(Instant.now()));
  }

  @Override
  public SessionToken findToken(String token) {
    return sessionTokenRepository.findByAuthToken(token);
  }

  @Override
  public User findUserByToken(SessionToken token) {
    if (token != null) {
      return userRepository.findByUsername(token.getUsername());
    } else {
      return null;
    }
  }

  String encrypt(String rawPassword) {
    try {
      byte[] updated = md.digest(rawPassword.getBytes("UTF-16LE"));
      return Base64.getEncoder().encodeToString(updated);
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("Not gonna happen if UTF-16LE not existed.");
      return null;
    }
  }
}
