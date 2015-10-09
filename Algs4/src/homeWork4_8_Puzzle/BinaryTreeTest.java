package homeWork4_8_Puzzle;

import java.util.Random;

public class BinaryTreeTest<T extends Comparable<T>>{
	
	private T[] tree;
	private int N;
	
	public BinaryTreeTest(int fixSize) {
		tree = (T[]) new Comparable[fixSize + 1];
		N = 0;
	}
	
	public boolean isEmpty() { return N == 0; }
	
	public void add(T e){
		tree[++N] = e;
		swimUp(N);
	}
	
	private void swimUp(int k){
		while(k > 1 && less(k/2 , k)){
			exch(k, k/2);
			k = k/2;
		}
	}
	
	public T delMax(){
		T max = tree[1];
		exch(1, N--);
		sink(1);
		tree[N+1] = null;
		return max;
	}
	
	private void sink(int k){
		while(2*k <= N){
			int j = 2*k;
			if (j < N && less(j, j+1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
	
	
	private void exch(int i, int j) {
		T tmp = tree[i];
		tree[i] = tree[j];
		tree[j] = tmp;
	}

	private boolean less(int i, int k) {
		return tree[i].compareTo(tree[k]) < 0;
	}

	public static void main(String[] args) {
		
		
		
		
		
//		Random rand = new Random();
//		String word = "fhdyvmspz";
//		BinaryTreeTest<Character> tree = new BinaryTreeTest<>(word.length() + 1);
//		for (int i = 0; i < word.length(); i++) {
//			tree.add(word.charAt(i));
//		}
		
		
	}
}
