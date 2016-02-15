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
		initilize();
		createAgenda();
	}

	public void write(List<CustomObject> cobjlist) {

		if (cobjlist == null) {
			return;
		}
		for (CustomObject cobj : cobjlist) {
			createSheet(cobj);
			if (cobj.getFields() == null) {
				continue;
			}
			nocnt = 0;
			for (CustomField fobj : cobj.getFields()) {
				writeContents(fobj);
			}
			linecnt = sheet.getLastRowNum() + 1;
			if (cobj.getChildRelationships() == null || cobj.getChildRelationships().isEmpty()) {
				continue;
			}
			if (!cobj.getChildRelationships().isEmpty()) {
				writeContentsHeaderforRelation(sheet);
			}
			for (Relationship fobj : cobj.getChildRelationships()) {
				if (StringUtils.isBlank(fobj.getRelationshipName())) {
					continue;
				}
				writeContentsforRelation(fobj);
			}
		}
	}

	// ######################################################################
	// ## 目次ページ
	// ######################################################################

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
	// ネームスペース
	private int AD_NAMESPACE_SC_NUM = AD_LABEL_EC_NUM + 1;
	private int AD_NAMESPACE_EC_NUM = AD_NAMESPACE_SC_NUM + 5;
	// キープレフィックス
	private int AD_PREFIX_SC_NUM = AD_NAMESPACE_EC_NUM + 1;
	private int AD_PREFIX_EC_NUM = AD_PREFIX_SC_NUM + 2;
	// 作成日
	private int AD_CREATE_SC_NUM = AD_PREFIX_EC_NUM + 1;
	private int AD_CREATE_EC_NUM = AD_CREATE_SC_NUM + 4;
	// 更新日
	private int AD_UPDATE_SC_NUM = AD_CREATE_EC_NUM + 1;
	private int AD_UPDATE_EC_NUM = AD_UPDATE_SC_NUM + 4;
	// 更新者
	private int AD_UPDATEUSER_SC_NUM = AD_UPDATE_EC_NUM + 1;
	private int AD_UPDATEUSER_EC_NUM = 62; // 62

	/**
	 * シート作成処理
	 * @param sheetname シート名
	 * @param classname 機能名
	 */
	public void createSheet(CustomObject cobj) {
		log.debug("[createSheet] " + cobj.getName() + " : " + cobj.getLabel());

		// 目次の作成
		HSSFSheet asheet = writer.wb.getSheet("目次");
		writeAgenda(asheet, cobj);
		int i = writer.wb.getSheetIndex("temp");
		sheet = writer.wb.cloneSheet(i);
		writer.wb.setSheetName(i + (++cnt), cobj.getName());
		headertile3_4(sheet, cobj.getName());
		linecnt = 9;
	}

	private int cnt = 0;

	/**
	 * 目次シート作成処理
	 * @return 目次シート
	 */
	private HSSFSheet createAgenda() {

		HSSFSheet sheet = writer.wb.getSheet("目次");
		headertile3_4(sheet, "");
		return sheet;
	}

	/**
	 * 目次一覧マージ処理
	 * @param sheet 対象シート
	 */
	private void margedRegionforAgenda(HSSFSheet sheet) {
		margedRegion(sheet, ++agendalistEndCnt, agendalistEndCnt, AD_NO_SC_NUM, AD_NO_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_NAME_SC_NUM, AD_NAME_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_LABEL_SC_NUM, AD_LABEL_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_NAMESPACE_SC_NUM, AD_NAMESPACE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_PREFIX_SC_NUM, AD_PREFIX_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_CREATE_SC_NUM, AD_CREATE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_UPDATE_SC_NUM, AD_UPDATE_EC_NUM);
		margedRegion(sheet, agendalistEndCnt, agendalistEndCnt, AD_UPDATEUSER_SC_NUM, AD_UPDATEUSER_EC_NUM);
	}

	int agendaNoCnt = 0;

	/**
	 * 目次一覧
	 * @param sheet 対象シート
	 * @param j No
	 * @param classname クラス名
	 */
	private void writeAgenda(HSSFSheet sheet, CustomObject cobj) {

		margedRegionforAgenda(sheet);

		// セルへ出力
		HSSFRow row = sheet.createRow(agendalistEndCnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(contents_center_style);
		}
		cell = row.getCell(AD_NO_SC_NUM);
		cell.setCellValue(++agendaNoCnt);
		cell = row.getCell(AD_NAME_SC_NUM);
		cell.setCellValue(cobj.getName());
		cell.setCellStyle(contents_border_top_left_bottom_right_style);
		HSSFCreationHelper createHelper = (HSSFCreationHelper) writer.wb.getCreationHelper();
		HSSFHyperlink link = createHelper.createHyperlink(HSSFHyperlink.LINK_DOCUMENT);
		link.setAddress(cobj.getName() + "!A1");
		cell.setHyperlink(link);
		// ラベル名
		cell = row.getCell(AD_LABEL_SC_NUM);
		cell.setCellValue(cobj.getLabel());
		cell.setCellStyle(contents_border_top_left_bottom_right_style);
		// キーププレフィックス
		cell = row.getCell(AD_NAMESPACE_SC_NUM);
		cell.setCellValue(cobj.getNamespacePrefix());
		cell = row.getCell(AD_PREFIX_SC_NUM);
		cell.setCellValue(cobj.getKeyPrefix());
		cell = row.getCell(AD_CREATE_SC_NUM);
		if (cobj.getCreatedDate() != null) {
			cell.setCellValue(cobj.getCreatedDate());
			cell.setCellStyle(contents_center_date_style);
		}
		cell = row.getCell(AD_UPDATE_SC_NUM);
		if (cobj.getLastModifiedDate() != null) {
			cell.setCellValue(cobj.getLastModifiedDate());
			cell.setCellStyle(contents_center_date_style);
		}
		cell = row.getCell(AD_UPDATEUSER_SC_NUM);
		cell.setCellValue(cobj.getLastModifiedByName());
	}

	// ######################################################################
	// ## 詳細ページ
	// ######################################################################

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
	private int CH_CREATE_SC_NUM = CH_NOTE_EC_NUM + 2;
	//private int CH_CREATE_EC_NUM = CH_CREATE_SC_NUM + 4;
	// 更新日
	private int CH_UPDATE_SC_NUM = CH_CREATE_SC_NUM + 1;
	//private int CH_UPDATE_EC_NUM = CH_UPDATE_SC_NUM + 4;
	// 更新者
	private int CH_UPDATEUSER_SC_NUM = CH_UPDATE_SC_NUM + 1;
	//private int CH_UPDATEUSER_EC_NUM = CH_UPDATEUSER_SC_NUM + 4; // 62
	// リレーションオブジェクト名
	private int CH_RELATION_OBJECT_SC_NUM = CH_LABEL_EC_NUM + 1;
	private int CH_RELATION_OBJECT_EC_NUM = 62;

	/**
	 * 詳細ページセル結合
	 * @param sheet
	 */
	private void margedRegion(HSSFSheet sheet) {
		linecnt = sheet.getLastRowNum() + 1;
		margedRegion(sheet, linecnt, linecnt, CH_NO_SC_NUM, CH_NO_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_NAME_SC_NUM, CH_NAME_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_LABEL_SC_NUM, CH_LABEL_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_DATA_TYPE_SC_NUM, CH_DATA_TYPE_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_LENGTH_SC_NUM, CH_LENGTH_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_REFERENCE_SC_NUM, CH_REFERENCE_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_PICKVALUE_SC_NUM, CH_PICKVALUE_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_REQUAIRED_SC_NUM, CH_REQUAIRED_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_EXTERNALID_SC_NUM, CH_EXTERNALID_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_FILTERABLE_SC_NUM, CH_FILTERABLE_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_MATHMATICAL_SC_NUM, CH_MATHMATICAL_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_HELP_TEXT_SC_NUM, CH_HELP_TEXT_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_NOTE_SC_NUM, CH_NOTE_EC_NUM);
	}

	/**
	 * 詳細ページセル結合
	 * @param sheet
	 */
	private void margedRegionforRelation(HSSFSheet sheet) {
		//linecnt = sheet.getLastRowNum() + 1;
		margedRegion(sheet, ++linecnt, linecnt, CH_NO_SC_NUM, CH_NO_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_NAME_SC_NUM, CH_NAME_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_LABEL_SC_NUM, CH_LABEL_EC_NUM);
		margedRegion(sheet, linecnt, linecnt, CH_RELATION_OBJECT_SC_NUM, CH_RELATION_OBJECT_EC_NUM);
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

	/** NOカウント */
	int nocnt = 0;

	/**
	 * 
	 * @param field
	 */
	private void writeContents(CustomField field) {

		margedRegion(sheet);
		//		sheet.setColumnWidth(64, 3000);
		//		sheet.setColumnWidth(65, 3000);
		//		sheet.setColumnWidth(66, 6000);
		// セルへ出力
		HSSFRow row = getRow(sheet, linecnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = getCell(row, i);
			cell.setCellStyle(contents_border_top_left_bottom_right_style);
		}
		cell = getCell(row, CH_NO_SC_NUM);
		cell.setCellValue(++nocnt);
		cell = getCell(row, CH_NAME_SC_NUM);
		cell.setCellValue(field.getName());
		cell = getCell(row, CH_LABEL_SC_NUM);
		cell.setCellValue(field.getLabel());
		cell = getCell(row, CH_DATA_TYPE_SC_NUM);
		cell.setCellValue(field.getType());
		cell = getCell(row, CH_LENGTH_SC_NUM);
		cell.setCellValue(field.getLength());
		cell = getCell(row, CH_REFERENCE_SC_NUM);
		cell.setCellValue(field.getReferenceTo());
		cell = getCell(row, CH_PICKVALUE_SC_NUM);
		cell.setCellValue(field.getPicklistvalues());
		cell = getCell(row, CH_REQUAIRED_SC_NUM);
		cell.setCellValue(field.getNillable());
		cell.setCellStyle(contents_center_style);
		cell = getCell(row, CH_EXTERNALID_SC_NUM);
		cell.setCellValue(field.getExternalId());
		cell.setCellStyle(contents_center_style);
		cell = getCell(row, CH_FILTERABLE_SC_NUM);
		cell.setCellValue(field.getFilteredLookupInfo());
		cell = getCell(row, CH_MATHMATICAL_SC_NUM);
		cell.setCellValue(field.getCalculatedFormula());
		cell = getCell(row, CH_HELP_TEXT_SC_NUM);
		cell.setCellValue(field.getInlineHelpText());
		cell = getCell(row, CH_NOTE_SC_NUM);
		cell.setCellValue("");
		cell = getCell(row, CH_CREATE_SC_NUM);
		if (field.getCreatedDate() != null) {
			cell.setCellValue(field.getCreatedDate());
			cell.setCellStyle(contents_center_date_style);
		}
		cell = getCell(row, CH_UPDATE_SC_NUM);
		if (field.getLastModifiedDate() != null) {
			cell.setCellValue(field.getLastModifiedDate());
			cell.setCellStyle(contents_center_date_style);
		}
		cell = getCell(row, CH_UPDATEUSER_SC_NUM);
		if (StringUtils.isNoneBlank(field.getLastModifiedByName())) {
			cell.setCellValue(field.getLastModifiedByName());
			cell.setCellStyle(contents_border_top_left_bottom_right_style);
		}
		cell = getCell(row, 70);
	}

	/**
	 * 目次一覧ヘッダー
	 * @param sheet 対象シート
	 */
	private void writeContentsHeaderforRelation(HSSFSheet sheet) {

		margedRegionforRelation(sheet);

		// セルへ出力
		HSSFRow row = getRow(sheet, linecnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = getCell(row, i);
			cell.setCellStyle(title_style);
		}
		cell = getCell(row, CH_NO_SC_NUM);
		cell.setCellValue("No.");
		cell = getCell(row, CH_NAME_SC_NUM);
		cell.setCellValue("リレーション名");
		cell = getCell(row, CH_LABEL_SC_NUM);
		cell.setCellValue("項目名");
		cell = getCell(row, CH_RELATION_OBJECT_SC_NUM);
		cell.setCellValue("オブジェクト名");
		//		cell = row.getCell(AD_CREATE_SC_NUM);
		//		cell.setCellValue("作成日");
		//		cell = row.getCell(AD_UPDATE_SC_NUM);
		//		cell.setCellValue("更新日");
		//		cell = row.getCell(AD_UPDATEUSER_SC_NUM);
		//		cell.setCellValue("更新者");
	}

	/**
	 * 
	 * @param field
	 */
	private void writeContentsforRelation(Relationship field) {

		margedRegionforRelation(sheet);

		// セルへ出力
		HSSFRow row = getRow(sheet, linecnt);
		HSSFCell cell = null;
		for (int i = 1; i < 63; i++) {
			cell = getCell(row, i);
			cell.setCellStyle(contents_border_top_left_bottom_right_style);
		}
		cell = getCell(row, CH_NO_SC_NUM);
		cell.setCellValue(++nocnt);
		cell = getCell(row, CH_NAME_SC_NUM);
		cell.setCellValue(field.getRelationshipName());
		cell = getCell(row, CH_LABEL_SC_NUM);
		cell.setCellValue(field.getName());
		cell = getCell(row, CH_RELATION_OBJECT_SC_NUM);
		cell.setCellValue(field.getChildSObject());
		//		cell = row.getCell(AD_CREATE_SC_NUM);
		//		if (field.getCreatedDate() != null) {
		//			cell.setCellValue(field.getCreatedDate());
		//			cell.setCellStyle(contents_boldborder_bottom_date_style);
		//		}
		//		cell = row.getCell(AD_UPDATE_SC_NUM);
		//		if (field.getLastModifiedDate() != null) {
		//			cell.setCellValue(field.getLastModifiedDate());
		//			cell.setCellStyle(contents_boldborder_bottom_date_style);
		//		}
		//		cell = row.getCell(AD_UPDATEUSER_SC_NUM);
		//		cell.setCellValue(field.getLastModifiedByName());
	}

	public void finish() {
		int i = writer.wb.getSheetIndex("temp");
		writer.wb.removeSheetAt(i);
		super.writer.finish();
	}
}
