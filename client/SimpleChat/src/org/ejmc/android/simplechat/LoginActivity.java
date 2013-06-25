package org.ejmc.android.simplechat;

import org.ejmc.android.simplechat.configuration.DefaultValues;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	@Override
	protected void onStart() {
		SharedPreferences preferences = getSharedPreferences(DefaultValues.SHARED_PREFERENCES, MODE_PRIVATE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
