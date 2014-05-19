package rs.hakaton.euromesecno.sdk.util;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CustomDialog {
	
	static CustomDialog instance;
	
	public static CustomDialog getInstance() {
		if (instance == null) {
			instance = new CustomDialog();
		}
		return instance;
	}
	
	public void showInfoDialog(int textResource, OnClickListener positiveListener, Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		// Get the layout inflater
		LayoutInflater inflater = a.getLayoutInflater();

		// Inflate and set the layout for the dialog
		View parent = inflater.inflate(R.layout.dialog_info, null);

		builder.setView(parent);

		if (textResource != -1)
			builder.setMessage(textResource);

		builder.setNegativeButton(a.getString(R.string.info_dialog_negative_btn),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		builder.setPositiveButton(a.getString(R.string.info_dialog_positive_btn), positiveListener);
		final Dialog dialog = builder.create();

		dialog.show();

	}
	
	public void showThanksDialog(Beneficiary ben, Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		// Get the layout inflater
		LayoutInflater inflater = a.getLayoutInflater();

		// Inflate and set the layout for the dialog
		View parent = inflater.inflate(R.layout.dialog_thanks, null);

		TextView name = (TextView) parent.findViewById(R.id.dialog_thanks__name_txt);
		name.setText(ben.getIme() + " " + ben.getPrezime());

		TextView number = (TextView) parent.findViewById(R.id.dialog_thanks__nbr_txt_value);
		number.setText(ben.getSms());

		TextView message = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt_value);
		TextView messageTxt = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt);
		if (ben.getRedni_broj() == null || ben.getRedni_broj().trim().equals("")) {
			message.setVisibility(View.GONE);
			messageTxt.setVisibility(View.GONE);
		} else {
			message.setText(ben.getRedni_broj());
		}

		// Pass null as the parent view because its going in the dialog layout
		builder.setView(parent);

		final Dialog dialog = builder.create();
		dialog.show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				dialog.dismiss();
			}
		}, 2000);
	}
}
