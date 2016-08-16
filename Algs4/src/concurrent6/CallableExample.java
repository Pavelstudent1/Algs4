package concurrent6;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import concurrent1.Utils;

public class CallableExample {
	
	static class Task implements Callable<String>{
		@Override
		public String call() throws Exception {
			Utils.pause(5000);
			return "hello";
		}
		
	}
	
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<String> future = service.submit(new Task());
		try {
			System.out.println("waiting for result...");
			String result = future.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		service.shutdown();
	}
}
