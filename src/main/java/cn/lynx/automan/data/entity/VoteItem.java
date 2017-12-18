package cn.lynx.automan.data.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`AMVoteItem`")
public class VoteItem extends Model {
    @ManyToOne
    @JoinColumn(name = "`VoteId`")
    private Vote vote;

    @Column(name = "`Content`")
    private String content;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VoteRecord> voteRecords;

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
