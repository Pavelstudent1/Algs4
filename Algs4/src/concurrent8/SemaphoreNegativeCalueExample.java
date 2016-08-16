package concurrent8;

import java.util.concurrent.Semaphore;

import concurrent1.Utils;

public class SemaphoreNegativeCalueExample {
	
	public static void main(String[] args) {
		
		Semaphore sem = new Semaphore(-2);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				sem.acquireUninterruptibly();
				System.out.println("hello there!");
			}
		}).start();
		
		System.out.println("---------------");
		
		Utils.pause(1000);
		System.out.println("na start");
		sem.release();
		Utils.pause(1000);
		System.out.println("vnimanie");
		sem.release();
		Utils.pause(1000);
		System.out.println("marsh!");
		sem.release();
	}
}
