package com.cotodel.hrms.auth.server.multi.datasource;

public class SetDatabaseTenent {
	
	public static void setDataSource(String employerCode) {
		
		MultiDbManager.setCurrentDBConnection(employerCode);
	}

}
