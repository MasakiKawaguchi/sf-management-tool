package com.SFManagementAntTask.tooling.dao;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.tooling.dao.model.ApexLog;

public class ApexLogDao {

	private final static Logger log = LoggerFactory.getLogger(ApexLogDao.class);

	public static Map<String, ApexLog> findMapApexLog() {
		Map<String, ApexLog> logmap = new HashMap<String, ApexLog>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_LOG);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			ApexLog dto = new ApexLog();
			dto.setId(((JSONObject) list.get(i)).getString("Id"));
			dto.setApplication(((JSONObject) list.get(i)).getString("Application"));
			dto.setDurationMilliseconds(((JSONObject) list.get(i)).getLong("DurationMilliseconds"));
			dto.setLocation(((JSONObject) list.get(i)).getString("Location"));
			dto.setLogLength(((JSONObject) list.get(i)).getInt("LogLength"));
			dto.setLogUserId(((JSONObject) list.get(i)).getString("LogUserId"));
			dto.setOperation(((JSONObject) list.get(i)).getString("Operation"));
			dto.setRequest(((JSONObject) list.get(i)).getString("Request"));
			dto.setStartTime(CommonUtil.toDate(((JSONObject) list.get(i)).getString("StartTime")));
			dto.setStatus(((JSONObject) list.get(i)).getString("Status"));
			logmap.put(dto.getId(), dto);
		}
		log.debug("[getApexLog] result size : " + logmap.size());
		return logmap;
	}
}
