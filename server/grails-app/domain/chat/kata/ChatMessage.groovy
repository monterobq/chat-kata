package chat.kata

import com.google.common.base.Objects

class ChatMessage {
	
	String nick
	String message
	
	int hashCode(){
		return Objects.hashCode(nick, message)
	}
	
	boolean equals(obj){
		if(obj == null || !(obj instanceof ChatMessage)){
			return false
		}
		return Objects.equal(this.nick, obj.nick) && Objects.equal(this.message, obj.message)
	}
	
	String toString(){
		return nick+":"+message
	}
	
	
}
