package com.SFManagementAntTask.tooling.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.tooling.dao.model.CValidationRule;
import com.sforce.soap.tooling.CustomField;
import com.sforce.soap.tooling.CustomObject;
import com.sforce.soap.tooling.LookupFilter;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.SObject;
import com.sforce.soap.tooling.ValidationRule;
import com.sforce.soap.tooling.WorkflowAlert;
import com.sforce.soap.tooling.WorkflowFieldUpdate;
import com.sforce.soap.tooling.WorkflowOutboundMessage;
import com.sforce.soap.tooling.WorkflowRule;
import com.sforce.soap.tooling.WorkflowTask;
import com.sforce.ws.ConnectionException;

/**
 * SObjectデータアクセスクラス
 * @author mkawaguchi
 */
public class SObjectDao {

	/** ログ */
	private final static Logger log = LoggerFactory.getLogger(SObjectDao.class);

	/**
	 * CustomField取得処理
	 */
	public static List<com.SFManagementAntTask.partner.dao.model.CustomField> findCustomField() {

		QueryResult res = null;
		List<com.SFManagementAntTask.partner.dao.model.CustomField> cobjlist = new ArrayList<com.SFManagementAntTask.partner.dao.model.CustomField>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, DeveloperName, ManageableState, ")
		        .append(" NamespacePrefix, TableEnumOrId, EntityDefinitionId, ")
		        .append(" DefaultValue, Description, InlineHelpText, Length, MaskChar, MaskType, ")
		        .append(" Precision, RelationshipLabel, Scale, SummaryOperation, ")
		        .append(" CreatedDate, CreatedById, CreatedBy.Name, ")
		        .append(" LastModifiedDate, LastModifiedById, LastModifiedBy.Name ")
		        .append(" From CustomField").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		if (res == null) {
			return null;
		}
		for (SObject obj : res.getRecords()) {
			CustomField cobj = (CustomField) obj;
			com.SFManagementAntTask.partner.dao.model.CustomField fobj = new com.SFManagementAntTask.partner.dao.model.CustomField();
			fobj.setId(cobj.getId());
			fobj.setName(cobj.getDeveloperName());
			//log.debug("getDeveloperName : " + cobj.getDeveloperName());
			fobj.setEntityName(cobj.getEntityDefinitionId());
			//log.debug(cobj.getFullName()); // ?
			//log.debug(cobj.getMetadata()); // ?
			//log.debug(cobj.getNamespacePrefix());
			//log.debug(cobj.getTableEnumOrId());
			//log.debug(cobj.getEntityDefinition()); // ?
			//log.debug(cobj.getFieldsToNull()); // ?
			//log.debug(cobj.getManageableState()); // ?
			//log.debug("getManageableState : " + cobj.getManageableState());
			fobj.setDefaultValue(cobj.getDefaultValue());
			fobj.setDescription(cobj.getDescription());
			fobj.setInlineHelpText(cobj.getInlineHelpText());
			if (cobj.getLength() != null) {
				fobj.setLength(cobj.getLength());
			}
			//log.debug("getMaskChar : " + cobj.getMaskChar());
			//log.debug("getMaskType : " + cobj.getMaskType());
			if (cobj.getPrecision() != null) {
				fobj.setPrecision(cobj.getPrecision());
			}
			//log.debug("getRelationshipLabel : " + cobj.getRelationshipLabel());
			if (cobj.getScale() != null) {
				fobj.setScale(cobj.getScale());
			}
			//log.debug("getSummaryOperation : " + cobj.getSummaryOperation());
			if (cobj.getCreatedBy() != null) {
				fobj.setCreatedByName(cobj.getCreatedBy().getName());
			}
			fobj.setCreatedById(cobj.getCreatedById());
			if (cobj.getCreatedDate() != null) {
				Date date = cobj.getCreatedDate().getTime();
				fobj.setCreatedDate(date);
			}
			if (cobj.getLastModifiedBy() != null) {
				fobj.setLastModifiedByName(cobj.getLastModifiedBy().getName());
			}
			fobj.setLastModifiedById(cobj.getLastModifiedById());
			if (cobj.getLastModifiedDate() != null) {
				Date date = cobj.getLastModifiedDate().getTime();
				fobj.setLastModifiedDate(date);
			}
			cobjlist.add(fobj);
		}
		return cobjlist;
	}

