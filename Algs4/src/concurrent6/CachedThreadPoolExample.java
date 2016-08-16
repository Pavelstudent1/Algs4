package concurrent6;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import concurrent1.Utils;

//newCachedThreadPool() мочит неиспользуемые потоки после 
//~60 секунд бездействия (для конкретного потока)
public class CachedThreadPoolExample {
	
	static class Task implements Runnable{
		@Override
		public void run() {
			Utils.pause(3000);
		}
	}
	
	public static void main(String[] args) {
		
		final ThreadPoolExecutor service = 
				(ThreadPoolExecutor) Executors.newCachedThreadPool();
		printStats(service);

		
		service.execute(new Task());
		service.execute(new Runnable() {
			@Override
			public void run() {
				while(true){
					Utils.pause(1000);
					printStats(service);
				}
			}
		});
		
		for (int i = 0; i < 20; i++) {
			service.execute(new Task());
			Utils.pause(200);
		}
		
		
		
	}

	private static void printStats(ThreadPoolExecutor service) {
		System.out.println();
		System.out.println(service.getPoolSize());
		System.out.println(service.getActiveCount());
	}
}
