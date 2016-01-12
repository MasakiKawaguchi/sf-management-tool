package com.SFManagementAntTask.tooling.dao.model;

import java.util.ArrayList;
import java.util.List;

public class Package implements ISFDto {

	private String name;

	private int errorCnt = 0;

	private int testCnt = 0;

	private int failurCnt = 0;

	private List<CoverageClass> cvgClasslist = new ArrayList<CoverageClass>();

	private List<UnitTestClass> utClasslist = new ArrayList<UnitTestClass>();

	public String getName() {
		return name;
	}

	public void setName(String nam) {
		this.name = nam;
	}

	public List<CoverageClass> getCVGClasslist() {
		return cvgClasslist;
	}

	public void setCVGClasslist(List<CoverageClass> classlist) {
		this.cvgClasslist = classlist;
	}

	public void addCVGClasslist(CoverageClass classdto) {
		if (this.cvgClasslist == null) {
			this.cvgClasslist = new ArrayList<CoverageClass>();
		}
		this.cvgClasslist.add(classdto);
	}

	public List<UnitTestClass> getUTClasslist() {
		return utClasslist;
	}

	public void setUTClasslist(List<UnitTestClass> utclasslist) {
		this.utClasslist = utclasslist;
	}

	public void addUTClasslist(UnitTestClass classdto) {
		if (this.utClasslist == null) {
			this.utClasslist = new ArrayList<UnitTestClass>();
		}
		this.utClasslist.add(classdto);
	}

	public int getErrorCnt() {
		if (this.utClasslist == null) {
			this.utClasslist = new ArrayList<UnitTestClass>();
		}
		for (UnitTestClass cobj : this.utClasslist) {
			errorCnt += cobj.getErrorCnt();
		}
		return errorCnt;
	}

	public int getTestCnt() {
		if (this.utClasslist == null) {
			this.utClasslist = new ArrayList<UnitTestClass>();
		}
		for (UnitTestClass cobj : this.utClasslist) {
			testCnt += cobj.getTestCnt();
		}
		return testCnt;
	}

	public int getFailurCnt() {
		if (this.utClasslist == null) {
			this.utClasslist = new ArrayList<UnitTestClass>();
		}
		for (UnitTestClass cobj : this.utClasslist) {
			failurCnt += cobj.getFailurCnt();
		}
		return failurCnt;
	}
}
