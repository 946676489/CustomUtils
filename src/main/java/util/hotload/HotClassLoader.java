package util.hotload;

import java.util.List;

import util.FileUtils;

public class HotClassLoader extends ClassLoader {
	private static List<String> classes;
	public HotClassLoader(String classPath) {
		super(ClassLoader.getSystemClassLoader());
		if(classes==null) {
			classes = FileUtils.getAllFiles(classPath, "*.class");
		}
	}
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> cls = findClass(name);
		if(cls!=null) {
			return cls;
		}
		return super.loadClass(name, resolve);
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classdata = loadClassData(name);
        return classdata==null?null:this.defineClass(name, classdata, 0, classdata.length);
	}
	private byte[] loadClassData(String name) {
		for (String cls : classes) {
			if(!cls.replace('\\', '.').endsWith(name+".class")) {
				continue;
			}
			return FileUtils.readBytes(cls);
		}
		return null;
	}
}

