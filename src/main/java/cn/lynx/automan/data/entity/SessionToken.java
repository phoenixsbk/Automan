package cn.lynx.automan.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "`AMSessionToken`")
public class SessionToken extends Model {
    @Column(name = "`Username`")
    private String username;

    @Column(name = "`AuthToken`")
    private String authToken;

    @Column(name = "`SessionStartDate`")
    private Timestamp sessionStartDate;

    @Column(name = "`SessionEndDate`")
    private Timestamp sessionEndDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Timestamp getSessionStartDate() {
        return sessionStartDate;
    }

    public void setSessionStartDate(Timestamp sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public Timestamp getSessionEndDate() {
        return sessionEndDate;
    }

    public void setSessionEndDate(Timestamp sessionEndDate) {
        this.sessionEndDate = sessionEndDate;
    }
}
