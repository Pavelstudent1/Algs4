package homeWork6_WordNet;

public class WordNet {
	
	public WordNet(String synset, String hypernyms) {
		if (synset == null || hypernyms == null) throw new NullPointerException();
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
		
	}
	
}
