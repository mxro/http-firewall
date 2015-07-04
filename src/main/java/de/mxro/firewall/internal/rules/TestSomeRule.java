package de.mxro.firewall.internal.rules;

import de.mxro.firewall.CheckCallback;
import de.mxro.firewall.Rule;
import de.mxro.firewall.rules.RequestFilter;
import de.mxro.httpserver.Request;
import de.mxro.httpserver.Response;
import delight.async.callbacks.ValueCallback;

/**
 * Apply a decorated rule to some requests, which match a filter.
 * 
 * @author adminuser
 * 
 */
public class TestSomeRule implements Rule {

	
	private final RequestFilter filter;
	private final Rule decorated;

	@Override
	public void apply(final Request request, final Response response,
			final CheckCallback callback) {

		filter.doesRequestMatch(request, new ValueCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean value) {
				if (!value) {
					callback.allow();
					return;
				}
				
				decorated.apply(request, response, callback);
			}
			
			@Override
			public void onFailure(Throwable t) {
				callback.onFailure(t);
			}
		});
		
	}

	public TestSomeRule(RequestFilter filter, Rule decorated) {
		super();
		this.filter = filter;
		this.decorated = decorated;
	}

	
	
}
