package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {


	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 */
	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {

	
	}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 */
	public void chatPOST(Message message, NetResponseHandler<Message> handler) {

		
	}

	
}
