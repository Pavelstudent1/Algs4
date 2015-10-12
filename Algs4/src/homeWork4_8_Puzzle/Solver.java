package homeWork4_8_Puzzle;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	
	private class SearchNode implements Comparable<Board>{
		
		private Board current;
		private SearchNode previous;
		private int moves;
		
		public SearchNode(Board b) {
			this.current = b;
			this.previous = null;
			this.moves = 0;
		}
		
		
		
		
		@Override
		public int compareTo(Board o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
	}
	
	public Solver(Board initial) {
		
		

	}
	
	public boolean isSolvable() {
		return false;
	}
	
	public int moves(){
		return 0;
	}
	
	public Iterable<Board> solution(){
		return null;
	}
	
	public static void main(String[] args) {
		int[][] testBoard = {{0,1,3},{4,2,5},{7,8,6}};
		Board b = new Board(testBoard);
		Solver s = new Solver(b);
		
		
		
	}
}
