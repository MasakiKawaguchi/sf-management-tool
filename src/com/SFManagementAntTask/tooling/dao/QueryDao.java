package com.SFManagementAntTask.tooling.dao;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.common.Const;

public class QueryDao {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(QueryDao.class);

	public QueryDao() {}

	public String getClassId(String classname) {
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("Name", "classname");
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS, cond);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		String id = "";
		for (Integer i = 0; i < list.length(); i++) {
			log.trace(">>> content is " + ((JSONObject) list.get(i)).get("Id"));
			id = ((JSONObject) list.get(0)).getString("Id");
		}

		return id;
	}

	public String getApexTestQueueItem(String id) {
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("ApexClassId", "id");
		//String res = RestUtil.mekeGetUrl( "sobjects/ApexTestQueueItem/709100000009feRAAQ");
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_TEST_QUEUE_ITEM, cond);
		log.debug(">>> content is " + res);
		return res;
	}

	public String getApexTestResult() {
		String res = "";
		//		String res = RestUtil.queryforGet(Constant.SESSION_ID, Constant.Q_APEX_TEST_RESULT);
		//		log.debug(">>> Q_APEX_TEST_RESULT is " + res);

		res = ConnectionUtil.queryforGet(Const.Q_APEX_CODE_COVERAGE);
		log.debug(">>> Q_APEX_CODE_COVERAGE is " + res);
		//		res = RestUtil.queryforGet(Constant.SESSION_ID, Constant.Q_APEX_LOG);
		//		log.debug(">>> Q_APEX_LOG is " + res);
		return res;
	}

	public void getMetaData() {
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS_MEMBER);
		res = ConnectionUtil.queryforGet(Const.Q_METADATA_CONTAINER);
		log.debug(">>> content is " + res);
	}

	public static void describeApexTestResult() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexTestResult/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeApexTestQueueItem() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexTestQueueItem/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeApexResult() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexResult/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeApexExecutionOverlayAction() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexExecutionOverlayAction/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeApexExecutionOverlayResult() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexExecutionOverlayResult/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeContainerAsyncRequest() {
		String res = ConnectionUtil.queryforGet("sobjects/ContainerAsyncRequest/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeAsyncApexJob() {
		String res = ConnectionUtil.queryforGet("sobjects/AsyncApexJob/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describeApexOrgWideCoverage() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexOrgWideCoverage/describe");
		log.debug(">>> describe is " + res);
	}

	public static void describe() {
		String res = ConnectionUtil.queryforGet("");
		log.debug(">>> describe is " + res);
	}

	public static void describeRuntestSynchronous() {
		String res = ConnectionUtil.queryforGet(Const.Q_RUNTEST_SYNCHRONOUS + "describe");
		log.debug(">>> describe is " + res);
	}

}
