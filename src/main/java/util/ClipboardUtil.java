package util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Objects;

import util.format.DataFormatter;
import util.format.TableData;
import util.format.TableData.Type;

public class ClipboardUtil {
	private ClipboardUtil() {
	}
	private static final Clipboard BOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public static void main(String[] args) {
		TableData[] datas = new TableData[10];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = TableData.createData("A"+i,Type.values()[i%3],new String[] {"a"+i,"b"+i,"c"+i,"2021/7/3  11:55:34"});
		}
		String html = DataFormatter.formate2Excel(datas);
//		setText(DataFormatter.formate2Excel(datas));
		setClipboard(new HtmlTransfer("XXXXXXXXXXXXXXXXXXXXXXX", html), DataFlavor.plainTextFlavor);
		System.out.println(getData(DataFlavor.allHtmlFlavor));
//		System.out.println(getData(DataFlavor.allHtmlFlavor));
	}
	public static String getData(DataFlavor dataFlavor) {
		Object data = "";
		try {
			data = BOARD.getData(dataFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return data.toString();
	}
	public static String getText() {
		return getData(DataFlavor.stringFlavor);
	}
	public static void setClipboard(Object data, DataFlavor dataFlavor) {
		if (dataFlavor == DataFlavor.stringFlavor) {
			StringSelection ss = new StringSelection(Objects.toString(data));
			BOARD.setContents(ss, null);
		} else if (dataFlavor == DataFlavor.allHtmlFlavor || dataFlavor == DataFlavor.fragmentHtmlFlavor
				|| dataFlavor == DataFlavor.selectionHtmlFlavor) {
			BOARD.setContents((HtmlTransfer)data, null);
		} else {
			throw new RuntimeException(new UnsupportedFlavorException(dataFlavor));
		}
	}
	public static void setText(String data) {
		setClipboard(data, DataFlavor.stringFlavor);
	}
}
