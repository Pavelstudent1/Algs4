package concurrent_chat;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import concurrent1.Utils;

public class Chat {
	
	static final String[] phrases = {"hi", "bye", "yo", "ok", "yes", "no", "oo"};
	
	static class MemberImitator implements Runnable {

		private ChatMember _member;
		
		public MemberImitator(ChatMember member) {
			_member = member;
		}
		
		@Override
		public void run() {
			Random random = new Random();
			while(true){
				Utils.pause(3000 + random.nextInt(5000));
				String message = phrases[random.nextInt(phrases.length)];
				_member.received(message);
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		ChatServer server = new ChatServer();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(server);

		ChatMember m1 = new ChatMember("Pete");
		ChatMember m2 = new SlowChatMember("Ann");
		ChatMember m3 = new ChatMember("John");
		
		server.addMember(m1);
		Utils.pause(3000);
		server.addMember(m2);
		Utils.pause(3000);
		server.addMember(m3);
		Utils.pause(3000);
		
		
		
		service.execute(new MemberImitator(m1));
		service.execute(new MemberImitator(m2));
		service.execute(new MemberImitator(m3));

//		Utils.pause(3000);
//		server.removeMember(m1);
		
		
	}
}
