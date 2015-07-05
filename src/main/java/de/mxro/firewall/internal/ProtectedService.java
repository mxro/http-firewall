package de.mxro.firewall.internal;

import de.mxro.firewall.CheckCallback;
import de.mxro.firewall.Rule;
import de.mxro.httpserver.HttpService;
import de.mxro.httpserver.Request;
import de.mxro.httpserver.Response;
import delight.async.callbacks.SimpleCallback;
import delight.functional.Closure;
import delight.functional.SuccessFail;

public class ProtectedService implements HttpService {

	private final HttpService decorated;
	private final Rule rule;

	@Override
	public void process(final Request request, final Response response,
			final Closure<SuccessFail> callback) {

		rule.apply(request, response, new CheckCallback() {

			@Override
			public void allow() {
				decorated.process(request, response, callback);
			}

			@Override
			public void onFailure(Throwable t) {
				callback.apply(SuccessFail.fail(t));
			}

			@Override
			public void block() {
				callback.apply(SuccessFail.success());
			}

			@Override
			public void block(String cause) {

				response.setContent(cause);
				response.setMimeType("text/plain");
				response.setResponseCode(403);
				block();
			}

		});

	}

	@Override
	public void stop(SimpleCallback callback) {

		decorated.stop(callback);

	}

	@Override
	public void start(SimpleCallback callback) {
		decorated.start(callback);
	}

	public ProtectedService(HttpService decorated, Rule rule) {
		super();
		this.decorated = decorated;
		this.rule = rule;
	}

}
