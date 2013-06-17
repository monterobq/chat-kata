package org.ejmc.android.simplechat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Simple message.
 * 
 * @author startic
 * 
 */
public class Message {

	@SerializedName("nick")
	private String nick;

	@SerializedName("message")
	private String message;

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
