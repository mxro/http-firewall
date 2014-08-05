package de.mxro.firewall.rules;

import de.mxro.firewall.Rule;
import de.mxro.firewall.internal.rules.AllowAllRule;
import de.mxro.firewall.internal.rules.TestSomeRule;

public class Rules {

	public final static Rule applyDecoratedRuleForSomeRequests(RequestFilter filter, Rule decorated) {
		
		return new TestSomeRule(filter, decorated);
		
	}
	
	public final static Rule allowAll() {
		return new AllowAllRule();
	}
	
}
