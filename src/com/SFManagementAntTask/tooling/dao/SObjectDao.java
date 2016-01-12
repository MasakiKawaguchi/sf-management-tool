package com.SFManagementAntTask.tooling.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.sforce.soap.tooling.CustomField;
import com.sforce.soap.tooling.CustomObject;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.SObject;
import com.sforce.ws.ConnectionException;

public class SObjectDao {

	private final static Logger log = LoggerFactory.getLogger(SObjectDao.class);

	public static void querySObjectField() {
		QueryResult res = null;
		try {
			res = ConnectionUtil.connectTooling().query("Select EntityDefinitionId,DeveloperName,CreatedDate From CustomField");
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			CustomField cobj = (CustomField) obj;
			System.out.println(cobj.getId());
			System.out.println(cobj.getEntityDefinitionId());
			System.out.println(cobj.getFullName());
			System.out.println(cobj.getDeveloperName());
			if (cobj.getCreatedDate() != null) {
				Date date = cobj.getCreatedDate().getTime();
				System.out.println(format.format(date));
			}
		}
	}

	public static void querySObject() {
		QueryResult res = null;
		try {
			res = ConnectionUtil.connectTooling().query("Select DeveloperName From CustomObject");
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			CustomObject mobj = (CustomObject) obj;
			System.out.println(mobj.getId());
			System.out.println(mobj.getDeveloperName());
			if (mobj.getCreatedDate() != null) {
				Date date = mobj.getCreatedDate().getTime();
				System.out.println(format.format(date));
			}
			System.out.println(mobj.getFieldsToNull());
		}
	}
}
