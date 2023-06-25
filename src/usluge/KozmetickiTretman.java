package usluge;

import java.time.LocalTime;

public class KozmetickiTretman {
	private String naziv;
	private LocalTime vremeTrajanja;
	private TipKozmetickogTretmana tipTretmana;
	private int idKozmetickogTretmana;
	private boolean obrisan;
	
	public int getidKozmetickogTretmana() {
		return idKozmetickogTretmana;
	}

	public void setidKozmetickogTretmana(int sifraKozmetickogTretmana) {
		this.idKozmetickogTretmana = sifraKozmetickogTretmana;
	}

	public TipKozmetickogTretmana getTipTretmana() {
		return tipTretmana;
	}

	public void setTipTretmana(TipKozmetickogTretmana tipTretmana) {
		this.tipTretmana = tipTretmana;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public LocalTime getVremeTrajanja() {
		return vremeTrajanja;
	}

	public void setVremeTrajanja(LocalTime vreme_trajanja) {
		this.vremeTrajanja = vreme_trajanja;
	}

	public KozmetickiTretman() {
	}

	public KozmetickiTretman(boolean obrisan, String naziv, LocalTime vremeTrajanja, TipKozmetickogTretmana tipTretmana,int idKozmetickogTretmana) {
		this.naziv = naziv;
		this.vremeTrajanja = vremeTrajanja;
		this.tipTretmana = tipTretmana;
		this.idKozmetickogTretmana = idKozmetickogTretmana;
		this.obrisan = obrisan;
	}

	@Override
	public String toString() {
		return this.naziv;
	}

	public String UpisUFajl(double cena) {
		return idKozmetickogTretmana+";" + naziv +";"+ cena +";"+ vremeTrajanja +";"+ tipTretmana.getSifra() +";"+ obrisan;
	}
	public Boolean jeObrisan() {
		return this.obrisan;
	}
	public void obrisiKozmetickiTretman() {
		this.obrisan = true;
	}
	public String stringZaPrikaz(double cena) {
		return "naziv: "+ this.naziv + " " + " cena: " + cena + " trajanje: " + this.vremeTrajanja;
	}

}
