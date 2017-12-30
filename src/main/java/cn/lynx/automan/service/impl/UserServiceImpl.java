package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.UserRepository;
import cn.lynx.automan.handler.Transformer;
import cn.lynx.automan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private List<Transformer<User>> transformers;

  @Override
  public User findUser(String username) {
    User user = userRepository.findByUsername(username);
    transformers.forEach(t -> t.handle(user));
    return user;
  }
}
