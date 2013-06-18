package org.ejmc.android.simplechat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Server request error.
 * 
 * @author startic
 * 
 */
public class RequestError {

	@SerializedName("error")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
