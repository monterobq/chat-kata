package chat.kata

class ChatMessage {
	
	String nick
	String message

	int hash(){
		return Objects.hash(nick, message)
	}
	
	boolean equals(obj){
		if(obj == null || !(obj instanceof ChatMessage)){
			return false
		}
		return Objects.equals(this.nick, obj.nick) && Objects.equals(this.message, obj.message)
	}
	
	String toString(){
		return nick+":"+message
	}
	
	
}
