package cn.lynx.automan.service.impl;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.SessionTokenRepository;
import cn.lynx.automan.data.repo.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {
  @InjectMocks
  AuthServiceImpl authService;

  @Mock
  private MessageDigest md;

  @Mock
  private UserRepository userRepository;

  @Mock
  private SessionTokenRepository sessionTokenRepository;

  @Mock
  private AuthConfig authConfig;

  @Test
  public void testInit() throws NoSuchFieldException, IllegalAccessException {
    AuthServiceImpl impl = new AuthServiceImpl();
    impl.init();

    Field mdField = AuthServiceImpl.class.getDeclaredField("md");
    mdField.setAccessible(true);
    MessageDigest out = (MessageDigest) mdField.get(impl);
    assertNotNull(out);
  }

  @Test
  public void testLogin() {
    AuthServiceImpl spyImpl = spy(authService);
    doReturn("encryptedPassword").when(spyImpl).encrypt(anyString());
    when(userRepository.findByUsernameAndPassword(anyString(), eq("encryptedPassword"))).thenReturn(mock(User.class));
    SessionToken token = mock(SessionToken.class);
    doReturn(token).when(spyImpl).createToken(anyString(), any(Instant.class));

    SessionToken out = spyImpl.login("myuser", "mypassw");
    assertNotNull(out);
    assertEquals(token, out);
  }

  @Test
  public void testLoginFail() {
    AuthServiceImpl spyImpl = spy(authService);
    doReturn("encryptedPassword").when(spyImpl).encrypt(anyString());

    when(userRepository.findByUsernameAndPassword(anyString(), eq("encryptedPassword"))).thenReturn(null);
    SessionToken out = spyImpl.login("myuser", "mypass");
    assertNull(out);
  }

  @Test
  public void testCreateToken() {
    when(authConfig.getExpire()).thenReturn(123L);
    Instant current = Instant.now();
    Instant expire = current.plus(123L, ChronoUnit.SECONDS);
    SessionToken token = authService.createToken("myuser", current);
    assertNotNull(token);
    assertEquals(current, token.getSessionStartDate().toInstant());
    assertEquals(expire, token.getSessionEndDate().toInstant());
    assertEquals("myuser", token.getUsername());
    assertNotNull(token.getAuthToken());
  }

  @Test
  public void testLogout() {
    doNothing().when(sessionTokenRepository).delete(any(SessionToken.class));
    boolean result = authService.logout(mock(SessionToken.class));
    assertTrue(result);
  }

  @Test
  public void testLogoutFail() {
    assertFalse(authService.logout(null));
  }

  @Test
  public void testIsTokenValid() {
    Instant valid = Instant.now().plus(123L, ChronoUnit.HOURS);
    SessionToken token = mock(SessionToken.class);
    when(token.getSessionEndDate()).thenReturn(Timestamp.from(valid));

    assertTrue(authService.isTokenValid(token));

    Instant invalid = Instant.now().minus(123L, ChronoUnit.HOURS);
    when(token.getSessionEndDate()).thenReturn(Timestamp.from(invalid));

    assertFalse(authService.isTokenValid(token));
  }

  @Test
  public void testFindToken() {
    SessionToken token = mock(SessionToken.class);
    when(sessionTokenRepository.findByAuthToken(anyString())).thenReturn(token);

    SessionToken out = authService.findToken("mytoken");
    assertNotNull(out);
    assertEquals(out, token);
  }

  @Test
  public void testFindUserByToken() {
    SessionToken token = mock(SessionToken.class);
    when(token.getUsername()).thenReturn("myuser");
    User user = mock(User.class);
    when(userRepository.findByUsername(eq("myuser"))).thenReturn(user);

    User out = authService.findUserByToken(token);
    assertNotNull(out);
    assertEquals(user, out);

    out = authService.findUserByToken(null);
    assertNull(out);
  }

  @Test
  public void testEncryption() {
    String password = "12345";
    AuthServiceImpl impl = new AuthServiceImpl();
    impl.init();
    String out = impl.encrypt(password);
    assertNotNull(out);
    assertEquals("wxrGBXk/WAs4bA+1PxuXdQ==", out);
  }
}
