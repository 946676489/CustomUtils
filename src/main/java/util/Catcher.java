package util;

@FunctionalInterface
public interface Catcher<T extends Throwable> {
	void test() throws T;
}