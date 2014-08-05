package de.mxro.firewall;

import de.mxro.httpserver.Request;
import de.mxro.httpserver.Response;

public interface Rule {

	public void apply(Request request, Response response, CheckCallback callback);
	
}
