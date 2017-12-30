package cn.lynx.automan.handler;

public interface Transformer<T> {
  void handle(T t);
}
