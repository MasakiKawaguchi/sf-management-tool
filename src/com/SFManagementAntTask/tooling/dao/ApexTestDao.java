package com.SFManagementAntTask.tooling.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ApexClassParser;
import com.SFManagementAntTask.common.CommonUtil;
import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.tooling.dao.model.ApexLog;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.ISFDto;
import com.SFManagementAntTask.tooling.dao.model.Line;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.dao.model.Package;
import com.SFManagementAntTask.tooling.dao.model.UnitTestClass;
import com.SFManagementAntTask.tooling.dao.model.UnitTestMethod;
import com.sforce.soap.tooling.CodeCoverageResult;
import com.sforce.soap.tooling.CodeCoverageWarning;
import com.sforce.soap.tooling.CodeLocation;
import com.sforce.soap.tooling.RunTestFailure;
import com.sforce.soap.tooling.RunTestSuccess;
import com.sforce.soap.tooling.RunTestsRequest;
import com.sforce.soap.tooling.RunTestsResult;
import com.sforce.ws.ConnectionException;

/**
 * ApexTestデータアクセスクラス
 * @author mkawaguchi
 */
public class ApexTestDao {

	/** ログクラス */
	private final static Logger log = LoggerFactory.getLogger(ApexTestDao.class);

	/**
	 * 全テスト実行処理
	 * @return 組織情報リスト
	 */
	public static Organization runTestsbyLocalSoap() {

		RunTestsRequest request = new RunTestsRequest();
		request.setAllTests(false);
		//request.setNamespace(null);
		//request.setPackages(new String[] { "Classes" });
		//request.setClasses(classes);
		RunTestsResult res = null;
		try {
			res = ConnectionUtil.connectTooling().runTests(request);
		} catch (ConnectionException e) {
			log.error("[runTestsbyLocal]", e);
			new BuildException(e);
		}
		//log.debug(res.toString());
		Organization oobj = new Organization();
		oobj = editUnitTestResult(res, oobj);
		oobj = editCoverageResult(res, oobj);
		return oobj;
	}

	/**
	 * カバレッジ情報編集処理
	 * @param res テスト結果情報
	 * @param oobj 組織情報
	 * @return 組織情報
	 */
	private static Organization editCoverageResult(RunTestsResult res, Organization oobj) {
		Map<String, CoverageClass> amap = new HashMap<String, CoverageClass>();
		amap = ApexClassParser.parseClasses(Const.SRC_ROOT, "classes", amap);
		amap = ApexClassParser.parseClasses(Const.SRC_ROOT, "triggers", amap);
		oobj.setSourcepath(Const.SRC_ROOT);
		for (CodeCoverageResult ccr : res.getCodeCoverage()) {
			CoverageClass acobj = amap.get(ccr.getName());
			if (acobj == null) {
				log.error("[Get Meta Apex] not existing " + ccr.getName());
				continue;
			}
			acobj.setId(ccr.getId());
			acobj.setName(ccr.getName());
			acobj.setNamespacePrefix(ccr.getNamespace());
			acobj.setNumLocations(ccr.getNumLocations());
			acobj.setNumLinesCovered(ccr.getNumLocations() - ccr.getNumLocationsNotCovered());
			for (CodeLocation cl : ccr.getLocationsNotCovered()) {
				for (Line lobj : acobj.getCoveredLines()) {
					if (lobj.getNum() == cl.getLine()) {
						lobj.setHits(0);
					}
				}
			}
			Package pobj = oobj.getPackage(ccr.getType());
			if (pobj == null) {
				pobj = new Package();
				pobj.setName("triggers");
				oobj.addPackagelist(pobj);
			}
			pobj.addCVGClasslist(acobj);
		}
		for (CodeCoverageWarning ccw : res.getCodeCoverageWarnings()) {
			//log.debug("[CodeCoverageWarning] " + ccw.getId());
			//log.debug("[CodeCoverageWarning] " + ccw.getName());
			//log.debug("[CodeCoverageWarning] " + ccw.getNamespace());
			log.debug("[CodeCoverageWarning] " + ccw.getMessage());
			oobj.addCodeCoverageWarnings(ccw.getMessage());
		}
		return oobj;
	}

