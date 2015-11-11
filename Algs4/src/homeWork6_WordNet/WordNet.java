package homeWork6_WordNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
	
	private Digraph DG;
	private int dgLength = 0;
	
	private Map<Integer, Integer[]> verticeWithHyp = new HashMap<>();
	private Map<Integer, String[]> synsets = new HashMap<>();
	private Map<String, ArrayList<Integer>> all_nouns = new HashMap<>();
	
	
	public WordNet(String synset, String hypernyms) {
		if (synset == null || hypernyms == null) throw new NullPointerException();
		collectSynsets(synset);
		collectHypernyms(hypernyms);
		
		this.DG = new Digraph(dgLength);
		
		for (Map.Entry<Integer, Integer[]> vert : verticeWithHyp.entrySet()) {
			for (Integer el : vert.getValue()) {
				this.DG.addEdge(vert.getKey(), el);
			}
		}
		//https://github.com/destructivecreator/coursera/blob/master/wordnet/src/WordNet.java
		
		System.out.println("Done!");
		
		
	}
	
	private void collectSynsets(String synset) {
		
		In in = new In(synset);
		String line = null;
		String[] splittedline = null;
		String[] synonyms = null;
		int synonymsID = 0;
		ArrayList<Integer> idConnectedToNoun = null;
		
		
		while(!in.isEmpty()) {
			line = in.readLine();
			splittedline = line.split(",");
			synonymsID = Integer.parseInt(splittedline[0]);
			synonyms = splittedline[1].split(" ");
			
			for (String s : synonyms) {
				
				idConnectedToNoun = all_nouns.get(s);
				if (idConnectedToNoun != null){
					System.out.println("Match noun: " + s);
					idConnectedToNoun.add(synonymsID);
				}else{
					idConnectedToNoun = new ArrayList<>();
					idConnectedToNoun.add(synonymsID);
					all_nouns.put(s, idConnectedToNoun);
				}
			}
			
			this.synsets.put(synonymsID, synonyms);
			

			++this.dgLength;
		}
		
		System.out.println("Done!");
		
	}

	private void collectHypernyms(String hypernyms) {

		In in = new In(hypernyms);
		String line = null;
		String[] splittedline = null;
		Integer[] arrayOfHyps;
		
		while(!in.isEmpty()){
			
			line = in.readLine();
			splittedline = line.split(",");
			arrayOfHyps = new Integer[splittedline.length - 1];
			
			for (int i = 0; i < arrayOfHyps.length; i++) {
				arrayOfHyps[i] = Integer.valueOf(splittedline[i + 1]);
			}
			
			verticeWithHyp.put(Integer.valueOf(splittedline[0]), arrayOfHyps);
			

		}
		
		System.out.println("Done!");
		
	}

	public Iterable<String> nouns(){
		return null;
	}
	
	public boolean isNoun(String word){
		if (word == null) throw new NullPointerException();
		
		return false;
	}
	
	public int distance(String nounA, String nounB){
		if (nounA == null || nounB == null) throw new NullPointerException();
		if (isNoun(nounA) && isNoun(nounB)) throw new IllegalArgumentException();
		
		return 0;
	}
	
	public String sap(String nounA, String nounB){
		if (nounA == null || nounB == null) throw new NullPointerException();
		
		return null;
	}
	
	public static void main(String[] args) {
		
		WordNet wn = new WordNet(args[0], args[1]);
	}
	
}
