package concurrent1;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadAndExeptions {
	
	public static void main(String[] args) {
		
		System.out.println("start");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName());
					Utils.pause(1000);
					throw new RuntimeException();
				}
			}
		}).start();
		
		
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("bad exception cought!");
			}
		});
		
		
		System.out.println("finish");
		
	}
	
}
