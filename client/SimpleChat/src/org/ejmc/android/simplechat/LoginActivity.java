package org.ejmc.android.simplechat;

import org.ejmc.android.simplechat.configuration.DefaultValues;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.loginButton).setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		SharedPreferences preferences = getSharedPreferences(DefaultValues.SHARED_PREFERENCES, MODE_PRIVATE);

		EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
		usernameEditText.setText(preferences.getString("nick", DefaultValues.NICK));
		
		EditText addressEditText = (EditText) findViewById(R.id.addressEditText);
		addressEditText.setText(preferences.getString("host", DefaultValues.HOST));
		
		EditText portEditText = (EditText) findViewById(R.id.portEditText);
		portEditText.setText(String.valueOf(preferences.getInt("port", DefaultValues.PORT)));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		
		SharedPreferences preferences = getSharedPreferences(DefaultValues.SHARED_PREFERENCES, MODE_PRIVATE);
				
		Editor preferencesEditor = preferences.edit();
		
		EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
		String username = usernameEditText.getText().toString();
		if(!username.isEmpty()) {
			preferencesEditor.putString("nick", username);
		} else {
			preferencesEditor.putString("nick", DefaultValues.NICK);
		}
		
		EditText addressEditText = (EditText) findViewById(R.id.addressEditText);
		String address = addressEditText.getText().toString();
		if(!address.isEmpty()) {
			preferencesEditor.putString("host", address);
		} else {
			preferencesEditor.putString("host", DefaultValues.HOST);
		}
		
		EditText portEditText = (EditText) findViewById(R.id.portEditText);
		String port = portEditText.getText().toString();
		if(!port.isEmpty()) {
			preferencesEditor.putInt("port", Integer.parseInt(port));
		} else {
			preferencesEditor.putInt("port", DefaultValues.PORT);
		}
		preferencesEditor.commit();
		
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
	}

}
