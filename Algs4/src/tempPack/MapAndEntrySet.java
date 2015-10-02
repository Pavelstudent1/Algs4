package tempPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapAndEntrySet {
	
	public static void main(String[] args) {
		
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Odin");
		map.put(2, "Dva");
		map.put(3, "Tri");
		map.put(4, "Chetire");
		
		
		for (Entry<Integer, String> Map : map.entrySet()) {
			System.out.print(Map.getKey() + " ---- " + Map.getValue());
			if (Map.getValue().contains("Chetire")){
				System.out.println(" --> here you are!");
			}else System.out.println(" --> not here!");
		}
		
		System.out.println();
		System.out.println();
		
		
		Map<Integer, List<String>> map2 = new HashMap<>();
		List<String> list1 = new ArrayList<>();
		list1.add("One");
		list1.add("Two");
		list1.add("Three");
		list1.add("Two");
		
		List<String> list2 = new ArrayList<>();
		list2.add("Four");
		list2.add("Five");
		list2.add("Six");
		
		map2.put(1, list1);
		map2.put(2, list2);
		
		
		
		for (Entry<Integer, List<String>> Map : map2.entrySet()) {
			System.out.print(Map.getKey() + " ---- " + Map.getValue());
			if (Map.getValue().contains("Three")){
				System.out.println(" --> here you are!");
			}else System.out.println(" --> not here!");
		}
		
		
	}
	
}
