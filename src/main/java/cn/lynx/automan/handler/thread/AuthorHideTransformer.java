package cn.lynx.automan.handler.thread;

import cn.lynx.automan.data.entity.AThread;
import cn.lynx.automan.data.entity.ThreadStatus;
import cn.lynx.automan.handler.Transformer;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthorHideTransformer implements Transformer<AThread> {

  @Override
  public void handle(AThread thread) {
    Set<ThreadStatus> statuses = thread.getThreadStatuses();
    if (statuses != null) {
      if (statuses.stream().anyMatch(s -> s.getStatus().equals(ThreadStatuses.AUTHOR_HIDDEN.value()))) {
        thread.setUser(null);
      }
    }
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE + 50;
  }
}
