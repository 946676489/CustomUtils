package test;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import util.FileUtils;
import util.StringUtil;

public class T2 {

	public static void main(String[] args) {
		String dir = "D:\\Software\\UltraISO\\obb\\obb\\ucvdo\\local/";
		String vdoDir = "1573874343956";
		dir += vdoDir;
		List<String> fs = FileUtils.getAllFiles(dir, null);
		fs = fs.stream().map(s -> s.substring(s.lastIndexOf('\\')+1)).collect(Collectors.toList());
		fs.sort(StringUtil.naturalComparator());
		
		String s = "copy /b %s %s.mp4";
		StringJoiner joiner = new StringJoiner("+", "", "");
		for (String string : fs) {
			if(string.endsWith(".bat")||string.endsWith(".mp4")||string.endsWith(".m3u8")) {
				continue;
			}
//			System.out.println(string);
			joiner.add(string);
		}
		System.out.println(String.format(s, joiner,vdoDir));
		FileUtils.write(dir+"/0xx.bat", String.format(s, joiner,vdoDir),false);

	}

}
