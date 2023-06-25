package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;
import usluge.ZakazanTretman;

public class KlijentModel extends AbstractTableModel {
	private MenadzerOsoba mo;
	private String[] koloneNazivi = { "Ime","Prezime","Korisnicko ime", "Lozinka","Pol","Broj telefona","Adresa","Kartica lojalnosti","Potrosio"};

	public KlijentModel(MenadzerOsoba mo) {
		this.mo = mo;
	}

	@Override
	public int getRowCount() {
		return mo.sviKlijenti().size();
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
		Klijent k = mo.sviKlijenti().get(red);
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
			if(k.getKarticaLojalnosti()) {
				return "DA";
			}
			else {
				return "NE";
			}
		case 8:
			return k.getPotrosio();
			
		default:
			return null;

		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}
}
