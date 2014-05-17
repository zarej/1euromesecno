package rs.hakaton.euromesecno.sdk.webservice;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import rs.hakaton.euromesecno.sdk.model.Settings;
import android.util.Log;

import com.google.gson.Gson;




public class WebService implements RequestListener {
	
	private static final int REQ_SETTINGS = 1;	
	private static final int REQ_SERVERMETA = 2;
	
	private RequestHandler handler;
	
	public void getSettings(String url, HashMap<String, String> pairs, OnSettingsResponseListener listener) {
		
		getRequestHandler().getRequest(url, pairs, "getSettings", REQ_SETTINGS, listener);
	}

	@Override
	public void onComplete(String response, String methodName, int requestCode, Object callback) {
		Log.d("onComplete", "Response for" + methodName + "=" + response);
		
		switch (requestCode) {
		case REQ_SETTINGS:
			OnSettingsResponseListener settingsListener = (OnSettingsResponseListener) callback;
			if (isJSONValid(response)) {
				settingsListener.onGetSettingsRespondSuccess(parseSettings(response));
			} else {
				settingsListener.onGetSettingsRespondError("Error parsing JSON", response);
			}
			break;
		}
		
	}
	
	private Settings parseSettings(String jsonResponse) {
		
		Settings settings = new Gson().fromJson(jsonResponse, Settings.class);
		
		return settings;
	}

	@Override
	public void onResponseException(String response, int requestCode, Object callback) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case REQ_SETTINGS:
			OnSettingsResponseListener listener = (OnSettingsResponseListener) callback;
			listener.onGetSettingsRespondError("Probably server error", response);
			break;
		case REQ_SERVERMETA:
			
			break;
		}
	}
	
	protected RequestHandler getRequestHandler() {
		if (handler == null) {
			handler = new RequestHandler(this);
		}
		return handler;
	}
	
	protected boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			return false;
		} catch (NullPointerException ex) {
			return false;
		}
		return true;
	}
	
	private static final String FIRST_TAG = "settings";
	
	public HashMap<String, String> parseServerMeta(String xml) throws XmlPullParserException, NumberFormatException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xml = xml.replaceAll("\n", "");
		xpp.setInput(new StringReader(xml));
		
		
		HashMap<String, String> serverMeta = new HashMap<String, String>();
		
		String key = null;
		
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				System.out.println("Start document");
				
			} else if (eventType == XmlPullParser.START_TAG) {
				if (xpp.getName() == FIRST_TAG) {
					eventType = xpp.next();
					continue;
				}
				System.out.println("Start tag " + xpp.getName());
				key = xpp.getName();
				
			} else if (eventType == XmlPullParser.END_TAG) {
				System.out.println("End tag " + xpp.getName());
			} else if (eventType == XmlPullParser.TEXT) {
				
				if (xpp.getText().trim().equals("")) {
					eventType = xpp.next();
					continue;
				}
				
				serverMeta.put(key, xpp.getText());
				System.out.println("Text " + xpp.getText());
				
			}
			eventType = xpp.next();
		}
		System.out.println("End document");

		return serverMeta;	
	}
	
	
}
