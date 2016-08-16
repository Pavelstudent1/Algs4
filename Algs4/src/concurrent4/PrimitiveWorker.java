package concurrent4;

import java.util.LinkedList;
import java.util.Queue;

import concurrent1.Utils;

public class PrimitiveWorker {
	
	private static final Runnable POISON_PILL = new Runnable() {
		@Override
		public void run() {}
	};

	private final Queue<Runnable> _tasks = new LinkedList<>();
	
	//внутренний класс скрывает реализацию Runnable извне
	//класса Worker, т.е. Worker извне принимает лишь
	//задачи.
	private class WorkerTask implements Runnable {
		@Override
		public void run() {
			Runnable task;
			while (true) {
				synchronized (_tasks) {
					while (!hasNewTask()) {
						try {
							_tasks.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					task = _tasks.poll();
				}
				if (task == POISON_PILL){
					break;
				}
				try{
					task.run();
				} catch (Throwable e){
					//ни Exception, ни Throwable не рекомендуется перехватывать
					e.printStackTrace();
				}
			}
		}
	}
	
	public PrimitiveWorker() {
		new Thread(new WorkerTask()).start();
	}
	

	private boolean hasNewTask() {
		return !_tasks.isEmpty();
	}

	public void execute(Runnable task) {
		synchronized (_tasks) {
			_tasks.offer(task);
			_tasks.notify();
			System.out.println("Added task");
		}
	}
	
	//кладёт стоп-задачу в конец очереди. Когда очередь дойдёт
	//до стоп-задачи, работа Worker прекратится. Будь в while
	//некая обновляемая стоп-переменная, то пришлось бы писать код,
	//учитывающий, что ещё могут быть задачи в очереди, которые
	//перед завершением нужно доделать и т.п. 
	//Отравленная пилюля простое и элегантное решение.
	public void shutdown(){
		execute(POISON_PILL);
	}
}