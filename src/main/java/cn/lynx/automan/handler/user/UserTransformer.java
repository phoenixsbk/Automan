package cn.lynx.automan.handler.user;

import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.handler.Transformer;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer implements Transformer<User> {
  @Override
  public void handle(User user) {
    user.setPassword(null);
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE + 50;
  }
}
