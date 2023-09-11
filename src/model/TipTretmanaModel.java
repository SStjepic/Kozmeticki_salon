package model;

import javax.swing.table.AbstractTableModel;

import menadzeri.MenadzerKozmetickiTretmani;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

public class TipTretmanaModel extends AbstractTableModel {
	private MenadzerKozmetickiTretmani mkt;
	private String[] koloneNazivi = {"ID", "Naziv"};
	
	public TipTretmanaModel(MenadzerKozmetickiTretmani mkt) {
		this.mkt = mkt;
	}
	
	@Override
	public int getRowCount() {
		return mkt.sviTipoviTretmana().size();
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
		TipKozmetickogTretmana tkt = mkt.sviTipoviTretmana().get(red);
		switch (kolona) {
		case 0:
			return tkt.getSifra();
		case 1:
			return tkt.getNaziv();
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
