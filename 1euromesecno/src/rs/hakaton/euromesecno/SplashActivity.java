package rs.hakaton.euromesecno;

import java.util.ArrayList;

import rs.hakaton.euromesecno.sdk.activity.MainActivity;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BeneficiaryServiceResponseListener;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BenficiaryService;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends ActionBarActivity  implements
BeneficiaryServiceResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        onBeneficiaryListReturnError("error");
        BenficiaryService service = new BenficiaryService();
        service.getBeneficiaries(getString(R.string.open_data_url_json), this);
    }

	@Override
	public void onBeneficiaryListReturn(ArrayList<Beneficiary> beneficiaries) {
		Intent i = new Intent(SplashActivity.this, MainActivity.class);
		i.putExtra(MainActivity.EXTRA_BENS, beneficiaries);
		startActivity(i);
		finish();
	}

	@Override
	public void onBeneficiaryListReturnJson(String jsonResponse) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBeneficiaryListReturnError(String error) {
		Toast.makeText(this, "Probably problem with Open data. Opening demo...", Toast.LENGTH_LONG).show();
		ArrayList<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		beneficiaries.add(new Beneficiary("Zarkoooo", "Zarkoooo", "31", "1102", "2", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQaadEwWgFZW7Kxryp-tyML1pd0ZN9R6IaKumaNK1s0GpApo3yMNw", "Bla bla"));
		beneficiaries.add(new Beneficiary("Aleksandra", "Karapandzic", "31", "1102", "2", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQaadEwWgFZW7Kxryp-tyML1pd0ZN9R6IaKumaNK1s0GpApo3yMNw", "Bla bla"));
		
		Intent i = new Intent(SplashActivity.this, MainActivity.class);
		i.putExtra(MainActivity.EXTRA_BENS, beneficiaries);
		startActivity(i);
		finish();
		
	}




}