	/**
	 * ユニットテスト結果格納処理
	 * @param res ユニットテスト結果
	 * @return 組織情報
	 */
	private static Organization editUnitTestResult(RunTestsResult res, Organization oobj) {
		Map<String, UnitTestClass> cobjmap = new HashMap<String, UnitTestClass>();
		for (RunTestFailure fobj : res.getFailures()) {
			UnitTestClass aobj = cobjmap.get(fobj.getName());
			if (aobj == null) {
				aobj = new UnitTestClass();
				aobj.setName(fobj.getName());
			}
			if (StringUtils.isNoneBlank(fobj.getMethodName())) {
				UnitTestMethod mobj = new UnitTestMethod();
				mobj.setId(fobj.getId());
				mobj.setMethodName(fobj.getMethodName());
				mobj.setName(fobj.getName());
				mobj.setAroundTime(fobj.getTime());
				mobj.setMessage(fobj.getMessage());
				mobj.setStackTrace(fobj.getStackTrace());
				//fobj.getType();
				mobj.setSeeAllData(fobj.getSeeAllData());
				mobj.setOutcome("×");
				aobj.addMethodlist(mobj);
				aobj.setErrorCnt(aobj.getErrorCnt() + 1);
			}
			if (StringUtils.isBlank(fobj.getMethodName())) {
				aobj.setFailurCnt(aobj.getFailurCnt() + 1);
			}
			log.trace("[getNamespace] is " + fobj.getNamespace());
			aobj.setTestCnt(aobj.getTestCnt() + 1);
			if (StringUtils.isBlank(fobj.getMethodName())) {
				aobj.setSystemout(fobj.getMessage());
				//aobj.setSystemerr(fobj.getMessage());
			}
			cobjmap.put(fobj.getName(), aobj);
		}
		for (RunTestSuccess sobj : res.getSuccesses()) {
			UnitTestMethod mobj = new UnitTestMethod();
			mobj.setId(sobj.getId());
			mobj.setMethodName(sobj.getMethodName());
			mobj.setName(sobj.getName());
			mobj.setAroundTime(sobj.getTime());
			mobj.setOutcome("○");
			//sobj.getNamespace();
			//fobj.getType();
			//fobj.getSeeAllData();
			UnitTestClass aobj = cobjmap.get(sobj.getName());
			if (aobj == null) {
				aobj = new UnitTestClass();
			}
			log.trace("[getNamespace] is " + sobj.getNamespace());
			aobj.setName(sobj.getName());
			aobj.addMethodlist(mobj);
			aobj.setTestCnt(aobj.getTestCnt() + 1);
			cobjmap.put(sobj.getName(), aobj);
		}
		Package pobj = new Package();
		pobj.setName("classes");
		for (UnitTestClass aobj : cobjmap.values()) {
			pobj.addUTClasslist(aobj);
		}
		log.debug("[getClasslist] class list is " + pobj.getCVGClasslist().size());
		oobj.addPackagelist(pobj);
		return oobj;
	}

	/**
	 * テスト非同期実行処理
	 * @param param テストクラスID
	 * @return ジョブID
	 */
	public String runTestsAsynchronous(String param) {

		String res = null;
		try {
			res = ConnectionUtil.connectTooling().runTestsAsynchronous(param);
		} catch (ConnectionException e) {
			log.error("runTestsAsynchronous", e);
			new BuildException(e);
		}
		log.debug("[runTestsAsynchronous] " + res.toString());
		return res;
	}

	/**
	 * 非同期テスト実行クラス（REST）
	 * @param dtolist テストクラスIDリスト
	 * @return ジョブID
	 */
	public static String runTestsAsynchronousREST(List<ISFDto> dtolist) {
		String param = "";
		Boolean flg = false;
		for (ISFDto dto : dtolist) {
			CoverageClass adto = (CoverageClass) dto;
			if (flg) {
				param = param + ",";
			}
			param = param + adto.getId();
			flg = true;
		}
		String res = ConnectionUtil.queryforGet(Const.Q_RUNTEST_ASYNCHRONOUS + param);
		if (res == null) {
			return null;
		}
		String id = StringUtils.replace(res, "\"", "");
		log.debug("[getRunTest] asynApexJobId : " + id);
		return id;
	}

	/**
	 * ジョブ完了チェック処理
	 * @param asynApexJobId ジョブID
	 * @return 完了 true | 未完了 false
	 */
	public static Boolean isCompleteAsyncApexJob(String asynApexJobId) {
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("id", asynApexJobId);
		String res = ConnectionUtil.queryforGet(Const.Q_ASYNC_APEX_JOB, cond);
		if (res == null) {
			return null;
		}
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		String status = "";
		for (Integer i = 0; i < list.length(); i++) {
			status = ((JSONObject) list.get(i)).getString("Status");
		}
		log.debug("[isCompleteAsyncApexJob] asynApexJobId : " + asynApexJobId + " , status : " + status);
		return !StringUtils.equals("Completed", status);
	}

	/**
	 * テスト結果情報取得処理(REST)
	 * @param asynApexJobId ジョブID
	 * @param odto 組織情報
	 * @return 組織情報
	 */
	public static Organization findApexTest(String asynApexJobId, Organization odto) {

		Map<String, UnitTestClass> adtomap = new HashMap<String, UnitTestClass>();
		List<UnitTestClass> adtolist = findApexTestQueueItembyAsynid(asynApexJobId);
		for (UnitTestClass adto : adtolist) {
			adtomap.put(adto.getId(), adto);
		}
		List<UnitTestMethod> mdtolis = findApexTestResultbyAsynid(asynApexJobId);
		Map<String, ApexLog> ldtomap = ApexLogDao.findMapApexLog();
		for (UnitTestMethod mdto : mdtolis) {
			UnitTestClass cdto = adtomap.get(mdto.getQueueItemId());
			if (cdto == null) {
				continue;
			}
			cdto.setBeforeStartDate(mdto.getTestTimestamp());
			cdto.setAfterEndDate(mdto.getTestTimestamp());
			ApexLog ldto = ldtomap.get(mdto.getApexLogId());
			mdto.setApexLog(ldto);
			cdto.addMethodlist(mdto);
		}
		List<UnitTestClass> rdtolist = new ArrayList<UnitTestClass>();
		for (UnitTestClass adto : adtomap.values()) {
			rdtolist.add(adto);
			odto.addMethodcnt(adto.getUTMethodlist().size());
		}
		Package pdto = new Package();
		pdto.setName("classes");
		pdto.setUTClasslist(rdtolist);
		odto.setClasscnt(rdtolist.size());
		odto.addPackagelist(pdto);
		log.debug("[findApexTest] asynApexJobId : " + asynApexJobId + " , result size : " + rdtolist.size());
		return odto;
	}

