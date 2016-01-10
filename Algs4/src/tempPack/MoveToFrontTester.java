package tempPack;

import homeWork10_Burrows_Wheeler_Data_Compression.MoveToFront;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class MoveToFrontTester {
	
	public static void main(String[] args) {
		InputStream stdIn = System.in;
		PrintStream stdOut = System.out;
		
		byte[] bytes = testEncode(args);
		
		System.setIn(stdIn);
		System.setOut(stdOut);
		
		testDecode(bytes);
		
	}


	private static byte[] testEncode(String[] s) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		try {
			System.setIn(new FileInputStream(s[0]));
			System.setOut(new PrintStream(b));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		MoveToFront.encode();
	
		return b.toByteArray();
	}
	
	private static void testDecode(byte[] bytes) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		System.setIn(new ByteArrayInputStream(bytes));
//		System.setOut(new PrintStream(b));
		
//		In in = new In();
//		while(!in.isEmpty()){
//			System.out.print(in.readChar());
//		}
		
		MoveToFront.decode();
		String out = b.toString();
		
	}
}
