package tempPack;

import java.util.Arrays;
import java.util.Comparator;

public class RecordSorting {
	
	public static void main(String[] args) {
		
		Record[] rec = new Record[RecordGenerator.MAX];
		RecordGenerator rg = new RecordGenerator();
		RecordDefComparator defcomp = 
				new RecordDefComparator();
		
		for (int i = 0; i < rec.length; i++) {
			rec[i] = (Record) rg.iterator().next();
		}
		
		long start = System.nanoTime();
		Arrays.sort(rec, 0, rec.length, defcomp);
		long stop = System.nanoTime();
		System.out.println("END = " + (double)(stop - start) / 1000000000 + " sec");
	}
	
	
	static class RecordDefComparator implements Comparator<Record>{
		
		final int BASES = 5;
		@Override
		public int compare(Record r1, Record r2) {

			for (int i = 0; i < BASES; i++) {
				int ret = r1.read1[i] - r2.read1[i];
				if ( ret != 0) return ret; 
			}
			
			for (int i = 0; i < BASES; i++) {
				int ret = r1.read2[i] - r2.read2[i];
				if ( ret != 0) return ret; 
			}
			
			return 0;
		}
		
	}
}
