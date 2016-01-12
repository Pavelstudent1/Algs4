package concurrent4;

import java.util.Scanner;

import concurrent1.Utils;

public class NotifyAllExample {
	
	static final Object mutex = new Object();
	
	static class Task implements Runnable{

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + 
					"trying to lock mutex");
			synchronized (mutex) {
				System.out.println(Thread.currentThread().getName() + 
						"locked mutex");
				try {
					mutex.wait();
					System.out.println(Thread.currentThread().getName() + 
							"was woken");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	

	public static void main(String[] args) {
		//проблема: мэйн заканчивает свою работу раньше, чем потоки успевают сделать свою,
		//и, захватив mutex, они навечно зависли, т.к. сигнал notify никогда не придёт,
		//т.к. они его банально пропускили
		Task task = new Task();
		
		new Thread(task).start();
		new Thread(task).start();
		new Thread(task).start();
		
		Utils.waitForUserInput();
		
//		Utils.pause(500);	//простейшее решение: небольшая задержка. 
							//Особенность: если mutex ждут несколько потоков, то notify
							//пробуждает только один случайный поток. Разбудить все можно
							//послав нужное количество notify вызовов
		
		System.out.println("1 before locking mutex");
		synchronized (mutex) {
			System.out.println("2 mutex locked");
			Utils.waitForUserInput();
			mutex.notifyAll(); //Более правильное решение: notifyAll
			System.out.println("3 notified");
			Utils.waitForUserInput();
		}
		System.out.println("4 unlocked mutex");
	}
	
}
