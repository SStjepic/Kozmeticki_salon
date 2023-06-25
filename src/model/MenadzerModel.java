package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Klijent;
import korisnici.Menadzer;
import korisnici.Recepcioner;
import menadzeri.MenadzerOsoba;

public class MenadzerModel extends AbstractTableModel {
	private MenadzerOsoba mo;
	private Klijent k;
	private String[] koloneNazivi = { "Ime","Prezime","Korisničko ime", "Lozinka","Pol","Broj telefona","Adresa","Stručna sprema","Radni staž", "Bonus", "Plata" };

	public MenadzerModel(MenadzerOsoba mo) {
		this.mo = mo;
	}

	@Override
	public int getRowCount() {
		return mo.sviMenadzeri().size();
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
		Menadzer m = mo.sviMenadzeri().get(red);
		switch (kolona) {
		case 0:
			return m.getIme();
		case 1:
			return m.getPrezime();
		case 2:
			return m.getKorisnickoIme();
		case 3:
			return m.getLozinka();
		case 4:
			return m.getPol();
		case 5:
			return m.getBrojTelefona();
		case 6:
			return m.getAdresaStanovanja();
		case 7:
			return m.getStrucnaSprema();
		case 8:
			return m.getStaz();
		case 9:
			return m.getBonus();
		case 10:
			return m.plata();
		default:
			return null;

		}
	}
	
	@Override
	public String getColumnName(int kolona) {
		return this.koloneNazivi[kolona];
	}
}
