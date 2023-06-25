package usluge;

import java.util.HashMap;
/**
 * Klasa Cenovnik čuva podatke o cenama pojedinačnog kozmetičkog tretmnana u formatu (idKozmetičkogTretmana/cena)
 * @author Stefan Stjepić
 *
 */
public class Cenovnik {
	private HashMap<KozmetickiTretman, Double> cenovnik;
	
	public HashMap<KozmetickiTretman, Double> getCenovnik() {
		return cenovnik;
	}
	
	
	public Cenovnik() {
		cenovnik=new HashMap<KozmetickiTretman, Double>();
	}
	
	
	public void promeniCenu(Double novaCena, KozmetickiTretman kt) {
		cenovnik.replace(kt, novaCena);
	}
	public void dodajStavkuCenovnika(KozmetickiTretman kt, double cena) {
		cenovnik.put(kt, cena);
	}
	public Double nadjiCenuKozmetickogTretmana(KozmetickiTretman kt) {
		return cenovnik.get(kt);
	}
	public HashMap<String, Double> prikaziCenovnik(){
		HashMap<String, Double> spisak = new HashMap<String, Double>();
		for(KozmetickiTretman kt: cenovnik.keySet()) {
			if(!kt.jeObrisan()) {
				spisak.put(kt.getNaziv(), cenovnik.get(kt));
			}
		}
		return spisak;
	}
}
