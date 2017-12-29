package cn.lynx.automan.data.entity;

public enum SubjectStatuses {
  TOP("Top"),
  ELITE("Elite");

  private String status;

  SubjectStatuses(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return this.status;
  }
}
