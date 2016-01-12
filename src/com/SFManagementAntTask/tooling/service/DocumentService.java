package com.SFManagementAntTask.tooling.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.excel.TableDefinitionExcelWriter;
import com.SFManagementAntTask.partner.dao.PartnerDao;
import com.SFManagementAntTask.partner.dao.model.CustomObject;

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
		TableDefinitionExcelWriter writer = new TableDefinitionExcelWriter();
		writer.write(cobjlist);
		writer.finish();
		log.debug("create Document end...");
	}
}
