package concurrent5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrent1.Utils;

public class LockEfficiancyExample {
	
	static class Task implements Runnable{
		
		private boolean stop;
		final private Lock mutex = new ReentrantLock();
		
		public boolean isStopped(){
			mutex.lock();
			try{
				return stop;
			} finally {
				mutex.unlock();
			}
		}
		
		@Override
		public void run() {
			long counter = 0;
			while(!isStopped()){
				counter++;
			}
			System.out.println(counter);
		}
		
		public void stop(){
			mutex.lock();
			try{
				stop = true;
			}finally{
				mutex.unlock();
			}
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("start");
		
		Task task = new Task();
		new Thread(task).start();
		
		Utils.pause(3000);
		
		task.stop();
		
	}
	
	
}
