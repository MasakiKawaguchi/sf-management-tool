package com.SFManagementAntTask;

import com.SFManagementAntTask.ant.task.ExportDocumentTask;
import com.SFManagementAntTask.ant.task.SFAntTaskAbs;

public class Main {

	public static void main(String[] args) {
		//ExportCoverageTask action = new ExportCoverageTask();
		SFAntTaskAbs action = new ExportDocumentTask();
		executeforAries(action);
		//action.setSrcRoot("meta");
		//action.setExportExcel(false);
		action.execute();
	}

	private static SFAntTaskAbs executeforAries(SFAntTaskAbs action) {
		action.setServerURL("https://login.salesforce.com");
		action.setUsername("mkawaguchi@terrasky.co.jp.aries01");
		action.setPassword("kawasakigpz900ryKzxYdUIxCqewxjLvTxn7b0NR");
		return action;
	}

	private static SFAntTaskAbs executeforSisolar(SFAntTaskAbs action) {
		action.setServerURL("https://test.salesforce.com");
		action.setUsername("matsumoto@sisolar.jp.sandbox");
		action.setPassword("terrasky201512");
		return action;
	}

	private static SFAntTaskAbs executeforTerraSkySand(SFAntTaskAbs action) {
		action.setServerURL("https://test.salesforce.com");
		action.setUsername("sales.terrasky@gmail.com.sandbox");
		action.setPassword("Nihonmaru1101");
		return action;
	}

	private static SFAntTaskAbs executefor13(SFAntTaskAbs action) {
		action.setServerURL("https://login.salesforce.com");
		action.setUsername("mkawaguchi@terrasky.co13.jp");
		action.setPassword("kawasakigpz900r");
		return action;
	}

	private static SFAntTaskAbs executefor6(SFAntTaskAbs action) {
		action.setServerURL("https://login.salesforce.com");
		action.setUsername("mkawaguchi@terrasky.co6.jp");
		action.setPassword("kawasakigpz900r");
		return action;
	}
}
