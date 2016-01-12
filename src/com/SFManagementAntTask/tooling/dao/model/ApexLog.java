package com.SFManagementAntTask.tooling.dao.model;

import java.util.Date;

public class ApexLog implements ISFDto {

	private String id;

	private String application;

	private Long durationMilliseconds;

	private String location;

	private Integer logLength;

	private String logUserId;

	private String operation;

	private String request;

	private Date startTime;

	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Long getDurationMilliseconds() {
		return durationMilliseconds;
	}

	public void setDurationMilliseconds(Long durationMilliseconds) {
		this.durationMilliseconds = durationMilliseconds;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getLogLength() {
		return logLength;
	}

	public void setLogLength(Integer logLength) {
		this.logLength = logLength;
	}

	public String getLogUserId() {
		return logUserId;
	}

	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ApexLog [id=" + id + ", application=" + application + ", durationMilliseconds=" + durationMilliseconds + ", location=" + location + ", logLength=" + logLength + ", logUserId="
		        + logUserId + ", operation=" + operation + ", request=" + request + ", startTime=" + startTime + ", status=" + status + "]";
	}

}
