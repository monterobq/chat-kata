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

/**
 * Activity for app configuration.
 * 
 * @author startic
 * 
 */
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

		// Load values
		SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
				Context.MODE_PRIVATE);

		EditText server = (EditText) findViewById(R.id.server);
		String servername = prefs.getString(Magic.P_HOST, "");
		server.setText(servername);

		EditText port = (EditText) findViewById(R.id.port);
		int portNumber = prefs.getInt(Magic.P_PORT, 80);
		port.setText(Integer.toString(portNumber));

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * Configuration button action.
	 * 
	 * Saves the configuration.
	 * 
	 * @param view
	 */
	public void doConfig(View view) {

		boolean ok = false;

		SharedPreferences prefs = getSharedPreferences(Magic.PREFERENCES,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		// Save server
		EditText server = (EditText) findViewById(R.id.server);
		String servername = server.getText().toString().trim();
		if (!"".equals(servername)) {
			editor.putString(Magic.P_HOST, servername);
			ok = true;
		}

		// Save port
		EditText port = (EditText) findViewById(R.id.port);
		String portNumber = port.getText().toString().trim();
		if (!"".equals(portNumber)) {
			editor.putInt(Magic.P_PORT, Integer.parseInt(portNumber));
		}

		if (ok) {
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
