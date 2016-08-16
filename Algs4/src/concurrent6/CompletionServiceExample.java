package concurrent6;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import concurrent1.Utils;

public class CompletionServiceExample {
	
	static class Task implements Callable<String> {
		
		private int _id;
		private int _millis;

		public Task(int id, int millis) {
			_id = id;
			_millis = millis;
		}
		@Override
		public String call() throws Exception {
			Utils.pause(_millis);
			return _id + " - " + _millis;
		}
		
	}
	
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		//обёртка над внешним экзекьютором
		ExecutorCompletionService<String> completionService = 
				new ExecutorCompletionService<>(service);
		
		Random random = new Random(); 
		for (int i = 0; i < 10; i++) {
			completionService.submit(new Task(i, random.nextInt(7000) + 3000));
		}
		
		for (int i = 0; i < 10; i++) {
			try {
				Future<String> future = completionService.take();
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}
}
