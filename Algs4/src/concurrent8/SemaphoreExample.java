package concurrent8;

import java.util.concurrent.Semaphore;

/**
 *	Для семафора не важно, какой поток освобождает его
 *	Основное употребление семафора: ограничение числа потоков,
 *	использующих некоторый ресурс
 */
public class SemaphoreExample {

	static class Counter {
		volatile int count;
		final Semaphore sem = new Semaphore(1);

		public int get() {
			return count;
		}

		public void inc() {
			sem.acquireUninterruptibly();
			try {
				count++;
			} finally {
				sem.release();
			}
		}
	}

	public static void main(String[] args) {

	}

}
