package rs.hakaton.euromesecno;

import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			
			Long time = new GregorianCalendar().getTimeInMillis()+5*1000;
	    	
	    	Intent intentAlarm = new Intent(context, AlarmReciever.class);
	    	
	    	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    	
	    	
	    	alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 10*1000, PendingIntent.getBroadcast(context,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//	    	alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	    	Log.e("alarm debug", "Zakazao alarm na bootu");
			
	    	Toast.makeText(context, "Zakazao alarm na bootu", Toast.LENGTH_LONG).show();

		}

	}
}