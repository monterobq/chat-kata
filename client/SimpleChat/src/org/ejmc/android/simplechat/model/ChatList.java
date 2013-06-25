package org.ejmc.android.simplechat.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ChatList {
	
	private List<Message> messages;
	
	@SerializedName("last_seq")
	private int lastSeq;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getLastSeq() {
		return lastSeq;
	}

	public void setLastSeq(int lastSeq) {
		this.lastSeq = lastSeq;
	}
	
	
	
}
