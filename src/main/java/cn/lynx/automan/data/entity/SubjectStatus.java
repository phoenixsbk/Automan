package cn.lynx.automan.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "`AMSubjectStatus`")
public class SubjectStatus extends Model {
  @ManyToOne
  @JoinColumn(name = "`SubjectId`")
  private Subject subject;

  @Column(name = "`Status`")
  private String status;

  @Column(name = "`StatusValue`")
  private String statusValue;

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusValue() {
    return statusValue;
  }

  public void setStatusValue(String statusValue) {
    this.statusValue = statusValue;
  }

  @Override
  public boolean equals(Object that) {
    return status != null && that != null && (that instanceof SubjectStatus) && status.equals(((SubjectStatus) that).status);
  }
}
