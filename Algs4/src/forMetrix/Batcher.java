package forMetrix;

import java.util.ArrayList;
import java.util.Collection;

public class Batcher<E> {
	
	private Collection<E> batch;
	private int batchSize;
	private Consumer<E> consumer;
	
	public Batcher(Consumer<E> consumer, int size) {
		this.consumer = consumer;
		this.batchSize = size;
		this.batch = new ArrayList<>(size);
	}
	
	public void add(E data){
		batch.add(data);
		if (batch.size() == batchSize){
			consumer.consume(batch);
			batch = new ArrayList<>(batchSize);
		}
	}
	
	public void finish(){
		consumer.consume(batch);
		batch = null;
	}
	
	
	

}
