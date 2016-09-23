package concurrent3;

import concurrent1.Utils;

public class DeadLock {
	
	static Object fork = new Object();
	static Object knife = new Object();
	
	
	/**
	 * если первый поток берёт вилку, а затем нож, а второй поток наоборот - получится дедлок, 
	 * т.к. один поток блокирует то, что требуется свободным другому.
	 * Если у каждого будет сначала вилка, а затем нож, то проблем не будет
	 */
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (fork) {	
					Utils.pause(1000);
					synchronized (knife) {
						System.out.println("first person is eating");
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (knife) {
					Utils.pause(1000);
					synchronized (fork) {
						System.out.println("second person is eating");
					}
				}
			}
		}).start();
		
	}
	
}

//Суть: первый поток берёт вилку, второй берёт нож. Первый пытается взять нож,
//а второй вилку. Всё. Смертельная хватка(клинч).

//Правило: все ресурсы должны захватываться потоками в строго определённом порядке.
//		   Но это зачастую невозможно/крайне трудно обеспечить.

//syncronized - довольно жёсткая вещь, пытающаяся не попробовать захватить ресурс,
//				а вцепляющаяся в него мёртвой хваткой. В новых версиях Жабы есть
//				более щадящие способы обеспечить критическую секцию.
