package concurrent2;

import java.util.Random;

import concurrent1.Utils;

public class SyncronizedTurniketExample {

	static int counter;
	
	//в любой момент времени, данный метод может выполнить строго один поток
	synchronized static void incrementCounter(){  
		int tmp = counter;
		int tmp2 = tmp + 1;
		counter = tmp2;
		System.out.println(Thread.currentThread().getName() + ": " + counter);
	}
	
	static class Turniket implements Runnable{
		
		@Override
		public void run() {
			Random random = new Random();
			while(true){
				Utils.pause(2000);
				incrementCounter();
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 8; i++) {
			new Thread(new Turniket()).start();
		}
		
 	}
}
