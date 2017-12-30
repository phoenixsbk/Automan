package cn.lynx.automan.handler.subject;

public enum SubjectStatuses {
  TOP("Top"),
  ELITE("Elite");

  private String status;

  SubjectStatuses(String status) {
    this.status = status;
  }

  public String value() {
    return this.status;
  }

  @Override
  public String toString() {
    return this.status;
}
}
