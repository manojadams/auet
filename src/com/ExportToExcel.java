package com;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportToExcel {
	

//export to excel
public static void exportToExcel(String filename, String sheetname, Map<String,Object[]> data){
	//Blank workbook	
	XSSFWorkbook workbook = new XSSFWorkbook();
	
	//Create a blank sheet
    XSSFSheet sheet = workbook.createSheet(sheetname);
    
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
        FileOutputStream out = new FileOutputStream(new File(filename));
        workbook.write(out);
        out.close();
        System.out.println("File report "+filename+" created. ");
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}
}