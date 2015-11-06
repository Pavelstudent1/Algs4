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

public class KdTree2 {

	private Node root;
	private int size;

	public KdTree2() {
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
		
		insert_recursive(p, root, null, 0, false);							
	}
	
	private void insert_recursive(Point2D p, Node cur, Node prev, int depth, boolean isLeftTurn){
		
		if (cur == null){
			cur = new Node(p, depth);
			if (prev != null){
				
			}
			return;
		}
		if (depth % 2 == 0){
			if (Point2D.X_ORDER.compare(p, root.p) < 0){ //если точка левее текущего узла
				insert_recursive(p, cur.left, cur, ++depth, true);						
			}else{
				insert_recursive(p, cur.right, cur, ++depth, false);	
			}
			
		}else{
			
			
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
//	private Point2D nearestRecursive(Point2D p, Point2D near, Node node, double minL){
//		
//		pointOn(node.p);
//		double minCur = node.p.distanceTo(p);
//		if (minCur < minL){
//			minL = minCur;
//			near = node.p;
//		}
//		pointOff(node.p);
//		if (node.left != null && node.left.r.contains(p))
//			near = nearestRecursive(p, near, node.left, minL);
//		if (node.right != null && node.right.r.contains(p))
//			near = nearestRecursive(p, near, node.right, minL);
//		
//		return near;
//	}
	
	
	
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
		
		KdTree2 kd = new KdTree2();
		Scanner in = null;
		try {
			in = new Scanner(new File("C:\\Users\\Pavel_Fedorov\\Downloads\\kdtree\\circle10.txt"));
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
