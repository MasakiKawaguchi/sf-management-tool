package com.SFManagementAntTask.tooling.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Organization implements ISFDto {

	private String sourcepath;

	private String id;

	private Boolean isDeleted;

	private Date createdDate;

	private String createdById;

	private Date lastModifiedDate;

	private String lastModifiedById;

	private Date systemModstamp;

	private Integer percentCovered;

	private List<String> codeCoverageWarnings;

	private List<Package> packagelist;

	private Integer classcnt;

	private Integer methodcnt;

	public String getSourcepath() {
		return sourcepath;
	}

	public void setSourcepath(String sourcepath) {
		this.sourcepath = sourcepath;
	}

	public List<Package> getPackagelist() {
		return packagelist;
	}

	public Package getPackage(String name) {
		if (packagelist == null) {
			packagelist = new ArrayList<Package>();
		}
		for (Package pobj : packagelist) {
			if (StringUtils.equals("classes", name) || StringUtils.equals("Class", name)) {
				return pobj;
			}
			if (StringUtils.equals("triggers", name) || StringUtils.equals("Trigger", name)) {
				return pobj;
			}
		}
		return null;
	}

	public void setPackagelist(List<Package> packagelist) {
		this.packagelist = packagelist;
	}

	public void addPackagelist(Package packagedto) {
		if (this.packagelist == null) {
			this.packagelist = new ArrayList<Package>();
		}
		this.packagelist.add(packagedto);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public Date getSystemModstamp() {
		return systemModstamp;
	}

	public void setSystemModstamp(Date systemModstamp) {
		this.systemModstamp = systemModstamp;
	}

	public Integer getPercentCovered() {
		return percentCovered;
	}

	public void setPercentCovered(Integer percentCovered) {
		this.percentCovered = percentCovered;
	}

	public Integer getClasscnt() {
		return classcnt;
	}

	public void setClasscnt(Integer classcnt) {
		this.classcnt = classcnt;
	}

	public Integer getMethodcnt() {
		return methodcnt;
	}

	public void setMethodcnt(Integer methodcnt) {
		this.methodcnt = methodcnt;
	}

	public void addMethodcnt(Integer methodcnt) {
		this.methodcnt += methodcnt;
	}

	public List<String> getCodeCoverageWarnings() {
		if (this.codeCoverageWarnings == null) {
			this.codeCoverageWarnings = new ArrayList<String>();
		}
		return codeCoverageWarnings;
	}

	public void addCodeCoverageWarnings(String codeCoverageWarning) {
		if (this.codeCoverageWarnings == null) {
			this.codeCoverageWarnings = new ArrayList<String>();
		}
		this.codeCoverageWarnings.add(codeCoverageWarning);
	}

}
