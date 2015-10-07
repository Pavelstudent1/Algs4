package SingleHashTest;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SingleHashTest {
	
	public static final int MAX_ERROR = 9;
	
	public static void main(String[] args) {
		
		Data data = new Data();
		Iterator<Record> it = data.iterator();
		Record a = null, 
			   b = null;
		
		while(it.hasNext()){
			a = it.next();
			b = it.next();
			if (compare(a, b)) {
				System.out.println("I find them!!!!!11111"); 
				break;
			}
		}
		
		
		char[] A_Hmin = null, B_Hmin = null, X_Hmin = null, TEMP = null;
		int k = 5;
		A_Hmin = findMinHash(a.read, k);
		B_Hmin = findMinHash(b.read, k);
		
		TEMP = new char[k * 2];
		System.arraycopy(A_Hmin, 0, TEMP, 0, k);
		System.arraycopy(B_Hmin, 0, TEMP, k, k);
		X_Hmin = findMinHash(TEMP, k);	//чтобы не делать лишних вычислений, можно спользовать класс, содержащий в себе
										//два поля: char[] и его int-хэш
		
		Set<Character> set = new TreeSet<>();
		for (int i = 0; i < k; i++) {
			set.add(A_Hmin[i]);
			set.add(B_Hmin[i]);
		}
		
		for (Character character : set) {
			System.out.print(character + " ");
		}
		System.out.println();
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("Done");
	}

	private static char[] findMinHash(char[] t, int k) {
		
		char[] minSeq = null;
		int minHash = Integer.MAX_VALUE,	//также, можно не высчитывать первый ход от i = 0,
											//т.к. первый хэш всегда будет меньше Integer.MAX_VALUE
			curHash = 0;
		for(int i = 0; i < t.length; i += k){
			curHash = getHash(t, i, i + k, k);
			if (curHash < minHash){
				minHash = curHash;
				minSeq = Arrays.copyOfRange(t, i, i + k); //можно не копировать постоянно, а сделав это в конце, 
			}												   //просто запоминая i и i + k значения. И использовать System.arraycopy
		}
		
		return minSeq;
	}
	
	private static int getHash(char[] t, int i, int j, int len){
		//return Integer.valueOf(String.copyValueOf(rec.read).hashCode());
		int hash = 0;
		for(int k = i; k < j; k++){
			hash += (t[k]*(31 << len));
		}
		
		return hash;
	}

	private static boolean compare(Record a, Record b) {
		
		if (a.read.length != b.read.length) return false;
		int cur_error = 0;
		
		for (int i = 0; i < a.read.length && cur_error < MAX_ERROR; i++) {
			if (a.read[i] != b.read[i]){
				cur_error++;
			}
		}
		
		return (cur_error < MAX_ERROR);
	}
}
