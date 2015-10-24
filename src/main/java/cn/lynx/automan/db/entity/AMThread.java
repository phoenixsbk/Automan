package cn.lynx.automan.db.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AM_Thread")
public class AMThread extends Model {
	@OneToMany
	@JoinColumn(name = "Subject Id")
	private Subject subject;
	
	@Lob
	@Column(name = "Content")
	private String content;
	
	@Column(name = "Publish Date")
	private Date publishDate;
	
	@OneToMany
	@JoinColumn(name = "Author Id")
	private User user;
	
	@Column(name = "Author Hidden")
	private boolean authorHidden;
	
	@Column(name = "Publish IP")
	private String publishIP;
	
	@Column(name = "Editted")
	private boolean editted;
	
	@Column(name = "Edit Time")
	private Date editTime;
	
	@Column(name = "Status")
	private int status;
}
