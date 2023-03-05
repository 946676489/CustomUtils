package util;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.StringJoiner;

public class HtmlTransfer implements Transferable, ClipboardOwner{
	private static final int STRING = 0;
	private static final int HTML1 = 1;
	private static final int HTML2 = 2;
	private static final int HTML3 = 3;
    
	private static final DataFlavor[] flavors = {
	        DataFlavor.stringFlavor,
	        DataFlavor.allHtmlFlavor,
	        DataFlavor.fragmentHtmlFlavor,
	        DataFlavor.selectionHtmlFlavor
	    };
	private String html;
	private String text;

    public HtmlTransfer(String text,String html) {
        this.text = text;
        this.html = html;
    }
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors.clone();
	}
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (int i = 0; i < flavors.length; i++) {
			if (flavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(flavors[STRING])) {
			return (Object) text;
		} else if (flavor.equals(flavors[HTML1]) || flavor.equals(flavors[HTML2]) || flavor.equals(flavors[HTML3])) {
			if(html==null) {
				return "";
			}
			StringJoiner sj = new StringJoiner(System.lineSeparator());
			sj.add("Version:0.9")
			.add("StartHTML:00000097")
			.add("EndHTML:"+String.format("%08d", html.length()+120))
			.add("StartFragment:00000120")
			.add("EndFragment:"+String.format("%08d", html.length()+140))
			.add("<!--StartFragment -->")
			.add(html)
			.add("<!--EndFragment-->")
			;
			
			return sj.toString();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
}
