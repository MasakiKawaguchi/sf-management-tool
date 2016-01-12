package com.SFManagementAntTask.metadata.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.tooling.dao.model.ISFDto;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.ListMetadataQuery;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.ReadResult;
import com.sforce.ws.ConnectionException;

public class SObjectDao {

	private final static Logger log = LoggerFactory.getLogger(SObjectDao.class);

	/** デフォルトコンストラクタ */
	public SObjectDao() {

	}

	public static ReadResult readMetadata(String[] classnames) {
		log.debug("readMetadata start...");
		String[] classname = { "TestDummy__c" };
		ReadResult res = null;
		try {
			res = ConnectionUtil.connectMetadata().readMetadata("CustomObject", classname);
		} catch (ConnectionException e) {
			log.error("[execute] ", e);
			throw new BuildException(e);
		}
		for (Metadata mdata : res.getRecords()) {
			mdata.getFullName();
		}
		log.debug("readMetadata end...");
		return res;
	}

	public static List<ISFDto> listMetadata(List<String> typelist) {

		List<ISFDto> reslist = new ArrayList<ISFDto>();
		ListMetadataQuery[] lmtq = new ListMetadataQuery[typelist.size()];
		for (Integer i = 0; i < typelist.size(); i++) {
			String type = typelist.get(i);
			ListMetadataQuery query = new ListMetadataQuery();
			query.setType(type);
			lmtq[i] = query;
		}
		FileProperties[] lmr = null;
		double asOfVersion = 35.0;
		try {
			// Assuming that the SOAP binding has already been established.
			lmr = ConnectionUtil.connectMetadata().listMetadata(lmtq, asOfVersion);
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		if (lmr == null) {
			return null;
		}
		for (FileProperties n : lmr) {
			//			Class<?> classins = Class.forName(n.getType());
			//			classins.newInstance();
			log.debug("Component fullName: " + n.getFullName());
			log.debug("Component type: " + n.getType());
			log.debug(n.getNamespacePrefix()); // cuda_signnow
			log.debug("" + n.getManageableState()); //ApexClass
		}
		return reslist;
	}
}
