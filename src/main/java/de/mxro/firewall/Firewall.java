package de.mxro.firewall;

import java.util.List;

import de.mxro.firewall.internal.ProtectedService;
import de.mxro.httpserver.HttpService;
import de.mxro.httpserver.services.Services;

public class Firewall {

	public static final HttpService protect(HttpService service, Rule rule) {
		
		return Services.safeShutdown(new ProtectedService(service, rule));
		
	}
	
	public static final HttpService protect(HttpService service, List<Rule> rules) {
		return protect(service, rules, 0);
	}
	
	private static final HttpService protect(HttpService service, List<Rule> rules, int fromIdx) {
		if (fromIdx >= rules.size()) {
			return service;
		}
		
		return protect(protect(service, rules.get(fromIdx)), rules, fromIdx +1);
	}
}
