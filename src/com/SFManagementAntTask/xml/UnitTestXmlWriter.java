package com.SFManagementAntTask.xml;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.FileWriter;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.dao.model.Package;
import com.SFManagementAntTask.tooling.dao.model.UnitTestClass;
import com.SFManagementAntTask.tooling.dao.model.UnitTestMethod;

/**
 * ユニットテストXML出力クラス
 * @author mkawaguchi
 */
public class UnitTestXmlWriter {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(UnitTestXmlWriter.class);

	/** 出力クラス */
	FileWriter writer;

	/**
	 * デフォルトコンストラクタ
	 */
	public UnitTestXmlWriter() {
		this.writer = new FileWriter(CommonUtil.getXmlPath() + CommonUtil.setStr() + "unittest.xml");
		log.debug("create UnitTestXml start...");
	}

	/**
	 * 出力処理
	 * @param odto 組織情報
	 */
	public void write(Organization odto) {
		writePackage(odto);
		writer.finish();
		log.debug("create UnitTestXml end...");
	}

	/**
	 * ヘッダー情報出力処理
	 * @param odto 組織情報
	 */
	private void writePackage(Organization odto) {
		writer.writeXml("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", 0);
		writer.writeXml("<testsuites>", 0);
		for (Package dto : odto.getPackagelist()) {
			writeClass(dto);
		}
		writer.writeXml("</testsuites>", 0);
	}

	/**
	 * テストクラス情報出力処理
	 * @param pdto パッケージ情報
	 */
	private void writeClass(Package pdto) {
		for (UnitTestClass dto : pdto.getUTClasslist()) {
			writer.writeXml("<testsuite errors=\"" + dto.getErrorCnt()
			        + "\" skipped=\"0\" tests=\"" + dto.getTestCnt()
			        + "\" failures=\"" + dto.getFailurCnt()
			        + "\" hostname=\"mkawaguchi-PC\" id=\"1\" name=\"" + dto.getName()
			        + "\" package=\"" + pdto.getName()
			        + "\" time=\"" + dto.getAroundTime()
			        + "\" timestamp=\"" + dto.getOperationDate() + "\">",
			        4);
			writer.writeXml("<properties>", 8);
			writer.writeXml("<property name=\"sun.jnu.encoding\" value=\"UTF-8\" />", 12);
			writer.writeXml("<property name=\"file.encoding.pkg\" value=\"sun.io\" />", 12);
			writer.writeXml("<property name=\"path.separator\" value=\";\" />", 12);
			writer.writeXml("</properties>", 8);
			for (UnitTestMethod mdto : dto.getUTMethodlist()) {
				writer.writeXml("<testcase classname=\"" + mdto.getName() + "\" name=\"" + mdto.getMethodName()
				        + "\" time=\"" + mdto.getAroundTime() + "\">", 8);
				if (StringUtils.isNoneBlank(mdto.getMessage())) {
					writer.writeXml("<error message=\"" + mdto.getMessage() + "\" type=\"" + ""
					        + "\">" + mdto.getStackTrace() + "</error>", 12);
				}
				writer.writeXml("</testcase>", 8);
			}
			writer.writeXml("<system-out><![CDATA[" + dto.getSystemout() + "]]></system-out>", 8);
			writer.writeXml("<system-err><![CDATA[" + dto.getSystemerr() + "]]></system-err>", 8);
			writer.writeXml("</testsuite>", 4);
		}
	}
}
