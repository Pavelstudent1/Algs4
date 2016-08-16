package concurrent5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrent1.Utils;

public class AvoidBlockingExample {
	
	static final Lock mutex = new ReentrantLock();
	
	
	public static void main(String[] args) {

		System.out.println("Start");
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start heavy task");
				mutex.lock();
				try{
					while(true){
						//heavy task
					}
				}finally{
					mutex.unlock();
				}
			}
		}, "heavy job").start();
		
		Utils.pause(1000);
		
		System.out.println("Trying to lock mutex");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mutex.lockInterruptibly();
					try{
						System.out.println("Hello there!");
					}finally{
						mutex.unlock();
					}
				} catch (InterruptedException e) {
					System.out.println("we are interrupted");
				}
			}
		}, "second thread");
		thread.start();
		
		Utils.pause(1000);
		
		System.out.println("interrupt");
		thread.interrupt();
//		System.out.println("stop");
//		thread.stop();
		
	}
	
}
