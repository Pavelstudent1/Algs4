package homeWork2_Rand_Queue_Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class randomizedQueue<Item> implements Iterable<Item>{
	
	private Node head;
	private Node tail;
	private int size;
	private Random rand;
	
	private class Node{
		Item data = null;
		Node next = null;
		Node prev = null;
	}
	public randomizedQueue() {
		this.head = this.tail = null;
		this.size = 0;
		rand = new Random();
	}
	
	public boolean isEmpty(){
		return !(size > 0);
	}
	
	public int size(){
		return size;
	}
	
	public void enqueue(Item item){
		if (item == null) throw new NullPointerException();
		Node tmp = new Node();
		tmp.data = item;
		
		if (head == null){
			head = tail = tmp;
		}else{
			tail.next = tmp;
			tmp.prev = tail;
			tail = tmp;
		}
		
		size++;
	}
	
	
	public Item dequeue(){
		if (size == 0) throw new NoSuchElementException();
		
		int dest = rand.nextInt(size);
		int counter = 0;
		Item outData;
		Node current = head;
		
		while(counter++ != dest){
			current = current.next;
		}
		if (size == 1){
			head = tail = null;
			outData = current.data;
		}else if (current == head){
			head.next.prev = null;
			head = head.next;
		}else if (current == tail){
			tail.prev.next = null;
			tail = tail.prev;
		}else{
			current.prev.next = current.next;
			current.next.prev = current.prev;
		}
		current.next = current.prev = null;
		
		size--;
		return outData = current.data;
	}
	
	public Item sample(){
		if (size == 0) throw new NoSuchElementException();
		int dest = rand.nextInt(size);
		int counter = 0;
		Node current = head;
		
		while(counter++ != dest){
			current = current.next;
		}
		
		return current.data;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			
			private Node current = head;
			private Object[] order = new Object[size];
			private int step = 0;
			
			{
				for (int i = 0; current != null; i++) {	//заполняем массив ссылками на элементы очереди по очереди :)
					order[i] = current;
					current = current.next;
				}
				shuffling(order);	//перемешиваем массив по Кнуту, получая независимую от другого итератора,
									//случайную последовательность элементов очереди
			}
			
			private void shuffling(Object[] obj){
				for (int i = 0; i < obj.length; i++) {
					int r = rand.nextInt(i + 1);
					exch(obj, i, r);
				}
				
			}
			
			private void exch(Object[] obj, int i, int r){
				Object tmp = obj[i];
				obj[i] = obj[r];
				obj[r] = tmp;
			}

			@Override
			public boolean hasNext() {
				return (step < order.length);
			}

			@Override
			public Item next() {
				if (step >= order.length) throw new NoSuchElementException();
				return ((Node)order[step++]).data;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	
	
	public static void main(String[] args) {
		
		randomizedQueue<Float> rdeq = new randomizedQueue<>();
		Random rand = new Random();
		
		for (int i = 0; i < 10; i++) {
			float f = rand.nextFloat();
			System.out.print(f + " ");
			rdeq.enqueue(f);
		}
		System.out.println();
		
		for (Float f : rdeq) {
			System.out.print(f + " ");
		}
		System.out.println();
		for (Float f : rdeq) {
			System.out.print(f + " ");
		}
		
//		for (int i = 0; i < 10; i++) {
//			System.out.print(rdeq.dequeue() + " ");
//		}
//		System.out.println("\n");
		
//		for (int i = 0; i < 5; i++) {
//			System.out.print(rdeq.sample() + " ");
//		}
//		System.out.println("\n");
		
		
	}
	
	
	

	
	
	
	
}
