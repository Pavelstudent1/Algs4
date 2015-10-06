package homeWork2_Rand_Queue_Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Deque<Item> implements Iterable<Item>
{
	
	private class Node{
		Item data;
		Node prev;
		Node next;
	}
	
	private Node head;
	private Node tail;
	private int size;
	
	public Deque() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	public boolean isEmpty(){
		return !(size > 0);
	}
	
	public int size(){
		return size;
	}
	
	public void addFirst(Item item){
		if (item == null) throw new NullPointerException();
		if (size == 0){
			tail = head = new Node();
			head.data = item;
			head.next = head.prev = null;
		}else{
			Node tmp = new Node();
			tmp.data = item;
			tmp.prev = null;
			tmp.next = head;
			
			head.prev = tmp;
			head = tmp;
		}
		size++;
	}
	
	public void addLast(Item item){
		if (item == null) throw new NullPointerException();
		if (size == 0){
			tail = head = new Node();
			head.data = item;
			head.next = head.prev = null;
		}else{
			Node tmp = new Node();
			tmp.data = item;
			tmp.prev = tail;
			tmp.next = null;
			
			tail.next = tmp;
			tail = tmp;
		}
		size++;
	}
	
	public Item removeFirst(){
		if (size == 0) throw new NoSuchElementException();
		
		Item outData = head.data;
		
		if (head == tail){
			head = tail = null;
		}else{
			Node tmp = head.next;
			
			head.next = null;
			head = tmp;
		}
		size--;
		return outData;
	}
	
	public Item removeLast(){
		if (size == 0) throw new NoSuchElementException();
		
		Item outData = tail.data;

		if (tail == head){
			head = tail = null;
		}else{
			Node tmp = tail.prev;
			tmp.next = null;		
			tail.next = null;
			tail.prev = null;
			tail = tmp;
		}
		size--;
		return outData;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			
			private Node current = head;

			@Override
			public boolean hasNext() {
				if (current != null) return true;
				return false;
			}

			@Override
			public Item next() {
				if (current == null) throw new NoSuchElementException();
				Item tmp = current.data;
				current = current.next;
				return tmp;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		
		Deque<Float> deq = new Deque<>();
		Random rand = new Random();
//		for (int i = 0; i < 10; i++) {
//			float f = rand.nextFloat();
//			System.out.print(f + " ");
//			deq.addFirst(f);
//		}
//		System.out.println();
//		for (int i = 0; i < 10; i++) {
//			System.out.print(deq.removeFirst() + " ");
//		}
//		
//		System.out.println("\n\n");
		deq = new Deque<>();
		for (int i = 0; i < 10; i++) {
			float f = rand.nextFloat();
			System.out.print(f + " ");
			deq.addLast(f);
		}
		
		
		System.out.println(deq.isEmpty());
		for (int i = 0; i < 10; i++) {
			System.out.print(deq.removeLast() + " ");
		}
		System.out.println(deq.isEmpty());
		
		
//		System.out.println("\n\n");
//		deq = new Deque<>();
//		for (int i = 0; i < 10; i++) {
//			float f = rand.nextFloat();
//			System.out.print(f + " ");
//			deq.addFirst(f);
//		}
//		
//		System.out.println();
//		for (Float f : deq) {
//			System.out.print(f + " ");
//		}
//		System.out.println();
		
		//deq.iterator().remove();
		
		
		
	}
	
	

	
	
}
