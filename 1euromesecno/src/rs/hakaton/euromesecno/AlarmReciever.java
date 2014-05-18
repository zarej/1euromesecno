package rs.hakaton.euromesecno;

import rs.hakaton.euromesecno.sdk.activity.MainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {
	
	String TAG = "OnAlarmReceiver";
	NotificationManager nm;
	public static final int uniqueID = 22069621;

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO make notification happen here
		
		Log.e("alarm debug", "Receiver primljen");
		// Main Activity is from the 1euromesecno-sdk
		Intent i = new Intent(context, MainActivity.class);
		nm = (NotificationManager) context
		.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(uniqueID);

		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		String title = "Dobrota je navika :)";
		String body = "Donirajte 1 euro i pomozite najugroženijima";
		
		Log.e("alarm debug", "Treba da krene notifikacija");
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(context)
			    .setSmallIcon(R.drawable.ic_launcher)
			    .setContentTitle(title)
			    .setAutoCancel(true)
			    .setContentText(body);
		mBuilder.setContentIntent(pi);
		
		Notification n = mBuilder.build();
//		n.setLatestEventInfo(context, title, body, pi);
//		n.defaults = Notification.DEFAULT_ALL;
		nm.notify(uniqueID, n);
	}

}
