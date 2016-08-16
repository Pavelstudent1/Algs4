package concurrent7;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

//last started 140624_02
public class ScheduledExecutorServiceExample {
	
	private static ScheduledExecutorService service;
	private static ScheduledFuture<?> timeoutFuture;
	private static ScheduledFuture<?> countdownFuture;

	static class CountDownAction implements Runnable{
		
		private int _countDown;
		private ScheduledFuture<?> _scheduledFuture;

		public void setScheduledFuture(ScheduledFuture<?> scheduledFuture){
			_scheduledFuture = scheduledFuture;
		}
		
		public CountDownAction(int seconds) {
			_countDown = seconds;
		}
		
		@Override
		public void run() {
			System.out.println("time left " + (_countDown--));
			if (_countDown == 0){
				_scheduledFuture.cancel(true);
			}
		}
		
	}
	
	static class TimeOutAction implements Runnable{
		@Override
		public void run() {
			System.out.println("Timeout! Try again.");
			resetActions();
		}
	}
	
	public static void main(String[] args) {
		service = Executors.newSingleThreadScheduledExecutor();
		resetActions();
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			System.out.println(scanner.nextLine());
			resetActions();
		}
	}

	public static void resetActions() {
		
		if (timeoutFuture != null){
			timeoutFuture.cancel(true);
		}
		if (countdownFuture != null){
			countdownFuture.cancel(true);
		}
		
		timeoutFuture = service.schedule(new TimeOutAction(), 10, TimeUnit.SECONDS);
		
		CountDownAction countDownAction = new CountDownAction(10);
		countdownFuture = service.scheduleAtFixedRate(
				countDownAction, 0, 1, TimeUnit.SECONDS);
		countDownAction.setScheduledFuture(countdownFuture);
	}
}
