package rs.hakaton.euromesecno.sdk.beneficiaryservice;

import java.util.HashMap;

import android.util.Log;
import rs.hakaton.euromesecno.sdk.webservice.OnSettingsResponseListener;
import rs.hakaton.euromesecno.sdk.webservice.WebService;

public class BenficiaryService extends WebService {
	
	private static final int GETTING_LIST = 1;
	
	public void getBeneficiaries(OnSettingsResponseListener listener){
		String retrievalUrl="";
		HashMap<String, String> requestParamsPairs = new HashMap<String, String>();
		
		getRequestHandler().getRequest(retrievalUrl, requestParamsPairs, "getBeneficiaries", GETTING_LIST, listener);
	}
	
	@Override
	public void onComplete(String response, String methodName, int requestCode, Object callback) {
		Log.d("onComplete", "Response for" + methodName + "=" + response);
		
		switch (requestCode) {
		case GETTING_LIST:
			BeneficiaryServiceResponseListener beneficiaryListener = (BeneficiaryServiceResponseListener) callback;
			if (isJSONValid(response)) {
				beneficiaryListener.onBeneficiaryListReturn(parseBeneficiaries(response));
			} else {
				beneficiaryListener.onGetSettingsRespondError("Error parsing JSON", response);
			}
			break;
		}
		
	}
	
	private List<Beneficiary> parseBeneficiaries(String jsonResponse) {
		// TODO Parsing a json response full of Beneficiaries
		Beneficiary[] bens = new Gson().fromJson(jsonResponse, Beneficiary[].class);
		return null;
	}

	@Override
	public void onResponseException(String response, int requestCode, Object callback) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case GETTING_LIST:
			OnSettingsResponseListener listener = (OnSettingsResponseListener) callback;
			listener.onGetSettingsRespondError("Probably server error", response);
			break;
		}
	}
}
