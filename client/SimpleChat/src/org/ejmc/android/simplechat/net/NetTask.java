package org.ejmc.android.simplechat.net;

import java.util.Map;

import android.os.AsyncTask;

/**
 * Base net task.
 * 
 * handles http
 * 
 * @author startic
 * 
 * @param <ReturnValue>
 */
public abstract class NetTask<ReturnValue> {

	
	private class MockAsyncTask extends
			AsyncTask<ReturnValue, Void, ReturnValue> {

		protected ReturnValue doInBackground(ReturnValue... params) {

			return params[0];
		};

		protected void onPostExecute(ReturnValue result) {

			onSuccess(result);
		};
	}

	protected void send(Map<String, String> paramters, Object jsonBody) {
	}

	/**
	 * Method for developers .
	 * 
	 * Set response immediately (for building mocks )
	 * 
	 * @param result
	 */
	protected void sendResponse(ReturnValue result) {
		new MockAsyncTask().execute(result);
	}

	protected abstract void onSuccess(ReturnValue result);
}
