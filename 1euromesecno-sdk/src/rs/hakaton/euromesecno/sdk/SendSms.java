package rs.hakaton.euromesecno.sdk;

import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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
					c.getApplicationContext(), 0, sentIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);

			Intent deliveryIntent = new Intent(DELIVERED);

			PendingIntent deliverPI = PendingIntent.getBroadcast(
					c.getApplicationContext(), 0, deliveryIntent,
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
					showThanksDialog(c, ben);
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
	
	private void showThanksDialog(Context c, Beneficiary ben ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c.getApplicationContext());
	    // Get the layout inflater
	    LayoutInflater inflater = ((Activity) c).getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    View parent = inflater.inflate(R.layout.dialog_thanks, null);
	    
	    TextView name = (TextView) parent.findViewById(R.id.dialog_thanks__name_txt);
	    name.setText(ben.getIme() + " " + ben.getPrezime());
	    
	    TextView number = (TextView) parent.findViewById(R.id.dialog_thanks__nbr_txt_value);
	    number.setText(ben.getSms());
	    
	    TextView message = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt_value);
	    TextView messageTxt = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt_value);
	    if (ben.getRedni_broj()==null || ben.getRedni_broj().equals("")) {
	    	message.setVisibility(View.GONE);
	    	messageTxt.setVisibility(View.GONE);
	    } else {
	    	message.setText(ben.getRedni_broj());
	    }
	    
	    
	    
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(parent);
	    
	    builder.create().show();;

	}
}
