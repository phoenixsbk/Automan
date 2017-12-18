package cn.lynx.automan.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "`AMVoteRecord`")
public class VoteRecord extends Model {
    @ManyToOne
    @JoinColumn(name = "`VoteItemId`")
    private VoteItem voteItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`UserId`")
    private User user;

    @Column(name = "`IP`")
    private String ip;

    @Column(name = "`VoteDate`")
    private Timestamp voteDate;

    public VoteItem getVoteItem() {
        return voteItem;
    }

    public void setVoteItem(VoteItem voteItem) {
        this.voteItem = voteItem;
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

    public Timestamp getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Timestamp voteDate) {
        this.voteDate = voteDate;
    }
}
