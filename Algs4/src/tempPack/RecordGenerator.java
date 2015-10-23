package tempPack;

import java.util.Iterator;
import java.util.Random;

public class RecordGenerator implements Iterable<Record> {
	
	public static final int MAX = 1000000;
	public static final int READ_SIZE = 150;
	private static final Random random = new Random();
	
	
	
	private static final char[] LETTERS = {'A','C','G','T'};

	@Override
	public Iterator iterator() {
		return new Iterator<Record>() {
			private int count;

			@Override
			public boolean hasNext() {
				return count < MAX;
			}

			@Override
			public Record next() {
				count++;
				byte[] data1 = new byte[READ_SIZE];
				byte[] data2 = new byte[READ_SIZE];
				for (int i = 0; i < data1.length; i++) {
					data1[i] = (byte) LETTERS[random.nextInt(LETTERS.length)];
					data2[i] = (byte) LETTERS[random.nextInt(LETTERS.length)]; 
				}
				return new Record(data1, data2);
			}

			@Override
			public void remove() {
			}
		};
	}

}
