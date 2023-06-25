package model;

import java.time.LocalDate;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;

public class RadKozmeticaraModel extends AbstractTableModel {
	private MenadzerKozmetickogSalona mks;
	private MenadzerZakazivanja mz;
	private MenadzerOsoba mo;
	private LocalDate odDana, doDana;
	private String[] koloneNazivi = { "Ime","Prezime","Korisnicko ime", "Izvršio tretmana", "Prihodovao"};

	public RadKozmeticaraModel(MenadzerKozmetickogSalona mks, LocalDate odDana, LocalDate doDana) {
		this.mks = mks;
		this.mz = mks.getMenadzerZakazivanja();
		this.mo = mks.getMenadzerOsoba();
		this.odDana = odDana;
		this.doDana = doDana;
	}

	@Override
	public int getRowCount() {
		return mo.sviKozmeticari().size();
	}

	@Override
	public int getColumnCount() {
		return koloneNazivi.length;
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		if(getRowCount() == 0) {
			return 0;
		}
		Kozmeticar k = mo.sviKozmeticari().get(red);
		switch (kolona) {
		case 0:
			return k.getIme();
		case 1:
			return k.getPrezime();
		case 2:
			return k.getKorisnickoIme();
		case 3:
			return mz.brojTretmanaPoKozmeticaruZaVremenskiPeriod(k.getKorisnickoIme(), odDana, doDana);
		case 4:
			return mz.prihodZaPeriodZaKozmeticara(odDana, doDana, k.getKorisnickoIme());
		default:
			return null;

		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}
}
