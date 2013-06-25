package org.ejmc.android.simplechat.model;


public class Message {
	
	private String nick;
	private String message;
	
	public Message(String nick, String message) {
		this.nick = nick;
		this.message = message;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
