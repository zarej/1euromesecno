package rs.hakaton.euromesecno.sdk.adapter;

import java.util.ArrayList;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

public class BeneficiaryAdapter extends BaseAdapter {
	
	private ArrayList<Beneficiary> benificiaries;
	Context context;
	
	public BeneficiaryAdapter(ArrayList<Beneficiary> benificiaries, Context context) {
		this.benificiaries = benificiaries;
		this.context = context;
	}

	@Override
	public int getCount() {
		return benificiaries.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return benificiaries.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.main_list_item, parent, false);
		}
		
		Beneficiary benificiary = benificiaries.get(position);
		
		ImageView image = (ImageView) convertView.findViewById(R.id.main_list_item__image);
		
		ImageLoader.getInstance().displayImage(benificiary.getSlika(), image);
		
		TextView name = (TextView) convertView.findViewById(R.id.main_list_item__name);
		name.setText(benificiary.getIme() + " " + benificiary.getPrezime());
		
		Button sendSMSButton = (Button) convertView.findViewById(R.id.main_list_item__send_sms_btn);
		
		sendSMSButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Saljem poruku", Toast.LENGTH_LONG).show();
			}
		});
		
		return convertView;
	}

}
