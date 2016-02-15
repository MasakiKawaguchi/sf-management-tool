package com.SFManagementAntTask.partner.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomObject {

	public CustomObject() {}

	private String id;

	private String name;

	private String label;

	private boolean custom;

	private boolean feedEnabled;

	private String keyPrefix;

	private String labelPlural;

	private String namespacePrefix;

	private boolean activateable;

	private boolean customSetting;

	private boolean deprecatedAndHidden;

	private boolean idEnabled;

	private boolean layoutable;

	private boolean mergeable;

	private boolean replicateable;

	private boolean retrieveable;

	private boolean queryable;

	private boolean searchable;

	private boolean triggerable;

	private boolean createable;

	private boolean updateable;

	private boolean deletable;

	private boolean undeletable;

	private String description;

	private List<CustomField> fields;

	private List<Relationship> childRelationships;

	private Date createdDate;

	private String createdById;

	private String createdByName;

	private Date lastModifiedDate;

	private String lastModifiedById;

	private String lastModifiedByName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public boolean isFeedEnabled() {
		return feedEnabled;
	}

	public void setFeedEnabled(boolean feedEnabled) {
		this.feedEnabled = feedEnabled;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public String getLabelPlural() {
		return labelPlural;
	}

	public void setLabelPlural(String labelPlural) {
		this.labelPlural = labelPlural;
	}

	public boolean isActivateable() {
		return activateable;
	}

	public void setActivateable(boolean activateable) {
		this.activateable = activateable;
	}

	public boolean isCustomSetting() {
		return customSetting;
	}

	public void setCustomSetting(boolean customSetting) {
		this.customSetting = customSetting;
	}

	public boolean isDeprecatedAndHidden() {
		return deprecatedAndHidden;
	}

	public void setDeprecatedAndHidden(boolean deprecatedAndHidden) {
		this.deprecatedAndHidden = deprecatedAndHidden;
	}

	public boolean isIdEnabled() {
		return idEnabled;
	}

	public void setIdEnabled(boolean idEnabled) {
		this.idEnabled = idEnabled;
	}

	public boolean isLayoutable() {
		return layoutable;
	}

	public void setLayoutable(boolean layoutable) {
		this.layoutable = layoutable;
	}

	public boolean isMergeable() {
		return mergeable;
	}

	public void setMergeable(boolean mergeable) {
		this.mergeable = mergeable;
	}

	public boolean isReplicateable() {
		return replicateable;
	}

	public void setReplicateable(boolean replicateable) {
		this.replicateable = replicateable;
	}

	public boolean isRetrieveable() {
		return retrieveable;
	}

	public void setRetrieveable(boolean retrieveable) {
		this.retrieveable = retrieveable;
	}

	public boolean isQueryable() {
		return queryable;
	}

	public void setQueryable(boolean queryable) {
		this.queryable = queryable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isTriggerable() {
		return triggerable;
	}

	public void setTriggerable(boolean triggerable) {
		this.triggerable = triggerable;
	}

	public boolean isCreateable() {
		return createable;
	}

	public void setCreateable(boolean createable) {
		this.createable = createable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isUndeletable() {
		return undeletable;
	}

	public void setUndeletable(boolean undeletable) {
		this.undeletable = undeletable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedByName() {
		return lastModifiedByName;
	}

	public void setLastModifiedByName(String lastModifiedByName) {
		this.lastModifiedByName = lastModifiedByName;
	}

	public String getNamespacePrefix() {
		return namespacePrefix;
	}

	public void setNamespacePrefix(String namespacePrefix) {
		this.namespacePrefix = namespacePrefix;
	}

}
