package rs.hakaton.euromesecno;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			
			Intent i = new Intent(context, AlarmReciever.class);
			i.putExtra(AlarmReciever.EXTRA_ONLY_SET_ALARM, true);
			i.putExtra(AlarmReciever.EXTRA_RECV_FROM_BOOT, true);
			context.sendBroadcast(i);
			
//			Long time = new GregorianCalendar().getTimeInMillis()+5*24*60*60*1000;
//			
//			Long interval = (long) (30*24*60*60*1000);
//	    	
//	    	Intent intentAlarm = new Intent(context, AlarmReciever.class);
//	    	
//	    	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//	    	
//	    	
//	    	alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, interval, PendingIntent.getBroadcast(context,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
////	    	alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	    	Log.d(AlarmReciever.TAG, "Zakazao alarm na bootu");
			
//	    	Toast.makeText(context, "Zakazao alarm na bootu", Toast.LENGTH_LONG).show();

		}

	}
}