package org.ejmc.android.simplechat.net;

import android.os.AsyncTask;

/**
 * Async Task for mock operations.
 * 
 * (Helper class for development only)
 * 
 * @author startic
 * 
 * @param <Response>
 */
public class MockAsyncTask<Response> extends
		AsyncTask<Response, Void, Response> {

	private NetResponseHandler<Response> handler;

	public MockAsyncTask(NetResponseHandler<Response> handler) {
		super();
		this.handler = handler;
	}

	/**
	 * Returns parameter value.
	 */
	protected Response doInBackground(Response... params) {
		return params[0];
	};

	/**
	 * Notifies succes to UI with parameter value.
	 */
	protected void onPostExecute(Response result) {
		if (handler != null) {
			handler.onSuccess(result);
		}

	};

}
