package cn.lynx.automan.handler.thread;

import cn.lynx.automan.data.entity.AThread;
import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.handler.Transformer;
import org.springframework.stereotype.Component;

@Component
public class CommonTransformer implements Transformer<AThread> {
  @Override
  public void handle(AThread aThread) {
    User user = aThread.getUser();
    if (user != null) {
      user.setUsername(null);
      user.setPassword(null);
      user.setBirthday(null);
      user.setEmail(null);
      user.setMobile(null);
    }
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE + 75;
  }
}
