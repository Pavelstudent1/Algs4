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
		List<BlankLine> lines = new ArrayList<>();

		Point minPoint = null, maxPoint = null;

		for (int i = 0; i < tmp.length; i++) {

			if (maxPoint != null) {
				BlankLine bline = new BlankLine(minPoint, maxPoint); 
				if (isDifferentLine(lines, bline)) {
					lines.add(bline);
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
							//как только нашли макс -> создаём линию
							//в проверке линий нужно учесть, помимо того, что линия может помещаться в имеющуюся,
							//также и то, что добавляемая может вместить в себя имеющуюся, тогда имеющаяся
							//заменяется (перепись отличных координат) на добавляемую
							System.out.print(tmp[i] + " -> " + tmp[l]);
							System.out.printf("| i = %d, j = %d, k = %d, l = %d\n", i, j, k, l);
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
	//вспомогательный класс с доступам к координатам, чего LineSegment не позволяет по условию задачи
	private class BlankLine{
		Point a = null;
		Point b = null;
		
		public BlankLine(Point a, Point b) {
			this.a = a;
			this.b = b;
		}
	}

	
	private boolean isDifferentLine(List<BlankLine> lines, BlankLine l) {
		
		if (lines.size() == 0) return true;
		
		for (BlankLine bl : lines) { 
//			if (isHorizontal(bl, l) && (bl.a.getY() != l.a.getY())) return true;
			if (bl.a.compareTo(l.a) <= 0 && bl.b.compareTo(l.b) >= 0) return false;
		}
		
		return true;
	}
	

	private boolean isHorizontal(BlankLine bl, BlankLine l) {

		if (bl.a.getY() == bl.b.getY() && l.a.getY() == l.b.getY()) return true;
			
		return false;
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
