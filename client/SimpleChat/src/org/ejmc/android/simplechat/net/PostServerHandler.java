package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.Message;

import android.content.Context;
import android.widget.TextView;

public class PostServerHandler extends ServerResponseHandler<Message> {

	private TextView chatList;
	
	public PostServerHandler(Context applicationContext, TextView chatList) {
		super(applicationContext);
		this.chatList = chatList;
	}

	@Override
	public void onSuccess(Message result) {
		chatList.append(result.getNick() + ": " + result.getMessage() + "\n");
	}

}