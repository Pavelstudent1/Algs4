package homeWork2_Rand_Queue_Deque;

import java.util.Iterator;
import java.util.Random;

import javax.swing.text.AbstractDocument.LeafElement;

public class randomizedQueueArray<Item> implements Iterable<Item> {
	
	private final int INITIAL_CAPACITY = 10;
	private Item[] rq;
	private int size;
	private int count;
	private Random rand;
	
	public randomizedQueueArray() {
		rq = (Item[]) new Object[INITIAL_CAPACITY];
		size = count = 0;
		rand = new Random();
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public int size(){
		return size;
	}
	
	public void enqueue(Item item){
		
		if (count == rq.length) resizeQueue(rq.length * 2);
		++size;
		rq[count++] = item;
	}
	
	public Item dequeue(){
		
		if (count <= (rq.length / 4)) resizeQueue(rq.length / 2);
		Item out;
		
		int index = rand.nextInt(count);
		
		out = rq[index];
		rq[index] = rq[count - 1];
		rq[--count] = null;
		
		return out;
	}
	
	public Item sample(){
		
		
		
		return null;
	}
	
	private void resizeQueue(int newsize){
		Item[] tmp = (Item[]) new Object[newsize];
		System.arraycopy(rq, 0, tmp, 0, count);
		rq = tmp;
	}
	
	@Override
	public Iterator<Item> iterator() {

		return null;
	}
	
	
	public static void main(String[] args) {
		
		randomizedQueueArray<Integer> rq = new randomizedQueueArray<>();
		Random rand = new Random();
		for (int i = 0; i < 15; i++) {
			rq.enqueue(1 + rand.nextInt(50));
		}
		
		for (int i = 0; i < 15; i++) {
			System.out.println(rq.dequeue());
		}
		
	}

}
