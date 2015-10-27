package concurrent3;

import concurrent1.Utils;

public class WaitExample {

	public static void main(String[] args) {

		final Object mutex = new Object();
		final Object mutex2 = new Object();

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start run");
				synchronized (mutex) {
					synchronized (mutex2) {
						System.out.println("lock mutex");
						try {
							Utils.pause(5000);
							System.out.println("before wait");
							mutex.wait(2000); // wait должен применяться к mutex.
												// поток спит некоторое время,
												// a mutex, на это время, становится свободным.
							System.out.println("awake");
							Utils.pause(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println("unlock mutex");
			}
		}).start();

		Utils.pause(1000);
		System.out.println("main trying to lock mutex");
		synchronized (mutex2) {
			System.out.println("main lock mutex");
			Utils.pause(3000);
		}
		System.out.println("main unlock mutex");

	}

}
