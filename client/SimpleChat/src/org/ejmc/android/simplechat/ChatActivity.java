package org.ejmc.android.simplechat;

import org.ejmc.android.simplechat.configuration.DefaultValues;
import org.ejmc.android.simplechat.configuration.Host;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.model.NextSeq;
import org.ejmc.android.simplechat.net.GetServerHandler;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.PostServerHandler;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends Activity implements OnClickListener {

	private String nick;
	private NetRequests requests;
	private TextView chatList;
	private PostServerHandler postServerHandler;
	private GetServerHandler getServerHandler;
	private Handler refreshThreadHandler;
	private NextSeq nextSeq;

	private final Runnable refreshThread = new Runnable() {
		@Override
		public void run() {
			try {
				requests.chatGET(nextSeq.getNextSeq(), getServerHandler);
				refreshThreadHandler.postDelayed(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		findViewById(R.id.sendButton).setOnClickListener(this);
		setupActionBar();

		SharedPreferences preferences = getSharedPreferences(
				DefaultValues.SHARED_PREFERENCES, MODE_PRIVATE);

		nick = preferences.getString("nick", DefaultValues.NICK);

		String address = preferences.getString("host", DefaultValues.HOST);
		String port = preferences.getString("port", DefaultValues.PORT);
		Host host = new Host(address, port);

		chatList = (TextView) findViewById(R.id.chatList);

		postServerHandler = new PostServerHandler(getApplicationContext(),
				chatList);

		nextSeq = new NextSeq();

		getServerHandler = new GetServerHandler(getApplicationContext(),
				nextSeq, nick, chatList);

		refreshThreadHandler = new Handler();

		requests = new NetRequests(host);
	}

	@Override
	protected void onStart() {
		super.onStart();
		refreshThreadHandler.post(refreshThread);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		refreshThreadHandler.removeCallbacks(refreshThread);
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
	public void onClick(View arg0) {
		EditText messageEditText = (EditText) findViewById(R.id.messageToSendEditText);
		String messageString = messageEditText.getText().toString().trim();
		if (!messageString.isEmpty()) {
			Message message = new Message(nick, messageString);
			requests.chatPOST(message, postServerHandler);
		}
		messageEditText.setText("");
	}

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}
}
