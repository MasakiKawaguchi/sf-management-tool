package com.SFManagementAntTask.ant.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.service.CoverageService;
import com.salesforce.ant.SFDCAntTask;

/**
 * ユニットテスト、カバレッジ集計プラグイン
 * @author mkawaguchi
 */
public class ExportCoverageTask extends SFAntTaskAbs {

	private final static Logger log = LoggerFactory.getLogger(SFDCAntTask.class);

	private List<String> testclasslist = new ArrayList<String>();

	private String testclasses;

	private boolean exportExcel = true;

	/**
	 * Ant executer
	 * 
	 * @throws BuildException
	 */
	public void execute() throws BuildException {
		log.info("execute processing... ");
		ConnectionUtil.login(this);

		CoverageService service = new CoverageService();
		Organization oobj = null;
		if (!testclasslist.isEmpty()) {
			service.runTestbyClassname(testclasslist);
		}
		if (testclasslist.isEmpty()) {
			oobj = service.runTestbyLocal();
		}
		if (isExportExcel()) {
			service.exportCoverageReport(oobj);
		}

		log.info("execute processing end...");
	}

	public String getTestclasses() {
		return testclasses;
	}

	public void setTestclasses(String testclasses) {
		String[] ttestclasses = testclasses.split(",");
		for (String classname : ttestclasses) {
			this.testclasslist.add(classname);
		}
	}

	public boolean isExportExcel() {
		return exportExcel;
	}

	public void setExportExcel(boolean exportExcel) {
		this.exportExcel = exportExcel;
	}

}
