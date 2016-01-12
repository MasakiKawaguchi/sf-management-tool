package com.SFManagementAntTask.metadata.dao;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.sforce.soap.metadata.CustomField;
import com.sforce.soap.metadata.CustomObject;
import com.sforce.soap.metadata.DeleteResult;
import com.sforce.soap.metadata.DeploymentStatus;
import com.sforce.soap.metadata.DescribeMetadataObject;
import com.sforce.soap.metadata.DescribeMetadataResult;
import com.sforce.soap.metadata.DescribeValueTypeResult;
import com.sforce.soap.metadata.FieldType;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.ListMetadataQuery;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.ReadResult;
import com.sforce.soap.metadata.SaveResult;
import com.sforce.soap.metadata.SharingModel;
import com.sforce.soap.metadata.UpsertResult;
import com.sforce.soap.metadata.ValueTypeField;
import com.sforce.ws.ConnectionException;

public class MetadataDao {

	private final static Logger log = LoggerFactory.getLogger(MetadataDao.class);

	public MetadataDao() {

	}

	public static void createCustomObjectSync() {
		String uniqueName = "MyCustomObject__c";
		final String label = "My Custom Object";
		CustomObject co = new CustomObject();
		co.setFullName(uniqueName);
		co.setDeploymentStatus(DeploymentStatus.Deployed);
		co.setDescription("Created by the Metadata API Sample");
		co.setEnableActivities(true);
		co.setLabel(label);
		co.setPluralLabel(label + "s");
		co.setSharingModel(SharingModel.ReadWrite);
		// The name field appears in page layouts, related lists, and elsewhere.
		CustomField nf = new CustomField();
		nf.setType(FieldType.Text);
		nf.setDescription("The custom object identifier on page layouts, related listsetc");
		nf.setLabel(label);
		nf.setFullName(uniqueName);
		co.setNameField(nf);
		SaveResult[] results = null;
		try {
			results = ConnectionUtil.connectMetadata().createMetadata(new Metadata[] { co });
		} catch (ConnectionException e) {
			log.error("[describeValueType]", e);
			new BuildException(e);
		}
		for (SaveResult r : results) {
			if (r.isSuccess()) {
				System.out.println("Created component: " + r.getFullName());
			} else {
				System.out
				        .println("Errors were encountered while creating "
				                + r.getFullName());
				for (com.sforce.soap.metadata.Error e : r.getErrors()) {
					System.out.println("Error message: " + e.getMessage());
					System.out.println("Status code: " + e.getStatusCode());
				}
			}
		}
	}

