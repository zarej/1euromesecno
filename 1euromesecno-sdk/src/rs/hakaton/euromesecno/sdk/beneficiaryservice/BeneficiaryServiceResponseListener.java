package rs.hakaton.euromesecno.sdk.beneficiaryservice;

import java.util.List;

import rs.hakaton.euromesecno.sdk.model.Beneficiary;

public interface BeneficiaryServiceResponseListener {
	void onBeneficiaryListReturn(List<Beneficiary> beneficiaries);
}
