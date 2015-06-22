package com.db;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBRelated {

	public DBRelated(String url){
		
		String oracleDriver = "com.wm.dd.jdbc.oracle.OracleDriver";
		String sqlServerDriver = "com.wm.dd.jdbc.sqlserver.SQLServerDriver";
		String oracleDatabaseType = "oracle";
		String sqlserverDatabaseType = "sqlserver";
		Statement stmt;
		ResultSet result;
		String url2 = "jdbc:wm:sqlserver://sprodb.eur.ad.sag:1433;databaseName=monitor82";
		String user = "monitor82";
		String pass = "monitor82";
		String[] urlParts = url.split(":");
		String dbType = "sqlserver";
		try {
			if(dbType.equals("sqlserver")){
				Class.forName(sqlServerDriver);
				Connection conn = DriverManager.getConnection(url2,user,pass);
				this.unlockConnection(conn);
				stmt = conn.createStatement();
				result = stmt.executeQuery("select distinct processkey from dbo.WMPROCESSDEFINITION");
				while(result.next()){
					System.out.println("Yes, data is coming");
				}
				
			} else if(dbType.equals("oracle")){
				Class.forName(oracleDriver);
				Connection conn = DriverManager.getConnection(url);
			} else if(dbType.equals("db2")){
				
			}
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//unlocks a connection
	private static void unlockConnection(Connection con) {
		try {
			Class className = Class
					.forName("com.merant.datadirect.jdbc.extensions.ExtEmbeddedConnection");
			Method meth = className.getMethod("unlock",
					new Class[] { String.class });
			meth.invoke(con, new Object[] { "webMethods" });
		} catch (Exception ex) {
			System.out.println("Error in creating connection");
			throw new RuntimeException(ex);
		}
	}
}
