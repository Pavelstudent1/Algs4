package homeWork2_Rand_Queue_Deque;

public class Subset {
	
	public static void main(String[] args) {
		
		randomizedQueueList<String> rdeq = new randomizedQueueList<>();
		int count = 0;
		int lines = 0;
		while(!args[count].equals("|")) {
			System.out.print(args[count] + " ");
			rdeq.enqueue(args[count]);
			count++;
		}
		lines = Integer.valueOf(args[count + 1]);
		System.out.print("| output lines = " + lines + "\n");
		for (int i = 0; i < lines; i++) {
			System.out.println(rdeq.sample());
		}
	}
}
