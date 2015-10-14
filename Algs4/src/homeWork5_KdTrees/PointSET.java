package homeWork5_KdTrees;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	
	private Set<Point2D> sp;
	
	public PointSET() {
		sp = new TreeSet<Point2D>();
	}
	
	public boolean isEmpty(){
		return sp.isEmpty();
	}
	
	public int size(){
		return sp.size();
	}
	
	public void insert(Point2D p){
		if (p == null) throw new NullPointerException();
		sp.add(p);
	}
	
	public boolean contains(Point2D p){
		if (p == null) throw new NullPointerException();
		return sp.contains(p);
	}
	
	public void draw(){
		for (Point2D p : sp) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect){
		if (rect == null) throw new NullPointerException();
		
		ArrayList<Point2D> list = new ArrayList<Point2D>();
		for (Point2D p : sp) {
			if (rect.contains(p))
				list.add(p);
		}
		
		return list;
	}
	
	public Point2D nearest(Point2D p){
		if (p == null) throw new NullPointerException();
		
		double minLength = Double.MAX_VALUE;
		Point2D nearestPoint;
		for (Point2D point : sp) {
			double curLength = point.distanceTo(p);
			if (curLength < minLength)
				minLength = ;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		Point2D[] points = new Point2D[args.length / 2];
		for (int i = 0, j = 0; i < args.length; i += 2, j++) {
			points[j] = new Point2D(Double.valueOf(args[i]), Double.valueOf(args[i + 1]));
		}
		
		PointSET pset = new PointSET();
		for (Point2D p : points) {
			pset.insert(p);
		}
		
		
		StdDraw.show();
		pset.draw();
		
		RectHV rect = new RectHV(0.1, 0.1, 0.5, 0.9);
		rect.draw();
		
		
		
		
		
		
		
		
		
		
	}
	
}
