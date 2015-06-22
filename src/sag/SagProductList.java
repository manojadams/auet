package sag;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SagProductList {
	
	private ArrayList<SagProduct> sagList; 
	private int size;
	
	class SagProduct {
		
		public String productDetails;
		public String version;
		public String canonical;
		
		public SagProduct(String productDetails, String version, String canonical ){
			this.productDetails = productDetails;
			this.version = version;
			this.canonical = canonical;
		}
	}
	
	public SagProductList(){
		this.sagList = new ArrayList<SagProduct>();
		this.size = 0;
	}
	//adds a new sag product
	public void add(String productDetails, String version, String canonical){
		SagProduct product = new SagProduct(productDetails,version,canonical);
		this.sagList.add(product);
	}
	//displays sag product list
	public void displayList(){
		if(sagList.size()>0)
		for(SagProduct s:this.sagList){
			System.out.println(s.productDetails);
			System.out.println(s.version);
			System.out.println(s.canonical);
		}
	}
	
	//export to excel
	public void exportToExcel(){
		//Blank workbook	
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("wM ProductSuite");
      //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1",new Object[]{"Product Details", "Version", "Canonical name"});
        int count = 2;
        if(sagList.size()>0)
        for(SagProduct s:this.sagList){
        	System.out.println(s.productDetails);
        	data.put(Integer.toString(count), new Object[]{s.productDetails,s.version,s.canonical});
			count++;
		}
        
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("C:/Sag_Products.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("File report Sag_Products.xlsx created. ");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
}
