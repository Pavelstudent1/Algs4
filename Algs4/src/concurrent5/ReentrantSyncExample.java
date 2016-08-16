package concurrent5;

public class ReentrantSyncExample {
	
	static Object mutex = new Object();
	
	public static void main(String[] args) {
		
		a(5);
		b();
		c();
		
	}

	private static void b() {
		synchronized (mutex) {
			c();
		}
	}

	private static void c() {
		synchronized (mutex) {
			System.out.println("hello there");
		}
	}

	private static void a(int repeat) {
		System.out.println(repeat);
		repeat--;
		synchronized (mutex) {
			if (repeat > 0){
				a(repeat);
			}
		}
	}
}
