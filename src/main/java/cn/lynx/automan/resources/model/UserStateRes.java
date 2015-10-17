package cn.lynx.automan.resources.model;

import java.sql.Timestamp;

public class UserStateRes {
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

	private int loginTimes;

	private int publishTimes;

	private Timestamp lastLoginTime;

	private String lastIp;

	private int status;

	private int role;

	private int expLevel;

	private int currency;
}
