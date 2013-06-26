package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.model.NextSequence;

import android.content.Context;
import android.widget.TextView;

public class GetServerHandler extends ServerResponseHandler<ChatList> {

	private TextView chatList;
	private NextSequence nextSeq;

	public GetServerHandler(Context applicationContext, NextSequence nextSeq, TextView chatList) {
		super(applicationContext);
		this.chatList = chatList;
		this.nextSeq = nextSeq;
	}

	@Override
	public void onSuccess(ChatList result) {
		for (Message message : result.getMessages()) {
			chatList.append(message.getNick() + ": " + message.getMessage() + "\n");
		}
		nextSeq.setNextSeq(result.getNextSeq());
	}
}