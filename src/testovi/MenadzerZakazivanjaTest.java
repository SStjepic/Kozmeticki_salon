package testovi;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runners.MethodSorters;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;
import usluge.ZakazanTretman;

@FixMethodOrder(MethodSorters.DEFAULT)
public class MenadzerZakazivanjaTest {
	
	public static MenadzerZakazivanja mz;
	public static MenadzerKozmetickiTretmani mkt;
	public static MenadzerOsoba mo;
	

	@BeforeClass 
	public static void setUpBeforeClass() throws Exception {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File[] obrisi = {
                new File("testTretmani.txt"),
                new File("testTipovi.txt"),
                new File("testOsobe.txt"),
                new File("zakazivanjeTest.txt"),
                new File("testTretmani1.txt"),
                new File("testTipovi1.txt"),
                new File("testTrtmani.txt")
        };

        for (File fajl : obrisi) {
            boolean jeObrisan = fajl.delete();
        }
	}

	@Test
	public void testFajl() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 7, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 8, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mz.upisiPodatke();
		mz.ucitajPodatke();
		assertTrue(mz.sviZakazaniTretmani().size() == 4);

	}

	@Test
	public void testOdrediCenuKozmetickogTretmana() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mo.dodeliKarticuLojalnosti(1000);
		double cena = mz.odrediCenuKozmetickogTretmana((Klijent)mo.nadjiOsobu("pperic"),  mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		assertTrue(cena == 2340);
	}

	@Test
	public void testNadjiZakazanTretmanInt() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		
		assertTrue(mz.nadjiZakazanTretman(0) != null);
	}

	@Test
	public void testNadjiZakazanTretmanLocalDateTimeKlijent() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		
		assertTrue(mz.nadjiZakazanTretman(vreme, (Klijent)mo.nadjiOsobu("pperic")) != null);
	}

	@Test
	public void testZakaziKozmetickiTretman() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		assertTrue(mz.sviZakazaniTretmani().size() == 1);
	}

	@Test
	public void testPromeniZakazaniTretman() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 10, 10, 0, 0);
		mz.promeniZakazaniTretman((Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme,  mkt.nadjiKozmetickiTretman("Brazilska masaza"),  mkt.nadjiKozmetickiTretman("Brazilska masaza").getidKozmetickogTretmana());
		assertTrue(mz.nadjiZakazanTretman(vreme, (Klijent)mo.nadjiOsobu("pperic"))!=null);
	}


	@Test
	public void testPromeniStatusZakazanogTretmana() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mz.promeniStatusZakazanogTretmana(mz.nadjiZakazanTretman(vreme, (Klijent)mo.nadjiOsobu("pperic")), MenadzerKozmetickiTretmani.status.IZVRSEN.name());
		ZakazanTretman zt = mz.nadjiZakazanTretman(vreme, (Klijent)mo.nadjiOsobu("pperic"));
		assertTrue(zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.IZVRSEN.name()));
	}

	@Test
	public void testObuceniKozmeticariZaTretman() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));

		
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		Kozmeticar k =(Kozmeticar)mo.nadjiOsobu("mmirkovic");
		ArrayList<Integer> tipovi = new ArrayList<>();
		tipovi.add(0);
		mo.azurirajTretmaneKozmeticara(tipovi, k.getKorisnickoIme());
		mo.registrujKozmeticara("Milan", "Milanovic", "muski", "0657556285", "Beograd", "mmilanovic", "milan123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		k =(Kozmeticar)mo.nadjiOsobu("mmilanovic");
		tipovi = new ArrayList<>();
		mo.azurirajTretmaneKozmeticara(tipovi, k.getKorisnickoIme());
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");

		assertTrue(mz.obuceniKozmeticariZaTretman(0).size() == 1);
	}

	@Test
	public void testZakazaniTretmaniKlijenta() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 7, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 8, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		assertTrue(mz.zakazaniTretmaniKlijenta((Klijent)mo.nadjiOsobu("pperic")).size() == 4);

	}

	@Test
	public void testJeSlobodanKozmeticar() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 7, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		vreme = LocalDateTime.of(2023, 6, 8, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		assertTrue(!mz.jeSlobodanKozmeticar(mkt.nadjiKozmetickiTretman("Brazilska masaza"), vreme, "mmirkovic"));

	}

	@Test
	public void testSlobodniTerminiKozmeticara() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 0), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 7, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mz.slobodniTerminiKozmeticara(vreme.toLocalDate(), mkt.nadjiKozmetickiTretman("Brazilska masaza"), "mmirkovic");
		assertEquals(mz.slobodniTerminiKozmeticara(vreme.toLocalDate(), mkt.nadjiKozmetickiTretman("Brazilska masaza"), "mmirkovic").size(), 11);
	}

	@Test
	public void testPrihodZaMesec() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 0), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 5, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mz.slobodniTerminiKozmeticara(vreme.toLocalDate(), mkt.nadjiKozmetickiTretman("Brazilska masaza"), "mmirkovic");
		double prihod = mz.prihodZaMesec(5);
		assertTrue(prihod == 2600.0);
	
	}

	@Test
	public void testRashodZaMesec() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		double rashod = mz.rashodZaMesec(5);
		assertTrue(rashod == ((Kozmeticar)mo.nadjiOsobu("mmirkovic")).getPlata());
	}

	@Test
	public void testDodeliBonusKozmeticarimaIntDoubleLocalDateLocalDate() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 0), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 5, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mz.slobodniTerminiKozmeticara(vreme.toLocalDate(), mkt.nadjiKozmetickiTretman("Brazilska masaza"), "mmirkovic");
		mz.promeniStatusZakazanogTretmana(mz.nadjiZakazanTretman(vreme, (Klijent)mo.nadjiOsobu("pperic")), MenadzerKozmetickiTretmani.status.IZVRSEN.name());

		mz.dodeliBonusKozmeticarima(1, 2000, LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 30));
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		assertTrue(k.getBonus() == 2000);
	}

	@Test
	public void testDodeliBonusKozmeticarimaDoubleDoubleLocalDateLocalDate() {
		mo = new MenadzerOsoba("testOsobe.txt");
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mz = new MenadzerZakazivanja("zakazivanjeTest.txt", 0, mo, mkt, LocalTime.of(8, 0), LocalTime.of(20, 0));
        
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 0), mkt.nadjiTipTretmana(0));

		LocalDateTime vreme = LocalDateTime.of(2023, 5, 6, 10, 0, 0);
		mz.zakaziKozmetickiTretman((Klijent)mo.nadjiOsobu("pperic"), (Kozmeticar)mo.nadjiOsobu("mmirkovic"), vreme, mkt.nadjiKozmetickiTretman("Brazilska masaza"));
		mz.slobodniTerminiKozmeticara(vreme.toLocalDate(), mkt.nadjiKozmetickiTretman("Brazilska masaza"), "mmirkovic");
		mz.promeniStatusZakazanogTretmana(mz.nadjiZakazanTretman(vreme, (Klijent)mo.nadjiOsobu("pperic")), MenadzerKozmetickiTretmani.status.IZVRSEN.name());

		mz.dodeliBonusKozmeticarima(2000.0, 2000, LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 30));
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		assertTrue(k.getBonus() == 2000);	}

}
