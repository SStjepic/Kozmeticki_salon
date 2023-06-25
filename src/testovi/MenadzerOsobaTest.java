package testovi;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import korisnici.Menadzer;
import korisnici.Recepcioner;
import menadzeri.MenadzerKozmetickiTretmani.status;
import menadzeri.MenadzerOsoba;

public class MenadzerOsobaTest {

	public static MenadzerOsoba mo;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			mo = new MenadzerOsoba("testMO.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
        File[] obrisi = {
                new File("testMO.txt")
        };

        for (File fajl : obrisi) {
            boolean jeObrisan = fajl.delete();
        }
	}



	@Test
	public void testRegistrujKlijenta() {
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		Klijent k = (Klijent)mo.nadjiOsobu("pperic");
		assertTrue((k instanceof Klijent));
	}

	@Test
	public void testSviKlijenti() {
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		mo.registrujRecepcionera("mika", "mikic", "muski", "48486684", "Novi Sad", "mmikic", "mika123", MenadzerOsoba.sprema.srednja.name(),0,17000);
		List<Klijent> spisak=mo.sviKlijenti();
		assertTrue(spisak.size() == 1);
	}

	@Test
	public void testSviRecepcioneri() {
		mo.registrujRecepcionera("mika", "mikic", "muski", "48486684", "Novi Sad", "mmikic", "mika123", MenadzerOsoba.sprema.srednja.name(),0,17000);
		List<Recepcioner> spisak=mo.sviRecepcioneri();
		assertTrue(spisak.size() == 1);
	}

	@Test
	public void testSviKozmeticari() {
		List<Kozmeticar> spisak=mo.sviKozmeticari();
		assertTrue(spisak.size() == 1);
	}

	@Test
	public void testSviMenadzeri() {
		mo.registrujMenadzera("stefan", "stjepic", "muski", "0653255512", "Uzice", "sstjepic", "stefan123", MenadzerOsoba.sprema.visoka.name(), 0, 23000);
		List<Menadzer> spisak=mo.sviMenadzeri();
		assertTrue(spisak.size() == 1);
	}

	@Test
	public void testSveOsobe() {
		assertTrue((mo.sveOsobe().values().size()) == 4);
	}

	@Test
	public void testProveriLoginUspesan() {
		assertTrue("Uspesan login",mo.proveriLogin("pperic", "petar123") != null);
	}
	@Test
	public void testProveriLoginNeuspesan() {
		assertTrue("Uspesan login",mo.proveriLogin("pperic", "petar") == null);
	}

	@Test
	public void testJeJedinstvenoKorisnickoImeUspesan() {
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		assertTrue(!mo.jeJedinstvenoKorisnickoIme("pperic"));
	}	
	
	@Test
	public void testJeJedinstvenoKorisnickoImeNeuspesan() {
		assertTrue(mo.jeJedinstvenoKorisnickoIme("djuka"));
	}

	@Test
	public void testAzurirajZaposlenog() {
		Menadzer m = (Menadzer)mo.nadjiOsobu("sstjepic");
		mo.azurirajZaposlenog(m.getKorisnickoIme(), "Djura", "Djuric", m.getPol(), m.getBrojTelefona(), m.getAdresaStanovanja(), m.getLozinka(), m.getStrucnaSprema(), m.getStaz(), m.getBonus(), m.getOsnova());
		m = (Menadzer)mo.nadjiOsobu("sstjepic");
		assertTrue(	m.getIme().equals("Djura") && m.getPrezime().equals("Djuric"));
	}

	@Test
	public void testDodajTretmanKozmeticaru() {
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		mo.dodajTretmanKozmeticaru(0, "mmirkovic");
		assertTrue(k.jeObucenZaTipTretmana(0));
	}

	@Test
	public void testAzurirajTretmaneKozmeticara() {
		ArrayList<Integer> lista = new ArrayList<>();
		lista.add(4);
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		mo.azurirajTretmaneKozmeticara(lista, "mmirkovic");
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		assertTrue( k.jeObucenZaTipTretmana(4));
	}



	@Test
	public void testAzurirajKlijenta() {
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		Klijent k = (Klijent)mo.nadjiOsobu("pperic");
		k = (Klijent)mo.nadjiOsobu("pperic");
		mo.azurirajKlijenta(k.getKorisnickoIme(), "Djura", "Djuric", k.getPol(), k.getBrojTelefona(), k.getAdresaStanovanja(), k.getLozinka());
		assertTrue(	k.getIme().equals("Djura") && k.getPrezime().equals("Djuric"));
	}

	@Test
	public void testObrisiOsobu() {
		mo.obrisiOsobu("pperic");
		assertTrue((mo.sveOsobe().values().size()) == 3);
	}

	@Test
	public void testAzurirajPotrosnjuKorisnika() {
		mo.registrujKlijenta("petar", "peric", "muski", "1556556", "Uzice", "pperic", "petar123");
		Klijent k = (Klijent)mo.nadjiOsobu("pperic");
		mo.azurirajPotrosnjuKorisnika("pperic", 1500);
		assertTrue(k.getPotrosio() == 1500);
	}

	@Test
	public void testObuceniKozmeticari() {
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		assertTrue(k.jeObucenZaTipTretmana(0));
	}

	@Test
	public void testDodeli() {
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		mo.dodeliBonus(10, 4, k, 15000);
		assertTrue(k.getBonus() == 15000);
	}

	@Test
	public void testDodeliBonusPrihod() {
		mo.registrujKozmeticara("Mirko", "Mirkovic", "muski", "0652586285", "Beograd", "mmirkovic", "mirko123", MenadzerOsoba.sprema.srednja.name(), 0, 19000);
		Kozmeticar k = (Kozmeticar)mo.nadjiOsobu("mmirkovic");
		mo.dodeliBonus(1200.0, 15000, k, 15000);
		assertTrue(k.getBonus() == 0);
	}

	@Test
	public void testDodeliKarticuLojalnosti() {
		Klijent k = (Klijent)mo.nadjiOsobu("pperic");
		mo.azurirajPotrosnjuKorisnika("pperic", 1500);
		mo.dodeliKarticuLojalnosti(1000);
		assertTrue(k.getKarticaLojalnosti());
	}
	
	@Test
	public void testFajl() {
		mo.upisiPodatke();
		mo.ucitajPodatke();
		assertTrue(mo.sveOsobe().size() == 4);

	}


}
