package com.lexisnexis.bridgerinsight.BridgerInsight_Web_Services_Interfaces_11_3;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

public class BridgerSearchSWIFT {
	
	public static void main(String[] args) {

		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		// CUSTOMIZE HERE !!!!
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		String clientID = "CLIENTID";	
		String userID 	= "USERID";
		String password = "PASSWORD";
		String myRole 	= "ROLE";
		String myPDS 	= "PDS";
		String myDiv	= "DIVISION";
		Boolean WriteDB	= true;	// True to send search results to the database
		
		// Sample Search Fields
		//String firstNameToSearchFor = "Rafael";
		//String lastNameToSearchFor = "Marquez";

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
			InputEFT eft = new InputEFT();
			
			/////////////////////////////////////////////////////////////////
			//Set up the name to search for or EFT search
			/////////////////////////////////////////////////////////////////
			eft.setType(EFTType.SWIFT);
			eft.setValue("{1:F01QBANAU4BAXXX0001563860}{2:I103QBANAU4BXXXXN}{3:{103:PDS}{113:AAAA}}{4:\r\n"
					+ ":20:WIRE1563860\r\n"
					+ ":23B:CRED\r\n"
					+ ":32A:180320AUD10,00\r\n"
					+ ":33B:AUD10,00\r\n"
					+ ":50K:/951200000002\r\n"
					+ "VIKTOR BOUT\r\n"
					+ "LEVEL 21 THE CHIFLEY TOWER\r\n"
					+ "2 CHIFLEY SQUARE\r\n"
					+ "SYDNEY, NSW, 2001 AU\r\n"
					+ ":57A://AU083088\r\n"
					+ "NATAAU3303M\r\n"
					+ ":59:/740522154\r\n"
					+ "BANK OF IRAN\r\n"
					+ "144 EDWARD STREET\r\n"
					+ "BRISBANE QLD 4000\r\n"
					+ "AUSTRALIA\r\n"
					+ ":70:H4ACCF6757\r\n"
					+ "TICKET ID 408357\r\n"
					+ ":71A:OUR\r\n"
					+ ":72:/ACC/NATAAU3303M\r\n"
					+ "//SETTLEMENT\r\n"
					+ "-}");
			//entity.setEntityType(InputEntityType.Individual);
			//entity.setName(new InputName());
			//entity.getName().setFirst( firstNameToSearchFor );
			//entity.getName().setLast( lastNameToSearchFor );
			//entity.getName().setFull("Donald Trump");
			
			/////////////////////////////////////////////////////////////////
			// Provide the Account Id.
			/////////////////////////////////////////////////////////////////
			//InputID id = new InputID();
			//InputID[] ids = new InputID[1];
			//id.setType(IDType.Account);
			//id.setNumber("5673234");
			//ids[0] = id;
			//entity.setIDs(ids);

			inputRec.setEFT(eft);
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
