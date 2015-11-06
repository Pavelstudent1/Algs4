package homeWork6_WordNet;

import edu.princeton.cs.algs4.Digraph;

public class SAP { //SAP = Shortest Ancestral Path
	private Digraph dg;
	
	public SAP(Digraph G) {
		if (G == null) throw new NullPointerException();
		dg = G;
	}
	
	public int length(int v, int w){
		
		return 0;
	}
	
	public int ancestor(int v, int w){
		
		DirectedDFS dv = new DirectedDFS(dg, v);
		DirectedDFS dw = new DirectedDFS(dg, w);
		
		int ancestor = -1, minlength = -1, i = 0;
		
		while(i < dg.V()){
			if (dv.marked[i].wasHere == dw.marked[i].wasHere){
				int length = dv.marked[i].length + dw.marked[i].length;
				if (minlength == -1 || length > 0 && length <= minlength){
					ancestor = i;
					minlength = length;
				}
			}
			++i;
		}
		
		
		
	
		return ancestor;
	}
	
	public int length(Iterable<Integer> v, Iterable<Integer> w){
		
		return 0;
	}
	
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
		
		return 0;
	}
	
	public static void main(String[] args) {
		
	
	}
	
	private class DirectedDFS {
		private class Node{
			boolean wasHere;
			int length;
			
			public Node() {
				wasHere = false;
				length = -1;
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
			marked[v].wasHere = true;
			marked[v].length = len;
		
			for (int w : G.adj(v)) {
				if (!marked[w].wasHere) dfs(G, w, ++len);
			}
		}
		
	}
}
