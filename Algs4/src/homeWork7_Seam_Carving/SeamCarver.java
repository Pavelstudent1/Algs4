package homeWork7_Seam_Carving;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	
	Picture pic;
	int picWidth;
	int picHeight;
	
	public SeamCarver(Picture picture) {
		if (picture == null) throw new NullPointerException("Empty constructor's argument!");
		this.pic = picture;
		this.picWidth = picture.width();
		this.picHeight = picture.height();
	}	
	
	public Picture picture(){
		return pic;
	}
	
	public int width(){
		return picWidth;
	}
	
	public int height(){ 
		return picHeight; 
	}
	
	//Calculate dual-gradient energy of pixel
	public double energy(int x, int y){
		if (x < 0 || y < 0 || x > picWidth - 1 || y > picHeight) 
			throw new IndexOutOfBoundsException();
		
		if (x == 0 || x == picWidth - 1) return 1000;
		if (y == 0 || y == picHeight - 1) return 1000;
		
		int Rx = pic.get(x + 1, y).getRed() - pic.get(x - 1, y).getRed(),
			Gx = pic.get(x + 1, y).getGreen() - pic.get(x - 1, y).getGreen(),
			Bx = pic.get(x + 1, y).getBlue() - pic.get(x - 1, y).getBlue();
		double deltaX = Math.pow(Rx, 2) + Math.pow(Gx, 2) + Math.pow(Bx, 2);
		
		
		int Ry = pic.get(x, y + 1).getRed() - pic.get(x, y - 1).getRed(),
			Gy = pic.get(x, y + 1).getGreen() - pic.get(x, y - 1).getGreen(),
			By = pic.get(x, y + 1).getBlue() - pic.get(x, y - 1).getBlue();
		double deltaY = Math.pow(Ry, 2) + Math.pow(Gy, 2) + Math.pow(By, 2);
		
		return Math.sqrt(deltaX + deltaY);
	}
	
	public int[] findHorizontalSeam(){
		return null;
	}
	
	public int[] findVerticalSeam(){
		return null;
	}
	
	public void removeHorizontalSeam(int[] seam){
		
	}
	
	public void removeVerticalSeam(int[] seam){
		
	}
	
	//Width для картинки это визуально КОЛОНКИ!!!
	//Height соответственно СТРОКИ!!!!
	public void printPicMatrix(Picture pic){
		
		System.out.println("Orig = " + 3 + " Power = " + (3 << 2));
		
		int[][] out = new int[pic.width()][pic.height()];
		System.out.println("W = " + pic.width() + " H = " + pic.height());	
	
		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				System.out.print(pic.get(j, i) + " ");
			}
			System.out.println();
		}
		
		System.out.println("\n");
		for (int i = 0; i < pic.height(); i++) {
			for (int j = 0; j < pic.width(); j++) {
				System.out.printf("%10.3f", energy(j, i));
			}
			System.out.println();
		}
		
		
	}
	
	public static void main(String[] args) {
		Picture pic = new Picture(args[0]);
		SeamCarver sc = new SeamCarver(pic);
		sc.printPicMatrix(pic);
		System.out.println(String.format("\nEnergy of (%d,%d) is %.3f", 1, 2, sc.energy(1, 2)));
	}
}
