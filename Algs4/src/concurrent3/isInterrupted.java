package concurrent3;

import concurrent1.Utils;

public class isInterrupted {
	
	public static void main(String[] args) {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				long counter = 0;
				while(true){
					counter++;
				}
			}
			
		});
		
		thread.start();
		
		Utils.pause(1000);
		System.out.println(thread.isInterrupted());
		thread.interrupt();
		System.out.println(thread.isInterrupted());
		System.out.println(thread.isInterrupted());
	}
	
}
