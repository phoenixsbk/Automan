package cn.lynx.automan.db.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AM_User")
public class User extends Model {
	
	@Column(name = "Username")
	private String username;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "EMail")
	private String email;
	
	@Column(name = "LastLogin")
	private Date lastLogin;
}
