package usluge;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import menadzeri.MenadzerKozmetickiTretmani;

public class ZakazanTretman {
	
	private int sifraZakazanogTretmana;
	private Klijent klijent;
	private Kozmeticar kozmeticar;
	private KozmetickiTretman vrstaTretmana;
	private double cena;
	private LocalDateTime termin;
	private String statusTretmana;


	
	public int getSifraZakazanogTretmana() {
		return sifraZakazanogTretmana;
	}
	public void setSifraZakazanogTretmana(int sifraZakazanogTretmana) {
		this.sifraZakazanogTretmana = sifraZakazanogTretmana;
	}
	public KozmetickiTretman getVrstaTretmana() {
		return vrstaTretmana;
	}
	public void setVrstaTretmana(KozmetickiTretman vrstaTretmana) {
		this.vrstaTretmana = vrstaTretmana;
	}
	public String getStatusTretmana() {
		return statusTretmana;
	}
	public void setStatusTretmana(String status_tretmana) {
		this.statusTretmana = status_tretmana;
	}
	public String terminZaTabelu() {
		DateTimeFormatter formatTermina = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String terminString = termin.format(formatTermina);
		return terminString;
	}
	public LocalDateTime getTermin() {
		return termin;
	}
	public void setTermin(LocalDateTime termin) {
		this.termin = termin;
	}
	public Klijent getKlijent() {
		return klijent;
	}
	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}
	public Kozmeticar getKozmeticar() {
		return kozmeticar;
	}
	public void setKozmeticar(Kozmeticar kozmeticar) {
		this.kozmeticar = kozmeticar;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public ZakazanTretman() {}
	public ZakazanTretman(Klijent klijent, Kozmeticar kozmeticar, double cena, LocalDateTime termin,
			String statusTretmana, KozmetickiTretman vrstaTretmana,int sifraZakazanogTretmana) {
		super();
		this.klijent = klijent;
		this.kozmeticar = kozmeticar;
		this.cena = cena;
		this.termin = termin;
		this.statusTretmana = statusTretmana;
		this.vrstaTretmana = vrstaTretmana;
		this.statusTretmana = statusTretmana;
		this.sifraZakazanogTretmana = sifraZakazanogTretmana;
	}
	
	public String StringUpisUFajl() {
		DateTimeFormatter formatTermina = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String terminString = termin.format(formatTermina);
		return this.sifraZakazanogTretmana +";"+ this.klijent.getKorisnickoIme() +";"+ this.kozmeticar.getKorisnickoIme() +";"+ this.vrstaTretmana.getidKozmetickogTretmana()
			+";"+ this.cena +";"+ terminString +";"+ this.statusTretmana;
	}
	
	@Override
	public String toString() {
//		DateTimeFormatter formatTermina = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		String terminString = termin.format(formatTermina);
//		return "ZakazanTretman --> Klijent=[" +klijent.getIme() +" "+klijent.getPrezime() +"] Kozmeticar=["+kozmeticar.getIme()+" "+kozmeticar.getPrezime() +"] Kozmeticki tretman=[" +vrstaTretmana.getNaziv() + "] Termin=["+terminString +"] Cena=["+cena  +"] Status=["+statusTretmana+"]";
		return this.getVrstaTretmana().getNaziv();
	}
	
	
	
}
