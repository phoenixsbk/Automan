package cn.lynx.automan.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "`AMSubject`")
public class Subject extends Model {
    @Column(name = "`Title`")
    private String title;

    @Column(name = "`Publish Date`")
    private Timestamp publishDate;

    @Column(name = "`UpdateDate`")
    private Timestamp updateDate;

    @Column(name = "`Views`")
    private int views;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubjectStatus> statuses;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AThread> threads;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vote> votes;

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

    public List<SubjectStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<SubjectStatus> statuses) {
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
