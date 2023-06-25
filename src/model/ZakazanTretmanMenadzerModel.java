package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import menadzeri.MenadzerZakazivanja;
import usluge.ZakazanTretman;

public class ZakazanTretmanMenadzerModel extends AbstractTableModel {
	private MenadzerZakazivanja mz;
	private String[] koloneNazivi = {"ID", "Naziv","Tip","Klijent","Kozmetičar", "Termin", "Cena", "Status" };

	public ZakazanTretmanMenadzerModel(MenadzerZakazivanja mz) {
		this.mz = mz;
	}
	
	public String[] zaglavlje() {
		return koloneNazivi;
	}
	
	@Override
	public int getRowCount() {
		return mz.zakazaniTretmaniKlijenta(null).size();
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
		ZakazanTretman zt = mz.zakazaniTretmaniKlijenta(null).get(red);
		switch (kolona) {
		case 0:
			return zt.getSifraZakazanogTretmana();
		case 1:
			return zt;
		case 2:
			return zt.getVrstaTretmana().getTipTretmana();
		case 3:
			return zt.getKlijent();
		case 4:
			return zt.getKozmeticar();
		case 5:
			return zt.terminZaTabelu();
		case 6:
			return zt.getCena();
		case 7:
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
