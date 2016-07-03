package cn.lynx.automan.db.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AM_Subject")
public class Subject extends Model {
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Publish Date")
	private Timestamp publishDate;
	
	@Column(name = "Update Date")
	private Timestamp updateDate;
	
	@Column(name = "Read Time")
	private int readTime;
	
	@Column(name = "On Top")
	private boolean onTop;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<AMThread> threads;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public int getReadTime() {
		return readTime;
	}

	public void setReadTime(int readTime) {
		this.readTime = readTime;
	}

	public boolean isOnTop() {
		return onTop;
	}

	public void setOnTop(boolean onTop) {
		this.onTop = onTop;
	}

	public List<AMThread> getThreads() {
		return threads;
	}

	public void setThreads(List<AMThread> threads) {
		this.threads = threads;
	}
}
