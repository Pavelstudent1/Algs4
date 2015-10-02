package concurrent2;

import concurrent1.Utils;

public class SharedStaticExample {
	
	static String message;
	static class Task implements Runnable {
		
		private String _message;
		private int _millis;
		
		public Task(String message, int millis) {
			_message = message;
			_millis = millis;
		}
		
		@Override
		public void run() {
			while(true){
				message = _message;
				Utils.pause(_millis);
			}
		}
		
	}
	public static void main(String[] args) {
		
		new Thread(new Task("First", 2000)).start();
		new Thread(new Task("Second", 2500)).start();
		new Thread(new Task("Third", 2900)).start();
		
		while(true){
			Utils.pause(1000);
			System.out.println(message);
		}
		
	}
	
}
