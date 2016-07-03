package cn.lynx.automan.db.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`AM_Thread`")
public class AMThread extends Model {
	@OneToOne
	@JoinColumn(name = "`Subject Id`")
	private Subject subject;
	
	@Lob
	@Column(name = "`Content`")
	private String content;
	
	@Column(name = "`Publish Date`")
	private Timestamp publishDate;
	
	@OneToOne
	@JoinColumn(name = "`Author Id`")
	private User user;
	
	@Column(name = "`Author Hidden`")
	private boolean authorHidden;
	
	@Column(name = "`Publish IP`")
	private String publishIP;
	
	@Column(name = "`Editted`")
	private boolean editted;
	
	@Column(name = "`Edit Time`")
	private Timestamp editTime;
	
	@Column(name = "`Status`")
	private int status;

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAuthorHidden() {
		return authorHidden;
	}

	public void setAuthorHidden(boolean authorHidden) {
		this.authorHidden = authorHidden;
	}

	public String getPublishIP() {
		return publishIP;
	}

	public void setPublishIP(String publishIP) {
		this.publishIP = publishIP;
	}

	public boolean isEditted() {
		return editted;
	}

	public void setEditted(boolean editted) {
		this.editted = editted;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
