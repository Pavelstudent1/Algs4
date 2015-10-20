package homeWork3_Pattern_Recognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
	
	final static int MIN_POINT_IN_LINE = 4;
	List<LineSegment> uniq_line = new ArrayList<>();
	
	private class TempLine{	//т.к. класс LineSegment по зданию нельзя менять,
		Point start;		//для временных операций используется данный аналог
		Point end;
	}
	
	public FastCollinearPoints(Point[] p) {
	
		
	for (int i = 0; i < p.length; i++) {
		
		Point[] t = new Point[p.length];	//чтобы не менять исходный порядок элементов, создаём копию вх. массива
		System.arraycopy(p, 0, t, 0, p.length);	
		Arrays.sort(t, t[i].slopeOrder());	//сортируем массив точек по slopeOrder относительно выбранной точки
											//, получая в нём участки с повторяющимися значениями - это и есть 
											//искомые прямые.
		
		int startLineIndex = 0;
		int lengthCount = 0;
		
		double prev = Double.NaN;
		for (int j = 1; j < t.length; j++) {
			
			double cur = t[0].slopeTo(t[j]);
			if (cur != prev){
				if (lengthCount != 0) {
					examLine(t, startLineIndex, lengthCount); //проверим полученный отрезок
					startLineIndex = lengthCount = 0; //обнуляем счётчики и смотрим дальше
				}
				prev = cur;
			}else{
				if (lengthCount == 0) {
					startLineIndex = j - 1;
					lengthCount = 3;
				}
				else lengthCount++;
			}
			
		}
		if (lengthCount != 0) examLine(t, startLineIndex, lengthCount);
	}
	
	
	
	
	}
	
	private void examLine(Point[] t, int startLineIndex, int lengthCount) {
		
		if (lengthCount < MIN_POINT_IN_LINE) return;
		LineSegment line = makeLine(t, startLineIndex, lengthCount);
		tryAddLine(line);
		
	}

	private void tryAddLine(LineSegment line) {

		if (uniq_line.isEmpty()){
			uniq_line.add(line);
		}else{
			for (LineSegment l : uniq_line) {
				if (l.p.compareTo(line.p) == 0 && l.q.compareTo(line.q) == 0) return; //можно попробовать сравнивать ссылки, а не координаты
			}
			
			uniq_line.add(line);
		}
	}

	private LineSegment makeLine(Point[] src, int startLineIndex,int lengthCount) {

		Point[] t = new Point[lengthCount];
		t[0] = src[0];
		System.arraycopy(src, startLineIndex, t, 1, lengthCount - 1);
		
		//нахождение крайних 2-х точек линии с мин. и макс. значениями Х-координаты
		Point minP = null, maxP = null;
		int min = Integer.MAX_VALUE, max = -1;
		if (isLineVertical(t)){ //проверка, что линия НЕ вертикальная
			
			for (int i = 0; i < t.length; i++) {
				if (t[i].getY() < min){
					min = t[i].getY();
					minP = t[i];
				}
				if (t[i].getY() > max){
					max = t[i].getY();
					maxP = t[i];
				}
			}
			
			
		}else{
			for (int i = 0; i < t.length; i++) {
				if (t[i].getX() < min){
					min = t[i].getX();
					minP = t[i];
				}
				if (t[i].getX() > max){
					max = t[i].getX();
					maxP = t[i];
				}
			}
		}
		
		
		return new LineSegment(minP, maxP);
	}
	
	private boolean isLineVertical(Point[] src){

		for (int i = 0; i < src.length - 1; i++) {
			if (src[i].getX() != src[i + 1].getX()) return false;
		}
		
		return true;
	}

	public int numberOfSegments(){
		return uniq_line.size();
	}
	
	public LineSegment[] segments(){
		LineSegment[] out = new LineSegment[uniq_line.size()];
		for (int i = 0; i < out.length; i++) {
			out[i] = uniq_line.get(i);
		}
		
		return out;
	}
}
