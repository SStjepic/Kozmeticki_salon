package menadzeri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import korisnici.Osoba;
import korisnici.Zaposleni;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;
import usluge.ZakazanTretman;

public class MenadzerZakazivanja {
	private String fajlZakazaniTretmani;
	static int idSledecegZakazivanja;
	private MenadzerOsoba menadzerOsoblja;
	private MenadzerKozmetickiTretmani menadzerTretmana;
	private HashMap<Integer, ZakazanTretman> sviZakazaniTretmani;
	
	private LocalTime pocetakRadnogVremena;
	private LocalTime krajRadnogVremena;
	
	public MenadzerOsoba getMenadzerOsoblja() {
		return menadzerOsoblja;
	}

	public MenadzerKozmetickiTretmani getMenadzerTretmana() {
		return menadzerTretmana;
	}

	public int getIdSledecegZakazivanja() {
		return idSledecegZakazivanja;
	}

	public String getFajlZakazaniTretmani() {
		return fajlZakazaniTretmani;
	}
	
	/*KONSTRUKTORI*/
	public MenadzerZakazivanja() {
		sviZakazaniTretmani = new HashMap<Integer, ZakazanTretman>();
	}
	public MenadzerZakazivanja(String fajl, int sifraSledecegZakazivanja, MenadzerOsoba menadzerOsoblja, MenadzerKozmetickiTretmani menadzerTretmana, LocalTime pocetak, LocalTime kraj) {
		this.pocetakRadnogVremena = pocetak;
		this.krajRadnogVremena = kraj;
		this.fajlZakazaniTretmani = fajl;
		MenadzerZakazivanja.idSledecegZakazivanja = sifraSledecegZakazivanja;
		this.menadzerOsoblja = menadzerOsoblja;
		this.menadzerTretmana = menadzerTretmana;
		sviZakazaniTretmani = new HashMap<Integer, ZakazanTretman>();
	}
	
