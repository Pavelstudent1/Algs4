package homeWork7_Seam_Carving;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	Picture pic;
	int picWidth;
	int picHeight;

	double[][] picEnergy = null;

	public SeamCarver(Picture picture) {
		if (picture == null)
			throw new NullPointerException("Empty constructor's argument!");
		this.pic = picture;
		this.picWidth = picture.width();
		this.picHeight = picture.height();
		this.calculateEnergyMatrix();
	}

	public Picture picture() {
		return pic;
	}

	public int width() {
		return picWidth;
	}

	public int height() {
		return picHeight;
	}

	// Calculate dual-gradient energy of pixel
	public double energy(int x, int y) {
		if (x < 0 || y < 0 || x > picWidth - 1 || y > picHeight)
			throw new IndexOutOfBoundsException();

		if (x == 0 || x == picWidth - 1)
			return 1000;
		if (y == 0 || y == picHeight - 1)
			return 1000;

		int Rx = pic.get(x + 1, y).getRed() - pic.get(x - 1, y).getRed(), 
			Gx = pic.get(x + 1, y).getGreen() - pic.get(x - 1, y).getGreen(), 
			Bx = pic.get(x + 1, y).getBlue() - pic.get(x - 1, y).getBlue();
		double deltaX = Rx * Rx + Gx * Gx + Bx * Bx;

		int Ry = pic.get(x, y + 1).getRed() - pic.get(x, y - 1).getRed(), 
			Gy = pic.get(x, y + 1).getGreen() - pic.get(x, y - 1).getGreen(), 
			By = pic.get(x, y + 1).getBlue() - pic.get(x, y - 1).getBlue();
		double deltaY = Ry * Ry + Gy * Gy + By * By;

		return Math.sqrt(deltaX + deltaY);
	}



	// Этот метод ищет все вертикальные симы и выбирает наименьшую по суммарной
	// энергии
	// Минус метода идёт из условия задачи - энергия краевых пикселей равна
	// 1000, откуда
	// получается ситуация, что самые верхний и нижний пиксели могут выбираться
	// из трёх
	// одинаковых по энергии, но различными по реальному RGB цвету.
	public int[] findVerticalSeam() {

		List<Seam> seams = new ArrayList<>();
		Coord[] seam = new Coord[picHeight];

		for (int i = 1, k = 0; i < picWidth - 1; i++) {
			seam[k] = new Coord(i, k, picEnergy[k][i]);
			++k;

			int x = i;
			while (k != picHeight) {
				Coord left = new Coord(x - 1, k, picEnergy[k][x - 1]);
				Coord center = new Coord(x, k, picEnergy[k][x]);
				Coord right = new Coord(x + 1, k, picEnergy[k][x + 1]);

				Coord min = minimumByEnergy(left, center, right,
						k == picHeight - 1 ? true : false);
				seam[k] = min;
				x = min.x;
				++k;
			}

			int[] seam_sh = new int[picHeight];
			double seam_energy = 0;
			for (int j = 0; j < seam_sh.length; j++) {
				seam_sh[j] = seam[j].x;
				seam_energy += seam[j].energy;
			}

			seams.add(new Seam(seam_sh, seam_energy));
			seam = new Coord[picHeight];
			k = 0;
		}
		
		//первая встреченная кривая, с минимальной суммарной энергией
		Seam firstMin = seams.get(0);	
		for (int i = 1; i < seams.size(); i++) {
			if (firstMin.compareTo(seams.get(i)) > 0) {
				firstMin = seams.get(i);
			}
		}

		return firstMin.seam;
	}
	
	//TODO оптимизация: считать seam_sh и seam_energy сразу при поиске кривой
	public int[] findHorizontalSeam() {

		List<Seam> seams = new ArrayList<>();
		Coord[] seam = new Coord[picWidth];

		for (int i = 1, k = 0; i < picHeight - 1; i++) {
			seam[k] = new Coord(k, i, picEnergy[i][k]);
			++k;

			int x = i;
			while (k != picWidth) {
				Coord left = new Coord(k, x - 1, picEnergy[x - 1][k]);
				Coord center = new Coord(k, x, picEnergy[x][k]);
				Coord right = new Coord(k, x + 1, picEnergy[x + 1][k]);

				Coord min = minimumByEnergy(left, center, right,
						k == picWidth - 1 ? true : false);
				seam[k] = min;
				x = min.y;
				++k;
			}

			int[] seam_sh = new int[picWidth];
			double seam_energy = 0;
			for (int j = 0; j < seam_sh.length; j++) {
				seam_sh[j] = seam[j].y;
				seam_energy += seam[j].energy;
			}

			seams.add(new Seam(seam_sh, seam_energy));
			seam = new Coord[picWidth];
			k = 0;
		}

		Seam firstMin = seams.get(0);
		for (int i = 1; i < seams.size(); i++) {
			if (firstMin.compareTo(seams.get(i)) > 0) {
				firstMin = seams.get(i);
			}
		}

		return firstMin.seam;
	}
	
	//Универсальный метод поиск Seam (недоделан)
	private int[] findSeam(boolean isVertical) {
		
		//локально-глобальные переменные, зависящие от того, 
		//какого типа Seam ищется
		int limit = (isVertical ? picHeight : picWidth),
			//-1 берётся от того, что проходим ряд, не включая первый и последний
			//элементы, т.к. у них не будет 3-го члена для сравнения
			for_limit = (isVertical ? picWidth - 1 : picHeight - 1),
			g, h, a, b;

		List<Seam> seams = new ArrayList<>();
		Coord[] seam = new Coord[limit];

		for (int i = 1, k = 0, p = 0; i < for_limit; i++) {
			if (isVertical){ //g и h маскируют i и k далее в цикле
				g = i; h = k; a = 1; b = 0;
			}else{
				g = k; h = i; a = 0; b = 1;
			}
			seam[p++] = new Coord(g, h, picEnergy[h][g]);
			
			if(isVertical) ++h;
			else ++g;
			
			while (p != limit) { //затыка здесь, нужна ещё переменная принимающая
								 //один раз значение, и далее просто инкрементируемая
								 //до нужного предела
				Coord left = new Coord(g - a, h - b, picEnergy[h - b][g - a]);
				Coord center = new Coord(g, h, picEnergy[h][g]);
				Coord right = new Coord(g + a, h + b, picEnergy[h + b][g + a]);

				Coord min = minimumByEnergy(left, center, right,
						p == limit - 1 ? true : false);
				seam[p] = min;
				
				if(isVertical) {g = min.x; ++h;}
				else {h = min.y; ++g;}
				
				++p;
			}
				
			//оптимизация todo: не складировать варианты симов, а сразу сравнивать с той,
			//что уже имеется и выбирать наименьшую.
			int[] seam_sh = new int[limit];
			double seam_energy = 0;
			for (int j = 0; j < seam_sh.length; j++) {
				seam_sh[j] = (isVertical ? seam[j].x : seam[j].y);
				seam_energy += seam[j].energy;
			}

			seams.add(new Seam(seam_sh, seam_energy));
			seam = new Coord[limit];
			p = 0;
		}

		Seam firstMin = seams.get(0);
		for (int i = 1; i < seams.size(); i++) {
			if (firstMin.compareTo(seams.get(i)) > 0) {
				firstMin = seams.get(i);
			}
		}

		return firstMin.seam;
	}

	private Coord minimumByEnergy(Coord left, Coord center, Coord right,
			boolean isTail) {

		Coord min = null;
		if (!isTail) {
			if (left.compareTo(center) > 0)
				min = center;
			else
				min = left;

			if (min.compareTo(right) > 0)
				min = right;
		} else { // корректный выбор хвостового элемента симы. Выбирается самый
				 // тёмный, т.е. сумма R+G+B составляющих наименьшая
				 //UPD: данный метод не верен. Надо переделать так, как 
				 //в методе energy, с поправкой на краевые элементы
			if (left.compareByRGBSum(center) > 0)
				min = center;
			else
				min = left;

			if (min.compareByRGBSum(right) > 0)
				min = right;
		}

		return min;
	}
	
	//TODO оптимизация: попробовать пересчитывать только те участки массива,
	//где происходило удаление кривой
	private void calculateEnergyMatrix() {
		this.picEnergy = new double[picHeight][picWidth];

		for (int i = 0; i < picHeight; i++) {
			for (int j = 0; j < picWidth; j++) {
				picEnergy[i][j] = this.energy(j, i);
			}
		}
	}

	public void removeHorizontalSeam(int[] seam) {
		int k = 0;
		boolean done = false;
		Picture newPic = new Picture(picWidth, picHeight - 1);

		for (int i = 0; i < newPic.width(); i++) {
			for (int j = 0, row = 0; j < newPic.height(); j++) {
				if (j == seam[i] && !done) {
					++row;
					done = true;
					// continue;
				}
//				System.out.printf("%10d ",pic.get(i, row).getRGB());
				newPic.set(i, j, pic.get(i, row++));
			}
			done = false;
//			System.out.println();
		}

		pic = newPic;
		picHeight = newPic.height();
		picWidth = newPic.width();
		this.calculateEnergyMatrix();
	}

	public void removeVerticalSeam(int[] seam) {

		int k = 0;
		boolean done = false;
		Picture newPic = new Picture(picWidth - 1, picHeight);

		for (int i = 0; i < newPic.height(); i++) {
			for (int j = 0, col = 0; j < newPic.width(); j++) {
				if (j == seam[i] && !done) {
					++col;
					done = true;
					// continue;
				}
				// System.out.printf("%10d ",pic.get(col, i).getRGB());
				newPic.set(j, i, pic.get(col++, i));
			}
			done = false;
			// System.out.println();
		}

		pic = newPic;
		picHeight = newPic.height();
		picWidth = newPic.width();
		this.calculateEnergyMatrix();
	}

	// Width для картинки это визуально КОЛОНКИ!!!
	// Height соответственно СТРОКИ!!!!
	public void printPicMatrix() {

		System.out.println("Orig = " + 3 + " Power = " + (3 << 2));

		int[][] out = new int[pic.width()][pic.height()];
		System.out.println("W = " + pic.width() + " H = " + pic.height());

		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				System.out.printf("%10d ", pic.get(j, i).getRGB());
			}
			System.out.println();
		}

		System.out.println();
		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				System.out.printf("%10.2f", energy(j, i));
			}
			System.out.println();
		}

	}

	public void print() {
		System.out.println("\nW = " + pic.width() + " H = " + pic.height());

		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				System.out.printf("%10.2f", picEnergy[i][j]);
			}
			System.out.println();
		}
	}

	public Picture getImage() {
		return pic;
	}

	public static void main(String[] args) {
		long sTime = System.currentTimeMillis(), eTime = 0;
		Picture pic = new Picture(args[0]);
		SeamCarver sc = new SeamCarver(pic);
		int round = 0;
		while (sc.getImage().height() != 364) {
			System.out.println("============ Minus " + (++round) + " pixel from Width =============");
//			sc.printPicMatrix();
//			int[] seam = sc.findVerticalSeam();
			int[] seam = sc.findHorizontalSeam();
//			sc.removeVerticalSeam(seam);
			sc.removeHorizontalSeam(seam);
//			sc.print();
			File f = new File(String.format("C:\\4\\img%dx%d.png", sc
					.getImage().width(), sc.getImage().height()));
			sc.getImage().save(f);
		}
		eTime = System.currentTimeMillis();
		System.out.println("Elapsed: " + ((double) (eTime - sTime) / 1000)
				+ " seconds.");
	}
	
	//Вспомогательные классы, чтобы код не превращался в бурду
	private class Coord implements Comparable<Coord> {
		int x;
		int y;
		double energy;

		public Coord(int x, int y, double energy) {
			this.x = x;
			this.y = y;
			this.energy = energy;
		}

		@Override
		public int compareTo(Coord that) {

			if (this.energy > that.energy)
				return 1;
			if (this.energy < that.energy)
				return -1;

			return 0;
		}

		public int compareByRGBSum(Coord that) {
			int athis = pic.get(x, y).getRed() + pic.get(x, y).getBlue()
					+ pic.get(x, y).getGreen();
			int athat = pic.get(that.x, that.y).getRed()
					+ pic.get(that.x, that.y).getBlue()
					+ pic.get(that.x, that.y).getGreen();

			if (athis > athat)
				return 1;
			if (athis < athat)
				return -1;

			return 0;
		}

		@Override
		public String toString() {
			return String.format("Point(%d,%d), energy = %7.2f", x, y, energy);
		}
	}

	private static class Seam implements Comparable<Seam> {
		int[] seam;
		double energy;

		public Seam(int[] seam, double energy) {
			this.seam = seam;
			this.energy = energy;
		}

		@Override
		public int compareTo(Seam another) {
			if (this.energy > another.energy)
				return 1;
			if (this.energy < another.energy)
				return -1;
			return 0;
		}

	}
}


