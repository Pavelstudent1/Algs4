package homeWork4_8_Puzzle;

import java.util.ArrayList;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	
	private SearchNode goal;
	
	private class SearchNode implements Comparable<SearchNode>{
		
		private Board cur;
		private SearchNode prevS;
		private int moves;
		
		public SearchNode(Board b, int moves) {
			this.cur = b;
			this.prevS = null;
			this.moves = moves;
		}
		

		@Override
		public int compareTo(SearchNode that) {
			
			int priority1 = this.cur.manhattan() + this.moves;
			int priority2 = that.cur.manhattan() + that.moves;
			
			if (priority1 > priority2) return 1;
			if (priority1 < priority2) return -1;
			return 0;
		}
		
		
	}
	
	public Solver(Board initial) {
		
		SearchNode root = new SearchNode(initial, 0);
		MinPQ<SearchNode> pq = new MinPQ<>();
		pq.insert(root);
		
		SearchNode minSN = pq.delMin();
		while(!minSN.cur.isGoal()){
			
			for (Board b : minSN.cur.neighbours()) {
				if (minSN.prevS == null || !b.equals(minSN.prevS.cur)){
					SearchNode node = new SearchNode(b, minSN.moves + 1);
					node.prevS = minSN;
					pq.insert(node);
				}
			}
			
		minSN = pq.delMin();	
		}
		
		if (minSN.cur.isGoal()){
			goal = minSN;
		}else{
			goal = null;
		}

	}
	
	public boolean isSolvable() {
		return goal != null;
	}
	
	public int moves(){
		return goal == null ? -1 : goal.moves;
	}
	
	public Iterable<Board> solution(){
		if (!isSolvable()) return null;
		ArrayList<Board> path = new ArrayList<>();
		SearchNode tmp = goal;
		while(tmp != null){
			path.add(0, tmp.cur);
			tmp = tmp.prevS;
		}
		
		return path;
	}
	
	public static void main(String[] args) {
		int[][] testBoard = {{0,1,3},{4,2,5},{7,8,6}};
		Board b = new Board(testBoard);
		Solver s = new Solver(b);
		
		int step = 0;
		for (Board brd : s.solution()) {
			System.out.printf("----- Step = %2d -----\n", step++);
			brd.toString();
		}
		
		
	}
}
