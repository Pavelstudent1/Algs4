package concurrent1;

public class LifeCycle {
	
	public static void main(String[] args) {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread());
				Utils.pause(10000);
				while(true);
			}
		});
		System.out.println(t.getState());
		
		t.start();
		
		System.out.println(t.getState());
		
//		for (int i = 0; i < 12; i++) {
//			Utils.pause(1000);
//			System.out.println(t.getState());
//		}
		
		try {
			t.join(); //текущий main блокируется, пока поток t не завершит работу
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(t.getState());
	}
}
