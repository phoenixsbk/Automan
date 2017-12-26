package cn.lynx.automan.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "`AMVote`")
public class Vote extends Model {
    @ManyToOne
    @JoinColumn(name = "`SubjectId`")
    private Subject subject;

    @Column(name = "`EndDate`")
    private Timestamp endDate;

    @Column(name = "`MaxVoteItem`")
    private int maxVoteItem;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vote")
    private List<VoteItem> voteItems;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getMaxVoteItem() {
        return maxVoteItem;
    }

    public void setMaxVoteItem(int maxVoteItem) {
        this.maxVoteItem = maxVoteItem;
    }
}
