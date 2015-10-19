package homeWork4_8_Puzzle;

import java.util.LinkedList;
import java.util.List;

public class Board {
	
	private final int[][] a;
	private int dimension = 0;
	
	public Board(int[][] blocks) { //конструктор "корня" дерева ходов
		
		a = blocks;
		dimension = blocks.length;
	}
	
	//конструктор строящий копию на основе другого Board
	private Board(Board orig){
		a = new int[orig.dimension][orig.dimension];
		dimension = a.length;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = orig.a[i][j];
			}
		}
		
	}
	
//	public Board(int[][] blocks, int move){ //конструктор для потомков
//		a = blocks;
//		dimension = blocks.length;
//	}
	
	public int dimension(){
		return dimension;
	}
	
	public int hamming(){
		
		int expected = 1;
		int hamming = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] != expected) ++hamming;
				if (expected == (dimension * dimension - 1)) break;
				else ++expected;
			}
		}
		
		return hamming;
	}
	
	public int manhattan(){
		
		int manhattan = 0;
		int toFind = 1;
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length && toFind <= (a.length * a.length - 1); j++) {
					if (toFind == a[i][j]) ++toFind;
					else{
						manhattan += findmoves(a, toFind, i, j);
						++toFind;
					}
				}
		}

		return manhattan;
	}
		

	
	private int findmoves(int[][] a, int whtToFind, int toX, int toY) {
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (whtToFind == a[i][j]) 
					return Math.abs(i - toX) + Math.abs(j - toY);
			}
		}
		
		return 0;
	}

	public boolean isGoal(){
		//dimension*i + j + 1 даёт значение, которое должно находится в a[i][j]
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if ((i == dimension - 1) && j == (dimension - 1)) break;
				if ((dimension*i + j + 1) != a[i][j]) 
					return false;
			}
		}
		//if (this.hamming() != 0) return false;
		
		return true;
	}
	
	
	//данный метод возвращает близнеца оригинального Board, 
	//но с поменяными местами двумя плитками, которые не являются 0 (пустой плиткой) 
	//Данный метод нужен для проверки на решаемость
	public Board twin(){
		
		int[][] twin = new int[dimension][dimension];
		for (int i = 0; i < a.length; i++) {
			System.arraycopy(a[i], 0, twin[i], 0, a.length);
		}
		
		int swapRow = 0;
		if (twin[0][0] == 0 || twin[0][1] == 0){
			swapRow = 1;
		}
		
		int tmp = twin[swapRow][0];
		twin[swapRow][0] = twin[swapRow][1];
		twin[swapRow][1] = tmp;
		
		return new Board(twin);
	}
	
	

	// данный метод проверяет, является ли объект y копией this
	public boolean equals(Object y){
		
		if (y == null) return false;
		if (y == this) return true;
		if (this.getClass() != y.getClass()) return false;
		
		if (this.a.length != ((Board)y).a.length) return false;
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (this.a[i][j] != ((Board)y).a[i][j]) return false;
			}
		}
		
		return true;
	}
	
	
	//возвращает список возможных соседей текущего нода
	public Iterable<Board> neighbours(){
		
		List<Board> neibours = new LinkedList<>();
		
		int blankX = 0, blankY = 0;
		
		//ищем координаты пустой клетки
		label: for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 0){
					blankX = i;
					blankY = j;
					break label;
				}
			}
		}
		//возможное смещения от положения пустой клетки: вверх, вправо, вниз, влево
		if (blankX > 0){
			Board tmp = new Board(this);
			exch(tmp, blankX, blankY, blankX - 1, blankY);
			neibours.add(tmp);
		}
		
		if (blankY < dimension - 1){
			Board tmp = new Board(this);
			exch(tmp, blankX, blankY, blankX, blankY + 1);
			neibours.add(tmp);
		}
		
		if (blankX < dimension - 1){
			Board tmp = new Board(this);
			exch(tmp, blankX, blankY, blankX + 1, blankY);
			neibours.add(tmp);
		}
		
		if (blankY > 0){
			Board tmp = new Board(this);
			exch(tmp, blankX, blankY, blankX, blankY - 1);
			neibours.add(tmp);
		}
		
		return neibours;
	}
	
	private void exch(Board nei, int p1x, int p1y, int p2x, int p2y){
		int tmp = nei.a[p1x][p1y];
		nei.a[p1x][p1y] = nei.a[p2x][p2y];
		nei.a[p2x][p2y] = tmp;
	}
	
	
	public String toString(){
		
//		for (int i = 0; i < a.length; i++) {
//			for (int j = 0; j < a[i].length; j++) {
//				System.out.printf("%2d ", a[i][j]);
//			}
//		System.out.println();
//		}
//		System.out.println();
		
		StringBuilder bldr = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				bldr.append(String.valueOf(a[i][j] + " "));
			}
		bldr.append("\n");
		}
		
		return bldr.toString();
	}
	
	public static void main(String[] args) {
		int[][] testBoard = {{8,1,3},{4,0,2},{7,6,5}};
		int[][] goalBoard = {{1,2,3},{4,5,6},{7,8,0}};
		
		Board b = new Board(testBoard);
		Board g = new Board(goalBoard);
		System.out.println(b.hamming());
		System.out.println(b.manhattan());
		System.out.println(b.isGoal() + " --- " + g.isGoal());
		
		for (Board neib : b.neighbours()) {
			neib.toString();
			System.out.println("Neibour is Goal = " + neib.isGoal());
		}
		
	}
}
