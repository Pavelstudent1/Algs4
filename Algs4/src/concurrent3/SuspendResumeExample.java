package concurrent3;

import concurrent1.Utils;

/** ПОСЛЕДНЯЯ == java_140605_03 ближе к концу**/

public class SuspendResumeExample {

	public static void main(String[] args) {
		
		System.out.println("start");
		
		final Object mutex = new Object();
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				long counter = 0;
				synchronized (mutex) {
					while(true){
						Utils.pause(1000);
						System.out.println("working...");
					}					
				}
			}
		});
		
		thread.start();
		
		Utils.pause(2000);
		
		thread.suspend();	//поток останавливается, но всё ещё считается Running
		
		synchronized (mutex) {
			System.out.println("before");
			Utils.pause(20000);
			System.out.println("after");
		}
		
		
		thread.resume();
		
	}
}
