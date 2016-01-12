package com.SFManagementAntTask.partner.dao;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.ws.ConnectionException;

public class ProfileDao {

	private final static Logger log = LoggerFactory.getLogger(ProfileDao.class);

	public void describeProfile(MetadataConnection con) {
		DescribeSObjectResult res = null;
		try {
			res = ConnectionUtil.connectPartner().describeSObject("Profile");
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		for (Field field : res.getFields()) {
			log.debug("" + field.getName());
		}
	}
}
