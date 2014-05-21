package rs.hakaton.euromesecno.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

// implements parceable because we need to fling it across activities
public class Beneficiary implements Parcelable{
//	public Beneficiary(String ime, String prezime, String godine, String sms,
//			String redni_broj, String slika, String info) {
//		super();
//		this.ime = ime;
//		this.prezime = prezime;
//		this.godine = godine;
//		this.sms = sms;
//		this.redni_broj = redni_broj;
//		this.slika = slika;
//		this.info = info;
//	}

	public Beneficiary(String ime, String prezime, String godine, String sms,
			String redni_broj, String slika, String info, String bolest,
			String cena_lecenja, String cena_sms, String domaca_banka,
			String broj_racuna_domaci, String broj_racuna_domaci_ime,
			String strana_banka, String strana_banka_swift,
			String strana_banka_iban, String email, String telefon, String sajt) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.godine = godine;
		this.sms = sms;
		this.redni_broj = redni_broj;
		this.slika = slika;
		this.info = info;
		this.bolest = bolest;
		this.cena_lecenja = cena_lecenja;
		this.cena_sms = cena_sms;
		this.domaca_banka = domaca_banka;
		this.broj_racuna_domaci = broj_racuna_domaci;
		this.broj_racuna_domaci_ime = broj_racuna_domaci_ime;
		this.strana_banka = strana_banka;
		this.strana_banka_swift = strana_banka_swift;
		this.strana_banka_iban = strana_banka_iban;
		this.email = email;
		this.telefon = telefon;
		this.sajt = sajt;
	}

	String ime;
	String prezime;
	String godine;
	String sms;
	String redni_broj;
	String slika;
	String info = "Nema informacija joč uvek";
	
	//Defaults for not yet implemented fields
	String bolest = "Nije navedeno";
	String cena_lecenja = "Nije navedeno";
	String cena_sms = "Nije navedeno";
	String domaca_banka = "Nije navedeno";
	String broj_racuna_domaci = "Nije navedeno";
	String broj_racuna_domaci_ime = "Nije navedeno";
	String strana_banka = "Nije navedeno";
	String strana_banka_swift = "Nije navedeno";
	String strana_banka_iban = "Nije navedeno";
	String email = "Nije navedeno";
	String telefon = "Nije navedeno";
	String sajt = "Nije navedeno";

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

	public String getBolest() {
		return bolest;
	}

	public String getCena_lecenja() {
		return cena_lecenja;
	}

	public String getCena_sms() {
		return cena_sms;
	}

	public String getDomaca_banka() {
		return domaca_banka;
	}

	public String getBroj_racuna_domaci() {
		return broj_racuna_domaci;
	}

	public String getBroj_racuna_domaci_ime() {
		return broj_racuna_domaci_ime;
	}

	public String getStrana_banka() {
		return strana_banka;
	}

	public String getStrana_banka_swift() {
		return strana_banka_swift;
	}

	public String getStrana_banka_iban() {
		return strana_banka_iban;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getSajt() {
		return sajt;
	}

	public static Parcelable.Creator<Beneficiary> getCreator() {
		return CREATOR;
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
		
		dest.writeString(bolest);
		dest.writeString(cena_lecenja);
		dest.writeString(cena_sms);
		dest.writeString(domaca_banka);
		dest.writeString(broj_racuna_domaci);
		dest.writeString(broj_racuna_domaci_ime);
		dest.writeString(strana_banka);
		dest.writeString(strana_banka_swift);
		dest.writeString(strana_banka_iban);
		dest.writeString(email);
		dest.writeString(telefon);
		dest.writeString(sajt);
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
			
			String bolest = source.readString();
			String cena_lecenja = source.readString();
			String cena_sms = source.readString();
			String domaca_banka = source.readString();
			String broj_racuna_domaci = source.readString();
			String broj_racuna_domaci_ime = source.readString();
			String strana_banka = source.readString();
			String strana_banka_swift = source.readString();
			String strana_banka_iban = source.readString();
			String email = source.readString();
			String telefon = source.readString();
			String sajt = source.readString();
			
			return new Beneficiary(ime, prezime, godine, sms, 
					redni_broj, slika, info, bolest, cena_lecenja, 
					cena_sms, domaca_banka, broj_racuna_domaci, 
					broj_racuna_domaci_ime, strana_banka, 
					strana_banka_swift, strana_banka_iban, email, telefon, sajt);
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
