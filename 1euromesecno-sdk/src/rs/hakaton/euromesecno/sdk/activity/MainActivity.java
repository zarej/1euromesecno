package rs.hakaton.euromesecno.sdk.activity;

import java.util.ArrayList;
import java.util.HashMap;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.adapter.BeneficiaryAdapter;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import rs.hakaton.euromesecno.sdk.model.Settings;
import rs.hakaton.euromesecno.sdk.webservice.OnSettingsResponseListener;
import rs.hakaton.euromesecno.sdk.webservice.WebService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
    public static class PlaceholderFragment extends Fragment implements OnSettingsResponseListener {

        public PlaceholderFragment() {
        	
        	WebService webService = new WebService();
        	HashMap<String, String> pairs = new HashMap<String, String>();
    		pairs.put("token", "vrednost");
			webService.getSettings("", pairs, this);
        	
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListView list = (ListView) rootView.findViewById(R.id.main_list);
            
            ArrayList<Beneficiary> benificiaries = new ArrayList<Beneficiary>();
//            benificiaries.add(new )
            
//            BeneficiaryAdapter adapter = new BeneficiaryAdapter(benificiaries, getActivity());
            
            
            return rootView;
        }

		@Override
		public void onGetSettingsRespondSuccess(Settings settings) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSettingsRespondError(String error, String response) {
			// TODO Auto-generated method stub
			
		}
    }

}
