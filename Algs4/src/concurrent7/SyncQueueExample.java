package concurrent7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import concurrent1.Utils;

/**
 * SynchronousQueue не содержит в себе элементов. 
 * Сделав put, очередь ждёт take. В этот момент элемент передаётся.
 * Это синхронизирует потоки между собой.
 */
public class SyncQueueExample {

	public static void main(String[] args) {
		
		final SynchronousQueue<String> queue = new SynchronousQueue<>();
		ExecutorService service = Executors.newCachedThreadPool();
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("creating thing");
//				Utils.pause(10000);
				System.out.println("thing is ready");
				try {
					queue.put("THING");
					System.out.println("passed thing");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("doing some stuff");
				Utils.pause(10000);
				System.out.println("waiting for thing");
				try {
					String thing = queue.take();
					System.out.println("got " + thing);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
