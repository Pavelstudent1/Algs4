package homeWork5_KdTrees;

import homeWork5_KdTrees.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
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
			
			if (curNode.p.equals(p)) return;

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
		
			
		if (prevNode.isVertical){
			if (lf) node.r = new RectHV(prevNode.r.xmin(), prevNode.r.ymin(), prevNode.p.x(), prevNode.r.ymax());
			else node.r = new RectHV(prevNode.p.x(), prevNode.r.ymin(), prevNode.r.xmax(), prevNode.r.ymax());
		}else{
			if (lf) node.r = new RectHV(prevNode.r.xmin(), prevNode.r.ymin(), prevNode.r.xmax(), prevNode.p.y());			
			else node.r = new RectHV(prevNode.r.xmin(), prevNode.p.y(), prevNode.r.xmax(), prevNode.r.ymax());			
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
		
		//return nearestRecursive(p, nearestPoint, cur, minLength);
		return nearestRecursiveV2(p, nearestPoint, cur);
	}
	
	//Почти хорошо работающий вариант: в большей части случаев подкрашивает ближайший
	//узел
	private Point2D nearestRecursive(Point2D p, Point2D near, Node node, double minL){
		
		//pointOn(node);
		double minCur = node.p.distanceTo(p);
		if (minCur < minL){
			minL = minCur;
			near = node.p;
		}
		//pointOff(node);
		/**Проблема: вследствие того, что качество поиска ближайшей точки дерева зависит от количества самих точек,
		 * а точнее, от степени разбитости пространства, имеется проблема, когда визуально, точка близка к той точке,
		 * к которой альгоритм поиска не придёт в силу логики его работы. Вероятное решение проблемы: искомая точка
		 * снабжается некоторым радиусом(т.е. вокруг точки описывается окружность), который также проверяется на 
		 * пересечение с соседними областями, точки которых вероятно могут быть ближе к заданной точке.
		 * Вот брутфорсный вариант такой проблемы не имеет >:(
		 **/
		
		
//		if (node.left != null && node.left.r.contains(p))
//			near = nearestRecursive(p, near, node.left, minL);
//		if (node.right != null && node.right.r.contains(p))
//			near = nearestRecursive(p, near, node.right, minL);
		
		Point2D candidate = near;
		if (node.left != null && node.left.r.contains(p)){
			candidate = nearestRecursive(p, near, node.left, minL);
			if (candidate == near && node.right != null) {
				candidate = nearestRecursive(p, near, node.right, minL);
			}
		}
			
		if (node.right != null && node.right.r.contains(p)){
			candidate = nearestRecursive(p, near, node.right, minL);
			if (candidate == near && node.left != null){
				candidate = nearestRecursive(p, near, node.left, minL);
			}
			
		}
		
		return candidate;
//		return near;
	}
	
	//Pаботает, но суть работы немного напоминает брут-форс: множеством рекурсивных
	//вызовом вынуждаем зайти во все(все?) листья КД-дерева. Зато лишён
	//недостатков прошлой реализации(в силу разбития пространства на части, алгоритм
	//мог не зайти в лист, точка которого очевидно ближе к заданной точке, но кусок
	//области этого листа точку не содержит)
	private Point2D nearestRecursiveV2(Point2D p, Point2D near, Node node){
		
		//pointOn(node);
		double minNear = (near == null ? Double.MAX_VALUE : near.distanceTo(p));
		//т.к. имеем дело с рекрсиями и выходами из них,
		//мин. расстояние вычисляем ВНУТРИ конкретного вызова
		if (node.p.distanceTo(p) < minNear){ 
			near = node.p;					 
		}
		
		//pointOff(node);

		if (node.isVertical){
			if (p.x() <= node.p.x()){
				if (node.left != null) near = nearestRecursiveV2(p, near, node.left);
				if (node.right != null) near = nearestRecursiveV2(p, near, node.right);
			}else{
				if (node.right != null) near = nearestRecursiveV2(p, near, node.right);
				if (node.left != null) near = nearestRecursiveV2(p, near, node.left);				
			}
		}else{
			if (p.y() <= node.p.y()){
				if (node.left != null) near = nearestRecursiveV2(p, near, node.left);
				if (node.right != null) near = nearestRecursiveV2(p, near, node.right);
			}else{
				if (node.right != null) near = nearestRecursiveV2(p, near, node.right);
				if (node.left != null) near = nearestRecursiveV2(p, near, node.left);				
			}
		}
		
		return near;
	}
	
	
	
//	private Point2D nearestRecursive(Point2D p, Point2D near, Node node, double minL){
//		
//		pointOn(node);
//		double minCur = node.p.distanceTo(p);
//		if (minCur < minL){
//			minL = minCur;
//			near = node.p;
//		}
//		pointOff(node);
//		if (node.left != null && node.left.r.contains(p))
//			near = nearestRecursive(p, near, node.left, minL);
//		if (node.right != null && node.right.r.contains(p))
//			near = nearestRecursive(p, near, node.right, minL);
//		
//		return near;
//	}
	
	
	
	private void pointOn(Node node){
		StdDraw.setPenColor(StdDraw.ORANGE);
		node.p.draw();
		StdDraw.setPenColor(StdDraw.RED);
		if (node.left != null) node.left.p.draw();
		StdDraw.setPenColor(StdDraw.GREEN);
		if (node.right != null) node.right.p.draw();
		
	}
	
	private void pointOff(Node node){
		StdDraw.setPenColor(StdDraw.BLACK);
		node.p.draw();
		StdDraw.setPenColor(StdDraw.BLACK);
		if (node.left != null) node.left.p.draw();
		StdDraw.setPenColor(StdDraw.BLACK);
		if (node.right != null) node.right.p.draw();
	}
	
	
	
	public static void main(String[] args) {
	
		In in = new In(args[0]);
		
		KdTree kd = new KdTree();
		while(!in.isEmpty()){
			double x = in.readDouble();
			double y = in.readDouble();
			kd.insert(new Point2D(x, y));
		}
		
		kd.draw();
		StdDraw.show();
		
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
