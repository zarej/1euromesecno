package rs.hakaton.euromesecno.sdk.webservice;

public interface RequestListener {

	/**
	 * On complete method called when request completes with a given response
	 * 
	 * @param response
	 */
	public void onComplete(String response, String methodName, int requestCode, Object callback);

	/**
	 * Exception when request is not completed with given response
	 * 
	 * @param response
	 */
	public void onResponseException(String response, int requestCode, Object callback);

}
