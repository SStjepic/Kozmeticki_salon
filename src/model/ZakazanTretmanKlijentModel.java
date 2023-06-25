package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import menadzeri.MenadzerZakazivanja;
import usluge.ZakazanTretman;

public class ZakazanTretmanKlijentModel extends AbstractTableModel {

	private MenadzerZakazivanja mz;
	private Klijent k;
	private String[] koloneNazivi = { "Naziv", "Termin", "Cena", "Status" };

	public ZakazanTretmanKlijentModel(MenadzerZakazivanja mz, Klijent k) {
		this.mz = mz;
		this.k = k;
	}

	@Override
	public int getRowCount() {
		return mz.zakazaniTretmaniKlijenta(k).size();
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
		ZakazanTretman zt = mz.zakazaniTretmaniKlijenta(k).get(red);
		switch (kolona) {
		case 0:
			return zt;
		case 1:
			return zt.terminZaTabelu();
		case 2:
			return zt.getCena();
		case 3:
			return zt.getStatusTretmana();
		default:
			return null;

		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}

	@Override
	public Class<?> getColumnClass(int kolonaIndex) {
		return this.getValueAt(0, kolonaIndex).getClass();
	}
}
