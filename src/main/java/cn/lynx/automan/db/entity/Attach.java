package cn.lynx.automan.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`AM_Attach`")
public class Attach extends Model {
	@JoinColumn(name = "`Thread Id`")
	@OneToOne
	private AMThread thread;
	
	@Column(name = "`Title`")
	private String title;
	
	@Column(name = "`Type`")
	private String type;
	
	@Column(name = "`Link`")
	private String link;

	public AMThread getThread() {
		return thread;
	}

	public void setThread(AMThread thread) {
		this.thread = thread;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
