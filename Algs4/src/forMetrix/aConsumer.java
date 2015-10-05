package forMetrix;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class aConsumer implements Consumer<Record> {
	
	private static final class FullyBlockingQueue<T> extends LinkedBlockingQueue<T> {
        private FullyBlockingQueue(int capacity) {
             super(capacity);
        }

        @Override
        public boolean offer(T e) {
           try {
               put(e);
               return true;
           } catch (InterruptedException ex) {
               throw new RuntimeException(ex);
           }
        }
    }
	
	public static final int CORE_POOL = Runtime.getRuntime().availableProcessors();
	public static final int MAX_POOL = 2 * Runtime.getRuntime().availableProcessors();
	public static final int QUEUE_SIZE = 10;
	ThreadPoolExecutor executer;
	BlockingQueue<Runnable> queue;
	CountDownLatch latch = new CountDownLatch(10);
	
	
	public aConsumer() {
		queue = new FullyBlockingQueue<Runnable>(QUEUE_SIZE);
		executer = new ThreadPoolExecutor(CORE_POOL, MAX_POOL, 10, TimeUnit.MILLISECONDS, queue);
	}

	@Override
	public void consume(Collection<Record> data) {
		executer.execute(new WorkTask(data, latch));
		System.out.println("Thread sleep. Queue size = " + queue.size() + " Threads = " + executer.getActiveCount());
	}
	
	public final ExecutorService getExecutor(){
		return executer;
	}
	
	public CountDownLatch getLatch(){
		return latch;
	}
	
	
	class WorkTask implements Runnable {
		Collection<Record> batch;
		CountDownLatch latch;
		
		public WorkTask(Collection<Record> batch, CountDownLatch latch) {
			this.batch = batch;
			this.latch = latch;
		}
		
		@Override
		public void run() {
			Random rand = new Random();
			
			try {
				
				Thread.sleep(500 + rand.nextInt(501));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			for(int i = 0; i < 10 && batch.iterator().hasNext(); i++){
//				Record rec = batch.iterator().next();
//				for (int j = 0; j < 10; j++) {
//					System.out.print(rec.read[j]);
//				}
//				System.out.print("  ");
//			}
//			System.out.println();
			//spillToDisk
			
			latch.countDown();
		}
		
	}
	
	
}
