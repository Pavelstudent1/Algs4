package unionFind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CallableFutureTest implements Callable<Object[]>{
	
	public static final int SITE = 1000;
	public static final int NUMBER_OF_ROUNDS = 10;

	private int N = 0;
	
	public CallableFutureTest(int n) {
		this.N = n;
	}
	
	@Override
	public Object[] call() throws Exception {
		
		Random rand = new Random();
		UFforMatrix uf = new UFforMatrix(SITE);
		boolean[][] matrix =  new boolean[SITE][SITE];
		
		long countOfPercolateSites = 0;
		while(!uf.connected(uf.getUpSite(), uf.getDownSite())){
			
			int x = rand.nextInt(SITE);
			int y = rand.nextInt(SITE);
			
			if (matrix[x][y]) continue;
			matrix[x][y] = true;
			
			searchForFrendlySites(matrix, uf, x, y);
			countOfPercolateSites++;
		}
		
		return new Object[] {countOfPercolateSites, this.N};
	}
	
	public static void main(String[] args) {
		
		int roundCounter = 0;
		long sum = 0;
		
		List<FutureTask<Object[]>> list = new ArrayList<>();
		ExecutorService service = Executors.newCachedThreadPool();
		//ExecutorCompletionService<Object[]> ecs = new ExecutorCompletionService<>(service);
		
		long startTime = System.nanoTime(), stopTime = 0;
		//Список future
		while(roundCounter++ < NUMBER_OF_ROUNDS){
			CallableFutureTest cft = new CallableFutureTest(roundCounter);
			FutureTask<Object[]> ft = new FutureTask<>(cft);
			list.add(ft);
			service.submit(ft);
			//ecs.submit(new CallableFutureTest(roundCounter));
		}
		
		for (FutureTask<Object[]> ftask : list) {
			Object[] obj;
			try {
				obj = ftask.get();
				System.out.println((int)obj[1]);
				sum += (long)obj[0];
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		
		service.shutdown();
		try {
			service.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		stopTime = System.nanoTime();
		
		System.out.println("--------------------------");
		
		System.out.println("Average percolation is " + ((float)sum / (SITE * SITE)) / NUMBER_OF_ROUNDS + 
						   "\nrunTime = " + (float)(stopTime - startTime) / 1000000000 + " seconds");
		
		
	}
	
	


	private static void print(boolean[][] matrix) {
		
		for (boolean[] bs : matrix) {
			for (boolean b : bs) {
				System.out.print(b ? "1 " : "0 ");
			}
		System.out.println();
		}
		System.out.println("----------------------------");
	}




	private static void searchForFrendlySites(boolean[][] matrix, UFforMatrix uf, int x, int y) {
		
		//узнаём порядковый номер клетки в матрице, учитывая что (0,0) == 0, (0,1) == 1 и т.д.
		int siteN = uf.initN * x + y;
		
		if (x == 0){
			uf.union(siteN, uf.getUpSite());
		}
		
		if (x == uf.initN - 1){
			uf.union(siteN, uf.getDownSite());
		}
		
		
		if (x != 0 && matrix[x - 1][y]){
				uf.union(siteN, siteN - SITE);
		}
		
		if (x != SITE - 1 && matrix[x + 1][y]){
				uf.union(siteN, siteN + SITE);
		}
		
		if (y != 0 && matrix[x][y - 1]){
				uf.union(siteN, siteN - 1);
		}
		
		if (y != SITE - 1 && matrix[x][y + 1]){
				uf.union(siteN, siteN + 1);
		}
		
		
	}


	
	
	
	
}
