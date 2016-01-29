package com.SFManagementAntTask.tooling.dao.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class UnitTestMethod implements ISFDto {

	// ##############################
	// ## SOAP
	// ##############################
	private String id;

	private String name;

	private double aroundTime;

	private String methodName;

	private String message;

	private boolean outcome;

	private String stackTrace;

	private boolean seeAllData;

	// ##############################
	// ## REST
	// ##############################

	private String apexClassId;

	private String asyncApexJobId;

	private String queueItemId;

	private String apexLogId;

	private ApexLog apexLog;

	private Date systemModstamp;

	private Date testTimestamp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		if (stackTrace == null) {
			return StringUtils.EMPTY;
		}
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Boolean getSeeAllData() {
		return seeAllData;
	}

	public void setSeeAllData(Boolean seeAllData) {
		this.seeAllData = seeAllData;
	}

	public String getApexClassId() {
		return apexClassId;
	}

	public void setApexClassId(String apexClassId) {
		this.apexClassId = apexClassId;
	}

	public String getAsyncApexJobId() {
		return asyncApexJobId;
	}

	public void setAsyncApexJobId(String asyncApexJobId) {
		this.asyncApexJobId = asyncApexJobId;
	}

	public boolean getOutcome() {
		return outcome;
	}

	public String getOutcomeStr() {
		if (outcome) {
			return "○";
		}
		return "×";
	}

	public void setOutcome(boolean outcome) {
		this.outcome = outcome;
	}

	public void setOutcome(String outcome) {
		if (StringUtils.equals(outcome, "SUCCESS")) {
			this.outcome = true;
			return;
		}
		this.outcome = false;
	}

	public String getQueueItemId() {
		return queueItemId;
	}

	public void setQueueItemId(String queueItemId) {
		this.queueItemId = queueItemId;
	}

	public String getApexLogId() {
		return apexLogId;
	}

	public void setApexLogId(String apexLogId) {
		this.apexLogId = apexLogId;
	}

	public Date getSystemModstamp() {
		return systemModstamp;
	}

	public void setSystemModstamp(Date systemModstamp) {
		this.systemModstamp = systemModstamp;
	}

	public Date getTestTimestamp() {
		return testTimestamp;
	}

	public void setTestTimestamp(Date testTimestamp) {
		this.testTimestamp = testTimestamp;
	}

	public ApexLog getApexLog() {
		return apexLog;
	}

	public void setApexLog(ApexLog apexLog) {
		this.apexLog = apexLog;
	}

}
