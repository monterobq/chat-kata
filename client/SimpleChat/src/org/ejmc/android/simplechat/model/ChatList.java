package org.ejmc.android.simplechat.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ChatList {
	
	private List<Message> messages;
	
	@SerializedName("next_seq")
	private int nextSeq;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getNextSeq() {
		return nextSeq;
	}

	public void setNextSeq(int nextSeq) {
		this.nextSeq = nextSeq;
	}
}
