package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	@Override
	protected void onStart() {
		super.onStart();

		EditText server = (EditText) findViewById(R.id.server);
		SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
				Context.MODE_PRIVATE);
		String servername = prefs.getString(Magic.P_HOST, "");
		server.setText(servername);

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	public void doConfig(View view) {

		EditText server = (EditText) findViewById(R.id.server);

		String servername = server.getText().toString().trim();

		// If nick is not empty, store it and start chat
		if (!"".equals(servername)) {

			SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(Magic.P_HOST, servername);
			editor.commit();

			NavUtils.navigateUpFromSameTask(this);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
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

}
