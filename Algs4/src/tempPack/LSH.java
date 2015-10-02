package tempPack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LSH {
	
	static final int MAX_ERROR = 1;
	static final int shingleLength = 3;
	static final int shinglesInRead = 3;
	static final int NUMBER_OF_HASHFUNCTIONS = 10;
	
	static int calcHash(){
		
		return 0;
	}
	
	static Map<Integer, List<PRS>> shinglzation(List<PRS> seq){
		
		Map<Integer, List<PRS>> mapOfDuples = new HashMap<>();
		
		
		for (PRS prs : seq) {
			for(int i = 0; i < shinglesInRead; i++){
				
				calcOnShingle(mapOfDuples, prs, prs.read1, i);
				calcOnShingle(mapOfDuples, prs, prs.read2, i);
				
			}
		}
		
		return mapOfDuples;
	}
	
	
	
	private static void calcOnShingle(Map<Integer, List<PRS>> mapOfDuples, PRS prs, char[] read, int pos) {
		
		int shingleHash = hash(read, pos*shingleLength, (pos + 1)*shingleLength);
		
		List<PRS> dupleOnShingle = mapOfDuples.get(shingleHash);
		if (dupleOnShingle == null){
			dupleOnShingle = new ArrayList<>();
			dupleOnShingle.add(prs);
			mapOfDuples.put(shingleHash, dupleOnShingle);
		}
		else{
			dupleOnShingle.add(prs);
		}
		
		
		
		
		
	}

	private static int hash(char[] read, int strt, int end) {
		
		if (read == null) return 0;
		
		int result = 1;
		for(int i = strt; i < end; i++){
			
			result = 31 * result + (int)read[i];
		}
		
		
		return result;
	}
	
	private static int hash(char[] input){
		
		int res = 1;
		for (char c : input) {
			res = 31 * res + c;
		}
		return res;
	}

	public static void main(String[] args) {
		
//		int[][] hash = getValueMatrix(20);
//		System.out.println();
//		System.out.println();
		
		
//		System.out.println(hash(new char[] {'A','A','A'}));
//		System.out.println(hash(new char[] {'A','A','B'}));
//		System.out.println(hash(new char[] {'A','B','A'}));
//		System.out.println(hash(new char[] {'B','A','A'}));
//		System.out.println(hash(new char[] {'A','B','B'}));
//		System.out.println(hash(new char[] {'B','B','A'}));
//		System.out.println(hash(new char[] {'B','B','B'}));
//		
//		System.out.println(hash(new char[] {'A','A','A','A','A'}));
//		System.out.println(hash(new char[] {'G','A','A','A','A'}));
//		System.out.println(hash(new char[] {'A','G','A','G','G'}));
		
		
		List<PRS> seq = new ArrayList<PRS>();
		
		seq.add(new PRS(new char[] {'A','A','A','A','A','A','A','A','A'}, new char[] {'A','B','C','C','A','B','A','B','C'}));
		seq.add(new PRS(new char[] {'A','A','A','A','B','A','A','A','A'}, new char[] {'B','B','B','B','B','B','B','B','A'}));
		seq.add(new PRS(new char[] {'A','C','A','A','C','C','C','A','A'}, new char[] {'A','B','A','B','A','B','A','B','A'}));
		seq.add(new PRS(new char[] {'A','A','A','B','A','A','A','A','A'}, new char[] {'A','B','C','C','A','B','A','B','A'}));
		
		
		
		Map<Integer, List<PRS>> mapOfDuples = shinglzation(seq);
		Object[] keys = mapOfDuples.keySet().toArray();
		for (Object key : keys) {
			if(mapOfDuples.get(key).size() == 1){
				mapOfDuples.remove(key);
			}
		}
		
		
		int numberOfShingles = mapOfDuples.size();
		fillMatrixOfHash(seq, mapOfDuples, numberOfShingles);
		
		
		
		
		
	}

	private static void fillMatrixOfHash(List<PRS> seq, Map<Integer, List<PRS>> mapOfDuples, int numberOfShingles) {
		
		int[][] matrixOfValues = getValueMatrix(numberOfShingles);
		
		for (PRS prs : seq) {
			prs.hashFunc = new int[NUMBER_OF_HASHFUNCTIONS];
			Arrays.fill(prs.hashFunc, numberOfShingles + 1);
		}
		
		int count = 0;
		
		for (List<PRS> entry : mapOfDuples.values()) {
			for (PRS prs : entry) {
				for (int i = 0; i < prs.hashFunc.length; i++) {
					if (prs.hashFunc[i] > matrixOfValues[i][count]){
						prs.hashFunc[i] = matrixOfValues[i][count];
					}
				}
			}
			++count;
		}
		
		System.out.println();
	}

	private static int[][] getValueMatrix(int numberOfShingles) {
		

		int[][] matrix = new int[NUMBER_OF_HASHFUNCTIONS][numberOfShingles];
		
		for (int i = 0; i < NUMBER_OF_HASHFUNCTIONS; i++) {
			for (int j = 0; j < numberOfShingles; j++) {
				matrix[i][j] = ((j << i) + 1) % numberOfShingles;
			}
		}
		
		for (int[] is : matrix) {
			for (int i : is) {
				System.out.printf("%3d", i);
			}
			System.out.println();
		}

		return matrix;
	}
}
