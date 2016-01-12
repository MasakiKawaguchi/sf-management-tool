package com.SFManagementAntTask.tooling.dao;

import com.SFManagementAntTask.common.ConnectionUtil;
import com.sforce.soap.tooling.ApexClassMember;
import com.sforce.soap.tooling.ApexCodeCoverage;
import com.sforce.soap.tooling.ApexExecutionOverlayAction;
import com.sforce.soap.tooling.ContainerAsyncRequest;
import com.sforce.soap.tooling.Coverage;
import com.sforce.soap.tooling.MetadataContainer;
import com.sforce.soap.tooling.Method;
import com.sforce.soap.tooling.Parameter;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.SaveResult;
import com.sforce.soap.tooling.SymbolTable;
import com.sforce.ws.ConnectionException;

public class ToolingDao extends Thread {

	public static void create() {
		String classId = "";
		String updatedClassBody = "public class Messages {\n"
		        + "public string SayHello(string fName, string lName) {\n"
		        + " return 'Hello ' + fName + ' ' + lName;\n" + "}\n"
		        + "}";
		//create the metadata container object
		MetadataContainer Container = new MetadataContainer();
		Container.setName("SampleContainer");
		MetadataContainer[] Containers = { Container };
		SaveResult[] containerResults = null;
		try {
			containerResults = ConnectionUtil.connectTooling().create(Containers);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (containerResults[0].getSuccess()) {
			String containerId = containerResults[0].getId();
			//create the ApexClassMember object
			ApexClassMember classMember = new ApexClassMember();
			//pass in the class ID from the first example
			classMember.setContentEntityId(classId);
			classMember.setBody(updatedClassBody);
			//pass the ID of the container created in the first step
			classMember.setMetadataContainerId(containerId);
			ApexClassMember[] classMembers = { classMember };
			SaveResult[] MembersResults = null;
			try {
				MembersResults = ConnectionUtil.connectTooling().create(classMembers);
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (MembersResults[0].isSuccess()) {
				//create the ContainerAsyncRequest object
				ContainerAsyncRequest request = new ContainerAsyncRequest();
				//if the code compiled successfully, save the updated class to the server
				//change to IsCheckOnly = true to compile without saving
				request.setIsCheckOnly(false);
				request.setMetadataContainerId(containerId);
				ContainerAsyncRequest[] requests = { request };
				SaveResult[] RequestResults = null;
				try {
					RequestResults = ConnectionUtil.connectTooling().create(requests);
				} catch (ConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (RequestResults[0].isSuccess()) {
					String requestId = RequestResults[0].getId();
					//poll the server until the process completes
					QueryResult queryResult = null;
					String soql = "SELECT Id, State, ErrorMsg FROM ContainerAsyncRequest where id= '" + requestId + "'";
					try {
						queryResult = ConnectionUtil.connectTooling().query(soql);
					} catch (ConnectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (queryResult.getSize() > 0) {
						ContainerAsyncRequest _request = (ContainerAsyncRequest) queryResult.getRecords()[0];
						while (_request.getState().toLowerCase() == "queued") {
							//pause the process for 2 seconds
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//poll the server again for completion
							try {
								queryResult = ConnectionUtil.connectTooling().query(soql);
							} catch (ConnectionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							_request = (ContainerAsyncRequest) queryResult.getRecords()[0];
						}
						//now process the result
						switch (_request.getState()) {
						case "Invalidated":
							break;
						case "Completed":
							//class compiled successfully
							//see the next example on how to process the SymbolTable
							break;
						case "Failed":
							break;
						case "Error":
							break;
						case "Aborted":
							break;
						}
					} else {
						//no rows returned
					}
				} else {
					System.out.println("Error: could not create ContainerAsyncRequest object");
					System.out.println(" The error reported was: " +
					        RequestResults[0].getErrors()[0].getMessage() + "\n");
				}
			} else {
				System.out.println("Error: could not create Class Member ");
				System.out.println(" The error reported was: " +
				        MembersResults[0].getErrors()[0].getMessage() + "\n");
			}
		} else {
			System.out.println("Error: could not create MetadataContainer ");
			System.out.println(" The error reported was: " +
			        containerResults[0].getErrors()[0].getMessage() + "\n");
		}
	}

	public static void query() {
		//use the ID of the class from the previous step
		String classId = "01pA00000036itIIAQ";
		QueryResult queryResult = null;
		String soql = "SELECT ContentEntityId, SymbolTable FROM ApexClassMember where ContentEntityId = '" + classId + "'";
		try {
			queryResult = ConnectionUtil.connectTooling().query(soql);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (queryResult.getSize() > 0) {
			ApexClassMember apexClass = (ApexClassMember) queryResult.getRecords()[0];
			SymbolTable symbolTable = apexClass.getSymbolTable();
			for (Method _method : symbolTable.getMethods()) {
				//here's the SayHello method
				String _methodName = _method.getName();
				//is the method Global, Public or Private?
				//String _methodVisibility = _method.visibility.ToString();
				//get the method's return type
				String _methodReturnType = _method.getReturnType();
				//get the fName & lName parameters
				for (Parameter _parameter : _method.getParameters()) {
					String _paramName = _parameter.getName();
					String _parmType = _parameter.getType();
				}
			}
		} else {
			//unable to locate class
		}
	}

	public static void test() {
		//use the ID of the class from the first sample.
		String classId = "01pA00000036itIIAQ";
		ApexExecutionOverlayAction action = new ApexExecutionOverlayAction();
		action.setExecutableEntityId(classId);
		action.setLine(3);
		//action.LineSpecified = true;
		action.setIteration(1);
		//action.IterationSpecified = true;
		ApexExecutionOverlayAction[] actions = { action };
		SaveResult[] actionResults = null;
		try {
			actionResults = ConnectionUtil.connectTooling().create(actions);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (actionResults[0].getSuccess()) {
			// checkpoint created successfully
		} else {
			System.out.println("Error: could not create Checkpoint ");
			System.out.println(" The error reported was: " +
			        actionResults[0].getErrors()[0].getMessage() + "\n");
		}
	}

	public static void Coverage() {
		ApexCodeCoverage acc = null; //Query for an ApexCodeCoverage object
		Coverage coverage = acc.getCoverage();
		int[] covered = coverage.getCoveredLines();
		int[] uncovered = coverage.getUncoveredLines();
		int percent = covered.length / (covered.length + uncovered.length);
		System.out.println("Total class coverage is " + percent + "%.");
	}
}
