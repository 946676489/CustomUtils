package util;

public class CommonUtils {
	private CommonUtils() {
	}

	public static boolean containsNPE(Catcher<NullPointerException> catcher) {
		try {
			catcher.test();
		} catch (NullPointerException e) {
			return true;
		}
		return false;
	}
}
