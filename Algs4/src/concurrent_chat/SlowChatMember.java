package concurrent_chat;

import concurrent1.Utils;

public class SlowChatMember extends ChatMember{

	public SlowChatMember(String name) {
		super(name);
	}
	
	@Override
	public void sendMessage(String message) {
		Utils.pause(15000);
		super.sendMessage(message);
	}

}
