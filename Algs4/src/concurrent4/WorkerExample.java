package concurrent4;

import java.util.LinkedList;
import java.util.Queue;

import concurrent1.Utils;

//ПОСЛЕДНИЙ ПОСМОТРЕННЫЙ 14.06.10_01
public class WorkerExample {
	
	public static class Worker implements Runnable {
		
		private final Queue<Runnable> _tasks = new LinkedList<>();
		
		@Override
		public void run() {
			while(!hasNewTask()){
				Utils.pause(1000);
			}
		}
		
		private boolean hasNewTask() {
			return !_tasks.isEmpty();
		}

		public void execute(Runnable task){
			_tasks.add(task);
		}
	}
	
}
