package concurrent_chat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

//Actor Concept

//last started 140703_06
public class ChatServer implements Runnable {

	List<ChatMember> members = new LinkedList<>();
	LinkedBlockingQueue<Runnable> actions = new LinkedBlockingQueue<>();
	ExecutorService sender = Executors.newCachedThreadPool();
	
	
	public void addMember(final ChatMember member) {
		actions.add(new Runnable() {
			public void run() {
				member.setServer(ChatServer.this);
				broadcast(member.getName() + " entered");
				members.add(member);
			}
		});
	}

	public void removeMember(final ChatMember member) {
		actions.add(new Runnable() {
			public void run() {
				members.remove(member);
				broadcast(member.getName() + " has left chat");
			}
		});
	}

	public void broadcast(final String message) {
		for (final ChatMember chatMember : members) {
			sender.execute(new Runnable() {
				@Override
				public void run() {
					chatMember.sendMessage(message);
				}
			});
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Runnable action = actions.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void process(final String message) {
		actions.add(new Runnable() {
			@Override
			public void run() {
				broadcast(message);
			}
		});
	}
}
