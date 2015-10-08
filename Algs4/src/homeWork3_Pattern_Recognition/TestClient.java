package homeWork3_Pattern_Recognition;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class TestClient {
	
	public static void main(String[] args) {

	    // read the N points from a file
//	    In in = new In(args[0]);
	    //int N = in.readInt();
		int N = Integer.valueOf(args[0]);
	    Point[] points = new Point[N];
	    for (int i = 1, j = 0; i < N * 2; i += 2, j++) {
	        int x = Integer.valueOf(args[i]);
	        int y = Integer.valueOf(args[i + 1]);
//	        int x = in.readInt();
//	        int y = in.readInt();
	        points[j] = new Point(x, y);
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
	    //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	}
}
