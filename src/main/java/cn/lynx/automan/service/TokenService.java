package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.SessionToken;

public interface TokenService {
  SessionToken getToken(String username);

  SessionToken createToken(String username);

  boolean isTokenValid(String authToken);

  boolean invalidateToken(String authToken);
}
