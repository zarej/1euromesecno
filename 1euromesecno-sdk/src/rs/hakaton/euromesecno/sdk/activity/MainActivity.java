package rs.hakaton.euromesecno.sdk.activity;

import java.util.ArrayList;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.adapter.BeneficiaryAdapter;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BeneficiaryServiceResponseListener;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BenficiaryService;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private static final int MENU_ITEM_SHARE = 1;
	private static final int MENU_ITEM_ABOUT = 2;
	
	public static final String EXTRA_BENS = "extra_bens";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	public void showThanksDialog(Beneficiary ben ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    View parent = inflater.inflate(R.layout.dialog_thanks, null);
	    
	    TextView name = (TextView) parent.findViewById(R.id.dialog_thanks__name_txt);
	    name.setText(ben.getIme() + " " + ben.getPrezime());
	    
	    TextView number = (TextView) parent.findViewById(R.id.dialog_thanks__nbr_txt_value);
	    number.setText(ben.getSms());
	    
	    TextView message = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt_value);
	    TextView messageTxt = (TextView) parent.findViewById(R.id.dialog_thanks__message_txt);
	    if (ben.getRedni_broj()==null || ben.getRedni_broj().trim().equals("")) {
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
	
	public void showInfoDialog(int textResource, OnClickListener positiveListener ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    View parent = inflater.inflate(R.layout.dialog_info, null);
	    
//	    TextView name = (TextView) parent.findViewById(R.id.dialog_thanks__name_txt);
//	    name.setText(ben.getIme() + " " + ben.getPrezime());
	    
	    	    
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(parent);
	    
	    if (textResource != -1) builder.setMessage(textResource);
	    
	    builder.setNegativeButton(getString(R.string.info_dialog_negative_btn), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	    builder.setPositiveButton(getString(R.string.info_dialog_positive_btn), positiveListener);
	    final Dialog dialog = builder.create();
	    
	    
	    dialog.show();
	    
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		// Create the search view
		menu.add(Menu.NONE, MENU_ITEM_SHARE, Menu.NONE, getString(R.string.share_btn))
		.setIcon(R.drawable.ic_launcher)
		.setOnMenuItemClickListener(onMenuItemclick)
		.setShowAsAction(
		MenuItem.SHOW_AS_ACTION_IF_ROOM
		| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		menu.add(Menu.NONE, MENU_ITEM_ABOUT, Menu.NONE, getString(R.string.about_btn))
		.setIcon(R.drawable.ic_launcher)
		.setOnMenuItemClickListener(onMenuItemclick)
		.setShowAsAction(
		MenuItem.SHOW_AS_ACTION_IF_ROOM
		| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		return super.onCreateOptionsMenu(menu);
		
	}
	
	MenuItem.OnMenuItemClickListener onMenuItemclick = new MenuItem.OnMenuItemClickListener() {
		
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			
			switch (item.getItemId()) {
			case MENU_ITEM_SHARE:
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						getString(R.string.share_subject));
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						getString(R.string.share_message));
				startActivity(Intent.createChooser(sharingIntent,
						getString(R.string.share_via)));
				break;
			case MENU_ITEM_ABOUT:
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			BeneficiaryServiceResponseListener {

		private ListView list;

		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			list = (ListView) rootView.findViewById(R.id.main_list);
			
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
				}
			});

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			ArrayList<Beneficiary> beneficiaries = getActivity().getIntent().getParcelableArrayListExtra(EXTRA_BENS);
			
			if(beneficiaries!=null) {
				BeneficiaryAdapter adapter = new BeneficiaryAdapter(beneficiaries,
						getActivity());
				list.setAdapter(adapter);
			} else {
				
				BenficiaryService service = new BenficiaryService();
				
				service.getBeneficiaries(getString(R.string.open_data_url_json),
						this);
			}
			
			
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onBeneficiaryListReturn(ArrayList<Beneficiary> beneficiaries) {
			// TODO Auto-generated method stub
			BeneficiaryAdapter adapter = new BeneficiaryAdapter(beneficiaries,
					getActivity());
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

	}

}
