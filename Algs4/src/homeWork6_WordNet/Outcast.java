package homeWork6_WordNet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	
	private WordNet wn;
	
	public Outcast(WordNet wordnet) {
		wn = wordnet;
	}
	
	public String outcast(String[] nouns){
		
		String outcast = null;
		int max = 0;
		
		for (String s1 : nouns) {
			int current_dist = 0;
			for (String s2 : nouns) {
				if (s1 != s2){
					current_dist += wn.distance(s1, s2);
				}
				
			}
			
			if (current_dist > max){
				max = current_dist;
				outcast = s1;
			}
		}
		
		return outcast;
	}
	
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
	
}
