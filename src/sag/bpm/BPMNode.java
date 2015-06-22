package sag.bpm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class BPMNode {

	//table informations
	//table1 wmprocessdefinition,wmstepdefinition,wmsteptransitiondefinition
	public BPMNode(String BPMPath){
		
	}
	public Boolean validate(){
		return true;
	}
	//public void getDbInfo(String url) {
	public static void main(String[] args){
		String temp = "jdbc:wm:sqlserver://sprodb.eur.ad.sag:1433";
		try {
		Connection conn = DriverManager.getConnection(temp,"td82","td82");
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		while (rs.next()) {
		  System.out.println(rs.getString(3));
		}
		
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
