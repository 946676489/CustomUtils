package util.hotload;

import java.lang.reflect.Method;

public class HotLoadUtil {
	private static final String classPath = "E:\\Develop\\Workspace\\MyTools\\target";
	public static void main(String[] args) throws Exception {
//		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//		service.scheduleWithFixedDelay(() ->invoke("util.hotload.TestHotLoad", "display",(byte) 2), 0,3, TimeUnit.SECONDS);
		Class<?> cls0 = getHotLoadClass("util.hotload.TestHotLoad");
		Class<?> cls1 = ClassLoader.getSystemClassLoader().loadClass("util.hotload.TestHotLoad");
		System.out.println(cls0.getClassLoader());
		System.out.println(cls1.getClassLoader());
		System.out.println(TestHotLoad.class.getClassLoader());
	}

	public static Class<?> getHotLoadClass(String name) {
		ClassLoader cl = new HotClassLoader(classPath);
		try {
			Class<?> cls = cl.loadClass(name);
			return cls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Object invoke(String className,String method,Object... args) {
		Class<?> cls = getHotLoadClass(className);
		try {
			Object instance = cls.getDeclaredConstructor().newInstance();
			Method[] ms = cls.getDeclaredMethods();
			for (Method m : ms) {
				if(method.equals(m.getName())) {
					return m.invoke(instance,args);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
