package concurrent_chat;

public class ChatMember {

	private String _name;
	private ChatServer _chatServer;
	
	public ChatMember(String name) {
		_name = name;
	}
	
	public void sendMessage(String message) {
		System.out.println(_name + " <- " + message);
		
	}

	public String getName() {
		return _name;
	}

	public void received(String message) {
		_chatServer.process(_name + " > " + message);
	}

	public void setServer(ChatServer chatServer) {
		_chatServer = chatServer;
	}

}
