package com.SFManagementAntTask.tooling.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.ISFDto;
import com.sforce.soap.tooling.ApexClassMember;
import com.sforce.soap.tooling.Constructor;
import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.Position;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.SObject;
import com.sforce.soap.tooling.SymbolTable;
import com.sforce.ws.ConnectionException;

public class ApexClassDao {

	private final static Logger log = LoggerFactory.getLogger(ApexClassDao.class);

	/**
	 * Apexクラスのメタ情報取得処理
	 * @return Apexメタ情報
	 */
	public static List<CoverageClass> findApexDetail() {
		String query = new StringBuffer()
		        .append("SELECT ")
		        .append(" id, Name, NamespacePrefix, ApiVersion, Status, BodyCrc, IsValid, Body,")
		        .append(" LengthWithoutComments, CreatedDate, CreatedById, LastModifiedDate, LastModifiedById,")
		        .append(" SystemModstamp, SymbolTable")
		        .append(" FROM ApexClass").toString();
		QueryResult res = null;
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("[findApexDetail]", e);
			new BuildException(e);
		}
		log.debug(">>>" + res.getEntityTypeName());
		for (SObject obj : res.getRecords()) {
			com.sforce.soap.tooling.ApexClass aobj = (com.sforce.soap.tooling.ApexClass) obj;
			SymbolTable st = aobj.getSymbolTable();
			log.debug("[Name] " + aobj.getName());
			for (Constructor cobj : st.getConstructors()) {
				Position pobj = cobj.getLocation();
				log.debug("[Constructor:Line] " + pobj.getLine());
			}
			for (SymbolTable iobj : st.getInnerClasses()) {
				//log.debug("[InnerClasses:Line] " + iobj);
			}
			for (Method mobj : st.getMethods()) {
				Position pobj = mobj.getLocation();
				log.debug("[Method:Line] " + pobj.getLine());
			}
		}
		return null;
	}

	public static List<CoverageClass> parseApexClass() {
		File file = new File("meta/classes/ChangePasswordController.cls");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		String line;
		int count = 0;
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(++count + "行目：" + line);
			}
		} catch (IOException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		//終了処理
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		return null;
	}

	/**
	 * クラス名指定ApexTestClass取得処理
	 * @param classlist 取得クラス名
	 * @return クラス情報リスト
	 */
	public static List<ISFDto> findApexTestClassbyClassName(List<String> classlist) {
		List<ISFDto> dtolist = new ArrayList<ISFDto>();
		Map<String, ISFDto> dtomap = new HashMap<String, ISFDto>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		Boolean flg = true;
		for (Integer i = 0; i < list.length(); i++) {
			CoverageClass dto = new CoverageClass();
			dto.setId(((JSONObject) list.get(i)).getString("Id"));
			dto.setName(((JSONObject) list.get(i)).getString("Name"));
			dto.setNamespacePrefix(((JSONObject) list.get(i)).getString("NamespacePrefix"));
			dto.setStatus(((JSONObject) list.get(i)).getString("Status"));
			dto.setIsTest(isTest(((JSONObject) list.get(i)).getString("Body")));
			log.trace("[findTestApexTestClass] dto : " + dto);
			if (StringUtils.isNotBlank(dto.getNamespacePrefix()) || !dto.getIsTest()) {
				continue;
			}
			if (flg) {
				flg = false;
			}
			dtomap.put(dto.getName(), dto);
		}
		for (String classname : classlist) {
			CoverageClass dto = (CoverageClass) dtomap.get(classname);
			dtolist.add(dto);
		}
		log.debug("[findTestApexTestClass] result list size : " + dtolist.size());
		return dtolist;
	}

	/**
	 * ローカルApexTestClass取得処理
	 * @return クラス情報リスト
	 */
	public static List<ISFDto> findApexTestClassbyLocal() {
		List<ISFDto> dtolist = new ArrayList<ISFDto>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		Boolean flg = true;
		for (Integer i = 0; i < list.length(); i++) {
			CoverageClass dto = new CoverageClass();
			dto.setId(((JSONObject) list.get(i)).getString("Id"));
			dto.setName(((JSONObject) list.get(i)).getString("Name"));
			dto.setNamespacePrefix(((JSONObject) list.get(i)).getString("NamespacePrefix"));
			dto.setStatus(((JSONObject) list.get(i)).getString("Status"));
			dto.setIsTest(isTest(((JSONObject) list.get(i)).getString("Body")));
			log.trace("findApexTestClass find meta data is " + dto);
			if (StringUtils.isNotBlank(dto.getNamespacePrefix()) || !dto.getIsTest()) {
				continue;
			}
			if (flg) {
				flg = false;
			}
			dtolist.add(dto);
		}
		log.debug("findApexTestClass result list size is " + dtolist.size());
		return dtolist;
	}

	private static Boolean isTest(String body) {
		if (StringUtils.indexOfAny(body, " testMethod ", " testmethod ", "@isTest", "@IsTest") >= 0) {
			return true;
		}
		return false;
	}

	public static Map<String, CoverageClass> findMapApexClass() {
		Map<String, CoverageClass> dtomap = new HashMap<String, CoverageClass>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			CoverageClass dto = new CoverageClass();
			dto.setId(((JSONObject) list.get(i)).getString("Id"));
			dto.setName(((JSONObject) list.get(i)).getString("Name"));
			dto.setNamespacePrefix(((JSONObject) list.get(i)).getString("NamespacePrefix"));
			dto.setStatus(((JSONObject) list.get(i)).getString("Status"));
			dtomap.put(dto.getId(), dto);
		}
		log.trace(">>> findMapApexClass..." + res);
		return dtomap;
	}

	public static void queryApexClassMember() {
		QueryResult res = null;
		try {
			res = ConnectionUtil.connectTooling().query("Select Id,FullName,LastModifiedDate From ApexClassMember");
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			ApexClassMember mobj = (ApexClassMember) obj;
			System.out.println(mobj.getId());
			System.out.println(mobj.getFullName());
			Date date = mobj.getLastModifiedDate().getTime();
			System.out.println(format.format(date));
		}
	}

	public static void describe() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexClass/describe");
		log.debug("apexclass describe infomation is " + res);
	}
}
