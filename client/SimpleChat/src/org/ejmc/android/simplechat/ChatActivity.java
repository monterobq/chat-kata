package org.ejmc.android.simplechat;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.NetConfig;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	/**
	 * Gets chat from server
	 */
	private class GetChatHandler extends NetResponseHandler<ChatList> {

		@Override
		public void onSuccess(ChatList result) {

			boolean added = false;
			// Filter my messages
			for (Message m : result.getMessages()) {
				if (!nick.equals(m.getNick())) {
					added = true;
					tv.append(m.getNick() + ": " + m.getMessage() + "\n");
				}
			}
			// Update last sequence
			lastSeq = result.getLastSeq();

			if (added) {
				updateScroll();
			}

			// Re-schedule refresh
			scheduleRefresh(false);
		}
	}

	/**
	 * Send message to server and paint it on return ;
	 * 
	 */
	private class PostChatHandler extends NetResponseHandler<Message> {
		@Override
		public void onSuccess(Message result) {
			tv.append(result.getNick() + ": " + result.getMessage() + "\n");
			updateScroll();
		}

	}

	private static final long REFRESH_INTERVAL = 2 * 1000L;

	private String nick;

	private int lastSeq = -1;

	private boolean started;

	private Handler periodicHandler;

	private TextView tv;

	private ScrollView scroller;

	private NetRequests netRequests;

	private GetChatHandler getChatHandler = new GetChatHandler();

	private PostChatHandler postChatHandler = new PostChatHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		// Show the Up button in the action bar.
		setupActionBar();

		tv = (TextView) findViewById(R.id.chat);
		scroller = (ScrollView) findViewById(R.id.chatScroll);

		periodicHandler = new Handler();

		// Get nick name
		SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
				Context.MODE_PRIVATE);
		nick = prefs.getString(Magic.P_NICK, "!undef!");

		// Configure network
		String host = prefs.getString(Magic.P_HOST, "localhost");
		int port = prefs.getInt(Magic.P_PORT, 80);
		NetConfig netConfig = new NetConfig(host, port);
		netRequests = new NetRequests(netConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		started = true;
		scheduleRefresh(true);

	}

	@Override
	protected void onStop() {
		super.onStop();
		started = false;
	}

	private void scheduleRefresh(boolean now) {
		if (started) {

			Runnable refresh = new Runnable() {
				@Override
				public void run() {
					if (started) {
						netRequests.chatGET(lastSeq, getChatHandler);
					}
				}
			};

			if (now) {
				refresh.run();
			} else {
				periodicHandler.postDelayed(refresh, REFRESH_INTERVAL);
			}
		}
	}

	public void sendMessage(View view) {

		// Obtain message
		EditText msg = (EditText) findViewById(R.id.message);
		String message = msg.getText().toString().trim();

		if (!"".equals(message)) {

			// send to server if not empty
			Message m = new Message(nick, message);
			netRequests.chatPOST(m, postChatHandler);
		}

		// Clear input
		msg.setText("");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void updateScroll() {
		// Run scroll after text additions
		scroller.post(new Runnable() {
			@Override
			public void run() {
				scroller.smoothScrollTo(0, tv.getBottom());
			}
		});
	}
}
