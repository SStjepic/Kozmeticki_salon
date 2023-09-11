package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerZakazivanja;
import usluge.KozmetickiTretman;

public class KozmetickiTretmanModel extends AbstractTableModel  {
	private MenadzerKozmetickiTretmani mkt;
	private MenadzerZakazivanja mz;
	private Klijent k;
	private String[] koloneNazivi = { "Naziv","Tip", "Vreme trajanja u minutima", "Cena" };
	
	public KozmetickiTretmanModel(MenadzerKozmetickiTretmani mkt) {
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
		if(getRowCount() == 0) {
			return 0;
		}
		KozmetickiTretman kt = mkt.kozmetickiTretmaniZaTabelu().get(red);
		switch (kolona) {
		case 0:
			return kt;
		case 1:
			return kt.getTipTretmana().getNaziv();
		case 2:
			String vreme = kt.getVremeTrajanja().toString();
			int sat = Integer.parseInt(vreme.split(":")[0]);
			int minut = Integer.parseInt(vreme.split(":")[1]);
			return sat*60+minut;
		case 3:
			return mkt.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kt);
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}


}
