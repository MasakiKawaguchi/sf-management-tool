package com.SFManagementAntTask.ant.task;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.tooling.service.DocumentService;
import com.salesforce.ant.SFDCAntTask;

public class ExportDocumentTask extends SFAntTaskAbs {

	private final static Logger log = LoggerFactory.getLogger(SFDCAntTask.class);

	/**
	 * Ant executer
	 * 
	 * @throws BuildException
	 */
	public void execute() throws BuildException {
		log.info("execute processing... ");
		ConnectionUtil.login(this);
		DocumentService service = new DocumentService();
		service.createTableDifinitionBook();
		log.info("execute processing end...");
	}

}
