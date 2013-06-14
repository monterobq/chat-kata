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
	
	private String userAgent ;
	
	private Gson gson = new Gson();

	public NetConfig(String hostName, int port) {

		host = new HttpHost(hostName, port);
		context = new BasicHttpContext();
		userAgent = System.getProperty("http.agent");

	}

	public HttpContext getContext() {
		return context;
	}

	public Gson getGson() {
		return gson;
	}

	public HttpHost getHost() {
		return host;
	}

	public String getUserAgent() {
		return userAgent;
	}
}
