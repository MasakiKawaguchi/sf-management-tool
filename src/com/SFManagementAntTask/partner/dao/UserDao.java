package com.SFManagementAntTask.partner.dao;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.ws.ConnectionException;

public class UserDao {

	public UserDao() {}

	public void findUsers() {
		DescribeSObjectResult res = null;
		try {
			res = ConnectionUtil.connectPartner().describeSObject("Account");
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.getLabel();
	}
}
