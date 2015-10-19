package homeWork1_Percolation;

import java.util.Random;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF wquf;	//Взвешенный быстрый поиск связей
	private boolean[][] matrix;			//массив, отражающий открытые/закрытые клетки
	private int N;
	
	public Percolation(int N) {
		
		if (N <= 0) throw new IllegalArgumentException("N must be greater than 0");
		
		this.N = N;
		wquf = new WeightedQuickUnionUF((N * N) + 2);
		matrix = new boolean[N][N];
		
//		//т.к. представление матрицы внутри wquf реализовано одномерным массивом:
//		//элементы N*N и N*N + 1 - верхняя и нижняя точки, которые должны перкалировать  
//		//объединяем N первых (т.е. первая строка для матрицы) элементов с элементом,
//		//отражающим верхнюю точку перколяции
//		for (int i = 0; i < N; i++) {
//			wquf.union(i, N * N);
//		}
//		
//		//а теперь N нижних элементов с нижней точкой перколяции
//		for (int i = 0; i < N; i++) {
//			wquf.union(((N - 1)*N + i), (N * N + 1));
//		}
		
	}
	
	public void open(int i, int j){
		
		if (i < 1 && i > N && j < 1 && j > N) throw new IndexOutOfBoundsException();
		
		int x = i - 1;	//"обычное" представление i и j в массиве для удобства
		int y = j - 1;
		matrix[i - 1][j - 1] = true;
		
		int k = convertMatrixIndexToLinear(i, j);
		
		//если клетка открылась на первой строке массива true/false, значит она автоматом
		//соединяется с верхним виртуальным узлом перколяции (его индекс в линейном массиве N*N)
		if (x == 0){
			wquf.union(k, N*N);
		}
		
		//если клетка принадлежит нижнему ряду массива, то привязываем её к нижнему вирт.
		//узлу перколяции (N*N + 1 в wquf)
		if (x == N - 1){
			wquf.union(k, N*N + 1);
		}
		
		//теперь проверяем 4-х соседей данной клетки:
		//верхний сосед
		if (x != 0 && matrix[x - 1][y]){
			wquf.union(k, k - N);
		}
		
		//нижний сосед
		if (x != N - 1 && matrix[x + 1][y]){
			wquf.union(k, k + N);
		}
		
		//левый сосед
		if (y != 0 && matrix[x][y - 1]){
			wquf.union(k, k - 1);
		}
		
		//правый сосед
		if (y != N - 1 && matrix[x][y + 1]){
			wquf.union(k, k + 1);
		}

	}
	
	public boolean isOpen(int i, int j){
		if (i < 1 && i > N && j < 1 && j > N) throw new IndexOutOfBoundsException();
		
		return matrix[i - 1][j - 1];
	}
	
	public boolean isFull(int i, int j){
		if (i < 1 && i > N && j < 1 && j > N) throw new IndexOutOfBoundsException();
		
		return wquf.connected(convertMatrixIndexToLinear(i, j), N * N);
	}
	
	public boolean percolates(){
		return wquf.connected(N*N, N*N + 1);
	}
	
	public void print(){
		for (boolean[] bs : matrix) {
			for (boolean b : bs) {
				System.out.print((b == true? 1 : 0) + " ");
			}
		System.out.println();
		}
	}
	
	//по соглашениям задания, верхняя левая точка массива имеет координаты (1,1), а не (0,0)
	private int convertMatrixIndexToLinear(int i, int j){
		return (N * (i - 1)) + (j - 1);
	}
	
	public static void main(String[] args) {
		
		int N = 5;
		Random rand = new Random();
		Percolation p = new Percolation(N);
		int percolateAt = 0;
		p.print();
		while(!p.percolates()){
			
			int x = 1 + rand.nextInt(N);
			int y = 1 + rand.nextInt(N);
			
			if (p.isOpen(x, y)) continue;
				
			p.open(x, y);
			++percolateAt;
			p.print();
			System.out.println("-------------------------------");
			
		}
		System.out.println("Percolate at " + percolateAt + ". Percolation = " + (double)percolateAt / (N * N));
	}
	
	
	
}
