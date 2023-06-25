package menadzeri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import korisnici.Menadzer;
import korisnici.Osoba;
import korisnici.Recepcioner;
import korisnici.Zaposleni;

public class MenadzerOsoba {
	private HashMap<String, Osoba> osobe;
	private String osobeFajl;
	public static enum sprema {niska, srednja, visoka};

	public void setOsobe(HashMap<String, Osoba> osobe) {
		this.osobe = osobe;
	}

	public String getOsobeFajl() {
		return osobeFajl;
	}


	
	/*KONSTRUKTORI*/
	public MenadzerOsoba() {
	}
	public MenadzerOsoba(String putanja) {
		this.osobe = new HashMap<String, Osoba>();
		this.osobeFajl = putanja;
	}
	
	/* METODA ZA UČITAVANJE IZ FAJLA*/
	public boolean ucitajPodatke() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(osobeFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] podaci = linija.split(";");
				if (podaci[0].equals("menadzer")) {
					Menadzer m = new Menadzer(Boolean.parseBoolean(podaci[8]), podaci[1], podaci[2], podaci[3], podaci[4], podaci[5], podaci[6],
							podaci[7], podaci[9], Integer.parseInt(podaci[10]), Double.parseDouble(podaci[11]),
							Double.parseDouble(podaci[12]));
					osobe.put(m.getKorisnickoIme(), m);

				} else if (podaci[0].equals("recepcioner")) {
					Recepcioner r = new Recepcioner(Boolean.parseBoolean(podaci[8]), podaci[1], podaci[2], podaci[3], podaci[4], podaci[5], podaci[6],
							podaci[7], podaci[9], Integer.parseInt(podaci[10]), Double.parseDouble(podaci[11]),
							Double.parseDouble(podaci[12]));
					osobe.put(r.getKorisnickoIme(), r);

				} else if (podaci[0].equals("kozmeticar")) {
					ArrayList<Integer> kozmetickiTretmani = new ArrayList<Integer>();
					for (int i = 14; i < podaci.length; i++) {
						kozmetickiTretmani.add(Integer.parseInt(podaci[i]));
					}
					Kozmeticar k = new Kozmeticar(Boolean.parseBoolean(podaci[8]), podaci[1], podaci[2], podaci[3], podaci[4], podaci[5], podaci[6],
							podaci[7], podaci[9], Integer.parseInt(podaci[10]), Double.parseDouble(podaci[11]),
							Double.parseDouble(podaci[12]), kozmetickiTretmani);
					osobe.put(k.getKorisnickoIme(), k);

				} else if (podaci[0].equals("klijent")) {
					Klijent k = new Klijent(Boolean.parseBoolean(podaci[8]),podaci[1], podaci[2], podaci[3], podaci[4], podaci[5], podaci[6], podaci[7],
							Double.parseDouble(podaci[9]), Boolean.parseBoolean(podaci[10]));
					osobe.put(k.getKorisnickoIme(), k);
				}

			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/*METODA ZA UPISIVANJE U FAJL*/
	public boolean upisiPodatke() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.osobeFajl, false));
			for (Osoba o : osobe.values()) {
				pw.println(o.stringZaUpis());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/*METODE*/
	public Double isplatiPlate() {
		double isplata = 0;
		for(Osoba o:sveOsobe().values()) {
			if(o instanceof Zaposleni) {
				Zaposleni z = (Zaposleni)o;
				isplata+=z.isplatiPlatu();
			}
		}
		return isplata;
	}
	public List<Klijent> sviKlijenti(){
		List<Klijent> spisak = new ArrayList<Klijent>();
		for(Osoba o: sveOsobe().values()) {
			if(!o.jeObrisan() && o instanceof Klijent) {
				spisak.add((Klijent)o);
			}
		}
		return spisak;
	}
	public List<Recepcioner> sviRecepcioneri(){
		List<Recepcioner> spisak = new ArrayList<Recepcioner>();
		for(Osoba o: sveOsobe().values()) {
			if(!o.jeObrisan() && o instanceof Recepcioner) {
				spisak.add((Recepcioner)o);
			}
		}
		return spisak;
	}
	public List<Kozmeticar> sviKozmeticari(){
		List<Kozmeticar> spisak = new ArrayList<Kozmeticar>();
		for(Osoba o: sveOsobe().values()) {
			if(!o.jeObrisan() && o instanceof Kozmeticar) {
				spisak.add((Kozmeticar)o);
			}
		}
		return spisak;
	}
	public List<Menadzer> sviMenadzeri(){
		List<Menadzer> spisak = new ArrayList<Menadzer>();
		for(Osoba o: sveOsobe().values()) {
			if(!o.jeObrisan() && o instanceof Menadzer) {
				spisak.add((Menadzer)o);
			}
		}
		return spisak;
	}
	
	public HashMap<String, Osoba> sveOsobe(){
		HashMap<String, Osoba> spisak = new HashMap<String, Osoba>();
		for(Osoba o: this.osobe.values()) {
			if(!o.jeObrisan()) {
				spisak.put(o.getKorisnickoIme(), o);
			}
		}
		return spisak;
	}
	public Osoba proveriLogin(String korisnickoIme, String lozinka) {
		HashMap<String, Osoba> spisak = sveOsobe();
		for (Osoba o : spisak.values()) {
			if (o.proveriLogin(korisnickoIme, lozinka)) {
				return o;
			}
		}
		return null;
	}
	public boolean jeJedinstvenoKorisnickoIme(String korisnickoIme) {
		HashMap<String, Osoba> spisak = sveOsobe();
		for(Osoba o: spisak.values()) {
			if(o.getKorisnickoIme().equals(korisnickoIme)) {
				return false;
			}
		}
		return true;
	}

	public Menadzer registrujMenadzera(String ime, String prezime, String pol, String brojTelefona, String adresaStanovanja,
			String korisnickoIme, String lozinka, String strucnaSprema,int staz, double osnova) {
		Menadzer m = new Menadzer(false, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka,
				strucnaSprema, staz, 0, osnova);
		osobe.put(korisnickoIme, m);
		upisiPodatke();
		return m;
	}
	public Kozmeticar registrujKozmeticara(String ime, String prezime, String pol, String brojTelefona,
			String adresaStanovanja, String korisnickoIme, String lozinka, String strucnaSprema,int staz,
			double osnova, ArrayList<Integer> tretmani) {
		Kozmeticar k = new Kozmeticar(false, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka,
				strucnaSprema,staz,0, osnova, tretmani);
		osobe.put(korisnickoIme, k);
		upisiPodatke();
		return k;
	}
	public Kozmeticar registrujKozmeticara(String ime, String prezime, String pol, String brojTelefona,
			String adresaStanovanja, String korisnickoIme, String lozinka, String strucnaSprema,int staz,
			double osnova) {
		ArrayList<Integer> tretmani = new ArrayList<Integer>();
		Kozmeticar k = new Kozmeticar(false, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka,
				strucnaSprema,staz, 0, osnova, tretmani);
		osobe.put(korisnickoIme, k);
		upisiPodatke();
		return k;
	}
	public Recepcioner registrujRecepcionera(String ime, String prezime, String pol,
			String brojTelefona, String adresaStanovanja, String korisnickoIme, String lozinka, String strucnaSprema,int staz, double osnova) {
		Recepcioner r = new Recepcioner(false, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka,
				strucnaSprema,staz,0, osnova);
		osobe.put(korisnickoIme, r);
		upisiPodatke();
		return r;
	}
	public void azurirajZaposlenog(String korisnickoIme,String ime, String prezime, String pol, String brojTelefona,
			String adresaStanovanja, String lozinka, String strucnaSprema, int staz, double bonus,
			double osnova) {
		Zaposleni z = (Zaposleni) osobe.get(korisnickoIme);
		if(!ime.equals(z.getIme())) {
			z.setIme(ime);
			}
		if(!prezime.equals(z.getPrezime())) {
			z.setPrezime(prezime);
		}
		if(!pol.equals(z.getPol())) {
			z.setPol(pol);
		}
		if(!brojTelefona.equals(z.getBrojTelefona())) {
			z.setBrojTelefona(brojTelefona);
		}
		if(!adresaStanovanja.equals(z.getAdresaStanovanja())) {
			z.setAdresaStanovanja(adresaStanovanja);
		}
		if(!lozinka.equals(z.getLozinka())) {
			z.setLozinka(lozinka);
		}
		if(!strucnaSprema.equals(z.getStrucnaSprema())) {
			z.setStrucnaSprema(strucnaSprema);
		}
		if(staz != z.getStaz()) {
			z.setStaz(staz);
			z.plata();
		}
		if(bonus != z.getBonus()) {
			z.setBonus(bonus);
			z.plata();
		}
		if(osnova != z.getOsnova()) {
			z.setOsnova(osnova);
			z.plata();
		}
		upisiPodatke();
	}
	public void azurirajTretmaneKozmeticara(ArrayList<Integer> noviTretmani, String korisnickoImeKozmeticara) {
		Kozmeticar k = (Kozmeticar)osobe.get(korisnickoImeKozmeticara);
		k.setTretmani(noviTretmani);
		upisiPodatke();
	}
	public void dodajTretmanKozmeticaru(int idTretmana, String korisnickoImeKozmeticara) {
		Kozmeticar k = (Kozmeticar)osobe.get(korisnickoImeKozmeticara);
		k.getTretmani().add(idTretmana);
		upisiPodatke();
	}
	public Klijent registrujKlijenta(String ime, String prezime, String pol, String brojTelefona,
			String adresaStanovanja, String korisnickoIme, String lozinka) {
		Klijent k = new Klijent(false, ime, prezime, pol, brojTelefona, adresaStanovanja, korisnickoIme, lozinka);
		osobe.put(korisnickoIme, k);
		upisiPodatke();
		return k;
	}
	public void azurirajKlijenta(String korisnickoIme,String ime, String prezime, String pol, String brojTelefona,
			String adresaStanovanja, String lozinka) {
		Klijent klijent = (Klijent) osobe.get(korisnickoIme);
		if(!ime.equals(klijent.getIme())) {
			klijent.setIme(ime);
			}
		if(!prezime.equals(klijent.getPrezime())) {
			klijent.setPrezime(prezime);
		}
		if(!pol.equals(klijent.getPol())) {
			klijent.setPol(pol);
		}
		if(!brojTelefona.equals(klijent.getBrojTelefona())) {
			klijent.setBrojTelefona(brojTelefona);
		}
		if(!adresaStanovanja.equals(klijent.getAdresaStanovanja())) {
			klijent.setAdresaStanovanja(adresaStanovanja);
		}
		if(!lozinka.equals(klijent.getLozinka())) {
			klijent.setLozinka(lozinka);
		}
		upisiPodatke();
	}
	public void obrisiOsobu(String korisnickoIme) {
		Osoba o = nadjiOsobu(korisnickoIme);
		o.obrisiOsobu();
		upisiPodatke();
	}
	public void azurirajPotrosnjuKorisnika(String korisnickoIme, double suma) {
		Klijent k = (Klijent) this.osobe.get(korisnickoIme);
		k.azurirajPotrosnju(suma);
	}
	public Osoba nadjiOsobu(String korisnickoIme) {
		HashMap<String, Osoba> spisak = sveOsobe();
		if(spisak.containsKey(korisnickoIme)) {
			return spisak.get(korisnickoIme);
			}
		else {
			return null;
		}
	}
	public HashMap<String, Kozmeticar> obuceniKozmeticari(int idTipaTretmana){
		HashMap<String, Kozmeticar> sviObuceniKozmeticari = new HashMap<String, Kozmeticar>();
		for(Osoba o: osobe.values()) {
			if(o instanceof Kozmeticar) {
				Kozmeticar k = (Kozmeticar) o;
				if(k.jeObucenZaTipTretmana(idTipaTretmana)) {
					sviObuceniKozmeticari.put(k.getKorisnickoIme(), k);
				}
			}
		}
		return sviObuceniKozmeticari;
	}

	public void dodeliBonus(int brojObavljenihTretmana, int granica, Kozmeticar k,	double bonus) {
		if(brojObavljenihTretmana>=granica) {
			k.setBonus(bonus);
		}
	}
	public void dodeliBonus(double prihodKozmeticara, double granica, Kozmeticar k,	double bonus) {
		if(prihodKozmeticara>=granica) {
			k.setBonus(bonus);
		}
	}
	public void dodeliKarticuLojalnosti(double prag) {
		for(Osoba o: osobe.values()) {
			if(o instanceof Klijent) {
				Klijent k = (Klijent) o;
				if(k.getPotrosio()>=prag) {
					k.postaviKarticuLojalnosti(true);
				}
				else {
					k.postaviKarticuLojalnosti(false);
				}
			}
		}
		upisiPodatke();
	}
}
