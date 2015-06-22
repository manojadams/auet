package sag.is;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


//represents one package instance
public class ISNode {
	
	public Boolean isValid = false;
	public Boolean includeWMPackages = false;
	private String ISPath;
	private String pathToInstance;
	private String pathToInstancePkg;
	private String[] packages;
	private int packageCount;
	private ArrayList<String> nodes;
	private ArrayList<String> users;
	private ArrayList<String> remoteServers;
	private ArrayList<String> acls;
	private ArrayList<String> jmsQueue;
	private ArrayList<SvcNode> svcNodes;
	public int javaCounts;	
		public int lowJavaComplexityCount;
		public int medJavaComplexityCount;
		public int highJavaComplexityCount;
	public int flowCounts;	
		public int lowFlowComplexityCount;
		public int medFlowComplexityCount;
		public int highFlowComplexityCount;
	public int docCounts;
		public int lowDocComplexityCount;
		public int medDocComplexityCount;
		public int highDocComplexityCount;
	private String packageComplexity;
	protected FileWriter logger;
	
	public ISNode(String ISPath, String ISInstance) throws IOException{//integration server path
		
		this.ISPath = ISPath;
		this.logger = new FileWriter("logger.txt",true);
		this.validate();
		this.includeWMPackages = true;
		if(this.isValid){
		if(ISInstance.equals("")||ISInstance==null){
			this.pathToInstancePkg = this.ISPath+"/packages";
			this.pathToInstance = this.ISPath;
		} else {
//			this.pathToInstancePkg = this.ISPath+"/instances/"+ISInstance+"/packages";
//			this.pathToInstance = this.ISPath+"/instances/"+ISInstance;
			this.pathToInstancePkg = ISInstance+"/packages";
			this.pathToInstance = ISInstance;
		}
		
		this.packages = this.processPackages();
		//this.packageCount = this.getPackageCount();
		
		//System.out.println(this.packageCount+this.packages.length);
		nodes = new ArrayList<String>();
//		this.getNodesList();
//		this.displayNodesList();
//		this.processMessagingInfo();
//		this.processAclInfo();
//		this.processRemoteServersInfo();
//		this.processUsersInfo();
//		this.processJmsQueue();
		}
	}
	
	//validate IS settings
	public void validate(){
		File file = new File(this.ISPath);
		if(!file.exists()){
			Shell sh = new Shell();
			MessageBox msgBx = new MessageBox(sh);
			msgBx.setMessage("No valid IS folder");
			this.isValid = false;
		}
		else	
		this.isValid = true;
	}
	
	//get the no. of packages
	protected int getPackageCount(){

		int count=0;
			
		if (this.packages.length == 0){
				System.out.println("The directory is empty");
		} 
		else {
			for (String aFile : this.packages){
				count++;
			}
		}
		return count;
	}
	
	//get all package names inside package folder of a particular instance
	protected String[] processPackages(){
		
		File dir=new File(this.pathToInstancePkg);		
		return dir.list();
		
	}
//find nodes
	public void getNodesList(Text details) throws IOException{
		
		if(this.includeWMPackages){
			for(String node:this.packages){
				String pathToNode = this.pathToInstancePkg+"/"+node+"/ns";
				this.getNodes(new File(pathToNode),details);
			}
		}
		else{
			for(String node:this.packages){
				if(node.startsWith("Wm")) continue;
				String pathToNode = this.pathToInstancePkg+"/"+node+"/ns";
				//System.out.println(pathToNode);
				this.getNodes(new File(pathToNode),details);
			}
		}
	}
	//gets node.ndf files
	public void getNodes(File dir,Text details){
		File[] files = dir.listFiles();
		if(files!=null)
		for(File f:files){
			if(f.isDirectory())
				this.getNodes(f,details);
			else if(f.toString().endsWith("node.ndf")){
				this.nodes.add(f.toString());
				details.append(f.toString()+"\n");
			}
		}
	}
	//print nodes
	public void displayNodesList(){
		for(String s:this.nodes){
			System.out.println(s);
		}
	}
	
