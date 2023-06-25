package usluge;

import java.util.Objects;

public class TipKozmetickogTretmana {
	private int sifra;
	private String naziv;
	private boolean obrisan;
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getSifra() {
		return sifra;
	}
	
	public TipKozmetickogTretmana() {}
	public TipKozmetickogTretmana(boolean obrisan, int sifra,String naziv) {
		this.sifra = sifra;
		this.naziv = naziv;
		this.obrisan = obrisan;
	}
	
	@Override
	public String toString() {
		return this.naziv;
	}
	public String StringZaUpis() {
		return sifra +";"+ naziv+";"+obrisan;
	}
	
	public Boolean jeObrisan() {
		return this.obrisan;
	}
	public void obrisiTipTretmana() {
		this.obrisan = true;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipKozmetickogTretmana other = (TipKozmetickogTretmana) obj;
		return Objects.equals(naziv, other.naziv) && obrisan == other.obrisan && sifra == other.sifra;
	}
	
	
}
