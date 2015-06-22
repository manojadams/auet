package init;

import org.eclipse.swt.widgets.Display;

import com.db.DBRelated;

import gui.test113;
import sag.GetDBInfo;
import sag.GetProductDetails;

public class AUET {

	public static void main(String[] args) throws Exception{
		String dir = "c:/SoftwareAG";
		String os = "Windows";
		//GetProductDetails productDetails = new GetProductDetails(dir,os);
		//BPM process
		//GetDBInfo dbInfo = new GetDBInfo();
		//dbInfo.getTables();
		
//		try {
//			Display display = Display.getDefault();
//			test113 shell = new test113(display);
//			shell.open();
//			shell.layout();
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch()) {
//					display.sleep();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		DBRelated db = new DBRelated("");
	}
}
