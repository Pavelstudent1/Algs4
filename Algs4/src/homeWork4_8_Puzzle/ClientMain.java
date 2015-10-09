package homeWork4_8_Puzzle;

import edu.princeton.cs.algs4.StdOut;

public class ClientMain {

	public static void main(String[] args) {

		// create initial board from file
		int N = Integer.valueOf(args[0]);
		int counter = 1;
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = Integer.valueOf(args[counter++]);
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}

}
