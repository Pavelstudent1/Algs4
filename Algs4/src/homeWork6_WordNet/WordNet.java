package homeWork6_WordNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;

public class WordNet {

	private Digraph dg;
	private int dgLength = 0;
	private SAP sap;

	private Map<Integer, Integer[]> verticesOfHyp = new HashMap<>();
	private Map<Integer, String[]> synsets = new HashMap<>();
	private Map<String, ArrayList<Integer>> all_nouns = new HashMap<>();

	public WordNet(String synset, String hypernyms) {
		if (synset == null || hypernyms == null)
			throw new NullPointerException();
		collectSynsets(synset);
		collectHypernyms(hypernyms);

		this.dg = new Digraph(dgLength);

		for (Map.Entry<Integer, Integer[]> vert : verticesOfHyp.entrySet()) {
			for (Integer el : vert.getValue()) {
				this.dg.addEdge(vert.getKey(), el);
			}
		}

		// Проверим получившийся граф на его основные свойства:
		// - наличие корня, т.е. существует вершина, не имеющая исходящих связей
		//	 (т.к. в данном графе все связи направлены вверх)
		// - ацикличность, т.е. отсутствие циклов

		try {
			int root = 0;
			for (int i = 0; i < dg.V(); i++) {
				if (!dg.adj(i).iterator().hasNext())
					++root;
			}
			if (root > 1) {
				throw new Exception("Wordnet digraph isn't rooted DAG!");
			} else {
				System.out.println("Rooted verification is successful!");
			}

			DirectedCycle dc = new DirectedCycle(dg);
			if (dc.hasCycle()) {

				throw new Exception("Wordnet digraph has a cycle!");
			} else {
				System.out.println("Acyclic verification is successful!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		this.sap = new SAP(dg);
		
	}

	private void collectSynsets(String synset) {

		In in = new In(synset);
		String line = null;
		String[] splittedline = null;
		String[] synonyms = null;
		int synonymsID = 0;
		ArrayList<Integer> idConnectedToNoun = null;

		while (!in.isEmpty()) {
			line = in.readLine();
			splittedline = line.split(",");
			synonymsID = Integer.parseInt(splittedline[0]);
			synonyms = splittedline[1].split(" ");

			for (String s : synonyms) {

				idConnectedToNoun = all_nouns.get(s);
				if (idConnectedToNoun != null) {
					// System.out.println("Match noun: " + s);
					idConnectedToNoun.add(synonymsID);
				} else {
					idConnectedToNoun = new ArrayList<>();
					idConnectedToNoun.add(synonymsID);
					all_nouns.put(s, idConnectedToNoun);
				}
			}

			this.synsets.put(synonymsID, synonyms);

			++this.dgLength;
		}

	}

	private void collectHypernyms(String hypernyms) {

		In in = new In(hypernyms);
		String line = null;
		String[] splittedline = null;
		Integer[] arrayOfHyps;

		while (!in.isEmpty()) {

			line = in.readLine();
			splittedline = line.split(",");
			arrayOfHyps = new Integer[splittedline.length - 1];

			for (int i = 0; i < arrayOfHyps.length; i++) {
				arrayOfHyps[i] = Integer.valueOf(splittedline[i + 1]);
			}

			verticesOfHyp.put(Integer.valueOf(splittedline[0]), arrayOfHyps);

		}

	}

	public Iterable<String> nouns() {
		return this.all_nouns.keySet();
	}

	public boolean isNoun(String word) {
		if (word == null)
			throw new NullPointerException();

		return this.all_nouns.containsKey(word);
	}

	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException("One or both arguments isn't valid!");
		
		
		return this.sap.length(all_nouns.get(nounA), all_nouns.get(nounB));
	}

	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException("One or both arguments isn't valid!");
		
		int minAncestor = this.distance(nounA, nounB);
		StringBuilder sb = new StringBuilder();
		for (String s : synsets.get(minAncestor)) {
			sb.append(s + " ");
		}
		
		return sb.toString();
	}

	public static void main(String[] args) {

		WordNet wn = new WordNet(args[0], args[1]);
		System.out.println(wn.distance("Rameses_the_Great", "Henry_Valentine_Miller"));
		System.out.println(wn.sap("Rameses_the_Great", "Henry_Valentine_Miller"));
	}

}
