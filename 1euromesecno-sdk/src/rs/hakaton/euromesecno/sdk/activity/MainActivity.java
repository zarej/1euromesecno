package rs.hakaton.euromesecno.sdk.activity;

import java.util.ArrayList;
import java.util.List;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.adapter.BeneficiaryAdapter;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BeneficiaryServiceResponseListener;
import rs.hakaton.euromesecno.sdk.beneficiaryservice.BenficiaryService;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.app.ActionBar;
import android.os.Bundle;
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

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
			// setting titlebar color here, because it's too involved to do it properly for now :)
//			android.support.v7.app.ActionBar titleBar = getSupportActionBar();
//			titleBar.setBackgroundDrawable(getResources().getColor(R.color.title));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

			ArrayList<Beneficiary> benificiaries = new ArrayList<Beneficiary>();
			benificiaries
					.add(new Beneficiary(
							"Zarko",
							"Jevtic",
							"82",
							"2245",
							"2",
							"http://www.google.rs/imgres?imgurl=https%3A%2F%2Fforum.ceviz.net%2Fattachments%2Fphp%2F10863d1321713473-resim-boyutlandirma-phpthumb_generated_thumbnailjpgrererjpg&imgrefurl=https%3A%2F%2Fforum.ceviz.net%2Fphp%2F112609-resim-boyutlandirma.html&h=300&w=300&tbnid=Ue4TrwIjnMbdpM%3A&zoom=1&docid=0nAY4mKVzT5kOM&ei=q7B2U8LkDcasPOPNgPAC&tbm=isch&client=ubuntu&ved=0CFYQMygFMAU&iact=rc&uact=3&dur=883&page=1&start=0&ndsp=15",
							""));
			benificiaries
					.add(new Beneficiary(
							"Marko",
							"Kacanski",
							"86",
							"2245",
							"2",
							"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQk_9uvmqTovBFoexIgE76i9n8yqfiuFeU6Fo_M7pjLkmBuKRgIiw",
							""));

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			BenficiaryService service = new BenficiaryService();

			service.getBeneficiaries(getString(R.string.open_data_url_json),
					this);
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onBeneficiaryListReturn(List<Beneficiary> beneficiaries) {
			// TODO Auto-generated method stub
			BeneficiaryAdapter adapter = new BeneficiaryAdapter(beneficiaries,
					getActivity());
			list.setAdapter(adapter);

		}

	}

}
