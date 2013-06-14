package org.ejmc.android.simplechat.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;

import android.app.Activity;

public abstract class GetChatTask extends NetTask<ChatList> {

	public void execute(int seq) {

		ChatList l = new ChatList();

		List<Message> messages = new ArrayList<Message>();
		l.setMessages(messages);

		int size = new Random().nextInt(6);
		for (int i = 0; i < size; i++) {
			Message m = new Message();
			m.setNick("user" + i);
			m.setMessage("fsdfasfas " + (seq + i + 1));
			messages.add(m);
		}
		l.setLastSeq(seq + size);

		sendResponse(l);
	}

}
