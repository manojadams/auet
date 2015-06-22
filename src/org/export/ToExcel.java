package org.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartData;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.jfree.data.general.DefaultPieDataset;


public class ToExcel {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFSheet pieSheet;
	private String filename;
	private String sheetName;
	private int fileCount = 0; 
	
	//number of columns
	private int colNum=0;
	
	//for styles headers
	private String fontFamily;
	private int fontHeight;
	private IndexedColors fontColor;
	private IndexedColors bgColor;
	
	//for data items
	private int dataFontHeight;
	private IndexedColors lowDataBgColor;
	private IndexedColors medDataBgColor;
	private IndexedColors highDataBgColor;
	
	//export to excel
	public ToExcel(String filename,String sheetName){
		this.filename = filename;
		this.sheetName = sheetName;
		this.workbook = new XSSFWorkbook();
        sheet = this.workbook.createSheet(sheetName);
        pieSheet = this.workbook.createSheet("Pie Chart");
        
        //set header style data
        this.bgColor = IndexedColors.DARK_TEAL;
        this.fontColor = IndexedColors.WHITE;
        this.fontFamily = "Times new Roman";
        this.fontHeight = 20;
        
        //for data items
        this.dataFontHeight = 14;
        this.lowDataBgColor = IndexedColors.LIGHT_GREEN;
        this.medDataBgColor = IndexedColors.LIGHT_ORANGE;
        this.highDataBgColor = IndexedColors.BROWN;
        //set number of columns present 
        this.colNum = 9;
	}
	
	public void exportToExcel(Map<String,Object[]> data){
		
		//set data styles
		XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) this.dataFontHeight);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        
        //data styles for low complexity
        XSSFFont font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) this.dataFontHeight);
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(lowDataBgColor.getIndex());
        style2.setFillPattern(style2.SOLID_FOREGROUND);
        style2.setFont(font2);
        
        //data styles for medium complexity
        XSSFFont font3 = workbook.createFont();
        font3.setFontHeightInPoints((short) this.dataFontHeight);
        XSSFCellStyle style3 = workbook.createCellStyle();
        style3.setFillForegroundColor(medDataBgColor.getIndex());
        style3.setFillPattern(style3.SOLID_FOREGROUND);
        style3.setFont(font3);
        
        //data styles for high complexity
        XSSFFont font4 = workbook.createFont();
        font4.setFontHeightInPoints((short) this.dataFontHeight);
        font4.setColor(IndexedColors.WHITE.getIndex());
        XSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(highDataBgColor.getIndex());
        style4.setFillPattern(style4.SOLID_FOREGROUND);
        style4.setFont(font4);
        
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        for (String key : keyset)
        {
            Row row = sheet.createRow(Integer.valueOf(key));
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr){
            	
               Cell cell = row.createCell(cellnum++);
               
               if(obj instanceof String){
                    cell.setCellValue((String)obj);
                    if(cellnum==5)
                    	cell.setCellStyle(style2);
                    else if(cellnum==6)
                    	cell.setCellStyle(style3);
                    else if(cellnum==7)
                    	cell.setCellStyle(style4);
                    else 
                    	cell.setCellStyle(style);
               }
                else if(obj instanceof Integer){
                    cell.setCellValue((Integer)obj);
                    if(cellnum==5)
                    	cell.setCellStyle(style2);
                    else if(cellnum==6)
                    	cell.setCellStyle(style3);
                    else if(cellnum==7)
                    	cell.setCellStyle(style4);
                    else 
                    	cell.setCellStyle(style);
                }
            }
        }
      
	}
	//@overloaded method for headers (for styles)
	public void exportToExcel(Map<String,Object[]> data, int headers){
		
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) this.fontHeight);
        font.setFontName(this.fontFamily);
        font.setColor(this.fontColor.getIndex());
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(this.bgColor.getIndex());
        style.setFillPattern(style.SOLID_FOREGROUND);
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        for (String key : keyset)
        {
            Row row = sheet.createRow(Integer.valueOf(key));
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
            	
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String){
                    cell.setCellValue((String)obj);
                    cell.setCellStyle(style);
               }
                else if(obj instanceof Integer){
                    cell.setCellValue((Integer)obj);
                    cell.setCellStyle(style);
                }
            }
        }
	      
		}
		
		//writes file to specified location
		//default location is the location of this program
		public void exportFile(){
			
			//adjust the column widths
			for(int col=0;col<this.colNum;col++)
				sheet.autoSizeColumn(col);
			this.sheet.addMergedRegion(new CellRangeAddress(
			        13, //first row (0-based)
			        13, //last row  (0-based)
			        1, //first column (0-based)
			        6  //last column  (0-based)
			));
	        
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
		        	this.fileCount++;
		        	String filename2;
		        	if(filename.contains("."))
		        		filename2 = filename.replaceAll("(.*)(\\.[a-z]*)","$1("+fileCount+")$2");
		        	else filename2 = filename.replaceAll("(.*)","$1("+fileCount+")");
		        	try {
		        		FileOutputStream out = new FileOutputStream(new File(filename2));
		        		workbook.write(out);
			            out.close();
			            System.out.println("File report "+filename2+" created. ");
		        	} catch(Exception e1){
		        		this.exportFile();
		        		e1.printStackTrace();
		        	}
		            e.printStackTrace();
		        }
		}
		
		public void createPieChart(){
			
			  /* At the end of this step, we have a worksheet with test data, that we want to write into a chart */
            /* Create a drawing canvas on the worksheet */
            XSSFDrawing xlsx_drawing = pieSheet.createDrawingPatriarch();
            /* Define anchor points in the worksheet to position the chart */
            XSSFClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
            /* Create the chart object based on the anchor point */
            XSSFChart my_pie_chart = xlsx_drawing.createChart(anchor);
            /* Define legends for the line chart and set the position of the legend */
            XSSFChartLegend legend = my_pie_chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.BOTTOM);  
            /* Create data for the chart */
            
            //ChartData data2 = my_line_chart.getChartDataFactory().
            DefaultPieDataset pieData = new DefaultPieDataset();
            Number abc = 0;
            pieData.setValue("abc", abc);
            //LineChartData data = my_line_chart.getChartDataFactory().createLineChartData();     
            /* Define chart AXIS */
//            ChartAxis bottomAxis = my_line_chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
//            ValueAxis leftAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
//            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);  
            
            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(pieSheet, new CellRangeAddress(0, 0, 0, 4));
            ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(pieSheet, new CellRangeAddress(1, 1, 0, 4));
            ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(pieSheet, new CellRangeAddress(2, 2, 0, 4));
            ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(pieSheet, new CellRangeAddress(3, 3, 0, 4));
            
            /* Add chart data sources as data to the chart */
           
//            data.addSeries(xs, ys1);
//            data.addSeries(xs, ys2);
//            data.addSeries(xs, ys3);
//            
            /* Plot the chart with the inputs from data and chart axis */
//            my_pie_chart.plot(pieData);

            
		}
}
