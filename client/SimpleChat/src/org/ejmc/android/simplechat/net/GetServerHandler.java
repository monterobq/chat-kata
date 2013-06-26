package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.model.NextSeq;

import android.content.Context;
import android.widget.TextView;

public class GetServerHandler extends ServerResponseHandler<ChatList> {

	private TextView chatList;
	private NextSeq nextSeq;
	private String nick;

	public GetServerHandler(Context applicationContext, NextSeq nextSeq,
			String nick, TextView chatList) {
		super(applicationContext);
		this.chatList = chatList;
		this.nextSeq = nextSeq;
		this.nick = nick;
	}

	@Override
	public void onSuccess(ChatList result) {
		for (Message message : result.getMessages()) {
			if (!nick.equals(message.getNick()) || nextSeq.getNextSeq() == 0) {
				chatList.append(message.getNick() + ": " + message.getMessage() + "\n");
			}
		}
		nextSeq.setNextSeq(result.getLastSeq());
	}
}