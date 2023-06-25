package korisnici;


public class Osoba{
	
	protected String ime;
	protected String prezime;
	protected String pol;
	protected String brojTelefona;
	protected String adresaStanovanja;
	protected String korisnickoIme;
	protected String lozinka;
	protected boolean obrisan;
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojeTelefona) {
		this.brojTelefona = brojeTelefona;
	}
	public String getAdresaStanovanja() {
		return adresaStanovanja;
	}
	public void setAdresaStanovanja(String adresaStanovanja) {
		this.adresaStanovanja = adresaStanovanja;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public Osoba() {}
	public Osoba(String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja, String korisnickoIme,String lozinka, boolean obrisan) {
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.brojTelefona = brojTelefona;
		this.adresaStanovanja = adresaStanovanja;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.obrisan = obrisan;
	}
	
	public boolean proveriLogin(String korisnickoIme, String lozinka) {
		if(this.korisnickoIme.equals(korisnickoIme) && this.lozinka.equals(lozinka)) {
			return true;
		}
		return false;
	}
	
	public boolean proveriKorIme(String korisnickoIme) {
		if(this.korisnickoIme.equals(korisnickoIme)) {
			return true;
		}
		return false;
	}
	public String stringZaUpis() {
		return this.ime+";"+this.prezime+";"+this.pol+";"+this.brojTelefona+";"+this.adresaStanovanja+";"+this.korisnickoIme+";"+
				this.lozinka +";"+ this.obrisan;
	}
	
	public void obrisiOsobu() {
		this.obrisan = true;
	}
	public Boolean jeObrisan() {
		return this.obrisan;
	}
	
}
