package rs.hakaton.euromesecno.sdk.activity;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import rs.hakaton.euromesecno.sdk.util.CustomDialog;
import rs.hakaton.euromesecno.sdk.util.SendSms;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class InfoActivity extends ActionBarActivity {
	
	private static final int MENU_ITEM_SHARE = 1;
	private static final int MENU_ITEM_ABOUT = 2;
	
	PlaceholderFragment listFragment = new PlaceholderFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.info_container, listFragment).commit();
		}
		
		
	}
	
	protected void displayBeneficiary(Beneficiary beneficiary){
		TextView txt = (TextView) findViewById(R.id.info_text);
		txt.setText(beneficiary.getPrezime());
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		// Create the search view
		menu.add(Menu.NONE, MENU_ITEM_SHARE, Menu.NONE,
				getString(R.string.share_btn))
				.setIcon(R.drawable.share)
				.setOnMenuItemClickListener(onMenuItemclick)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

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

		@Override
		public boolean onMenuItemClick(MenuItem item) {

			switch (item.getItemId()) {
			case MENU_ITEM_SHARE:
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				
				String subject = "";
				
				Beneficiary ben = listFragment.getBeneficiary();
				
				if (TextUtils.isEmpty(ben.getRedni_broj())) {
					subject = String.format(getString(R.string.share_subject), 
							ben.getIme() + " " +
							ben.getPrezime(), 
							ben.getSms());
				} else {
					subject = String.format(getString(R.string.share_subject_2), 
							ben.getIme() + " " +
							ben.getPrezime(), 
							ben.getSms(), 
							ben.getRedni_broj());
				}
				
				String message = String.format(getString(R.string.share_message), Html.fromHtml(ben.getInfo()).toString());
				
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						subject);
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, subject + "\n\n" + message);
				startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
				break;
			case MENU_ITEM_ABOUT:
				Intent aboutIntent = new Intent(InfoActivity.this, AboutActivity.class);
				
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
		int id = item.getItemId();
		if (id == android.R.id.home) {
//			 NavUtils.navigateUpFromSameTask(this);
			finish();
	            return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Beneficiary beneficiary;

		public PlaceholderFragment() {
		}
		
		public Beneficiary getBeneficiary() {
			return beneficiary;
		}
		
		private void setText(View rootView,Object id, Object txt){
			TextView textView = (TextView) rootView.findViewById( (Integer) id);
			textView.setText((String) txt);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_info, container,
					false);
			
			Intent i = getActivity().getIntent();
			
			beneficiary = (Beneficiary) i.getParcelableExtra(MainActivity.SELECTED_LIST_ITEM);
			
			ImageView img = (ImageView) rootView.findViewById(R.id.main_list_item__image);
			
			ImageLoader.getInstance().displayImage(beneficiary.getSlika(), img);
			
			TextView txt = (TextView)rootView.findViewById(R.id.main_list_item__name);
			txt.setText(beneficiary.getIme()+"\n"+beneficiary.getPrezime());
			
//			txt = (TextView)rootView.findViewById(R.id.info_amount);
//			txt.setText("300 000 €");
			
			Button sendSmsButton = (Button) rootView.findViewById(R.id.info__send_sms_btn);
			Button sendSmsButtonBottom = (Button) rootView.findViewById(R.id.info__send_sms_btn_bottom);
			
			sendSmsButton.setOnClickListener(sendSmsListener);
			sendSmsButtonBottom.setOnClickListener(sendSmsListener);
			
//			String infoSmsText = TextUtils.isEmpty(beneficiary.getRedni_broj())? "" : beneficiary.getRedni_broj();
			
//			HashMap<Integer, String> benHm = new HashMap<Integer, String>();
//			benHm.put(R.id.info_amount, beneficiary.getCena_lecenja());
//			benHm.put(R.id.info_problem, beneficiary.getBolest());
//			benHm.put(R.id.info_sms_number, beneficiary.getSms());
//			benHm.put(R.id.info_sms_text, beneficiary.getRedni_broj());
//			benHm.put(R.id.info_text, Html.fromHtml(beneficiary.getInfo()).toString());
//			benHm.put(R.id.info_domestic_account, beneficiary.getBroj_racuna_domaci());
//			benHm.put(R.id.info_domestic_bank, beneficiary.getDomaca_banka());
//			benHm.put(R.id.info_domestic_account_holder, beneficiary.getBroj_racuna_domaci_ime());
//			benHm.put(R.id.info_foreign_bank_account, beneficiary.getStrana_banka());
//			benHm.put(R.id.info_foreign_bank_swift, beneficiary.getStrana_banka_swift());
//			benHm.put(R.id.info_foreign_bank_iban, beneficiary.getStrana_banka_iban());
//			benHm.put(R.id.info_email, beneficiary.getEmail());
//			benHm.put(R.id.info_phone, beneficiary.getTelefon());
//			benHm.put(R.id.info_site, beneficiary.getSajt());
			
//			Iterator<Map.Entry<Integer, String>> it =  benHm.entrySet().iterator();
//			
//			while (it.hasNext()) {
//				Map.Entry<Integer, String> me= (Map.Entry<Integer, String>) it.next();
//				Log.d("iteracija", "value=" + me.getValue() + " key=" + me.getKey());
//				setText(rootView, me.getKey(), me.getValue());
//			}
			
			
			setInfoSection(rootView, beneficiary);
			setDescriptionSection(rootView, beneficiary);
			setBankSection(rootView, beneficiary);
			setContactSection(rootView, beneficiary);
			
			
			return rootView;
		}
		OnClickListener sendSmsListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialog.getInstance().showInfoDialog(-1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new SendSms().send(getActivity(), beneficiary);
					}
				}, getActivity());
				
			}
		};
		private void setInfoSection(View parent, Beneficiary ben) {
			
			boolean allFieldsAreEmpty = true;
			
			//Potrebno
			if (TextUtils.isEmpty(ben.getCena_lecenja())) {
				parent.findViewById(R.id.info_amount_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_amount).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_amount, ben.getCena_lecenja());
				allFieldsAreEmpty = false;
			}
			
			//Boluje od
			if (TextUtils.isEmpty(ben.getBolest())) {
				parent.findViewById(R.id.info_problem_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_problem).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_problem, ben.getBolest());
				allFieldsAreEmpty = false;
			}
			
			//SMS Broj
			if (TextUtils.isEmpty(ben.getSms())) {
				parent.findViewById(R.id.info_sms_number_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_sms_number).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_sms_number, ben.getSms());
				allFieldsAreEmpty = false;
			}
			
			//Set text
			if (TextUtils.isEmpty(ben.getRedni_broj())) {
				parent.findViewById(R.id.info_sms_text_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_sms_text).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_sms_text, ben.getRedni_broj());
				allFieldsAreEmpty = false;
			}
			
			if(allFieldsAreEmpty) {
				//Hide separator
				parent.findViewById(R.id.separator_info).setVisibility(View.GONE);
			}
			
			
		}
		
		private void setDescriptionSection(View parent, Beneficiary ben) {
			
			//Opis
			if (TextUtils.isEmpty(ben.getInfo())) {
				parent.findViewById(R.id.separator_desc).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_text, Html.fromHtml(beneficiary.getInfo()).toString());
			}
		}
		
		private void setBankSection(View parent, Beneficiary ben) {
			
			boolean domesticBankFieldsAreEmpty = true;
			
			//Broj domaceg racuna
			if (TextUtils.isEmpty(ben.getBroj_racuna_domaci())) {
				parent.findViewById(R.id.info_domestic_account).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_domestic_account, ben.getBroj_racuna_domaci());
				domesticBankFieldsAreEmpty = false;
			}
			
			//Ime domace banke
			if (TextUtils.isEmpty(ben.getDomaca_banka())) {
				parent.findViewById(R.id.info_domestic_bank).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_domestic_bank, ben.getDomaca_banka());
				domesticBankFieldsAreEmpty = false;
			}
			
			//Na ime
			if (TextUtils.isEmpty(ben.getBroj_racuna_domaci_ime())) {
				parent.findViewById(R.id.info_domestic_account_holder_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_domestic_account_holder).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_domestic_account_holder, ben.getBroj_racuna_domaci_ime());
				domesticBankFieldsAreEmpty = false;
			}
			
			if (domesticBankFieldsAreEmpty){
				parent.findViewById(R.id.info_domestic_account_label).setVisibility(View.GONE);
			}
			
			boolean foreignBankFieldsAreEmpty = true;
			
			//Ime strane banke
			if (TextUtils.isEmpty(ben.getStrana_banka())) {
				parent.findViewById(R.id.info_foreign_bank_account).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_foreign_bank_account, ben.getStrana_banka());
				foreignBankFieldsAreEmpty = false;
			}
			
			//Swift
			if (TextUtils.isEmpty(ben.getStrana_banka_swift())) {
				parent.findViewById(R.id.info_foreign_bank_swift_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_foreign_bank_swift).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_foreign_bank_swift, ben.getStrana_banka_swift());
				foreignBankFieldsAreEmpty = false;
			}
			
			//Iban
			if (TextUtils.isEmpty(ben.getStrana_banka_iban())) {
				parent.findViewById(R.id.info_foreign_bank_iban_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_foreign_bank_iban).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_foreign_bank_iban, ben.getStrana_banka_iban());
				foreignBankFieldsAreEmpty = false;
			}
			
			if (foreignBankFieldsAreEmpty) {
				parent.findViewById(R.id.info_foreign_bank_account_label).setVisibility(View.GONE);
			}
			
			if (foreignBankFieldsAreEmpty && domesticBankFieldsAreEmpty) {
				parent.findViewById(R.id.separator_bank).setVisibility(View.GONE);
			}
			
		}
		
		private void setContactSection(View parent, Beneficiary ben) {
			
			boolean contactFieldsAreEmpty = true;
			
			//Ime strane banke
			if (TextUtils.isEmpty(ben.getEmail())) {
				parent.findViewById(R.id.info_email_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_email).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_email, ben.getEmail());
				contactFieldsAreEmpty = false;
			}
			
			if (TextUtils.isEmpty(ben.getTelefon())) {
				parent.findViewById(R.id.info_phone_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_phone).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_phone, ben.getTelefon());
				contactFieldsAreEmpty = false;
			}
			
			if (TextUtils.isEmpty(ben.getSajt())) {
				parent.findViewById(R.id.info_site_label).setVisibility(View.GONE);
				parent.findViewById(R.id.info_site).setVisibility(View.GONE);
			} else {
				setText(parent, R.id.info_site, ben.getSajt());
				contactFieldsAreEmpty = false;
			}
			
			if (contactFieldsAreEmpty) {
				parent.findViewById(R.id.info_contact_label).setVisibility(View.GONE);
				parent.findViewById(R.id.separator_bank).setVisibility(View.GONE);
			}
			
		}
	}
	

}
