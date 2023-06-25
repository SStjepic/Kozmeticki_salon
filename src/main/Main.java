package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import grafici.KozmeticariGrafik;
import gui.LoginDialog;
import gui.MenadzerFrame;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;
import usluge.Cenovnik;

public class Main {
	public static MenadzerKozmetickogSalona gm;
	public static MenadzerOsoba mo;
	public static MenadzerKozmetickiTretmani mkt;
	public static MenadzerZakazivanja mz;
	public static Cenovnik cenovnikUsluga;
	public static KozmetickiSalon ks;
	
	public static void main(String[] args) {
		LocalTime pocetak = LocalTime.now().withHour(8).withMinute(0).withNano(0).withSecond(0);
		ks = new KozmetickiSalon("Salon Maestro", pocetak, pocetak.withHour(20));
		
		gm = new MenadzerKozmetickogSalona("./podaci/osobe.txt", "./podaci/kozmetickiTretmani.txt", "./podaci/zakazaniTretmani.txt", "./podaci/tipoviKozmetickihTretmana.txt", "./podaci/id.txt", ks);
		mo = gm.getMenadzerOsoba();
		mkt = gm.getMenadzerTretmana();
		mz = gm.getMenadzerZakazivanja();

		gm.ucitajPodatke();
		LoginDialog ldg = new LoginDialog(gm);
		mo.upisiPodatke();
		
	}

}
