package unionFind;

import java.util.Random;

public class Percolation {
	
	public static final int SITE = 1000;
	public static final int NUMBER_OF_ROUNDS = 100;
	
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		int roundCounter = 0;
		float sumOfPercolates = 0;
		
		long startTime = System.nanoTime(), stopTime = 0;
		while(roundCounter++ < NUMBER_OF_ROUNDS){
			
			UFforMatrix uf = new UFforMatrix(SITE);
			boolean[][] matrix = new boolean[SITE][SITE];
			
			int countOfPercolateSites = 0;
			while(!uf.connected(uf.getUpSite(), uf.getDownSite())){
				
				int x = rand.nextInt(SITE);
				int y = rand.nextInt(SITE);
				
				if (matrix[x][y]) continue;
				matrix[x][y] = true;
				
//				print(matrix);
				
				searchForFrendlySites(matrix, uf, x, y);
				countOfPercolateSites++;
			}
			
//			System.out.println("===========================================");
			//sumOfPercolates += (float)countOfPercolateSites / (SITE * SITE);
			//System.out.println("Round[" + roundCounter + "]-> Percolate value is " + (float)countOfPercolateSites / (SITE * SITE));
		}
		stopTime = System.nanoTime();
		System.out.println("--------------------------");
		System.out.println("Average percolation is " + sumOfPercolates / NUMBER_OF_ROUNDS + 
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
