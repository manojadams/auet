package com;

import java.util.Map;
import java.util.TreeMap;

import org.export.ToExcel;

import sag.bpm.BPMNode;
import sag.broker.BrokerNode;
import sag.is.ISNode;
import sag.tn.TNNode;

public class GenerateReport {
	
	public GenerateReport(ISNode isNode, BrokerNode brokerNode, BPMNode bpmNode, TNNode tnNode){
		Map<String, Object[]> headers = new TreeMap<String, Object[]>();	//for headers
		Map<String, Object[]> data = new TreeMap<String, Object[]>();		//for data
		
		//set up basic data header lines
		headers.put("1",new Object[]{null,null, null,null,"","Complexity",""});
		headers.put("2",new Object[]{"S.No","IS Related Artifacts", "Present", "Count","Low","Medium","High","Estimated Time","Remarks"});
//		headers.put("18",new Object[]{"Trading Network Related Artifacts", "Present", "Count","Complexity","Time","Remarks"});
//		headers.put("30",new Object[]{"Broker", "Present", "Count","Complexity","Time","Remarks"});
//		headers.put("38",new Object[]{"Composite Application Related", "Present", "Count","Complexity","Time","Remarks"});
//		headers.put("46",new Object[]{"Process Model Details", "Present", "Count","Complexity","Time","Remarks"});
//		
		//is services count
		if(isNode.getPackages().length>0)
			data.put("3",new Object[]{1,"Number of IS packages", "Yes", isNode.getPackages().length,isNode.getPackageComplexity(),"",""});
		else
			data.put("3",new Object[]{1,"Number of IS packages", "No", "","","",""});
		if(isNode.flowCounts>0)
			data.put("4",new Object[]{2,"Flow services", "Yes", isNode.flowCounts,isNode.lowFlowComplexityCount,isNode.medFlowComplexityCount,isNode.highFlowComplexityCount});
		else
			data.put("4",new Object[]{2,"Flow services", "No", "","","",""});
		if(isNode.javaCounts>0)
			data.put("5",new Object[]{3,"Java services", "Yes", isNode.javaCounts,isNode.lowJavaComplexityCount,isNode.medJavaComplexityCount,isNode.highJavaComplexityCount});
		else
			data.put("5",new Object[]{3,"Java services", "No", "","","",""});
		if(isNode.getUsers().size()>0)
			data.put("7",new Object[]{5,"Users (LDAP Configuration)", "Yes", isNode.getUsers().size(),"","",""});
		else
			data.put("7",new Object[]{5,"Users (LDAP Configuration)", "No", "","","",""});
		if(isNode.getACLs().size()>0)
			data.put("8",new Object[]{6,"ACL's", "Yes", isNode.getACLs().size(),"","",""});
		else
			data.put("8",new Object[]{6,"ACL's", "No", "","","",""});
		if(isNode.getRemoteServers().size()>0)
			data.put("10",new Object[]{8,"Remote Servers", "Yes", isNode.getRemoteServers().size(),"","",""});
		else
			data.put("10",new Object[]{8,"Remote Servers", "No", "","","",""});
		
		data.put("6",new Object[]{4,"", "", "","","",""});
		data.put("9",new Object[]{7,"", "", "","","",""});
		data.put("11", new Object[]{9,"Web Services","","","","","",""});
		data.put("12", new Object[]{10,"Extended Settings","","","","",""});
		headers.put("13", new Object[]{null,"Automatic Upgrade Estimation Time","","","","","","45 minutes"});
		//TN data
		
		//create export filename data
		ToExcel exportFile = new ToExcel("wm_Upgrade_report.xlsx", "AssetsToBeMigrated");
		
		//export header items
		exportFile.exportToExcel(headers,1);
		
		//export  data items;
		exportFile.exportToExcel(data);
		exportFile.createPieChart();
		//write out data
		exportFile.exportFile();
		
	}
}
