package com.SFManagementAntTask.partner.dao.model;

import java.util.ArrayList;
import java.util.List;

public class CustomObject {

	public CustomObject() {}

	private String name;

	private String label;

	private List<CustomField> fields;

	private List<Relationship> childRelationships;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<CustomField> getFields() {
		return fields;
	}

	public void setFields(List<CustomField> fields) {
		this.fields = fields;
	}

	public void addFields(CustomField field) {
		if (this.fields == null) {
			this.fields = new ArrayList<CustomField>();
		}
		this.fields.add(field);
	}

	public List<Relationship> getChildRelationships() {
		return childRelationships;
	}

	public void setChildRelationships(List<Relationship> childRelationships) {
		this.childRelationships = childRelationships;
	}

	public void addChildRelationships(Relationship childRelationship) {
		if (this.childRelationships == null) {
			this.childRelationships = new ArrayList<Relationship>();
		}
		this.childRelationships.add(childRelationship);
	}
}
