package com.SFManagementAntTask.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.ExcelWriter;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.Line;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.dao.model.Package;
import com.SFManagementAntTask.tooling.dao.model.UnitTestClass;
import com.SFManagementAntTask.tooling.dao.model.UnitTestMethod;

/**
 * カバレッジExcel出力クラス
 * @author mkawaguchi
 */
public class CoverageExcelWriter extends AbsExcelWriter {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(CoverageExcelWriter.class);

	private HSSFCellStyle sorce_left_style;
	private HSSFCellStyle sorce_right_style;
	private HSSFCellStyle covered_style;
	private HSSFCellStyle locate_style;

	private Map<String, String> indexmap = new HashMap<String, String>();

	/**
	 * デフォルトコンストラクター
	 */
	public CoverageExcelWriter() {
		super();
		log.debug("[CoverageExcelWriter] start...");
		writer = new ExcelWriter(CommonUtil.getReportPath() + "/excel/" + "ソース分析報告書", true);
		initilize();

		// ソース記載（左枠）
		sorce_left_style = writer.wb.createCellStyle();
		sorce_left_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// ソース記載（右枠）
		sorce_right_style = writer.wb.createCellStyle();
		sorce_right_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 実行行
		covered_style = writer.wb.createCellStyle();
		covered_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		covered_style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		// 未実行行
		locate_style = writer.wb.createCellStyle();
		locate_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		locate_style.setFillForegroundColor(IndexedColors.RED.getIndex());

		createAgenda();
	}

	/**
	 * シート作成処理
	 * @param sheetname シート名
	 * @param classname 機能名
	 */
	public void createSheet(String sheetname, String classname) {
		log.trace("[createSheet] " + sheetname + " : " + classname);
		HSSFSheet asheet = writer.wb.getSheet("目次");
		writeListforAgenda(asheet, sheetname, classname);
		indexmap.put(classname, sheetname);
		try {
			sheet = writer.wb.createSheet(sheetname);
		} catch (IllegalArgumentException e) {
			log.error("[IllegalArgumentException]..." + sheetname + " : " + classname);
			throw e;
		}
		// サイズ設定
		for (int i = 0; i < 64; i++) {
			sheet.setColumnWidth(i, 580);
		}
		headertile1_2(sheet);
		headertile3_4(sheet, classname);
		sorceheader();
		linecnt = 8;
	}

	/**
	 * ソース出力処理
	 * @param code ソース行情報
	 */
	public void writeSource(String code) {
		HSSFRow row = sheet.createRow(++linecnt);
		HSSFCell cell = row.createCell(1);
		cell.setCellStyle(sorce_left_style);
		cell = row.createCell(2);
		cell.setCellValue(code);
		cell = row.createCell(62);
		cell.setCellStyle(sorce_right_style);
		sorcefooter();
	}

	/**
	 * 
	 * @param obj
	 */
	public void writeUnitTest(Organization obj) {

		linecnt = 8;
		for (Package pobj : obj.getPackagelist()) {
			for (UnitTestClass cobj : pobj.getUTClasslist()) {
				cobj.getSystemout();
				for (UnitTestMethod mobj : cobj.getUTMethodlist()) {
					mobj.getId();
					mobj.getMethodName();
					mobj.getName();
					mobj.getAroundTime();
					mobj.getMessage();
					mobj.getStackTrace();
					mobj.getSeeAllData();
				}
			}
		}
	}

	/**
	 * 
	 * @param oobj
	 */
	public void writeCoverage(Organization oobj) {
		log.debug("[writeCoverage] start...");
		linecnt = 8;
		HSSFSheet asheet = writer.wb.getSheet("目次");
		writeCodeCoverageWarningseforAgenda(asheet, oobj);
		for (Package pobj : oobj.getPackagelist()) {
			for (CoverageClass cobj : pobj.getCVGClasslist()) {
				String index = indexmap.get(cobj.getName());
				this.sheet = writer.wb.getSheet(index);
				HSSFRow row = this.sheet.getRow(2);
				HSSFCell cell = row.getCell(30);
				log.trace("[writeCoverage] " + cobj.getName());
				if (StringUtils.equals(cell.getStringCellValue(), cobj.getName())) {
					writeCoverageInfoforAgenda(asheet, cobj);
					writeContentsHeaderforCoverage(cobj);
					for (Line line : cobj.getCoveredLines()) {
						row = this.sheet.getRow(line.getNum() + linecnt);
						for (int j = 2; j < 62; j++) {
							cell = row.getCell(j);
							if (cell == null) {
								cell = row.createCell(j);
							}
							if (line.getHits() == 0) {
								cell.setCellStyle(locate_style);
							}
							if (line.getHits() > 0) {
								cell.setCellStyle(covered_style);
							}
						}
					}
				}
			}
		}
		log.debug("[writeCoverage] end...");
	}

