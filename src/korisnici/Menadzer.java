package korisnici;


public class Menadzer extends Zaposleni {
	
	
	public Menadzer() {}
	/**
	 * 
	 * @param ime
	 * @param prezime
	 * @param pol
	 * @param brojTelefona
	 * @param adresaStanovanja
	 * @param korisnickoIme
	 * @param lozinka
	 * @param strucnaSprema
	 * @param staz
	 * @param bonus
	 * @param osnova
	 */
	public Menadzer(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema, double osnova) {
		super(obrisan, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, strucnaSprema, 0, 0, osnova);
	}
	public Menadzer(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema,int staz,double bonus, double osnova) {
		super(obrisan, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, strucnaSprema, staz, bonus, osnova);
	}
	
	
	public String stringZaUpis() {
		return "menadzer"+";"+super.stringZaUpis();
	}
	@Override
	public String toString() {
		return "Menadzer [strucnaSprema=" + strucnaSprema + ", staz=" + staz + ", bonus=" + bonus + ", osnova=" + osnova
				+ ", ime=" + ime + ", prezime=" + prezime + ", pol=" + pol + ", brojeTelefona=" + brojTelefona
				+ ", adresaStanovanja=" + adresaStanovanja + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka
				+ "]";
	}
	
	
	
	
	
}
