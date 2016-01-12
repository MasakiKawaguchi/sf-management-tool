package com.SFManagementAntTask.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import com.SFManagementAntTask.common.ExcelWriter;

public abstract class AbsExcelWriter {

	/** Excelライター */
	ExcelWriter writer;
	/** 作業シート */
	HSSFSheet sheet = null;
	/** 作業シート：開始位置 */
	int linecnt = 8;

	/** ヘッダー用タイトルスタイル */
	protected HSSFCellStyle title_style;
	/** 一般スタイル（中央揃え） */
	protected HSSFCellStyle contents_center_style;
	/** 一般スタイル（左揃え） */
	protected HSSFCellStyle contents_border_top_left_bottom_right_style;
	protected HSSFCellStyle contents_border_top_left_bottom_style;
	protected HSSFCellStyle contents_border_top_bottom_style;
	protected HSSFCellStyle contents_border_top_bottom_right_style;
	protected HSSFCellStyle contents_border_top_left_style;
	protected HSSFCellStyle contents_border_top_style;
	protected HSSFCellStyle contents_border_top_right_style;
	protected HSSFCellStyle contents_border_left_bottom_style;
	protected HSSFCellStyle contents_border_bottom_style;
	protected HSSFCellStyle contents_border_bottom_right_style;

	public AbsExcelWriter() {}

	protected void initilize() {
		setTitlStyle();
		HSSFFont contentsFont = writer.wb.createFont();
		contentsFont.setColor(HSSFColor.BLACK.index);
		contentsFont.setFontName("メイリオ");

		// 一般スタイル（中央揃え）
		contents_center_style = writer.wb.createCellStyle();
		contents_center_style.setFont(contentsFont);
		contents_center_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		contents_center_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_center_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_center_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contents_center_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contents_center_style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		setContentsStyle(contentsFont);
	}

	/**
	 * シート作成メソッド
	 * @param sheetname シート名
	 * @param filename 機能名
	 */
	public void createSheet(String sheetname, String filename) {
		sheet = writer.wb.createSheet(sheetname);
		// サイズ設定
		for (int i = 0; i < 64; i++) {
			sheet.setColumnWidth(i, 580);
		}
		headertile1_2(sheet);
		headertile3_4(sheet, filename);
		linecnt = 8;
	}

	/**
	 * ヘッダー（1行目-2行目）
	 * @param sheet
	 * @return
	 */
	protected HSSFSheet headertile1_2(HSSFSheet sheet) {

		// マージ処理
		CellRangeAddress system_region = new CellRangeAddress(0, 1, 0, 14);
		CellRangeAddress subsystem_region = new CellRangeAddress(0, 1, 15, 29);
		CellRangeAddress function_region = new CellRangeAddress(0, 1, 30, 43);
		CellRangeAddress createuser_region = new CellRangeAddress(0, 0, 44, 53);
		CellRangeAddress createdate_region = new CellRangeAddress(1, 1, 44, 53);
		CellRangeAddress updateuser_region = new CellRangeAddress(0, 0, 54, 63);
		CellRangeAddress updatedate_region = new CellRangeAddress(1, 1, 54, 63);

		sheet.addMergedRegion(system_region);
		sheet.addMergedRegion(subsystem_region);
		sheet.addMergedRegion(function_region);
		sheet.addMergedRegion(createuser_region);
		sheet.addMergedRegion(createdate_region);
		sheet.addMergedRegion(updateuser_region);
		sheet.addMergedRegion(updatedate_region);

		writer.wb.getFontAt((short) 0).setFontName("メイリオ");

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		HSSFCellStyle style1 = writer.wb.createCellStyle();
		style1.cloneStyleFrom(title_style);
		style1.setBorderTop(HSSFCellStyle.BORDER_THICK);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		cell.setCellValue("システム名");
		cell.setCellStyle(style1);

		HSSFCellStyle style2 = writer.wb.createCellStyle();
		style2.cloneStyleFrom(title_style);
		style2.setBorderTop(HSSFCellStyle.BORDER_THICK);
		for (int i = 1; i < 15; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style2);
		}

