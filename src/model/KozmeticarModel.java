package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import korisnici.Recepcioner;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerOsoba;

public class KozmeticarModel extends AbstractTableModel {
	private MenadzerOsoba mo;
	private MenadzerKozmetickiTretmani mkt;
	private Klijent k;
	private String[] koloneNazivi = { "Ime","Prezime","Korisničko ime", "Lozinka","Pol","Broj telefona","Adresa","Stručna sprema","Radni staž","Tipovi tretmana", "Bonus", "Plata" };

	public KozmeticarModel(MenadzerOsoba mo, MenadzerKozmetickiTretmani mkt) {
		this.mo = mo;
		this.mkt = mkt;
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
			return k.getLozinka();
		case 4:
			return k.getPol();
		case 5:
			return k.getBrojTelefona();
		case 6:
			return k.getAdresaStanovanja();
		case 7:
			return k.getStrucnaSprema();
		case 8:
			return k.getStaz();
		case 9:
			ArrayList<String> spisak = mkt.tipoviTretmanaKozmeticara(k);
			String sb = "";
			for(String s:spisak) {
				sb += s + ", " ;
			}
			return sb;
		case 10:
			return k.getBonus();
		case 11:
			return k.plata();
		default:
			return null;

		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}
}
