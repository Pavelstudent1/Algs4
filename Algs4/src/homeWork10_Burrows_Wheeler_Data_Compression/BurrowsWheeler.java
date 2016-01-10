/** 
 * 
 * ЗАПУСКАТЬ ТОЛЬКО ИЗ КОНСОЛИ КАК УКАЗАННО В ДОМАШНЕМ ЗАДАНИИ
 * http://coursera.cs.princeton.edu/algs4/assignments/burrows.html
 * 
 * **/

package homeWork10_Burrows_Wheeler_Data_Compression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.In;

public class BurrowsWheeler {
	
	public static void encode(){
		
		
		String str = BinaryStdIn.readString();
		CircularSuffixArray cs = new CircularSuffixArray(str);
		
		//В суффиксном "массиве" строк ищем индекс == 0
		for (int i = 0; i < cs.length(); i++) {
			if (cs.index(i) == 0){
				BinaryStdOut.write(i);
				break;
			}
		}
		
		//Выводим кончики "строк" "массива" суффиксов
		for (int i = 0; i < cs.length(); i++) {
			int pos = cs.index(i);
			if (pos == 0) {	//значит встретили крайний правый символ строки str
				BinaryStdOut.write(str.charAt(str.length() - 1), 8);
				continue;
			}
			BinaryStdOut.write(str.charAt(pos - 1), 8);
		}
		
		BinaryStdOut.close();
	}
	
	
	
	public static void decode(){
		

		int fst = BinaryStdIn.readInt();
		String str = BinaryStdIn.readString();

		char[] sorted = str.toCharArray();
		Arrays.sort(sorted);
		
		int[] next = new int[str.length()];
		
		/** 
		 * Поиск нужного символа
		 * Берётся i-й отсортированного массива символов и ему ищется первый
		 * попавшийся символ в строке str(выходная строка после кодирования)
		 * Если символ повторяющийся, то задаётся нужное число skip'ов, отражающее
		 * количество пропускаемых таких же встреченных символов
		 * **/
		for (int i = 0, skip = 0, skipGoal = 0; i < sorted.length; i++) {
			
			if (i != 0 && sorted[i] != sorted[i - 1]) skipGoal = 0;
			skip = 0;
			
			for (int j = 0; j < str.length(); j++) {
				if (sorted[i] == str.charAt(j)){
					if (skip != skipGoal) {++skip; continue;}
					next[i] = j;
					++skipGoal;
					break;
				}
			}
		}
		
		
		for (int i = next[fst], s = 0; s < str.length(); s++, i = next[i]) {
			BinaryStdOut.write(str.charAt(i));
		}
		
		BinaryStdOut.close();
	}
	
	
	
	
	public static void main(String[] args) {
		
		if (args[0].equals("-")) encode();
		if (args[0].equals("+")) decode();
		
	}
}
