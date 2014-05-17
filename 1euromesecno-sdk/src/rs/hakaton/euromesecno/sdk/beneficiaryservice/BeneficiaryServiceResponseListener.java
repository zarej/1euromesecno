package rs.hakaton.euromesecno.sdk.beneficiaryservice;

public interface BeneficiaryServiceResponseListener {
	void onBeneficiaryListReturn(List<Beneficiary> beneficiaries);
}
