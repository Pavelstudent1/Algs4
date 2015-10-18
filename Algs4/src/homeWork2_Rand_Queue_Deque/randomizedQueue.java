package homeWork2_Rand_Queue_Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class randomizedQueue<Item> implements Iterable<Item> {
	
	private final int DOWNSIZE_THRESHOLD = 4; //расчёт предельной заполенности: rq.length / DOWNSIZE_THRESHOLD
	private final int INITIAL_CAPACITY = 10;	//начальный объём очереди
	private Item[] rq;
	private int count;	//число элементов, реально присутствующих в очереди
	private Random rand;
	
	public randomizedQueue() {
		rq = (Item[]) new Object[INITIAL_CAPACITY];
		count = 0;
		rand = new Random();
	}
	
	public boolean isEmpty(){
		return count == 0;
	}
	
	//возвращает количество элементов на момент вызова size()!
	public int size(){
		return count;
	}
	
	public void enqueue(Item item){
		
		if (item == null) throw new NullPointerException("Trying to enqueue a null!");
		
		if (count == rq.length) resizeQueue(rq.length * 2);
		rq[count++] = item;
	}
	
	public Item dequeue(){
		
		if (count == 0) throw new NoSuchElementException("Queue is empty!");
		
		//условие сокращения массива при заполненности ниже DOWNSIZE_THRESHOLD, но не меньше INITIAL_CAPACITY
		if (count <= (rq.length / DOWNSIZE_THRESHOLD) && rq.length != INITIAL_CAPACITY) resizeQueue(rq.length / 2);
		Item out;
		
		int index = rand.nextInt(count);
		
		out = rq[index];			//запоминаем выводимый элемент
		rq[index] = rq[count - 1];	//в позицию выводимого элемента записываем последний имеющийся
		rq[--count] = null;			//зануляем позицию последнего имеющегося, уменьшая count
		
		return out;
	}
	
	public Item sample(){ //тут можно возвращать ссылку как на сам элемент, как и на его копию, защитив содержимое от изменения
		
		if (count == 0) throw new NoSuchElementException("Queue is empty!");
		
		int index = rand.nextInt(count);
		
		return rq[index];
	}
	
	
	private void resizeQueue(int newsize){
		Item[] tmp = (Item[]) new Object[newsize];
		System.arraycopy(rq, 0, tmp, 0, count);
		rq = tmp;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			
			Item[] t = (Item[]) new Object[count];
			int index = 0;
			
			{
				for (int i = 0; i < count; i++) {
					t[i] = rq[i];
				}
				shuffling(t); //перемешивание по Кнуту
			}
			
			private void shuffling(Item[] obj){
				for (int i = 0; i < obj.length; i++) {
					int r = rand.nextInt(i + 1);
					exch(obj, i, r);
				}
				
			}
			
			private void exch(Item[] obj, int i, int r){
				Item tmp = obj[i];
				obj[i] = obj[r];
				obj[r] = tmp;
			}
			
			
			@Override
			public boolean hasNext() {
				if (index == t.length) return false;
				return true;
			}

			@Override
			public Item next() {
				if (index == t.length) throw new NoSuchElementException("Iterator is over!");

				return t[index++];
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	public static void main(String[] args) {
		
		randomizedQueue<Integer> rq = new randomizedQueue<>();
		Random rand = new Random();
		System.out.print("Enq: ");
		for (int i = 0, val = 0; i < 15; i++) {
			val = 1 + rand.nextInt(50);
			rq.enqueue(val);
			System.out.print(val + " ");
		}
		
		System.out.print("\nSam: ");
		for (int i = 0; i < 15; i++) {
			System.out.print(rq.sample() + " ");
		}
		
//		System.out.print("\nDeq: ");
//		for (int i = 0; i < 15; i++) {
//			System.out.print(rq.dequeue() + " ");
//		}
		
		System.out.print("\nItr: ");
		for (Integer i : rq) {
			System.out.print(i + " ");
		}
		
	}

}
