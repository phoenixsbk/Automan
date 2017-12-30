package cn.lynx.automan.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`AMSubject`")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Subject extends Model {
  @Column(name = "`Title`")
  private String title;

  @Column(name = "`PublishDate`")
  private Timestamp publishDate;

  @Column(name = "`UpdateDate`")
  private Timestamp updateDate;

  @Column(name = "`Views`")
  private int views;

  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<SubjectStatus> statuses;

  @JsonIgnore
  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
  private List<AThread> threads;

  @JsonIgnore
  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
  private Set<Vote> votes;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Timestamp getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(Timestamp publishDate) {
    this.publishDate = publishDate;
  }

  public Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Timestamp updateDate) {
    this.updateDate = updateDate;
  }

  public Set<SubjectStatus> getStatuses() {
    return statuses;
  }

  public void setStatuses(Set<SubjectStatus> statuses) {
    this.statuses = statuses;
  }

  public int getViews() {
    return views;
  }

  public void setViews(int views) {
    this.views = views;
  }

  public List<AThread> getThreads() {
    return threads;
  }

  public void setThreads(List<AThread> threads) {
    this.threads = threads;
  }
}
