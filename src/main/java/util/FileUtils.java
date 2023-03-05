package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
	private FileUtils() {
	}
	public static List<String> getAllFiles(String dir, String glob) {
		return getAllFiles(Path.of(dir), glob);
	}
	public static List<String> getAllFiles(Path path, String glob) {
		if (glob == null) {
			glob = "*";
		}
		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
		List<String> list = null;
		try {
			DirectoryStream<Path> ds = Files.newDirectoryStream(path);
			for (Path p : ds) {
				if (!Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS)) {
					if (matcher.matches(p.getFileName())) {
						if (list == null || list.isEmpty()) {
							list = new ArrayList<String>();
						}
						list.add(p.toString());
					}
				} else {
					List<String> fs = getAllFiles(p, glob);
					if (fs.isEmpty()) {
						continue;
					} else if (list == null) {
						list = fs;
					} else {
						list.addAll(fs);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list == null ? Collections.emptyList() : list;
	}

	public static List<String> readLines(String file, String... charset) {
		String cs = "UTF-8";
		if (charset.length > 0) {
			cs = charset[0];
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), cs))) {
			return br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readBytes(String file) {
		try (InputStream is = new FileInputStream(file)) {
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void write(String file, String str, boolean append, String... charset) {
		String cs = "UTF-8";
		if (charset.length > 0) {
			cs = charset[0];
		}
		File f = new File(file);
		if (f.exists()) {
			throw new RuntimeException(f + " has existed!");
		}
		if (!f.getParentFile().exists()) {
			f.mkdirs();
		}
		try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, append), cs))) {
			pw.println(str);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeLines(String file, List<String> lines, String... charset) {
		String cs = "UTF-8";
		if (charset.length > 0) {
			cs = charset[0];
		}
		File f = new File(file);
		if (f.exists()) {
			throw new RuntimeException(f + " has existed!");
		}
		if (!f.getParentFile().exists()) {
			f.mkdirs();
		}
		try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), cs))) {
			for (String line : lines) {
				pw.println(line);
			}
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getFileName(String file, boolean ext) {
		int len = file.length();
		int pos = len, dot = len;
		while (pos-- > 0) {
			char c = file.charAt(pos);
			if (dot == len && c == '.') {
				dot = pos;
			} else if (c == '\\' || c == '/') {
				pos++;
				break;
			}
		}
		if (ext) {
			return pos == -1 ? file : file.substring(pos);
		}
		return pos == -1 ? file.substring(0, dot) : file.substring(pos, dot);
	}
	public static String getFileExt(String file) {
		int len = file.length();
		int pos = len, dot = len;
		while (pos-- > 0) {
			char c = file.charAt(pos);
			if (c == '.') {
				dot = pos + 1;
				break;
			}
		}
		return pos == -1 ? "" : file.substring(dot);
	}
	public static String unifyPath(String path, char separator) {
		if (separator == '/') {
			return path.replace('\\', '/');
		} else if (separator == '\\') {
			return path.replace('/', '\\');
		}
		throw new IllegalArgumentException("wrong argument: " + separator);
	}
}
