package concurrent3;

import concurrent1.Utils;

public class InterraptExamples {
	
	static class SleepingTask implements Runnable {
		@Override
		public void run() {
			try{
				Thread.sleep(10000);
			}catch(Exception e){
				System.out.println("we are interrupted in SleepingTask!");
			}
		}
	}
	
	static class WorkingTask implements Runnable {
		@Override
		public void run() {
			long counter = 0;
			try{
				while(true){
					counter++;
				}
			}catch(Exception e){
				System.out.println("we are interrupted in WorkingTask! Counter = " + counter);
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("start");
		
		Thread thread = new Thread(new SleepingTask());
		thread.start();
		Utils.pause(3000);
		thread.interrupt();
		
		thread = new Thread(new WorkingTask());
		thread.start();
		Utils.pause(3000);
		thread.interrupt();
		
		
		System.out.println("finish");
	}
}
