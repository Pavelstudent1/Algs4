package concurrent6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import concurrent1.Utils;

public class ExecutorsExample {

	
	private static final class Task implements Runnable {
		@Override
		public void run() {
			System.out.println("hello from " + Thread.currentThread());
			Utils.pause(5000);
			System.out.println("bye from " + Thread.currentThread());
		}
	}

	public static void main(String[] args) {
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(new Task());
		service.execute(new Task());
		service.shutdown();
		
		//возвращает список Runnable, которые ещё не обрабатывались.
		//(прерванные задачи в этот список НЕ ВХОДЯТ!!!)
//		service.shutdownNow(); 
		try {
			service.awaitTermination(1, TimeUnit.HOURS);
			System.out.println("finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
}
