package com.SFManagementAntTask.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tools.ant.BuildException;

public class ExcelWriter {

	private String filename;
	public HSSFWorkbook wb;
	private Integer ROW_LIMIT = 9999;

	public ExcelWriter(String filename, boolean overwriteflg) {
		String tfilename = "";
		if (overwriteflg) {
			tfilename = filename + "_template.xls";
		}
		if (!overwriteflg) {
			tfilename = filename + ".xls";
		}
		this.filename = filename;
		POIFSFileSystem filein;
		try {
			filein = new POIFSFileSystem(new FileInputStream(tfilename));
			wb = new HSSFWorkbook(filein);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BuildException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	public void finish() throws BuildException {
		try {
			wb.write(new FileOutputStream(filename + ".xls"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BuildException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}
}
