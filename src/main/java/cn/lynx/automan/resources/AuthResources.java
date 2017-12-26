package cn.lynx.automan.resources;

import cn.lynx.automan.configuration.AuthConfig;
import cn.lynx.automan.service.AuthService;
import cn.lynx.automan.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.Map;

@Path("/auth")
public class AuthResources {
  @Autowired
  private AuthService authService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private AuthConfig authConfig;

  @POST
  @Path("/login/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loginUser(@PathParam("username") String username, @HeaderParam("AMPass") String password) {
    String token = authService.login(username, password);
    if (token == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } else {
      return Response.ok(Collections.singletonMap("token", token))
          .cookie(new NewCookie(authConfig.getCookieKey(),
              token,
              null,
              null,
              null,
              (int) authConfig.getExpire(),
              false)).build();
    }
  }

  @POST
  @Path("/logout")
  @Produces(MediaType.APPLICATION_JSON)
  public Response logoutUser(@Context HttpHeaders headers) {
    Cookie tokenCookie = headers.getCookies().get(authConfig.getCookieKey());
    if (tokenCookie != null) {
      tokenService.invalidateToken(tokenCookie.getValue());
    }
    return Response.ok().cookie(new NewCookie(authConfig.getCookieKey(), null)).build();
  }

  @GET
  @Path("/validate")
  @Produces(MediaType.APPLICATION_JSON)
  public Response validateToken(@Context HttpHeaders headers) {
    Map<String, Cookie> cookieMap = headers.getCookies();
    Cookie cookie = cookieMap.get(authConfig.getCookieKey());
    if (cookie != null) {
      String token = cookie.getValue();
      if (tokenService.isTokenValid(token)) {
        return Response.ok().build();
      }
    }

    return Response.status(Response.Status.UNAUTHORIZED).build();
  }
}
