package homeWork5_KdTrees;

import homeWork5_KdTrees.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		boolean lf = false;	//в какую сторону был последний заход

		for (i = 0; curNode != null; i++) {

			if (i % 2 == 0) {	//curNode - сканируемый узел, а p - точка, которой ищется нужное место
				if (Point2D.X_ORDER.compare(curNode.p, p) > 0) {
					prevNode = curNode;
					curNode = curNode.left;
					lf = true;
				} else if (Point2D.X_ORDER.compare(curNode.p, p) <= 0) {
					prevNode = curNode;
					curNode = curNode.right;
					lf = false;
				}
			} else {
				if (Point2D.Y_ORDER.compare(curNode.p, p) > 0) { 
					prevNode = curNode;
					curNode = curNode.left;
					lf = true;
				} else if (Point2D.Y_ORDER.compare(curNode.p, p) <= 0) {
					prevNode = curNode;
					curNode = curNode.right;
					lf = false;
				}
			}

		}
		
		++this.size;
		Node node = new Node(p, i);
		//p.draw();
		
		if (root == null) {
			root = node;
			return;
		} else {
			if (lf) {
				prevNode.left = node;
			} else {
				
				prevNode.right = node;
			}
		}
		
		try{
			
		if (prevNode.isVertical){
			if (lf) node.r = new RectHV(prevNode.r.xmin(), prevNode.r.ymin(), prevNode.p.x(), prevNode.r.ymax());
			else node.r = new RectHV(prevNode.p.x(), prevNode.r.ymin(), prevNode.r.xmax(), prevNode.r.ymax());
		}else{
			if (lf) node.r = new RectHV(prevNode.r.xmin(), prevNode.r.ymin(), prevNode.r.xmax(), prevNode.p.y());			
			else node.r = new RectHV(prevNode.r.xmin(), prevNode.p.y(), prevNode.r.xmax(), prevNode.r.ymax());			
		}
		}catch(IllegalArgumentException e){
			System.out.println("null");
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
		
		StdDraw.setPenRadius();
		if (node.isVertical){
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.p.x(), node.r.ymin(), node.p.x(), node.r.ymax());
		}else{
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(node.r.xmin(), node.p.y(), node.r.xmax(), node.p.y());			
		}
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		node.p.draw();
		
		
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		
		ArrayList<Point2D> list = new ArrayList<>();
		Node node = root;
		
		rangeRecursive(node, rect, list);
		
		return list;
	}
	
	private void rangeRecursive(Node node, RectHV r, List<Point2D> l){
		
		if (r.contains(node.p)) 
			l.add(node.p);			
		//рассмотреть возможность запуска двух потоков для поиска наимешьшего расстояния:
		//один поток идёт налево, один на право, а на выходе сравнить, у кого расстояние меньше.
		if (node.left != null && node.left.r.intersects(r)){ 
			rangeRecursive(node.left, r, l);			
		}
		if (node.right != null && node.right.r.intersects(r)){
			rangeRecursive(node.right, r, l);
		}
		
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		
		Node cur = root;
		double minLength = Double.MAX_VALUE;
		Point2D nearestPoint = null;
		
//		Point2D nearestPoint = null;
//		while(cur != null){ //случай, когда точка лежит на одной из линий не учитывается
//			pointOn(cur.p);
//			double curMin = cur.p.distanceTo(p);
//			if (curMin < minLength){
//				minLength = curMin;
//				nearestPoint = cur.p;
//			}
//			pointOff(cur.p);
//			if (cur.left != null && cur.left.r.contains(p)) 
//				cur = cur.left;
//			else 
//				cur = cur.right;
//			
//		}
		
		return nearestRecursive(p, nearestPoint, cur, minLength);
	}
	
	private Point2D nearestRecursive(Point2D p, Point2D near, Node node, double minL){
		
		pointOn(node.p);
		double minCur = node.p.distanceTo(p);
		if (minCur < minL){
			minL = minCur;
			near = node.p;
		}
		pointOff(node.p);
		if (node.left != null && node.left.r.contains(p))
			near = nearestRecursive(p, near, node.left, minL);
		if (node.right != null && node.right.r.contains(p))
			near = nearestRecursive(p, near, node.right, minL);
		
		return near;
	}
	
	
	
	private void pointOn(Point2D p){
		StdDraw.setPenColor(StdDraw.ORANGE);
		p.draw();
	}
	
	private void pointOff(Point2D p){
		StdDraw.setPenColor(StdDraw.BLACK);
		p.draw();		
	}
	
	

	public static void main(String[] args) {

//		Point2D[] points = new Point2D[args.length / 2];
//		for (int i = 0, j = 0; i < args.length; i += 2, j++) {
//			points[j] = new Point2D(Double.valueOf(args[i]), Double.valueOf(args[i + 1]));
//		}
		
//		StdDraw.show();
//		StdDraw.setPenRadius(0.01);
//		KdTree kd = new KdTree();
//		for (Point2D p : points) {
//			kd.insert(p);
//		}
		
		KdTree kd = new KdTree();
		Scanner in = null;
		try {
			in = new Scanner(new File("C:\\Users\\Pavel_Fedorov\\Downloads\\kdtree\\circle1000.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(in.hasNext()){
			double x = in.nextDouble();
			double y = in.nextDouble();
			kd.insert(new Point2D(x, y));
		}
		in.close();
		

		
		StdDraw.clear();
		kd.draw();
		
		StdDraw.setPenRadius(0.01);
		while(true){
			
			if (StdDraw.mousePressed()){
				//StdDraw.clear();
				kd.draw();
				double x = StdDraw.mouseX();
				double y = StdDraw.mouseY();
				Point2D p = new Point2D(x,y);
				StdDraw.setPenColor(StdDraw.GREEN);
				p.draw();
				Point2D nearest = kd.nearest(p);
				StdDraw.setPenColor(StdDraw.GREEN);
				nearest.draw();
			}
		}
		
		
	}

}
