package com.SFManagementAntTask.tooling.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.excel.TableDefinitionExcelWriter;
import com.SFManagementAntTask.partner.dao.PartnerDao;
import com.SFManagementAntTask.partner.dao.model.CustomField;
import com.SFManagementAntTask.partner.dao.model.CustomObject;
import com.SFManagementAntTask.tooling.dao.SObjectDao;

/**
 * 設計書関連サービスクラス
 * @author mkawaguchi
 */
public class DocumentService {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(DocumentService.class);

	/**
	 * デフォルトコンストラクタ
	 */
	public DocumentService() {}

	/**
	 * テーブル定義書作成処理
	 */
	public void createTableDifinitionBook() {

		List<CustomObject> cobjlist = PartnerDao.describeGlobal();
		cobjlist = PartnerDao.describeSObjects(cobjlist);
		List<CustomObject> ccobjlist = SObjectDao.findCustomObject();
		List<CustomField> fobjlist = SObjectDao.findCustomField();
		mergeObject(cobjlist, ccobjlist, fobjlist);
		SObjectDao.findCustomField();
		TableDefinitionExcelWriter writer = new TableDefinitionExcelWriter();
		writer.write(cobjlist);
		writer.finish();
		log.debug("[createTableDifinitionBook] create Document end...");
	}

	/**
	 * リストマージ処理
	 * @param cobjlist
	 * @param ccobjlist
	 * @param fobjlist
	 * @return
	 */
	private List<CustomObject> mergeObject(List<CustomObject> cobjlist, List<CustomObject> ccobjlist, List<CustomField> fobjlist) {

		if (cobjlist == null) {
			return cobjlist;
		}
		for (CustomObject cobj : cobjlist) {
			for (CustomObject ccobj : ccobjlist) {
				log.trace("[createTableDifinitionBook:object]" + cobj.getName().replaceAll("__c", "") + " : " + ccobj.getName());
				if (StringUtils.equals(cobj.getName().replaceAll("__c", ""), ccobj.getName())) {
					cobj.setId(ccobj.getId());
					cobj.setDescription(ccobj.getDescription());
					cobj.setCreatedById(ccobj.getCreatedById());
					cobj.setCreatedByName(ccobj.getCreatedByName());
					cobj.setCreatedDate(ccobj.getCreatedDate());
					cobj.setLastModifiedById(ccobj.getLastModifiedById());
					cobj.setLastModifiedByName(ccobj.getLastModifiedByName());
					cobj.setLastModifiedDate(ccobj.getLastModifiedDate());
					break;
				}
			}
			if (cobj.getFields() == null) continue;
			for (CustomField fobj : cobj.getFields()) {
				if (!fobj.isCustom()) {
					continue;
				}
				for (CustomField ffobj : fobjlist) {
					log.trace("[createTableDifinitionBook:field]" + " : " + cobj.getId() + " : " + cobj.getName().replaceAll("__c", "") + " : " + ffobj.getEntityName());
					if ((StringUtils.equals(cobj.getName().replaceAll("__c", ""), ffobj.getEntityName())
					        || StringUtils.contains(cobj.getId(), ffobj.getEntityName()))
					        && StringUtils.equals(fobj.getName().replaceAll("__c", ""), ffobj.getName())) {
						fobj.setDescription(ffobj.getDescription());
						fobj.setCreatedById(ffobj.getCreatedById());
						fobj.setCreatedByName(ffobj.getCreatedByName());
						fobj.setCreatedDate(ffobj.getCreatedDate());
						fobj.setLastModifiedById(ffobj.getLastModifiedById());
						fobj.setLastModifiedByName(ffobj.getLastModifiedByName());
						fobj.setLastModifiedDate(ffobj.getLastModifiedDate());
						break;
					}
				}
			}
		}
		return cobjlist;
	}
}
