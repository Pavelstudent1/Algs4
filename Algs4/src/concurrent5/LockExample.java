package concurrent5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrent1.Utils;

/**
 * Код между lock()/unlock() аналог критической секции
 */
public class LockExample {

	static Lock lock = new ReentrantLock();

	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try {
					System.out.println("locked");
					Utils.pause(3000);
					if (args.length == 0) {
						throw new RuntimeException();
					}
				} finally { //необходим на случай Exception при активном lock 
					lock.unlock();
				}
				System.out.println("unlocked");
			}
		}).start();

		Utils.pause(100);

		lock.lock(); // start of critical section
		System.out.println("hello there");
		lock.unlock(); // end of critical section
	}
}
