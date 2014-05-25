package rs.hakaton.euromesecno;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import rs.hakaton.euromesecno.sdk.activity.MainActivity;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BeneficiaryServiceResponseListener;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BenficiaryService;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import rs.hakaton.euromesecno.sdk.util.SharedPrefernceHelper;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity  implements
BeneficiaryServiceResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	if(!SharedPrefernceHelper.isIconCreated(this)) {
    		addShortcut();
    		SharedPrefernceHelper.setIconCreated(this, true);
    	}
    	
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        onBeneficiaryListReturnError("error");
        BenficiaryService service = new BenficiaryService();
        service.getBeneficiaries(getString(R.string.open_data_url_json), this);
//        Log.e("alarm debug", "Pocinjem da zakazujem alarm");
//        scheduleAlarm();
        scheduleAlarm2();
    }
    
    private void scheduleAlarm2() {
//    	if (!SharedPrefernceHelper.isAlarmFirstTimeStarted(this)) {
    		Log.i(AlarmReciever.TAG, "Pocinjem da zakazujem alarm");
    		Intent i = new Intent(this, AlarmReciever.class);
    		i.putExtra(AlarmReciever.EXTRA_ONLY_SET_ALARM, true);
    		sendBroadcast(i);
    		SharedPrefernceHelper.setAlarmFirstTimeStarted(this, true);
//    	} else {
//    		Log.i(AlarmReciever.TAG, "Alarm je vec zakazan");
//    	}
    }
    private void scheduleAlarm(){
    	// set the time at which alarm will fire
    	// current time + 1 minute
    	Long time = new GregorianCalendar().getTimeInMillis();
    	
    	Long interval = (long) (30*24*60*60*1000);
    	Intent intentAlarm = new Intent(this, AlarmReciever.class);
    	
    	AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	
    	
    	alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, interval, interval, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
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
		beneficiaries.add(new Beneficiary("Open Data", "Problem", "31", "1102", "2", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQaadEwWgFZW7Kxryp-tyML1pd0ZN9R6IaKumaNK1s0GpApo3yMNw", 
				"Bla bla", "", "", "", "", "", "", "", "", "", "", "", ""));
		
		Intent i = new Intent(SplashActivity.this, MainActivity.class);
		i.putExtra(MainActivity.EXTRA_BENS, beneficiaries);
		startActivity(i);
		finish();
		
	}

	private void addShortcut() {
	    //Adding shortcut for MainActivity 
	    //on Home screen
	    Intent shortcutIntent = new Intent(getApplicationContext(),
	            SplashActivity.class);

	    shortcutIntent.setAction(Intent.ACTION_MAIN);

	    Intent addIntent = new Intent();
	    addIntent
	            .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
	            Intent.ShortcutIconResource.fromContext(getApplicationContext(),
	                    R.drawable.ic_launcher));

	    addIntent
	            .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    getApplicationContext().sendBroadcast(addIntent);
	}


}
