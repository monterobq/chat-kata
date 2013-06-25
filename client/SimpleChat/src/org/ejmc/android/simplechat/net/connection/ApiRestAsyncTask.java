package org.ejmc.android.simplechat.net.connection;

import org.apache.http.HttpRequest;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.os.AsyncTask;

public class ApiRestAsyncTask<Response> extends AsyncTask<HttpRequest, Void, String> {

	private ServerConnection connection = new ServerConnection();
	private NetResponseHandler<Response> handler;
	private int statusCode;
	
	public ApiRestAsyncTask(NetResponseHandler<Response> handler) {
		this.handler = handler;
	}

	@Override
	protected String doInBackground(HttpRequest... params) {
		String text = null;
		ServerResponse response = connection.callServer(params[0]);
		if (response != null) {
			statusCode = response.getStatusCode();
			text = response.getMessage();
		}
		return text;
	}

	@Override
	protected void onPostExecute(String results) {
//		EditText et = (EditText) findViewById(R.id.my_edit);
//		if (results != null) {
//			et.setText(results);
//		} else {
//			et.setText("Chat-Kata Error: Connection error");
//		}
	}

}
