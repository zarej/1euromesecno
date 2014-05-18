package rs.hakaton.euromesecno;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import rs.hakaton.euromesecno.sdk.activity.MainActivity;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BeneficiaryServiceResponseListener;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BenficiaryService;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
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
        Log.e("alarm debug", "Pocinjem da zakazujem alarm");
        scheduleAlarm();
    }
    
    public void scheduleAlarm(){
    	// set the time at which alarm will fire
    	// current time + 1 minute
    	Long time = new GregorianCalendar().getTimeInMillis()+5*1000;
    	
    	Intent intentAlarm = new Intent(this, AlarmReciever.class);
    	
    	AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	
    	
    	alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 10*1000, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//    	alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    	Log.e("alarm debug", "Zakazao alarm");
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
