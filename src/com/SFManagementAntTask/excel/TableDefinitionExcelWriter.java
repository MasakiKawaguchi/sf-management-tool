package com.SFManagementAntTask.excel;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
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

	public void finish() {
		super.writer.finish();
	}
}
