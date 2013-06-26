package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.R;
import org.ejmc.android.simplechat.model.RequestError;

import android.content.Context;
import android.widget.Toast;

public class ServerResponseHandler<Ŕesponse> extends NetResponseHandler<Ŕesponse> {
	
	private Context applicationContext;
	
	public ServerResponseHandler(Context applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onNetError() {
		Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRequestError(RequestError error) {
		Toast.makeText(applicationContext, error.getError(), Toast.LENGTH_SHORT).show();
	}
}
