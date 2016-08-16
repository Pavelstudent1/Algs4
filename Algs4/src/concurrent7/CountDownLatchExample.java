package concurrent7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import concurrent1.Utils;

/**
 *	Используется, если нужно запустить некоторое количество
 *	потоков одновременно
 */
public class CountDownLatchExample {
	
	
	
	static class Runner implements Runnable {
	
		private CountDownLatch _latch;
		
		public Runner(CountDownLatch latch) {
			_latch = latch;
		}
		
		@Override
		public void run() {
			try {
				System.out.println(this + " is ready");
				_latch.await();
				long currentTimeMillis = System.nanoTime();
				System.out.println(this + " (" + Thread.currentThread()+ ") has started at " + currentTimeMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		//число говорит, сколько нужно подать сигналов,
		//чтобы курок сработал
		CountDownLatch latch = new CountDownLatch(3);
		service.execute(new Runner(latch));
		service.execute(new Runner(latch));
		service.execute(new Runner(latch));
		service.execute(new Runner(latch));
		
		System.out.println("----");
		Utils.pause(3000);
		System.out.println("start!");
		latch.countDown();
		Utils.pause(2000);
		System.out.println("steady!");
		latch.countDown();
		Utils.pause(2000);
		System.out.println("go!");
		latch.countDown();
		
	}
}
