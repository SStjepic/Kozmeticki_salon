package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import gui.ZakazivanjeTretmanaDialog.izdvojKozmeticare;
import gui.ZakazivanjeTretmanaDialog.izdvojKozmetickeTretmane;
import korisnici.Klijent;
import korisnici.Kozmeticar;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerZakazivanja;
import model.KozmetickiTretmanModel;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;
import usluge.ZakazanTretman;

public class IzmenaZakazanogTretmanaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField klijentText;
	private JTable table;
	private ZakazanTretman zt;
	private MenadzerZakazivanja mz;
	private MenadzerKozmetickiTretmani mkt;
	private MenadzerKozmetickogSalona mks;
	private Klijent k;
	private JFrame roditelj;
	private JComboBox kozmeticariCB;
	private JComboBox satnicaCB;
	private JDateChooser dateChooser;
	private JLabel prethodniKozmeticar;

	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private JLabel datumValidacija;
	private JButton potvrdi;
	private JButton odbaci;

	public IzmenaZakazanogTretmanaDialog(ZakazanTretman zt, MenadzerKozmetickogSalona mks, JFrame roditelj) {
		this.zt = zt;
		this.mks = mks;
		this.mkt = mks.getMenadzerTretmana();
		this.mz = mks.getMenadzerZakazivanja();
		this.roditelj = roditelj;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 444, 282);
		getContentPane().setLayout(new MigLayout("", "[][][][65.00]", "[][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Klijent");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel, "cell 1 0,alignx trailing");
		
		klijentText = new JTextField();
		getContentPane().add(klijentText, "cell 2 0,growx");
		klijentText.setColumns(20);
		klijentText.setEnabled(false);
		JLabel lblNewLabel_5 = new JLabel("Kozmeticar");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_5, "cell 1 1,alignx trailing");
		
		kozmeticariCB = new JComboBox();
		getContentPane().add(kozmeticariCB, "cell 2 1,growx");
		
		dateChooser = new JDateChooser();
		dateChooser.getDateEditor().addPropertyChangeListener("date", evt->{
			satnicaCB.removeAllItems();
			if(kozmeticariCB.getSelectedItem()!=null) {
				LocalDate dan = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if(!dan.isBefore(LocalDate.now())) {
					datumValidacija.setText("");
					Kozmeticar odabraniKozmeticar =(Kozmeticar) kozmeticariCB.getSelectedItem();
					KozmetickiTretman odabraniTretman =zt.getVrstaTretmana();
					ArrayList<LocalTime> listaTermina = mz.slobodniTerminiKozmeticara(dan, odabraniTretman, odabraniKozmeticar.getKorisnickoIme());
					for(LocalTime sati: listaTermina) {
						satnicaCB.addItem(sati);
					}
				}
				else {
					datumValidacija.setText("Ne mozete zakazati tretman u proslosti");
				}
				
			}
		});
		
		 prethodniKozmeticar = new JLabel("Prethodni:" + zt.getKozmeticar().toString());
		getContentPane().add(prethodniKozmeticar, "cell 3 1,growx");
		
		JLabel lblNewLabel_3 = new JLabel("Datum");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_3, "cell 1 2,alignx right");
		getContentPane().add(dateChooser, "cell 2 2,growx,aligny top");
		
		datumValidacija = new JLabel("");
		getContentPane().add(datumValidacija, "cell 3 2");
		
		JLabel lblNewLabel_4 = new JLabel("Vreme");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_4, "cell 1 3,alignx trailing");
		
		satnicaCB = new JComboBox();
		getContentPane().add(satnicaCB, "cell 2 3,growx");
		
		potvrdi = new JButton("Potvrdi");
		potvrdi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(kozmeticariCB.getSelectedObjects()!=null && dateChooser.getDate()!=null && satnicaCB.getSelectedItem()!=null) {
						Kozmeticar kozm = (Kozmeticar) kozmeticariCB.getSelectedItem();
						KozmetickiTretman kt = zt.getVrstaTretmana();
						LocalDate dan = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalTime sat = (LocalTime) satnicaCB.getSelectedItem();
						LocalDateTime termin = LocalDateTime.of(dan, sat);
						mz.promeniZakazaniTretman(kozm, termin, kt, zt.getSifraZakazanogTretmana());
						mks.upisiID();
						if(IzmenaZakazanogTretmanaDialog.this.roditelj instanceof MenadzerFrame) {
							((MenadzerFrame)roditelj).osveziPodatke();
						}
						else if(IzmenaZakazanogTretmanaDialog.this.roditelj instanceof RecepcionerFrame) {
							((RecepcionerFrame)roditelj).osveziPodatke();
						}
						dispose();
				}
			}
		});
		getContentPane().add(potvrdi, "flowx,cell 2 5");
		
		odbaci = new JButton("Odbaci");
		odbaci.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		getContentPane().add(odbaci, "cell 2 5,growx");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		popuniPodatke();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void popuniPodatke() {
		klijentText.setText(zt.getKlijent().getKorisnickoIme());
		KozmetickiTretman kt1 =zt.getVrstaTretmana();
		HashMap<String, Kozmeticar> slobodniKozmeticari = mz.obuceniKozmeticariZaTretman(kt1.getTipTretmana().getSifra());
		for(Kozmeticar k: slobodniKozmeticari.values()) {
			kozmeticariCB.addItem(k);
		}
		kozmeticariCB.setSelectedItem(zt.getKozmeticar());
		
	}

}
