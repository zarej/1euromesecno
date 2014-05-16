package rs.hakaton.euromesecno.sdk.webservice;

import rs.hakaton.euromesecno.sdk.model.Settings;

public interface OnSettingsResponseListener {
	void onGetSettingsRespondSuccess(Settings settings);
	void onGetSettingsRespondError(String error, String response);
}
