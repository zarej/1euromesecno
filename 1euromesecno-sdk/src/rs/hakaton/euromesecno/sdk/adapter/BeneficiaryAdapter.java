package rs.hakaton.euromesecno.sdk.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rs.hakaton.euromesecno.sdk.R;
import rs.hakaton.euromesecno.sdk.SendSms;
import rs.hakaton.euromesecno.sdk.activity.MainActivity;
import rs.hakaton.euromesecno.sdk.model.Beneficiary;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class BeneficiaryAdapter extends BaseAdapter {
	
	private List<Beneficiary> benificiaries = new ArrayList<Beneficiary>();
	Context context;
	private static final String TAG_HEADER_ITEM = "header_item";
	private static final String TAG_NORMAL_ITEM = "normal_item";
	
	public BeneficiaryAdapter(List<Beneficiary> benificiaries, Context context) {
		//pos 0 is header
		this.benificiaries.add(new Beneficiary(null, null, null, null, null, null, null));
		this.benificiaries.addAll(benificiaries);
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
		
		Log.d("pos", "Position = " + position);
		
		//for header only
		if (position == 0) {
			if (convertView == null  || (convertView != null && convertView.getTag().equals(TAG_NORMAL_ITEM)) ) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.main_list_header, parent, false);
				convertView.setTag(TAG_HEADER_ITEM);
			}
			
			convertView.findViewById(R.id.random_sms_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int num = getCount();
					Random rnd = new Random();
					int position = 1 + rnd.nextInt(num-1); // returns range 1-listItemsCount
					final Beneficiary benificiaryRandom = benificiaries.get(position);
					((MainActivity) context).showInfoDialog(-1, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							new SendSms().send(context, benificiaryRandom);
						}
					});
				}
			});
			
			
		} else {
			if (convertView == null || (convertView != null && convertView.getTag().equals(TAG_HEADER_ITEM)) ) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.main_list_item, parent, false);
				convertView.setTag(TAG_NORMAL_ITEM);
			}
			
			final Beneficiary benificiary = benificiaries.get(position);
			ImageView image = (ImageView) convertView.findViewById(R.id.main_list_item__image);
			
			ImageLoader.getInstance().displayImage(benificiary.getSlika(), image);
			
			TextView name = (TextView) convertView.findViewById(R.id.main_list_item__name);
			name.setText(benificiary.getIme() + "\n" + benificiary.getPrezime());
			Button sendSMSButton = (Button) convertView.findViewById(R.id.main_list_item__send_sms_btn);
			
			sendSMSButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((MainActivity) context).showInfoDialog(-1, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							new SendSms().send(context, benificiary);
						}
					});
				}
			});
		}
		
		
		return convertView;
	}

}
