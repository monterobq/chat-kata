package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.Message;

public abstract class PostChatTask extends NetTask<Message> {

	public void execute(Message m) {
		sendResponse(m);
	}

}
