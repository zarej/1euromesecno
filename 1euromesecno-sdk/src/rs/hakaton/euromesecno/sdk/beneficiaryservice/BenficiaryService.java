package rs.hakaton.euromesecno.sdk.beneficiaryservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import rs.hakaton.euromesecno.sdk.webservice.WebService;
import android.util.Log;

import com.google.gson.Gson;

public class BenficiaryService extends WebService {
	
	private static final int GETTING_BENS = 1;
	
	
	public void getBeneficiaries(String retrievalUrl, BeneficiaryServiceResponseListener listener){
		HashMap<String, String> requestParamsPairs = new HashMap<String, String>();
		
		getRequestHandler().getRequest(retrievalUrl, requestParamsPairs, "getBeneficiaries", GETTING_BENS, listener);
	}
	
	@Override
	public void onComplete(String response, String methodName, int requestCode, Object callback) {
		Log.d("onComplete", "Response for" + methodName + "=" + response);
		
		switch (requestCode) {
		case GETTING_BENS:
			BeneficiaryServiceResponseListener beneficiaryListener = (BeneficiaryServiceResponseListener) callback;
			if (isJSONValid(response)) {
				beneficiaryListener.onBeneficiaryListReturnJson(response);
				beneficiaryListener.onBeneficiaryListReturn(parseBeneficiaries(response));
			} else {
				beneficiaryListener.onBeneficiaryListReturnError("Error parsing JSON");
			}
			break;
		}
		
	}
	
	private ArrayList<Beneficiary> parseBeneficiaries(String jsonResponse) {
		
		ArrayList<Beneficiary> benseficiaries = new ArrayList<Beneficiary>();
		
		try {
			JSONArray values = new JSONArray(jsonResponse);
			
			for (int i=0; i<values.length(); i++) {
				
				Beneficiary bens = new Gson().fromJson(values.getString(i), Beneficiary.class);
				benseficiaries.add(bens);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return benseficiaries;
	}

	@Override
	public void onResponseException(String response, int requestCode, Object callback) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case GETTING_BENS:
			BeneficiaryServiceResponseListener listener = (BeneficiaryServiceResponseListener) callback;
			listener.onBeneficiaryListReturnError("Probably server error");
			break;
		}
	}
}
