package concurrent4;

import concurrent1.Utils;

/**
 *	Проблемы syncronized:
 *	1) если mutex захвачен, то вывести его из этого
 *	состояния извне невозможно.
 *	2)  Нет "честности" в получении прав на захват mutex,
 *	т.е. право захватить mutex у множества обращающихся 
 *	потоков случайно (в теории, один поток из большого 
 *	множества потоков может никогда не получить доступ) 
 *
 */

public class AlwaysBlockingExample {
	
	static final Object mutex = new Object();
	
	
	public static void main(String[] args) {

		System.out.println("Start");
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start heavy task");
				synchronized (mutex) {
					while(true){
						//heavy task
					}
				}
			}
		}).start();
		
		Utils.pause(1000);
		
		System.out.println("Trying to lock mutex");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (mutex) {
					System.out.println("Hello there!");
				}
			}
		});
		thread.start();
		
		Utils.pause(1000);
		
		//ни один из способов не остановит поток с тяжёлой задачей
		thread.interrupt();
		thread.stop();
		
	}
	
}
