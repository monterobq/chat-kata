package org.ejmc.android.simplechat.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.ejmc.android.simplechat.configuration.DefaultValues;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.connection.ApiRestAsyncTask;

public class NetRequests {

	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {
		String uri = seq > 0 ? (DefaultValues.URI + "?seq=" + seq) : DefaultValues.URI;
		HttpGet get = new HttpGet(uri);
		new ApiRestAsyncTask<ChatList>(handler).execute(get);
	}

	public void chatPOST(Message message, NetResponseHandler<Message> handler) {
		HttpPost post = new HttpPost(DefaultValues.URI);
		StringEntity entity = getJsonStringEntity(message);
		post.setEntity(entity);
		new ApiRestAsyncTask<Message>(handler).execute(post);

	}

	private StringEntity getJsonStringEntity(Message message) {
		String jsonMessage = DefaultValues.GSON.toJson(message);
		StringEntity entity = null;
		try {
			entity = new StringEntity(jsonMessage, DefaultValues.CHARSET);
			entity.setContentEncoding(DefaultValues.CHARSET);
			entity.setContentType(DefaultValues.CONTENT_TYPE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}

}
