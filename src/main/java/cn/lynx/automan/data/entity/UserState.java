package cn.lynx.automan.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "`AMUserState`")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserState extends Model {
  @JsonIgnore
  @OneToOne(mappedBy = "userState")
  private User user;

  @Column(name = "`LoginTimes`")
  private int loginTimes;

  @Column(name = "`PublishTimes`")
  private int publishTimes;

  @Column(name = "`LastLoginTime`")
  private Timestamp lastLoginTime;

  @Column(name = "`LastIP`")
  private String lastIp;

  @Column(name = "`Status`")
  private int status;

  @Column(name = "`Role`")
  private int role;

  @Column(name = "`Level`")
  private int expLevel;

  @Column(name = "`Currency`")
  private int currency;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getLoginTimes() {
    return loginTimes;
  }

  public void setLoginTimes(int loginTimes) {
    this.loginTimes = loginTimes;
  }

  public int getPublishTimes() {
    return publishTimes;
  }

  public void setPublishTimes(int publishTimes) {
    this.publishTimes = publishTimes;
  }

  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getLastIp() {
    return lastIp;
  }

  public void setLastIp(String lastIp) {
    this.lastIp = lastIp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  public int getExpLevel() {
    return expLevel;
  }

  public void setExpLevel(int expLevel) {
    this.expLevel = expLevel;
  }

  public int getCurrency() {
    return currency;
  }

  public void setCurrency(int currency) {
    this.currency = currency;
  }
}
