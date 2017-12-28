package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.UserRepository;
import cn.lynx.automan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findUser(String username) {
    return userRepository.findByUsername(username);
  }
}
