package homeWork10_Burrows_Wheeler_Data_Compression;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.text.AbstractDocument.LeafElement;

import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {
	
	private static final boolean SHOW_MORE_INFO = false;
	private String str;
	private Integer[] index;
	
	public CircularSuffixArray(String s) {
		if (s == null) throw new NullPointerException();
		str = s;
		index = new Integer[s.length()];
		for(int i = 0; i < index.length; i++){
			index[i] = i;
		}
		
		Arrays.sort(index, new SuffixComparator());
	}
	
	/**Закономерность суффиксов на картинке в задании:
	 * индекс i показывает с какого символа должен начинаться суффикс,
	 * что и использовано в компараторе, который строк не создаёт (требование задания),
	 * а считает строкой все символы, что после номера индекса.
	 * **/
	private class SuffixComparator implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			
			int firstInx = o1, sndInx = o2;
			
			if (SHOW_MORE_INFO){
				System.out.print("First string = ");
				printlnSuffix(firstInx);
				System.out.print("Second string = ");
				printlnSuffix(sndInx);
				System.out.println("-------------------------");
			}
			
			//цикл для случая, если сравниваемые элементы совпадают,
			//как если бы первые, вторые и т.д. символы двух строк повторялись
			for(int i = 0; i < index.length; i++){
				if (str.charAt(firstInx) > str.charAt(sndInx)) return 1;
				if (str.charAt(firstInx) < str.charAt(sndInx)) return -1;
				//если first и second равны, то увеличиваем их на 1, проверяя,
				//что они не перешли через длину строки (что просто значит читку 
				//с 0-го индекса строки) и проверем их снова, как будто сравниваем
				//две реальные строки
				firstInx++;
				sndInx++;
				if (firstInx > index.length) firstInx = 0;
				if (sndInx > index.length) sndInx = 0;
			}
			
			return 0;
		}

		private void printlnSuffix(int firstInx) {
			
			for (int i = 0, len = index.length; i < index.length; i++) {
				if (firstInx >= len) firstInx = 0;
				System.out.print(str.charAt(firstInx++));
			}
			System.out.println();
		}
		
	}
	public int length(){
		return str.length();
	}
	
	public int index(int i){
		return index[i];
	}
	
	public static void main(String[] args) {
		
		In in = new In(args[0]);
		String s = in.readLine();
		CircularSuffixArray csa = new CircularSuffixArray(s);
		for (int i = 0; i < csa.length(); i++) {
			System.out.print(csa.index(i) + " ");
		}
	}
}
