package com.SFManagementAntTask.excel;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.ExcelWriter;
import com.SFManagementAntTask.partner.dao.model.CustomField;
import com.SFManagementAntTask.partner.dao.model.CustomObject;
import com.SFManagementAntTask.partner.dao.model.Relationship;

public class TableDefinitionExcelWriter extends AbsExcelWriter {

	private final static Logger log = LoggerFactory.getLogger(CoverageExcelWriter.class);

	public TableDefinitionExcelWriter() {
		super.writer = new ExcelWriter(CommonUtil.getReportPath() + "/excel/" + "項目定義書", true);
	}

	public void write(List<CustomObject> cobjlist) {

		for (CustomObject cobj : cobjlist) {
			createSheet(cobj.getLabel(), cobj.getName());
			for (CustomField fobj : cobj.getFields()) {
				HSSFRow row = sheet.createRow(++linecnt);
				HSSFCell cell = row.createCell(3);
				cell.setCellValue(fobj.getLabel());
				cell = row.createCell(11);
				cell.setCellValue(fobj.getName());
			}
			linecnt += 2;
			for (Relationship fobj : cobj.getChildRelationships()) {
				if (StringUtils.isBlank(fobj.getRelationshipName())) {
					continue;
				}
				HSSFRow row = sheet.createRow(++linecnt);
				HSSFCell cell = row.createCell(3);
				cell.setCellValue(fobj.getRelationshipName());
				cell = row.createCell(11);
				cell.setCellValue(fobj.getName());
				cell = row.createCell(18);
				cell.setCellValue(fobj.getChildSObject());
			}
		}
	}

	/** 目次シート：開始行 */
	private int AGENDA_S_ROW_NUM = 5;
	private int agendalistStartRow = 0;
	private int agendalistEndCnt = AGENDA_S_ROW_NUM;

