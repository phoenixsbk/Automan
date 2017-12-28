package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.entity.User;

public interface AuthService {
  String CONTEXT_SESSIONTOKEN = "AUTH_SVC_CONTEXT_SESSIONTOKEN";

  SessionToken login(String username, String password);

  boolean logout(SessionToken token);

  boolean isTokenValid(SessionToken token);

  SessionToken findToken(String token);

  User findUserByToken(SessionToken token);
}
