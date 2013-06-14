package org.ejmc.android.simplechat;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.GetChatTask;
import org.ejmc.android.simplechat.net.PostChatTask;

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
	private class GetTask extends GetChatTask {

		@Override
		protected void onSuccess(ChatList result) {

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
	private class PostTask extends PostChatTask {
		@Override
		protected void onSuccess(Message result) {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		// Show the Up button in the action bar.
		setupActionBar();

		periodicHandler = new Handler();

		// Get nick name
		SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
				Context.MODE_PRIVATE);
		nick = prefs.getString("nick", "!undef!");

		tv = (TextView) findViewById(R.id.chat);
		scroller = (ScrollView) findViewById(R.id.chatScroll);

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
						new GetTask().execute(lastSeq);
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
			new PostTask().execute(m);
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
		scroller.post(new Runnable() {

			@Override
			public void run() {
				scroller.smoothScrollTo(0, tv.getBottom());

			}
		});
	}
}
