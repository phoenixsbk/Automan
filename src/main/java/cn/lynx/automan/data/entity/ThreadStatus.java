package cn.lynx.automan.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "`AMThreadStatus`")
public class ThreadStatus extends Model {
    @ManyToOne
    @JoinColumn(name = "`ThreadId`")
    private AThread thread;

    @Column(name = "`Status`")
    private String status;

    public AThread getThread() {
        return thread;
    }

    public void setThread(AThread thread) {
        this.thread = thread;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
