package de.mxro.firewall.internal.rules;

import de.mxro.firewall.CheckCallback;
import de.mxro.firewall.Rule;
import de.mxro.httpserver.Request;
import de.mxro.httpserver.Response;

public class AllowAllRule implements Rule {

	@Override
	public void apply(Request request, Response response, CheckCallback callback) {
		callback.allow();
	}

	
	
}
