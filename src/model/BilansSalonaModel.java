package model;

import java.time.LocalDate;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;

public class BilansSalonaModel extends AbstractTableModel {
	private MenadzerZakazivanja mz;
	private LocalDate od_dana, do_dana;
	private String[] koloneNazivi = { "Prihod salona", "Rashod salona"};

	public BilansSalonaModel(MenadzerZakazivanja mz,LocalDate od_dana, LocalDate do_dana) {
		this.mz = mz;
		this.do_dana = do_dana;
		this.od_dana = od_dana;
	}

	@Override
	public int getRowCount() {
		return 1;
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
		switch (kolona) {
		case 0:
			return mz.prihodZaPeriod(od_dana,do_dana);
		case 1:
			return mz.rashodZaPeriod(od_dana, do_dana);
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}
}
