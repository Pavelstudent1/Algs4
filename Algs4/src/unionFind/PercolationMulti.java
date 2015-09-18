package unionFind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class PercolationMulti {
	
	public static final int SITE = 1000;
	public static final int NUMBER_OF_ROUNDS = 100;
	
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		int roundCounter = 0;
		float sumOfPercolates = 0;
		AtomicLong aSumOfPercolates = new AtomicLong(0);
		
		
		
		ExecutorService service = Executors.newFixedThreadPool(4);
		BlockingQueue<List<Objects[]>> queue = new ArrayBlockingQueue<>(10);
		
		
		long startTime = System.nanoTime(), stopTime = 0;
		
		while(roundCounter++ < NUMBER_OF_ROUNDS){
			
			final int rC = roundCounter;
			
			final List<Object[]> pairs = new ArrayList<>();
			for(int i = 0; i < 5; i++){
				pairs.add(new Object[] {new UFforMatrix(SITE), new boolean[SITE][SITE]});
			}
			
			
			service.submit(new Runnable() {
				
				@Override
				public void run() {
					
					for (Object[] objects : pairs) {
						
					UFforMatrix uf = (UFforMatrix) objects[0];
					boolean[][] matrix = (boolean[][]) objects[1];
					
					int countOfPercolateSites = 0;
					while(!uf.connected(uf.getUpSite(), uf.getDownSite())){
						
						int x = rand.nextInt(SITE);
						int y = rand.nextInt(SITE);
						
						if (matrix[x][y]) continue;
						matrix[x][y] = true;
						
//					print(matrix);
						
						searchForFrendlySites(matrix, uf, x, y);
						countOfPercolateSites++;
					}
					
//				System.out.println("===========================================");
//					sumOfPercolates += (float)countOfPercolateSites / (SITE * SITE);
					aSumOfPercolates.addAndGet(countOfPercolateSites);
//					System.out.println("Round[" + rC + "]-> Percolate value is " + (float)countOfPercolateSites / (SITE * SITE));
				}
				}
				
			});
			
		
		//end of whole turn
		}
		
		

		
		service.shutdown();
		try {
			service.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		stopTime = System.nanoTime();
		
		System.out.println("--------------------------");
		
		System.out.println("Average percolation is " + ((float)aSumOfPercolates.get() / (SITE * SITE)) / NUMBER_OF_ROUNDS + 
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
		
		
//		if (x != 0){
//			if (matrix[x - 1][y]){
//				uf.union(siteN, siteN - SITE_VALUE);
//			}
//		}
		
//		if (x != 0 && matrix[x - 1][y]){
//			uf.union(siteN, siteN - SITE_VALUE);
//		}
//		
//		if (x != SITE_VALUE - 1){
//			if (matrix[x + 1][y]){
//				uf.union(siteN, siteN + SITE_VALUE);
//			}
//		}
//		
//		if (y != 0){
//			if (matrix[x][y - 1]){
//				uf.union(siteN, siteN - 1);
//			}
//		}
//		
//		if (y != SITE_VALUE - 1){
//			if (matrix[x][y + 1]){
//				uf.union(siteN, siteN + 1);
//			}
//		}
		
		
	}
	
	
	
	
}
