package concurrent5;

import concurrent1.Utils;

/**
 *	volatile позволяет всем потокам "видеть" изменение 
 *	переменной помеченной этим служебным словом
 *	(для простой переменной одного поля - лучшее решение)
 *
 *	Также volatile обеспечивает атомарность записи переменной
 *	(например, физически, запись переменной long проходит
 *	в два захода: старшие 32 бита, затем младшие)
 */
public class VisibilityVolatileExample {
	
	static class Task implements Runnable{
		
		volatile private boolean stop;
		
		//если убрать syncronized(и без volatile) 
		//для isStopped и stop,
		//то поток счётчика никогда не остановится
		public boolean isStopped(){
			return stop;
		}
		
		@Override
		public void run() {
			long counter = 0;
			while(!isStopped()){
				counter++;
			}
			System.out.println(counter);
		}
		
		public void stop(){
			stop = true;
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("start");
		
		Task task = new Task();
		new Thread(task).start();
		
		Utils.pause(3000);
		
		task.stop();
		
	}
	
	
}
