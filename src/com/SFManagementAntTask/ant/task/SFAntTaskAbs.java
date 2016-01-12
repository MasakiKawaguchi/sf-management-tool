package com.SFManagementAntTask.ant.task;

import com.SFManagementAntTask.common.CommonUtil;
import com.salesforce.ant.SFDCAntTask;

public class SFAntTaskAbs extends SFDCAntTask {

	private String reportRoot = CommonUtil.getReportPath();

	private String srcRoot = CommonUtil.getSrcPath();

	private Integer maxpoll = 150;

	private Integer pollWaitMillis = 10 * 1000; // ミリSec

	public String getReportRoot() {
		return reportRoot;
	}

	public void setReportRoot(String reportRoot) {
		this.reportRoot = reportRoot;
	}

	public String getSrcRoot() {
		return srcRoot;
	}

	public void setSrcRoot(String srcRoot) {
		this.srcRoot = srcRoot;
	}

	public Integer getMaxpoll() {
		return maxpoll;
	}

	public void setMaxpoll(Integer maxpoll) {
		this.maxpoll = maxpoll;
	}

	public Integer getPollWaitMillis() {
		return pollWaitMillis;
	}

	public void setPollWaitMillis(Integer pollWaitMillis) {
		this.pollWaitMillis = pollWaitMillis;
	}
}
