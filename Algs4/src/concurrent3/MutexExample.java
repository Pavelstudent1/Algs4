package concurrent3;

import concurrent1.Utils;

public class MutexExample {
	
	private Object mutex = new Object();	//monitor
	
	void a(){
		System.out.println("start a");
		synchronized (mutex) {
			System.out.println("locked mutex in a");
			Utils.pause(50000);
			System.out.println("unlocked mutex in a");
		}
		System.out.println("finish a");
	}
	
	void b(){
		System.out.println("start b");
		synchronized (mutex) {
			System.out.println("locked mutex in b");
			Utils.pause(50000);
			System.out.println("unlocked mutex in b");
		}
		System.out.println("finish b");	
	}
	
	
	public static void main(String[] args) {
		
		final MutexExample me = new MutexExample();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				me.a();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				me.b();		//Starvation, т.е., если mutex занят методом a и там либо вечный
							//цикл, либо очень долгие вычисления, поток, выполняющий метод b
							//находится в режиме "голодания", т.е. никогда не выполнится
							//Отсюда правило: вычисления в критической секции должны быть очень
							//быстрыми. Наилучшее - изменение состояния какого-либо поля/ей.
			}
		}).start();
		//вывести поток из сотояния блокировки, т.е. во время голода, можно лишь двумя
		//путями: либо mutex разблокирован и отдан голодающему потоку, либо этот поток убит
	}
	
}
