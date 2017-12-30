package cn.lynx.automan.handler.subject;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.handler.Transformer;
import org.springframework.stereotype.Component;

@Component
public class SubjectFakeTransformer implements Transformer<Subject> {
  @Override
  public void handle(Subject subject) {
    System.out.println("subject transformer");
  }
}
