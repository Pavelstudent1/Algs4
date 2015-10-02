package concurrent2;

import concurrent1.Utils;

public class ShutdownHookExample {
	
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.out.println("working...");
					Utils.pause(2000);
				}
			}
		}).start();
		
	System.out.println(Runtime.getRuntime().availableProcessors());
	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		
		@Override
		public void run() {
			System.out.println("Shutting downnnnn.....");
		}
	}));
		
	}
}
