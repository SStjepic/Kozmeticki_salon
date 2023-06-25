package model;

import javax.swing.table.AbstractTableModel;

import menadzeri.MenadzerKozmetickiTretmani;
import usluge.KozmetickiTretman;

public class KozmetickiTretmanMenadzerModel extends AbstractTableModel {
	
	private MenadzerKozmetickiTretmani mkt;
	private String[] koloneNazivi = {"ID", "Naziv","Tip", "Vreme trajanja", "Cena" };
	
	public KozmetickiTretmanMenadzerModel(MenadzerKozmetickiTretmani mkt) {
		this.mkt = mkt;
	}
	
	@Override
	public int getRowCount() {
		return mkt.kozmetickiTretmaniZaTabelu().size();
	}

	@Override
	public int getColumnCount() {
		return koloneNazivi.length;
	}

	@Override
	public Object getValueAt(int red, int kolona) {
		KozmetickiTretman kt = mkt.kozmetickiTretmaniZaTabelu().get(red);
		switch (kolona) {
		case 0:
			return kt.getidKozmetickogTretmana();
		case 1:
			return kt;
		case 2:
			return kt.getTipTretmana().getNaziv();
		case 3:
			return kt.getVremeTrajanja();
		case 4:
			return mkt.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kt);
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
