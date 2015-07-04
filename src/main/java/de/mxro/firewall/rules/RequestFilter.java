package de.mxro.firewall.rules;

import de.mxro.httpserver.Request;
import delight.async.callbacks.ValueCallback;

public interface RequestFilter {

	public void doesRequestMatch(Request request, ValueCallback<Boolean> callback);
	
}
