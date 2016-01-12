package com.SFManagementAntTask.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	private final static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	public static String getCurrentPath() {
		return System.getProperty("user.dir");
	}

	public static String getReportPath() {
		String path = "report";
		log.trace("Report path is " + path);
		return path;
	}

	public static String getXmlPath() {
		String path = getReportPath() + setStr() + "xml";
		log.trace("xml path is " + path);
		return path;
	}

	public static String getSrcPath() {
		//String path = getCurrentPath() + setStr() + "src";
		String path = "src";
		log.trace("source path is " + path);
		return path;
	}

	public static String setStr() {
		String sepStr = "/";
		String osname = System.getProperty("os.name");
		if (osname.indexOf("Windows") >= 0) {
			sepStr = "\\";
		}
		return sepStr;
	}

	public static Date toDate(String timestampStr) {
		try {
			return new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(timestampStr.substring(0, 19).replace("T", ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
