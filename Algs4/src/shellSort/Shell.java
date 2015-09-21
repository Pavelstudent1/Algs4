package shellSort;

import java.util.Random;
import java.util.Scanner;

public class Shell {
	
	public static final int SIZE = 10000000;

	private Shell() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
        int h = 1;
        while (h < N/3) h = 3*h + 1; 

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }
            assert isHsorted(a, h); 
            h /= 3;
        }
        assert isSorted(a);
    }



   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array h-sorted?
    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++)
            if (less(a[i], a[i-h])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
        	System.out.print(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; Shellsorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);
    	long start = 0, stop = 0;
    	
//        String[] a = (scanner.nextLine()).split("");
    	String[] a = generateStringLine(SIZE);
        
        start = System.nanoTime();
        Shell.sort(a);
        stop = System.nanoTime();
//        show(a);
        System.out.println("\nTime = " + ((float)(stop - start) / 1000000000) + " seconds");
        scanner.close();
    }

	public static String[] generateStringLine(int size) {
		
		String dict = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			sb.append(dict.charAt(rand.nextInt(26)));
		}
		
		return sb.toString().split("");
	}

	
}
