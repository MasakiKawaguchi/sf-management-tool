package com.SFManagementAntTask.common;

public class Const {

	public static String SESSION_ID;

	public static Integer MAX_POLL;

	public static Integer POLL_WAIT_MILLIS;

	public static String REPORT_ROOT;

	public static String SRC_ROOT;

	public static final String FT_YYYY_MM_DDTHHMMSS = "yyyy-MM-ddThh:mm:ss";

	public static final String[] META_TYPE = { "ApexClass", "ApexComponent", "ApexPage", "ApexTrigger", "ArticleType", "BaseSharingRule", "CriteriaBasedSharingRule", "CustomDataType", "CustomField",
	        "CustomObject", "DataCategoryGroup", "Flow", "InstalledPackage", "NamedFilter", "OwnerSharingRule", "PermissionSet", "Profile", "Queue", "RecordType", "RemoteSiteSetting", "Role",
	        "SharingReason", "Territory", "ValidationRules", "Workflow", "Setting", "AssignmentRules", "SharingCriteriaRule", "SharingOwnerRule", "SharingTerritoryRule" };

	// #############################
	// ## REST Query
	// #############################

	public static final String Q_START = "query/?q=";

	public static final String Q_METADATA_CONTAINER = Q_START + "Select+id,Name+from+MetadataContainer";

	public static final String Q_ASYNC_APEX_JOB = Q_START
	        + "Select+id,CreatedDate,CreatedById,JobType,ApexClassId,Status,JobItemsProcessed,TotalJobItems,NumberOfErrors,CompletedDate,MethodName,ExtendedStatus,ParentJobId,LastProcessed,LastProcessedOffset+from+AsyncApexJob";

	public static final String Q_APEX_CLASS = Q_START
	        + "Select+id,Name,FullName,NamespacePrefix,ApiVersion,Status,BodyCrc,IsValid,Body,LengthWithoutComments,Metadata,CreatedDate,CreatedById,LastModifiedDate,LastModifiedById,SystemModstamp,SymbolTable+from+ApexClass";
	public static final String Q_APEX_CLASS_MEMBER = Q_START + "Select+id,FullName,Body,LastSyncDate,Metadata+from+ApexClassMember";

	public static final String Q_APEX_TEST_RESULT = Q_START
	        + "Select+Id,SystemModstamp,TestTimestamp,Outcome,ApexClassId,MethodName,Message,StackTrace,AsyncApexJobId,QueueItemId,ApexLogId+from+ApexTestResult";

	public static final String Q_APEX_TEST_QUEUE_ITEM = Q_START + "Select+id,ApexClassId,ExtendedStatus,Status,ParentJobId+from+ApexTestQueueItem";

	public static final String Q_APEX_CODE_COVERAGE = Q_START + "Select+id,ApexTestClassId,TestMethodName,ApexClassorTriggerId,NumLinesCovered,NumLinesUncovered,Coverage+from+ApexCodeCoverage";

	public static final String Q_APEX_CODE_COVERAGE_AGGREGATE = Q_START + "Select+id,ApexClassorTriggerId,NumLinesCovered,NumLinesUncovered,Coverage+from+ApexCodeCoverageAggregate";

	public static final String Q_APEX_ORG_WIDE_COVERAGE = Q_START
	        + "Select+id,IsDeleted,CreatedDate,CreatedById,LastModifiedDate,LastModifiedById,SystemModstamp,PercentCovered+from+ApexOrgWideCoverage";

	public static final String Q_APEX_LOG = Q_START + "Select+id,Application,DurationMilliseconds,Location,LogLength,LogUserId,Operation,Request,StartTime,Status+from+ApexLog";

	public static final String Q_RUNTEST_ASYNCHRONOUS = "runTestsAsynchronous/?classids=";

	public static final String Q_RUNTEST_SYNCHRONOUS = "runTestsSynchronous/?classnames=";

	public static final String Q_APEX_MANI_FEST = "apexManifest";

	public static final String Q_EXECUTE_ANONYMOUS = "executeAnonymous/?anonymousBody=";

	public static final String Q_COMPLETIONS = "completions?type=";

	public static final String Q_SEARCH = "search/?q=";
}
