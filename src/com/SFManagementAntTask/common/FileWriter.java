package com.SFManagementAntTask.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;

public class FileWriter {

	private PrintWriter pw;

	public FileWriter(String filename) throws BuildException {
		try {
			pw = new PrintWriter(new BufferedWriter(new java.io.FileWriter(new File(filename))));
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}

	public void write(String msg) throws BuildException {

		pw.println(msg);
	}

	public void writeXml(String msg, int indent) throws BuildException {
		if (indent == 0) {
			pw.println(msg);
			return;
		}
		pw.println(StringUtils.leftPad(" ", indent) + msg);
	}

	public void finish() {
		pw.close();
	}
}
