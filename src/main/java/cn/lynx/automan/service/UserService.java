package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.User;

public interface UserService {
  User findUser(String username);
}
