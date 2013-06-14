package org.ejmc.android.simplechat.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.ejmc.android.simplechat.model.RequestError;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import com.google.gson.Gson;

/**
 * Async task for JSON REST requests.
 * 
 * @author startic
 * 
 * @param <Response>
 * 
 */
public class HttpJsonAsyncTask<Response> extends
		AsyncTask<HttpRequest, Void, String> {

	private NetConfig netConfig;

	private int returnCode;

	private Class<? extends Response> returnClass;

	private NetResponseHandler<Response> handler;

	public HttpJsonAsyncTask(NetConfig netConfig,
			NetResponseHandler<Response> handler,
			Class<? extends Response> returnClass) {
		super();
		this.netConfig = netConfig;
		this.handler = handler;
		this.returnClass = returnClass;
	}

	@Override
	protected String doInBackground(HttpRequest... params) {

		AndroidHttpClient client = AndroidHttpClient.newInstance(netConfig
				.getUserAgent());

		try {

			HttpResponse resp = client.execute(netConfig.getHost(), params[0],
					netConfig.getContext());

			returnCode = resp.getStatusLine().getStatusCode();
			// Read json
			// TODO Check Content type
			return readJson(resp);

		} catch (IOException e) {
		} finally {
			client.close();
		}
		// Is error
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		
		if (result == null) {
			handler.onNetError();
		}

		Gson gson = netConfig.getGson();
		if (returnCode == 200) {
			Response resp = gson.fromJson(result, returnClass);
			handler.onSuccess(resp);
		} else if (returnCode == 400) {
			RequestError error = gson.fromJson(result, RequestError.class);
			handler.onRequestError(error);
		} else {
			handler.onNetError();
		}

	}

	private String readJson(HttpResponse resp) {
		HttpEntity e = resp.getEntity();
		InputStreamReader reader = null;

		try {
			StringWriter writer = new StringWriter();
			reader = new InputStreamReader(e.getContent());

			char[] buff = new char[32768];
			int leidos;
			while ((leidos = reader.read()) != -1) {
				writer.write(buff, 0, leidos);
			}
			writer.flush();
			writer.close();

			return writer.toString();
		} catch (IOException ex) {
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					// TODO LOG
				}
			}
		}
	}
}