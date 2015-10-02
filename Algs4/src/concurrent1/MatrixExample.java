package concurrent1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MatrixExample {
	
	public static double[][] generateMatrix(int rows, int cols){
		double[][] matrix = new double[rows][cols];
		Random random = new Random();
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = random.nextDouble();
			}
		}
		
		return matrix;
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println("START");
		
		final double[][] matrix = generateMatrix(10, 1000000);
		
//		evaluate(new Runnable() { //One Thread
//			
//			@Override
//			public void run() {
//				for (int i = 0; i < matrix.length; i++) {
//					for (int j = 0; j < matrix[i].length; j++) {
//						double result = calculate(matrix[i][j]);
//					}
//				}
//			}
//		});
		
		evaluate(new Runnable() { //Many Thread
			
			@Override
			public void run() {
				List<Thread> threads = new LinkedList<>();
				for (int i = 0; i < matrix.length; i++) {
					Thread t = new Thread(new RowTask(matrix[i]));
					threads.add(t);
					t.start();
				}
				for (Thread t : threads) {
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		System.out.println("FINISH");
	}

	static class RowTask implements Runnable{
		
		private double[] _row;
		
		public RowTask(double[] row) {
			_row = row;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < _row.length; i++) {
				double result = calculate(_row[i]);
			}
		}
		
	}

	protected static double calculate(double d) {
		double result = 0;
		for (int i = 0; i < 1000; i++) {
			result += Math.pow(Math.sin(Math.pow(d, Math.E)) / Math.cos(Math.pow(d, Math.E)), Math.PI);
		}
		return result;
	}


	private static void evaluate(Runnable task) {
		long start = System.currentTimeMillis();
		task.run();
		long stop = System.currentTimeMillis();
		System.out.println("Elapsed = " + (stop - start));
	}
}
