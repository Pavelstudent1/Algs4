package homeWork1_Percolation;

import java.util.Random;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private double[] percStats;
	private double mean = 0.0;
	private double stddev = 0.0;
	
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) throw new IllegalArgumentException();
		
		percStats = new double[T];
		Random rand = new Random();
		for (int i = 0; i < T; i++) {
			
			int percolatedAt = 0;
			Percolation p = new Percolation(N);
			while(!p.percolates()){
				
				int x = 1 + rand.nextInt(N);
				int y = 1 + rand.nextInt(N);
				
				if (p.isOpen(x, y)) continue;
				
				p.open(x, y);
				++percolatedAt;
			}
			
			percStats[i] = (double)percolatedAt / (N * N);
		}
		
		this.mean = StdStats.mean(percStats);
		this.stddev = StdStats.stddev(percStats);
	}
	
	public double mean(){
		return mean;
	}
	
	public double stddev(){
		return stddev;
	}
	
	public double confidenceLo(){
		return mean - (1.96 * stddev) / Math.sqrt(percStats.length);
	}
	
	public double confidenceHi(){
		return mean + (1.96 * stddev) / Math.sqrt(percStats.length);
	}
	
	public static void main(String[] args) {
		
		PercolationStats ps = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
		System.out.println("mean = " + ps.mean() + 
						   "\nstddev = " + ps.stddev() + 
						   "\n95% confidence interval = " + 
						   ps.confidenceLo() + ", " + ps.confidenceHi());
		
	}
}
