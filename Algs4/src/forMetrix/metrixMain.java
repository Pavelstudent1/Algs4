package forMetrix;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class metrixMain {
	
	static final int BATCH_SIZE = 2048;
	
	
	
	public static void main(String[] args) {
		
		Data data = new Data();
		
		long start = System.nanoTime();
		doWork(data);
		long stop = System.nanoTime();
		System.out.println("Elapsed: " + (float)(stop - start)/1000000000 + " sec.");
		
	}

	private static void doWork(Data data) {
		
		aConsumer recConsumer = new aConsumer();
		Batcher<Record> batcher = new Batcher<>(recConsumer, BATCH_SIZE);
		Iterator<Record> it = data.iterator();
		
		while(it.hasNext()){
			batcher.add(it.next());
		}
		
		batcher.finish();
		try {
			recConsumer.getLatch().await();
			recConsumer.executer.shutdown();
			recConsumer.getExecutor().awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	

	
}
