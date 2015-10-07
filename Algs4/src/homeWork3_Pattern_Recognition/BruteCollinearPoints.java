package homeWork3_Pattern_Recognition;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BruteCollinearPoints {
	private Point[] points;
	private int numberOfSegments;
	private final int minPointsInLine = 4;
	private LineSegment[] lineseg;
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new NullPointerException("Constructor's argument is null");
		for (Point point : points) {
			if (points == null) throw new NullPointerException("Find a null reference in constructor's argument");
		}
		
		Point[] tmp = new Point[points.length];
		System.arraycopy(points, 0, tmp, 0, points.length);
		Arrays.sort(tmp);
		for (int i = 0; i < tmp.length - 1; i++) {
			if (tmp[i].compareTo(tmp[i + 1]) == 0)
				throw new IllegalArgumentException("Points array have dublicates");
		}
		
		
		this.points = points;
		
		
		
//		for (int i = 0; i < points.length; i++) {
//			
//			LineSegment[] tmpL = new LineSegment[pointsInLine];
//			int pointsCounter = 0;
//			double curSlope = Double.MAX_VALUE;
//			
//			for (int j = 0; j < points.length && pointsCounter <= 4; j++) {
//				if (points[i] == points[j]) continue;
//				double res = points[i].slopeTo(points[j]);
//				if (curSlope == Double.MAX_VALUE){
//					curSlope = res;
//				}else if(res == curSlope){
//					tmpL[pointsCounter++] = new LineSegment(points[i], points[j]);
//				}else{
//					pointsCounter = 0;
//					tmpL = null;
//				}
//			}
//
//		}
		
		Point[] p = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			
			double prev = Double.MAX_VALUE;
			for (int j = i; j < points.length; j++) {
				
				double cur = points[i].slopeTo(points[j]);
				if (prev == Double.MAX_VALUE) prev = cur;
				else if (prev == cur){
					
				}
				
			}
		}
		
		
		
		
		
	}
	
	public int numberOfSegments(){
		
		return 0;
	}
	
	public LineSegment[] segments(){
		
		
		
		return null;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}
}
