package rs.hakaton.euromesecno.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;
public class Beneficiary implements Parcelable{
	public Beneficiary(String ime, String prezime, String godine, String sms,
			String redni_broj, String slika, String info) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.godine = godine;
		this.sms = sms;
		this.redni_broj = redni_broj;
		this.slika = slika;
		this.info = info;
	}

	String ime;
	String prezime;
	String godine;
	String sms;
	String redni_broj;
	String slika;
	String info;

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getGodine() {
		return godine;
	}

	public String getSms() {
		return sms;
	}

	public String getRedni_broj() {
		return redni_broj;
	}

	public String getSlika() {
		return slika;
	}

	public String getInfo() {
		return info;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(ime);
		dest.writeString(prezime);
		dest.writeString(godine);
		dest.writeString(sms);
		dest.writeString(redni_broj);
		dest.writeString(slika);
		dest.writeString(info);
	}

	public static final Parcelable.Creator<Beneficiary> CREATOR = new Parcelable.Creator<Beneficiary>() {

		@Override
		public Beneficiary createFromParcel(Parcel source) {
			String ime = source.readString(); 
			String prezime = source.readString(); 
			String godine = source.readString(); 
			String sms = source.readString(); 
			String redni_broj = source.readString(); 
			String slika = source.readString(); 
			String info = source.readString(); 
			
			return new Beneficiary(ime, prezime, godine, sms, redni_broj, slika, info);
		}

		@Override
		public Beneficiary[] newArray(int size) {
			return new Beneficiary[size];
		}

	};

	
	public Beneficiary(Parcel in){
    	ime = in.readString();
		prezime = in.readString();
		godine = in.readString();
		sms = in.readString();
		redni_broj = in.readString();
		slika = in.readString();
		info = in.readString();
    }
	
	
}
