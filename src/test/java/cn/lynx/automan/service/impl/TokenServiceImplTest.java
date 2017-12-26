package cn.lynx.automan.service.impl;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.repo.SessionTokenRepository;
import cn.lynx.automan.testutils.TestConfigs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {
  @InjectMocks
  TokenServiceImpl impl;

  @Mock
  AuthConfig authConfig;

  @Mock
  SessionTokenRepository tokenRepo;

  @Test
  public void testInit() throws NoSuchFieldException, IllegalAccessException {
    when(authConfig.getKey()).thenReturn("YWJjZGU=");
    impl.init();

    Field encField = TokenServiceImpl.class.getDeclaredField("encrypt");
    encField.setAccessible(true);
    Cipher encout = (Cipher) encField.get(impl);

    Field decField = TokenServiceImpl.class.getDeclaredField("decrypt");
    decField.setAccessible(true);
    Cipher decout = (Cipher) decField.get(impl);

    assertNotNull(encout);
    assertNotNull(decout);
  }

  @Test
  public void testGetOrCreateToken() {
    when(authConfig.getKey()).thenReturn("YWJjZGU=");
    impl.init();

    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByUsernameAndSessionEndDateGreaterThanEqual(anyString(), any(Timestamp.class))).thenReturn(token);

    SessionToken out = impl.getToken("myuser");
    assertNotNull(out);
    assertEquals(out, token);
  }

  @Test
  public void testCreateToken() {
    when(authConfig.getKey()).thenReturn("YWJjZGU=");
    impl.init();

    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByUsernameAndSessionEndDateGreaterThanEqual(anyString(), any(Timestamp.class))).thenReturn(null);
    when(tokenRepo.saveAndFlush(any(SessionToken.class))).thenReturn(token);
    doNothing().when(tokenRepo).deleteByUsername(anyString());
    when(token.getUsername()).thenReturn("myuser");

    SessionToken out = impl.createToken("myuser");
    assertNotNull(out);
    assertEquals("myuser", out.getUsername());
  }

  @Test
  public void testIsTokenValidNull() {
    when(tokenRepo.findByAuthToken(anyString())).thenReturn(null);
    boolean result = impl.isTokenValid("mytoken");
    assertFalse(result);
  }

  @Test
  public void testIsTokenValidExpire() {
    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken(anyString())).thenReturn(token);
    Instant instant = Instant.now().minus(12, ChronoUnit.HOURS);
    when(token.getSessionEndDate()).thenReturn(Timestamp.from(instant));

    boolean result = impl.isTokenValid("mytoken");
    assertFalse(result);
  }

  @Test
  public void testIsTokenDecryptTimestampWrong() {
    String tsWord = "t;s=xxx";
    when(authConfig.getKey()).thenReturn(TestConfigs.getProp("automan.auth.token.key"));

    String enc = encryptIt(tsWord);
    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken(eq(enc))).thenReturn(token);
    Instant instant = Instant.now().plus(12, ChronoUnit.HOURS);
    when(token.getSessionEndDate()).thenReturn(Timestamp.from(instant));

    impl.init();
    boolean result = impl.isTokenValid(enc);
    assertFalse(result);
  }

  @Test
  public void testTokenInsideTimestampExpire() {
    DateTimeFormatter df = DateTimeFormatter.ISO_INSTANT;
    Instant instant = Instant.now().plus(12, ChronoUnit.HOURS);
    String ts = df.format(instant.plusSeconds(123L));
    String tsWord = "t=" + ts + ";u=myname";
    when(authConfig.getKey()).thenReturn(TestConfigs.getProp("automan.auth.token.key"));

    String enc = encryptIt(tsWord);
    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken(eq(enc))).thenReturn(token);

    when(token.getSessionEndDate()).thenReturn(Timestamp.from(instant));

    impl.init();
    assertFalse(impl.isTokenValid(enc));
  }

  @Test
  public void testUserEmpty() {
    DateTimeFormatter df = DateTimeFormatter.ISO_INSTANT;
    Instant instant = Instant.now().plus(12, ChronoUnit.HOURS);
    String ts = df.format(instant);
    String tsWord = "t=" + ts + ";u=";
    when(authConfig.getKey()).thenReturn(TestConfigs.getProp("automan.auth.token.key"));

    String enc = encryptIt(tsWord);
    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken(eq(enc))).thenReturn(token);

    when(token.getSessionEndDate()).thenReturn(Timestamp.from(instant));

    impl.init();
    assertFalse(impl.isTokenValid(enc));
  }

  @Test
  public void testUsernameNotMatch() {
    DateTimeFormatter df = DateTimeFormatter.ISO_INSTANT;
    Instant instant = Instant.now().plus(12, ChronoUnit.HOURS);
    String ts = df.format(instant);
    String tsWord = "t=" + ts + ";u=myname1";
    when(authConfig.getKey()).thenReturn(TestConfigs.getProp("automan.auth.token.key"));

    String enc = encryptIt(tsWord);
    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken(eq(enc))).thenReturn(token);

    when(token.getSessionEndDate()).thenReturn(Timestamp.from(instant));
    when(token.getUsername()).thenReturn("myname2");

    impl.init();
    assertFalse(impl.isTokenValid(enc));
  }

  @Test
  public void testValidToken() {
    DateTimeFormatter df = DateTimeFormatter.ISO_INSTANT;
    Instant instant = Instant.now().plus(12, ChronoUnit.HOURS);
    String ts = df.format(instant);
    String tsWord = "t=" + ts + ";u=myname";
    when(authConfig.getKey()).thenReturn(TestConfigs.getProp("automan.auth.token.key"));

    String enc = encryptIt(tsWord);
    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken(eq(enc))).thenReturn(token);

    when(token.getSessionEndDate()).thenReturn(Timestamp.from(instant));
    when(token.getUsername()).thenReturn("myname");

    impl.init();
    assertTrue(impl.isTokenValid(enc));
  }

  @Test
  public void testInvalidateToken() {
    when(tokenRepo.findByAuthToken(eq("mytoken"))).thenReturn(null);
    assertFalse(impl.invalidateToken("mytoken"));

    SessionToken token = mock(SessionToken.class);
    when(tokenRepo.findByAuthToken("validToken")).thenReturn(token);
    doNothing().when(tokenRepo).delete(eq(token));

    assertTrue(impl.invalidateToken("validToken"));
  }

  private String encryptIt(String rawPass) {
    String key = TestConfigs.getProp("automan.auth.token.key");
    try {
      KeyGenerator keygen = KeyGenerator.getInstance("AES");
      keygen.init(128, new SecureRandom(Base64.getDecoder().decode(key)));
      Cipher encrypt = Cipher.getInstance("AES");
      SecretKeySpec keySpec = new SecretKeySpec(keygen.generateKey().getEncoded(), "AES");
      encrypt.init(Cipher.ENCRYPT_MODE, keySpec);
      return Base64.getEncoder().encodeToString(encrypt.doFinal(rawPass.getBytes()));
    } catch (Exception e) {
      //
    }
    return null;
  }
}
