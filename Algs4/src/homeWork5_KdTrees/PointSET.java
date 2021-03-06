package homeWork5_KdTrees;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
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
		Point2D nearestPoint = null;
		for (Point2D point : sp) {
			double curLength = point.distanceTo(p);
			if (curLength < minLength){
				minLength = curLength;
				nearestPoint = point;
			}
		}
		
		return nearestPoint;
	}
	
	public static void main(String[] args) {
		
		In in = new In(args[0]);
		PointSET pset = new PointSET();
		
		while(!in.isEmpty()){
			double x = in.readDouble();
			double y = in.readDouble();
			pset.insert(new Point2D(x, y));
		}
		
		StdDraw.setPenRadius(0.005);
		StdDraw.show();
		pset.draw();
		
//		StdDraw.setPenRadius();
//		RectHV rect = new RectHV(0.1, 0.1, 0.5, 0.9);
//		rect.draw();
		
	}
	
}
