package rs.hakaton.euromesecno.sdk.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.nostra13.universalimageloader.core.ImageLoader;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.R.id;
import rs.hakaton.euromesecno.sdk.R.layout;
import rs.hakaton.euromesecno.sdk.R.menu;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class InfoActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		
		
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.info_container, new PlaceholderFragment()).commit();
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
		return true;
	}

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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		
		private void setText(View rootView,Object id, Object txt){
			TextView textView = (TextView) rootView.findViewById( (int) id);
			textView.setText((String) txt);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_info, container,
					false);
			
			Intent i = getActivity().getIntent();
			
			Beneficiary beneficiary = (Beneficiary) i.getParcelableExtra(MainActivity.SELECTED_LIST_ITEM);
			
			ImageView img = (ImageView) rootView.findViewById(R.id.main_list_item__image);
			
			ImageLoader.getInstance().displayImage(beneficiary.getSlika(), img);
			
			TextView txt = (TextView)rootView.findViewById(R.id.main_list_item__name);
			txt.setText(beneficiary.getIme()+"\n"+beneficiary.getPrezime());
			
//			txt = (TextView)rootView.findViewById(R.id.info_amount);
//			txt.setText("300 000 €");
			
			
			
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			
			hm.put("200€", R.id.info_amount);
			hm.put("Teške Bolesti", R.id.info_problem);
			hm.put(beneficiary.getSms(), R.id.info_sms_number);
			hm.put(beneficiary.getRedni_broj(), R.id.info_sms_text);
			hm.put(beneficiary.getInfo(), R.id.info_text);
			hm.put("123456789", R.id.info_domestic_account);
			hm.put("Vojvođanska Banka", R.id.info_domestic_bank);
			hm.put("Pera Perić", R.id.info_domestic_account_holder);
			hm.put("MorganChase LLC", R.id.info_foreign_bank_account);
			hm.put("123456789", R.id.info_foreign_bank_swift);
			hm.put("379372891739732937918793", R.id.info_foreign_bank_iban);
			hm.put("pera@peric.rs", R.id.info_email);
			hm.put("065-115-123", R.id.info_phone);
			hm.put("www.pera.rs", R.id.info_text);
			
			Iterator it =  hm.entrySet().iterator();
			
			while (it.hasNext()) {
				Map.Entry me= (Map.Entry) it.next();
				
				setText(rootView, me.getValue(), me.getKey());
				
			}
			
			return rootView;
		}
	}

}
