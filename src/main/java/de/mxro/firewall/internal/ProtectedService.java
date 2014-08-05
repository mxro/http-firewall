package de.mxro.firewall.internal;

import io.nextweb.fn.Closure;
import io.nextweb.fn.SuccessFail;
import de.mxro.firewall.CheckCallback;
import de.mxro.firewall.Rule;
import de.mxro.httpserver.Request;
import de.mxro.httpserver.Response;
import de.mxro.httpserver.StoppableService;

public class ProtectedService implements StoppableService {

	private final StoppableService decorated;
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
	public void stop(Closure<SuccessFail> callback) {

		decorated.stop(callback);

	}

	public ProtectedService(StoppableService decorated, Rule rule) {
		super();
		this.decorated = decorated;
		this.rule = rule;
	}

}
