package korisnici;

import java.util.Objects;

public class Klijent extends Osoba {

	private double potrosio;
	private boolean karticaLojalnosti;

	public double getPotrosio() {
		return potrosio;
	}

	public void setPotrosio(double potrosio) {
		this.potrosio = potrosio;
	}

	public boolean getKarticaLojalnosti() {
		return karticaLojalnosti;
	}

	public void setKarticaLojalnosti(boolean karticaLojalnosti) {
		this.karticaLojalnosti = karticaLojalnosti;
	}

	public Klijent() {
	}

	public Klijent(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka) {
		super(ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka,obrisan);
		this.potrosio = 0.0;
		this.karticaLojalnosti = false;
	}

	public Klijent(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, double potrosio, boolean karticaLojalnosti) {
		super(ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka,obrisan);
		this.potrosio = potrosio;
		this.karticaLojalnosti = karticaLojalnosti;
	}

	@Override
	public String toString() {
		return this.ime + " "+this.prezime;
	}

	public String stringZaUpis() {
		return "klijent" + ";" + super.stringZaUpis() + ";" + potrosio + ";" + karticaLojalnosti;
	}

	public void azurirajPotrosnju(double suma) {
		this.potrosio += suma;
	}
	
	public void postaviKarticuLojalnosti(boolean status) {
		this.karticaLojalnosti = status;
	}
}
