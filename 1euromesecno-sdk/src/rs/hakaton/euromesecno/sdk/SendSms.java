package rs.hakaton.euromesecno.sdk;

import rs.hakaton.euromesecno.sdk.activity.MainActivity;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SendSms {

	public void send(final Context c, final Beneficiary ben) {
		try {

			String SENT = "sent";
			String DELIVERED = "delivered";
			
			String msg = ben.getRedni_broj();
			
			if (msg==null) msg="pomoc";

			Intent sentIntent = new Intent(SENT);
			/* Create Pending Intents */
			PendingIntent sentPI = PendingIntent.getBroadcast(
					c, 0, sentIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);

			Intent deliveryIntent = new Intent(DELIVERED);

			PendingIntent deliverPI = PendingIntent.getBroadcast(
					c, 0, deliveryIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			/* Register for SMS send action */
			c.registerReceiver(new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					String result = "";

					switch (getResultCode()) {

					case Activity.RESULT_OK:
						result = "Transmission successful";
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						result = "Transmission failed";
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						result = "Radio off";
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						result = "No PDU defined";
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						result = "No service";
						break;
					}

					Toast.makeText(c.getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
				}

			}, new IntentFilter(SENT));
			/* Register for Delivery event */
			c.registerReceiver(new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					Toast.makeText(c.getApplicationContext(), "Deliverd",
							Toast.LENGTH_LONG).show();
					((MainActivity) c).showThanksDialog(ben);
				}

			}, new IntentFilter(DELIVERED));

			/* Send SMS */
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(ben.getSms(), null, msg, sentPI, deliverPI);
		} catch (Exception ex) {
			Toast.makeText(c.getApplicationContext(),
					ex.getMessage().toString(), Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}

	}
	
	
}
