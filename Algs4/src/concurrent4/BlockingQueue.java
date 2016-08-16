package concurrent4;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
	
	final private Queue<T> _items = new LinkedList<>();

	
	//может блокироваться, если очередь 
	//имеет конечную длину (блокирующий буфер)
	public void offer(T item){
		synchronized(_items){
			_items.offer(item);
			//сказать какому-нибудь потоку, 
			//что можно забирать элемент(если ожидающих много)
			_items.notify();
			//можно использовать и notifyAll, который разбудит
			//всех ожидающих, но в while из poll() зайдёт
			//только один из них, который заберёт элемент,
			//а все остальные потоки, ломанувшиеся за ним увидят,
			//что элементов больше нет и опять будут ждать.
			//Единственный минус: notifyAll тратит больше ресурсов,
			//т.к. будит всех ожидающих, большую часть из которых потом
			//опять надо будет усыплять
		}
	}
	
	public T poll(){
		synchronized(_items){
			//while отсекает случаи "внезапного" пробуждения
			//ожидающего потока (пробуждение не от положенного
			//элемента в _items, а от "фантомов" JVM)
			while(_items.isEmpty()){
				try {
					//когда поток в wait-состоянии, mutex свободен
					//(т.е. другой поток может что-нибудь в него положить)
					_items.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return _items.poll();
		}
	}
	
	
	
	
	
}
