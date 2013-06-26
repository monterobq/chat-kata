package org.ejmc.android.simplechat.configuration;

import com.google.gson.Gson;

public class DefaultValues {
	
	public static final String HOST = "172.16.100.51";
	
	public static final String PORT = "";
	
	public static final String USER_AGENT = "http.agent";
	
	public static final String URI = "/chat-kata/api/chat";
	
	public static final String NICK = "john.doe";
	
	public static final String CHARSET = "UTF-8";
	
	public static final String CONTENT_TYPE = "application/json";
	
	public static final String SHARED_PREFERENCES = "SharedPreferences";
	
	public static final int REFRESH_TIME = 1000;
	
	public static final Gson GSON = new Gson();

}
