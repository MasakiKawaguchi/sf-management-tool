package com.SFManagementAntTask.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.excel.CoverageExcelWriter;

public class FileReader {

	private final static Logger log = LoggerFactory.getLogger(FileReader.class);

	public FileReader() {}

	public static CoverageExcelWriter writeSource(String dirname, String packagename, int classcnt, String filename, CoverageExcelWriter cobj) {

		File file = new File(dirname + "/" + packagename + "/" + filename);
		BufferedReader br = null;
		java.io.FileReader fr = null;
		try {
			fr = new java.io.FileReader(file);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		log.trace("[writeSource] " + filename.split("\\.")[0]);
		cobj.createSheet("" + classcnt, filename.split("\\.")[0]);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				cobj.writeSource(line);
			}
		} catch (IOException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		cobj.writeSourceEnd();

		//終了処理
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}
		return cobj;
	}

	private static int coverageClassCnt = 0;

	public static CoverageExcelWriter writeClasses(String dirname, String packagename, CoverageExcelWriter cobj) {

		File dir = new File(dirname + "/" + packagename);
		if (dir == null || dir.list() == null) {
			log.error("[search directory] not existing directory " + dirname + "/" + packagename);
			return null;
		}

		for (String filename : dir.list()) {
			if (ApexClassParser.isMatch(".*\\.cls", filename) || ApexClassParser.isMatch(".*\\.trigger", filename)) {
				writeSource(dirname, packagename, ++coverageClassCnt, filename, cobj);
			}
		}
		return cobj;
	}
}
