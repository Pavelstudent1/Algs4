package concurrent2;

import java.util.Random;

import concurrent1.Utils;

public class TurniketExample {

	static int counter;
	static class Turniket implements Runnable{
		
		@Override
		public void run() {
			Random random = new Random();
			while(true){
//				Utils.pause(2000 + random.nextInt(3000));
				Utils.pause(2000);
				counter++; //равносильно int tmp = counter;		LOAD
						   //			 int tmp2 = tmp + 1;	INC
						   //			 counter = tmp2;		STORE
				
				System.out.println(Thread.currentThread().getName() + ": " + counter);
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 8; i++) {
			new Thread(new Turniket()).start();
		}
		
 	}
}
