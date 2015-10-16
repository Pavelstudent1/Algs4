package homeWork5_KdTrees;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {

		//String filename = args[0];
		//In in = new In(filename);
		Scanner in = null;
		try {
			in = new Scanner(new File("C:\\Users\\Pavel_Fedorov\\Downloads\\kdtree\\circle4.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int counter = 0;
		while(in.hasNext()){
			++counter;
			double x = in.nextDouble();
			double y = in.nextDouble();
			System.out.println(counter + " -> " + x + ", " + y);
		}

	}
}
