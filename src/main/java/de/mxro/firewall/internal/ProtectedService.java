package de.mxro.firewall.internal;

import io.nextweb.fn.Closure;
import io.nextweb.fn.SuccessFail;
import de.mxro.firewall.CheckCallback;
import de.mxro.firewall.Rule;
import de.mxro.httpserver.HttpService;
import de.mxro.httpserver.Request;
import de.mxro.httpserver.Response;
import de.mxro.service.callbacks.ShutdownCallback;
import de.mxro.service.callbacks.StartCallback;

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
	public void stop(ShutdownCallback callback) {

		decorated.stop(callback);

	}

	@Override
	public void start(StartCallback callback) {
		decorated.start(callback);
	}

	public ProtectedService(HttpService decorated, Rule rule) {
		super();
		this.decorated = decorated;
		this.rule = rule;
	}

}
