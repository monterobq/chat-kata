package org.ejmc.android.simplechat.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {

	private NetConfig netConfig;

	public NetRequests(NetConfig netConfig) {
		super();
		this.netConfig = netConfig;
	}

	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {
		// ChatList l = new ChatList();
		//
		// List<Message> messages = new ArrayList<Message>();
		// l.setMessages(messages);
		//
		// int size = new Random().nextInt(6);
		// for (int i = 0; i < size; i++) {
		// Message m = new Message();
		// m.setNick("user" + i);
		// m.setMessage("fsdfasfas " + (seq + i + 1));
		// messages.add(m);
		// }
		// l.setLastSeq(seq + size);
		//
		// new MockAsyncTask<ChatList>(handler).execute(l);
		//
		//
		HttpParams params = new BasicHttpParams();
		params.setIntParameter("seq", seq);
		HttpGet get = new HttpGet("/api/chat");

		new HttpJsonAsyncTask<ChatList>(netConfig, handler, ChatList.class)
				.execute(get);
	}

	public void chatPOST(Message message, NetResponseHandler<Message> handler) {

		HttpPost post = new HttpPost("/api/chat");
		HttpEntity entity = createEntity(message);
		post.setEntity(entity);

		new HttpJsonAsyncTask<Message>(netConfig, handler, Message.class)
				.execute(post);
	}

	/**
	 * Creates htp entity from object.
	 * 
	 * @param o
	 * @return
	 */
	private StringEntity createEntity(Object o) {
		try {
			String json = netConfig.getGson().toJson(o);

			StringEntity entity = new StringEntity(json, "UTF-8");
			entity.setContentType("application/json.");

			return entity;
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("UTF-8 not supported");

		}
	}

}
