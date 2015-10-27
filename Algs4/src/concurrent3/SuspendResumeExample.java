package concurrent3;

import concurrent1.Utils;

/** ПОСЛЕДНЯЯ == java_140605_03 ближе к концу**/

public class SuspendResumeExample {

	public static void main(String[] args) {
		
		System.out.println("start");
		
		final Object mutex = new Object();
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				long counter = 0;
				synchronized (mutex) {
					while(true){
						Utils.pause(1000);
						System.out.println("working...");
					}					
				}
			}
		});
		
		thread.start();
		
		Utils.pause(2000);
		
		thread.suspend();	//поток останавливается, но всё ещё считается Running
		
		System.out.println("Trying lock mutex");
		synchronized (mutex) {				//здесь происходит что: запускаемый поток захватывает
			System.out.println("before");	//mutex и вечно держит его + suspend его ещё и останавливает, 
			Utils.pause(20000);				//а потом мэин пытается захватить mutex и переводится 
			System.out.println("after");	//в состояние мониторинга и тоже зависает намертво.
											//И поток и мэин виснут намертво. Прога виснет намертво.
		}									//Перенос suspend в synchronized (mutex) ниже не решит
											//ситуацию: mutex всё равно удерживается вечным while
		
		thread.resume();
		
	}
}
