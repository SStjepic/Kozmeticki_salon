package model;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;
import usluge.KozmetickiTretman;

public class RealizacijaZakazanihTretmanaModel extends AbstractTableModel {
	
	private MenadzerKozmetickogSalona mks;
	private MenadzerKozmetickiTretmani mkt;
	private String[] koloneNazivi = {"Status", "Broj ostvarenih statusa" };
	private MenadzerZakazivanja mz;
	private MenadzerOsoba mo;
	private LocalDate odDana, doDana;

	public RealizacijaZakazanihTretmanaModel(MenadzerKozmetickogSalona mks, LocalDate odDana, LocalDate doDana) {
		this.mks = mks;
		this.mz = mks.getMenadzerZakazivanja();
		this.mo = mks.getMenadzerOsoba();
		this.odDana = odDana;
		this.doDana = doDana;
		this.mkt = mks.getMenadzerTretmana();
	}
	
	
	@Override
	public int getRowCount() {
		return  mz.realizacijaKozmetickihTretmana(odDana, doDana).keySet().size();
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
		HashMap<String, Integer> lista = mz.realizacijaKozmetickihTretmana(odDana, doDana);
		List<String> kljucevi = new ArrayList<String>(lista.keySet());
		String vrsta =  kljucevi.get(red);
		switch (kolona) {
		case 0:
			return vrsta;
		case 1:
			return lista.get(vrsta);
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
