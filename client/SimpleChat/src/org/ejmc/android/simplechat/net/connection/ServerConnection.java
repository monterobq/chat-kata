package org.ejmc.android.simplechat.net.connection;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.configuration.DefaultValues;

import android.net.http.AndroidHttpClient;

public class ServerConnection {

	private HttpContext context;
	private String userAgent;
	private HttpHost host;

	public ServerConnection() {
		host = new HttpHost(DefaultValues.HOST, DefaultValues.PORT);
		userAgent = System.getProperty("http.agent");
		context = new BasicHttpContext();
	}

	public ServerResponse callServer(HttpRequest request) {
		AndroidHttpClient client = AndroidHttpClient.newInstance(userAgent);
		ServerResponse response = null;
		try {
			HttpResponse httpResponse = client.execute(host, request, context);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String message = EntityUtils.toString(httpResponse.getEntity());
			response = new ServerResponse(statusCode, message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		return response;
	}

}
