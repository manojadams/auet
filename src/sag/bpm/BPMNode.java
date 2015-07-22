package sag.bpm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import auet.utils.Logger;
import sag.utils.DBConnector;

	
public class BPMNode {
	
	private int processModelCount;
	private int processModelStepCount;
	private int taskCount;
	
	private String query1 = "select count(distinct processkey) from dbo.wmprocessdefinition";
	private String query2 = "select count(distinct processkey) from dbo.wmstepdefinition";
	private String query3 = "select count(distinct instanceid) from dbo.wmprocesstask";
	private String query4 = "select count(distinct processkey) from dbo.wmprocesstaskstep";
	
	private ResultSet rs1;
	private ResultSet rs2;
	//table informations
	//table1 wmprocessdefinition,wmstepdefinition,wmsteptransitiondefinition
	public BPMNode(String BPMPath){
		String user = "pvdevwm";
		String pass = "pvdevwm";
		String url = "jdbc:wm:sqlserver://vmpvdb.eur.ad.sag:1433;databaseName=pvdevwm";
		DBConnector connector = new DBConnector(url,user,pass);
		Connection conn = connector.getConnection();
		if(conn==null) return;
		try {
			Statement stmt = conn.createStatement();
			rs1 = stmt.executeQuery(query1);
			conn.close();
			this.processModelCount = this.getCount(rs1);
			rs2 = stmt.executeQuery(query2);
			this.processModelStepCount = this.getCount(rs1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public Boolean validate(){
		return true;
	}
	//public void getDbInfo(String url) {
	public static void main(String[] args){
		BPMNode node = new BPMNode("");
		System.out.println(node.getModelCount());
		System.out.println(node.getModelStepsCount());
	}
	//counts the number of resultset
	private int getCount(ResultSet rs){
		
		int tempCount = 0;
		if(rs==null){
			return 0;
		}
		else{
			try {
				tempCount = rs.getInt(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.log("BPM:\tSQL exception");
			}
			return tempCount;
		}
	}

	public int getModelCount(){
		return this.processModelCount;
	}
	
	public int getModelStepsCount(){
		return this.processModelStepCount;
	}
}
