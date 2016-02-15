package com.SFManagementAntTask.partner.dao.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.sforce.soap.partner.FilteredLookupInfo;
import com.sforce.soap.partner.PicklistEntry;

public class CustomField {

	/** ログ */
	//private final static Logger log = LoggerFactory.getLogger(CustomField.class);

	public CustomField() {}

	private String id;

	private String name;

	private String label;

	private String entityName;

	private String picklistvalues = "";

	private boolean autoNumber;

	private boolean alculated;

	private String calculatedFormula;

	private boolean cascadeDelete;

	private boolean caseSensitive;

	private String controllerName;

	private boolean createable;

	private boolean custom;

	private boolean defaultedOnCreate;

	private boolean dependentPicklist;

	private boolean deprecatedAndHidden;

	private int digits;

	private boolean displayLocationInDecimal;

	private boolean encrypted;

	private boolean externalId;

	private String extraTypeInfo;

	private boolean filterable;

	private String filteredLookupInfo;

	private boolean groupable;

	private boolean highScaleNumber;

	private boolean htmlFormatted;

	private boolean idLookup;

	private String inlineHelpText;

	private int length; // int

	private String mask; // string

	private String maskType; //string

	private boolean isNameField;

	private boolean isNamePointing;

	private boolean isNillable;

	private boolean isPermissionable;

	private int precision; //int

	private boolean isQueryByDistance;

	private String referenceTargetField;

	private String referenceTo; // string[]

	private String relationshipName; // string

	private int relationshipOrder; // int

	private boolean isRestrictedDelete;

	private boolean isRestrictedPicklist;

	private int scale;

	private boolean isSortable;

	private String type;

	private boolean isUnique;

	private boolean isUpdateable;

	private boolean writeRequiresMasterRead;

	private String description;

	private String defaultValue;

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

	public String getPicklistvalues() {
		return picklistvalues;
	}

	public void addPicklistvalues(String label) {
		if (StringUtils.isBlank(this.picklistvalues)) {
			this.picklistvalues = label;
			return;
		}
		this.picklistvalues += " | " + label;
		//log.debug("picklistvalues..." + this.picklistvalues);
	}

	public void setPicklistvalues(PicklistEntry[] picklistValues) {
		for (PicklistEntry pobj : picklistValues) {
			addPicklistvalues(pobj.getLabel());
		}
	}

	public boolean isAutoNumber() {
		return autoNumber;
	}

	public String getAutoNumber() {
		if (autoNumber == false) {
			return "×";
		}
		return "○";
	}

	public void setAutoNumber(boolean autoNumber) {
		this.autoNumber = autoNumber;
	}

	public boolean isAlculated() {
		return alculated;
	}

	public String getAlculated() {
		if (alculated == false) {
			return "×";
		}
		return "○";
	}

	public void setAlculated(boolean alculated) {
		this.alculated = alculated;
	}

	public String getCalculatedFormula() {
		return calculatedFormula;
	}

	public void setCalculatedFormula(String calculatedFormula) {
		this.calculatedFormula = calculatedFormula;
	}

	public boolean isCascadeDelete() {
		return cascadeDelete;
	}

	public String getCascadeDelete() {
		if (cascadeDelete == false) {
			return "×";
		}
		return "○";
	}

