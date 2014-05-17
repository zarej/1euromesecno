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
	
	public Beneficiary(Parcel in){
    	ime = in.readString();
		prezime = in.readString();
		godine = in.readString();
		sms = in.readString();
		redni_broj = in.readString();
		slika = in.readString();
		info = in.readString();
    }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int arg1) {
		// TODO Auto-generated method stub
		out.writeString(ime);
		out.writeString(prezime);
		out.writeString(godine);
		out.writeString(sms);
		out.writeString(redni_broj);
		out.writeString(slika);
		out.writeString(info);
	}
	
	public static final Parcelable.Creator<Beneficiary> CREATOR = new Parcelable.Creator<Beneficiary>() {
        public Beneficiary createFromParcel(Parcel in) {
            return new Beneficiary(in);
        }

        public Beneficiary[] newArray(int size) {
            return new Beneficiary[size];
        }
    };
	
    
	
}
