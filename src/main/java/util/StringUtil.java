package util;

import java.util.Comparator;

public class StringUtil {
	private StringUtil() {
	}

	public static int getCharCnt(String s, char c) {
		if (s == null) {
			return 0;
		}
		int res = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				res++;
			}
		}
		return res;
	}
	
	public static Comparator<String> naturalComparator() {
		return new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if(o1.length()==o2.length()) {
					return o1.compareTo(o2);
				}
				return o1.length()-o2.length();
			}
		};
	}

	/**
	 * 
	 * @param sequence can be negative, it is same with 1 if 0
	 */
	public static int charIndex(String s, char c, int sequence) {
		if (s == null) {
			return 0;
		}
		if (s.indexOf(c) == -1) {
			return -1;
		}
		sequence = sequence == 0 ? 1 : sequence;
		int res = 0;
		if (sequence < 0) {
			for (int i = s.length() - 1; i > -1; i--) {
				if (s.charAt(i) == c) {
					sequence++;
				}
				if (sequence == 0) {
					res = i;
					break;
				}
			}
		} else {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == c) {
					sequence--;
				}
				if (sequence == 0) {
					break;
				}
				res++;
			}
		}
		return sequence == 0 ? res : -1;
	}

}
