package cn.lynx.automan.db.entity;

import java.sql.Date;
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
	private Date publishDate;
	
	@Column(name = "Update Date")
	private Date updateDate;
	
	@Column(name = "Read Time")
	private int readTime;
	
	@Column(name = "On Top")
	private boolean onTop;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
	private List<AMThread> threads;
}
