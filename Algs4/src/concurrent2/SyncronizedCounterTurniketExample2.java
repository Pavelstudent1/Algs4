package concurrent2;

import java.util.Random;

import concurrent1.Utils;

public class SyncronizedCounterTurniketExample2 {

	static Counter counter = new Counter();
	static int c2;
	
	
	synchronized static void intCounter(){ //Это...
		c2++;
	}
	
	static void intCounter2(){
		synchronized (SyncronizedCounterTurniketExample2.class) { //и это - эквивалентно!
			c2++;
		}
	}
	
	static class Turniket implements Runnable{
		
		@Override
		public void run() {
			Random random = new Random();
			while(true){
				Utils.pause(2000);
				counter.inc();
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 8; i++) {
			new Thread(new Turniket()).start();
		}
		
 	}
}
