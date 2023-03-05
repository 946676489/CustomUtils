package wxapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Unweapp {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("用法:\\nunweapp 输入文件 [输出文件夹]");
		} else {
			File in = new File(args[0]);
			File outDir = args.length == 2 ? new File(args[1]) : new File(in.getAbsolutePath() + "_unpacked");
			run(in, outDir);
		}

	}

	private static void run(File in, File outDir) throws IOException {
		RandomAccessFile r = new RandomAccessFile(in, "r");

		try {
			if (r.readByte() != -66) {
				throw new RuntimeException("文件类型错误");
			}

			r.seek(14L);
			int fileCount = r.readInt();
			List<WxapkgItem> wxapkgItems = new ArrayList<>(fileCount);

			for (int i = 0; i < fileCount; ++i) {
				int nameLen = r.readInt();
				byte[] buf = new byte[nameLen];
				r.read(buf, 0, nameLen);
				String name = new String(buf, 0, nameLen);
				int start = r.readInt();
				int length = r.readInt();
				wxapkgItems.add(new WxapkgItem(name, start, length));
			}

			Iterator<WxapkgItem> var14 = wxapkgItems.iterator();

			while (var14.hasNext()) {
				WxapkgItem wxapkgItem = var14.next();
				File outFile = new File(outDir, wxapkgItem.getName());
				System.out.println(wxapkgItem);
				r.seek((long) wxapkgItem.getStart());
				byte[] buf = new byte[wxapkgItem.getLength()];
				r.read(buf, 0, wxapkgItem.getLength());
				write(outFile, buf);
			}
		} finally {
			r.close();
		}

		System.out.println("ok");
	}

	private static void write(File outFile, byte[] buf) throws IOException {
		if (!outFile.getParentFile().exists() && !outFile.getParentFile().mkdirs()) {
			throw new RuntimeException("无法创建文件:" + outFile.getParentFile().getAbsolutePath());
		} else {
			FileOutputStream out = new FileOutputStream(outFile);

			try {
				out.write(buf);
			} finally {
				out.close();
			}

		}
	}
}