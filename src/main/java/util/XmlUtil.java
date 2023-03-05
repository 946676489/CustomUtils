package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {

	public static void main(String[] args) throws DocumentException {
		Document document = DocumentHelper.createDocument();
		Element ele = createElement("aaa", 
				createAttributes("id","0","name","ele0","type","xml","value","a"),
				"xvcvvvdfasdfdsa");
		document.add(ele);
		System.out.println(document.asXML());
	}
	
	public static Element createElement(String lable,String[][] attributes,String text) {
		Element ele = DocumentHelper.createElement(lable);
		for (String[] attr : attributes) {
			ele.add(DocumentHelper.createAttribute(ele, attr[0], attr[1]));
		}
		ele.add(DocumentHelper.createText(text));
		return ele;
	}

	public static String[][] createAttributes(String...attributes) {
		if(attributes.length%2!=0) {
			throw new RuntimeException("attributes's length must be multi of 2");
		}
		String[][] res = new String[attributes.length/2][];
		for (int i = 0; i < res.length; i++) {
			res[i] = new String[] {attributes[i*2], attributes[i*2+1]};
		}
		return res;
	}
}
