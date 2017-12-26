package cn.lynx.automan.data.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`AMVoteItem`")
public class VoteItem extends Model {
    @ManyToOne
    @JoinColumn(name = "`VoteId`")
    private Vote vote;

    @Column(name = "`Content`")
    private String content;

    @OneToMany(mappedBy = "voteItem", cascade = CascadeType.ALL)
    private Set<VoteRecord> voteRecords;

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

    public Set<VoteRecord> getVoteRecords() {
        return voteRecords;
    }

    public void setVoteRecords(Set<VoteRecord> voteRecords) {
        this.voteRecords = voteRecords;
    }
}
