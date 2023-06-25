package testovi;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalTime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerOsoba;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

public class MenadzerKozmetickiTretmaniTest {
	
	public static MenadzerKozmetickiTretmani mkt;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mkt = new MenadzerKozmetickiTretmani("testTrtmani.txt", "testTipovi.txt", 0, 0);
	}

	@AfterClass 
	public static void tearDownAfterClass() throws Exception {
        File[] obrisi = {
                new File("testTretmani.txt"),
                new File("testTipovi.txt")
        };

        for (File fajl : obrisi) {
            boolean jeObrisan = fajl.delete();
        }
	}

	@Test
	public void testProveriJedinstvenostTipaTretmana() {
		assertTrue(mkt.proveriJedinstvenostTipaTretmana("masaza"));
	}
	
	@Test
	public void testProveriJedinstvenostKozmetickogTretmana() {
		assertTrue(mkt.proveriJedinstvenostKozmetickogTretmana("Kineska masaza"));
	}
	
	@Test
	public void testDodajTipTretmana() {
		mkt.dodajTipTretmana("masaza");
		assertTrue(!mkt.proveriJedinstvenostTipaTretmana("masaza"));
	}
	
	@Test
	public void testNadjiTipTretmana() {
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana("masaza");
		TipKozmetickogTretmana tkt1 = mkt.nadjiTipTretmana(tkt.getSifra());
		
		assertEquals(tkt, tkt1);
	}
	
	@Test
	public void testDodajKozmetickiTretman() {
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), tkt);
		assertTrue(!mkt.proveriJedinstvenostKozmetickogTretmana("Brazilska masaza"));
	}
	
	@Test
	public void testCenovnikUsluga() {
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), tkt);
		KozmetickiTretman kt = mkt.nadjiKozmetickiTretman("Brazilska masaza");
		Double cena = mkt.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kt);
		assertEquals(cena, (Double)2600.0);
	}
	
	@Test
	public void testNadjiKozmetickiTretman() {
		KozmetickiTretman kt = mkt.nadjiKozmetickiTretman("Brazilska masaza");
		KozmetickiTretman kt1 = mkt.nadjiKozmetickiTretman(kt.getidKozmetickogTretmana());
		assertEquals(kt, kt1);
	}
	@Test
	public void testAzurirajKozmetickiTretman() {
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mkt.dodajTipTretmana("masaza");
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), tkt);
		KozmetickiTretman kt = mkt.nadjiKozmetickiTretman("Brazilska masaza");
		mkt.azurirajKozmetickiTretman(kt.getidKozmetickogTretmana(), kt.getNaziv(), 3000, LocalTime.of(0, 40), kt.getTipTretmana());
		kt = mkt.nadjiKozmetickiTretman("Brazilska masaza");
		assertEquals(kt.getVremeTrajanja(),  LocalTime.of(0, 40));
	}
	@Test
	public void testIzmenaCenovnikUsluga() {
		KozmetickiTretman kt = mkt.nadjiKozmetickiTretman("Brazilska masaza");
		mkt.azurirajKozmetickiTretman(kt.getidKozmetickogTretmana(), kt.getNaziv(), 3000, LocalTime.of(0, 40), kt.getTipTretmana());
		System.out.println(kt.getVremeTrajanja());
		Double cena = mkt.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kt);
		assertEquals(cena, (Double)3000.0);
	}

	@Test
	public void testSviTipoviTretmana() {
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mkt.dodajTipTretmana("masaza");
		mkt.dodajTipTretmana("pedikir");
		mkt.dodajTipTretmana("manikir");
		assertTrue(mkt.sviTipoviTretmana().size() == 3);
	}

	@Test
	public void testSviKozmetickiTretmani() {
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mkt.dodajTipTretmana("masaza");
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), tkt);
		mkt.dodajKozmetickiTretman("Kineska masaza", 2600, LocalTime.of(1, 20), tkt);
		mkt.dodajKozmetickiTretman("Tradicionalna masaza", 2600, LocalTime.of(1, 20), tkt);
		assertTrue(mkt.KozmetickiTretmani().size() == 3);
	}

	@Test
	public void testAzurirajTipKozmetickogTretmana() {
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mkt.dodajTipTretmana("masaza");
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana("masaza");
		mkt.azurirajTipKozmetickogTretmana(tkt.getSifra(), "masazaaaa");
		tkt = mkt.nadjiTipTretmana("masazaaaa");
		assertTrue(tkt!=null);
	}

	@Test
	public void testObrisiTipKozmetickogTretmana() {
		TipKozmetickogTretmana tkt = mkt.nadjiTipTretmana(0);
		assertTrue(tkt!=null);
		mkt.obrisiTipKozmetickogTretmana(0);
		tkt = mkt.nadjiTipTretmana(0);
		assertTrue(tkt==null);
	}



	@Test
	public void testObrisiKozmetickiTretman() {
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));
		KozmetickiTretman kt = mkt.nadjiKozmetickiTretman(0);
		assertTrue(kt!=null);
		mkt.obrisiKozmetickiTretman(0);
		kt = mkt.nadjiKozmetickiTretman(0);
		assertTrue(kt==null);

	}

	@Test
	public void testOdgovarajuciKozmetickiTretmani() {
		
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));
		assertEquals(mkt.odgovarajuciKozmetickiTretmani(mkt.nadjiTipTretmana("pedikir")).size(), 0);
	}
	
	@Test
	public void testFajl() {
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mkt.dodajTipTretmana("masaza");
		mkt.dodajKozmetickiTretman("Brazilska masaza", 2600, LocalTime.of(1, 20), mkt.nadjiTipTretmana(0));
		mkt.upisiPodatke();
		mkt = new MenadzerKozmetickiTretmani("testTretmani.txt", "testTipovi.txt", 0, 0);
		mkt.ucitajPodatke();
		
		assertEquals(mkt.sviTipoviTretmana().size(), 1);
		assertEquals(mkt.KozmetickiTretmani().size(), 1);
	}


}
