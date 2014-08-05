package de.mxro.firewall;

public interface CheckCallback {

	/**
	 * Call if the request is allowed.
	 */
	public void allow();
	
	/**
	 * Call if the request is not allowed. Response object should be populated.
	 */
	public void block();
	
	/**
	 * Call if the request is not allowed. Response object will be populated with provided message.
	 * @param cause
	 */
	public void block(String cause);
	
	public void onFailure(Throwable t);
	
}
