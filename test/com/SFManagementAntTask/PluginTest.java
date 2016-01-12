package com.SFManagementAntTask;

import org.junit.Before;
import org.junit.Test;

import com.SFManagementAntTask.ant.task.ExportCoverageTask;

public class PluginTest {

	@Before
	public void doBefore() {
		System.out.println("doBeforeしています。");
	}

	@Test
	public void testOne() {
		ExportCoverageTask action = new ExportCoverageTask();
		action.setServerURL("https://login.salesforce.com");
		action.setUsername("mkawaguchi@terrasky.co13.jp");
		action.setPassword("kawasakigpz900r");
		action.setTestclasses("ForgotPasswordControllerTest");
		action.execute();
	}
}
