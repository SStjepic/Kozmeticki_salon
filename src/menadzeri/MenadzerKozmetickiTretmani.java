package menadzeri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import korisnici.Kozmeticar;
import usluge.Cenovnik;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

public class MenadzerKozmetickiTretmani {
	private String fajlTretmani;
	private String fajlTioviTretmana;
	private ArrayList<TipKozmetickogTretmana> sviTipovi;
	private HashMap<Integer, KozmetickiTretman> sviKozmetickiTretmani;
	private Cenovnik cenovnikKozmetickihTretmana;
	private static int idTipaTretmana;
	private static int idKozmetickogTretmana;

	public int getIdKozmetickogTretmana() {
		return idKozmetickogTretmana;
	}

	public enum status{ZAKAZAN,IZVRSEN,OTKAZAO_KLIJENT, OTKAZAO_SALON, NIJE_SE_POJAVIO};

	public int getidTipaTretmana() {
		return idTipaTretmana;
	}

	public String getFajlTioviTretmana() {
		return fajlTioviTretmana;
	}

	public String getFajlTretmani() {
		return fajlTretmani;
	}
	public ArrayList<TipKozmetickogTretmana> getSviTipovi() {
		return sviTipovi;
	}

	/*KONSTRUKTORI*/
	public MenadzerKozmetickiTretmani() {}
	public MenadzerKozmetickiTretmani(String fajl, String fajlTip, int sledecaSifraTipa, int idKozmetickogTretmana) {
		this.fajlTretmani = fajl;
		this.fajlTioviTretmana = fajlTip;
		MenadzerKozmetickiTretmani.idTipaTretmana = sledecaSifraTipa;
		MenadzerKozmetickiTretmani.idKozmetickogTretmana = idKozmetickogTretmana;
		sviKozmetickiTretmani=new HashMap<Integer, KozmetickiTretman>();
		this.sviTipovi=new ArrayList<TipKozmetickogTretmana>();
		this.cenovnikKozmetickihTretmana = new Cenovnik();
	}
	
