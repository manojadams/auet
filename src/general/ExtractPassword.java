package general;

import com.wm.passman.PasswordManager;
import com.wm.passman.PasswordManagerException;
import com.wm.util.security.WmSecureString;
public class ExtractPassword implements PasswordManager{
	public String ePass;	//encrypted password
	public ExtractPassword(String ePass){
		this.ePass = ePass;
	}
	public String getPassword(){
//		PasswordManager pwd = new PasswordManager();
//		WmSecureString pass = PasswordManager.retrievePassword(this.ePass);
		return "";
	}

	public boolean removePassword(String pass){return true;}
	public void removeEncryptionCode(String code){}
	@Override
	public String adminHandlePrefix() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean handleInUse(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String retrieveEncryptionCode(String arg0)
			throws PasswordManagerException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public WmSecureString retrievePassword(String arg0)
			throws PasswordManagerException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean storePassword(String arg0, WmSecureString arg1)
			throws PasswordManagerException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean storePassword(String arg0, WmSecureString arg1, String arg2)
			throws PasswordManagerException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
