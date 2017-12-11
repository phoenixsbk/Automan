package cn.lynx.automan.db.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "`AMUserState`")
public class UserState extends Model {
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