	/*METODE ZA UČITAVANJE IZ FAJLOVA*/
	private KozmetickiTretman ucitajKozmetickiTretman(String tretman) {
		String[] podaci = tretman.split(";");
		LocalTime lt = LocalTime.parse(podaci[3]);
		return new KozmetickiTretman(Boolean.parseBoolean(podaci[5]), podaci[1], lt,nadjiTipTretmana(Integer.parseInt(podaci[4])) ,Integer.parseInt(podaci[0]));
	}
	private boolean ucitajTipoveTretmana() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fajlTioviTretmana));
			String novaLinija = br.readLine();
			while (novaLinija!=null) {
				String[] podaci = novaLinija.split(";");
				TipKozmetickogTretmana tkt = new TipKozmetickogTretmana(Boolean.parseBoolean(podaci[2]), Integer.parseInt(podaci[0]), podaci[1]);
				this.sviTipovi.add(tkt);
				novaLinija= br.readLine();
			}
			br.close();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean ucitajPodatke() {
		ucitajTipoveTretmana();
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fajlTretmani));
			String novaLinija = br.readLine();
			while (novaLinija!=null) {
				KozmetickiTretman kt = ucitajKozmetickiTretman(novaLinija);
				this.cenovnikKozmetickihTretmana.dodajStavkuCenovnika(kt,Double.parseDouble(novaLinija.split(";")[2]));
				this.sviKozmetickiTretmani.put(kt.getidKozmetickogTretmana(),kt);
				novaLinija=br.readLine();
			}
			br.close();
		}catch (Exception e) {
			return false;
		}
		
		return true;
	}
	/*METODE ZA UPISIVANJE U FAJLOVE*/
	private boolean upisiTipoveTretmana() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(this.fajlTioviTretmana, false));
			for(TipKozmetickogTretmana s: sviTipovi) {
				pw.println(s.StringZaUpis());
			}
			pw.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean upisiPodatke() {
		upisiTipoveTretmana();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.fajlTretmani), false);
			for(KozmetickiTretman s: this.sviKozmetickiTretmani.values()) {
				pw.println(s.UpisUFajl(this.cenovnikKozmetickihTretmana.nadjiCenuKozmetickogTretmana(s)));
			}
			pw.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/*METODE*/
	public Cenovnik cenovnikUsluga(){
		return cenovnikKozmetickihTretmana;
	}
	public ArrayList<TipKozmetickogTretmana> sviTipoviTretmana(){
		ArrayList<TipKozmetickogTretmana> spisak = new ArrayList<TipKozmetickogTretmana>();
		for(TipKozmetickogTretmana tkt: sviTipovi) {
			if(!tkt.jeObrisan()) {
				spisak.add(tkt);
			}
		}
		return spisak;
	}
	
	public List<KozmetickiTretman> kozmetickiTretmaniZaTabelu(){
		List<KozmetickiTretman> lista = new ArrayList<KozmetickiTretman>();
		for(KozmetickiTretman kt: this.sviKozmetickiTretmani.values()) {
			if(!kt.jeObrisan()) {
				lista.add(kt);
			}
		}
		return lista;
	}
	
	public HashMap<Integer, KozmetickiTretman> KozmetickiTretmani (){
		HashMap<Integer, KozmetickiTretman> spisak = new HashMap<Integer, KozmetickiTretman>();
		for(KozmetickiTretman kt: this.sviKozmetickiTretmani.values()) {
			if(!kt.jeObrisan()) {
				spisak.put(kt.getidKozmetickogTretmana(), kt);
			}
		}
		return spisak;
	}
	public TipKozmetickogTretmana nadjiTipTretmana(int sifra) {
		for(TipKozmetickogTretmana tkt: this.sviTipovi) {
			if(tkt.getSifra() == sifra && !tkt.jeObrisan())
				return tkt;
		}
		return null;
	}

	public TipKozmetickogTretmana nadjiTipTretmana(String nazivTipaTretmana) {
		for(TipKozmetickogTretmana tkt: this.sviTipovi) {
			if(tkt.getNaziv().equals(nazivTipaTretmana))
				return tkt;
		}
		return null;
	}
	public boolean proveriJedinstvenostTipaTretmana(String nazivTipaTretmana) {
		ArrayList<TipKozmetickogTretmana> spisak = sviTipoviTretmana();
		for(TipKozmetickogTretmana tkt: spisak) {
			if(tkt.getNaziv().equals(nazivTipaTretmana))
				return false;
		}
		return true;
	}
	
	public TipKozmetickogTretmana dodajTipTretmana(String nazivTipaTretmana)  {
		TipKozmetickogTretmana tkt = new TipKozmetickogTretmana(false, idTipaTretmana, nazivTipaTretmana);
		idTipaTretmana += 1;
		sviTipovi.add(tkt);
		upisiTipoveTretmana();
		return tkt;
	}
	public void azurirajTipKozmetickogTretmana(int idTipaTretmana, String naziv) {
		TipKozmetickogTretmana tkt = nadjiTipTretmana(idTipaTretmana);
		tkt.setNaziv(naziv);
		upisiTipoveTretmana();
	}
	public void obrisiTipKozmetickogTretmana(int idTipaTretmana) {
		TipKozmetickogTretmana tkt = nadjiTipTretmana(idTipaTretmana);
		tkt.obrisiTipTretmana();
		for(KozmetickiTretman kt: KozmetickiTretmani().values()) {
			if(kt.getTipTretmana().getSifra() == idTipaTretmana) {
				obrisiKozmetickiTretman(kt.getidKozmetickogTretmana());
			}
		}
		upisiPodatke();
	}
	public boolean proveriJedinstvenostKozmetickogTretmana(String nazivKozmetickogTretmana) {
		for(KozmetickiTretman kt: KozmetickiTretmani().values()) {
			if(kt.getNaziv().equals(nazivKozmetickogTretmana))
				return false;
		}
		return true;
	}
	public KozmetickiTretman dodajKozmetickiTretman(String naziv, double cena, LocalTime vremeTrajanja, TipKozmetickogTretmana tipTretmana)  {
		KozmetickiTretman kt = new KozmetickiTretman(false, naziv, vremeTrajanja, tipTretmana, idKozmetickogTretmana);
		this.cenovnikKozmetickihTretmana.dodajStavkuCenovnika(kt, cena);
		idKozmetickogTretmana += 1;
		this.sviKozmetickiTretmani.put(kt.getidKozmetickogTretmana(), kt);
		upisiPodatke();
		return kt;
	}
	public void azurirajKozmetickiTretman(int idKozmetickoTretmana, String naziv, double cena, LocalTime vremeTrajanja, TipKozmetickogTretmana tipTretmana) {
		KozmetickiTretman kt = nadjiKozmetickiTretman(idKozmetickoTretmana);
		if(!naziv.equals(kt.getNaziv())) {
			kt.setNaziv(naziv);
		}
		if(cena != this.cenovnikKozmetickihTretmana.nadjiCenuKozmetickogTretmana(nadjiKozmetickiTretman(idKozmetickoTretmana))) {
			cenovnikKozmetickihTretmana.promeniCenu(cena, nadjiKozmetickiTretman(idKozmetickoTretmana));
		}
		if(!vremeTrajanja.equals(kt.getVremeTrajanja())) {
			kt.setVremeTrajanja(vremeTrajanja);
		}
		if(!tipTretmana.equals(kt.getTipTretmana())) {
			kt.setTipTretmana(tipTretmana);
		}
		upisiPodatke();
	}
	public void obrisiKozmetickiTretman(int idKozmetickogTretmana) {
		KozmetickiTretman kt = nadjiKozmetickiTretman(idKozmetickogTretmana);
		kt.obrisiKozmetickiTretman();
		upisiPodatke();
	}
	public KozmetickiTretman nadjiKozmetickiTretman(int idKozmetickoTretmana) {
		return KozmetickiTretmani().get(idKozmetickoTretmana);
	}
	public KozmetickiTretman nadjiKozmetickiTretman(String nazivKozmetickogTretmana) {
		for(KozmetickiTretman kt: this.sviKozmetickiTretmani.values()) {
			if(kt.getNaziv().equals(nazivKozmetickogTretmana)) {
				return kt;
			}
		}
		return null;
	}
	public ArrayList<KozmetickiTretman> odgovarajuciKozmetickiTretmani(TipKozmetickogTretmana tipTretmana) {
		ArrayList<KozmetickiTretman> lista = new ArrayList<KozmetickiTretman>();
		for(KozmetickiTretman kt: sviKozmetickiTretmani.values()) {
			if(kt.getTipTretmana().equals(tipTretmana)) {
				lista.add(kt);
			}
		}
		return lista;
	}
	
	public ArrayList<String> tipoviTretmanaKozmeticara(Kozmeticar k){
		ArrayList<String> spisak = new ArrayList<String>();
		for(TipKozmetickogTretmana tkt:sviTipovi) {
			if(k.getTretmani().contains(tkt.getSifra())) {
				spisak.add(tkt.getNaziv());
			}
		}
		return spisak;
	}

}
