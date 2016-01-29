package com.SFManagementAntTask.tooling.dao.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.SFManagementAntTask.common.Const;

/**
 * ユニットテストクラス
 * @author mkawaguchi
 */
public class UnitTestClass implements ISFDto {

	public UnitTestClass() {}

	// ################################################
	// ## REST
	// ################################################

	private String name;

	private String namespacePrefix;

	private boolean seeAllData;

	private int errorCnt = 0;

	private int testCnt = 0;

	private int failurCnt = 0;

	private double aroundTime = 0.00;

	private List<UnitTestMethod> methodlist;

	private String systemout = "";

	private String systemerr = "";

	// ################################################
	// ## REST
	// ################################################

	private String id;

	private boolean isTest;

	private String status;

	private Date operationDate;

	private Date startDate;

	private Date endDate;

	private String apexClassId;

	private String parentJobId;

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

	public boolean getSeeAllData() {
		return seeAllData;
	}

	public String getSeeAllDataStr() {
		if (seeAllData) {
			return "○";
		}
		return "×";
	}

	public void setSeeAllData(boolean seeAllData) {
		this.seeAllData = seeAllData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

	public int getErrorCnt() {
		return errorCnt;
	}

	public void incrementErrorCnt() {
		++this.errorCnt;
	}

	public void addErrorCntforOutCome(String outcome) {
		if (StringUtils.equals("Fail", outcome)) {
			++this.errorCnt;
		}
	}

	public void setErrorCnt(int errorCnt) {
		this.errorCnt = errorCnt;
	}

	public int getTestCnt() {
		return testCnt;
	}

	public void setTestCnt(int testCnt) {
		this.testCnt = testCnt;
	}

	public void incrementTestCnt() {
		++this.testCnt;
	}

	public int getFailurCnt() {

		return failurCnt;
	}

	public void setFailurCnt(int failurCnt) {
		this.failurCnt = failurCnt;
	}

	public void incrementFailurCnt() {
		++this.failurCnt;
	}

	public Date getOperationDate() {
		if (operationDate == null) {
			operationDate = new Date();
		}
		return operationDate;
	}

	public String getOperationDateStr() {
		if (operationDate == null) {
			operationDate = new Date();
		}
		return new SimpleDateFormat(Const.FT_YYYY_MM_DDTHHMMSS).format(operationDate);
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public double getAroundTime() {
		return aroundTime;
	}

	public String getAroundTimeStr() {
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, (int) aroundTime);
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss SSS");
		return sdf.format(calender.getTime());
	}

	public void setAroundTime(double aroundTime) {
		this.aroundTime = aroundTime;
	}

	public void addAroundTime(double aroundTime) {
		this.aroundTime += aroundTime;
	}

	public List<UnitTestMethod> getUTMethodlist() {
		if (methodlist == null) {
			return new ArrayList<UnitTestMethod>();
		}
		return methodlist;
	}

	public void setMethodlist(List<UnitTestMethod> methodlist) {
		this.methodlist = methodlist;
	}

	public void addMethodlist(UnitTestMethod method) {
		if (this.methodlist == null) {
			this.methodlist = new ArrayList<UnitTestMethod>();
		}
		addAroundTime(method.getAroundTime());
		setSeeAllData(method.getSeeAllData());
		setName(method.getName());
		incrementTestCnt();
		if (!method.getOutcome()) {
			incrementErrorCnt();
		}
		this.methodlist.add(method);
	}

	public String getApexClassId() {
		return apexClassId;
	}

	public void setApexClassId(String apexClassId) {
		this.apexClassId = apexClassId;
	}

	public String getExtendedStatus() {
		return "(" + (this.testCnt - this.errorCnt) + "/" + this.testCnt + ")";
	}

	public void setExtendedStatus(String extendedStatus) {
		extendedStatus = StringUtils.replaceOnce(extendedStatus, "(", "");
		extendedStatus = StringUtils.replaceOnce(extendedStatus, ")", "");
		String[] cntstr = extendedStatus.split("/");
		int successCnt = Integer.parseInt((String) cntstr[0]);
		int testCnt = Integer.parseInt((String) cntstr[1]);
		int errorCnt = testCnt - successCnt;
		this.errorCnt = errorCnt;
		this.testCnt = testCnt;
	}

	public String getParentJobId() {
		return parentJobId;
	}

	public void setParentJobId(String parentJobId) {
		this.parentJobId = parentJobId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setBeforeStartDate(Date comp) {
		if (startDate == null || startDate.before(comp)) {
			startDate = comp;
		}
		return;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setAfterEndDate(Date comp) {
		if (endDate == null || endDate.after(comp)) {
			endDate = comp;
		}
		return;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSystemout() {
		return systemout;
	}

	public void setSystemout(String systemout) {
		this.systemout += systemout;
	}

	public String getSystemerr() {
		return systemerr;
	}

	public void setSystemerr(String systemerr) {
		this.systemerr += systemerr;
	}
}
