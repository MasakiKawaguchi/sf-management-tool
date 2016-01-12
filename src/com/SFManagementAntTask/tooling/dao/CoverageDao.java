package com.SFManagementAntTask.tooling.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.ISFDto;
import com.SFManagementAntTask.tooling.dao.model.Line;
import com.SFManagementAntTask.tooling.dao.model.CoverageMethod;
import com.SFManagementAntTask.tooling.dao.model.Organization;

public class CoverageDao {

	private final static Logger log = LoggerFactory.getLogger(CoverageDao.class);

	public static ISFDto findApexOrgWideCoverage() {

		List<ISFDto> dtolist = new ArrayList<ISFDto>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_ORG_WIDE_COVERAGE);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			JSONObject tjson = (JSONObject) list.get(i);
			Organization dto = new Organization();
			dto.setId(tjson.getString("Id"));
			dto.setIsDeleted(tjson.getBoolean("IsDeleted"));
			dto.setCreatedDate(CommonUtil.toDate((String) tjson.getString("CreatedDate")));
			dto.setCreatedById(tjson.getString("CreatedById"));
			dto.setLastModifiedDate(CommonUtil.toDate((String) tjson.getString("LastModifiedDate")));
			dto.setLastModifiedById(tjson.getString("LastModifiedById"));
			dto.setSystemModstamp(CommonUtil.toDate((String) tjson.getString("SystemModstamp")));
			dto.setPercentCovered(tjson.getInt("PercentCovered"));
			log.trace("findCoverage find meta is " + dto);
			dtolist.add(dto);
		}
		log.debug("findApexOrgWideCoverage find result is " + dtolist.size());
		if (dtolist.size() == 0) {
			return null;
		}
		return dtolist.get(0);
	}

	/**
	 * テストメソッド毎のカバレッジ取得
	 * @return メソッドカバレッジ情報
	 */
	public static List<ISFDto> findApexCodeCoverage() {

		List<ISFDto> dtolist = new ArrayList<ISFDto>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CODE_COVERAGE);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			JSONObject tjson = (JSONObject) list.get(i);
			CoverageMethod dto = new CoverageMethod();
			dto.setId(tjson.getString("Id"));
			dto.setApexTestClassId(tjson.getString("ApexTestClassId"));
			dto.setName(tjson.getString("TestMethodName"));
			dto.setApexClassOrTriggerId(tjson.getString("ApexClassOrTriggerId"));
			dto.setNumLinesCovered(tjson.getInt("NumLinesCovered"));
			dto.setNumLinesUncovered(tjson.getInt("NumLinesUncovered"));
			JSONObject cjson = (JSONObject) tjson.get("Coverage");
			JSONArray clist = cjson.getJSONArray("coveredLines");
			List<Line> linelist = new ArrayList<Line>();
			for (Integer j = 0; j < clist.length(); j++) {
				Line ldto = new Line();
				ldto.setNum((Integer) clist.get(j));
				linelist.add(ldto);
			}
			dto.setCoveredLines(linelist);
			clist = cjson.getJSONArray("uncoveredLines");
			linelist = new ArrayList<Line>();
			for (Integer j = 0; j < clist.length(); j++) {
				Line ldto = new Line();
				ldto.setNum((Integer) clist.get(j));
				linelist.add(ldto);
			}
			dto.setUncoveredLines(linelist);
			log.trace("findCoverage find meta is " + dto);
			dtolist.add(dto);
		}
		log.debug("findApexCodeCoverage find result is " + dtolist.size());
		return dtolist;
	}

	/**
	 * クラス毎のカバレッジ情報
	 * @return
	 */
	public static List<ISFDto> findApexCodeCoverageAggregate() {

		List<ISFDto> dtolist = new ArrayList<ISFDto>();
		String res = ConnectionUtil.queryforGet(Const.Q_APEX_CODE_COVERAGE_AGGREGATE);
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			JSONObject tjson = (JSONObject) list.get(i);
			CoverageClass dto = new CoverageClass();
			dto.setId(tjson.getString("ApexClassOrTriggerId"));
			dto.setNumLinesCovered(tjson.getInt("NumLinesCovered"));
			//dto.setNumLinesCovered(tjson.getInt("NumLinesUncovered"));
			JSONObject cjson = (JSONObject) tjson.get("Coverage");
			JSONArray clist = cjson.getJSONArray("coveredLines");
			List<Line> linelist = new ArrayList<Line>();
			for (Integer j = 0; j < clist.length(); j++) {
				Line ldto = new Line();
				ldto.setNum((Integer) clist.get(j));
				linelist.add(ldto);
			}
			dto.setCoveredLines(linelist);
			clist = cjson.getJSONArray("uncoveredLines");
			linelist = new ArrayList<Line>();
			for (Integer j = 0; j < clist.length(); j++) {
				Line ldto = new Line();
				ldto.setNum((Integer) clist.get(j));
				linelist.add(ldto);
			}
			dto.setUncoveredLines(linelist);
			log.trace("findCoverage find meta is " + dto);
			dtolist.add(dto);
		}

		return dtolist;
	}
}
