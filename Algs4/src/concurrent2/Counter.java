package concurrent2;

public class Counter {
	
	private int counter;
	
	public void inc(){
		synchronized (this) { //mutex = mutually exclusive = взаимное исключение, т.е. данный
			counter++;		  //объект this переходит в монопольное использование
			System.out.println(Thread.currentThread().getName() + ": " + counter);
		}
	}
	
	public void mul(){
		synchronized (this) { //блокировка действует на целый объект, даже если несколько
			counter *= 2;	  //его методов с synchronized. Они не выполнятся другими потоками!
		}
	}
	
	public int get(){
		return counter;
	}
}
