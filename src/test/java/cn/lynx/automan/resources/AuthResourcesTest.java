package cn.lynx.automan.resources;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthResourcesTest {
  @InjectMocks
  AuthResources authResources;

  @Mock
  AuthService authService;

  @Mock
  AuthConfig authConfig;

  @Test
  public void testLoginWrongUser() {
    when(authService.login(anyString(), anyString())).thenReturn(null);
    Response resp = authResources.loginUser("myuser", "mypass");
    assertEquals(401, resp.getStatus());
  }

  @Test
  public void testLoginOKUser() {
    SessionToken token = mock(SessionToken.class);
    when(authService.login(anyString(), anyString())).thenReturn(token);
    when(token.getAuthToken()).thenReturn("abcde");
    when(authConfig.getCookieKey()).thenReturn("mycookie");
    when(authConfig.getExpire()).thenReturn(123L);

    Response resp = authResources.loginUser("myuser", "mypass");
    assertEquals(200, resp.getStatus());
    assertFalse(resp.getCookies().isEmpty());
    assertEquals("abcde", resp.getCookies().get("mycookie").getValue());
    assertEquals(123L, resp.getCookies().get("mycookie").getMaxAge());
  }

  @Test
  public void testLogout() {
    HttpHeaders headers = mock(HttpHeaders.class);
    SessionToken token = mock(SessionToken.class);
    Map<String, Cookie> cookieMap = new HashMap<>();
    when(headers.getCookies()).thenReturn(cookieMap);
    Cookie mycookie = mock(Cookie.class);
    cookieMap.put("mycookie", mycookie);
    when(mycookie.getValue()).thenReturn("mytoken");
    when(authConfig.getCookieKey()).thenReturn("mycookie");
    when(authService.findToken(eq("mytoken"))).thenReturn(token);
    when(authService.logout(any(SessionToken.class))).thenReturn(true);
    Response resp = authResources.logoutUser(headers);
    assertEquals(200, resp.getStatus());
    assertEquals("", resp.getCookies().get("mycookie").getValue());
  }

  @Test
  public void testValidateToken() {
    HttpHeaders headers = mock(HttpHeaders.class);
    SessionToken token = mock(SessionToken.class);
    Map<String, Cookie> cookieMap = new HashMap<>();
    when(headers.getCookies()).thenReturn(cookieMap);
    Cookie mycookie = mock(Cookie.class);
    cookieMap.put("mycookie", mycookie);
    when(mycookie.getValue()).thenReturn("mytoken");
    when(authConfig.getCookieKey()).thenReturn("mycookie");
    when(authService.findToken(eq("mytoken"))).thenReturn(token);
    when(authService.isTokenValid(any(SessionToken.class))).thenReturn(true);

    Response resp = authResources.validateToken(headers);
    assertEquals(200, resp.getStatus());

    when(authService.isTokenValid(any(SessionToken.class))).thenReturn(false);
    resp = authResources.validateToken(headers);
    assertEquals(401, resp.getStatus());

    cookieMap.clear();
    resp = authResources.validateToken(headers);
    assertEquals(401, resp.getStatus());
  }
}
