package util.hotload;

public class T1 {
	public static String ss = "gfdg";
	public static void main(String[] args) {
		System.out.println("AAAA");
		TestHotLoad thl = new TestHotLoad();
		thl.display();
		System.out.println(ss);
	}

}
