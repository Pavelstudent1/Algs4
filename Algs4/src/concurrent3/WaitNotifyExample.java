package concurrent3;

import java.awt.Menu;
import java.util.Random;

import concurrent1.Utils;

public class WaitNotifyExample {

	static String meal;

	static Object bell = new Object();

	static String[] menu = { "Steak", "Pasta", "IceCream", "Soup" };

	static class Cook implements Runnable {
		@Override
		public void run() {
			Random random = new Random();
			while (true) {
				int pause = 1000 + random.nextInt(1000);
				Utils.pause(pause);
				String readyMeal = menu[random.nextInt(menu.length)];
				System.out.println("Cook: " + readyMeal);
				synchronized (bell) {
					meal = readyMeal;
					bell.notify();
				}
			}
		}

	}

	static class Waiter implements Runnable {
		@Override
		public void run() {
			while (true) {
				//Стандартный подход
				synchronized (bell) {
					while (meal == null) { //тоже стандарт, т.к. в Жабе есть проблема 
										   //внезапных пробуждений, когда поток будится не от notify
										   //а отчего угодно. Проверка в вайле гарантирует, что 
										   //официант не возьмёт тарелку без блюда
						try {
							bell.wait();	//wait без параметра - вечное ожидание
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				//
				String readyMeal = meal;
				meal = null;
				System.out.println("Waiter: got " + readyMeal);
			}
		}

	}

	public static void main(String[] args) {
		new Thread(new Cook()).start();		//Повар
		new Thread(new Waiter()).start();	//Официант
	}
	
	//Работа двух потоков безопасна и состояния гонки никогда не будет, т.к. потоки
	//синхронизированны по одному объекту - bell

}
