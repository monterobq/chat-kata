package org.ejmc.android.simplechat.net;

import android.content.Context;
import android.widget.TextView;

public class DeleteServerHandler extends ServerResponseHandler<Object> {
	
	private TextView chatList;
	
	public DeleteServerHandler(Context applicationContext, TextView chatList) {
		super(applicationContext);
		this.chatList = chatList;
	}
	
	@Override
	public void onSuccess() {
		chatList.setText("");
	}
}

