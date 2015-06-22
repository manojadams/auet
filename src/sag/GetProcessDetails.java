package sag;

public class GetProcessDetails {
	private String sag;
	public GetProcessDetails(String sag){
		this.sag = sag;
	}
	public void getProcessTableInfo(){
		String filepath = this.sag+"IntegrationServer/config/jdbc/pool/";
		String filename = "local.xml";
		
	}
}