	public void readCustomObjectSync() {
		try {
			ReadResult readResult = ConnectionUtil.connectMetadata().readMetadata("CustomObject", new String[] {
			        "MyCustomObject1__c", "MyCustomObject2__c" });
			Metadata[] mdInfo = readResult.getRecords();
			System.out.println("Number of component info returned: " + mdInfo.length);
			for (Metadata md : mdInfo) {
				if (md != null) {
					CustomObject obj = (CustomObject) md;
					System.out.println("Custom object full name: " + obj.getFullName());
					System.out.println("Label: " + obj.getLabel());
					System.out.println("Number of custom fields: " + obj.getFields().length);
					System.out.println("Sharing model: " + obj.getSharingModel());
				} else {
					System.out.println("Empty metadata.");
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void updateCustomObjectSync() {
		try {
			CustomObject co = new CustomObject();
			String name = "MyCustomObject1";
			co.setFullName(name + "__c");
			co.setDeploymentStatus(DeploymentStatus.Deployed);
			co.setDescription("Updated description");
			co.setLabel(name + " Object Update");
			co.setPluralLabel(co.getLabel() + "s");
			co.setSharingModel(SharingModel.ReadWrite);
			// Name field with a type and label is required
			CustomField cf = new CustomField();
			cf.setType(FieldType.Text);
			cf.setLabel(co.getFullName() + " Name");
			co.setNameField(cf);
			SaveResult[] results = ConnectionUtil.connectMetadata().updateMetadata(new Metadata[] { co });
			for (SaveResult r : results) {
				if (r.isSuccess()) {
					System.out.println("Updated component: " + r.getFullName());
				} else {
					System.out
					        .println("Errors were encountered while updating "
					                + r.getFullName());
					for (com.sforce.soap.metadata.Error e : r.getErrors()) {
						System.out.println("Error message: " + e.getMessage());
						System.out.println("Status code: " + e.getStatusCode());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void upsertMetadataSample() {
		try {
			// Create custom object to upsert
			CustomObject co = new CustomObject();
			String name = "MyCustomObject";
			co.setFullName(name + "__c");
			co.setDeploymentStatus(DeploymentStatus.Deployed);
			co.setDescription("Upserted by the Metadata API");
			co.setEnableActivities(true);
			co.setLabel(name + " Object");
			co.setPluralLabel(co.getLabel() + "s");
			co.setSharingModel(SharingModel.ReadWrite);
			CustomField nf = new CustomField();
			nf.setType(FieldType.Text);
			nf.setLabel("CustomField1");
			co.setNameField(nf);
			// Upsert the custom object
			UpsertResult[] results = ConnectionUtil.connectMetadata().upsertMetadata(new Metadata[] { co });
			for (UpsertResult r : results) {
				if (r.isSuccess()) {
					System.out.println("Success!");
					if (r.isCreated()) {
						System.out.println("Created component: "
						        + r.getFullName());
					} else {
						System.out.println("Updated component: "
						        + r.getFullName());
					}
				} else {
					System.out
					        .println("Errors were encountered while upserting "
					                + r.getFullName());
					for (com.sforce.soap.metadata.Error e : r.getErrors()) {
						System.out.println("Error message: " + e.getMessage());
						System.out.println("Status code: " + e.getStatusCode());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void deleteCustomObjectSync() {
		try {
			DeleteResult[] results = ConnectionUtil.connectMetadata().deleteMetadata(
			        "CustomObject", new String[] { "MyCustomObject1__c",
			                "MyCustomObject2__c" });
			for (DeleteResult r : results) {
				if (r.isSuccess()) {
					System.out.println("Deleted component: " + r.getFullName());
				} else {
					System.out
					        .println("Errors were encountered while deleting "
					                + r.getFullName());
					for (com.sforce.soap.metadata.Error e : r.getErrors()) {
						System.out.println("Error message: " + e.getMessage());
						System.out.println("Status code: " + e.getStatusCode());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void renameCustomObjectSync() {
		try {
			SaveResult result = ConnectionUtil.connectMetadata().renameMetadata(
			        "CustomObject", "MyCustomObject1__c", "MyCustomObject1New__c");
			//			for (SaveResult r : results) {
			//				if (r.isSuccess()) {
			//					System.out.println("Renamed component: " + r.getName());
			//				} else {
			//					System.out.println("Errors were encountered while renaming " + r.getName());
			//					for (Error e : r.getErrors()) {
			//						System.out.println("Error message: " + e.getMessage());
			//						System.out.println("Status code: " + e.getStatusCode());
			//					}
			//				}
			//			}
			if (result.isSuccess()) {
				System.out.println("Renamed component: " + result.getFullName());
			} else {
				System.out.println("Errors were encountered while renaming " + result.getFullName());
				for (com.sforce.soap.metadata.Error e : result.getErrors()) {
					System.out.println("Error message: " + e.getMessage());
					System.out.println("Status code: " + e.getStatusCode());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	// ########################################
	// ## Utilty call
	// ########################################

	public void describeMetadata() {
		try {
			double apiVersion = 21.0;
			// Assuming that the SOAP binding has already been established.
			DescribeMetadataResult res = ConnectionUtil.connectMetadata().describeMetadata(apiVersion);
			StringBuffer sb = new StringBuffer();
			if (res != null && res.getMetadataObjects().length > 0) {
				for (DescribeMetadataObject obj : res.getMetadataObjects()) {
					sb.append("***************************************************\n");
					sb.append("XMLName: " + obj.getXmlName() + "\n");
					sb.append("DirName: " + obj.getDirectoryName() + "\n");
					sb.append("Suffix: " + obj.getSuffix() + "\n");
					sb.append("***************************************************\n");
				}
			} else {
				sb.append("Failed to obtain metadata types.");
			}
			System.out.println(sb.toString());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public static void describeValueType() {
		try {
			DescribeValueTypeResult result = ConnectionUtil.connectMetadata().describeValueType("{urn:metadata.tooling.soap.sforce.com}ApexClass");
			StringBuffer sb = new StringBuffer();
			for (ValueTypeField field : result.getValueTypeFields()) {
				sb.append("***************************************************\n");
				sb.append("Name: " + field.getName() + "\n");
				sb.append("MinOccurs: " + field.getMinOccurs() + "\n");
				sb.append("SoapType: " + field.getSoapType() + "\n");
				sb.append("***************************************************\n");
			}
			System.out.println(sb.toString());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public static void listMetadata() {
		try {
			ListMetadataQuery query = new ListMetadataQuery();
			query.setType("CustomObject");
			//query.setFolder(null);
			double asOfVersion = 35.0;
			// Assuming that the SOAP binding has already been established.
			FileProperties[] lmr = ConnectionUtil.connectMetadata().listMetadata(
			        new ListMetadataQuery[] { query }, asOfVersion);
			if (lmr != null) {
				for (FileProperties n : lmr) {
					System.out.println("Component type: " + n.getType());
					System.out.println("Component fullName: " + n.getFullName());
					System.out.println("Component getManageableState: " + n.getManageableState());
					System.out.println("Component getCreatedDate: " + n.getCreatedDate());
					System.out.println("Component getCreatedByName: " + n.getCreatedByName());
					System.out.println("Component getLastModifiedDate: " + n.getLastModifiedDate());
					System.out.println("Component getLastModifiedByName: " + n.getLastModifiedByName());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
