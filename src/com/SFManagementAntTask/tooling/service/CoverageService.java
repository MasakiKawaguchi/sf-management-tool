package com.SFManagementAntTask.tooling.service;

import java.util.List;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.common.FileReader;
import com.SFManagementAntTask.common.PMDXmlPaser;
import com.SFManagementAntTask.excel.CoverageExcelWriter;
import com.SFManagementAntTask.excel.UnitTestExcelWriter;
import com.SFManagementAntTask.tooling.dao.ApexClassDao;
import com.SFManagementAntTask.tooling.dao.ApexTestDao;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.ISFDto;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.logic.AcessTestResultThread;
import com.SFManagementAntTask.xml.CoverageXmlWriter;
import com.SFManagementAntTask.xml.UnitTestXmlWriter;

/**
 * カバレッジ関連サービスクラス
 * @author mkawaguchi
 */
public class CoverageService {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(CoverageService.class);

	/**
	 * テストクラス指定テスト処理(REST)
	 * @param classlist テストクラス名リスト
	 */
	public void runTestbyClassname(List<String> classlist) {
		runTestforAsynchronousREST(ApexClassDao.findApexTestClassbyClassName(classlist));
	}

	/**
	 * ローカルスペース内全テスト処理(SOAP)
	 * @return 組織情報リスト
	 */
	public Organization runTestbyLocal() {
		//runTest(ApexClassDao.findApexTestClassbyLocal());
		Organization oobj = ApexTestDao.runTestsbyLocalSoap();
		UnitTestXmlWriter uwrite = new UnitTestXmlWriter();
		uwrite.write(oobj);
		CoverageXmlWriter cwrite = new CoverageXmlWriter();
		cwrite.write(oobj);
		return oobj;
	}

	/**
	 * カバレッジレポート作成処理
	 * @param objlist 組織情報
	 */
	public void exportCoverageReport(Organization objlist) {
		List<CoverageClass> apexdetaillist = ApexClassDao.findApexDetail();
		// ソースエクセルファイル作成
		CoverageExcelWriter writer = new CoverageExcelWriter();
		log.debug("[exportCoverageReport] SRC_ROOT is.. " + Const.SRC_ROOT);
		FileReader.writeClasses(Const.SRC_ROOT, "classes", writer);
		FileReader.writeClasses(Const.SRC_ROOT, "triggers", writer);
		writer.writeCoverage(objlist);
		writer.writePMD(new PMDXmlPaser().execute());
		writer.writeClassInfo(apexdetaillist);
		writer.finish();
	}

	/**
	 * PMDレポート作成処理
	 * @param objlist 組織情報
	 */
	public void exportUnitTestReport(Organization objlist) {
		// ソースエクセルファイル作成
		UnitTestExcelWriter writer = new UnitTestExcelWriter();
		log.debug("[exportCoverageReport] SRC_ROOT is.. " + Const.SRC_ROOT);
		writer.write(objlist);
		writer.finish();
	}

	/**
	 * テスト実行処理
	 * @param testlist テストクラスIDリスト
	 */
	private void runTestforAsynchronousREST(List<ISFDto> testlist) {
		String asyncid = ApexTestDao.runTestsAsynchronousREST(testlist);
		AcessTestResultThread acess = new AcessTestResultThread(asyncid, testlist.size());
		acess.start();
		try {
			acess.join();
		} catch (InterruptedException e) {
			log.error("[runTestforAsynchronousREST] ", e);
			throw new BuildException(e);
		}
	}
}
