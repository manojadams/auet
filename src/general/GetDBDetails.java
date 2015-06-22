package general;

import java.io.File;

import com.softwareag.install.tools.ToolException;
import com.softwareag.is.tools.*;


public class GetDBDetails {
	private String pass;
	private String user;
	private String url;
	private String driverAlias;
	
	public GetDBDetails(String installDir){
		ISDBConfigTool dbc = null;
        try
        {
            dbc = new ISDBConfigTool();
            dbc.setServerDir(new File("c:/SoftwareAG/IntegrationServer"));
            dbc.setFunctionalAlias(null);
            dbc.readall();
//            if(dbc.is_fc != null)
//            {
//                System.out.println((new StringBuilder()).append("*** Fn config, ").append(dbc.is_fnAlias).toString());
//                System.out.println((new StringBuilder()).append("\tName : ").append(dbc.is_fc.getName()).toString());
//                System.out.println((new StringBuilder()).append("\tPool : ").append(dbc.is_fc.getConnPoolAlias()).toString());
//                System.out.println("***");
//            } else
//            {
//                System.out.println((new StringBuilder()).append("*** Fn config, ").append(dbc.is_fnAlias).append(" does not exist on disk").toString());
//            }
          //  System.out.println((new StringBuilder()).append("*** Pool config, ").append(dbc.is_poolAlias).toString());
            System.out.println((new StringBuilder()).append("\tName : ").append(dbc.getPoolName()).toString());
            System.out.println((new StringBuilder()).append("\tDriver : ").append(dbc.getDriverAlias()).toString());
            System.out.println((new StringBuilder()).append("\tUrl : ").append(dbc.getUrl()).toString());
            System.out.println((new StringBuilder()).append("\tUser : ").append(dbc.getUser()).toString());
            System.out.println((new StringBuilder()).append("\tPwd : ").append(dbc.getPassword()).toString());
            System.out.println("***");
//            dbc.setPoolName("TN Test pool");
////            dbc.setDriverAlias(IS_CJORA_ALIAS);
//            dbc.setUrl("jdbc:oracle");
//            dbc.setUser("test");
//            dbc.setPassword("testpassword");
//            System.out.println("going to sleep...manually replace files to simulate installer copy files action");
//            System.out.println("woke up..");
//            dbc.writeall();
            this.pass = dbc.getPassword();
            this.user = dbc.getUser();
            this.url = dbc.getUrl();
            this.driverAlias = dbc.getDriverAlias();
        }
        catch(ToolException e)
        {
            System.out.println((new StringBuilder()).append("Exception: ").append(e.getMessage()).toString());
        }
	}
	public String getPassword(){
		return this.pass;
	}
	
	
}
