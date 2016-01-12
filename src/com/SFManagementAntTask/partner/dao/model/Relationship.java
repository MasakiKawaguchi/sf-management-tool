package com.SFManagementAntTask.partner.dao.model;

public class Relationship {

	private String name;

	private String relationshipName;

	private String childSObject;

	public Relationship() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	public String getChildSObject() {
		return childSObject;
	}

	public void setChildSObject(String childSObject) {
		this.childSObject = childSObject;
	}

}
