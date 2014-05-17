package rs.hakaton.euromesecno.sdk.beneficiaryservice;

import java.util.ArrayList;

import rs.hakaton.euromesecno.sdk.model.Beneficiary;

public interface BeneficiaryServiceResponseListener {
	void onBeneficiaryListReturn(ArrayList<Beneficiary> beneficiaries);
	void onBeneficiaryListReturnJson(String jsonResponse);
	void onBeneficiaryListReturnError(String error);
}
