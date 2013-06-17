package org.ejmc.android.simplechat.net;

import org.apache.http.HttpHost;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;

/**
 * Net configuration.
 * 
 * @author startic
 * 
 */
public class NetConfig {

	private HttpHost host;

	private HttpContext context;

	private String userAgent;

	private Gson gson = new Gson();

	public NetConfig(String hostName, int port) {

		// Initialize
		host = new HttpHost(hostName, port);
		context = new BasicHttpContext();
		userAgent = System.getProperty("http.agent");

	}

	/**
	 * HTTP context.
	 * 
	 * @return
	 */
	public HttpContext getContext() {
		return context;
	}

	/**
	 * JSON Serializer object.
	 * 
	 * @return
	 */
	public Gson getGson() {
		return gson;
	}

	/**
	 * Host configuration.
	 * 
	 * @return
	 */
	public HttpHost getHost() {
		return host;
	}

	/**
	 * User agent used for HTTP requests.
	 * 
	 * @return
	 */
	public String getUserAgent() {
		return userAgent;
	}
}
