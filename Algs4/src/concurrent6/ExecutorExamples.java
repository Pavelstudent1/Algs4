package concurrent6;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

import concurrent1.Utils;


//last started 14.06.19_03
public class ExecutorExamples {

	static class DirectExecutor implements Executor {
		@Override
		public void execute(Runnable r) {
			r.run();
		}
	}

	static class ThreadPerTaskExecutor implements Executor {
		public void execute(Runnable r) {
			new Thread(r).start();
		}
	}

	static class SerialExecutor implements Executor {
		final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
		final Executor executor;
		Runnable active;

		SerialExecutor(Executor executor) {
			this.executor = executor;
		}

		public synchronized void execute(final Runnable r) {
			tasks.offer(new Runnable() {
				public void run() {
					try {
						r.run();
					} finally {
						scheduleNext();
					}
				}
			});
			if (active == null) {
				scheduleNext();
			}
		}

		protected synchronized void scheduleNext() {
			if ((active = tasks.poll()) != null) {
				executor.execute(active);
			}
		}
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " start");
			Utils.pause(3000);
			System.out.println(Thread.currentThread().getName() + " finish");
			
		}

	}

	public static void main(String[] args) {

		DirectExecutor directExecutor = new DirectExecutor();
		directExecutor.execute(new Task());

		ThreadPerTaskExecutor threadPerTaskExecutor = new ThreadPerTaskExecutor();
		threadPerTaskExecutor.execute(new Task());
		threadPerTaskExecutor.execute(new Task());
		
		SerialExecutor serialExecutor = new SerialExecutor(threadPerTaskExecutor);
		serialExecutor.execute(new Task());
		serialExecutor.execute(new Task());
	}
}
