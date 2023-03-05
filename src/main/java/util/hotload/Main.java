package util.hotload;

import java.lang.reflect.Method;

public class Main {
	private static final String classPath = "E:\\Develop\\Workspace\\MyTools\\target";
	public static void main(String[] args) throws Exception {
		while (true) {
			ClassLoader cl = new HotClassLoader(classPath);
			Class<?> c = cl.loadClass("util.hotload.T1");
			Method m = c.getDeclaredMethod("main", String[].class);
			m.invoke(null, new Object[] {new String[] {}});
			System.gc();
			Thread.sleep(2000);
		}
	}
}
