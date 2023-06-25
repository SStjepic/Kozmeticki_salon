package korisnici;

public class Recepcioner extends Zaposleni {

	public Recepcioner() {
	}

	public Recepcioner(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema, double osnova) {
		super(obrisan, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, strucnaSprema, 0, 0,
				osnova);
	}
	
	public Recepcioner(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema,int staz, double bonus, double osnova) {
		super(obrisan, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, strucnaSprema, staz, bonus,
				osnova);
	}

	@Override
	public String toString() {
		return "Recepcioner [strucnaSprema=" + strucnaSprema + ", staz=" + staz + ", bonus=" + bonus + ", osnova="
				+ osnova + ", ime=" + ime + ", prezime=" + prezime + ", pol=" + pol + ", brojeTelefona=" + brojTelefona
				+ ", adresaStanovanja=" + adresaStanovanja + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka
				+ "]";
	}

	public String stringZaUpis() {
		return "recepcioner" + ";" + super.stringZaUpis();
	}

}
