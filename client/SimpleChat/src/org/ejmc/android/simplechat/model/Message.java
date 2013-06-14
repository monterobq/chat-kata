package org.ejmc.android.simplechat.model;

public class Message {
	
	private String nick ;

	private String message ;

	public Message() {
		super();
	}
	
	public Message(String nick, String message) {
		super();
		this.nick = nick;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getNick() {
		return nick;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
}
