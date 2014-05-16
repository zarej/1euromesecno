package rs.hakaton.euromesecno.sdk.webservice;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class RequestHandler {

	private static final int STATUS_CODE_OK = 0;
	private static final int STATUS_CODE_ERROR = 1;

	private RequestListener listener;


	public RequestHandler(RequestListener listener) {
		this.listener = listener;
	}

	/**
	 * Get request to the server
	 * 
	 * @param php
	 *            URI for request
	 * 
	 */
	public synchronized void getRequest(final String url, final HashMap<String, String> pairs, final String methodName, final int requestCode, final Object callback) {

		(new AsyncTask<Void, Void, ResponseMessage>() {

			@Override
			protected ResponseMessage doInBackground(Void... params) {

				ResponseMessage responseMessage = new ResponseMessage();
				try {
					Log.v("Request", url);
					boolean executedWithError = false;
					int executedWithErrorCount = 0;
					do {
						// execute request
						String requestUrl = url + getUrlParametars(pairs);
						Log.i("FRM", "URL :" + requestUrl);
						String response = executeGetRequestForUrl(requestUrl);

						executedWithError = false;
						// token has not expired
						responseMessage.setStatusMessage(response);
						responseMessage.setStatusCode(STATUS_CODE_OK);

					} while (executedWithError && executedWithErrorCount <= 2);

				} catch (Exception e) {
					responseMessage.setStatusMessage(e.toString());
					responseMessage.setStatusCode(STATUS_CODE_ERROR);
					e.printStackTrace();
				}
				return responseMessage;
			}

			@Override
			protected void onPostExecute(ResponseMessage result) {

				super.onPostExecute(result);
				if (result.getStatusCode() == STATUS_CODE_OK) {
					listener.onComplete(result.getStatusMessage(), methodName, requestCode, callback);
				} else if (result.getStatusCode() == STATUS_CODE_ERROR) {
					listener.onResponseException(result.getStatusMessage(), requestCode, callback);
				}
			}

		}).execute();
	}

	private HttpParams getHttpParameters() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(httpParams, NetworkManager.TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(httpParams, NetworkManager.TIMEOUT);
		return httpParams;
	}

	private String executeGetRequestForUrl(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);

		Collection<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Content-type", "application/xml; charset=utf-8"));
		headers.add(new BasicHeader("Cache-Control", "no-cache, no-store, must-revalidate")); // HTTP
																								// 1.1.
		headers.add(new BasicHeader("Pragma", "no-cache")); // HTTP
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("User-Agent", "an IP-Phone"));
		httpGet.getParams().setParameter("http.default-headers", headers);
		HttpResponse response = executeHttp(httpGet);

		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}

	private HttpResponse executeHttp(final HttpRequestBase http) throws Exception {
		HttpClient httpClient = new DefaultHttpClient(getHttpParameters());
		HttpResponse response = httpClient.execute(http);
		return response;
	}

	private String getUrlParametars(HashMap<String, String> pairs) {
		StringBuffer buffer = new StringBuffer();
		if (pairs != null && pairs.size() > 0) {
			buffer.append("?");
			Iterator<String> iterator = pairs.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = pairs.get(key);
				if (value != null) {
					buffer.append(key).append("=").append(URLEncoder.encode(value));
					if (iterator.hasNext()) {
						buffer.append("&");
					}
				}
			}
		}
		return buffer.toString();
	}

}
