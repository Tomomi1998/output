package kitamuralab;
 
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class excel_ex2 {
	public static void main(String[] args) {
 
		String excelread = "sampleExcelread.xlsx";
		XSSFWorkbook workbook  = null;
		try{
			workbook = new XSSFWorkbook(excelread);
			//左端のシートを取得する
			Sheet sheet = workbook.getSheetAt(0);
 
			//取得する値のセル設定
			Row row0 = sheet.getRow(2);  //3行目
			Row row1 = sheet.getRow(1);  //2行目
			Row row2 = sheet.getRow(4);  //5行目
			Row row3 = sheet.getRow(0);  //1行目
 
			Cell cell0 = row0.getCell(2);   //C列
			Cell cell1 = row1.getCell(4);   //E列
			Cell cell2 = row2.getCell(5);   //F列
			Cell cell3 = row3.getCell(0);   //A列
 
			//取得した値をコンソールに出力する
			System.out.println(cell0.getNumericCellValue());
			System.out.println(cell1.getStringCellValue());
			System.out.println(cell2.getNumericCellValue());
			System.out.println(cell3.getStringCellValue());
 
		    System.out.println("完了。。");
	    }catch(IOException e){
	      System.out.println(e.toString());
	    }finally{
	      try {
	        if (workbook != null) {
	            	workbook.close();
	          }
	      }catch(IOException e){
	        System.out.println(e.toString());
	      }
	    }
	}
}