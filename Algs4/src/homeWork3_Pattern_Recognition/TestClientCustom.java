package homeWork3_Pattern_Recognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class TestClientCustom {
	
	public static void main(String[] args) {

		Scanner in = null;
		try {
			in = new Scanner(new File("C:\\Users\\Pavel_Fedorov\\Downloads\\collinear\\vertical5.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int N = in.nextInt();
	    Point[] points = new Point[N];
	    int counter = 0;
	    while(in.hasNext()) {
	        int x = in.nextInt();
	        int y = in.nextInt();
	        points[counter++] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.show(0);
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    //FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	}
}
