package homeWork5_KdTrees;

import homeWork5_KdTrees.Node;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private Node root;
	private int size;

	public KdTree() {
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (root == null);
	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		int i = 0;
		Node curNode = root;
		Node prevNode = null;
		boolean lf = false;

		for (i = 0; curNode != null; i++) {

			if (i % 2 == 0) {
				if (Point2D.X_ORDER.compare(curNode.p, p) > 0) {
					prevNode = curNode;
					curNode = curNode.left;
					lf = true;
				} else if (Point2D.X_ORDER.compare(curNode.p, p) < 0) {
					prevNode = curNode;
					curNode = curNode.right;
					lf = false;
				}
			} else {
				if (Point2D.Y_ORDER.compare(curNode.p, p) > 0) {
					prevNode = curNode;
					curNode = curNode.left;
					lf = true;
				} else if (Point2D.Y_ORDER.compare(curNode.p, p) < 0) {
					prevNode = curNode;
					curNode = curNode.right;
					lf = false;
				}
			}

		}
		
		++this.size;
		Node node = new Node();
		node.p = p;
		node.depth = i;
		node.isVertical = (node.depth % 2 == 0 ? true : false);
		node.left = node.right = null;
		
		if (node.isVertical){
			if (lf) node.r = new RectHV(xmin, ymin, xmax, ymax);
			else node.r = new RectHV(xmin, ymin, xmax, ymax);
		}else{
			if (lf) node.r = new RectHV(prevNode.r.xmin(), p.y(), prevNode.p.x(), p.y());			
			else node.r = new RectHV(prevNode.r.xmin(), p.y(), prevNode.p.x(), p.y());			
		}
		
		if (root == null) {
			root = node;
		} else {
			if (lf) {
				prevNode.left = node;
			} else {
				
				prevNode.right = node;
			}
		}
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerException();

		return false;
	}

	public void draw() {
		
		draw(root);
		
	}

	private void draw(Node node) {
		
		if (node == null) return;
		if (node != null) draw(node.left);
		if (node != null) draw(node.right);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		node.p.draw();
		
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		
		ArrayList<Point2D> list = new ArrayList<>();
		
		
		
		
		return null;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerException();

		return null;
	}

	public static void main(String[] args) {

		Point2D[] points = new Point2D[args.length / 2];
		for (int i = 0, j = 0; i < args.length; i += 2, j++) {
			points[j] = new Point2D(Double.valueOf(args[i]), Double.valueOf(args[i + 1]));
		}

		KdTree kd = new KdTree();
		for (Point2D p : points) {
			kd.insert(p);
		}

		StdDraw.show();
		kd.draw();

	}

}
