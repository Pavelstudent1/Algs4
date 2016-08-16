package concurrent8;

import java.util.concurrent.atomic.AtomicInteger;

//last started 140703_01
public class AtomicVsSync {

	private static final int MAX_ITERATIONS = 1000000000;

	static class Counter{
		volatile int count;
		
		public int get() { return count; }
		
		synchronized public int inc(){ return ++count; }
	}
	
	public static void main(String[] args) {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Counter c = new Counter();
				long start = System.currentTimeMillis();
				for(int i = 0; i < MAX_ITERATIONS; i++){
					c.inc();
				}
				long stop = System.currentTimeMillis();
				System.out.println("elapsed: " + (stop - start));
			}
		});
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				AtomicInteger c = new AtomicInteger(0);
				long start = System.currentTimeMillis();
				for(int i = 0; i < MAX_ITERATIONS; i++){
					c.incrementAndGet();
				}
				long stop = System.currentTimeMillis();
				System.out.println("elapsed: " + (stop - start));
			}
		}).start();
		
	}
	
}
