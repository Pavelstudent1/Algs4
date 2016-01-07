package homeWork8_Baseball_Elimination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	
	private int numberOfTeam;
	private Map<String, Integer> teams;
	private Map<String, List<String>> elimCert;
	private int[] wins;
	private int[] loses;
	private int[] rem;
	private int games[][];
	
	public BaseballElimination(String filename) {
		
		In in = new In(filename);
		numberOfTeam = in.readInt();
		teams = new HashMap<>();
		elimCert = new HashMap<>();
		wins = new int[numberOfTeam];
		loses = new int[numberOfTeam];
		rem = new int[numberOfTeam];
		games = new int[numberOfTeam][numberOfTeam];
		
		for(int i = 0; !in.isEmpty(); i++){
			teams.put(in.readString(), i);
			wins[i] = in.readInt();
			loses[i] = in.readInt();
			rem[i] = in.readInt();
			
			for(int j = 0; j < numberOfTeam; j++){
				games[i][j] = in.readInt();
			}
			
		}
	
	}
	
	public int numberOfTeams(){
		return numberOfTeam;
	}
	
	public Iterable<String> teams(){
		return teams.keySet();
	}
	
	public int wins(String team){
		if (!teams.containsKey(team)) throw new IllegalArgumentException();
		return wins[teams.get(team)];
	}
	
	public int losses(String team){
		if (!teams.containsKey(team)) throw new IllegalArgumentException();
		return loses[teams.get(team)];
	}
	
	public int remaining(String team){
		if (!teams.containsKey(team)) throw new IllegalArgumentException();
		return rem[teams.get(team)];
	}
	
	public int against(String team1, String team2){
		if (!teams.containsKey(team1) || !teams.containsKey(team2)) 
			throw new IllegalArgumentException();
		return games[teams.get(team1)][teams.get(team2)];
	}
	
	public boolean isEliminated(String team){
		if (!teams.containsKey(team)) throw new IllegalArgumentException();
		
		//Trivial case
		int x = teams.get(team);
		for(int i = 0; i < numberOfTeam; i++){
			if (wins[x] + rem[x] - wins[i] < 0){
				addToElimCertTrivial(team);
				return true;
			}
		}
		
		//Non-trivial case
		final int possibleCombos = numberOfTeam * (numberOfTeam - 1) / 2;
		
		FlowNetwork fnet = new FlowNetwork(possibleCombos + numberOfTeam + 2);
		int s = possibleCombos + numberOfTeam,	//индексы вершин s и t в графе, т.е. последние две
			t = s + 1;
		
		
		/**Данный цикл отражает построение графа, указанного в задании:
		 1) Соединяем s с возможными сочетаниями(matchNum) матчей команд, где сочетания [0,1,...]
		 2) Соединяем matchNum с нужными командами
		 3) Соединяем команды c t
		
		Индексы внутреннего массива вершин FlowNetwork идут так:
		possibleCombos -> numberOfTeams -> s -> t
		 **/
		for (int i = 0, matchVertice = 0; i < numberOfTeam; i++) {
			
			for (int j = i + 1; j < numberOfTeam; j++) {
				
				if (i == j) continue;
				fnet.addEdge(new FlowEdge(s, matchVertice, games[i][j])); //соединяем s с возможными матчами
				fnet.addEdge(new FlowEdge(matchVertice, possibleCombos + i, Double.MAX_VALUE));
				fnet.addEdge(new FlowEdge(matchVertice, possibleCombos + j, Double.MAX_VALUE));
				
				++matchVertice;
			}
			
			fnet.addEdge(new FlowEdge(possibleCombos + i, t, wins[x] + rem[x] - wins[i]));
		}
		
		FordFulkerson ff = new FordFulkerson(fnet, s, t);
		
		for(FlowEdge edge : fnet.adj(s)){
			if (edge.flow() != edge.capacity()){
				addToElimCertNonTrivial(fnet, ff, team);
				return true;
			}
		}
		
		return false;
	}
	
	private void addToElimCertTrivial(String team) {

		List<String> subset = new ArrayList<String>();
		int teamNum = teams.get(team);
		
		for(String other : teams()){
			if (other.equals(team)) continue;
			int otherNum = teams.get(other);
			if(wins[teamNum] + rem[teamNum] < wins[otherNum]){
				subset.add(other);
			}
		}
		
		elimCert.put(team, subset);
	}

	private void addToElimCertNonTrivial(FlowNetwork fnet, FordFulkerson ff, String team) {
		
		List<String> subset = new ArrayList<String>();
		int possGames = numberOfTeam * (numberOfTeam - 1) / 2;
		
		for(String t : teams()){
			int teamNum = teams.get(t);
			int teamVert = possGames + teamNum;
			
			if (ff.inCut(teamVert)){
				subset.add(t);
			}
		}
		
		elimCert.put(team, subset);
	}

	public Iterable<String> certificateOfElimination(String team){
		if (!teams.containsKey(team)) throw new IllegalArgumentException();
		
		return elimCert.get(team);
	}
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
