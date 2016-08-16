package concurrent4;

import concurrent1.Utils;

/**
 *	"Нечестность" захвата mutex потоками
 */
public class UnFairExample {
	
	final static Object mutex = new Object();
	
	static class Task implements Runnable{
		@Override
		public void run() {
			synchronized (mutex) {
				System.out.println(Thread.currentThread());
			}
		}
		
	}
	public static void main(String[] args) {
		
		synchronized (mutex) {
			new Thread(new Task()).start();
			Utils.pause(1000);
			new Thread(new Task()).start();
			Utils.pause(1000);
			new Thread(new Task()).start();
			Utils.pause(1000);
		}
	}
}