	// NO
	private int AD_NO_SC_NUM = 1;
	private int AD_NO_EC_NUM = AD_NO_SC_NUM + 1;
	// API参照名
	private int AD_NAME_SC_NUM = AD_NO_EC_NUM + 1;
	private int AD_NAME_EC_NUM = AD_NAME_SC_NUM + 7;
	// ラベル
	private int AD_LABEL_SC_NUM = AD_NAME_EC_NUM + 1;
	private int AD_LABEL_EC_NUM = AD_LABEL_SC_NUM + 7;
	// 作成日
	private int AD_CREATE_SC_NUM = AD_LABEL_EC_NUM + 1;
	private int AD_CREATE_EC_NUM = AD_CREATE_SC_NUM + 4;
	// 更新日
	private int AD_UPDATE_SC_NUM = AD_CREATE_EC_NUM + 1;
	private int AD_UPDATE_EC_NUM = AD_UPDATE_SC_NUM + 4;
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
		writeContentsHeader(sheet);
		return sheet;
	}

	private void margedRegionforAgenda(HSSFSheet sheet) {
		margedRegion(sheet, ++agendalistEndCnt, agendalistEndCnt, AD_NO_SC_NUM, AD_NO_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_NAME_SC_NUM, AD_NAME_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_LABEL_SC_NUM, AD_LABEL_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_CREATE_SC_NUM, AD_CREATE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_UPDATE_SC_NUM, AD_UPDATE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_UPDATEUSER_SC_NUM, AD_UPDATEUSER_EC_NUM);
	}

	/**
	 * 目次一覧ヘッダー
	 * @param sheet 対象シート
	 */
	private void writeListHeaderforAgenda(HSSFSheet sheet) {

		margedRegionforAgenda(sheet);

		// セルへ出力
		HSSFRow row = sheet.createRow(agendalistEndCnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(title_style);
		}
		cell = row.getCell(AD_NO_SC_NUM);
		cell.setCellValue("No.");
		cell = row.getCell(AD_NAME_SC_NUM);
		cell.setCellValue("クラス名");
		cell = row.getCell(AD_LABEL_SC_NUM);
		cell.setCellValue("対象行");
		cell = row.getCell(AD_CREATE_SC_NUM);
		cell.setCellValue("作成日");
		cell = row.getCell(AD_UPDATE_SC_NUM);
		cell.setCellValue("更新日");
		cell = row.getCell(AD_UPDATEUSER_SC_NUM);
		cell.setCellValue("更新者");
	}

	/**
	 * 目次一覧
	 * @param sheet 対象シート
	 * @param j No
	 * @param classname クラス名
	 */
	private void writeAgenda(HSSFSheet sheet, String sheetname, String classname) {

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
		cell = row.getCell(AD_LABEL_SC_NUM);
		cell.setCellStyle(contents_dotborder_right_style);
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

	// NO
	private int CH_NO_SC_NUM = 1;
	private int CH_NO_EC_NUM = CH_NO_SC_NUM + 1;
	// API参照名
	private int CH_NAME_SC_NUM = CH_NO_EC_NUM + 1;
	private int CH_NAME_EC_NUM = CH_NAME_SC_NUM + 7;
	// ラベル
	private int CH_LABEL_SC_NUM = CH_NAME_EC_NUM + 1;
	private int CH_LABEL_EC_NUM = CH_LABEL_SC_NUM + 7;
	// データ型
	private int CH_DATA_TYPE_SC_NUM = CH_LABEL_EC_NUM + 1;
	private int CH_DATA_TYPE_EC_NUM = CH_DATA_TYPE_SC_NUM + 3;
	// 文字数
	private int CH_LENGTH_SC_NUM = CH_DATA_TYPE_EC_NUM + 1;
	private int CH_LENGTH_EC_NUM = CH_LENGTH_SC_NUM + 2;
	// 参照先
	private int CH_REFERENCE_SC_NUM = CH_LENGTH_EC_NUM + 1;
	private int CH_REFERENCE_EC_NUM = CH_REFERENCE_SC_NUM + 4;
	// 選択リスト
	private int CH_PICKVALUE_SC_NUM = CH_REFERENCE_EC_NUM + 1;
	private int CH_PICKVALUE_EC_NUM = CH_PICKVALUE_SC_NUM + 4;
	// 必須
	private int CH_REQUAIRED_SC_NUM = CH_PICKVALUE_EC_NUM + 1;
	private int CH_REQUAIRED_EC_NUM = CH_REQUAIRED_SC_NUM + 1;
	// 外部ID
	private int CH_EXTERNALID_SC_NUM = CH_REQUAIRED_EC_NUM + 1;
	private int CH_EXTERNALID_EC_NUM = CH_EXTERNALID_SC_NUM + 2;
	// フィルター
	private int CH_FILTERABLE_SC_NUM = CH_EXTERNALID_EC_NUM + 1;
	private int CH_FILTERABLE_EC_NUM = CH_FILTERABLE_SC_NUM + 3;
	// 数式
	private int CH_MATHMATICAL_SC_NUM = CH_FILTERABLE_EC_NUM + 1;
	private int CH_MATHMATICAL_EC_NUM = CH_MATHMATICAL_SC_NUM + 5;
	// ヘルプテキスト
	private int CH_HELP_TEXT_SC_NUM = CH_MATHMATICAL_EC_NUM + 1;
	private int CH_HELP_TEXT_EC_NUM = CH_HELP_TEXT_SC_NUM + 4;
	// NOTE
	private int CH_NOTE_SC_NUM = CH_HELP_TEXT_EC_NUM + 1;
	private int CH_NOTE_EC_NUM = CH_NOTE_SC_NUM + 6;
	// 作成日
	private int CH_CREATE_SC_NUM = CH_NOTE_EC_NUM + 1;
	private int CH_CREATE_EC_NUM = CH_CREATE_SC_NUM + 4;
	// 更新日
	private int CH_UPDATE_SC_NUM = CH_CREATE_EC_NUM + 1;
	private int CH_UPDATE_EC_NUM = CH_UPDATE_SC_NUM + 4;
	// 更新者
	private int CH_UPDATEUSER_SC_NUM = CH_UPDATE_EC_NUM + 1;
	private int CH_UPDATEUSER_EC_NUM = CH_UPDATEUSER_SC_NUM + 4; // 62

	private void margedRegion(HSSFSheet sheet) {
		margedRegion(sheet, ++agendalistEndCnt, agendalistEndCnt, CH_NO_SC_NUM, CH_NO_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_NAME_SC_NUM, CH_NAME_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_LABEL_SC_NUM, CH_LABEL_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_DATA_TYPE_SC_NUM, CH_DATA_TYPE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_LENGTH_SC_NUM, CH_LENGTH_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_REFERENCE_SC_NUM, CH_REFERENCE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_PICKVALUE_SC_NUM, CH_PICKVALUE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_REQUAIRED_SC_NUM, CH_REQUAIRED_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_EXTERNALID_SC_NUM, CH_EXTERNALID_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_FILTERABLE_SC_NUM, CH_FILTERABLE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_MATHMATICAL_SC_NUM, CH_MATHMATICAL_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_HELP_TEXT_SC_NUM, CH_HELP_TEXT_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, CH_NOTE_SC_NUM, CH_NOTE_EC_NUM);
	}

	/**
	 * 目次一覧ヘッダー
	 * @param sheet 対象シート
	 */
	private void writeContentsHeader(HSSFSheet sheet) {

		margedRegion(sheet);

		// セルへ出力
		HSSFRow row = sheet.createRow(agendalistEndCnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(title_style);
		}
		cell = row.getCell(CH_NO_SC_NUM);
		cell.setCellValue("No.");
		cell = row.getCell(CH_NAME_SC_NUM);
		cell.setCellValue("クラス名");
		cell = row.getCell(CH_LABEL_SC_NUM);
		cell.setCellValue("ラベル");
		cell = row.getCell(CH_DATA_TYPE_SC_NUM);
		cell.setCellValue("データ型");
		cell = row.getCell(CH_LENGTH_SC_NUM);
		cell.setCellValue("文字数");
		cell = row.getCell(CH_REFERENCE_SC_NUM);
		cell.setCellValue("参照先");
		cell = row.getCell(CH_PICKVALUE_SC_NUM);
		cell.setCellValue("選択リスト");
		cell = row.getCell(CH_REQUAIRED_SC_NUM);
		cell.setCellValue("必須");
		cell = row.getCell(CH_EXTERNALID_SC_NUM);
		cell.setCellValue("外部ID");
		cell = row.getCell(CH_FILTERABLE_SC_NUM);
		cell.setCellValue("filterable");
		cell = row.getCell(CH_MATHMATICAL_SC_NUM);
		cell.setCellValue("数式");
		cell = row.getCell(CH_HELP_TEXT_SC_NUM);
		cell.setCellValue("ヘルプテキスト");
		cell = row.getCell(CH_NOTE_SC_NUM);
		cell.setCellValue("備考");

		//		cell = row.getCell(AD_CREATE_SC_NUM);
		//		cell.setCellValue("作成日");
		//		cell = row.getCell(AD_UPDATE_SC_NUM);
		//		cell.setCellValue("更新日");
		//		cell = row.getCell(AD_UPDATEUSER_SC_NUM);
		//		cell.setCellValue("更新者");
	}

	private void writeContentsTitle() {

	}

	public void finish() {
		super.writer.finish();
	}
}
