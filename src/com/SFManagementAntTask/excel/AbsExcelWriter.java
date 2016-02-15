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
	protected HSSFCellStyle title_boldborder_top_left_style;
	protected HSSFCellStyle title_boldborder_top_style;
	protected HSSFCellStyle title_boldborder_top_right_style;
	protected HSSFCellStyle title_boldborder_left_style;
	protected HSSFCellStyle title_boldborder_right_style;
	/** 一般スタイル（中央揃え） */
	protected HSSFCellStyle contents_center_style;
	protected HSSFCellStyle contents_center_date_style;
	protected HSSFCellStyle contents_boldborder_left_style;
	protected HSSFCellStyle contents_boldborder_right_style;
	protected HSSFCellStyle contents_boldborder_left_bottom_style;
	protected HSSFCellStyle contents_boldborder_bottom_style;
	protected HSSFCellStyle contents_boldborder_bottom_date_style;
	protected HSSFCellStyle contents_boldborder_bottom_right_style;
	protected HSSFCellStyle contents_dotborder_right_style;
	protected HSSFCellStyle contents_dotborder_left_style;
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
		setContentsStyle();
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
		margedRegion(sheet, 0, 1, 0, 14);
		margedRegion(sheet, 0, 1, 15, 29);
		margedRegion(sheet, 0, 1, 30, 43);
		margedRegion(sheet, 0, 0, 44, 53);
		margedRegion(sheet, 1, 1, 44, 53);
		margedRegion(sheet, 0, 0, 54, 63);
		margedRegion(sheet, 1, 1, 54, 63);

		writer.wb.getFontAt((short) 0).setFontName("メイリオ");

		HSSFRow row = getRow(sheet, 0);
		HSSFCell cell = getCell(row, 0);
		cell.setCellStyle(title_boldborder_top_left_style);
		for (int i = 1; i < 63; i++) {
			cell = getCell(row, i);
			cell.setCellStyle(title_boldborder_top_style);
		}
		cell = getCell(row, 63);
		cell.setCellStyle(title_boldborder_top_right_style);

		cell = getCell(row, 0);
		cell.setCellValue("システム名");
		cell = getCell(row, 15);
		cell.setCellValue("サブシステム名");
		cell = getCell(row, 30);
		cell.setCellValue("機能名");
		cell = getCell(row, 44);
		cell.setCellValue("作成者");
		cell = getCell(row, 54);
		cell.setCellValue("更新者");

		row = getRow(sheet, 1);
		cell = row.createCell(0);
		cell.setCellStyle(title_boldborder_left_style);

		for (int i = 1; i < 63; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(title_style);
		}
		cell = row.createCell(63);
		cell.setCellStyle(title_boldborder_right_style);

		cell = getCell(row, 44);
		cell.setCellValue("作成日");
		cell = getCell(row, 54);
		cell.setCellValue("更新日");

		return sheet;
	}

	/**
	 * ヘッダー（3行目-4行目）
	 * @param sheet
	 * @param fnname
	 * @return
	 */
	protected HSSFSheet headertile3_4(HSSFSheet sheet, String fnname) {

		HSSFRow row = getRow(sheet, 2);
		HSSFCell cell = null;
		cell = getCell(row, 30);
		cell.setCellValue(fnname);
		cell = getCell(row, 44);
		cell.setCellValue("テラスカイ");
		cell = getCell(row, 54);
		cell.setCellValue("テラスカイ");
		row = getRow(sheet, 3);
		cell = getCell(row, 44);
		cell.setCellValue(new Date());
		//cell.setCellStyle(contents_center_date_style);
		cell = getCell(row, 54);
		cell.setCellValue(new Date());
		//cell.setCellStyle(contents_center_date_style);
		return sheet;
	}

	/**
	 * タイトルセルスタイル初期化
	 */
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

		title_boldborder_top_left_style = writer.wb.createCellStyle();
		title_boldborder_top_left_style.cloneStyleFrom(title_style);
		title_boldborder_top_left_style.setBorderTop(HSSFCellStyle.BORDER_THICK);
		title_boldborder_top_left_style.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		title_boldborder_top_style = writer.wb.createCellStyle();
		title_boldborder_top_style.cloneStyleFrom(title_style);
		title_boldborder_top_style.setBorderTop(HSSFCellStyle.BORDER_THICK);

		title_boldborder_top_right_style = writer.wb.createCellStyle();
		title_boldborder_top_right_style.cloneStyleFrom(title_style);
		title_boldborder_top_right_style.setBorderTop(HSSFCellStyle.BORDER_THICK);
		title_boldborder_top_right_style.setBorderRight(HSSFCellStyle.BORDER_THICK);

		title_boldborder_left_style = writer.wb.createCellStyle();
		title_boldborder_left_style.cloneStyleFrom(title_style);
		title_boldborder_left_style.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		title_boldborder_right_style = writer.wb.createCellStyle();
		title_boldborder_right_style.cloneStyleFrom(title_style);
		title_boldborder_right_style.setBorderRight(HSSFCellStyle.BORDER_THICK);

	}

	/**
	 * コンテンツセルスタイル初期化
	 */
	private void setContentsStyle() {

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

		contents_center_date_style = writer.wb.createCellStyle();
		contents_center_date_style.cloneStyleFrom(contents_center_style);
		CreationHelper createHelper = writer.wb.getCreationHelper();
		contents_center_date_style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

		contents_boldborder_left_style = writer.wb.createCellStyle();
		contents_boldborder_left_style.cloneStyleFrom(contents_center_style);
		contents_boldborder_left_style.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		contents_boldborder_right_style = writer.wb.createCellStyle();
		contents_boldborder_right_style.cloneStyleFrom(contents_center_style);
		contents_boldborder_right_style.setBorderRight(HSSFCellStyle.BORDER_THICK);

		contents_boldborder_left_bottom_style = writer.wb.createCellStyle();
		contents_boldborder_left_bottom_style.cloneStyleFrom(contents_center_style);
		contents_boldborder_left_bottom_style.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		contents_boldborder_left_bottom_style.setBorderBottom(HSSFCellStyle.BORDER_THICK);

		contents_boldborder_bottom_style = writer.wb.createCellStyle();
		contents_boldborder_bottom_style.cloneStyleFrom(contents_center_style);
		contents_boldborder_bottom_style.setBorderBottom(HSSFCellStyle.BORDER_THICK);

		contents_boldborder_bottom_date_style = writer.wb.createCellStyle();
		contents_boldborder_bottom_date_style.cloneStyleFrom(contents_center_style);
		contents_boldborder_bottom_date_style.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		contents_boldborder_bottom_date_style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

		contents_boldborder_bottom_right_style = writer.wb.createCellStyle();
		contents_boldborder_bottom_right_style.cloneStyleFrom(contents_center_style);
		contents_boldborder_bottom_right_style.setBorderRight(HSSFCellStyle.BORDER_THICK);
		contents_boldborder_bottom_right_style.setBorderBottom(HSSFCellStyle.BORDER_THICK);

		contents_dotborder_right_style = writer.wb.createCellStyle();
		contents_dotborder_right_style.cloneStyleFrom(contents_center_style);
		contents_dotborder_right_style.setBorderRight(HSSFCellStyle.BORDER_DOTTED);

		contents_dotborder_left_style = writer.wb.createCellStyle();
		contents_dotborder_left_style.cloneStyleFrom(contents_center_style);
		contents_dotborder_left_style.setBorderLeft(HSSFCellStyle.BORDER_NONE);

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
		contents_border_top_left_bottom_style.cloneStyleFrom(contents_border_top_left_bottom_right_style);
		contents_border_top_left_bottom_style.setBorderRight(HSSFCellStyle.BORDER_NONE);

		contents_border_top_bottom_style = writer.wb.createCellStyle();
		contents_border_top_bottom_style.cloneStyleFrom(contents_border_top_left_bottom_right_style);
		contents_border_top_bottom_style.setBorderRight(HSSFCellStyle.BORDER_NONE);
		contents_border_top_bottom_style.setBorderLeft(HSSFCellStyle.BORDER_NONE);

		contents_border_top_bottom_right_style = writer.wb.createCellStyle();
		contents_border_top_bottom_right_style.cloneStyleFrom(contents_border_top_left_bottom_right_style);
		contents_border_top_bottom_right_style.setBorderLeft(HSSFCellStyle.BORDER_NONE);

		contents_border_top_left_style = writer.wb.createCellStyle();
		contents_border_top_left_style.cloneStyleFrom(contents_border_top_left_bottom_right_style);
		contents_border_top_left_style.setBorderBottom(HSSFCellStyle.BORDER_NONE);
		contents_border_top_left_style.setBorderRight(HSSFCellStyle.BORDER_NONE);

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
	 * セルマージメソッド
	 * @param sheet シート
	 * @param startrow 開始行
	 * @param endrow 終了行
	 * @param startcol 開始列
	 * @param endcol 終了列
	 */
	protected void margedRegion(HSSFSheet sheet, int startrow, int endrow, int startcol, int endcol) {
		CellRangeAddress target_region = new CellRangeAddress(startrow, endrow, startcol, endcol);
		sheet.addMergedRegion(target_region);
	}

	/**
	 * 行取得処理
	 * @param sheet シート
	 * @param rownum 行
	 * @return 行情報
	 */
	protected HSSFRow getRow(HSSFSheet sheet, int rownum) {
		HSSFRow row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		return row;
	}

	/**
	 * 行取得処理
	 * @param sheet シート
	 * @param rownum 行
	 * @return 行情報
	 */
	protected HSSFCell getCell(HSSFRow row, int cellnum) {
		HSSFCell cell = row.getCell(cellnum);
		if (cell == null) {
			cell = row.createCell(cellnum);
		}
		return cell;
	}

	/**
	 * クローズ処理
	 */
	public void finish() {
		writer.finish();
	}
}
