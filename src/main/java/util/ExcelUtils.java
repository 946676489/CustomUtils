package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLDocumentPart.RelationPart;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;

public class ExcelUtils {
	private static POILogger logger = POILogFactory.getLogger(XSSFWorkbook.class);
	/** avoid creating too many CellStyles */
	private static final Object[] LINKSTYLE = new Object[2];
	private ExcelUtils() {
	}
	public static Workbook readExcel(String file) {
		try {
			Workbook workbook = XSSFWorkbookFactory.create(new File(file));
			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void copySheet(XSSFSheet srcSheet,XSSFWorkbook destBook,String destName) throws Exception {
		XSSFWorkbook srcBook = srcSheet.getWorkbook();
		int idx = srcBook.getSheetIndex(srcSheet);
		String name = destName;
		if(name==null) {
			name = srcSheet.getSheetName();
		}
		name = getUniqueSheetName(destBook, name);
		if(srcBook==destBook) {
			srcBook.cloneSheet(idx, name);
			return;
		}
		srcBook.setSheetName(idx, name);
//		Field parent = POIXMLDocumentPart.class.getDeclaredField("parent");
//		parent.setAccessible(true);
//		parent.set(srcSheet, destBook);
		Field sheets = XSSFWorkbook.class.getDeclaredField("sheets");
		sheets.setAccessible(true);
		List<XSSFSheet> newSheets = (List<XSSFSheet>) sheets.get(destBook);
		newSheets.add(srcSheet);
		sheets.set(destBook, newSheets);
		destBook.setCommitted(false);
	}
	private static String getUniqueSheetName(Workbook workbook, String srcName) {
		if(workbook.getSheet(srcName)==null) {
			return srcName;
		}
		String res = null; 
		Matcher m = Pattern.compile("\\(?\\d+\\)?$").matcher(srcName);
		if(m.find()) {
			String s = m.group();
			if(s.charAt(0)=='(') {
				res = m.replaceAll("("+(Integer.parseInt(s.substring(1,s.length()-1))+1)+")");
			} else {
				res = m.replaceAll(String.valueOf(Integer.parseInt(s)+1));
			}
		} else {
			res = srcName+"(1)";
		}
		if(workbook.getSheet(res)!=null) {
			res = getUniqueSheetName(workbook, res);
		}
		return res;
	}
	public static XSSFSheet copySheet2(XSSFSheet srcSheet,XSSFWorkbook destBook,String destName) throws Exception {
		XSSFWorkbook srcBook = srcSheet.getWorkbook();
		int idx = srcBook.getSheetIndex(srcSheet);
		String name = destName;
		if(name==null) {
			name = srcSheet.getSheetName();
		}
		name = getUniqueSheetName(destBook, name);
        XSSFSheet clonedSheet = destBook.createSheet(name);
        Method addRelation = XSSFWorkbook.class.getDeclaredMethod("addRelation", RelationPart.class, POIXMLDocumentPart.class);
        Method write = XSSFSheet.class.getDeclaredMethod("write", OutputStream.class);
        Method read = XSSFSheet.class.getDeclaredMethod("read", InputStream.class);
        addRelation.setAccessible(true);
        write.setAccessible(true);
        read.setAccessible(true);

        // copy sheet's relations
        List<RelationPart> rels = srcSheet.getRelationParts();
        // if the sheet being cloned has a drawing then remember it and re-create it too
        XSSFDrawing dg = null;
        for(RelationPart rp : rels) {
            POIXMLDocumentPart r = rp.getDocumentPart();
            // do not copy the drawing relationship, it will be re-created
            if(r instanceof XSSFDrawing) {
                dg = (XSSFDrawing)r;
                continue;
            }

            addRelation.invoke(destBook,rp, clonedSheet);
        }

        try {
            for(PackageRelationship pr : srcSheet.getPackagePart().getRelationships()) {
                if (pr.getTargetMode() == TargetMode.EXTERNAL) {
                    clonedSheet.getPackagePart().addExternalRelationship
                            (pr.getTargetURI().toASCIIString(), pr.getRelationshipType(), pr.getId());
                }
            }
        } catch (InvalidFormatException e) {
            throw new POIXMLException("Failed to clone sheet", e);
        }


        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            srcSheet.write(out);
        	write.invoke(srcSheet, out);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray())) {
//                clonedSheet.read(bis);
            	read.invoke(clonedSheet, bis);
            }
        } catch (IOException e){
            throw new POIXMLException("Failed to clone sheet", e);
        }
        CTWorksheet ct = clonedSheet.getCTWorksheet();
        if(ct.isSetLegacyDrawing()) {
            logger.log(POILogger.WARN, "Cloning sheets with comments is not yet supported.");
            ct.unsetLegacyDrawing();
        }
        if (ct.isSetPageSetup()) {
            logger.log(POILogger.WARN, "Cloning sheets with page setup is not yet supported.");
            ct.unsetPageSetup();
        }

        clonedSheet.setSelected(false);

        // clone the sheet drawing along with its relationships
        if (dg != null) {
            if(ct.isSetDrawing()) {
                // unset the existing reference to the drawing,
                // so that subsequent call of clonedSheet.createDrawingPatriarch() will create a new one
                ct.unsetDrawing();
            }
            XSSFDrawing clonedDg = clonedSheet.createDrawingPatriarch();
            // copy drawing contents
            clonedDg.getCTDrawing().set(dg.getCTDrawing().copy());

            // Clone drawing relations
            List<RelationPart> srcRels = srcSheet.createDrawingPatriarch().getRelationParts();
            for (RelationPart rp : srcRels) {
            	addRelation.invoke(destBook, rp, clonedDg);
            }
        }
        for(XSSFPictureData pic:srcBook.getAllPictures()) {
        	destBook.addPicture(pic.getData(), pic.getPictureType());
        }
        return clonedSheet;
	}
	public static void setHyperLink(Cell cell, String linkAddress, HyperlinkType hyperlinkType) {
		Workbook book = cell.getSheet().getWorkbook();
		CreationHelper helper = book.getCreationHelper();
		Hyperlink link = helper.createHyperlink(hyperlinkType);
		link.setAddress(linkAddress);
		cell.setHyperlink(link);

		CellStyle hyperLinkStyle = (CellStyle) LINKSTYLE[1];
		if (LINKSTYLE[0] != book) {
			hyperLinkStyle = book.createCellStyle();
			LINKSTYLE[0] = book;
			LINKSTYLE[1] = hyperLinkStyle;
		}
		Font cellFont = book.createFont();
		cellFont.setUnderline((byte) 1);
		cellFont.setColor(IndexedColors.BLUE.index);
		hyperLinkStyle.setFont(cellFont);
		cell.setCellStyle(hyperLinkStyle);
	}
	public static void outExcell(Workbook workbook, String outPath) {
		try {
			workbook.write(new FileOutputStream(outPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
