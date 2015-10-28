package tempPack;

import java.util.Arrays;
import java.util.Comparator;

public class RecordSorting {
	
	static final int VALUE_OF_TESTS = 10;

	public static void main(String[] args) {
		
		long avg = 0;
		RecordGenerator rg = new RecordGenerator();
		RecordDefComparator defcomp = new RecordDefComparator();
		for (int t = 0; t < VALUE_OF_TESTS; t++) {
			Record[] rec = new Record[3000000];
			
			for (int i = 0; i < rec.length; i++) {
				rec[i] = (Record) rg.iterator().next();
			}
			
			long start = System.nanoTime();
			Arrays.sort(rec, 0, rec.length, defcomp);
			//lsdSort(rec);
			//TimSort.sort(rec, defcomp);
			long stop = System.nanoTime() - start;
			System.out.println("Test#" + (t + 1) + " = " + (double)stop / 1000000000 + " sec");
			avg += stop ;
			//printFirstTen(rec);
		}
		System.out.println("Average time for " + VALUE_OF_TESTS + 
							" tests is " + ((double)avg / 1000000000) / VALUE_OF_TESTS + " sec");
	}
	
	
	static void printFirstTen(Record[] r){
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print((char)r[i].read1[j]);
			}
			System.out.println();
		}
	}
	
	
	
	static class RecordDefComparator implements Comparator<Record> {

		final int BASES = 5;		

		@Override
		public int compare(Record r1, Record r2) {
			

			for (int i = 0; i < BASES; i++) {
				int ret = r1.read1[i] - r2.read1[i];
				if (ret != 0)
					return ret;
			}

			for (int i = 0; i < BASES; i++) {
				int ret = r1.read2[i] - r2.read2[i];
				if (ret != 0)
					return ret;
			}

			return 0;
		}

	}

	static void lsdSort(Record[] array) {
		final int FIRST_N_BYTES = 5;

		int[] count_array = new int[20];
		int readLength = array[0].read1.length - 1;
		Record[] fin_array = new Record[array.length];

		for (int i = readLength - 1; i >= 0; i--) {
			if (i == readLength - FIRST_N_BYTES) i = FIRST_N_BYTES - 1;
			
			for (int j = 0; j < array.length; j++) {
				if (i > 100){
					++count_array[array[j].read2[i] % 65];
				}
				else {
					++count_array[array[j].read1[i] % 65];
				}
			}

			for (int j = 0; j < count_array.length - 1; j++) {
				count_array[j + 1] += count_array[j];
			}

			for (int j = array.length - 1; j >= 0; j--) {
				if (i > 100) {
					fin_array[--count_array[(array[j].read2[i] % 65)]] = array[j];
				}else{
					fin_array[--count_array[(array[j].read1[i] % 65)]] = array[j];
				}
				
			}

			//array = fin_array;
			count_array = new int[20];
			//fin_array = new Record[array.length];
			for (int j = 0; j < array.length; j++) {
				array[j] = fin_array[j];
				fin_array[j] = null;
			}
		}
		
//		for (Record r : array) {
//			for (int i = 0; i < 10; i++) {
//				System.out.printf("%s", (char)r.read1[i]);
//			}
//			System.out.println();
//		}

	}
//	static void lsdSort(Record[] array) {
//		final int FIRST_N_BYTES = 5;
//		
//		int[] count_array = new int[20];
//		Record[] fin_array = new Record[array.length];
//		
//		for (int i = FIRST_N_BYTES - 1; i >= 0; i--) {
//			
//			
//			for (int j = 0; j < array.length; j++) {
//				++count_array[array[j].read1[i] % 65];
//			}
//			
//			for (int j = 0; j < count_array.length - 1; j++) {
//				count_array[j + 1] += count_array[j];
//			}
//			
//			for (int j = array.length - 1; j >= 0; j--) {
//				fin_array[--count_array[(array[j].read1[i] % 65)]] = array[j];
//			}
//			
//			//array = fin_array;
//			count_array = new int[20];
//			//fin_array = new Record[array.length];
//			for (int j = 0; j < array.length; j++) {
//				array[j] = fin_array[j];
//				fin_array[j] = null;
//			}
//		}
//		
////		for (Record r : array) {
////			for (int i = 0; i < 10; i++) {
////				System.out.printf("%s", (char)r.read1[i]);
////			}
////			System.out.println();
////		}
//		
//	}
	
//	static void lsdSort(Record[] array) {
//		final int BASES = 5;
//
//		int[] count_array = new int[20];
//		Record[] fin_array = new Record[array.length];
//
//		for (int i = array[0].read1.length - 1; i >= 0; i--) {
//
//			for (Record r : array) {
//				++count_array[r.read1[i] % 65];
//			}
//
//			for (int j = 0; j < count_array.length - 1; j++) {
//				count_array[j + 1] += count_array[j];
//			}
//
//			for (int j = array.length - 1; j >= 0; j--) {
//				fin_array[--count_array[(array[j].read1[i] % 65)]] = array[j];
//			}
//
//			array = fin_array;
//			Arrays.fill(count_array, 0);
//			fin_array = new Record[array.length];
//		}
//		
//		for (Record r : array) {
//			for (byte b : r.read1) {
//				System.out.printf("%s", (char)b);
//			}
//			System.out.println();
//		}
//
//	}
}