	/**
	 * CustomObject取得処理
	 */
	public static List<com.SFManagementAntTask.partner.dao.model.CustomObject> findCustomObject() {
		QueryResult res = null;
		List<com.SFManagementAntTask.partner.dao.model.CustomObject> cobjlist = new ArrayList<com.SFManagementAntTask.partner.dao.model.CustomObject>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, CustomHelpId, Description, DeveloperName, NamespacePrefix,")
		        .append(" CreatedDate, CreatedById, CreatedBy.Name, ")
		        .append(" LastModifiedDate, LastModifiedById, LastModifiedBy.Name ")
		        .append(" From CustomObject").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			CustomObject mobj = (CustomObject) obj;
			com.SFManagementAntTask.partner.dao.model.CustomObject cobj = new com.SFManagementAntTask.partner.dao.model.CustomObject();
			cobj.setId(mobj.getId());
			//log.debug("getDeveloperName : " + mobj.getDeveloperName());
			cobj.setName(mobj.getDeveloperName());
			//log.debug("getLanguage : " + mobj.getLanguage());
			//log.debug("getCustomHelpId : " + mobj.getCustomHelpId()); // ?
			cobj.setDescription(mobj.getDescription()); // ? 
			//log.debug(mobj.getExternalDataSource()); // ?
			//log.debug("getExternalDataSourceId : " + mobj.getExternalDataSourceId());
			//log.debug("getExternalName : " + mobj.getExternalName());
			//log.debug("getExternalRepository : " + mobj.getExternalRepository()); // ?
			//log.debug(mobj.getFieldsToNull()); // ?
			cobj.setNamespacePrefix(mobj.getNamespacePrefix());
			//log.debug("getSharingModel : " + mobj.getSharingModel());
			cobj.setCreatedByName(mobj.getCreatedBy().getName());
			cobj.setCreatedById(mobj.getCreatedById());
			if (mobj.getCreatedDate() != null) {
				Date date = mobj.getCreatedDate().getTime();
				cobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			if (mobj.getLastModifiedBy() != null) {
				cobj.setLastModifiedByName(mobj.getLastModifiedBy().getName());
			}
			cobj.setLastModifiedById(mobj.getLastModifiedById());
			if (mobj.getLastModifiedDate() != null) {
				Date date = mobj.getLastModifiedDate().getTime();
				cobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			cobjlist.add(cobj);
		}
		return cobjlist;
	}

	/**
	 * 入力規則取得処理
	 * @return
	 */
	public List<CValidationRule> findValidationRule() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, ValidationName, FullName, NamespacePrefix, Active, Description, ")
		        .append(" EntityDefinition, EntityDefinitionId, ErrorDisplayField, ErrorMessage, ")
		        .append(" ManageableState, Metadata, errorConditionFormula, ")
		        .append(" CreatedDate, CreatedById, CreatedBy.Name, ")
		        .append(" LastModifiedDate, LastModifiedById, LastModifiedBy.Name ")
		        .append(" From ValidationRule").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			ValidationRule vobj = (com.sforce.soap.tooling.ValidationRule) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getDescription());
			log.debug("getDeveloperName : " + vobj.getEntityDefinitionId());
			log.debug("getDeveloperName : " + vobj.getErrorDisplayField());
			log.debug("getDeveloperName : " + vobj.getErrorMessage());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getValidationName());
			log.debug("getDeveloperName : " + vobj.getActive());
			log.debug("getDeveloperName : " + vobj.getEntityDefinition());
			//EntityDefinition ed = vobj.getEntityDefinition();
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			log.debug("getDeveloperName : " + vobj.getMetadata());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}

	/**
	 * ワークフロールール取得処理
	 * @return
	 */
	public List<CValidationRule> findWorkFlowRule() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, FullName, ManageableState, Metadata, Name, NamespacePrefix, TableEnumOrId, ")
		        .append(" CreatedDate, CreatedById, CreatedBy.Name, ")
		        .append(" LastModifiedDate, LastModifiedById, LastModifiedBy.Name ")
		        .append(" From WorkflowRule").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			WorkflowRule vobj = (WorkflowRule) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getName());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getTableEnumOrId());
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			log.debug("getDeveloperName : " + vobj.getMetadata());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}

	/**
	 * ワークフロールール取得処理
	 * @return
	 */
	public List<CValidationRule> findWorkFlowTask() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, EntityDefinition,EntityDefinitionId,FullName,ManageableState,Metadata,")
		        .append(" NamespacePrefix,Priority,Status,Subject, ")
		        .append(" CreatedDate, CreatedById, CreatedBy.Name, ")
		        .append(" LastModifiedDate, LastModifiedById, LastModifiedBy.Name ")
		        .append(" From WorkflowTask").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			WorkflowTask vobj = (WorkflowTask) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getEntityDefinitionId());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getPriority());
			log.debug("getDeveloperName : " + vobj.getStatus());
			log.debug("getDeveloperName : " + vobj.getSubject());
			log.debug("getDeveloperName : " + vobj.getEntityDefinition());
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			log.debug("getDeveloperName : " + vobj.getMetadata());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}

	/**
	 * ワークフロールール取得処理
	 * @return
	 */
	public List<CValidationRule> findWorkflowFieldUpdate() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, EntityDefinition,EntityDefinitionId,FieldDefinition,FieldDefinitionId, ")
		        .append(" FullName,LiteralValue,LookupValueId,ManageableState,Metadata,Name,")
		        .append(" NamespacePrefix,SourceTableEnumOrId ")
		        .append(" From WorkflowFieldUpdate").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			WorkflowFieldUpdate vobj = (WorkflowFieldUpdate) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getEntityDefinitionId());
			log.debug("getDeveloperName : " + vobj.getFieldDefinitionId());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getLiteralValue());
			log.debug("getDeveloperName : " + vobj.getLookupValueId());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getName());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getSourceTableEnumOrId());
			log.debug("getDeveloperName : " + vobj.getEntityDefinition());
			//EntityDefinition ed = vobj.getEntityDefinition();
			log.debug("getDeveloperName : " + vobj.getFieldDefinition());
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			log.debug("getDeveloperName : " + vobj.getMetadata());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}

	/**
	 * ワークフロールール取得処理
	 * @return
	 */
	public List<CValidationRule> findWorkflowAlert() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, CcEmails,Description,DeveloperName,EntityDefinition,EntityDefinitionId,")
		        .append(" FullName,ManageableState,Metadata,NamespacePrefix,SenderType,TemplateId ")
		        .append(" From WorkflowAlert").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			WorkflowAlert vobj = (WorkflowAlert) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getCcEmails());
			log.debug("getDeveloperName : " + vobj.getDescription());
			log.debug("getDeveloperName : " + vobj.getDeveloperName());
			log.debug("getDeveloperName : " + vobj.getEntityDefinitionId());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getSenderType());
			log.debug("getDeveloperName : " + vobj.getTemplateId());
			log.debug("getDeveloperName : " + vobj.getEntityDefinition());
			//EntityDefinition ed = vobj.getEntityDefinition();
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			log.debug("getDeveloperName : " + vobj.getMetadata());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}

	/**
	 * ワークフロールール取得処理
	 * @return
	 */
	public List<CValidationRule> findWorkflowOutboundMessage() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, ApiVersion,EntityDefinition,EntityDefinitionId,FullName,IntegrationUserId,")
		        .append(" ManageableState,Metadata,Name,NamespacePrefix ")
		        .append(" From WorkflowOutboundMessage").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			WorkflowOutboundMessage vobj = (WorkflowOutboundMessage) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getEntityDefinitionId());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getIntegrationUserId());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getName());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getApiVersion());
			log.debug("getDeveloperName : " + vobj.getEntityDefinition());
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			//EntityDefinition ed = vobj.getEntityDefinition();
			log.debug("getDeveloperName : " + vobj.getMetadata());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}

	/**
	 * ワークフロールール取得処理
	 * @return
	 */
	public List<CValidationRule> findLookupFilter() {

		QueryResult res = null;
		List<CValidationRule> vobjlist = new ArrayList<CValidationRule>();
		String query = new StringBuffer()
		        .append("Select ")
		        .append(" Id, Active, DeveloperName, FullName, IsOptional, ManageableState, ")
		        .append(" Metadata,NamespacePrefix,SourceFieldDefinition,SourceFieldDefinitionId,SourceObject, ")
		        .append(" TargetEntityDefinition,TargetEntityDefinitionId ")
		        .append(" From LookupFilter").toString();
		try {
			res = ConnectionUtil.connectTooling().query(query);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		if (res == null) {
			return null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		for (SObject obj : res.getRecords()) {
			LookupFilter vobj = (LookupFilter) obj;
			CValidationRule vvobj = new CValidationRule();
			log.debug("getDeveloperName : " + vobj.getId());
			log.debug("getDeveloperName : " + vobj.getDeveloperName());
			log.debug("getDeveloperName : " + vobj.getManageableState());
			log.debug("getDeveloperName : " + vobj.getNamespacePrefix());
			log.debug("getDeveloperName : " + vobj.getSourceFieldDefinitionId());
			log.debug("getDeveloperName : " + vobj.getFullName());
			log.debug("getDeveloperName : " + vobj.getSourceObject());
			log.debug("getDeveloperName : " + vobj.getSourceFieldDefinition());
			log.debug("getDeveloperName : " + vobj.getTargetEntityDefinitionId());
			log.debug("getDeveloperName : " + vobj.getActive());
			log.debug("getDeveloperName : " + vobj.getFieldsToNull());
			log.debug("getDeveloperName : " + vobj.getIsOptional());
			log.debug("getDeveloperName : " + vobj.getMetadata());
			log.debug("getDeveloperName : " + vobj.getSourceFieldDefinition());
			log.debug("getDeveloperName : " + vobj.getTargetEntityDefinition());
			vvobj.setCreatedByName(vobj.getCreatedBy().getName());
			vvobj.setCreatedById(vobj.getCreatedById());
			if (vobj.getCreatedDate() != null) {
				Date date = vobj.getCreatedDate().getTime();
				vvobj.setCreatedDate(date);
				//log.debug("getCreatedDate : " + format.format(date));
			}
			vvobj.setLastModifiedByName(vobj.getLastModifiedBy().getName());
			vvobj.setLastModifiedById(vobj.getLastModifiedById());
			if (vobj.getLastModifiedDate() != null) {
				Date date = vobj.getLastModifiedDate().getTime();
				vvobj.setLastModifiedDate(date);
				//log.debug("getLastModifiedDate : " + format.format(date));
			}
			vobjlist.add(vvobj);
		}
		return vobjlist;
	}
}
