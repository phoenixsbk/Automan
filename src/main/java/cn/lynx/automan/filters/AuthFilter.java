package cn.lynx.automan.filters;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static cn.lynx.automan.service.AuthService.CONTEXT_SESSIONTOKEN;

@Provider
public class AuthFilter implements ContainerRequestFilter {
  @Autowired
  private AuthConfig authConfig;

  @Autowired
  private AuthService authService;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    String urlRes = requestContext.getUriInfo().getPath();
    if (urlRes.startsWith("auth/login") || urlRes.startsWith("auth/logout") || urlRes.startsWith("auth/validate")) {
      return;
    }

    Cookie authCookie = requestContext.getCookies().get(authConfig.getCookieKey());
    if (authCookie != null && !StringUtils.isEmpty(authCookie.getValue())) {
      SessionToken token = authService.findToken(authCookie.getValue());
      if (token != null) {
        if (authService.isTokenValid(token)) {
          System.out.println("Token Valid");
          requestContext.setProperty(CONTEXT_SESSIONTOKEN, token);
          return;
        } else {
          System.out.println("Token invalid:" + token);
          authService.logout(token);
        }
      } else {
        System.out.println("Token empty");
      }
    } else {
      System.out.println("Cookie Empty");
    }

    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
  }
}
