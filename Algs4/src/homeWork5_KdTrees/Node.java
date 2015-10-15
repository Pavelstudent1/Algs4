package homeWork5_KdTrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class Node{

	Point2D p = null;	//координыты точки
	int depth = 0;	//"глубина", на которой находится точка и, от которой будет зависеть ориентация
	boolean isVertical = true;	//ориентация делящей плоскости: true = влево/вниз
								//								false = вправо/вверх 
	Node right = null;
	Node left = null;
	
	double ownLineLen = 1.0;
	double leftLineLen = 0;
	double rightLineLen = 0;
	
	RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
}
