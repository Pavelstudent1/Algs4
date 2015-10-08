package homeWork3_Pattern_Recognition;

import java.util.Arrays;

public class FastCollinearPoints {
	
	public FastCollinearPoints(Point[] p) {
		
	
	for (int i = 0; i < p.length; i++) {
		Point[] tmp = new Point[p.length];
		for (int j = 0; j < tmp.length; j++) {
			
		}
		System.arraycopy(p, 0, tmp, 0, p.length);
		Arrays.sort(tmp, tmp[i].slopeOrder());
		
		System.out.println();
	}
	
	
	
	
	}
	
	public int numberOfSegments(){
		return 0;
	}
	
	public LineSegment[] segments(){
		
		
		
		return null;
	}
}
