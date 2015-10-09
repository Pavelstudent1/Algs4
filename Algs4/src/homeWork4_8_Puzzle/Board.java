package homeWork4_8_Puzzle;

public class Board {
	
	private final int[][] a;
	private int dimension = 0;
	
	public Board(int[][] blocks) {
		a = blocks;
		dimension = blocks.length;
	}
	
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
		return 0;
	}
	
	public boolean isGoal(){
		return false;
	}
	
	public Board twin(){
		return null;
	}
	
	public boolean equals(Object y){
		return false;
	}
	
	public Iterable<Board> neighbours(){
		return null;
	}
	
	public String toString(){
		return null;
	}
	
	public static void main(String[] args) {
		int[][] testBoard = {{8,1,3},{4,0,2},{7,6,5}};
		
		Board b = new Board(testBoard);
		System.out.println(b.hamming());
		
	}
}
