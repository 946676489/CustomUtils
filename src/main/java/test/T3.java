package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//synchronized锁的原理
//通过synchronized关键字实现交替打印奇偶数
//创建两个线程，一个线程负责打印奇数，另一个线程打印偶数，两个线程竞争同一个对象锁，每次打印一个数字后释放锁，然后另一个线程拿到锁打印下一个数字
class A implements Runnable {
	@Override
	public void run() {
		for (int i = 1; i <= 100; i += 2) {
			T3.printFunc("A" + i);
		}
	};
};
class B implements Runnable {
	@Override
	public void run() {
		for (int i = 2; i <= 100; i += 2) {
			T3.printFunc("B" + i);
		}
	};
};
public class T3 {
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(a);
		service.submit(b);

		service.shutdown();
		try {
			service.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 请实现printFunc交替打印
	static void printFunc(String str) {
		synchronized (TimeUnit.DAYS) {
			TimeUnit.DAYS.notify();
			System.out.println(str);
			try {
				TimeUnit.DAYS.wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
