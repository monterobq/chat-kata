package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	protected void onStart() {
		super.onStart();

		SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
				Context.MODE_PRIVATE);

		
		
		String host = prefs.getString(Magic.P_HOST, "").trim();

		// Check server config.
		// If not set, go to config activity
		if ("".equals(host)) {
			Intent i = new Intent(this, ConfigActivity.class);
			startActivity(i);
		}

		// Recover nick
		String nick = prefs.getString(Magic.P_NICK, "") ;
		EditText nt = (EditText) findViewById(R.id.nick);
		nt.setText(nick) ;

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void doLogin(View view) {

		EditText nick = (EditText) findViewById(R.id.nick);
		String nickname = nick.getText().toString().trim();

		
		// If nick is not empty, store it and start chat
		if (!"".equals(nickname)) {

			SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(Magic.P_NICK, nickname);
			editor.commit();

			Intent i = new Intent(this, ChatActivity.class);
			startActivity(i);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_config:
			Intent i = new Intent(this, ConfigActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
