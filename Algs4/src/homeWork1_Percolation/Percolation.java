package homeWork1_Percolation;

import java.util.Random;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF wquf;	//Взвешенный быстрый поиск связей
	private boolean[][] matrix;			//массив, отражающий открытые/закрытые клетки
	
	public Percolation(int N) {
		wquf = new WeightedQuickUnionUF((N * N) + 2);
		matrix = new boolean[N][N];
	}
	
	public void open(int i, int j){
		matrix[i - 1][j - 1] = true;
	}
	
	public boolean isOpen(int i, int j){
		return matrix[i - 1][j - 1];
	}
	
	public boolean isFull(int i, int j){
		
		return false;
	}
	
	public boolean percolates(){
		
		return false;
	}
	
	public void print(){
		for (boolean[] bs : matrix) {
			for (boolean b : bs) {
				System.out.print((b == true? 1 : 0) + " ");
			}
		System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		int N = 5;
		Random rand = new Random();
		Percolation p = new Percolation(N);
		p.print();
		while(!p.percolates()){
			
			int x = rand.nextInt(1 + N);
			int y = rand.nextInt(1 + N);
			
			if (p.isOpen(x, y)) continue;
				
			p.open(x, y);
			p.print();
			
		}
		
	}
	
	
	
}
