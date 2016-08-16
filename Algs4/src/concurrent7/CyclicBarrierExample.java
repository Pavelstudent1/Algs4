package concurrent7;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import concurrent1.Utils;

/**
 * Немного похож на CountDownLatch, но делает наоборот: потоки вразнобой
 * работают, а когда вся их работа выполнена, они встают в точку ожидания.
 */

public class CyclicBarrierExample {

	static class Runner implements Runnable {

		private CyclicBarrier _barrier;

		public Runner(CyclicBarrier barrier) {
			_barrier = barrier;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " started");
			Random random = new Random();
			Utils.pause(5000 + random.nextInt(5000));
			System.out.println(Thread.currentThread() + " finished");
			try {
				_barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		
		CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
			@Override
			public void run() {
				System.out.println("all runners have finished!");
			}
		});
		ExecutorService service = Executors.newCachedThreadPool();

		service.execute(new Runner(barrier));
		service.execute(new Runner(barrier));
		service.execute(new Runner(barrier));
		service.execute(new Runner(barrier));

		System.out.println("await for runners");
	}
}
