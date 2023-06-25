package korisnici;

import java.util.ArrayList;

public class Kozmeticar extends Zaposleni {

	private ArrayList<Integer> tretmani = new ArrayList<Integer>();

	public ArrayList<Integer> getTretmani() {
		return tretmani;
	}

	public void setTretmani(ArrayList<Integer> tretmani) {
		this.tretmani = tretmani;
	}

	public void setTretmani(int tretman) {
		this.tretmani.add(tretman);
	}

	public Kozmeticar() {
	}

	public Kozmeticar(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema, double osnova,
			ArrayList<Integer> tretmani) {
		super(obrisan, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, strucnaSprema, 0, 0,
				osnova);
		this.tretmani = tretmani;
	}
	
	public Kozmeticar(boolean obrisan, String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema,int staz, double bonus, double osnova,
			ArrayList<Integer> tretmani) {
		super(obrisan, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka, strucnaSprema, staz, bonus,
				osnova);
		this.tretmani = tretmani;
	}
	
	/*METODE*/
	private String stringTretmana() {
		String tretman="";
		for(int s: tretmani) {
			tretman+=s+";";
		}
		return tretman;
	}

	@Override
	public String toString() {
		return this.ime +" "+this.prezime;
	}

	public String stringZaUpis() {
		return "kozmeticar" + ";" + super.stringZaUpis()+";"+stringTretmana();
	}
	public boolean jeObucenZaTipTretmana(int idTipaTretmana) {
		return tretmani.contains(idTipaTretmana);
	}
	public String stringZaPrikaz() {
		return this.ime + " " + this.prezime;
	}

}
