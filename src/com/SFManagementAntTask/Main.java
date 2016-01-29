package com.SFManagementAntTask;

import com.SFManagementAntTask.ant.task.ExportCoverageTask;

public class Main {

	public static void main(String[] args) {
		ExportCoverageTask action = new ExportCoverageTask();
		//SFAntTaskAbs action = new ExportDocumentTask();
		action.setServerURL("https://login.salesforce.com");
		//action.setServerURL("https://test.salesforce.com");
		//action.setUsername("mkawaguchi@terrasky.co6.jp");
		//action.setUsername("mkawaguchi@terrasky.co.jp.aries01");
		action.setUsername("mkawaguchi@terrasky.co13.jp");
		//action.setUsername("terraskysys1@bx.com.bxdev02");
		action.setPassword("kawasakigpz900r");
		//action.setPassword("kawasakigpz900ryKzxYdUIxCqewxjLvTxn7b0NR");
		//action.setPassword("Terrasky2015");
		action.setSrcRoot("meta");
		//action.setExportExcel(false);
		//action.setTestclasses("ForgotPasswordControllerTest");
		action.execute();
	}
}
