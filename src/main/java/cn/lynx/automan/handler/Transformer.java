package cn.lynx.automan.handler;

import org.springframework.core.Ordered;

public interface Transformer<T> extends Ordered {
  void handle(T t);
}
