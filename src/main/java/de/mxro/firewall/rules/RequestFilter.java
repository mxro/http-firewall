package de.mxro.firewall.rules;

import io.nextweb.fn.callbacks.ValueCallback;
import de.mxro.httpserver.Request;

public interface RequestFilter {

	public void doesRequestMatch(Request request, ValueCallback<Boolean> callback);
	
}