	public void setCascadeDelete(boolean cascadeDelete) {
		this.cascadeDelete = cascadeDelete;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public String getCaseSensitive() {
		if (caseSensitive == false) {
			return "×";
		}
		return "○";
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public boolean isCreateable() {
		return createable;
	}

	public String getCreateable() {
		if (createable == false) {
			return "×";
		}
		return "○";
	}

	public void setCreateable(boolean createable) {
		this.createable = createable;
	}

	public boolean isCustom() {
		return custom;
	}

	public String getCustom() {
		if (custom == false) {
			return "×";
		}
		return "○";
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public boolean isDefaultedOnCreate() {
		return defaultedOnCreate;
	}

	public String getDefaultedOnCreate() {
		if (defaultedOnCreate == false) {
			return "×";
		}
		return "○";
	}

	public void setDefaultedOnCreate(boolean defaultedOnCreate) {
		this.defaultedOnCreate = defaultedOnCreate;
	}

	public boolean isDependentPicklist() {
		return dependentPicklist;
	}

	public void setDependentPicklist(boolean dependentPicklist) {
		this.dependentPicklist = dependentPicklist;
	}

	public boolean isDeprecatedAndHidden() {
		return deprecatedAndHidden;
	}

	public void setDeprecatedAndHidden(boolean deprecatedAndHidden) {
		this.deprecatedAndHidden = deprecatedAndHidden;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public boolean isDisplayLocationInDecimal() {
		return displayLocationInDecimal;
	}

	public void setDisplayLocationInDecimal(boolean displayLocationInDecimal) {
		this.displayLocationInDecimal = displayLocationInDecimal;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public boolean isExternalId() {
		return externalId;
	}

	public String getExternalId() {
		if (externalId == false) {
			return "×";
		}
		return "○";
	}

	public void setExternalId(boolean externalId) {
		this.externalId = externalId;
	}

	public String getExtraTypeInfo() {
		return extraTypeInfo;
	}

	public void setExtraTypeInfo(String extraTypeInfo) {
		this.extraTypeInfo = extraTypeInfo;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public String getFilteredLookupInfo() {
		return filteredLookupInfo;
	}

	public void addFilteredLookupInfo(String fieldname) {
		if (StringUtils.isBlank(this.filteredLookupInfo)) {
			this.filteredLookupInfo = fieldname;
		}
		this.filteredLookupInfo += " | " + fieldname;
	}

	public void setFilteredLookupInfo(FilteredLookupInfo filteredLookInfo) {
		if (filteredLookInfo != null) {
			for (String fieldname : filteredLookInfo.getControllingFields()) {
				addFilteredLookupInfo(fieldname);
			}
		}
	}

	public boolean isGroupable() {
		return groupable;
	}

	public void setGroupable(boolean groupable) {
		this.groupable = groupable;
	}

	public boolean isHighScaleNumber() {
		return highScaleNumber;
	}

	public void setHighScaleNumber(boolean highScaleNumber) {
		this.highScaleNumber = highScaleNumber;
	}

	public boolean isHtmlFormatted() {
		return htmlFormatted;
	}

	public void setHtmlFormatted(boolean htmlFormatted) {
		this.htmlFormatted = htmlFormatted;
	}

	public boolean isIdLookup() {
		return idLookup;
	}

	public void setIdLookup(boolean idLookup) {
		this.idLookup = idLookup;
	}

	public String getInlineHelpText() {
		return inlineHelpText;
	}

	public void setInlineHelpText(String inlineHelpText) {
		this.inlineHelpText = inlineHelpText;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getMaskType() {
		return maskType;
	}

	public void setMaskType(String maskType) {
		this.maskType = maskType;
	}

	public boolean isNameField() {
		return isNameField;
	}

	public String getNameField() {
		if (isNameField == false) {
			return "×";
		}
		return "○";
	}

	public void setNameField(boolean isNameField) {
		this.isNameField = isNameField;
	}

	public boolean isNamePointing() {
		return isNamePointing;
	}

	public String getNamePointing() {
		if (isNamePointing == false) {
			return "×";
		}
		return "○";
	}

	public void setNamePointing(boolean isNamePointing) {
		this.isNamePointing = isNamePointing;
	}

	public boolean isNillable() {
		return isNillable;
	}

	public String getNillable() {
		if (isNillable == false) {
			return "×";
		}
		return "○";
	}

	public void setNillable(boolean isNillable) {
		this.isNillable = isNillable;
	}

	public boolean isPermissionable() {
		return isPermissionable;
	}

	public void setPermissionable(boolean isPermissionable) {
		this.isPermissionable = isPermissionable;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public boolean isQueryByDistance() {
		return isQueryByDistance;
	}

	public void setQueryByDistance(boolean isQueryByDistance) {
		this.isQueryByDistance = isQueryByDistance;
	}

	public String getReferenceTargetField() {
		return referenceTargetField;
	}

	public void setReferenceTargetField(String referenceTargetField) {
		this.referenceTargetField = referenceTargetField;
	}

	public String getReferenceTo() {
		return referenceTo;
	}

	public void setReferenceTo(String[] referenceTo) {
		for (String str : referenceTo) {
			if (StringUtils.isBlank(this.referenceTo)) {
				this.referenceTo = str;
				continue;
			}
			this.referenceTo += "|" + str;
		}
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	public int getRelationshipOrder() {
		return relationshipOrder;
	}

	public void setRelationshipOrder(int relationshipOrder) {
		this.relationshipOrder = relationshipOrder;
	}

	public boolean isRestrictedDelete() {
		return isRestrictedDelete;
	}

	public void setRestrictedDelete(boolean isRestrictedDelete) {
		this.isRestrictedDelete = isRestrictedDelete;
	}

	public boolean isRestrictedPicklist() {
		return isRestrictedPicklist;
	}

	public void setRestrictedPicklist(boolean isRestrictedPicklist) {
		this.isRestrictedPicklist = isRestrictedPicklist;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public boolean isSortable() {
		return isSortable;
	}

	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public boolean isUpdateable() {
		return isUpdateable;
	}

	public void setUpdateable(boolean isUpdateable) {
		this.isUpdateable = isUpdateable;
	}

	public boolean isWriteRequiresMasterRead() {
		return writeRequiresMasterRead;
	}

	public void setWriteRequiresMasterRead(boolean writeRequiresMasterRead) {
		this.writeRequiresMasterRead = writeRequiresMasterRead;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
