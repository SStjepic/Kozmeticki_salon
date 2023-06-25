package model;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.PlainDocument;

import korisnici.Klijent;
import korisnici.Recepcioner;
import menadzeri.MenadzerOsoba;

public class RecepcionerModel extends AbstractTableModel {
	private MenadzerOsoba mo;
	private Klijent k;
	private String[] koloneNazivi = { "Ime","Prezime","Korisničko ime", "Lozinka","Pol","Broj telefona","Adresa","Stručna sprema","Radni staž", "Bonus", "Plata" };

	public RecepcionerModel(MenadzerOsoba mo) {
		this.mo = mo;
	}

	@Override
	public int getRowCount() {
		return mo.sviRecepcioneri().size();
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
		Recepcioner r = mo.sviRecepcioneri().get(red);
		switch (kolona) {
		case 0:
			return r.getIme();
		case 1:
			return r.getPrezime();
		case 2:
			return r.getKorisnickoIme();
		case 3:
			return r.getLozinka();
		case 4:
			return r.getPol();
		case 5:
			return r.getBrojTelefona();
		case 6:
			return r.getAdresaStanovanja();
		case 7:
			return r.getStrucnaSprema();
		case 8:
			return r.getStaz();
		case 9:
			return r.getBonus();
		case 10:
			return r.plata();
		default:
			return null;

		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}

}
