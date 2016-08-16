package concurrent5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
	
	final static Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		
		a(5);
	}

	private static void a(int repeat) {
		System.out.println(repeat);
		repeat--;
		lock.lock();
		try{
			if (repeat > 0){
				a(repeat);
			}
		}finally{
			lock.unlock();
		}
	}
}
