package cn.lynx.automan.filters;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthFilter implements ContainerRequestFilter {
  @Autowired
  private AuthConfig authConfig;

  @Autowired
  private TokenService tokenService;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    UriInfo uri = requestContext.getUriInfo();
    String urlRes = uri.getPath();
    if (urlRes.startsWith("auth/login") || urlRes.startsWith("auth/logout")) {
      return;
    }

    Cookie authCookie = requestContext.getCookies().get(authConfig.getCookieKey());
    if (authCookie == null || StringUtils.isEmpty(authCookie.getValue())) {
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    } else {
      String tokenValue = authCookie.getValue();
      if (!tokenService.isTokenValid(tokenValue)) {
        tokenService.invalidateToken(tokenValue);
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).cookie(new NewCookie(authConfig.getCookieKey(), null)).build());
      }
    }
  }
}
