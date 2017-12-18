package cn.lynx.automan.data.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`Id`")
	protected int id;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
