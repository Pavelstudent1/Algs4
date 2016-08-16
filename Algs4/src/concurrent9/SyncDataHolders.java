package concurrent9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SyncDataHolders {

	public static void main(String[] args) {

		// Java 1.0
		Vector vector = new Vector<>(); // sync dynamic array
		Hashtable ht = new Hashtable<>();

		// Java 1.2
		// Not threadsafe
		List<String> list = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		// Threadsafe
		List<String> syncList = Collections.synchronizedList(list);
		Map<String, String> syncMap = Collections.synchronizedMap(map);

		//Java 1.5: Threadsafe, Efficient
		List<String> concurrentList = new CopyOnWriteArrayList<>();
		Queue<String> queue = new ConcurrentLinkedQueue<>();
		ConcurrentMap<String, String> cmap = new ConcurrentHashMap<>();
		
		
		
		
	}

}