	/*METODA ZA UPISIVANJE U FAJL*/
	public boolean upisiPodatke() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.fajlZakazaniTretmani), false);
			for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
				pw.println(zt.StringUpisUFajl());
			}
			pw.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/*METODA ZA UČITAVANJE IZ FAJLA*/
	public boolean ucitajPodatke() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.fajlZakazaniTretmani));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] podaci = linija.split(";");
				Klijent k = (Klijent) menadzerOsoblja.nadjiOsobu(podaci[1]);
				Kozmeticar koz = (Kozmeticar) menadzerOsoblja.nadjiOsobu(podaci[2]);
				KozmetickiTretman kt = menadzerTretmana.nadjiKozmetickiTretman(Integer.parseInt(podaci[3]));
				DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime termin = LocalDateTime.parse(podaci[5],dtf);
				ZakazanTretman zt = new ZakazanTretman(k, koz, Double.parseDouble(podaci[4]), termin, podaci[6], kt, Integer.parseInt(podaci[0]));
				sviZakazaniTretmani.put(zt.getSifraZakazanogTretmana(), zt);
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	/*METODE*/
	public double odrediCenuKozmetickogTretmana(Klijent klijent, KozmetickiTretman kozmetickiTretman) {
		if(klijent.getKarticaLojalnosti()) {
			return menadzerTretmana.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kozmetickiTretman) * 0.9;
		}
		else {
			return menadzerTretmana.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kozmetickiTretman);
		}
	}
	
	public ZakazanTretman nadjiZakazanTretman (int idZakazanogTretmana) {
		return sviZakazaniTretmani.get(idZakazanogTretmana);
	}
	
	public ZakazanTretman nadjiZakazanTretman (LocalDateTime termin, Klijent k) {
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			if(zt.getTermin().isEqual(termin) && zt.getKlijent().getKorisnickoIme().equals(k.getKorisnickoIme())) {
				return zt;
			}
		}
		return null;
	}
	public void zakaziKozmetickiTretman(Klijent klijent, Kozmeticar kozmeticar, LocalDateTime termin, KozmetickiTretman kozmetickiTretman ) {
		double cena = odrediCenuKozmetickogTretmana(klijent, kozmetickiTretman);
		ZakazanTretman zt = new ZakazanTretman(klijent, kozmeticar, cena, termin, MenadzerKozmetickiTretmani.status.ZAKAZAN.name(), kozmetickiTretman, MenadzerZakazivanja.idSledecegZakazivanja);
		this.sviZakazaniTretmani.put(MenadzerZakazivanja.idSledecegZakazivanja, zt);
		klijent.azurirajPotrosnju(cena);
		MenadzerZakazivanja.idSledecegZakazivanja += 1;
		upisiPodatke();
		menadzerOsoblja.upisiPodatke();
	}
	public void promeniZakazaniTretman(Kozmeticar kozmeticar, LocalDateTime termin, KozmetickiTretman kozmetickiTretman, int idTretmana) {
		ZakazanTretman zt = nadjiZakazanTretman(idTretmana);
		if(!kozmeticar.equals(zt.getKozmeticar())) {
			zt.setKozmeticar(kozmeticar);
		}
		if(!termin.equals(zt.getTermin())) {
			zt.setTermin(termin);
		}
		if(!kozmetickiTretman.equals(zt.getVrstaTretmana())) {
			zt.setVrstaTretmana(kozmetickiTretman);
			zt.setCena(menadzerTretmana.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kozmetickiTretman));
		}
		
	}
	
	public void promeniStatusZakazanogTretmana(ZakazanTretman zt, String noviStatus) {
		Klijent kl = zt.getKlijent();
		zt.setStatusTretmana(noviStatus);
		if(noviStatus.equals(MenadzerKozmetickiTretmani.status.OTKAZAO_SALON.name())) {
			kl.azurirajPotrosnju((-1)*zt.getCena());
			zt.setCena(0);
		}
		if(noviStatus.equals(MenadzerKozmetickiTretmani.status.OTKAZAO_KLIJENT.name())) {
			kl.azurirajPotrosnju((-1)*zt.getCena()*0.9);
			zt.setCena(zt.getCena()*0.1);
		}
		upisiPodatke();
		menadzerOsoblja.upisiPodatke();
	}
	public HashMap<String, Kozmeticar> obuceniKozmeticariZaTretman(int idTipaTretmana){
		return menadzerOsoblja.obuceniKozmeticari(idTipaTretmana);
	}
	
	public List<ZakazanTretman> zakazaniTretmaniKlijenta(Klijent k){
		List<ZakazanTretman> lista = new ArrayList<ZakazanTretman>();
		for(ZakazanTretman zkt: this.sviZakazaniTretmani().values()) {
			if(k == null) {
				lista.add(zkt);
			}
			else if(zkt.getKlijent().equals(k)) {
				lista.add(zkt);
			}

		}
		return lista;
	}
	public boolean jeSlobodanKozmeticar(KozmetickiTretman tretman, LocalDateTime termin, String korisnickoImeKozmeticara){
		for(ZakazanTretman zt: sviZakazaniTretmani.values()) {
			LocalTime vremeTrajanjaTretmana = tretman.getVremeTrajanja();//vreme trajanja tretmana
			int sati = 0;
			if(vremeTrajanjaTretmana.getHour() > 0) {
				sati++;
			}
			if(vremeTrajanjaTretmana.getMinute() > 0) {
				sati++;
			}
			LocalDateTime vremeKrajaTretmana = zt.getTermin().plusHours(sati);//datum i vreme kraja tretmana
			if(termin.toLocalDate().isEqual(zt.getTermin().toLocalDate()) &&  zt.getKozmeticar().getKorisnickoIme().equals(korisnickoImeKozmeticara) && zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.toString())) {
				if(zt.getTermin().isEqual(termin)) {
					return false;
				}
				if(vremeKrajaTretmana.isAfter(termin) && zt.getTermin().isBefore(termin)) {
					return false;
				}
				if(termin.plusHours(sati).toLocalTime().isAfter(krajRadnogVremena)) {
					return false;
				}
				
			}
		}
		return true;
		}
	public ArrayList<Kozmeticar> sviSlobodniKozmeticari(LocalDateTime termin, KozmetickiTretman tretman){
		ArrayList<Kozmeticar> spisak = new ArrayList<Kozmeticar>();
		for(Osoba o:menadzerOsoblja.sveOsobe().values()) {
			if(o instanceof Kozmeticar) {
				Kozmeticar k = (Kozmeticar) o;
				if(k.getTretmani().contains(tretman.getTipTretmana())) {
					if(jeSlobodanKozmeticar(tretman, termin, k.getKorisnickoIme())) {
						spisak.add(k);
					}
				}
			}
		}
		return spisak;
	}
	public ArrayList<LocalTime> slobodniTerminiKozmeticara(LocalDate dan, KozmetickiTretman tretman, String korisnickoImeKozmeticara){
		ArrayList<LocalTime> sviSlobodniTermini = new ArrayList<LocalTime>();
		LocalTime vreme = pocetakRadnogVremena;
		LocalDateTime trenutni;
		while( ! vreme.isAfter(krajRadnogVremena.minusHours(tretman.getVremeTrajanja().getHour()).minusMinutes(tretman.getVremeTrajanja().getMinute()))) {
			trenutni = LocalDateTime.of(dan, vreme);
			if(jeSlobodanKozmeticar(tretman, trenutni, korisnickoImeKozmeticara) && trenutni.isAfter(LocalDateTime.now())) {
				sviSlobodniTermini.add(trenutni.toLocalTime());
			}
			vreme = vreme.plusHours(1);
		}
		return sviSlobodniTermini;
	}
	public HashMap<Integer, ZakazanTretman> rasporedZaKozmeticara(String korisnickoImeKozmeticara){
		HashMap<Integer, ZakazanTretman> tretmani = new HashMap<Integer, ZakazanTretman>();
		for(ZakazanTretman zt: sviZakazaniTretmani.values()) {
			if(zt.getKozmeticar().getKorisnickoIme().equals(korisnickoImeKozmeticara) && zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.name())) {
				tretmani.put(zt.getSifraZakazanogTretmana(), zt);
			}
		}
		return tretmani;
	}
	public HashMap<Integer, ZakazanTretman> sviTretmaniKlijenta(String korisnickoImeKlijenta){
		HashMap<Integer, ZakazanTretman> tretmani = new HashMap<Integer, ZakazanTretman>();
		for(ZakazanTretman zt: sviZakazaniTretmani.values()) {
			if(zt.getKlijent().getKorisnickoIme().equals(korisnickoImeKlijenta)) {
				tretmani.put(zt.getSifraZakazanogTretmana(), zt);
			}
		}
		return tretmani;
	}
	public HashMap<Integer, ZakazanTretman> sviZakazaniTretmani(){
		HashMap<Integer, ZakazanTretman> tretmani = new HashMap<Integer, ZakazanTretman>();
		for(ZakazanTretman zt: sviZakazaniTretmani.values()) {
				tretmani.put(zt.getSifraZakazanogTretmana(), zt);
		}
		return tretmani;
	}
	public int brojTretmanaPoKozmeticaruZaVremenskiPeriod(String korisnickoImeKozmeticara,LocalDate od_dana, LocalDate do_dana) {
		int brojac = 0;
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			if(zt.getKozmeticar().getKorisnickoIme().equals(korisnickoImeKozmeticara)) {
				if((zt.getTermin().toLocalDate().isAfter(od_dana)||zt.getTermin().toLocalDate().isEqual(od_dana)) && (zt.getTermin().toLocalDate().isBefore(do_dana)||zt.getTermin().toLocalDate().isEqual(do_dana)) && (zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.IZVRSEN.name())||zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.NIJE_SE_POJAVIO.name()))) {
					brojac++;
				}
			}
			
		}
		return brojac;
	}
	public Double prihodZaMesec(int mesec) {
		double prihod = 0;
		LocalDate pocetak = LocalDate.now().withMonth(mesec).withDayOfMonth(1);
		if(mesec>LocalDate.now().getMonthValue()) {
			pocetak = pocetak.minusYears(1);
		}
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.getMonth().length(pocetak.isLeapYear()));
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(pocetak) && termin.isBefore(kraj)) {
				prihod+=zt.getCena();
			}
		}
		return prihod;
	}
	public double rashodZaMesec(int mesec) {
		double rashod = 0;
		LocalDate pocetak = LocalDate.now().withMonth(mesec).withDayOfMonth(1);
		if(mesec>LocalDate.now().getMonthValue()) {
			pocetak = pocetak.minusYears(1);
		}
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.getMonth().length(pocetak.isLeapYear()));
		for(Osoba o: menadzerOsoblja.sveOsobe().values()) {
			if(o instanceof Zaposleni) {
				Zaposleni z = (Zaposleni) o;
				rashod+=z.getPlata();
			}
		}
		return rashod;
	}

	public double prihodZaPeriodZaKozmeticara(LocalDate od_dana, LocalDate do_dana, String korisnickoImeKozmeticara){
		double prihod = 0;
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(od_dana) && termin.isBefore(do_dana) && zt.getKozmeticar().getKorisnickoIme().equals(korisnickoImeKozmeticara)) {
				prihod+=zt.getCena();
			}
		}
		return prihod;
	}
	public HashMap<String, Double> prihodZaPeriodPoKozmeticarima(LocalDate od_dana, LocalDate do_dana){
		HashMap<String, Double> spisak = new HashMap<String, Double>();
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(od_dana) && termin.isBefore(do_dana)) {
				double cena = zt.getCena();
				String korisnickoImeKozmeticara = zt.getKozmeticar().getKorisnickoIme();
				if(spisak.containsKey(korisnickoImeKozmeticara)) {
					double prihod = spisak.get(korisnickoImeKozmeticara)+cena;
					spisak.replace(korisnickoImeKozmeticara, prihod);
				}
				else {
					spisak.put(korisnickoImeKozmeticara, cena);
				}
			}
		}
		return spisak;
	}
	public HashMap<String, Integer> angazovanjePoKozmeticaruZaPoslednjiMesec(){
		LocalDate od_dana = LocalDate.now().minusMonths(1);
		LocalDate do_dana = LocalDate.now();
		HashMap<String, Integer> spisak = new HashMap<String, Integer>();
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(od_dana) && termin.isBefore(do_dana)) {
				String korisnickoImeKozmeticara = zt.getKozmeticar().getKorisnickoIme();
				if(spisak.containsKey(korisnickoImeKozmeticara)) {
					int broj = spisak.get(korisnickoImeKozmeticara)+1;
					spisak.replace(korisnickoImeKozmeticara, broj);
				}
				else {
					spisak.put(korisnickoImeKozmeticara, 1);
				}
			}
		}
		return spisak;
	}
	public HashMap<String, Integer> realizacijaKozmetickihTretmana(LocalDate od_dana, LocalDate do_dana){
		HashMap<String, Integer> spisak = new HashMap<String, Integer>();
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(od_dana) && termin.isBefore(do_dana)) {
				String tip_realizacije = zt.getStatusTretmana();
				if(spisak.containsKey(tip_realizacije)) {
					int broj = spisak.get(tip_realizacije);
					spisak.replace(tip_realizacije, broj+1);
				}
				else {
					spisak.put(tip_realizacije, 1);
				}
			}
		}
		return spisak;
	}
	public ArrayList<Double> prihodIRealizacijaZaPeriodPoTretmanu(LocalDate od_dana, LocalDate do_dana, int idTretmana) {
		double prihod = 0;
		int broj_zakazivanja= 0;
		ArrayList<Double> lista = new ArrayList<Double>();
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(od_dana) && termin.isBefore(do_dana) && zt.getVrstaTretmana().getidKozmetickogTretmana() == idTretmana) {
				prihod+=zt.getCena();
				broj_zakazivanja++;
			}
		}
		lista.add(prihod);
		lista.add((double) broj_zakazivanja);
		return lista;
	}
	public HashMap<String, Double> godisnjiPrihodPoTipuTretmana(){
		LocalDate do_dana = LocalDate.now();
		LocalDate od_dana = LocalDate.now().minusYears(1);
		HashMap<Integer, Double> spisak = new HashMap<Integer, Double>();
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if((termin.isAfter(od_dana)||termin.isEqual(od_dana)) && (termin.isBefore(do_dana) || termin.isEqual(do_dana) )) {
				int tipTretmana = zt.getVrstaTretmana().getTipTretmana().getSifra();
				double cena = zt.getCena();
				if(spisak.containsKey(tipTretmana)) {
					cena+=spisak.get(tipTretmana);
					spisak.replace(tipTretmana, cena);
					
				}else {
					spisak.put(tipTretmana, cena);
				}
			}
		}
		HashMap<String, Double> zavrsniSpisak = new HashMap<String, Double>();
		for(int i: spisak.keySet()) {
			String naziv = menadzerTretmana.nadjiTipTretmana(i).getNaziv();
			zavrsniSpisak.put(naziv, spisak.get(i));
		}
		return zavrsniSpisak;
	}
	public void dodeliBonusKozmeticarima(int granica, double bonus,LocalDate od_dana, LocalDate do_dana) {
		for(Osoba o: menadzerOsoblja.sveOsobe().values()) {
			if(o instanceof Kozmeticar) {
				menadzerOsoblja.dodeliBonus(brojTretmanaPoKozmeticaruZaVremenskiPeriod(o.getKorisnickoIme(),od_dana,do_dana), granica, (Kozmeticar)o, bonus);
			}
		}
		menadzerOsoblja.upisiPodatke();
	}
	
	public void dodeliBonusKozmeticarima(double granica, double bonus, LocalDate od_dana, LocalDate do_dana) {
		for(Osoba o: menadzerOsoblja.sveOsobe().values()) {
			if(o instanceof Kozmeticar) {
				menadzerOsoblja.dodeliBonus(prihodZaPeriodZaKozmeticara(od_dana, do_dana, o.getKorisnickoIme()), granica,(Kozmeticar) o, bonus);
			}
		}
		menadzerOsoblja.upisiPodatke();
	}
	
	public double prihodZaMesecPoTipu(int mesec, int IdTipa) {
		double prihod = 0;
		LocalDate pocetak = LocalDate.now().withMonth(mesec).withDayOfMonth(1);
		if(mesec>LocalDate.now().getMonthValue()) {
			pocetak = pocetak.minusYears(1);
		}
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.getMonth().length(pocetak.isLeapYear()));
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(pocetak) && termin.isBefore(kraj) && zt.getVrstaTretmana().getTipTretmana().getSifra() == IdTipa) {
				prihod+=zt.getCena();
			}
		}
		return prihod;
	}
	
	public HashMap<String, List<Double>> godisnjiPrihodPoTipuPoMesecu(){
		HashMap<String, List<Double>> spisak = new HashMap<String, List<Double>>();
		List<Double> prihod = new ArrayList<Double>();
		int mesec = LocalDate.now().getMonthValue();
		for(TipKozmetickogTretmana tkt: menadzerTretmana.sviTipoviTretmana()) {
			prihod = new ArrayList<Double>();
			for(int i = 0; i < 12; i++) {
				int trenutni = (mesec+i)%12 + 1;
				prihod.add(prihodZaMesecPoTipu(trenutni, tkt.getSifra()));
			}
			spisak.put(tkt.getNaziv(), prihod);
		}
		return spisak;
	}
	public double prihodZaPeriod(LocalDate od_dana, LocalDate do_dana) {
		double prihod = 0;
		for(ZakazanTretman zt: this.sviZakazaniTretmani.values()) {
			LocalDate termin = zt.getTermin().toLocalDate();
			if(termin.isAfter(od_dana) && termin.isBefore(do_dana)) {
				prihod+=zt.getCena();
			}
		}
		return prihod;
	}
	public double rashodZaPeriod(LocalDate od_dana, LocalDate do_dana) {
		double rashod = 0;
		long meseci = ChronoUnit.MONTHS.between(od_dana, do_dana);
		meseci += 1;
		for(Osoba o: menadzerOsoblja.sveOsobe().values()) {
			if(o instanceof Zaposleni) {
				rashod += meseci * ((Zaposleni)o).getPlata();
			}
		}
		return rashod;
	}
	
	
	public List<Double> godisnjiPrihodPoMesecima(){
		List<Double> prihod = new ArrayList<Double>();
		int mesec = LocalDate.now().getMonthValue();
		for(int i = 0; i < 12; i++) {
			int trenutni = (mesec+i)%12 + 1;
			prihod.add(prihodZaMesec(trenutni));
		}
		return prihod;
	}
	
	
	
	

}
