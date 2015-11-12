package homeWork6_WordNet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP { // SAP = Shortest Ancestral Path
	private Digraph dg;
	private int min_length = -1;
	private int common_ancestor = -1;
	private int prev_v = -1;
	private int prev_w = -1;

	public SAP(Digraph G) {
		if (G == null)
			throw new NullPointerException();
		dg = G;
	}

	public int length(int v, int w) {

		if (v < 0 || v > dg.V() - 1)
			throw new IndexOutOfBoundsException();
		if (w < 0 || w > dg.V() - 1)
			throw new IndexOutOfBoundsException();
		
		//т.к. наименьший путь и общий предок вычисляются одновременно,
		//а запрашиваться путь и предок могут по отдельности,
		//для ускорения храним эти данные, пока v и w не изменились
		if (prev_v != v || prev_w != w) { 
			calcLengthAndAncestor(v, w);
		}

		return this.min_length;
	}

	public int ancestor(int v, int w) {

		if (v < 0 || v > dg.V() - 1)
			throw new IndexOutOfBoundsException();
		if (w < 0 || w > dg.V() - 1)
			throw new IndexOutOfBoundsException();

		if (prev_v != v || prev_w != w) {
			calcLengthAndAncestor(v, w);
		}

		return this.common_ancestor;
	}

	private void calcLengthAndAncestor(int v, int w) {

		BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(dg, v);
		BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(dg, w);

		int ancestor = -1, minlength = Integer.MAX_VALUE, i = 0;
		//одновременно ищем предка и самый короткий путь до него из вершин v и w
		while (i < dg.V()) {
			if (V.hasPathTo(i) == W.hasPathTo(i)) {
				int length = V.distTo(i) + W.distTo(i);
				if (length > 0 && length <= minlength) {
					ancestor = i;
					minlength = length;
				}
			}
			++i;
		}

		this.common_ancestor = ancestor;
		this.min_length = (minlength == Integer.MAX_VALUE ? -1 : minlength);
		this.prev_v = v;
		this.prev_w = w;

	}
	////старый метод, работающий схожим образом с BreadthFirstDirectedPaths
	////(старый метод == модифицированный поиск в глубину)
	////правда не известно, который из них быстрее.
	// private void calcLengthAndAncestor(int v, int w) {
	// // Вычисляем путь от заданной вершины до "дна" графа методом поиска в
	// глубину
	// DirectedDFS dv = new DirectedDFS(dg, v);
	// DirectedDFS dw = new DirectedDFS(dg, w);
	//
	// int ancestor = -1, minlength = Integer.MAX_VALUE, i = 0;
	// // одновременно ищем предка и самый короткий путь до него из вершин v и w
	// while (i < dg.V()) {
	// if (dv.marked[i].wasHere == dw.marked[i].wasHere) {
	// int length = dv.marked[i].length + dw.marked[i].length;
	// if (length > 0 && length <= minlength) {
	// ancestor = i;
	// minlength = length;
	// }
	// }
	// ++i;
	// }
	//
	// this.common_ancestor = ancestor;
	// this.min_length = (minlength == Integer.MAX_VALUE ? -1 : minlength);
	// this.prev_v = v;
	// this.prev_w = w;
	// }

	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		
		for (Integer iv : v) {
			if (iv < 0 || iv > dg.V() - 1)
				throw new IndexOutOfBoundsException();
		}
		
		for (Integer iw : w) {
			if (iw < 0 || iw > dg.V() - 1)
				throw new IndexOutOfBoundsException();
		}
		
		//BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(dg, v);
		//BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(dg, w);
		
		
		calcLengthAndAncestor(v, w);
		
		return this.min_length;
	}

	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		
		for (Integer iv : v) {
			if (iv < 0 || iv > dg.V() - 1)
				throw new IndexOutOfBoundsException();
		}
		
		for (Integer iw : w) {
			if (iw < 0 || iw > dg.V() - 1)
				throw new IndexOutOfBoundsException();
		}
		
		
		calcLengthAndAncestor(v, w);
		
		return this.common_ancestor;
	}
	
	private void calcLengthAndAncestor(Iterable<Integer> v, Iterable<Integer> w){
		
		BreadthFirstDirectedPaths V = new BreadthFirstDirectedPaths(dg, v);
		BreadthFirstDirectedPaths W = new BreadthFirstDirectedPaths(dg, w);
		
		ArrayList<Integer> ancestors = new ArrayList<>();
		int ancestor = -1, minLen = Integer.MAX_VALUE;
		
		for (int i = 0; i < dg.V(); i++) {
			if (V.hasPathTo(i) == W.hasPathTo(i)){
					int len = V.distTo(i) + W.distTo(i);
					if (len > 0 && len <= minLen){
						ancestor = i;
						minLen = len;
					}
			}
		}
		
		this.common_ancestor = ancestor;
		this.min_length = minLen;
		
		
		
		
//		while (i < dg.V()) {
//			if (V.hasPathTo(i) == W.hasPathTo(i)) {
//				int length = V.distTo(i) + W.distTo(i);
//				if (length > 0 && length <= minlength) {
//					ancestor = i;
//					minlength = length;
//				}
//			}
//			++i;
//		}
	}
	
	
	

	private class DirectedDFS {

		private class Node {
			boolean wasHere;
			int length;

			public Node() {
				wasHere = false;
				length = -1;
			}

			public void set(int length) {
				wasHere = true;
				this.length = length;
			}

		}

		Node[] marked;

		public DirectedDFS(Digraph G, int s) {
			marked = new Node[G.V()];
			for (int i = 0; i < marked.length; i++) {
				marked[i] = new Node();
			}
			dfs(G, s, 0);
		}

		private void dfs(Digraph G, int v, int len) {
			marked[v].set(len);

			for (int w : G.adj(v)) {
				if (marked[w].wasHere) { // если в данной вершине уже были
					if (marked[w].length > len) // и если новый путь до неё
												// короче предыдущего
						marked[w].length = len; // переписываем имеющийся
				} else
					dfs(G, w, ++len);
			}
		}

	}

	public static void main(String[] args) {

		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}
