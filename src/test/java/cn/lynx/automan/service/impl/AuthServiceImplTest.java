package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.UserRepository;
import cn.lynx.automan.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.security.MessageDigest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {
  @InjectMocks
  AuthServiceImpl authService;

  @Mock
  TokenService tokenService;

  @Mock
  UserRepository userRepository;

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

    User user = mock(User.class);
    when(userRepository.findByUsernameAndPassword(anyString(), eq("encryptedPassword"))).thenReturn(user);

    SessionToken token = mock(SessionToken.class);
    when(tokenService.getOrCreateToken(anyString())).thenReturn(token);
    when(token.getAuthToken()).thenReturn("myToken");

    String tokenstr = spyImpl.login("myuser", "mypassw");
    assertNotNull(tokenstr);
    assertEquals("myToken", tokenstr);
  }

  @Test
  public void testLoginFail() {
    AuthServiceImpl spyImpl = spy(authService);
    doReturn("encryptedPassword").when(spyImpl).encrypt(anyString());

    when(userRepository.findByUsernameAndPassword(anyString(), eq("encryptedPassword"))).thenReturn(null);
    String tokenStr = spyImpl.login("myuser", "mypass");
    assertNull(tokenStr);
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
