package com.SFManagementAntTask.xml;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.FileWriter;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.CoverageMethod;
import com.SFManagementAntTask.tooling.dao.model.Line;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.dao.model.Package;

/**
 * カバレッジXML出力クラス
 * @author mkawaguchi
 */
public class CoverageXmlWriter {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(CoverageXmlWriter.class);

	/** 出力クラス */
	FileWriter writer;

	/**
	 * デフォルトコンストラクタ
	 */
	public CoverageXmlWriter() {
		this.writer = new FileWriter(CommonUtil.getXmlPath() + CommonUtil.setStr() + "coverage.xml");
		log.debug("create CoverageXml start...");
	}

	/**
	 * 出力処理
	 * @param odto 組織情報
	 */
	public void write(Organization odto) {
		writePackage(odto);
		writer.finish();
		log.debug("create CoverageXml end...");
	}

	/**
	 * ヘッダー情報出力処理
	 * @param odto
	 */
	private void writePackage(Organization odto) {
		writer.writeXml("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", 0);
		writer.writeXml("<!DOCTYPE coverage SYSTEM \"http://cobertura.sourceforge.net/xml/coverage-04.dtd\">", 0);
		writer.writeXml(
		        "<coverage line-rate=\"0.0\" branch-rate=\"0.0\" lines-covered=\"0\" lines-valid=\"0\" branches-covered=\"0\" branches-valid=\"0\" complexity=\"0.0\" version=\"0.0.0\" timestamp=\"0\">",
		        0);
		writer.writeXml("<sources>", 4);
		writer.writeXml("<source>" + odto.getSourcepath() + "</source>", 8);
		writer.writeXml("</sources>", 4);
		writer.writeXml("<packages>", 4);
		for (Package packagedto : odto.getPackagelist()) {
			writer.writeXml("<package name=\"" + packagedto.getName() + "\" line-rate=\"0.0\" branch-rate=\"0.0\" complexity=\"0.0\">", 8);
			writeClassPart(packagedto);
			writer.writeXml("</package>", 8);
		}
		writer.writeXml("</packages>", 4);
		writer.writeXml("</coverage>", 0);
	}

	/**
	 * カバレッジクラス情報出力処理
	 * @param pdto パッケージ情報
	 */
	private void writeClassPart(Package pdto) {

		writer.writeXml("<classes>", 12);
		for (CoverageClass classdto : pdto.getCVGClasslist()) {
			writer.writeXml(
			        "<class name=\"" + classdto.getName() + "\" filename=\"" + pdto.getName() + "/" + classdto.getName() + getExtension(pdto)
			                + "\" line-rate=\"0.0\" branch-rate=\"0.0\" complexity=\"0.0\">",
			        16);
			writeMethod(classdto.getMethodlist());
			writeLine(classdto.getCoveredLines(), 20);
			writer.writeXml("</class>", 16);
		}
		writer.writeXml("</classes>", 12);
	}

	/**
	 * 拡張子取得メソッド
	 * @param pdto パッケージ情報
	 * @return 拡張子
	 */
	private String getExtension(Package pdto) {

		if (StringUtils.equals("triggers", pdto.getName())) {
			return ".trigger";
		}
		return ".cls";
	}

	/**
	 * カバレッジメソッド情報出力処理
	 * @param methodlist メソッド情報リスト
	 */
	private void writeMethod(List<CoverageMethod> methodlist) {

		writer.writeXml("<methods>", 20);
		for (CoverageMethod methoddto : methodlist) {
			writer.writeXml("<method name=\"" + methoddto.getName() + "\" signature=\"(" + methoddto.getParamStr() + ")" + methoddto.getReturnStr() + "\" line-rate=\"0.0\" branch-rate=\"0.0\">",
			        24);
			writeLine(methoddto.getCoveredLines(), 28);
			writer.writeXml("</method>", 24);
		}
		writer.writeXml("</methods>", 20);
	}

	/**
	 * 行情報出力処理
	 * @param linelist 行情報
	 * @param i インデント情報
	 */
	private void writeLine(List<Line> linelist, Integer i) {

		writer.writeXml("<lines>", i);
		for (Line linedto : linelist) {
			String element = "<line number=\"" + linedto.getNum() + "\" hits=\"" + linedto.getHits() + "\" ";
			if (linedto.isBranch()) {
				element += " branch=\"" + linedto.isBranch() + "\" condition-coverage=\"0% (2/2)\">";
				writer.writeXml(element, (i + 4));
				writer.writeXml("<conditions>", (i + 8));
				writer.writeXml("<condition number=\"0\" type=\"jump\" coverage=\"0%\"/>", (i + 12));
				writer.writeXml("</conditions>", (i + 8));
				writer.writeXml("</line>", (i + 4));
			}
			if (!linedto.isBranch()) {
				element += " branch=\"" + linedto.isBranch() + "\"/>";
				writer.writeXml(element, (i + 4));
			}
		}
		writer.writeXml("</lines>", i);
	}
}
