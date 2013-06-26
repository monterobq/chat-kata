package org.ejmc.android.simplechat.net.connection;

import org.apache.http.HttpRequest;
import org.ejmc.android.simplechat.configuration.DefaultValues;
import org.ejmc.android.simplechat.configuration.Host;
import org.ejmc.android.simplechat.model.RequestError;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import com.google.gson.JsonParseException;

import android.os.AsyncTask;

public class ApiRestAsyncTask<Response> extends
		AsyncTask<HttpRequest, Void, String> {

	private ServerConnection connection;
	private NetResponseHandler<Response> handler;
	private Class<Response> responseClass;
	private int statusCode;

	public ApiRestAsyncTask(Host host, NetResponseHandler<Response> handler, Class<Response> responseClass) {
		connection = new ServerConnection(host);
		this.responseClass = responseClass;
		this.handler = handler;
	}
	
	public ApiRestAsyncTask(Host host, NetResponseHandler<Response> handler) {
		connection = new ServerConnection(host);
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
		if (results == null) {
			handler.onNetError();
		}
		try {
			switch (statusCode) {
			case 200:
				Response response = DefaultValues.GSON.fromJson(results, responseClass);
				handler.onSuccess(response);
				break;
			case 201:
				handler.onSuccess();
				break;
			case 204:
				handler.onSuccess();
				break;				
			case 400:
				RequestError error = DefaultValues.GSON.fromJson(results, RequestError.class);
				handler.onRequestError(error);
				break;
			default:
				handler.onNetError();
				break;
			}
		} catch (JsonParseException e) {
			RequestError error = new RequestError();
			error.setError("JSON response is invalid");
			handler.onRequestError(error);
		}
	}
}
