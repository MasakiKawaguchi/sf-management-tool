package com.SFManagementAntTask.partner.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.SFManagementAntTask.partner.dao.model.CustomField;
import com.SFManagementAntTask.partner.dao.model.CustomObject;
import com.SFManagementAntTask.partner.dao.model.Relationship;
import com.sforce.soap.partner.ChildRelationship;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PicklistEntry;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class PartnerDao {

	private final static Logger log = LoggerFactory.getLogger(PartnerDao.class);

	public static void query(String queryStr) {

		QueryResult res = null;
		try {
			res = ConnectionUtil.connectPartner().query(queryStr);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		for (SObject sobj : res.getRecords()) {
			log.debug("" + sobj.getName());
		}
	}

	public static void describeSObject(String sobj) {

		DescribeSObjectResult res = null;
		try {
			res = ConnectionUtil.connectPartner().describeSObject(sobj);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug(res.getLabel());
		for (Field fobj : res.getFields()) {
			log.debug(fobj.getName());
			log.debug(fobj.getLabel());
			for (PicklistEntry pobj : fobj.getPicklistValues()) {
				log.debug(pobj.getLabel());
				log.debug(pobj.getValue());
			}
		}
	}

	/**
	 * 項目取得処理
	 * @param sobjlist オブジェクト名配列
	 */
	public static List<CustomObject> describeSObjects(List<CustomObject> sobjlist) {

		//List<CustomObject> cobjlist = new ArrayList<CustomObject>();
		List<String> sobjnamelist = new ArrayList<String>();
		DescribeSObjectResult[] ressobjlist = null;
		List<DescribeSObjectResult> reslist = new ArrayList<DescribeSObjectResult>();
		try {
			for (CustomObject cobj : sobjlist) {
				sobjnamelist.add(cobj.getName());
				if (sobjnamelist.size() == 100) {
					ressobjlist = ConnectionUtil.connectPartner().describeSObjects(sobjnamelist.toArray(new String[sobjnamelist.size()]));
					for (DescribeSObjectResult res : ressobjlist) {
						reslist.add(res);
					}
					sobjnamelist = new ArrayList<String>();
				}
			}
			ressobjlist = ConnectionUtil.connectPartner().describeSObjects(sobjnamelist.toArray(new String[sobjnamelist.size()]));
			for (DescribeSObjectResult res : ressobjlist) {
				reslist.add(res);
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		log.debug(">>> " + reslist.size());
		for (DescribeSObjectResult res : reslist) {
			//CustomObject cobj = null;
			for (CustomObject cobj : sobjlist) {
				log.debug(">>> " + cobj.getName() + " : " + res.getName());
				if (StringUtils.equals(cobj.getName(), res.getName())) {
					//cobj = tcobj;
					cobj.setName(res.getName());
					cobj.setLabel(res.getLabel());
					cobj.setCustom(res.isCustom());
					cobj.setLabel(res.getLabel());
					cobj.setName(res.getName());
					cobj.setCustom(res.isCustom());
					cobj.setFeedEnabled(res.isFeedEnabled());
					cobj.setKeyPrefix(res.getKeyPrefix());
					cobj.setLabelPlural(res.getLabelPlural());
					cobj.setActivateable(res.isActivateable());
					cobj.setCustom(res.isCustomSetting());
					cobj.setDeprecatedAndHidden(res.isDeprecatedAndHidden());
					cobj.setIdEnabled(res.isIdEnabled());
					cobj.setLayoutable(res.isLayoutable());
					cobj.setMergeable(res.isMergeable());
					cobj.setReplicateable(res.isReplicateable());
					cobj.setRetrieveable(res.isRetrieveable());
					cobj.setQueryable(res.isQueryable());
					cobj.setSearchable(res.isSearchable());
					cobj.setTriggerable(res.isTriggerable());
					cobj.setCreateable(res.isCreateable());
					cobj.setUpdateable(res.isUpdateable());
					cobj.setDeletable(res.isDeletable());
					cobj.setUndeletable(res.isUndeletable());
					for (Field field : res.getFields()) {
						CustomField fobj = new CustomField();
						fobj.setName(field.getName());
						fobj.setLabel(field.getLabel());
						fobj.setPermissionable(field.getPermissionable());
						fobj.setAutoNumber(field.isAutoNumber());
						field.isCalculated();
						fobj.setCalculatedFormula(field.getCalculatedFormula());
						fobj.setCascadeDelete(field.isCascadeDelete());
						fobj.setCaseSensitive(field.isCaseSensitive());
						fobj.setControllerName(field.getControllerName());
						fobj.setCreateable(field.isCreateable());
						fobj.setCustom(field.isCustom());
						fobj.setDefaultedOnCreate(field.isDefaultedOnCreate());
						fobj.setDependentPicklist(field.isDependentPicklist());
						fobj.setDeprecatedAndHidden(field.isDeprecatedAndHidden());
						fobj.setDigits(field.getDigits());
						fobj.setDisplayLocationInDecimal(field.isDisplayLocationInDecimal());
						fobj.setEncrypted(field.isEncrypted());
						fobj.setExternalId(field.isExternalId());
						fobj.setExtraTypeInfo(field.getExtraTypeInfo());
						fobj.setFilterable(field.isFilterable());
						fobj.setFilteredLookupInfo(field.getFilteredLookupInfo());
						fobj.setGroupable(field.isGroupable());
						fobj.setHighScaleNumber(field.isHighScaleNumber());
						fobj.setHtmlFormatted(field.isHtmlFormatted());
						fobj.setIdLookup(field.isIdLookup());
						fobj.setInlineHelpText(field.getInlineHelpText()); //string
						fobj.setLength(field.getLength()); // int
						fobj.setMask(field.getMask()); // string
						fobj.setMaskType(field.getMaskType()); //string
						fobj.setNameField(field.isNameField());
						fobj.setNamePointing(field.isNamePointing());
						fobj.setNillable(field.isNillable());
						fobj.setPermissionable(field.isPermissionable());
						fobj.setPrecision(field.getPrecision()); //int
						fobj.setQueryByDistance(field.isQueryByDistance());
						fobj.setReferenceTargetField(field.getReferenceTargetField());
						fobj.setReferenceTo(field.getReferenceTo());
						fobj.setRelationshipName(field.getRelationshipName());
						fobj.setRelationshipOrder(field.getRelationshipOrder());
						fobj.setRestrictedDelete(field.isRestrictedDelete());
						fobj.setRestrictedPicklist(field.isRestrictedPicklist());
						fobj.setScale(field.getScale());
						fobj.setSortable(field.isSortable());
						fobj.setType("" + field.getType());
						fobj.setUnique(field.isUnique());
						fobj.setUpdateable(field.isUpdateable());
						fobj.setWriteRequiresMasterRead(field.isWriteRequiresMasterRead());
						cobj.addFields(fobj);
					}
					for (ChildRelationship crs : res.getChildRelationships()) {
						Relationship fobj = new Relationship();
						fobj.setName(crs.getField());
						fobj.setRelationshipName(crs.getRelationshipName());
						fobj.setChildSObject(crs.getChildSObject());
						//log.debug("getCascadeDelete:" + crs.getCascadeDelete()); //true
						//log.debug("getJunctionIdListName:" + crs.getJunctionIdListName()); //null
						//log.debug("getDeprecatedAndHidden:" + crs.getDeprecatedAndHidden()); //false
						//log.debug("getRestrictedDelete:" + crs.getRestrictedDelete()); //false
						//						for (String jrt : crs.getJunctionReferenceTo()) {
						//							log.debug("getJunctionReferenceTo:" + jrt);
						//						}
						cobj.addChildRelationships(fobj);
					}
				}
			}
		}
		return sobjlist;
	}

	public static void queryUser() {

		QueryResult res = null;
		try {
			res = ConnectionUtil.connectPartner().query("Select Id,Name,ProfileId From User");
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (SObject obj : res.getRecords()) {
			log.debug(obj.getType());
			log.debug(obj.getId());
			log.debug(obj.getField("Name").toString());
			log.debug(obj.getField("ProfileId").toString());
		}
	}

	/**
	 * グローバル情報取得処理
	 * @return カスタムオブジェクト情報
	 */
	public static List<CustomObject> describeGlobal() {

		List<CustomObject> cobjlist = new ArrayList<CustomObject>();
		DescribeGlobalResult res = null;
		try {
			res = ConnectionUtil.connectPartner().describeGlobal();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (DescribeGlobalSObjectResult dobj : res.getSobjects()) {
			//if (!dobj.isCustom() && !StringUtils.equals(dobj.getName(), "User")) {
			//if (!dobj.isCustom()) {
			if (StringUtils.endsWithAny(dobj.getName(), "Share", "History", "Feed") || StringUtils.equals(dobj.getName(), "CaseStatus")
			        || StringUtils.equals(dobj.getName(), "CaseTeamMember") || StringUtils.equals(dobj.getName(), "CaseTeamRole")
			        || StringUtils.equals(dobj.getName(), "CaseTeamTemplate") || StringUtils.equals(dobj.getName(), "CaseTeamTemplateMember")
			        || StringUtils.equals(dobj.getName(), "CaseTeamTemplateRecord") || StringUtils.equals(dobj.getName(), "CategoryNodeLocalization")
			        || StringUtils.equals(dobj.getName(), "AcceptedEventRelation") || StringUtils.equals(dobj.getName(), "AccountContactRole")
			        || StringUtils.equals(dobj.getName(), "AccountPartner") || StringUtils.equals(dobj.getName(), "ActionLinkGroupTemplate")
			        || StringUtils.equals(dobj.getName(), "ActionLinkTemplate") || StringUtils.equals(dobj.getName(), "AdditionalNumber")
			        || StringUtils.equals(dobj.getName(), "AggregateResult") || StringUtils.equals(dobj.getName(), "Announcement")
			        || StringUtils.equals(dobj.getName(), "ApexTestQueueItem") || StringUtils.equals(dobj.getName(), "ApexTestResult")
			        || StringUtils.equals(dobj.getName(), "ApexTrigger") || StringUtils.equals(dobj.getName(), "AppMenuItem")
			        || StringUtils.equals(dobj.getName(), "Approval") || StringUtils.equals(dobj.getName(), "AssignmentRule")
			        || StringUtils.equals(dobj.getName(), "AsyncApexJob") || StringUtils.equals(dobj.getName(), "AttachedContentDocument")
			        || StringUtils.equals(dobj.getName(), "Attachment") || StringUtils.equals(dobj.getName(), "AuthConfig")
			        || StringUtils.equals(dobj.getName(), "AuthConfigProviders") || StringUtils.equals(dobj.getName(), "Announcement")
			        || StringUtils.equals(dobj.getName(), "AuthSession") || StringUtils.equals(dobj.getName(), "BusinessHours")
			        || StringUtils.equals(dobj.getName(), "BusinessProcess") || StringUtils.equals(dobj.getName(), "CallCenter")
			        || StringUtils.equals(dobj.getName(), "CollaborationGroup") || StringUtils.equals(dobj.getName(), "CollaborationGroupMember")
			        || StringUtils.equals(dobj.getName(), "CollaborationGroupMemberRequest") || StringUtils.equals(dobj.getName(), "CollaborationGroupRecord")
			        || StringUtils.equals(dobj.getName(), "CollaborationInvitation") || StringUtils.equals(dobj.getName(), "CombinedAttachment")
			        || StringUtils.equals(dobj.getName(), "Community") || StringUtils.equals(dobj.getName(), "ConnectedApplication")
			        || StringUtils.equals(dobj.getName(), "ContentDistribution") || StringUtils.equals(dobj.getName(), "ContentDistributionView")
			        || StringUtils.equals(dobj.getName(), "ContentWorkspace") || StringUtils.equals(dobj.getName(), "ContentWorkspaceDoc")
			        || StringUtils.equals(dobj.getName(), "ContentVersion") || StringUtils.equals(dobj.getName(), "CorsWhitelistEntry")
			        || StringUtils.equals(dobj.getName(), "CronJobDetail") || StringUtils.equals(dobj.getName(), "CronTrigger")
			        || StringUtils.equals(dobj.getName(), "CustomObjectUserLicenseMetrics") || StringUtils.equals(dobj.getName(), "CustomPermission")
			        || StringUtils.equals(dobj.getName(), "CustomPermissionDependency") || StringUtils.equals(dobj.getName(), "Dashboard")
			        || StringUtils.equals(dobj.getName(), "DashboardComponent") || StringUtils.equals(dobj.getName(), "DataType")
			        || StringUtils.equals(dobj.getName(), "DeclinedEventRelation") || StringUtils.equals(dobj.getName(), "DocumentAttachmentMap")
			        || StringUtils.equals(dobj.getName(), "Domain") || StringUtils.equals(dobj.getName(), "DomainSite")
			        || StringUtils.equals(dobj.getName(), "DuplicateRecordItem") || StringUtils.equals(dobj.getName(), "DuplicateRecordSet")
			        || StringUtils.equals(dobj.getName(), "DuplicateRule") || StringUtils.equals(dobj.getName(), "DuplicateRecordSet")
			        || StringUtils.equals(dobj.getName(), "DuplicateRecordItem") || StringUtils.equals(dobj.getName(), "EmailDomainKey")
			        || StringUtils.equals(dobj.getName(), "EmailMessage") || StringUtils.equals(dobj.getName(), "EmailServicesAddress")
			        || StringUtils.equals(dobj.getName(), "EmailServicesFunction") || StringUtils.equals(dobj.getName(), "EmailStatus")
			        || StringUtils.equals(dobj.getName(), "EmailTemplate") || StringUtils.equals(dobj.getName(), "EntityDefinition")
			        || StringUtils.equals(dobj.getName(), "EntityParticle") || StringUtils.equals(dobj.getName(), "EntitySubscription")
			        || StringUtils.equals(dobj.getName(), "EventLogFile") || StringUtils.equals(dobj.getName(), "EventRecurrenceException")
			        || StringUtils.equals(dobj.getName(), "EventRelation") || StringUtils.equals(dobj.getName(), "ExternalDataSource")
			        || StringUtils.equals(dobj.getName(), "ExternalDataUserAuth") || StringUtils.equals(dobj.getName(), "FeedComment")
			        || StringUtils.equals(dobj.getName(), "FeedPollChoice") || StringUtils.equals(dobj.getName(), "FeedPollVote")
			        || StringUtils.equals(dobj.getName(), "FeedRevision") || StringUtils.equals(dobj.getName(), "FeedTrackedChange")
			        || StringUtils.equals(dobj.getName(), "FieldDefinition") || StringUtils.equals(dobj.getName(), "FeedLike")
			        || StringUtils.equals(dobj.getName(), "FiscalYearSettings") || StringUtils.equals(dobj.getName(), "FlowInterview")
			        || StringUtils.equals(dobj.getName(), "Folder") || StringUtils.equals(dobj.getName(), "FolderedContentDocument")
			        || StringUtils.equals(dobj.getName(), "GrantedByLicense") || StringUtils.equals(dobj.getName(), "Group")
			        || StringUtils.equals(dobj.getName(), "GroupMember") || StringUtils.equals(dobj.getName(), "FeedLike")
			        || StringUtils.equals(dobj.getName(), "KnowledgeableUser") || StringUtils.equals(dobj.getName(), "Holiday")
			        || StringUtils.equals(dobj.getName(), "ListView") || StringUtils.equals(dobj.getName(), "ListViewChart")
			        || StringUtils.equals(dobj.getName(), "ListViewChartInstance") || StringUtils.equals(dobj.getName(), "LoginGeo")
			        || StringUtils.equals(dobj.getName(), "LoginIp") || StringUtils.equals(dobj.getName(), "LookedUpFromActivity")
			        || StringUtils.equals(dobj.getName(), "MailmergeTemplate") || StringUtils.equals(dobj.getName(), "FeedLike")
			        || StringUtils.equals(dobj.getName(), "MatchingRuleItem") || StringUtils.equals(dobj.getName(), "MatchingRule")
			        || StringUtils.equals(dobj.getName(), "Name") || StringUtils.equals(dobj.getName(), "NamedCredential")
			        || StringUtils.equals(dobj.getName(), "Note") || StringUtils.equals(dobj.getName(), "NoteAndAttachment")
			        || StringUtils.equals(dobj.getName(), "OauthToken") || StringUtils.equals(dobj.getName(), "ObjectPermissions")
			        || StringUtils.equals(dobj.getName(), "OpportunityCompetitor") || StringUtils.equals(dobj.getName(), "OpportunityContactRole")
			        || StringUtils.equals(dobj.getName(), "OpportunityOverride") || StringUtils.equals(dobj.getName(), "OpportunityPartner")
			        || StringUtils.equals(dobj.getName(), "OpportunityStage") || StringUtils.equals(dobj.getName(), "Opportunity__hd")
			        || StringUtils.equals(dobj.getName(), "OrgWideEmailAddress") || StringUtils.equals(dobj.getName(), "OwnedContentDocument")
			        || StringUtils.equals(dobj.getName(), "PackageLicense") || StringUtils.equals(dobj.getName(), "Partner")
			        || StringUtils.equals(dobj.getName(), "PartnerRole") || StringUtils.equals(dobj.getName(), "Period")
			        || StringUtils.equals(dobj.getName(), "PermissionSet") || StringUtils.equals(dobj.getName(), "PermissionSetAssignment")
			        || StringUtils.equals(dobj.getName(), "PermissionSetLicense") || StringUtils.equals(dobj.getName(), "PermissionSetLicenseAssign")
			        || StringUtils.equals(dobj.getName(), "PicklistValueInfo") || StringUtils.equals(dobj.getName(), "PlatformAction")
			        || StringUtils.equals(dobj.getName(), "PlatformCachePartition") || StringUtils.equals(dobj.getName(), "PlatformCachePartitionType")
			        || StringUtils.equals(dobj.getName(), "ProcessDefinition") || StringUtils.equals(dobj.getName(), "ProcessInstance")
			        || StringUtils.equals(dobj.getName(), "ProcessInstanceNode") || StringUtils.equals(dobj.getName(), "ProcessInstanceStep")
			        || StringUtils.equals(dobj.getName(), "ProcessInstanceWorkitem") || StringUtils.equals(dobj.getName(), "ProcessNode")
			        || StringUtils.equals(dobj.getName(), "Publisher") || StringUtils.equals(dobj.getName(), "PushTopic")
			        || StringUtils.equals(dobj.getName(), "QuantityForecast") || StringUtils.equals(dobj.getName(), "QueueSobject")
			        || StringUtils.equals(dobj.getName(), "RecentlyViewed") || StringUtils.equals(dobj.getName(), "RecordType")
			        || StringUtils.equals(dobj.getName(), "RecordTypeLocalization") || StringUtils.equals(dobj.getName(), "RelationshipDomain")
			        || StringUtils.equals(dobj.getName(), "RelationshipInfo") || StringUtils.equals(dobj.getName(), "Report")
			        || StringUtils.equals(dobj.getName(), "RevenueForecast") || StringUtils.equals(dobj.getName(), "SamlSsoConfig")
			        || StringUtils.equals(dobj.getName(), "Scontrol") || StringUtils.equals(dobj.getName(), "ScontrolLocalization")
			        || StringUtils.equals(dobj.getName(), "SearchLayout") || StringUtils.equals(dobj.getName(), "SecureAgentsCluster")
			        || StringUtils.equals(dobj.getName(), "SelfServiceUser") || StringUtils.equals(dobj.getName(), "SetupAuditTrail")
			        || StringUtils.equals(dobj.getName(), "SetupEntityAccess") || StringUtils.equals(dobj.getName(), "Site")
			        || StringUtils.equals(dobj.getName(), "SolutionStatus") || StringUtils.equals(dobj.getName(), "StaticResource")
			        || StringUtils.equals(dobj.getName(), "StreamingChannel") || StringUtils.equals(dobj.getName(), "TaskPriority")
			        || StringUtils.equals(dobj.getName(), "TaskRecurrenceException") || StringUtils.equals(dobj.getName(), "TaskStatus")
			        || StringUtils.equals(dobj.getName(), "TenantUsageEntitlement") || StringUtils.equals(dobj.getName(), "ThirdPartyAccountLink")
			        || StringUtils.equals(dobj.getName(), "Topic") || StringUtils.equals(dobj.getName(), "TopicAssignment")
			        || StringUtils.equals(dobj.getName(), "TopicLocalization") || StringUtils.equals(dobj.getName(), "UndecidedEventRelation")
			        || StringUtils.equals(dobj.getName(), "UserAppMenuCustomization") || StringUtils.equals(dobj.getName(), "UserAppMenuItem")
			        || StringUtils.equals(dobj.getName(), "UserEntityAccess") || StringUtils.equals(dobj.getName(), "UserFieldAccess")
			        || StringUtils.equals(dobj.getName(), "UserLicense") || StringUtils.equals(dobj.getName(), "UserListView")
			        || StringUtils.equals(dobj.getName(), "UserListViewCriterion") || StringUtils.equals(dobj.getName(), "UserLogin")
			        || StringUtils.equals(dobj.getName(), "UserPackageLicense") || StringUtils.equals(dobj.getName(), "UserPreference")
			        || StringUtils.equals(dobj.getName(), "UserProvAccount") || StringUtils.equals(dobj.getName(), "UserProvAccountStaging")
			        || StringUtils.equals(dobj.getName(), "UserProvMockTarget") || StringUtils.equals(dobj.getName(), "UserProvisioningConfig")
			        || StringUtils.equals(dobj.getName(), "UserProvisioningLog") || StringUtils.equals(dobj.getName(), "UserProvisioningRequest")
			        || StringUtils.equals(dobj.getName(), "UserRecordAccess") || StringUtils.equals(dobj.getName(), "UserRole")
			        || StringUtils.equals(dobj.getName(), "Vote") || StringUtils.equals(dobj.getName(), "WebLink")
			        || StringUtils.equals(dobj.getName(), "WebLinkLocalization") || StringUtils.equals(dobj.getName(), "WorkAccess")
			        || StringUtils.equals(dobj.getName(), "WorkBadge") || StringUtils.equals(dobj.getName(), "WorkBadgeDefinition")
			        || StringUtils.equals(dobj.getName(), "WorkThanks") || StringUtils.equals(dobj.getName(), "Organization")
			        || StringUtils.equals(dobj.getName(), "LeadStatus") || StringUtils.equals(dobj.getName(), "InstalledMobileApp")
			        || StringUtils.equals(dobj.getName(), "FeedItem") || StringUtils.equals(dobj.getName(), "Document")
			        || StringUtils.equals(dobj.getName(), "ContractStatus") || StringUtils.equals(dobj.getName(), "ContractContactRole")
			        || StringUtils.equals(dobj.getName(), "ContentFolderMember") || StringUtils.equals(dobj.getName(), "ContentFolderLink")
			        || StringUtils.equals(dobj.getName(), "ContentFolderItem") || StringUtils.equals(dobj.getName(), "ContentDocumentLink")
			        || StringUtils.equals(dobj.getName(), "ContentDocument") || StringUtils.equals(dobj.getName(), "ClientBrowser")
			        || StringUtils.equals(dobj.getName(), "ChatterActivity") || StringUtils.equals(dobj.getName(), "CategoryNode")
			        || StringUtils.equals(dobj.getName(), "CategoryData") || StringUtils.equals(dobj.getName(), "CampaignMemberStatus")
			        || StringUtils.equals(dobj.getName(), "BrandTemplate") || StringUtils.equals(dobj.getName(), "AuthProvider")
			        || StringUtils.equals(dobj.getName(), "ApexPage") || StringUtils.equals(dobj.getName(), "ApexLog")
			        || StringUtils.equals(dobj.getName(), "ApexEmailNotification") || StringUtils.equals(dobj.getName(), "ApexComponent")
			        || StringUtils.equals(dobj.getName(), "BrandTemplate") || StringUtils.equals(dobj.getName(), "AuthProvider")
			        || StringUtils.equals(dobj.getName(), "ApexClass") || StringUtils.equals(dobj.getName(), "AuraDefinition")
			        || StringUtils.equals(dobj.getName(), "AuraDefinitionBundle")) {
				continue;
			}
			CustomObject cobj = new CustomObject();
			cobj.setLabel(dobj.getLabel());
			cobj.setName(dobj.getName());
			cobj.setCustom(dobj.isCustom());
			cobj.setFeedEnabled(dobj.isFeedEnabled());
			cobj.setKeyPrefix(dobj.getKeyPrefix());
			cobj.setLabelPlural(dobj.getLabelPlural());
			cobj.setActivateable(dobj.isActivateable());
			cobj.setCustom(dobj.isCustomSetting());
			cobj.setDeprecatedAndHidden(dobj.isDeprecatedAndHidden());
			cobj.setIdEnabled(dobj.isIdEnabled());
			cobj.setLayoutable(dobj.isLayoutable());
			cobj.setMergeable(dobj.isMergeable());
			cobj.setReplicateable(dobj.isReplicateable());
			cobj.setRetrieveable(dobj.isRetrieveable());
			cobj.setQueryable(dobj.isQueryable());
			cobj.setSearchable(dobj.isSearchable());
			cobj.setTriggerable(dobj.isTriggerable());
			cobj.setCreateable(dobj.isCreateable());
			cobj.setUpdateable(dobj.isUpdateable());
			cobj.setDeletable(dobj.isDeletable());
			cobj.setUndeletable(dobj.isUndeletable());

			//log.debug("SObject:" + sres.getName());
			//log.debug(sres.getKeyPrefix()); // null
			//log.debug(sres.getCustom()); // false
			//log.debug(sres.getLabelPlural()); // 参加行動リレーション
			//log.debug(sres.getQueryable()); //true
			cobjlist.add(cobj);
		}
		log.debug("[describeGlobal] cobjlist size is : " + cobjlist.size());
		return cobjlist;
	}
}
