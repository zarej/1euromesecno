package rs.hakaton.euromesecno.sdk.model;

public class Beneficiary {
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
	
	
}
