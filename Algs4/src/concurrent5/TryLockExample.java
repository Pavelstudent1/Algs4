package concurrent5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrent1.Utils;

public class TryLockExample {

	static Lock mutex = new ReentrantLock();

	public static void main(String[] args) {

		System.out.println("start");
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start heavy task");
				mutex.lock();
				try {
					while (true) {
						// heavy task
					}
				} finally {
					mutex.unlock();
				}
			}
		}, "heavy job").start();

		Utils.pause(1000);

		try {
			if (mutex.tryLock(2, TimeUnit.SECONDS)) {
				try {
					System.out.println("hello there");
				} finally {
					mutex.unlock();
				}
			} else {
				System.out.println("timeout...");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (mutex.tryLock()) {
			try {
				System.out.println("hello there");
			} finally {
				mutex.unlock();
			}
		} else {
			System.out.println("mutex is busy...");
		}

	}

}
