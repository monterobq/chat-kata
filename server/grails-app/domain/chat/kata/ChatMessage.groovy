package chat.kata

class ChatMessage {
	
	String nick
	String message

	
	String toString(){
		return nick+":"+message
	}
}
