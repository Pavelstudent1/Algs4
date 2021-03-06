package concurrent4;

//Last 14.06.10_04

public class Worker {
	
	private static final Runnable POISON_PILL = new Runnable() {
		@Override
		public void run() {}
	};

	private final BlockingQueue<Runnable> _tasks = new BlockingQueue<>();
	
	//внутренний класс скрывает реализацию Runnable извне
	//класса Worker, т.е. Worker извне принимает лишь
	//задачи.
	private class WorkerTask implements Runnable {
		@Override
		public void run() {
			Runnable task;
			while (true) {
				task = _tasks.poll();
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
	
	public Worker() {
		new Thread(new WorkerTask()).start();
	}

	public void execute(Runnable task) {
		_tasks.offer(task);
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