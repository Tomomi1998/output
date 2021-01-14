package kitamuralab;



import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class excel_ex {
	public static void main(String[] args) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("test");
		Row row0 = sheet.createRow(0);
		Row row1 = sheet.createRow(1);
		Row row2 = sheet.createRow(2);
 
		Cell cell0 = row0.createCell(0);
		Cell cell1 = row1.createCell(1);
		Cell cell2 = row2.createCell(2);
 
		cell0.setCellValue("Excelを出力してみた");
		cell1.setCellValue(100 / 3.14);
		cell2.setCellValue(12345.9876);
 
	    FileOutputStream output = null;
	    try{
	    output = new FileOutputStream("sampleExcelout.xlsx");
	      workbook.write(output);
	      System.out.println("完了。。");
	    }catch(IOException e){
	      System.out.println(e.toString());
	    }finally{
	      try {
	    	  if (output != null) {
	    		  	output.close();
	    	      }
	        if (workbook != null) {
	            	workbook.close();
	          }
	      }catch(IOException e){
	        System.out.println(e.toString());
	      }
	    }
	}
}