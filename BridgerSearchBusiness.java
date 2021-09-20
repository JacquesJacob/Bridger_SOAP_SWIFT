package com.lexisnexis.bridgerinsight.BridgerInsight_Web_Services_Interfaces_11_3;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

public class BridgerSearchBusiness {
	public static void main(String[] args) {

		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		// CUSTOMIZE HERE !!!!
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		String clientID = "ClientID";	
		String userID 	= "UserID";
		String password = "Password";
		String myRole 	= "Role";
		String myPDS 	= "PDS";
		String myDiv	= "Division";
		Boolean WriteDB	= true;	// True to send search results to the database
		
		// Sample Search Fields
		//String firstNameToSearchFor = "Rafael";
		//String lastNameToSearchFor = "Marquez";
		String fullNameToSearchFor = "Alpargatas";

		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		// Start context, configuration, and input Setup.
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		SearchInput Input = new SearchInput();

		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		// Set up the context
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
			ClientContext Context = new ClientContext();
			Context.setClientID( clientID );	
			Context.setUserID( userID );
			Context.setPassword( password );
			//Context.setClientReference("GUID here Please");
			//Context.setGLB(5);
			//Context.setDPPA(DPPAChoiceType.Choice3);
		
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		//Set up the configuration.
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
			SearchConfiguration Config = new SearchConfiguration();

			/////////////////////////////////////////////////////////////////
			//Set the alert assignment.
			/////////////////////////////////////////////////////////////////
			Config.setAssignResultTo(new AssignmentInfo());
			Config.getAssignResultTo().setRolesOrUsers(new String[1]);
			Config.getAssignResultTo().setType(AssignmentType.Role);
			Config.getAssignResultTo().getRolesOrUsers()[0]= myRole;

			/////////////////////////////////////////////////////////////////
			//Set DB Write instruction, Division and Predefined Search
			/////////////////////////////////////////////////////////////////
			Config.setWriteResultsToDatabase( WriteDB );
			Config.getAssignResultTo().setDivision( myDiv );
			Config.setPredefinedSearchName( myPDS );
			
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		//Set up the input information.
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
			InputRecord inputRec = new InputRecord();
			InputRecord[] inputRecArr = new InputRecord[1];
			InputEntity entity = new InputEntity();
			
			/////////////////////////////////////////////////////////////////
			//Set up the name to search for.
			/////////////////////////////////////////////////////////////////
			entity.setEntityType(InputEntityType.Business);
			entity.setName(new InputName());
			entity.getName().setFull( fullNameToSearchFor );
			//entity.getName().setLast( lastNameToSearchFor );
			// entity.getName().setFull("Donald Trump");
			
			/////////////////////////////////////////////////////////////////
			// Provide the Account Id.
			/////////////////////////////////////////////////////////////////
			InputID id = new InputID();
			InputID[] ids = new InputID[1];
			id.setType(IDType.Account);
			id.setNumber("5673234");
			ids[0] = id;
			entity.setIDs(ids);

			inputRec.setEntity(entity);
			inputRecArr[0] = inputRec;
			Input.setRecords(inputRecArr);

		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		// Do the actual call.
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
			XGServices locator;
	   
			try {
				locator = new XGServicesLocator();			
						
				/////////////////////////////////////////////////////////////////
				//Get the result
				/////////////////////////////////////////////////////////////////
				SearchResults result = locator.getBasicHttpBinding_ISearch().search(Context, Config, Input);
				ResultRecord[] results = result.getRecords();

				/////////////////////////////////////////////////////////////////
				// Display the results
				/////////////////////////////////////////////////////////////////
				System.out.println("Result size:" + results.length);
				for (int i=0; (results != null) && i < results.length;i++) {
					System.out.println("First result size: "+ results[i].getRecord());
					System.out.println("First result id: "+ results[i].getResultID());
					System.out.println("Full name: " + results[i].getRecordDetails().getName().getFull());
					System.out.println("Run ID:\t"+ results[i].getRunID());
					
					WLMatch[] recordMatches = results[i].getWatchlist().getMatches();
					for (int j=0; (recordMatches != null) && j < recordMatches.length;j++) {
						System.out.println("\nMatch #" + j);
						System.out.println("\tAutoFalsePositive : " + recordMatches[j].getAutoFalsePositive());
						System.out.println("\tBest Name         : " + recordMatches[j].getBestName());
						System.out.println("\tBest Name Score   : " + recordMatches[j].getBestNameScore());
						System.out.println("\tFalse Positive    : " + recordMatches[j].getFalsePositive());
						System.out.println("\tFile Name         : " + recordMatches[j].getFile().getName());
						System.out.println("\tTrue Match        : " + recordMatches[j].getTrueMatch());
						System.out.println("\tReason Listed     : " + recordMatches[j].getReasonListed());
					}
				}
				
			} catch (ServiceFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
