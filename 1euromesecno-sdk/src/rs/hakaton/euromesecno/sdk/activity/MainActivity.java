package rs.hakaton.euromesecno.sdk.activity;

import java.util.ArrayList;
import java.util.Random;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.adapter.BeneficiaryAdapter;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BeneficiaryServiceResponseListener;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BenficiaryService;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import rs.hakaton.euromesecno.sdk.util.CustomDialog;
import rs.hakaton.euromesecno.sdk.util.SendSms;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	public static final String EXTRA_BENS = "extra_bens";
	public static final String SELECTED_LIST_ITEM = "euromesecno.selectedBeneficiary";
	private static final int MENU_ITEM_ABOUT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}

//	public void showThanksDialog(Beneficiary ben) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		// Get the layout inflater
//		LayoutInflater inflater = getLayoutInflater();
//
//		// Inflate and set the layout for the dialog
//		View parent = inflater.inflate(R.layout.dialog_thanks, null);
//
//		TextView name = (TextView) parent.findViewById(R.id.dialog_thanks__name_txt);
//		name.setText(ben.getIme() + " " + ben.getPrezime());
//
//		TextView number = (TextView) parent.findViewById(R.id.dialog_thanks__nbr_txt_value);
//		number.setText(ben.getSms());
//
//		TextView message = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt_value);
//		TextView messageTxt = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt);
//		if (ben.getRedni_broj() == null || ben.getRedni_broj().trim().equals("")) {
//			message.setVisibility(View.GONE);
//			messageTxt.setVisibility(View.GONE);
//		} else {
//			message.setText(ben.getRedni_broj());
//		}
//
//		// Pass null as the parent view because its going in the dialog layout
//		builder.setView(parent);
//
//		final Dialog dialog = builder.create();
//		dialog.show();
//
//		new Handler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				dialog.dismiss();
//			}
//		}, 2000);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.add(Menu.NONE, MENU_ITEM_ABOUT, Menu.NONE,
				getString(R.string.about_btn))
				.setIcon(R.drawable.info)
				.setOnMenuItemClickListener(onMenuItemclick)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		return super.onCreateOptionsMenu(menu);

	}
	
	MenuItem.OnMenuItemClickListener onMenuItemclick = new MenuItem.OnMenuItemClickListener() {
	
	public boolean onMenuItemClick(MenuItem item) {

		switch (item.getItemId()) {
		case MENU_ITEM_ABOUT:
			Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
			
			startActivity(aboutIntent);
			break;
		}

		return false;
	}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements BeneficiaryServiceResponseListener,
			android.view.View.OnClickListener {

		private ListView list;
		private View randomSmsButton;
		private ArrayList<Beneficiary> beneficiaries;

		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			
			list = (ListView) rootView.findViewById(R.id.main_list);
			
			View headerRoot = inflater.inflate(R.layout.main_list_header, null);
			
			View footerRoot = inflater.inflate(R.layout.main_list_footer, null);
			
//			list.addHeaderView(headerRoot.findViewById(R.id.random_sms_btn));

//			list.addFooterView(footerRoot.findViewById(R.id.footer_space));
			
			randomSmsButton = headerRoot.findViewById(R.id.random_sms_btn);
			randomSmsButton.setOnClickListener(this);
			

			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(view.getContext(), InfoActivity.class);
					intent.putExtra(MainActivity.SELECTED_LIST_ITEM,
							(Parcelable) parent.getItemAtPosition(position));
					view.getContext().startActivity(intent);
				}
			});

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			if (beneficiaries == null) {
				if (getActivity().getIntent().getParcelableArrayListExtra(
						EXTRA_BENS) != null) {
					beneficiaries = getActivity().getIntent().getParcelableArrayListExtra(
							EXTRA_BENS);
					BeneficiaryAdapter adapter = new BeneficiaryAdapter(beneficiaries, getActivity());
					list.setAdapter(adapter);
				} else {
					
					BenficiaryService service = new BenficiaryService();
					
					service.getBeneficiaries(getString(R.string.open_data_url_json), this);
				}
			}

			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onBeneficiaryListReturn(ArrayList<Beneficiary> beneficiaries) {
			// TODO Auto-generated method stub
			this.beneficiaries = beneficiaries;
			BeneficiaryAdapter adapter = new BeneficiaryAdapter(beneficiaries, getActivity());
			list.setAdapter(adapter);

		}

		@Override
		public void onBeneficiaryListReturnJson(String jsonResponse) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onBeneficiaryListReturnError(String error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onClick(View v) {
			// get list number of items and fire random in that range

			int num = list.getAdapter().getCount();
			Random rnd = new Random();
			int position = 1 + rnd.nextInt(num-1); // returns range 1-listItemsCount
			final Beneficiary benificiary = (Beneficiary) list.getAdapter().getItem(position);

			CustomDialog.getInstance().showInfoDialog(-1, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					new SendSms().send(getActivity(), benificiary);
				}
			}, getActivity());

		}

	}
	
}
