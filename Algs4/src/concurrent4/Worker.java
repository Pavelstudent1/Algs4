package concurrent4;

import java.util.LinkedList;
import java.util.Queue;

import concurrent1.Utils;

//Last 14.06.10_04

public class Worker implements Runnable {

	private final Queue<Runnable> _tasks = new LinkedList<>();
	
	public Worker() {
		new Thread(this).start();
	}
	
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
			try{
			task.run();
			} catch (Throwable e){
				//ни Exception, ни Throwable не рекомендуется перехватывать
				e.printStackTrace();
			}
		}
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
}