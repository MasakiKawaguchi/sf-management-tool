package com.SFManagementAntTask.tooling.dao.model;

public class Line implements ISFDto {

	private Integer num = 0;

	private Integer hits = 0;

	private boolean branch = false;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void addHits() {
		++this.hits;
	}

	public boolean isBranch() {
		return branch;
	}

	public void setBranch(boolean branch) {
		this.branch = branch;
	}
}
