/** 
 * 
 * ЗАПУСКАТЬ ТОЛЬКО ИЗ КОНСОЛИ КАК УКАЗАННО В ДОМАШНЕМ ЗАДАНИИ
 * http://coursera.cs.princeton.edu/algs4/assignments/burrows.html
 * 
 * **/


package homeWork10_Burrows_Wheeler_Data_Compression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	
	public static void encode(){
		
		List<Character> abc = createABC();
		while(!BinaryStdIn.isEmpty()){
			char ch = BinaryStdIn.readChar();
			
			int pos = 0;
			Iterator<Character> it = abc.iterator();
			while(it.hasNext()){	//взятому символу из входа ищем соответствующий в словаре
				if (it.next().equals(ch)){
					BinaryStdOut.write(pos, 8);	//выводим этот символ
					char c = abc.remove(pos);	//удаляем и перемещаем в голову
					abc.add(0, c);
					break;
				}
			pos++;
			}
		}
		
		BinaryStdOut.close();
	}
	
	//создаём набор символов от 0 до 255 включительно
	private static List<Character> createABC() {
		List<Character> list = new ArrayList<Character>();
		for(int i = 0; i < 256; i++){
			list.add((char)i );
		}
		return list;
	}

	public static void decode(){
		List<Character> abc = createABC();
		while(!BinaryStdIn.isEmpty()){
			int pos = BinaryStdIn.readChar(); 
			char c = abc.remove(pos);
//			System.out.print(c);
			BinaryStdOut.write(c);
			abc.add(0, c);
		}
		BinaryStdOut.close();
//		System.out.println();
	}
	
	
	public static void main(String[] args) {
		
		if (args[0].equals("-")) encode();
		if (args[0].equals("+")) decode();
	}
}
