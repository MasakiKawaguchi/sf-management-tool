package com.SFManagementAntTask.tooling.dao;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.common.ConnectionUtil;

public class QueryDao {

	public QueryDao() {}

	public String getClassId(String classname) {
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("Name", "classname");
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS, cond);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		String id = "";
		for (Integer i = 0; i < list.length(); i++) {
			System.out.println(">>> content is " + ((JSONObject) list.get(i)).get("Id"));
			id = ((JSONObject) list.get(0)).getString("Id");
		}

		return id;
	}

	public String getApexTestQueueItem(String id) {
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("ApexClassId", "id");
		//String res = RestUtil.mekeGetUrl( "sobjects/ApexTestQueueItem/709100000009feRAAQ");
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_TEST_QUEUE_ITEM, cond);
		System.out.println(">>> content is " + res);
		return res;
	}

	public String getApexTestResult() {
		String res = "";
		//		String res = RestUtil.queryforGet(Constant.SESSION_ID, Constant.Q_APEX_TEST_RESULT);
		//		System.out.println(">>> Q_APEX_TEST_RESULT is " + res);

		res = ConnectionUtil.queryforGet(Const.Q_APEX_CODE_COVERAGE);
		System.out.println(">>> Q_APEX_CODE_COVERAGE is " + res);
		//		res = RestUtil.queryforGet(Constant.SESSION_ID, Constant.Q_APEX_LOG);
		//		System.out.println(">>> Q_APEX_LOG is " + res);
		return res;
	}

	public void getMetaData() {
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CLASS_MEMBER);
		res = ConnectionUtil.queryforGet(Const.Q_METADATA_CONTAINER);
		System.out.println(">>> content is " + res);
	}

	public static void describeApexTestResult() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexTestResult/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeApexTestQueueItem() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexTestQueueItem/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeApexResult() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexResult/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeApexExecutionOverlayAction() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexExecutionOverlayAction/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeApexExecutionOverlayResult() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexExecutionOverlayResult/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeContainerAsyncRequest() {
		String res = ConnectionUtil.queryforGet("sobjects/ContainerAsyncRequest/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeAsyncApexJob() {
		String res = ConnectionUtil.queryforGet("sobjects/AsyncApexJob/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describeApexOrgWideCoverage() {
		String res = ConnectionUtil.queryforGet("sobjects/ApexOrgWideCoverage/describe");
		System.out.println(">>> describe is " + res);
	}

	public static void describe() {
		String res = ConnectionUtil.queryforGet("");
		System.out.println(">>> describe is " + res);
	}

	public static void describeRuntestSynchronous() {
		String res = ConnectionUtil.queryforGet(Const.Q_RUNTEST_SYNCHRONOUS + "describe");
		System.out.println(">>> describe is " + res);
	}

}
