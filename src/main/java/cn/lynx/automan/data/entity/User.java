package cn.lynx.automan.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "`AMUser`")
public class User extends Model {

  @Column(name = "`Username`")
  private String username;

  @JsonIgnore
  @Column(name = "`Password`")
  private String password;

  @Column(name = "`RealName`")
  private String realName;

  @Column(name = "`Gender`")
  private int gender;

  @Column(name = "`Birthday`")
  private Date birthday;

  @Column(name = "`Email`")
  private String email;

  @Column(name = "`Mobile`")
  private String mobile;

  @Column(name = "`Grade`")
  private String grade;

  @Column(name = "`Signature`")
  @Lob
  private String signature;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "`UserStateId`")
  private UserState userState;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }
}
