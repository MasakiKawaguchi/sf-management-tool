package com.SFManagementAntTask.partner.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.partner.dao.model.CustomField;
import com.SFManagementAntTask.partner.dao.model.CustomObject;
import com.SFManagementAntTask.partner.dao.model.Relationship;
import com.sforce.soap.partner.ChildRelationship;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PicklistEntry;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class PartnerDao {

	private final static Logger log = LoggerFactory.getLogger(PartnerDao.class);

	public static void query(String queryStr) {

		QueryResult res = null;
		try {
			res = ConnectionUtil.connectPartner().query(queryStr);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		for (SObject sobj : res.getRecords()) {
			log.debug("" + sobj.getName());
		}
	}

	public static void describeSObject(String sobj) {

		DescribeSObjectResult res = null;
		try {
			res = ConnectionUtil.connectPartner().describeSObject(sobj);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug(res.getLabel());
		for (Field fobj : res.getFields()) {
			log.debug(fobj.getName());
			log.debug(fobj.getLabel());
			for (PicklistEntry pobj : fobj.getPicklistValues()) {
				log.debug(pobj.getLabel());
				log.debug(pobj.getValue());
			}
		}
	}

	/**
	 * 項目取得処理
	 * @param sobjlist オブジェクト名配列
	 */
	public static List<CustomObject> describeSObjects(List<CustomObject> sobjlist) {

		String[] sobjs = new String[sobjlist.size()];
		int i = 0;
		for (CustomObject cobj : sobjlist) {
			sobjs[i++] = cobj.getName();
		}
		DescribeSObjectResult[] reslist = null;
		try {
			reslist = ConnectionUtil.connectPartner().describeSObjects(sobjs);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (DescribeSObjectResult res : reslist) {
			CustomObject cobj = null;
			for (CustomObject tcobj : sobjlist) {
				if (StringUtils.equals(tcobj.getName(), res.getName())) {
					cobj = tcobj;
					cobj.setName(res.getName());
					cobj.setLabel(res.getLabel());
					//res.getCustom();
					//res.getCreateable();
					//res.getUpdateable();
					//res.getDeletable();
					for (Field field : res.getFields()) {
						CustomField fobj = new CustomField();
						fobj.setName(field.getName());
						fobj.setLabel(field.getLabel());
						//				for (PicklistEntry pobj : fobj.getPicklistValues()) {
						//					log.debug(pobj.getLabel());
						//					log.debug(pobj.getValue());
						//				}
						cobj.addFields(fobj);
					}
					for (ChildRelationship crs : res.getChildRelationships()) {
						Relationship fobj = new Relationship();
						fobj.setName(crs.getField());
						fobj.setRelationshipName(crs.getRelationshipName());
						fobj.setChildSObject(crs.getChildSObject());
						log.debug("getChildSObject:" + crs.getChildSObject()); //TopicAssignment
						log.debug("getField:" + crs.getField()); //EntityId
						//log.debug("getJunctionIdListName:" + crs.getJunctionIdListName()); //null
						log.debug("getRelationshipName:" + crs.getRelationshipName()); //TopicAssignments
						//log.debug("getCascadeDelete:" + crs.getCascadeDelete()); //true
						//log.debug("getDeprecatedAndHidden:" + crs.getDeprecatedAndHidden()); //false
						//log.debug("getRestrictedDelete:" + crs.getRestrictedDelete()); //false
						//						for (String jrt : crs.getJunctionReferenceTo()) {
						//							log.debug("getJunctionReferenceTo:" + jrt);
						//						}
						cobj.addChildRelationships(fobj);
					}
				}
			}
		}
		return sobjlist;
	}

	public static void queryUser() {

		QueryResult res = null;
		try {
			res = ConnectionUtil.connectPartner().query("Select Id,Name,ProfileId From User");
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (SObject obj : res.getRecords()) {
			log.debug(obj.getType());
			log.debug(obj.getId());
			log.debug(obj.getField("Name").toString());
			log.debug(obj.getField("ProfileId").toString());
		}
	}

	public static List<CustomObject> describeGlobal() {

		List<CustomObject> cobjlist = new ArrayList<CustomObject>();
		DescribeGlobalResult res = null;
		try {
			res = ConnectionUtil.connectPartner().describeGlobal();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (DescribeGlobalSObjectResult sres : res.getSobjects()) {
			if (!sres.isCustom() && !StringUtils.equals(sres.getName(), "User")) {
				continue;
			}
			CustomObject cobj = new CustomObject();
			cobj.setLabel(sres.getLabel());
			cobj.setName(sres.getName());
			//System.out.println("SObject:" + sres.getName());
			//System.out.println(sres.getKeyPrefix()); // null
			//System.out.println(sres.getCustom()); // false
			//System.out.println(sres.getLabelPlural()); // 参加行動リレーション
			//System.out.println(sres.getQueryable()); //true
			cobjlist.add(cobj);
		}
		return cobjlist;
	}
}
