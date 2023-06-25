package model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;
import usluge.KozmetickiTretman;

public class RealizacijaTretmanaModel extends AbstractTableModel {
	private MenadzerKozmetickogSalona mks;
	private MenadzerKozmetickiTretmani mkt;
	private String[] koloneNazivi = {"ID", "Naziv","Tip", "Vreme trajanja", "Cena", "Izvršeno tretmana", "Prihodovano" };
	private MenadzerZakazivanja mz;
	private MenadzerOsoba mo;
	private LocalDate odDana, doDana;

	public RealizacijaTretmanaModel(MenadzerKozmetickogSalona mks, LocalDate odDana, LocalDate doDana) {
		this.mks = mks;
		this.mz = mks.getMenadzerZakazivanja();
		this.mo = mks.getMenadzerOsoba();
		this.odDana = odDana;
		this.doDana = doDana;
		this.mkt = mks.getMenadzerTretmana();
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
		ArrayList<Double> lista= mz.prihodIRealizacijaZaPeriodPoTretmanu(odDana, doDana, kt.getidKozmetickogTretmana());
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
		case 5:
			return lista.get(1);
		case 6:
			return lista.get(0);
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
