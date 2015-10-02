package concurrent1;

public class ThreadExample {
	
	private static final class Task implements Runnable {
		@Override
		public void run() {				
			System.out.println(Thread.currentThread());
		}
	}

	public static void main(String[] args) {
		
		System.out.println(Thread.currentThread());
		new Thread(){
			@Override
			public void run() {				
				System.out.println(Thread.currentThread());
			};
		}.start();
		
		new Thread(new Task()).start();
		
	}
}
