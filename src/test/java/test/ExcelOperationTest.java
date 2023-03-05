package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.ExcelUtils;

public class ExcelOperationTest {

	public static void main(String[] args) {
		try {
			XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(true);
			
			Sheet sheet = workbook.createSheet("AAAAA");
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("AAAA");
			ExcelUtils.setHyperLink(cell, "B1", HyperlinkType.DOCUMENT);
			Cell cell1 = row.createCell(1);
			cell1.setCellValue("Link Test");
			ExcelUtils.setHyperLink(cell1, "A1", HyperlinkType.DOCUMENT);
			workbook.write(new FileOutputStream(new File("D:/temp/xxx.xlsx")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copySheet() {
		String srcWkb = "D:/Temp/OLD.xlsx";
		String desWkb = "D:/Temp/NEW.xlsx";
		XSSFWorkbook srcwb = (XSSFWorkbook) ExcelUtils.readExcel(srcWkb);
		XSSFWorkbook deswb = (XSSFWorkbook) ExcelUtils.readExcel(desWkb);
		int num = srcwb.getNumberOfSheets();
		for(int i=0;i<num;i++) {
			try {
				ExcelUtils.copySheet2(srcwb.getSheetAt(i), deswb, "NNUESS");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ExcelUtils.outExcell(srcwb, "D:/Temp/gen.xlsx");
	}

}