		cell = row.createCell(15);
		cell.setCellValue("サブシステム名");
		cell.setCellStyle(style2);

		for (int i = 16; i < 30; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style2);
		}
		cell = row.createCell(30);
		cell.setCellValue("機能名");
		cell.setCellStyle(style2);

		for (int i = 31; i < 44; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style2);
		}
		cell = row.createCell(44);
		cell.setCellValue("作成者");
		cell.setCellStyle(style2);

		for (int i = 45; i < 54; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style2);
		}
		cell = row.createCell(54);
		cell.setCellValue("更新者");
		cell.setCellStyle(style2);

		for (int i = 55; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style2);
		}
		cell = row.createCell(63);
		HSSFCellStyle style3 = writer.wb.createCellStyle();
		style3.cloneStyleFrom(title_style);
		style3.setBorderTop(HSSFCellStyle.BORDER_THICK);
		style3.setBorderRight(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style3);

		row = sheet.createRow(1);
		cell = row.createCell(0);
		HSSFCellStyle style4 = writer.wb.createCellStyle();
		style4.cloneStyleFrom(title_style);
		style4.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		//style4.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style4);

		HSSFCellStyle style5 = writer.wb.createCellStyle();
		style5.cloneStyleFrom(title_style);
		//style5.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		for (int i = 1; i < 44; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style5);
		}

		cell = row.createCell(44);
		cell.setCellValue("作成日");
		cell.setCellStyle(style5);
		for (int i = 45; i < 54; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style5);
		}
		cell = row.createCell(54);
		cell.setCellValue("更新日");
		cell.setCellStyle(style5);

		for (int i = 55; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style5);
		}
		cell = row.createCell(63);
		HSSFCellStyle style6 = writer.wb.createCellStyle();
		style6.cloneStyleFrom(title_style);
		style6.setBorderRight(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style6);

		return sheet;
	}

	/**
	 * ヘッダー（3行目-4行目）
	 * @param sheet
	 * @param fnname
	 * @return
	 */
	protected HSSFSheet headertile3_4(HSSFSheet sheet, String fnname) {

		CellRangeAddress system_region = new CellRangeAddress(2, 3, 0, 14);
		CellRangeAddress subsystem_region = new CellRangeAddress(2, 3, 15, 29);
		CellRangeAddress function_region = new CellRangeAddress(2, 3, 30, 43);
		CellRangeAddress createuser_region = new CellRangeAddress(2, 2, 44, 53);
		CellRangeAddress createdate_region = new CellRangeAddress(3, 3, 44, 53);
		CellRangeAddress updateuser_region = new CellRangeAddress(2, 2, 54, 63);
		CellRangeAddress updatedate_region = new CellRangeAddress(3, 3, 54, 63);

		sheet.addMergedRegion(system_region);
		sheet.addMergedRegion(subsystem_region);
		sheet.addMergedRegion(function_region);
		sheet.addMergedRegion(createuser_region);
		sheet.addMergedRegion(createdate_region);
		sheet.addMergedRegion(updateuser_region);
		sheet.addMergedRegion(updatedate_region);

		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		HSSFCellStyle style1 = writer.wb.createCellStyle();
		style1.cloneStyleFrom(contents_center_style);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style1);

		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(contents_center_style);
		}
		cell = row.getCell(30);
		cell.setCellValue(fnname);
		cell = row.getCell(44);
		cell.setCellValue("テラスカイ");
		cell = row.getCell(54);
		cell.setCellValue("テラスカイ");

		cell = row.createCell(63);
		HSSFCellStyle style3 = writer.wb.createCellStyle();
		style3.cloneStyleFrom(contents_center_style);
		style3.setBorderRight(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style3);

		row = sheet.createRow(3);
		cell = row.createCell(0);
		HSSFCellStyle style4 = writer.wb.createCellStyle();
		style4.cloneStyleFrom(contents_center_style);
		style4.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		style4.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style4);

		HSSFCellStyle style5 = writer.wb.createCellStyle();
		style5.cloneStyleFrom(contents_center_style);
		style5.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style5);
		}

		cell = row.getCell(44);
		HSSFCellStyle style7 = cell.getCellStyle();
		CreationHelper createHelper = writer.wb.getCreationHelper();
		style7.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));
		cell.setCellValue(new Date());
		cell.setCellStyle(style7);
		cell = row.getCell(54);
		cell.setCellValue(new Date());
		cell.setCellStyle(style7);
		cell = row.createCell(63);
		HSSFCellStyle style6 = writer.wb.createCellStyle();
		style6.cloneStyleFrom(contents_center_style);
		style6.setBorderRight(HSSFCellStyle.BORDER_THICK);
		style6.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		cell.setCellStyle(style6);
		return sheet;
	}

	private void setTitlStyle() {
		HSSFFont titleFont = writer.wb.createFont();
		titleFont.setColor(HSSFColor.WHITE.index);
		titleFont.setFontName("メイリオ");

		title_style = writer.wb.createCellStyle();
		title_style.setFont(titleFont);
		title_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		title_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		title_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		title_style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		title_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		title_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		title_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		title_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	}

	private void setContentsStyle(HSSFFont contentsFont) {

		// 一般スタイル（左揃え）
		contents_border_top_left_bottom_right_style = writer.wb.createCellStyle();
		contents_border_top_left_bottom_right_style.setFont(contentsFont);
		contents_border_top_left_bottom_right_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_left_bottom_right_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_left_bottom_right_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_border_top_left_bottom_right_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contents_border_top_left_bottom_right_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contents_border_top_left_bottom_right_style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		contents_border_top_left_bottom_style = writer.wb.createCellStyle();
		contents_border_top_left_bottom_style.setFont(contentsFont);
		contents_border_top_left_bottom_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_left_bottom_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_left_bottom_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_border_top_left_bottom_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contents_border_top_left_bottom_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		contents_border_top_bottom_style = writer.wb.createCellStyle();
		contents_border_top_bottom_style.setFont(contentsFont);
		contents_border_top_bottom_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_bottom_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_bottom_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_border_top_bottom_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		contents_border_top_bottom_right_style = writer.wb.createCellStyle();
		contents_border_top_bottom_right_style.setFont(contentsFont);
		contents_border_top_bottom_right_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_bottom_right_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_bottom_right_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_border_top_bottom_right_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contents_border_top_bottom_right_style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		contents_border_top_left_style = writer.wb.createCellStyle();
		contents_border_top_left_style.setFont(contentsFont);
		contents_border_top_left_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_left_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_left_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_border_top_left_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		contents_border_top_style = writer.wb.createCellStyle();
		contents_border_top_style.setFont(contentsFont);
		contents_border_top_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		contents_border_top_right_style = writer.wb.createCellStyle();
		contents_border_top_right_style.setFont(contentsFont);
		contents_border_top_right_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_top_right_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_top_right_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contents_border_top_right_style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		contents_border_left_bottom_style = writer.wb.createCellStyle();
		contents_border_left_bottom_style.setFont(contentsFont);
		contents_border_left_bottom_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_left_bottom_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_left_bottom_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contents_border_left_bottom_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		contents_border_bottom_style = writer.wb.createCellStyle();
		contents_border_bottom_style.setFont(contentsFont);
		contents_border_bottom_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_bottom_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_bottom_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		contents_border_bottom_right_style = writer.wb.createCellStyle();
		contents_border_bottom_right_style.setFont(contentsFont);
		contents_border_bottom_right_style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		contents_border_bottom_right_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contents_border_bottom_right_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contents_border_bottom_right_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	}

	/**
	 * クローズ処理
	 */
	public void finish() {
		writer.finish();
	}
}
