package homeWork3_Pattern_Recognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
	
	private Point[] points = null;
	private LineSegment[] linesegments = null;

	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new NullPointerException("Constructor's argument is null");
		for (Point point : points) {
			if (points == null)
				throw new NullPointerException("Find a null reference in constructor's argument");
		}

		Point[] tmp = new Point[points.length];
		System.arraycopy(points, 0, tmp, 0, points.length);

		Arrays.sort(tmp); //для брут-форса нужно отсортировать массив точек.
						  //от точки с наименьшими координатами, до точки с
						  //наибольшими
		List<BilletLine> lines = new ArrayList<>();

		Point minPoint = null, maxPoint = null;

		for (int i = 0; i < tmp.length; i++) {

			if (maxPoint != null) {
				BilletLine l = new BilletLine(minPoint, maxPoint); 
				if (isDifferentLine(lines, l)) {
					lines.add(l);
				}
//				lines.add(new BilletLine(minPoint, maxPoint));
				maxPoint = null;
			}
			minPoint = tmp[i];

			for (int j = i + 1; j < tmp.length; j++) {
				for (int k = j + 1; k < tmp.length; k++) {
					for (int l = k + 1; l < tmp.length; l++) {

						if (tmp[i].slopeTo(tmp[j]) == tmp[i].slopeTo(tmp[k])
								&& tmp[i].slopeTo(tmp[j]) == tmp[i].slopeTo(tmp[l])) {
							// lines.add(new LineSegment(tmp[i], tmp[l]));
							maxPoint = tmp[l];
							//System.out.println(tmp[i] + " -> " + tmp[l]);
						}

					}
				}
			}
		}
		
		linesegments = new LineSegment[lines.size()];
		for (int i = 0; i < linesegments.length; i++) {
			linesegments[i] = new LineSegment(lines.get(i).a, lines.get(i).b);
		}

	}
	
	private class BilletLine{
		Point a = null;
		Point b = null;
		
		public BilletLine(Point a, Point b) {
			this.a = a;
			this.b = b;
		}
	}

	private boolean isDifferentLine(List<BilletLine> lines, BilletLine l) {
		
		if (lines.size() == 0) return true;
		
		for (BilletLine bl : lines) {
			if (bl.a.compareTo(l.a) <= 0 && bl.b.compareTo(l.b) >= 0) return false;
		}
		
		return true;
	}

	public int numberOfSegments() {
		return linesegments.length;
	}

	public LineSegment[] segments(){
		return linesegments;
	}

	public static void main(String[] args) {

	}
}
