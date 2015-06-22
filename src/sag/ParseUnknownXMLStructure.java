package sag;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
 
public class ParseUnknownXMLStructure {
 
  public static void main(String argv[]) {
 
    try {
 
    	File fXmlFile = new File("d:/node.ndf");
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    	Document doc = dBuilder.parse(fXmlFile);
    	doc.getDocumentElement().normalize();
    	
    	//root element
    	System.out.println(doc.getNodeName());
    	
    	//get the list of all nodes of type value
    	NodeList nList = doc.getElementsByTagName("value");
    	int record,java,flow;
    	record=java=flow=0;
    	
    	for (int temp = 0; temp < nList.getLength(); temp++) {
    		Node nNode = nList.item(temp);
    		
    		//nNode.getNodeType();
    		Element eElement = (Element) nNode;
    		
    		//System.out.println(nNode.getNodeName());
    		    		
    		String name="";
    		name=eElement.getAttribute("name");
    		
    		System.out.println(name);
    		//System.out.println(eElement.getTextContent());
    		//System.out.println(eElement.getAttribute("name"));
    		
    		
    		if(name.equalsIgnoreCase("svc_type")){
    		String nodeType=eElement.getTextContent();	
    		
    		
    			if(nodeType.equals("record"))
    			{
    				record++;
    				//System.out.println("record");
    			}
    			else if(nodeType.equals("java"))
    			{
    				java++;
    				//System.out.println("java");
       			}
    			else if(nodeType.equals("flow"))
    			{
    				flow++;
    				//System.out.println("flow");
    			}
    		
    		
    		}
    		
    		}
    	System.out.println(record);
		System.out.println(flow);
		System.out.println(java);}catch (Exception e) {
    		e.printStackTrace();
    
    	
    	
    	
    	
    	
    	
    	}
  }
  }
    	