	//gets service types in node.ndf files
	public void processSeviceTypes(){
		
	}
	
	//get messaging details  
	protected void processMessagingInfo() 
	{
		try 
		{
			String messagingPath = this.pathToInstance+"/config/messaging.cnf";
			File fXmlFile = new File(messagingPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("value");
			for (int temp = 0; temp < nList.getLength(); temp++) 
    			{
    				Node nNode = nList.item(temp);
    				Element eElement = (Element) nNode;
    				String name="";
    				name=eElement.getAttribute("name");
    				    		
    				if(name.equalsIgnoreCase("aliasName"))
    					{
    						String aliasName=eElement.getTextContent();
    						//saves("Alias Name",aliasName);
    						System.out.println(aliasName);
    					}
    		
    				else if(name.equalsIgnoreCase("type"))
    					{
    						String typeName=eElement.getTextContent();
//    						saves("Type",typeName);
    						System.out.println(typeName);
    					}
    		
    				else if(name.equalsIgnoreCase("brokerName"))
    					{
    						String brokerName=eElement.getTextContent();
//    						saves("Broker Name",brokerName);
    						System.out.println(brokerName);
    					}
    		
    				else if(name.equalsIgnoreCase("um_rname"))
    					{	
    						String um_rName=eElement.getTextContent();
//    						saves("UM Realm Name",um_rName);
    						System.out.println(um_rName);
    					}
    		
    		
    			}    	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		
	}
	
	protected void processRemoteServersInfo() 
			{
	   			remoteServers = new ArrayList<String>();
				try 
				{
					String remoteServerPath = this.pathToInstance+"/config/remote.cnf";
					File fXmlFile = new File(remoteServerPath);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("value");
					
					for (int temp = 0; temp < nList.getLength(); temp++) 
		    			{
		    				Node nNode = nList.item(temp);
		    				Element eElement = (Element) nNode;
		    				String name="";
		    				name=eElement.getAttribute("name");
		    				    		
		    				if(name.equalsIgnoreCase("alias"))
		    					{
		    						String aliasName = eElement.getTextContent();
//			    						saves("Remote server Alias Name",aliasName);
		    						System.out.println(aliasName);
		    						if(aliasName!=null && !aliasName.equals("")) remoteServers.add(aliasName);
		    					}
		    			
		    			}  
					
					
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}

				
				}	
	 //get users
	protected void processUsersInfo() 
	{
		try 
		{
			//String[] tempUsers;
			users = new ArrayList<String>();
			String userInfoPath = this.pathToInstance+"/config/users.cnf";
			File fXmlFile = new File(userInfoPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("value");
			for (int temp = 0; temp < nList.getLength(); temp++) 
    			{
    				Node nNode = nList.item(temp);
    				Element eElement = (Element) nNode;
    				String name="";
    				name=eElement.getAttribute("name");
    				    		
    				if(name.equalsIgnoreCase("name"))
    					{
    						String userName = eElement.getTextContent();
    						if(userName!=null && !userName.equals("")) users.add(userName);
    						System.out.println(userName);
    						
    					}
    			
    			} 
			
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	//get acls
	protected void processAclInfo(){
		
		acls = new ArrayList<String>(); 
				try 
				{
					String aclPath = this.pathToInstance+"/config/acls.cnf";
					File fXmlFile = new File(aclPath);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("value");
					
					for (int temp = 0; temp < nList.getLength(); temp++) 
		    			{
		    				Node nNode = nList.item(temp);
		    				Element eElement = (Element) nNode;
		    				String name="";
		    				name=eElement.getAttribute("name");
		    				    		
		    				if(name.equalsIgnoreCase("name"))
		    					{
		    						String aclName=eElement.getTextContent();
		    						System.out.println(aclName);
		    						if(aclName!=null && !aclName.equals("")) acls.add(aclName);
		    						
		    					}
		    			
		    			}  
					
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}

				
			}
	//get JMS queue
	public void processJmsQueue() 
	{
		jmsQueue = new ArrayList<String>();
		try 
		{
			String jmsPath = this.pathToInstance+"/config/jms.cnf";
			File fXmlFile = new File(jmsPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("value");
			for (int temp = 0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				String name="";
				name=eElement.getAttribute("name");
				    		
				if(name.equalsIgnoreCase("aliasName"))
					{
						String aliasName=eElement.getTextContent();
//						saves("JMS Alias Name",aliasName);
						System.out.println(aliasName);
						if(aliasName!=null && !aliasName.equals("")) jmsQueue.add(aliasName);
						
					}
    			}    	
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void processNodes(){
		svcNodes = new ArrayList<SvcNode>();
		try{
		for(String n:nodes){
			String svcType = ISInfo.getServiceType(n);						//get service type
			System.out.println(svcType);
			this.logger.write(svcType);
			long svcSize = ISInfo.getServiceSize(n);						//get service size
			String svcComplexity = ISInfo.getServiceComplexity(svcSize);	//get service complexity
			SvcNode svcNode = new SvcNode(svcType,svcSize,svcComplexity,n);
			svcNodes.add(svcNode);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//gets all service counts
	public void getServiceCount(){
		
		int flowCount = 0, javaCount = 0, docCount = 0;
		for(SvcNode s:this.svcNodes){
			if(s.nodeType.equals("flow")){
				flowCount++;
				if(s.nodeComplexty.equals("low")){
					lowFlowComplexityCount++;
				}
				else if(s.nodeComplexty.equals("medium")){
					medFlowComplexityCount++;
				} 
				else if(s.nodeComplexty.equals("high")){
					highFlowComplexityCount++;
				}
			}
			else if(s.nodeType.equals("java")){
				javaCount++;
				if(s.nodeComplexty.equals("low")){
					lowJavaComplexityCount++;
				}
				else if(s.nodeComplexty.equals("medium")){
					medJavaComplexityCount++;
				} 
				else if(s.nodeComplexty.equals("high")){
					highJavaComplexityCount++;
				}
			}
			else if(s.nodeType.equals("doc")){
				docCount++;
				if(s.nodeComplexty.equals("low")){
					lowDocComplexityCount++;
				}
				else if(s.nodeComplexty.equals("medium")){
					medDocComplexityCount++;
				} 
				else if(s.nodeComplexty.equals("high")){
					highDocComplexityCount++;
				}
			}
			
		}
		this.flowCounts = flowCount;
		this.javaCounts = javaCount;
		this.docCounts = docCount;
	}
	
	//start processing IS node
	public void processIsNode(ProgressBar progressBar,Text details){
		this.packages = this.getPackages();
		this.packageCount = this.getPackageCount();
		nodes = new ArrayList<String>();
		try {
			
//			progressBar.setSelection(20);
			this.getNodesList(details);
//			progressBar.setSelection(40);
			this.processNodes();
			this.getServiceCount();
			//this.processSeviceTypes();
			//this.getNodesList(details);
			this.displayNodesList();
			this.processMessagingInfo();
			this.processAclInfo();
			this.processRemoteServersInfo();
			this.processUsersInfo();
			this.processJmsQueue();
//			progressBar.setSelection(100);
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	public ArrayList<String> getRemoteServers(){
		return this.remoteServers;
	}
	
	public ArrayList<String> getUsers(){
		return this.users;
	}
	public ArrayList<String> getACLs(){
		return this.acls;
	}
	public ArrayList<String> getJmsInfo(){
		return this.jmsQueue;
	}
	public String[] getPackages(){
		return this.packages;
	}
	public ArrayList<String> getNodes(){
		return this.nodes;
	}
	public ArrayList<SvcNode> getSvcNodes(){
		return this.svcNodes;
	}
	public String getPackageComplexity(){
		return this.packageComplexity;
	}
}
