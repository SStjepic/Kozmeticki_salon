package korisnici;

public class Zaposleni extends Osoba{
	
	protected String strucnaSprema; 
	protected int staz;
	protected double bonus;
	protected double osnova;
	private double koeficijent;
	private double plata;
	
	public double getKoeficijent() {
		return koeficijent;
	}
	public double getPlata() {
		return plata;
	}
	public String getStrucnaSprema() {
		return strucnaSprema;
	}
	public void setStrucnaSprema(String sprema) {
		this.strucnaSprema = sprema;
		this.koeficijent=odredjivanjeKoeficijenta();
	}
	public int getStaz() {
		return staz;
	}
	public void setStaz(int staz) {
		this.staz = staz;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
		this.plata +=bonus;
	}
	
	public void setOsnova(double osnova) {
		this.osnova = osnova;
	}
	
	public double getOsnova() {
		return osnova;
	}
	public Zaposleni() {}
	/**
	 * Konstruktor sa parametrima klase Zaposleni
	 * @param ime
	 * @param prezime
	 * @param pol
	 * @param brojTelefona
	 * @param adresaStanovanja
	 * @param korisnickoIme
	 * @param lozinka
	 * @param strucnaSprema
	 * @param staz
	 */
	public Zaposleni(boolean obrisan,String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema, int staz, double bonus, double osnova) {
		super(ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, obrisan);
		this.strucnaSprema = strucnaSprema;
		this.koeficijent=odredjivanjeKoeficijenta();
		this.staz = staz;
		this.bonus = bonus;
		this.osnova = osnova;
		this.plata = plata();
	}

	/**
	 * Metoda koja odredjuje visinu plate zaposlenog
	 * @return vrednost plate zaposlenog
	 */
	public double plata() {
		if(koeficijent*osnova*(1+staz*0.15) + bonus<50000) {
			return 50000;
		}
		else{
			return koeficijent*osnova*(1+staz*0.15) + bonus;
		}
	}
	
	public double odredjivanjeKoeficijenta() {
		if(this.strucnaSprema.equals("niska")) {
			return 0.4;
		}
		else if(this.strucnaSprema.equals("srednja")){
			return 0.7;
		}
		else {
			return 1;
		}
	}
	
	public String stringZaUpis() {
		return super.stringZaUpis()+";"+this.strucnaSprema+";"+this.staz+";"+this.bonus+";"+this.osnova+";"+this.plata;
	}
	
	public Double isplatiPlatu() {
		double isplata = plata;
		this.bonus = 0;
		this.plata = plata();
		return isplata;
	}
	

}