	/**
	 * テストクラス結果情報取得処理
	 * @param asynApexJobId ジョブID
	 * @return テストクラスリスト
	 */
	private static List<UnitTestClass> findApexTestQueueItembyAsynid(String asynApexJobId) {

		List<UnitTestClass> classlist = new ArrayList<UnitTestClass>();
		String res = ConnectionUtil.queryforGetNocash(Const.Q_APEX_TEST_QUEUE_ITEM);
		Map<String, CoverageClass> apexmap = ApexClassDao.findMapApexClass();
		if (res == null) {
			return null;
		}
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			String status = ((JSONObject) list.get(i)).getString("ExtendedStatus");
			if (status == null || StringUtils.indexOf(status, "Could not run tests on class") >= 0) {
				continue;
			}
			UnitTestClass cdto = new UnitTestClass();
			cdto.setId(((JSONObject) list.get(i)).getString("Id")); //"709100000009fddAAA"
			cdto.setApexClassId(((JSONObject) list.get(i)).getString("ApexClassId")); //"01p10000000nnUwAAI"
			cdto.setName(apexmap.get(cdto.getApexClassId()).getName());
			cdto.setExtendedStatus(((JSONObject) list.get(i)).getString("ExtendedStatus")); //"(0/14)"
			cdto.setStatus(((JSONObject) list.get(i)).getString("Status")); //"Completed"
			cdto.setParentJobId(((JSONObject) list.get(i)).getString("ParentJobId"));
			cdto.setIsTest(true);
			String parentJobId = cdto.getParentJobId();
			log.trace(">>> " + cdto);
			if (parentJobId.indexOf(asynApexJobId) < 0) {
				continue;
			}
			classlist.add(cdto);
		}
		log.debug("[findApexTestQueueItem] asynApexJobId : " + asynApexJobId + " , result size : " + classlist.size());
		return classlist;
	}

	/**
	 * テストメソッド情報取得処理
	 * @param asyncid ジョブID
	 * @return テストメソッドリスト
	 */
	private static List<UnitTestMethod> findApexTestResultbyAsynid(String asynApexJobId) {

		List<UnitTestMethod> methodlis = new ArrayList<UnitTestMethod>();
		String res = ConnectionUtil.queryforGetNocash(Const.Q_APEX_TEST_RESULT);
		Map<String, CoverageClass> apexmap = ApexClassDao.findMapApexClass();
		if (res == null) {
			return null;
		}
		JSONObject json = new JSONObject(res);
		JSONArray list = json.getJSONArray("records");
		for (Integer i = 0; i < list.length(); i++) {
			String tasynApexJobId = ((JSONObject) list.get(i)).getString("AsyncApexJobId");
			if (tasynApexJobId == null || tasynApexJobId.indexOf(asynApexJobId) < 0) {
				continue;
			}
			UnitTestMethod method = new UnitTestMethod();
			method.setId(((JSONObject) list.get(i)).getString("Id"));
			method.setSystemModstamp(CommonUtil.toDate((String) ((JSONObject) list.get(i)).getString("SystemModstamp")));
			method.setTestTimestamp(CommonUtil.toDate((String) ((JSONObject) list.get(i)).getString("TestTimestamp")));
			method.setOutcome(((JSONObject) list.get(i)).getString("Outcome"));
			method.setApexClassId(((JSONObject) list.get(i)).getString("ApexClassId"));
			method.setName(apexmap.get(method.getApexClassId()).getName());
			method.setMethodName(((JSONObject) list.get(i)).getString("MethodName"));
			method.setMessage(((JSONObject) list.get(i)).getString("Message"));
			method.setStackTrace(((JSONObject) list.get(i)).getString("StackTrace"));
			method.setAsyncApexJobId(tasynApexJobId);
			method.setQueueItemId(((JSONObject) list.get(i)).getString("QueueItemId"));
			method.setApexLogId(((JSONObject) list.get(i)).getString("ApexLogId"));
			//log.debug(">>>" + method.toString());
			methodlis.add(method);
		}
		log.debug("[findApexTestResultbyAsynid] asynApexJobId : " + asynApexJobId + " , result size : " + methodlis.size());
		return methodlis;
	}

}
