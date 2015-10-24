package cn.lynx.automan.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AM_Attach")
public class Attach extends Model {
	@JoinColumn(name = "Thread Id")
	@OneToMany
	private AMThread thread;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Type")
	private String type;
	
	@Column(name = "Link")
	private String link;
}
