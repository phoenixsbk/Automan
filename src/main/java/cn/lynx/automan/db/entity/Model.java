package cn.lynx.automan.db.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Model {
	protected int id;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
