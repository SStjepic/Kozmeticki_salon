package menadzeri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalTime;

import main.KozmetickiSalon;


public class MenadzerKozmetickogSalona {
	private MenadzerKozmetickiTretmani menadzerTretmana;
	private MenadzerOsoba menadzerOsoba;
	private MenadzerZakazivanja menadzerZakazivanja;
	
	private KozmetickiSalon ks;
	
	private String fajlOsobe;
	private String fajlTretmani;
	private String fajlZakazaniTretmani;
	private String fajlTipoviKozmetickihTretmana;
	private String fajlID;
	
	private int idTipaTretmana;
	private int idZakazanogTretmana;
	private int idKozmetickogTretmana;

	
	
	
	public void setIdTipaTretmana(int idTipaTretmana) {
		this.idTipaTretmana = idTipaTretmana;
	}
	public void setIdZakazanogTretmana(int idZakazanogTretmana) {
		this.idZakazanogTretmana = idZakazanogTretmana;
	}
	public void setIdKozmetickogTretmana(int idKozmetickogTretmana) {
		this.idKozmetickogTretmana = idKozmetickogTretmana;
	}
	public String getFajlID() {
		return fajlID;
	}
	public MenadzerKozmetickiTretmani getMenadzerTretmana() {
		return menadzerTretmana;
	}
	public MenadzerOsoba getMenadzerOsoba() {
		return menadzerOsoba;
	}
	public MenadzerZakazivanja getMenadzerZakazivanja() {
		return menadzerZakazivanja;
	}
	public String getFajlOsobe() {
		return fajlOsobe;
	}
	public String getFajlTretmani() {
		return fajlTretmani;
	}
	public String getFajlZakazaniTretmani() {
		return fajlZakazaniTretmani;
	}
	public KozmetickiSalon getKs() {
		return ks;
	}
	/*KONSTRUKTORI*/
	public MenadzerKozmetickogSalona() {}
	/**
	 * Konstruktor klase GlavniMenadzer
	 * Podaci koji se prosledjuju jesu putanje do fajlova u kojima se čuvaju podaci vezani za rad salona
	 * @param fajlOsobe
	 * @param fajlTretmani
	 * @param fajlZakazaniTretmani
	 * @param fajlTipoviKozmetickihTretmana
	 * @param fajlID
	 */
	public MenadzerKozmetickogSalona(String fajlOsobe, String fajlTretmani, String fajlZakazaniTretmani, String fajlTipoviKozmetickihTretmana, String fajlID, KozmetickiSalon ks) {
		this.ks = ks;
		this.fajlOsobe = fajlOsobe;
		this.fajlTretmani = fajlTretmani;
		this.fajlZakazaniTretmani = fajlZakazaniTretmani;
		this.fajlTipoviKozmetickihTretmana = fajlTipoviKozmetickihTretmana;
		this.fajlID = fajlID;
		ucitajID();
		this.menadzerOsoba = new MenadzerOsoba(this.fajlOsobe);
		this.menadzerTretmana = new MenadzerKozmetickiTretmani(this.fajlTretmani,this.fajlTipoviKozmetickihTretmana, this.idTipaTretmana, this.idKozmetickogTretmana);
		this.menadzerZakazivanja = new MenadzerZakazivanja(this.fajlZakazaniTretmani, this.idZakazanogTretmana, this.menadzerOsoba, this.menadzerTretmana, ks.getPocetakRadnogVremena(), ks.getKrajRadnogVremena());
	}
	/*METODA ZA UČITAVANJE*/
	private boolean ucitajID() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fajlID));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] podaci = linija.split(":");
				if(podaci[0].equals("idTipaTretmana")) {
					this.idTipaTretmana = Integer.parseInt(podaci[1]);
				}
				else if(podaci[0].equals("idZakazanogTretmana")) {
					this.idZakazanogTretmana = Integer.parseInt(podaci[1]);
				}
				else {
					this.idKozmetickogTretmana = Integer.parseInt(podaci[1]);
				}
			
			}
			br.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public void ucitajPodatke() {
		this.menadzerOsoba.ucitajPodatke();
		this.menadzerTretmana.ucitajPodatke();
		this.menadzerZakazivanja.ucitajPodatke();
	}
	
	/*METODA ZA UPISIVANJE*/
	public boolean upisiID() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(this.fajlID), false);
			pw.println("idTipaTretmana:"+menadzerTretmana.getidTipaTretmana());
			pw.println("idZakazanogTretmana:"+menadzerZakazivanja.getIdSledecegZakazivanja());
			pw.println("idKozmetickogTretmana:"+menadzerTretmana.getIdKozmetickogTretmana());
			
		} catch (Exception e) {
			return false;
		}
		pw.close();
		return true;
	}

	
}
