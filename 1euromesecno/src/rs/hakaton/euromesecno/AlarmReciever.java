package rs.hakaton.euromesecno;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {

	public static final String TAG = "AlarmManagerDebug";
	NotificationManager nm;
	public static final int uniqueID = 22069621;
	public static final String EXTRA_ONLY_SET_ALARM = "only_set_alarm";
	public static final String EXTRA_RECV_FROM_BOOT = "received_from_boot_receiver";
	@Override
	public void onReceive(Context context, Intent callingIntent) {
		// TODO make notification happen here
		
		Log.d(TAG, "Receiver primljen");
		if (callingIntent.getBooleanExtra(EXTRA_ONLY_SET_ALARM, false)) {
			
			setMonthlyAlarm(context, callingIntent.getBooleanExtra(EXTRA_RECV_FROM_BOOT, false));
			return;
		}

		// Main Activity is from the 1euromesecno-sdk
		Intent i = new Intent(context, SplashActivity.class);
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(uniqueID);

		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		String title = "Dobrota je navika :)";
		String body = "Donirajte 1 SMS najugroženijima";

		Log.e("alarm debug", "Treba da krene notifikacija");
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title).setAutoCancel(true)
				.setContentText(body);
		mBuilder.setContentIntent(pi);

		Notification n = mBuilder.build();
		// n.setLatestEventInfo(context, title, body, pi);
		// n.defaults = Notification.DEFAULT_ALL;
		nm.notify(uniqueID, n);
//		setMonthlyAlarm(context);
	}

	public void setMonthlyAlarm(Context c, boolean receiveFromBoot) {

		 Log.d(TAG, "Podsetnik je u setMonthlyAlarm");

		Calendar calendarNow = Calendar.getInstance();

//		calendarNow.add(Calendar.MONTH, 1);
//		calendarNow.set(calendarNow.get(Calendar.YEAR),
//				calendarNow.get(Calendar.MONTH), 15, 13, 30);
		
		long interval = AlarmManager.INTERVAL_DAY;
		
		if (receiveFromBoot) interval = AlarmManager.INTERVAL_DAY * 3;
		

		long ms = calendarNow.getTimeInMillis() + interval;
		
//		long ms = System.currentTimeMillis() + 60 * 1000;

		AlarmManager mgr = (AlarmManager) c
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(c, AlarmReciever.class);
		PendingIntent pi = PendingIntent.getBroadcast(c, 0, i, 0);
		mgr.setInexactRepeating(AlarmManager.RTC, ms, interval, pi);
	}

}
