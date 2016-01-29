package com.SFManagementAntTask.excel;

import java.util.HashMap;
import java.util.List;
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
import com.SFManagementAntTask.tooling.dao.model.PMDClass;
import com.SFManagementAntTask.tooling.dao.model.PMDViolation;
import com.SFManagementAntTask.tooling.dao.model.Package;

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
	private HSSFCellStyle wrn_high_style;
	private HSSFCellStyle wrn_mid_style;
	private HSSFCellStyle wrn_low_style;

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
		// 警告Lv1
		wrn_high_style = writer.wb.createCellStyle();
		wrn_high_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		wrn_high_style.setFillForegroundColor(IndexedColors.RED.getIndex());
		// 警告Lv2
		wrn_mid_style = writer.wb.createCellStyle();
		wrn_mid_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		wrn_mid_style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		// 警告Lv3
		wrn_low_style = writer.wb.createCellStyle();
		wrn_low_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		wrn_low_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());

		createAgenda();
	}

	/**
	 * シート作成処理
	 * @param sheetname シート名
	 * @param classname 機能名
	 */
	public void createSheet(String sheetname, String classname) {
		log.debug("[createSheet] " + sheetname + " : " + classname);
		HSSFSheet asheet = writer.wb.getSheet("目次");
		writeListforAgenda(asheet, sheetname, classname);
		indexmap.put(classname, sheetname);
		int i = writer.wb.getSheetIndex("temp");
		sheet = writer.wb.cloneSheet(i);
		//		writer.wb.getSheetIndex(name);
		writer.wb.setSheetName(i, sheetname);
		headertile3_4(sheet, classname);
		writeContentsHeader();
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
	 * カバレッジ情報出力処理
	 * @param oobj 組織情報
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
						for (int j = 2; j < WRNLV1_TITLE_SC_NUM; j++) {
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

	/**
	 * カバレッジ情報出力処理
	 * @param oobj 組織情報
	 */
	public void writePMD(List<PMDClass> pobjlist) {
		log.debug("[writePMD] start...");
		if (pobjlist == null) {
			return;
		}
		linecnt = 8;
		HSSFSheet asheet = writer.wb.getSheet("目次");
		writePMDforAgenda(asheet, pobjlist);
		for (PMDClass cobj : pobjlist) {
			String index = indexmap.get(cobj.getName());
			if (index == null) {
				continue;
			}
			this.sheet = writer.wb.getSheet(index);
			HSSFRow row = this.sheet.getRow(2);
			HSSFCell cell = row.getCell(30);
			log.trace("[writePMD] " + cobj.getName());
			writeContentsHeaderforPMD(cobj);
			for (PMDViolation vobj : cobj.getVaiolationlist()) {
				row = this.sheet.getRow(vobj.getBeginline() + linecnt);
				cell = row.getCell(WRNMSG_TITLE_SC_NUM);
				log.trace("[violation value] " + vobj.getName() + " : " + vobj.getMsg());
				String msg = vobj.getMsg();
				if (cell != null) {
					msg += cell.getStringCellValue();
				}
				if (cell == null) {
					cell = row.createCell(WRNMSG_TITLE_SC_NUM);
				}
				msg = msg.replaceFirst("\n", "").replaceAll("\n\n", "\n");
				cell.setCellValue(msg);
				for (int i = vobj.getBeginline() + linecnt; i <= vobj.getEndline() + linecnt; i++) {
					row = this.sheet.getRow(i);
					if (vobj.getPriority() == 1) {
						cell = row.createCell(WRNLV1_TITLE_SC_NUM);
						cell.setCellValue(cell.getNumericCellValue() + 1);
						cell.setCellStyle(wrn_mid_style);
					}
					if (vobj.getPriority() == 2) {
						cell = row.createCell(WRNLV2_TITLE_SC_NUM);
						cell.setCellValue(cell.getNumericCellValue() + 1);
						cell.setCellStyle(wrn_mid_style);
					}
					if (vobj.getPriority() == 3) {
						cell = row.createCell(WRNLV3_TITLE_SC_NUM);
						cell.setCellValue(cell.getNumericCellValue() + 1);
						cell.setCellStyle(wrn_mid_style);
					}
					if (vobj.getPriority() == 4) {
						cell = row.createCell(WRNLV4_TITLE_SC_NUM);
						cell.setCellValue(cell.getNumericCellValue() + 1);
						cell.setCellStyle(wrn_mid_style);
					}
					if (vobj.getPriority() == 5) {
						cell = row.createCell(WRNLV5_TITLE_SC_NUM);
						cell.setCellValue(cell.getNumericCellValue() + 1);
						cell.setCellStyle(wrn_mid_style);
					}
				}
			}
		}
		log.debug("[writePMD] end...");
	}

	/**
	 * クラス情報出力処理
	 * @param cobjlist クラス情報リスト
	 */
	public void writeClassInfo(List<CoverageClass> cobjlist) {

		HSSFSheet asheet = writer.wb.getSheet("目次");
		writeClassInfoforAgenda(asheet, cobjlist);
	}

	/** 目次シート：開始行 */
	private int AGENDA_S_ROW_NUM = 5;
	private int agendalistStartRow = 0;
	private int agendalistEndCnt = AGENDA_S_ROW_NUM;
	// NO
	private int AD_NO_SC_NUM = 1;
	private int AD_NO_EC_NUM = AD_NO_SC_NUM + 1;
	// クラス名
	private int AD_NAME_SC_NUM = AD_NO_EC_NUM + 1;
	private int AD_NAME_EC_NUM = AD_NAME_SC_NUM + 9; // 12
	// 対象行数
	private int AD_TAISYOUGYOU_SC_NUM = AD_NAME_EC_NUM + 1;
	private int AD_TAISYOUGYOU_EC_NUM = AD_TAISYOUGYOU_SC_NUM + 2; // 15
	// （独自）対象行数
	private int AD_CTAISYOUGYOU_SC_NUM = AD_TAISYOUGYOU_EC_NUM + 1;
	private int AD_CTAISYOUGYOU_EC_NUM = AD_CTAISYOUGYOU_SC_NUM + 2; // 18
	// 実行行数
	private int AD_JIKKOUGYOU_SC_NUM = AD_CTAISYOUGYOU_EC_NUM + 1;
	private int AD_JIKKOUGYOU_EC_NUM = AD_JIKKOUGYOU_SC_NUM + 2; // 21
	// カバレッジ
	private int AD_COVERAGE_SC_NUM = AD_JIKKOUGYOU_EC_NUM + 1;
	private int AD_COVERAGE_EC_NUM = AD_COVERAGE_SC_NUM + 2; // 24
	// 警告Lv1
	private int AD_WRNLV1_SC_NUM = AD_COVERAGE_EC_NUM + 1;
	private int AD_WRNLV1_EC_NUM = AD_WRNLV1_SC_NUM + 2; //27
	// 警告Lv2
	private int AD_WRNLV2_SC_NUM = AD_WRNLV1_EC_NUM + 1;
	private int AD_WRNLV2_EC_NUM = AD_WRNLV2_SC_NUM + 2; // 30
	// 警告Lv3
	private int AD_WRNLV3_SC_NUM = AD_WRNLV2_EC_NUM + 1;
	private int AD_WRNLV3_EC_NUM = AD_WRNLV3_SC_NUM + 2; // 33
	// 警告Lv4
	private int AD_WRNLV4_SC_NUM = AD_WRNLV3_EC_NUM + 1;
	private int AD_WRNLV4_EC_NUM = AD_WRNLV4_SC_NUM + 2; // 36
	// 警告Lv5
	private int AD_WRNLV5_SC_NUM = AD_WRNLV4_EC_NUM + 1;
	private int AD_WRNLV5_EC_NUM = AD_WRNLV5_SC_NUM + 2; // 39
	// APIVersion
	private int AD_APIVRN_SC_NUM = AD_WRNLV5_EC_NUM + 1;
	private int AD_APIVRN_EC_NUM = AD_APIVRN_SC_NUM + 2; // 42
	// 作成日
	private int AD_CREATE_SC_NUM = AD_APIVRN_EC_NUM + 1;
	private int AD_CREATE_EC_NUM = AD_CREATE_SC_NUM + 4; // 46
	// 更新日
	private int AD_UPDATE_SC_NUM = AD_CREATE_EC_NUM + 1;
	private int AD_UPDATE_EC_NUM = AD_UPDATE_SC_NUM + 4; // 50
	// 更新者
	private int AD_UPDATEUSER_SC_NUM = AD_UPDATE_EC_NUM + 1;
	private int AD_UPDATEUSER_EC_NUM = 62; // 62

	/**
	 * 目次シート作成処理
	 * @return 目次シート
	 */
	private HSSFSheet createAgenda() {

		HSSFSheet sheet = writer.wb.createSheet("目次");
		for (int i = 0; i < 64; i++) {
			sheet.setColumnWidth(i, 580);
		}
		headertile1_2(sheet);
		headertile3_4(sheet, "");
		//		writeListHeaderforAgenda(sheet);
		return sheet;
	}

	/**
	 * 目次一覧マージ処理
	 * @param sheet 対象シート
	 */
	private void margedRegionforAgenda(HSSFSheet sheet) {
		// NO.
		margedRegion(sheet, ++agendalistEndCnt, agendalistEndCnt, AD_NO_SC_NUM, AD_NO_EC_NUM);
		// クラス名
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_NAME_SC_NUM, AD_NAME_EC_NUM);
		// 対象行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_TAISYOUGYOU_SC_NUM, AD_TAISYOUGYOU_EC_NUM);
		// 対象行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_CTAISYOUGYOU_SC_NUM, AD_CTAISYOUGYOU_EC_NUM);
		// 実行行
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_JIKKOUGYOU_SC_NUM, AD_JIKKOUGYOU_EC_NUM);
		// カバレッジ
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_COVERAGE_SC_NUM, AD_COVERAGE_EC_NUM);
		// 警告Lv1
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_WRNLV1_SC_NUM, AD_WRNLV1_EC_NUM);
		// 警告Lv2
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_WRNLV2_SC_NUM, AD_WRNLV2_EC_NUM);
		// 警告Lv3
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_WRNLV3_SC_NUM, AD_WRNLV3_EC_NUM);
		// 警告Lv4
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_WRNLV4_SC_NUM, AD_WRNLV4_EC_NUM);
		// 警告Lv5
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_WRNLV5_SC_NUM, AD_WRNLV5_EC_NUM);
		// API VERSION
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_APIVRN_SC_NUM, AD_APIVRN_EC_NUM);
		// 作成日
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_CREATE_SC_NUM, AD_CREATE_EC_NUM);
		// 更新日
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_UPDATE_SC_NUM, AD_UPDATE_EC_NUM);
		// 更新者
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_UPDATEUSER_SC_NUM, AD_UPDATEUSER_EC_NUM);
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
			cell.setCellStyle(contents_center_style);
		}
		cell = row.getCell(AD_NO_SC_NUM);
		cell.setCellValue(sheetname);
		cell = row.getCell(AD_NAME_SC_NUM);
		cell.setCellValue(classname);
		cell.setCellStyle(contents_border_top_left_bottom_right_style);
		HSSFCreationHelper createHelper = (HSSFCreationHelper) writer.wb.getCreationHelper();
		HSSFHyperlink link = createHelper.createHyperlink(HSSFHyperlink.LINK_DOCUMENT);
		link.setAddress(sheetname + "!A1");
		cell.setHyperlink(link);
		// 対象行
		cell = row.getCell(AD_TAISYOUGYOU_SC_NUM);
		cell.setCellValue(0);
		cell.setCellStyle(contents_dotborder_right_style);
		// 対象行
		cell = row.getCell(AD_CTAISYOUGYOU_SC_NUM);
		cell.setCellValue(0);
		cell.setCellStyle(contents_dotborder_left_style);
		// 実行行
		cell = row.getCell(AD_JIKKOUGYOU_SC_NUM);
		cell.setCellValue(0);
		// カバレッジ
		cell = row.getCell(AD_COVERAGE_SC_NUM);
		cell.setCellValue(0);
		// 警告Lv1
		cell = row.getCell(AD_WRNLV1_SC_NUM);
		cell.setCellValue(0);
		// 警告Lv2
		cell = row.getCell(AD_WRNLV2_SC_NUM);
		cell.setCellValue(0);
		// 警告Lv3
		cell = row.getCell(AD_WRNLV3_SC_NUM);
		cell.setCellValue(0);
		// 警告Lv4
		cell = row.getCell(AD_WRNLV4_SC_NUM);
		cell.setCellValue(0);
		// 警告Lv5
		cell = row.getCell(AD_WRNLV5_SC_NUM);
		cell.setCellValue(0);
		// API_Version
		cell = row.getCell(AD_APIVRN_SC_NUM);
		cell.setCellValue(0);
		// 作成日
		cell = row.getCell(AD_CREATE_SC_NUM);
		cell.setCellStyle(contents_center_date_style);
		// 更新日
		cell = row.getCell(AD_UPDATE_SC_NUM);
		cell.setCellStyle(contents_center_date_style);
		// 更新者
		cell = row.getCell(AD_UPDATEUSER_SC_NUM);
		cell.setCellStyle(contents_border_top_left_bottom_right_style);
	}

	/**
	 * 目次作成処理
	 * @param sheet 対象シート
	 * @param oobj 組織情報
	 */
	private void writeCodeCoverageWarningseforAgenda(HSSFSheet sheet, Organization oobj) {

		if (oobj.getCodeCoverageWarnings().isEmpty()) {
			return;
		}
		agendalistStartRow = AGENDA_S_ROW_NUM + oobj.getCodeCoverageWarnings().size() + 1;
		log.debug("[writeCodeCoverageWarningseforAgenda] start... " + oobj.getCodeCoverageWarnings().size() + " : " + AGENDA_S_ROW_NUM + " : " + agendalistStartRow + " : " + agendalistEndCnt);
		int acnt = AGENDA_S_ROW_NUM;
		sheet.shiftRows(AGENDA_S_ROW_NUM, agendalistEndCnt, oobj.getCodeCoverageWarnings().size());
		margedRegion(sheet, AGENDA_S_ROW_NUM, AGENDA_S_ROW_NUM + oobj.getCodeCoverageWarnings().size() - 1, 1, 7);
		margedRegion(sheet, AGENDA_S_ROW_NUM, AGENDA_S_ROW_NUM + oobj.getCodeCoverageWarnings().size() - 1, 8, 62);
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

		acnt = AGENDA_S_ROW_NUM;
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
			if (row == null) {
				row = sheet.createRow(i);
			}
			cell = row.getCell(3);
			if (cell == null) {
				continue;
			}
			if (StringUtils.equals(cobj.getName(), cell.getStringCellValue())) {
				cell = row.getCell(AD_TAISYOUGYOU_SC_NUM);
				cell.setCellValue(cobj.getNumLocations());
				cell = row.getCell(AD_CTAISYOUGYOU_SC_NUM);
				cell.setCellValue(cobj.getCalcLocations());
				cell = row.getCell(AD_JIKKOUGYOU_SC_NUM);
				cell.setCellValue(cobj.getNumLinesCovered());
				cell = row.getCell(AD_COVERAGE_SC_NUM);
				cell.setCellValue(cobj.getCoverageRate());
			}
		}
	}

	/**
	 * 目次PMDサマリ情報出力処理
	 * @param sheet 目次シート
	 * @param pobjlist PMDクラス情報リスト
	 */
	private void writePMDforAgenda(HSSFSheet sheet, List<PMDClass> pobjlist) {

		for (int i = AGENDA_S_ROW_NUM; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			HSSFCell cell = row.getCell(3);
			for (PMDClass cobj : pobjlist) {
				if (cell == null || cell.getCellType() != 1) {
					break;
				}
				if (StringUtils.equals(cobj.getName(), cell.getStringCellValue())) {
					cell = row.getCell(AD_WRNLV1_SC_NUM);
					cell.setCellValue(cobj.getAbsolutelyrequiredcnt());
					cell = row.getCell(AD_WRNLV2_SC_NUM);
					cell.setCellValue(cobj.getHightlyrecommendedcnt());
					cell = row.getCell(AD_WRNLV3_SC_NUM);
					cell.setCellValue(cobj.getRecommendedcnt());
					cell = row.getCell(AD_WRNLV4_SC_NUM);
					cell.setCellValue(cobj.getOptinalcnt());
					cell = row.getCell(AD_WRNLV5_SC_NUM);
					cell.setCellValue(cobj.getHightlyrecommendedcnt());
					break;
				}
			}
		}
	}

	/**
	 * 目次クラス情報出力処理
	 * @param sheet 目次シート
	 * @param pobjlist PMDクラス情報リスト
	 */
	private void writeClassInfoforAgenda(HSSFSheet sheet, List<CoverageClass> cobjlist) {

		for (int i = AGENDA_S_ROW_NUM; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			HSSFCell cell = row.getCell(3);
			for (CoverageClass cobj : cobjlist) {
				if (cell == null || cell.getCellType() != 1) {
					break;
				}
				if (StringUtils.equals(cobj.getName(), cell.getStringCellValue())) {
					cell = row.getCell(AD_APIVRN_SC_NUM);
					cell.setCellValue(cobj.getApiVersion());
					cell = row.getCell(AD_CREATE_SC_NUM);
					if (cobj.getCreatedDate() != null) {
						cell.setCellValue(cobj.getCreatedDate());
					}
					cell = row.getCell(AD_UPDATE_SC_NUM);
					if (cobj.getLastModifiedDate() != null) {
						cell.setCellValue(cobj.getLastModifiedDate());
					}
					cell = row.getCell(AD_UPDATEUSER_SC_NUM);
					cell.setCellValue(cobj.getLastModifiedByName());
					break;
				}
			}
		}
	}

	private int CH_ROW_NUM = 5;
	// 対象行数
	private int CH_TAISYOUGYOU_TITLE_SC_NUM = 1;
	private int CH_TAISYOUGYOU_TITLE_EC_NUM = CH_TAISYOUGYOU_TITLE_SC_NUM + 2; // 3
	private int CH_TAISYOUGYOU_CTNTS_SC_NUM = CH_TAISYOUGYOU_TITLE_EC_NUM + 1; // 5
	private int CH_TAISYOUGYOU_CTNTS_EC_NUM = CH_TAISYOUGYOU_CTNTS_SC_NUM + 2; // 7
	// （独自）対象行数
	//	private int CH_CTAISYOUGYOU_TITLE_SC_NUM = CH_TAISYOUGYOU_CTNTS_EC_NUM + 1;
	//	private int CH_CTAISYOUGYOU_TITLE_EC_NUM = CH_CTAISYOUGYOU_TITLE_SC_NUM + 2;
	private int CH_CTAISYOUGYOU_CTNTS_SC_NUM = CH_TAISYOUGYOU_CTNTS_EC_NUM + 1;
	private int CH_CTAISYOUGYOU_CTNTS_EC_NUM = CH_CTAISYOUGYOU_CTNTS_SC_NUM + 2;
	// 実行行数
	private int CH_JIKKOUGYOU_TITLE_SC_NUM = CH_CTAISYOUGYOU_CTNTS_EC_NUM + 1;
	private int CH_JIKKOUGYOU_TITLE_EC_NUM = CH_JIKKOUGYOU_TITLE_SC_NUM + 2;
	private int CH_JIKKOUGYOU_CTNTS_SC_NUM = CH_JIKKOUGYOU_TITLE_EC_NUM + 1;
	private int CH_JIKKOUGYOU_CTNTS_EC_NUM = CH_JIKKOUGYOU_CTNTS_SC_NUM + 2;
	// カバレッジ
	private int CH_COVERAGE_TITLE_SC_NUM = CH_JIKKOUGYOU_CTNTS_EC_NUM + 1;
	private int CH_COVERAGE_TITLE_EC_NUM = CH_COVERAGE_TITLE_SC_NUM + 2;
	private int CH_COVERAGE_CTNTS_SC_NUM = CH_COVERAGE_TITLE_EC_NUM + 1;
	private int CH_COVERAGE_CTNTS_EC_NUM = CH_COVERAGE_CTNTS_SC_NUM + 2;
	// 警告Lv1
	private int CH_WRNLV1_TITLE_SC_NUM = CH_COVERAGE_CTNTS_EC_NUM + 1;
	private int CH_WRNLV1_TITLE_EC_NUM = CH_WRNLV1_TITLE_SC_NUM + 2;
	private int CH_WRNLV1_CTNTS_SC_NUM = CH_WRNLV1_TITLE_EC_NUM + 1;
	private int CH_WRNLV1_CTNTS_EC_NUM = CH_WRNLV1_CTNTS_SC_NUM + 2;
	// 警告Lv2
	private int CH_WRNLV2_TITLE_SC_NUM = CH_WRNLV1_CTNTS_EC_NUM + 1;
	private int CH_WRNLV2_TITLE_EC_NUM = CH_WRNLV2_TITLE_SC_NUM + 2;
	private int CH_WRNLV2_CTNTS_SC_NUM = CH_WRNLV2_TITLE_EC_NUM + 1;
	private int CH_WRNLV2_CTNTS_EC_NUM = CH_WRNLV2_CTNTS_SC_NUM + 2;
	// 警告Lv3
	private int CH_WRNLV3_TITLE_SC_NUM = CH_WRNLV2_CTNTS_EC_NUM + 1;
	private int CH_WRNLV3_TITLE_EC_NUM = CH_WRNLV3_TITLE_SC_NUM + 2;
	private int CH_WRNLV3_CTNTS_SC_NUM = CH_WRNLV3_TITLE_EC_NUM + 1;
	private int CH_WRNLV3_CTNTS_EC_NUM = CH_WRNLV3_CTNTS_SC_NUM + 2;
	// 警告Lv4
	private int CH_WRNLV4_TITLE_SC_NUM = CH_WRNLV3_CTNTS_EC_NUM + 1;
	private int CH_WRNLV4_TITLE_EC_NUM = CH_WRNLV4_TITLE_SC_NUM + 2;
	private int CH_WRNLV4_CTNTS_SC_NUM = CH_WRNLV4_TITLE_EC_NUM + 1;
	private int CH_WRNLV4_CTNTS_EC_NUM = CH_WRNLV4_CTNTS_SC_NUM + 2;
	// 警告Lv5
	private int CH_WRNLV5_TITLE_SC_NUM = CH_WRNLV4_CTNTS_EC_NUM + 1;
	private int CH_WRNLV5_TITLE_EC_NUM = CH_WRNLV5_TITLE_SC_NUM + 2;
	private int CH_WRNLV5_CTNTS_SC_NUM = CH_WRNLV5_TITLE_EC_NUM + 1;
	private int CH_WRNLV5_CTNTS_EC_NUM = CH_WRNLV5_CTNTS_SC_NUM + 2;

	private int WRNMSG_TITLE_EC_NUM = 62;
	private int WRNMSG_TITLE_SC_NUM = WRNMSG_TITLE_EC_NUM - 5;
	private int WRNLV5_TITLE_EC_NUM = WRNMSG_TITLE_SC_NUM - 1;
	private int WRNLV5_TITLE_SC_NUM = WRNLV5_TITLE_EC_NUM - 1;
	private int WRNLV4_TITLE_EC_NUM = WRNLV5_TITLE_SC_NUM - 1;
	private int WRNLV4_TITLE_SC_NUM = WRNLV4_TITLE_EC_NUM - 1;
	private int WRNLV3_TITLE_EC_NUM = WRNLV4_TITLE_SC_NUM - 1;
	private int WRNLV3_TITLE_SC_NUM = WRNLV3_TITLE_EC_NUM - 1;
	private int WRNLV2_TITLE_EC_NUM = WRNLV3_TITLE_SC_NUM - 1;
	private int WRNLV2_TITLE_SC_NUM = WRNLV2_TITLE_EC_NUM - 1;
	private int WRNLV1_TITLE_EC_NUM = WRNLV2_TITLE_SC_NUM - 1;
	private int WRNLV1_TITLE_SC_NUM = WRNLV1_TITLE_EC_NUM - 1;

	/**
	 * 詳細ページヘッダー（全体）
	 */
	private void writeContentsHeader() {

		writeContentsHeader1();
		sorceheader();
	}

	/**
	 * 詳細ページヘッダー
	 */
	private void writeContentsHeader1() {

		// サマリー
		// 対象行数
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_TAISYOUGYOU_TITLE_SC_NUM, CH_TAISYOUGYOU_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_TAISYOUGYOU_CTNTS_SC_NUM, CH_TAISYOUGYOU_CTNTS_EC_NUM);
		// （独自）対象行数
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_CTAISYOUGYOU_CTNTS_SC_NUM, CH_CTAISYOUGYOU_CTNTS_EC_NUM);
		// 実行行数
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_JIKKOUGYOU_TITLE_SC_NUM, CH_JIKKOUGYOU_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_JIKKOUGYOU_CTNTS_SC_NUM, CH_JIKKOUGYOU_CTNTS_EC_NUM);
		// カバレッジ
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_COVERAGE_TITLE_SC_NUM, CH_COVERAGE_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_COVERAGE_CTNTS_SC_NUM, CH_COVERAGE_CTNTS_EC_NUM);
		// 警告Lv1
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV1_TITLE_SC_NUM, CH_WRNLV1_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV1_CTNTS_SC_NUM, CH_WRNLV1_CTNTS_EC_NUM);
		// 警告Lv2
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV2_TITLE_SC_NUM, CH_WRNLV2_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV2_CTNTS_SC_NUM, CH_WRNLV2_CTNTS_EC_NUM);
		// 警告Lv3
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV3_TITLE_SC_NUM, CH_WRNLV3_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV3_CTNTS_SC_NUM, CH_WRNLV3_CTNTS_EC_NUM);
		// 警告Lv4
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV4_TITLE_SC_NUM, CH_WRNLV4_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV4_CTNTS_SC_NUM, CH_WRNLV4_CTNTS_EC_NUM);
		// 警告Lv5
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV5_TITLE_SC_NUM, CH_WRNLV5_TITLE_EC_NUM);
		margedRegion(sheet, CH_ROW_NUM, CH_ROW_NUM, CH_WRNLV5_CTNTS_SC_NUM, CH_WRNLV5_CTNTS_EC_NUM);

		HSSFRow row = sheet.createRow(CH_ROW_NUM);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			if (i <= CH_TAISYOUGYOU_TITLE_EC_NUM
			        //			        || i >= CH_CTAISYOUGYOU_TITLE_SC_NUM && i <= CH_CTAISYOUGYOU_TITLE_EC_NUM
			        || i >= CH_JIKKOUGYOU_TITLE_SC_NUM && i <= CH_JIKKOUGYOU_TITLE_EC_NUM
			        || i >= CH_COVERAGE_TITLE_SC_NUM && i <= CH_COVERAGE_TITLE_EC_NUM
			        || i >= CH_WRNLV1_TITLE_SC_NUM && i <= CH_WRNLV1_TITLE_EC_NUM
			        || i >= CH_WRNLV2_TITLE_SC_NUM && i <= CH_WRNLV2_TITLE_EC_NUM
			        || i >= CH_WRNLV3_TITLE_SC_NUM && i <= CH_WRNLV3_TITLE_EC_NUM
			        || i >= CH_WRNLV4_TITLE_SC_NUM && i <= CH_WRNLV4_TITLE_EC_NUM
			        || i >= CH_WRNLV5_TITLE_SC_NUM && i <= CH_WRNLV5_TITLE_EC_NUM) {
				cell.setCellStyle(title_style);
			}
			if (i >= CH_TAISYOUGYOU_CTNTS_SC_NUM && i <= CH_TAISYOUGYOU_CTNTS_EC_NUM
			        || i >= CH_CTAISYOUGYOU_CTNTS_SC_NUM && i <= CH_CTAISYOUGYOU_CTNTS_EC_NUM
			        || i >= CH_JIKKOUGYOU_CTNTS_SC_NUM && i <= CH_JIKKOUGYOU_CTNTS_EC_NUM
			        || i >= CH_COVERAGE_CTNTS_SC_NUM && i <= CH_COVERAGE_CTNTS_EC_NUM
			        || i >= CH_WRNLV1_CTNTS_SC_NUM && i <= CH_WRNLV1_CTNTS_EC_NUM
			        || i >= CH_WRNLV2_CTNTS_SC_NUM && i <= CH_WRNLV2_CTNTS_EC_NUM
			        || i >= CH_WRNLV3_CTNTS_SC_NUM && i <= CH_WRNLV3_CTNTS_EC_NUM
			        || i >= CH_WRNLV4_CTNTS_SC_NUM && i <= CH_WRNLV4_CTNTS_EC_NUM
			        || i >= CH_WRNLV5_CTNTS_SC_NUM && i <= CH_WRNLV5_CTNTS_EC_NUM) {
				if (i == CH_TAISYOUGYOU_CTNTS_EC_NUM) {
					cell.setCellStyle(contents_dotborder_left_style);
					continue;
				}
				if (i == CH_CTAISYOUGYOU_CTNTS_SC_NUM) {
					cell.setCellStyle(contents_dotborder_right_style);
					continue;
				}
				cell.setCellStyle(contents_center_style);
			}
		}
		cell = row.getCell(CH_TAISYOUGYOU_TITLE_SC_NUM);
		cell.setCellValue("対象行数");
		cell = row.getCell(CH_TAISYOUGYOU_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_CTAISYOUGYOU_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_JIKKOUGYOU_TITLE_SC_NUM);
		cell.setCellValue("実行行数");
		cell = row.getCell(CH_JIKKOUGYOU_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_COVERAGE_TITLE_SC_NUM);
		cell.setCellValue("カバレッジ");
		cell = row.getCell(CH_COVERAGE_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_WRNLV1_TITLE_SC_NUM);
		cell.setCellValue("警告Lv1");
		cell = row.getCell(CH_WRNLV1_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_WRNLV2_TITLE_SC_NUM);
		cell.setCellValue("警告Lv2");
		cell = row.getCell(CH_WRNLV2_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_WRNLV3_TITLE_SC_NUM);
		cell.setCellValue("警告Lv3");
		cell = row.getCell(CH_WRNLV3_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_WRNLV4_TITLE_SC_NUM);
		cell.setCellValue("警告Lv4");
		cell = row.getCell(CH_WRNLV4_CTNTS_SC_NUM);
		cell.setCellValue(0);
		cell = row.getCell(CH_WRNLV5_TITLE_SC_NUM);
		cell.setCellValue("警告Lv5");
		cell = row.getCell(CH_WRNLV5_CTNTS_SC_NUM);
		cell.setCellValue(0);
	}

	/**
	 * ソース記載ヘッダー
	 */
	private void sorceheader() {

		margedRegion(sheet, 7, 7, WRNLV1_TITLE_SC_NUM, WRNLV1_TITLE_EC_NUM);
		margedRegion(sheet, 7, 7, WRNLV2_TITLE_SC_NUM, WRNLV2_TITLE_EC_NUM);
		margedRegion(sheet, 7, 7, WRNLV3_TITLE_SC_NUM, WRNLV3_TITLE_EC_NUM);
		margedRegion(sheet, 7, 7, WRNLV4_TITLE_SC_NUM, WRNLV4_TITLE_EC_NUM);
		margedRegion(sheet, 7, 7, WRNLV5_TITLE_SC_NUM, WRNLV5_TITLE_EC_NUM);
		margedRegion(sheet, 7, 7, WRNMSG_TITLE_SC_NUM, WRNMSG_TITLE_EC_NUM);
		HSSFRow row = sheet.createRow(7);
		HSSFCell cell = null;
		for (int i = WRNLV1_TITLE_SC_NUM; i < WRNMSG_TITLE_EC_NUM; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(title_style);
		}
		cell = row.getCell(WRNLV1_TITLE_SC_NUM);
		cell.setCellValue("Lv1");
		cell = row.getCell(WRNLV2_TITLE_SC_NUM);
		cell.setCellValue("Lv2");
		cell = row.getCell(WRNLV3_TITLE_SC_NUM);
		cell.setCellValue("Lv3");
		cell = row.getCell(WRNLV4_TITLE_SC_NUM);
		cell.setCellValue("Lv4");
		cell = row.getCell(WRNLV5_TITLE_SC_NUM);
		cell.setCellValue("Lv5");
		cell = row.getCell(WRNMSG_TITLE_SC_NUM);
		cell.setCellValue("メッセージ");
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

	/**
	 * クラス別カバレッジサマリ情報記載部分
	 * @param cobj カバレッジクラス情報
	 */
	private void writeContentsHeaderforCoverage(CoverageClass cobj) {

		HSSFRow row = sheet.getRow(CH_ROW_NUM);
		HSSFCell cell = null;
		cell = row.getCell(CH_TAISYOUGYOU_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getNumLocations());
		cell = row.getCell(CH_CTAISYOUGYOU_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getCalcLocations());
		cell = row.getCell(CH_JIKKOUGYOU_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getNumLinesCovered());
		cell = row.getCell(CH_COVERAGE_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getCoverageRate());
	}

	/**
	 * 詳細ページPMDサマリ情報出力処理
	 * @param cobj PMDクラス情報
	 */
	private void writeContentsHeaderforPMD(PMDClass cobj) {

		HSSFRow row = sheet.getRow(CH_ROW_NUM);
		HSSFCell cell = null;
		cell = row.getCell(CH_WRNLV1_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getAbsolutelyrequiredcnt());
		cell = row.getCell(CH_WRNLV2_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getHightlyrecommendedcnt());
		cell = row.getCell(CH_WRNLV3_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getRecommendedcnt());
		cell = row.getCell(CH_WRNLV4_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getOptinalcnt());
		cell = row.getCell(CH_WRNLV5_CTNTS_SC_NUM);
		cell.setCellValue(cobj.getHightlyoptionalcnt());
	}
}
