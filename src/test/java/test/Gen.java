package test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class Gen {
	public static void main(String[] args) {
//		int[] arr = {0,3,1,9,17,1,12,0,3,4,5,8,11,12,13,16,19,20,21,2,3,7,15,23,3,7,2,6,10,14,18,22,25,6,1,24};
		String ss = Base64.getEncoder().encodeToString("进度款啥地方卡萨付款发的开始恢复开机的萨 ".getBytes());
		System.out.println(ss);
		System.out.println(new String(Base64.getDecoder().decode(ss)));
	}
	private static String decode(Integer[] arr) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			max = Integer.max(max,arr[i]);
		}
		int[] newArr = new int[max+1];
		int a,b;
		for (int i = 0; i < arr.length-1;) {
			a = i+1;
			b = i+2;
			for (int j = 0; j < arr[a]; j++) {
				newArr[arr[b+j]] = arr[i];
			}
			i += arr[a]+2;
		}
		StringBuilder sb0 = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < newArr.length;) {
			for (int j = 0; j < newArr[i]; j++) {
				sb0.append(newArr[i+1+j]); 
			}
			sb1.append((char)Integer.parseInt(sb0.toString()));
			sb0.setLength(0);
			i += newArr[i]+1;
		}
		return sb1.toString();
	}
	private static Integer[] encode(String s) {
		char[] cs = s.toCharArray();
		List<Integer> l = new ArrayList<>(cs.length*3);
		for (int i = 0; i < cs.length; i++) {
			String v = String.valueOf((int) cs[i]);
			char[] css = v.toCharArray();
			l.add(css.length);
			for (int j = 0; j < css.length; j++) {
				l.add((int)css[j]-48);
			}
		}
		HashMap<Integer, List<Integer>> map = new HashMap<>();
		List<Integer> list = null;
		for (int i = 0; i < l.size(); i++) {
			if (map.containsKey(l.get(i))) {
				list = map.get(l.get(i));
			} else {
				list = new ArrayList<>();
				map.put(l.get(i), list);
			}
			list.add(i);
		}
		l.clear();
		map.forEach((k,v) -> {
			l.add(k);
			l.add(v.size());
			v.forEach(l::add);
		});
		return l.toArray(new Integer[l.size()]);
	}
}
