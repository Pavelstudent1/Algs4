package concurrent5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrent1.Utils;

/**
 *	Количество локов и анлоков должно совпадать!!!
 */
public class LockUnlockMustBeEqual {
	
	static Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				getLock();
				getLock();
				getLock();
				System.out.println("haha");
				freeLock();
				freeLock();
				freeLock();
			}
		}).start();
		
		Utils.pause(1000);
		
//		lock.unlock();	//нельзя сделать анлок из потока, который лок не вешал!
//		lock.unlock();	//лок/анлок должен происходить в конкретном потоке!!
		
		lock.lock();
		try{
			System.out.println("Hi there");
		}finally{
			lock.unlock();
		}
	}
	
	protected static void freeLock() {
		lock.unlock();
	}

	protected static void getLock() {
		lock.lock();
	}
}
