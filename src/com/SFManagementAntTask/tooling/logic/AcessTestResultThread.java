package com.SFManagementAntTask.tooling.logic;

import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.Const;
import com.SFManagementAntTask.tooling.dao.ApexClassDao;
import com.SFManagementAntTask.tooling.dao.ApexTestDao;
import com.SFManagementAntTask.tooling.dao.CoverageDao;
import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.ISFDto;
import com.SFManagementAntTask.tooling.dao.model.Organization;
import com.SFManagementAntTask.tooling.dao.model.Package;
import com.SFManagementAntTask.xml.CoverageXmlWriter;
import com.SFManagementAntTask.xml.UnitTestXmlWriter;

/**
 * テスト結果取得（REST）スレッドクラス
 * @author mkawaguchi
 */
public class AcessTestResultThread extends Thread {

	private final static Logger log = LoggerFactory.getLogger(AcessTestResultThread.class);

	private String asyncid;

	private int testcnt;

	public AcessTestResultThread(String asyncid, int testcnt) {
		this.asyncid = asyncid;
		this.testcnt = testcnt;
	}

	@Override
	public void run() {

		int i = 0;
		Organization odto = new Organization();
		do {
			++i;
			log.debug("[run] sleep cunt : " + i);
			try {
				sleep(Const.POLL_WAIT_MILLIS);
			} catch (InterruptedException e) {
				log.error("[sleep]", e);
				throw new BuildException(e);
			}
			odto = findUnitTest(odto);

		} while ((ApexTestDao.isCompleteAsyncApexJob(this.asyncid) && i < Const.MAX_POLL)
		        || odto.getClasscnt() < testcnt && odto.getMethodcnt() == 0);

		UnitTestXmlWriter uwrite = new UnitTestXmlWriter();
		uwrite.write(odto);
		odto = findCoverage(odto);
		CoverageXmlWriter cwrite = new CoverageXmlWriter();
		cwrite.write(odto);
	}

	private Organization findUnitTest(Organization odto) {
		odto = ApexTestDao.findApexTest(this.asyncid, odto);
		return odto;
	}

	private Organization findCoverage(Organization odto) {
		odto = (Organization) CoverageDao.findApexOrgWideCoverage();
		//CoverageDao.findApexCodeCoverage();
		odto.setSourcepath(Const.SRC_ROOT);
		Package pdto = new Package();
		pdto.setName("classes");
		List<ISFDto> apexlist = CoverageDao.findApexCodeCoverageAggregate();
		Map<String, CoverageClass> apexmap = ApexClassDao.findMapApexClass();
		log.debug("findCoverage coverage apex list size is " + apexlist.size());
		log.trace("findCoverage apex list size is " + apexmap.size());
		for (ISFDto apex : apexlist) {
			CoverageClass cdto = (CoverageClass) apex;
			CoverageClass tdto = apexmap.get(cdto.getId());
			if (tdto == null) {
				continue;
			}
			cdto.setName(tdto.getName());
			cdto.setNamespacePrefix(tdto.getNamespacePrefix());
			cdto.setStatus(tdto.getStatus());
			log.trace("findCoverage merge dto is ... " + cdto);
			pdto.addCVGClasslist(cdto);
		}
		odto.addPackagelist(pdto);
		return odto;
	}
}
