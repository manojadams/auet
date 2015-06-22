package sag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

//parse XML file and extract information
public class GetDBInfo  {
	
	private String url;
	public GetDBInfo() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		XPathFactory xpathFac = XPathFactory.newInstance();
		XPath xpath = xpathFac.newXPath();
		
		try {
			XPathExpression xPress = xpath.compile("//value[@name='dbURL']");
			Document doc = builder.parse("local.xml");
			
			Object result = xPress.evaluate(doc, XPathConstants.NODESET);
			 NodeList nodes = (NodeList) result;
			
			//System.out.println(nodes.item(0).getNodeName());
			//System.out.println(nodes.item(0).getNodeValue());
			System.out.println(nodes.item(0).getTextContent());
			this.url = nodes.item(0).getTextContent();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//gets the database url
	public String getDBUrl(){
		
		return this.url;
	}
	
	//gets table info
	public String getTables() throws Exception{
		
		String url = this.url.replaceAll("(.*);(.*)","$1");
		String user = "mano";
		String pass = "mano";
		Connection con = DriverManager.getConnection(url,user,pass);
		DatabaseMetaData md = con.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		while (rs.next()) {
		  System.out.println(rs.getString(3));
		}
		return "true";
	}
}