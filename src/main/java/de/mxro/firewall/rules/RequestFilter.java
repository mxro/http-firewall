package de.mxro.firewall.rules;

import de.mxro.async.callbacks.ValueCallback;
import de.mxro.httpserver.Request;

public interface RequestFilter {

	public void doesRequestMatch(Request request, ValueCallback<Boolean> callback);
	
}
