package sag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetProductDetails {
	
	public GetProductDetails(String directory, String OSName){
		
		String filePath = directory+"/install/history/";
		String fileName = "history.txt";
		Path path = Paths.get(filePath+fileName);
		//System.out.println(path.toString());
		File file = null;
		BufferedReader reader = null;
		String text = null;
		
		String productDetails = "";
		String productVersion = "";
		String productName = "";
		
		SagProductList sagList = new SagProductList();
		if(OSName.equals("Windows")){
			try {
				file = new File(path.toString());
				reader = new BufferedReader(new FileReader(file));
				while((text = reader.readLine())!= null){
					if(text.contains("(install)")){
						productDetails = text;text = reader.readLine();
						
						if(text.contains("version:")){
							productVersion = text;text = reader.readLine();
							if(text.contains("canonical:")){
								productName = text;
							} else {
								productName = "";
							}
						} else {
							productVersion = "";
						}
						sagList.add(productDetails, productVersion, productName);
					}
				}
			} catch(Exception e){
				System.out.print("Installation log file not found");
			}
		} else if(OSName.equals("Unix")){
			
		}
		//print out the sag product list
		sagList.exportToExcel();;
	}
}