	/** 目次シート：開始行 */
	private int startAgendaCnt = 5;
	private int agendalistStartRow = 0;
	private int agendalistEndCnt = startAgendaCnt;

	private HSSFSheet createAgenda() {

		HSSFSheet sheet = writer.wb.createSheet("目次");
		for (int i = 0; i < 64; i++) {
			sheet.setColumnWidth(i, 580);
		}
		headertile1_2(sheet);
		headertile3_4(sheet, "");
		writeListHeaderforAgenda(sheet);
		return sheet;
	}

	/**
	 * 目次一覧マージ処理
	 * @param sheet 対象シート
	 */
	private void margedRegionforAgenda(HSSFSheet sheet) {
		// NO.
		margedRegion(sheet, ++agendalistEndCnt, agendalistEndCnt, 1, 2);
		// クラス名
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 3, 12);
		// 対象行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 13, 16);
		// 実行行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 17, 20);
		// カバレッジ
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 21, 24);
		// 独自）対象行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 25, 28);
		// 独自）実行行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 29, 32);
		// その他
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, 33, 62);
	}

	/**
	 * 目次一覧ヘッダー
	 * @param sheet 対象シート
	 */
	private void writeListHeaderforAgenda(HSSFSheet sheet) {

		//HSSFSheet sheet = writer.wb.getSheet("目次");
		margedRegionforAgenda(sheet);

		// セルへ出力
		HSSFRow row = sheet.createRow(agendalistEndCnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(title_style);
		}
		cell = row.getCell(1);
		cell.setCellValue("No.");
		cell = row.getCell(3);
		cell.setCellValue("クラス名");
		cell = row.getCell(13);
		cell.setCellValue("対象行");
		cell = row.getCell(17);
		cell.setCellValue("実行行");
		cell = row.getCell(21);
		cell.setCellValue("カバレッジ");
		cell = row.getCell(25);
		cell.setCellValue("対象行");
		cell = row.getCell(29);
		cell.setCellValue("実行行");
	}

	/**
	 * 目次一覧
	 * @param sheet 対象シート
	 * @param j No
	 * @param classname クラス名
	 */
	private void writeListforAgenda(HSSFSheet sheet, String sheetname, String classname) {

		margedRegionforAgenda(sheet);

		// セルへ出力
		HSSFRow row = sheet.createRow(agendalistEndCnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(contents_border_top_left_bottom_right_style);
		}
		cell = row.getCell(1);
		cell.setCellValue(sheetname);
		cell = row.getCell(3);
		cell.setCellValue(classname);
		HSSFCreationHelper createHelper = (HSSFCreationHelper) writer.wb.getCreationHelper();
		HSSFHyperlink link = createHelper.createHyperlink(HSSFHyperlink.LINK_DOCUMENT);
		link.setAddress(sheetname + "!A1");
		cell.setHyperlink(link);
	}

	/**
	 * 目次作成処理
	 * @param sheet 対象シート
	 * @param oobj 組織情報
	 */
	private void writeCodeCoverageWarningseforAgenda(HSSFSheet sheet, Organization oobj) {

		agendalistStartRow = startAgendaCnt + oobj.getCodeCoverageWarnings().size() + 1;
		log.debug("[writeCodeCoverageWarningseforAgenda] start... " + oobj.getCodeCoverageWarnings().size() + " : " + startAgendaCnt + " : " + agendalistStartRow + " : " + agendalistEndCnt);
		int acnt = startAgendaCnt;
		sheet.shiftRows(startAgendaCnt, agendalistEndCnt, oobj.getCodeCoverageWarnings().size());
		margedRegion(sheet, startAgendaCnt, startAgendaCnt + oobj.getCodeCoverageWarnings().size() - 1, 1, 7);
		margedRegion(sheet, startAgendaCnt, startAgendaCnt + oobj.getCodeCoverageWarnings().size() - 1, 8, 62);
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = 0; i < oobj.getCodeCoverageWarnings().size(); i++) {
			row = sheet.createRow(acnt++);
			for (int j = 1; j < 63; j++) {
				cell = row.createCell(j);
				if (j < 8) {
					cell.setCellStyle(title_style);
				}
				if (j >= 8) {
					cell.setCellStyle(contents_border_top_left_bottom_right_style);
				}
			}
		}

		acnt = startAgendaCnt;
		row = sheet.getRow(acnt);
		cell = row.getCell(1);
		cell.setCellValue("メッセージ");
		for (String msg : oobj.getCodeCoverageWarnings()) {
			row = sheet.getRow(acnt++);
			cell = row.getCell(8);
			cell.setCellValue(msg);
		}
		log.debug("[writeCodeCoverageWarningseforAgenda] end...");
	}

	/**
	 * 目次作成処理
	 * @param sheet 対象シート
	 * @param cobj カバレッジクラス
	 */
	private void writeCoverageInfoforAgenda(HSSFSheet sheet, CoverageClass cobj) {

		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = agendalistStartRow; i < agendalistEndCnt; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(3);
			if (cell == null) {
				continue;
			}
			if (StringUtils.equals(cobj.getName(), cell.getStringCellValue())) {
				cell = row.getCell(13);
				cell.setCellValue(cobj.getNumLocations());
				cell = row.getCell(17);
				cell.setCellValue(cobj.getNumLinesCovered());
				cell = row.getCell(21);
				cell.setCellValue(cobj.getCoverageRate());
				cell = row.getCell(25);
				cell.setCellValue(cobj.getCalcLocations());
				cell = row.getCell(29);
				cell.setCellValue(cobj.getCalcLinesCovered());
			}
		}
	}

	/**
	 * クラス別カバレッジサマリ情報記載部分
	 * @param cobj カバレッジクラス情報
	 */
	private void writeContentsHeaderforCoverage(CoverageClass cobj) {

		// サマリー
		// 対象行数
		margedRegion(sheet, 5, 5, 1, 3);
		margedRegion(sheet, 5, 5, 4, 5);
		// 実行行数
		margedRegion(sheet, 5, 5, 6, 8);
		margedRegion(sheet, 5, 5, 9, 10);
		// 対象行数
		margedRegion(sheet, 5, 5, 11, 13);
		margedRegion(sheet, 5, 5, 14, 15);
		// 実行行数
		margedRegion(sheet, 5, 5, 16, 18);
		margedRegion(sheet, 5, 5, 19, 20);

		HSSFRow row = sheet.createRow(5);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			if (i <= 3 || i >= 6 && i <= 8
			        || i >= 11 && i <= 13 || i >= 16 && i <= 18) {
				cell.setCellStyle(title_style);
			}
			if (i >= 4 && i <= 5 || i >= 9 && i <= 10
			        || i >= 14 && i <= 15 || i >= 19 && i <= 20) {
				cell.setCellStyle(contents_center_style);
			}
		}
		cell = row.getCell(1);
		cell.setCellValue("対象行数");
		cell = row.getCell(4);
		cell.setCellValue(cobj.getNumLocations());
		cell = row.getCell(6);
		cell.setCellValue("実行行数");
		cell = row.getCell(9);
		cell.setCellValue(cobj.getNumLinesCovered());
		cell = row.getCell(11);
		cell.setCellValue("対象行数");
		cell = row.getCell(14);
		cell.setCellValue(cobj.getCalcLocations());
		cell = row.getCell(16);
		cell.setCellValue("実行行数");
		cell = row.getCell(19);
		cell.setCellValue(cobj.getCalcLinesCovered());
	}

	/**
	 * ソース記載ヘッダー
	 */
	private void sorceheader() {

		CellRangeAddress region = new CellRangeAddress(8, 8, 1, 62);
		HSSFRegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, region, sheet, writer.wb);
		HSSFRegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, region, sheet, writer.wb);
		HSSFRegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, region, sheet, writer.wb);
	}

	/**
	 * ソース記載フッダー
	 */
	private void sorcefooter() {

		CellRangeAddress region = new CellRangeAddress(linecnt + 1, linecnt + 1, 1, 62);
		HSSFRegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, region, sheet, writer.wb);
		HSSFRegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, region, sheet, writer.wb);
		HSSFRegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, region, sheet, writer.wb);
	}

	private void margedRegion(HSSFSheet sheet, int startrow, int endrow, int startcol, int endcol) {
		CellRangeAddress target_region = new CellRangeAddress(startrow, endrow, startcol, endcol);
		sheet.addMergedRegion(target_region);
	}
}
