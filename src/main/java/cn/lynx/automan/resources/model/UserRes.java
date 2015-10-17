package cn.lynx.automan.resources.model;

import java.sql.Date;

public class UserRes {
	private String username;
	
	private String realName;
	
	private int gender;
	
	private Date birthday;
	
	private String email;
	
	private String mobile;
	
	private String grade;
	
	private String signature;
	
	private UserStateRes userState;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public UserStateRes getUserState() {
		return userState;
	}

	public void setUserState(UserStateRes userState) {
		this.userState = userState;
	}
}
