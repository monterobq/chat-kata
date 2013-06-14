package org.ejmc.android.simplechat.model;

import java.util.List;

public class ChatList {

	
	private List<Message> messages ;
	
	private int lastSeq ;

	public int getLastSeq() {
		return lastSeq;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setLastSeq(int lastSeq) {
		this.lastSeq = lastSeq;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
}
