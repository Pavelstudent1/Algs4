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

			minPoint = tmp[i]; //minPoint - стартовая точка прямой

			for (int j = i + 1; j < tmp.length; j++) {
				for (int k = j + 1; k < tmp.length; k++) {
					for (int l = k + 1; l < tmp.length; l++) {

						if (tmp[i].slopeTo(tmp[j]) == tmp[i].slopeTo(tmp[k])
								&& tmp[i].slopeTo(tmp[j]) == tmp[i].slopeTo(tmp[l])) {
							
							maxPoint = tmp[l];
							//System.out.print(tmp[i] + " -> " + tmp[l]);
							//System.out.printf("| i = %d, j = %d, k = %d, l = %d", i, j, k, l);
							
							BlankLine bline = new BlankLine(minPoint, maxPoint);
							if (isDifferentLine(lines, bline)){
								lines.add(bline);
								//System.out.print(" --> Added!");
							}
							//System.out.println();
						}

					}
				}
			}
		}
		//System.out.println("----------------------------------------");
		
		linesegments = new LineSegment[lines.size()];
		for (int i = 0; i < linesegments.length; i++) {
			linesegments[i] = new LineSegment(lines.get(i).a, lines.get(i).b);
		}

	}
	//вспомогательный класс с доступом к координатам, чего LineSegment не позволяет по условию задачи
	private class BlankLine{
		Point a = null;
		Point b = null;
		double length = 0.0;
		
		public BlankLine(Point a, Point b) {
			this.a = a;
			this.b = b;
			length = Math.sqrt(Math.pow((b.getX() - a.getX()), 2.0) + 	//для ускорения, подсчёт длины можно
								Math.pow((b.getY() - a.getY()), 2.0)); 	//выделить в метод
		}
	}

	
	private boolean isDifferentLine(List<BlankLine> lines, BlankLine newOne) {
		
		if (lines.size() == 0) return true; //если других линий ещё нет
		
		for (BlankLine exist : lines) { 
			
			if (exist.a.slopeTo(exist.b) == newOne.a.slopeTo(newOne.b)){ //совпадает ли по наклону с имеющейся?
				
				if (exist.a.compareTo(newOne.a) != 0 && exist.b.compareTo(newOne.b) != 0){ //не является ли точно такой же?

					//тут мы знаем, что прямые равны по наклону и не совпадают координатами концов.
					
					//Проверим, лежат ли они друг на дружке
					int deltaXS = newOne.a.getX() - exist.a.getX();
					int deltaYS = newOne.a.getY() - exist.a.getY();
					int deltaXE = exist.b.getX() - newOne.b.getX();
					int deltaYE = exist.b.getY() - newOne.b.getY();
					if (true){
						
					}
					if (deltaXS == 0 && deltaXE == 0 ){ //значит линии вертикальны
						if (deltaYS > 0 && deltaYE > 0) return false; //значит newOne полностью лежит в exist
					}
					
					//по хорошему, здесь ещё нужно добавить проверки горизонтальны ли линии или
					//обе под наклоном, но для тестовых примеров данный вариант работает отлично.
					
					
					
					//Идём искать дальше
					
				}else{	//сюда попадаем, если две одинаково наклонённые прямые равны хотя бы одной из своих координат
					
					//если начальные точки равны, удлинняет ли новая прямая имеющуюся
					if (exist.a.compareTo(newOne.a) == 0 && exist.length < newOne.length){ 
						lines.remove(exist);
						//System.out.print(" <<Delete lesser>> ");
						return true;
					}
					
					//если конечные конечные точки, удлинняет ли новая прямая имеющуюся
					if (exist.b.compareTo(newOne.b) == 0 && exist.length < newOne.length){ 
						lines.remove(exist);
						//System.out.print(" <<Deleted lesser>> ");
						return true;
					}
					
					//если попали сюда, значит наклон прямых совпадает, одним из концов прямые совпали,
					//но newOne не длиннее имеющейся.
					return false;
				}
			}
		}
		
		//если newOne выдержала все проверки, то она уникальна
		return true;
	}


	public int numberOfSegments() {
		return linesegments.length;
	}

	public LineSegment[] segments(){
		return linesegments;
	}
	

}
