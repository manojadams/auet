package sag.is;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ISInfo {
	
	private static int lowComplexity = 102400;
	private static int medComplexity = 204800;
	private static int highComplexity;
	
	static int msg_count=0;
	static int remote_count=0;
	static int acl_count=0;
	static int jms_count=0;
	static int users_count=0;
	
	private static int record=0;
	private static int flow=0;
	private static int java=0;
	private static int wsdl=0;
	private static int adapter=0;
	private static int webM=0;
	
	private String sag_directory;
	
	public ISInfo(String sag_directory){
		this.sag_directory = sag_directory;
	}
	
	public static void main(String[] args) 
	{
		
		messaging_info();
		remoteServers_info();
		acl_info();
		users_info() ;
		jmsQUEUE_info();
		count_info();
		System.out.println("This file includes the information about the listed assets :");
		System.out.println("1. Messaging Alias count : "+msg_count);
		System.out.println("2. JMS QUEUE count : "+jms_count);
		System.out.println("3. ACL count : "+acl_count);
		System.out.println("4. Remote Seerver count : "+remote_count);
		System.out.println("5. Users count : "+users_count);
		System.out.println("Please check the output.txt file for detailed information-------------->");
	}
	  public static void saves(String str1,String str2) throws IOException
	  {
		   try
		   {
			   File writefile =new File("d:/output.txt");
			   
			   
		       //String filename= "c:/output.txt";
		       FileWriter fileWritter = new FileWriter(writefile,true);
		      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		       bufferWritter.write(str1+" : "+str2);
		       bufferWritter.newLine();
		       bufferWritter.close();
		   }
		   catch(IOException ioe)
		   {
		       System.err.println("IOException: " + ioe.getMessage());
		   } 
		}
	  
	  public static void newLine() throws IOException{
		   try
		   {
			   File writefile =new File("d:/output.txt");
			   FileWriter fileWritter = new FileWriter(writefile,true);
		       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		       bufferWritter.newLine();
		       bufferWritter.write("==========================================================");
		       bufferWritter.newLine();
		       bufferWritter.close();
		   }
		   catch(IOException ioe)
		   {
		       System.err.println("IOException: " + ioe.getMessage());
		   } 
		}
	  
	//get messaging details  
	public static void messaging_info() 
	{
		try 
		{
			String messagingPath = "d:/SoftwareAG98/IntegrationServer/instances/default/config/messaging.cnf";
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
    						saves("Alias Name",aliasName);
    					}
    		
    				else if(name.equalsIgnoreCase("type"))
    					{
    						String typeName=eElement.getTextContent();
    						saves("Type",typeName);
    					}
    		
    				else if(name.equalsIgnoreCase("brokerName"))
    					{
    						String brokerName=eElement.getTextContent();
    						saves("Broker Name",brokerName);
    						newLine();
    					}
    		
    				else if(name.equalsIgnoreCase("um_rname"))
    					{	
    						String um_rName=eElement.getTextContent();
    						saves("UM Realm Name",um_rName);
    						newLine();
    					}
    		
    		
    			}    	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}

		
	}
	
	//get remote server details
	
	public static void remoteServers_info() 
	{
		try 
		{
			String remote="d:/SoftwareAG98/IntegrationServer/instances/default/config/remote.cnf";
			File fXmlFile = new File(remote);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("value");
			newLine();
			for (int temp = 0; temp < nList.getLength(); temp++) 
    			{
    				Node nNode = nList.item(temp);
    				Element eElement = (Element) nNode;
    				String name="";
    				name=eElement.getAttribute("name");
    				    		
    				if(name.equalsIgnoreCase("alias"))
    					{
    						String aliasName=eElement.getTextContent();
    						saves("Remote server Alias Name",aliasName);
    						
    					}
    			
    			}  
			newLine();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		
	}
	
	//get users
		public static void acl_info() 
		{
			try 
			{
				String acl="D:/SoftwareAG98/IntegrationServer/instances/default/config/acls.cnf";
				File fXmlFile = new File(acl);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("value");
				newLine();
				for (int temp = 0; temp < nList.getLength(); temp++) 
	    			{
	    				Node nNode = nList.item(temp);
	    				Element eElement = (Element) nNode;
	    				String name="";
	    				name=eElement.getAttribute("name");
	    				    		
	    				if(name.equalsIgnoreCase("name"))
	    					{
	    						String aclName=eElement.getTextContent();
	    						saves("Acl",aclName);
	    						
	    					}
	    			
	    			}  
				newLine();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			
		}
	
	//get users
	public static void users_info() 
	{
		try 
		{
			String remote="d:/SoftwareAG98/IntegrationServer/instances/default/config/users.cnf";
			File fXmlFile = new File(remote);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("value");
			newLine();
			for (int temp = 0; temp < nList.getLength(); temp++) 
    			{
    				Node nNode = nList.item(temp);
    				Element eElement = (Element) nNode;
    				String name="";
    				name=eElement.getAttribute("name");
    				    		
    				if(name.equalsIgnoreCase("name"))
    					{
    						String userName=eElement.getTextContent();
    						saves("User",userName);
    						
    					}
    			
    			}  
			newLine();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	//get JMS queue
		public static void jmsQUEUE_info() 
		{
			try 
			{
				String messaging="d:/SoftwareAG98/IntegrationServer/instances/default/config/jms.cnf";
				File fXmlFile = new File(messaging);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("value");
				newLine();
				for (int temp = 0; temp < nList.getLength(); temp++) 
	    			{
	    				Node nNode = nList.item(temp);
	    				Element eElement = (Element) nNode;
	    				String name="";
	    				name=eElement.getAttribute("name");
	    				    		
	    				if(name.equalsIgnoreCase("aliasName"))
	    					{
	    						String aliasName=eElement.getTextContent();
	    						saves("JMS Alias Name",aliasName);
	    					}
	    			}    	
				newLine();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		//count the various info
		 public static void count_info(){	
			 /*int msg_count=0;
			 int remote_count=0;
			 int acl_count=0;
			 int jms_count=0;
			 int users_count=0;*/
			 
			 String msgAlias="Alias Name";
			 String remoteServer="Remote server Alias Name";
			 String acl="Acl";
			 String jms="JMS Alias Name";
			 String users="User";
			 
			 String line;
		     boolean result = false;
		     try {
		        	BufferedReader bf = new BufferedReader(new FileReader("d:/output.txt"));
		              while((line = bf.readLine()) != null)
		              {
		            	 
		            	  if(line.startsWith(msgAlias))
		            	  {
		            		  msg_count++;
		            		  
		            	  }
		            	  else if(line.startsWith(remoteServer))
		            	  {
		            		  remote_count++;
		            		  
		            	  }
		            	  else if(line.startsWith(acl))
		            	  {
		            		  acl_count++;
		            		  
		            	  }
		            	  else if(line.startsWith(jms))
		            	  {
		            		  jms_count++;
		            		  
		            	  }
		            	  else if(line.startsWith(users))
		            	  {
		            		  users_count++;
		            		  
		            	  }
		              }
		              newLine();
		              saves("Message Alias count",Integer.toString(msg_count));
		              saves("Remote Servers count",Integer.toString(remote_count));
		              saves("JMS QUEUE count",Integer.toString(jms_count));
		              saves("Users count",Integer.toString(users_count));
		              saves("Acl count",Integer.toString(acl_count));
		              newLine();
		              
		           }
		       catch(IOException e) 
		            {
			            e.printStackTrace();  
			        }
		     
		     }
		 
		 //get IS package instances
		 public String[] getPackageInstances(){
			 String pathToInstances = this.sag_directory+"/IntegrationServer/instances";
			 File file = new File(pathToInstances);
			 String[] instances = file.list(new FilenameFilter(){
				@Override
				public boolean accept(File current, String name) {
				    return new File(current, name).isDirectory();
				  }
			 });
			 return instances;
		 }
		 
		   
		   		   //go inside each dir and find node.nf and write its address in output2.txt
		   public static void getlist(File dir)
		   {
			   try {
					File[] files = dir.listFiles();
					for (File file : files) {
						if (file.isDirectory()) {
							String path = file.getCanonicalPath();
							//this print is not required
							//System.out.println("directory:" + file.getCanonicalPath());
							if(path.endsWith("node.ndf")){
								
								File writefile =new File("d:/output2.txt");
							    FileWriter fileWritter = new FileWriter(writefile,true);
							    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
							    bufferWritter.write(path);
							    bufferWritter.newLine();
							    bufferWritter.newLine();
							    bufferWritter.close();
							}
							getlist(file);
						} else {
							String path = file.getCanonicalPath();
							if(path.endsWith("node.ndf")){
								getServiceType(file.getCanonicalPath());
								File writefile =new File("d:/output2.txt");
							    FileWriter fileWritter = new FileWriter(writefile,true);
							    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
							    bufferWritter.write(path);
							    bufferWritter.newLine();
							    bufferWritter.newLine();
							    bufferWritter.close();
							}
							//System.out.println("     file:" + file.getCanonicalPath());
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		   
		   //save dir fn
		  /* public static void Save(String text,String type) throws IOException{
			   try
			   {
				   File file =new File("d:/output.txt");
			       //String filename= "c:/output.txt";
			       FileWriter fileWritter = new FileWriter(file,true);
			      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			       bufferWritter.write(type);
	    	        bufferWritter.write(text);
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
			       
			   }
			   catch(IOException ioe)
			   {
			       System.err.println("IOException: " + ioe.getMessage());
			   } 
			}*/
		   //save pkg count
		   public static void SavePkg(int count) throws IOException{
			   try
			   {
				   File file =new File("d:/output2.txt");
				   
				   
			       //String filename= "c:/output.txt";
			       FileWriter fileWritter = new FileWriter(file,true);
			      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			       bufferWritter.write("Package count: "+count);
			      // bufferWritter.write();
			      bufferWritter.newLine();
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
			       
			   }
			   catch(IOException ioe)
			   {
			       System.err.println("IOException: " + ioe.getMessage());
			   } 
			 }
		   
		   //save service type count into file output2.txt
		   public static void SaveserviceTypeCount() throws IOException{
			   try
			   {
				   File writefile =new File("d:/output2.txt");
				   
				   
			       //String filename= "c:/output.txt";
			       FileWriter fileWritter = new FileWriter(writefile,true);
			      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			       bufferWritter.write("Record count:"+record);
			       bufferWritter.write("WSDL count:"+wsdl);
			       bufferWritter.write("WebMethods count:"+webM);
			       bufferWritter.write("Adapter count:"+adapter);
			       bufferWritter.write("Flow count:"+flow);
			       bufferWritter.write("Java count:"+java);
			      // bufferWritter.write();
			      bufferWritter.newLine();
			        bufferWritter.newLine();
			        bufferWritter.close();
			       
			   }
			   catch(IOException ioe)
			   {
			       System.err.println("IOException: " + ioe.getMessage());
			   }
			 
			}  
		   
		    
		   
		   
		  //find node count
		   public static void find2() {
		    	 String searchString= "node.ndf";
		    	 String line;
		        boolean result = false;
		        
		        int count=0;
		        try {
		        	BufferedReader bf = new BufferedReader(new FileReader("d:/output2.txt"));
		              while((line = bf.readLine()) != null){
		            	  boolean value=false;
		            	  value=line.endsWith("node.ndf");
		            	  if(value)
		            	  {
		                count++;
		            	  }
		            }
		              File f = new File("d:/output2.txt");
				       FileWriter fileWritter = new FileWriter(f,true);
				      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
				       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				       bufferWritter.write("Nodes count: "+count);
				      // bufferWritter.write();
				      bufferWritter.newLine();
		    	        bufferWritter.newLine();
		    	        bufferWritter.close();
				       
		        }
		        catch(IOException e) {
		            e.printStackTrace();      
		        }
		        	        
		    }
		   
		
		   //find service type by going into each node.ndf file when it is encountered
		   public static String getServiceType(String file) throws IOException{
			   
			   File fXmlFile = new File(file); 
			   try {
			 
			    	
//			    	fXmlFile.get
			    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			    	Document doc = dBuilder.parse(fXmlFile);
			    	doc.getDocumentElement().normalize();
			    	
			    	//root element
			    	//System.out.println(doc.getNodeName());
			    	
			    	//get the list of all nodes of type value
			    	NodeList nList = doc.getElementsByTagName("value");
			    	
			    	
			    	for (int temp = 0; temp < nList.getLength(); temp++) {
			    		Node nNode = nList.item(temp);
			    		Element eElement = (Element) nNode;
			    		String name="";
			    		name=eElement.getAttribute("name");
			    				    		
			    		if(name.equalsIgnoreCase("svc_type")|name.equalsIgnoreCase("node_type")){
			    		String nodeType=eElement.getTextContent();	
			    		
			    		
			    			if(nodeType.equals("record"))
			    			{
			    				return "record";
			    				//System.out.println("record");
			    				//break;
			    			}
			    			else if(nodeType.equals("java"))
			    			{
			    				return "java";
			    				//System.out.println("java");
			    				//break;
			       			}
			    			else if(nodeType.equals("flow"))
			    			{
			    				return "flow";
			    				//System.out.println("flow");
			    				//break;
			    			}
			    			else if(nodeType.equals("webServiceDescriptor"))
			    			{
			    				return "wsdl";
			    				//System.out.println("flow");
			    				//break;
			    			}
			    			else if(nodeType.equals("AdapterService"))
			    			{
			    				return "adapter";
			    				//System.out.println("flow");
			    				//break;
			    			}
			    			else if(nodeType.equals("webMethods"))
			    			{
			    				return "webM";
			    				//System.out.println(webM);
			    				//break;
			    			}
			
			    		
			    		}
			    		
			    		}
			    	//System.out.println("Record count:"+record);
					//System.out.println("Flow count:"+flow);
					//System.out.println("Java count:"+java);
					
			    }
			    catch (Exception e) {
			    		e.printStackTrace();
			    		String str = FileUtils.readFileToString(fXmlFile);
			    		String flow_match = "<value name=\"svc_type\">flow</value>";
			    		String java_match = "<value name=\"svc_type\">java</value>";
			    		String doc_match = "<value name=\"node_type\">record</value>";
			    		if(str.matches(flow_match)) return "flow";
			    		else if(str.matches(java_match)) return "java";
			    		else if(str.matches(doc_match)) return "doc_match";
			    }
			
			return "";    
			    
			    
			  }
		   
		//gets the size of a file
		public static long getServiceSize(String file){
			
			File f = new File(file);
			if(f.isFile()) return f.length();
			else return 0;
		}
		
		//gets the service complexity
		public static String getServiceComplexity(long size){
			
			if((int)size<=lowComplexity) return "low";
			else if((int)size>lowComplexity && (int)size<=medComplexity) return "medium";
			else if((int)size>medComplexity) return "high";
			
			return "low";
		}
		
		//gets flow service count
		public static int getFlowServiceCount(ArrayList<SvcNode> svcs){
			int flowCount = 0;
			for(SvcNode s:svcs){
				if(s.nodeType.equals("flow"))
					flowCount++;
			}
			return flowCount;
		}
		
		//gets java service count
		public static int getJavaServiceCount(ArrayList<SvcNode> svcs){
			int javaCount = 0;
			for(SvcNode s:svcs){
				if(s.nodeType.equals("java"))
					javaCount++;
			}
			return javaCount;
		}
		
		public String[] getInstances(){
			
			String ISPathInstances =  this.sag_directory+"/IntegrationServer/instances";
			File instances = new File(ISPathInstances);
			File[] isInstanceFiles ;
			String[] isInstances = new String[100];
			try {
			if(instances.exists()){
				isInstanceFiles = instances.listFiles();
				int count=0;
				for(File f:isInstanceFiles){
					if(f.isDirectory())
						isInstances[count++] = f.getCanonicalPath();
				}
			}
			} catch(Exception e){
				e.printStackTrace();
			}
			return isInstances;
			
		}
		
}