package concurrent8;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import concurrent1.Utils;

public class SemBarrierExample {

	public static class MyBarrier {

		private final int _participants;
		private final Semaphore sem = new Semaphore(0);
		private int count = 0;

		public MyBarrier(int participants) {
			_participants = participants;
		}

		public void await() {
			int c = 0;
			synchronized (sem) {
				c = ++count;
			}
			if (c == _participants) {
				sem.release(c);
			} else {
				sem.acquireUninterruptibly();
				sem.release();
			}
		}

	}

	static class Task implements Runnable {

		private MyBarrier _barrier;

		public Task(MyBarrier barrier) {
			_barrier = barrier;
		}

		@Override
		public void run() {
			Random random = new Random();
			System.out.println(Thread.currentThread().getName() + "started");
			Utils.pause(2000 + random.nextInt(5000));
			System.out.println(Thread.currentThread().getName() + "waiting");
			_barrier.await();
			System.out.println(Thread.currentThread().getName() + "finished");

		}

	}

	public static void main(String[] args) {

		MyBarrier barrier = new MyBarrier(4);
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Task(barrier));
		service.execute(new Task(barrier));
		service.execute(new Task(barrier));
		service.execute(new Task(barrier));

	}

}
