package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import menadzeri.MenadzerZakazivanja;
import usluge.ZakazanTretman;

public class ZakazanTretmanRecepcionerModel extends AbstractTableModel {
	private MenadzerZakazivanja mz;
	private Klijent k;
	private String[] koloneNazivi = { "Naziv","Tip","Klijent", "Termin", "Cena", "Status" };

	public ZakazanTretmanRecepcionerModel(MenadzerZakazivanja mz, Klijent k) {
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
			return zt.getVrstaTretmana().getTipTretmana();
		case 2:
			return zt.getKlijent();
		case 3:
			return zt.terminZaTabelu();
		case 4:
			return zt.getCena();
		case 5:
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
