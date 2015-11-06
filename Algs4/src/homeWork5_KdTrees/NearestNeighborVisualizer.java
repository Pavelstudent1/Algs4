
/******************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointSET.java KdTree.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 ******************************************************************************/

package homeWork5_KdTrees;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        for (int i = 1; i < args.length; i += 2) {
        	double x = Double.valueOf(args[i]);
        	double y = Double.valueOf(args[i + 1]);
			Point2D p = new Point2D(x,y);
			//brute.insert(p);
			kdtree.insert(p);
		}

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            //brute.draw();
            kdtree.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            //brute.nearest(query).draw();
            kdtree.nearest(query).draw();
            StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            //kdtree.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
}
