package main;

import java.time.LocalTime;


public class KozmetickiSalon {
	private String nazivKozmetickogSalona;
	private LocalTime pocetakRadnogVremena;
	private LocalTime krajRadnogVremena;
	
	public String getNazivKozmetickogSalona() {
		return nazivKozmetickogSalona;
	}
	public LocalTime getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}
	public LocalTime getKrajRadnogVremena() {
		return krajRadnogVremena;
	}
	
	public KozmetickiSalon() {}
	public KozmetickiSalon(String nazivKozmetickogSalona, LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena) {
		this.nazivKozmetickogSalona = nazivKozmetickogSalona;
		this.pocetakRadnogVremena = pocetakRadnogVremena;
		this.krajRadnogVremena = krajRadnogVremena;
	}
	/*METODE*/
	public void promeniNazivKozmetickogSalona(String noviNaziv) {
		this.nazivKozmetickogSalona = noviNaziv;
	}
	
	public void promeniPocetakRadnogVremena(LocalTime novoVremePocetka) {
		this.pocetakRadnogVremena = novoVremePocetka;
	}
	
	public void promeniKrajRadnogVremena(LocalTime novoVremeKraja) {
		this.krajRadnogVremena = novoVremeKraja;
	}

}
