package cn.lynx.automan.handler.thread;

public enum ThreadStatuses {
  DISABLED("Disabled"),
  AUTHOR_HIDDEN("AuthorHide");

  private String value;

  ThreadStatuses(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
