package cn.lynx.automan.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "`AMThread`")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AThread extends Model {
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "`SubjectId`")
  private Subject subject;

  @Lob
  @Column(name = "`Content`")
  private String content;

  @Column(name = "`PublishDate`")
  private Timestamp publishDate;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "`AuthorId`")
  private User user;

  @Column(name = "`IP`")
  private String ip;

  @Column(name = "`EditDate`")
  private Timestamp editDate;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<ThreadStatus> threadStatuses;

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(Timestamp publishDate) {
    this.publishDate = publishDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Timestamp getEditDate() {
    return editDate;
  }

  public void setEditDate(Timestamp editDate) {
    this.editDate = editDate;
  }

  public Set<ThreadStatus> getThreadStatuses() {
    return threadStatuses;
  }

  public void setThreadStatuses(Set<ThreadStatus> threadStatuses) {
    this.threadStatuses = threadStatuses;
  }
}
