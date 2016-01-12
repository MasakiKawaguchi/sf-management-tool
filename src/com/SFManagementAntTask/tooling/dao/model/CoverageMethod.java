package com.SFManagementAntTask.tooling.dao.model;

import java.util.List;

public class CoverageMethod implements ISFDto {

	private String id;

	private String name;

	private String paramStr;

	private String returnStr;

	private String apexTestClassId;

	private String apexClassOrTriggerId;

	private Integer numLinesCovered;

	private Integer numLinesUncovered;

	private List<Line> coveredLines;

	private List<Line> uncoveredLines;

	private String methodName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	public String getReturnStr() {
		return returnStr;
	}

	public void setReturnStr(String returnStr) {
		this.returnStr = returnStr;
	}

	public String getApexClassOrTriggerId() {
		return apexClassOrTriggerId;
	}

	public void setApexClassOrTriggerId(String apexClassOrTriggerId) {
		this.apexClassOrTriggerId = apexClassOrTriggerId;
	}

	public Integer getNumLinesCovered() {
		return numLinesCovered;
	}

	public void setNumLinesCovered(Integer numLinesCovered) {
		this.numLinesCovered = numLinesCovered;
	}

	public Integer getNumLinesUncovered() {
		return numLinesUncovered;
	}

	public void setNumLinesUncovered(Integer numLinesUncovered) {
		this.numLinesUncovered = numLinesUncovered;
	}

	public List<Line> getCoveredLines() {
		return coveredLines;
	}

	public void setCoveredLines(List<Line> coveredLines) {
		this.coveredLines = coveredLines;
	}

	public List<Line> getUncoveredLines() {
		return uncoveredLines;
	}

	public void setUncoveredLines(List<Line> uncoveredLines) {
		this.uncoveredLines = uncoveredLines;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getApexTestClassId() {
		return apexTestClassId;
	}

	public void setApexTestClassId(String apexTestClassId) {
		this.apexTestClassId = apexTestClassId;
	}

}
