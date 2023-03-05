package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PickNum {

	public static void main(String[] args) {
		m2();
	}

	private static void m1() {
		String ss = "fds323(3)";
		if(ss.matches(".*\\(\\d+\\)$")) {
			int pos = ss.lastIndexOf('(');
			System.out.println(ss.substring(0,pos+1)+(Integer.parseInt(ss.substring(pos+1,ss.length()-1))+1)+')');
		} else {
			System.out.println(ss+"(1)");
		}
	}
	private static void m2() {
		String ss = "fdsfdas3gdf&*__(32)";
		Matcher m = Pattern.compile("\\(?\\d+\\)?$").matcher(ss);
		if(m.find()) {
			String s = m.group();
			if(s.charAt(0)=='(') {
				s = m.replaceAll("("+(Integer.parseInt(s.substring(1,s.length()-1))+1)+")");
			} else {
				s = m.replaceAll(String.valueOf(Integer.parseInt(s)+1));
			}
			System.out.println(s);
		} else {
			System.out.println(ss);
		}
	}
}
