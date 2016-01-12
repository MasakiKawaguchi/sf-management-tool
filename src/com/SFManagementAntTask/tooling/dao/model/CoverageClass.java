package com.SFManagementAntTask.tooling.dao.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CoverageClass implements ISFDto {

	// #################################
	// ## SOAP
	// #################################
	private String id;

	private String name;

	private String namespacePrefix;

	private int numLocations = 0;

	private int numLinesCovered = 0;

	private int calcLocations = 0;

	private int calcLinesCovered = 0;

	private List<Line> coveredLines = new ArrayList<Line>();

	// #################################
	// ## REST
	// #################################	

	private List<Line> uncoveredLines = new ArrayList<Line>();

	private String status;

	private boolean isTest;

	private List<CoverageMethod> methodlist;

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

	public String getNamespacePrefix() {
		return namespacePrefix;
	}

	public void setNamespacePrefix(String namespacePrefix) {
		this.namespacePrefix = namespacePrefix;
	}

	public int getNumLinesCovered() {
		return numLinesCovered;
	}

	public void setNumLinesCovered(int numLinesCovered) {
		this.numLinesCovered = numLinesCovered;
	}

	public int getNumLocations() {
		return numLocations;
	}

	public void setNumLocations(int numLocations) {
		this.numLocations = numLocations;
	}

	public String getCoverageRate() {
		if (numLinesCovered == 0 || numLocations == 0) {
			return "0%";
		}
		BigDecimal lnum = new BigDecimal(numLocations);
		BigDecimal cnum = new BigDecimal(numLinesCovered);
		NumberFormat nfPer = NumberFormat.getPercentInstance();
		return nfPer.format(cnum.divide(lnum, 2, BigDecimal.ROUND_HALF_UP));
	}

	public List<Line> getCoveredLines() {
		return coveredLines;
	}

	public void setCoveredLines(List<Line> coveredLines) {
		this.coveredLines = coveredLines;
	}

	public void addCoveredLines(Line coveredLine) {
		if (this.coveredLines == null) {
			this.coveredLines = new ArrayList<Line>();
		}
		this.coveredLines.add(coveredLine);
	}

	public List<Line> getUncoveredLines() {
		return uncoveredLines;
	}

	public void setUncoveredLines(List<Line> uncoveredLines) {
		this.uncoveredLines = uncoveredLines;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
	}

	public List<CoverageMethod> getMethodlist() {
		if (methodlist == null) {
			return new ArrayList<CoverageMethod>();
		}
		return methodlist;
	}

	public void setMethodlist(List<CoverageMethod> methodlist) {
		this.methodlist = methodlist;
	}

	public void addMethodlist(CoverageMethod method) {
		if (this.methodlist == null) {
			this.methodlist = new ArrayList<CoverageMethod>();
		}
		this.methodlist.add(method);
	}

	public int getCalcLocations() {

		return coveredLines.size();
	}

	public int getCalcLinesCovered() {
		for (Line line : coveredLines) {
			if (line.getHits() > 0) {
				++calcLocations;
			}
		}
		return calcLinesCovered;
	}
}
