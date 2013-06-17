package org.ejmc.android.simplechat.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * List off chat messages..
 * 
 * @author startic
 *
 */
public class ChatList {

	@SerializedName("messages")
	private List<Message> messages ;
	
	@SerializedName("last_seq")
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
