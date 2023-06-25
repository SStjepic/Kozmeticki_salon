package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

import com.toedter.calendar.JDateChooser;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import main.KozmetickiSalon;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerZakazivanja;
import model.KozmetickiTretmanModel;
import model.ZakazanTretmanKlijentModel;
import model.ZakazanTretmanMenadzerModel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.RowFilter.ComparisonType;

public class ZakazivanjeTretmanaDialog extends JDialog {
	private JTextField klijentText;
	private JTable table;
	private MenadzerZakazivanja mz;
	private MenadzerKozmetickiTretmani mkt;
	private MenadzerKozmetickogSalona mks;
	private Klijent k;
	private JFrame roditelj;
	
	private JComboBox tipCB;
	private JComboBox kozmeticariCB;
	private JComboBox satnicaCB;
	private JDateChooser dateChooser;
	private JLabel datumValidacija, korisnikValidacija;

	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private JTextField cenaOd;
	private JTextField cenaDo;
	private JTextField pretragaNaziv;
	private JTextField cenaKlijent;
	private JLabel lblNewLabel_9;
	private JTextField vremeOd;
	private JTextField vremeDo;

	/**
	 * Create the dialog.
	 */
	public ZakazivanjeTretmanaDialog(MenadzerKozmetickogSalona mks, Klijent k, JFrame roditelj) {
		this.roditelj = roditelj;
		this.k = k;
		this.mks = mks;
		this.mz = mks.getMenadzerZakazivanja();
		this.mkt =mks.getMenadzerTretmana();
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 972, 473);
		getContentPane().setLayout(new MigLayout("", "[][][][150.00][75.00][64.00,grow][75.00,leading][][113.00,grow][110.00,grow][grow][grow][][][67.00,trailing]", "[][][][][][][][][20.00][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Klijent");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel, "cell 1 0,alignx trailing");
		
		klijentText = new JTextField();
		getContentPane().add(klijentText, "cell 2 0,growx");
		klijentText.setColumns(20);
		if(k != null) {
			klijentText.setText(k.getKorisnickoIme());
			klijentText.setEnabled(false);
		}
		else {
		}
		
		korisnikValidacija = new JLabel("");
		getContentPane().add(korisnikValidacija, "cell 3 0");
		
		JLabel lblNewLabel_2 = new JLabel("Pretraga");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_2, "cell 7 0 3 1,alignx center");
		
		table = new JTable(new KozmetickiTretmanModel(mkt));
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addListSelectionListener(new izdvojKozmeticare());
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(sortiranjeTabela);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "cell 1 1 6 7,grow");
		
		JLabel lblNewLabel_1 = new JLabel("Tip tretmana");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblNewLabel_1, "cell 5 1 3 1,alignx right");
		
		tipCB = new JComboBox();

		for(TipKozmetickogTretmana tkt: mkt.getSviTipovi()) {
			tipCB.addItem(tkt);
		}
		tipCB.setSelectedIndex(-1);
		getContentPane().add(tipCB, "cell 8 1 2 1,growx");
		tipCB.addItemListener(new izdvojKozmetickeTretmane());
		
		JLabel lblNewLabel_6 = new JLabel("Cena od/do");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblNewLabel_6, "cell 7 3,alignx trailing");
		
		cenaOd = new JTextField();
		getContentPane().add(cenaOd, "flowx,cell 8 3,growx");
		cenaOd.setColumns(10);
		cenaOd.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				sortirajPrikaz();
			}
		});
		
		JLabel lblNewLabel_7 = new JLabel("Naziv tretmana");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblNewLabel_7, "cell 7 4,alignx right");
		
		pretragaNaziv = new JTextField();
		getContentPane().add(pretragaNaziv, "cell 8 4 2 1,growx");
		pretragaNaziv.setColumns(10);
		pretragaNaziv.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				//System.out.println("~ "+tfSearch.getText());
				sortirajPrikaz();
			}
		});
		
		lblNewLabel_9 = new JLabel("Vreme trajanja od/do");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblNewLabel_9, "cell 7 5,alignx trailing");
		
		vremeOd = new JTextField();
		getContentPane().add(vremeOd, "cell 8 5,growx");
		vremeOd.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				sortirajPrikaz();
			}
		});
		vremeOd.setColumns(10);
		
		vremeDo = new JTextField();
		getContentPane().add(vremeDo, "cell 9 5,growx");
		vremeDo.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				sortirajPrikaz();
			}
		});
		vremeDo.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Cena za klijenta");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_8, "cell 4 9,alignx trailing");
		
		cenaKlijent = new JTextField();
		getContentPane().add(cenaKlijent, "cell 5 9 2 1,growx");
		cenaKlijent.setColumns(10);
		
		datumValidacija = new JLabel("");
		datumValidacija.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(datumValidacija, "cell 3 10");
		
		cenaDo = new JTextField();
		getContentPane().add(cenaDo, "cell 9 3,growx");
		cenaDo.setColumns(10);
		cenaDo.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				sortirajPrikaz();
			}
		});
		
		JButton btnNewButton = new JButton("Odustani");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Izgasi();
			}
		});
		
		JButton btnNewButton_1 = new JButton("Potvrdi");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(kozmeticariCB.getSelectedObjects()!=null && dateChooser.getDate()!=null && satnicaCB.getSelectedItem()!=null) {
					if(ZakazivanjeTretmanaDialog.this.k ==null) {
						ZakazivanjeTretmanaDialog.this.k  = (Klijent) mks.getMenadzerOsoba().nadjiOsobu(klijentText.getText());
						if(ZakazivanjeTretmanaDialog.this.k != null) {
							korisnikValidacija.setText("");
							klijentText.setEditable(false);
						}
						else {
							korisnikValidacija.setText("Niste uneli korisnkičko ime klijenta");
						}
					}
					if(ZakazivanjeTretmanaDialog.this.k !=null) {
						korisnikValidacija.setText("");
						Kozmeticar kozm = (Kozmeticar) kozmeticariCB.getSelectedItem();
						KozmetickiTretman kt = (KozmetickiTretman)table.getValueAt(table.getSelectedRow(), 0);
						LocalDate dan = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalTime sat = (LocalTime) satnicaCB.getSelectedItem();
						LocalDateTime termin = LocalDateTime.of(dan, sat);
						mz.zakaziKozmetickiTretman(ZakazivanjeTretmanaDialog.this.k, kozm, termin, kt);
						mks.upisiID();
						if(ZakazivanjeTretmanaDialog.this.roditelj instanceof KlijentFrame) {
							((KlijentFrame)roditelj).osveziPodatke();
						}
						else if(ZakazivanjeTretmanaDialog.this.roditelj instanceof RecepcionerFrame) {
							((RecepcionerFrame)roditelj).osveziPodatke();
						}else if(ZakazivanjeTretmanaDialog.this.roditelj instanceof MenadzerFrame) {
							((MenadzerFrame)roditelj).osveziPodatke();
						}
						
						Izgasi();
					}
					else {
						korisnikValidacija.setText("Unesite korisničko ime klijenta");
					}
					
				}
				
			}
		});
		
		dateChooser = new JDateChooser();
		dateChooser.getDateEditor().addPropertyChangeListener("date", evt->{
			satnicaCB.removeAllItems();
			if(kozmeticariCB.getSelectedItem()!=null) {
				LocalDate dan = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if(!dan.isBefore(LocalDate.now())) {
					datumValidacija.setText("");
					Kozmeticar odabraniKozmeticar =(Kozmeticar) kozmeticariCB.getSelectedItem();
					KozmetickiTretman odabraniTretman =(KozmetickiTretman)table.getValueAt(table.getSelectedRow(), 0);
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
		
		JLabel lblNewLabel_5 = new JLabel("Kozmeticar");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_5, "cell 1 9,alignx trailing");
		
		kozmeticariCB = new JComboBox();
		getContentPane().add(kozmeticariCB, "cell 2 9,growx");
		
		JLabel lblNewLabel_3 = new JLabel("Datum");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_3, "cell 1 10,alignx right");
		getContentPane().add(dateChooser, "cell 2 10,growx,aligny top");
		
		JLabel lblNewLabel_4 = new JLabel("Vreme");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNewLabel_4, "cell 1 11,alignx trailing");
		
		satnicaCB = new JComboBox();
		getContentPane().add(satnicaCB, "cell 2 11,growx");
		
		getContentPane().add(btnNewButton_1, "flowx,cell 2 14,growx");
		getContentPane().add(btnNewButton, "cell 2 14,growx");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	class izdvojKozmetickeTretmane implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			ArrayList<KozmetickiTretman> tkt = mkt.odgovarajuciKozmetickiTretmani((TipKozmetickogTretmana) tipCB.getSelectedItem());
			
			if(tkt.isEmpty()) {
			}
			else {
				sortirajPrikaz();
			}
			}
		}
	
	public void sortirajPrikaz() {
		List<RowFilter<Object,Object>> filteri = new ArrayList<RowFilter<Object,Object>>(6);
		try {
		    filteri.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(cenaDo.getText().trim())+1, 3));
		} catch (Exception e) {
		}
		try {
		    filteri.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(cenaOd.getText().trim())-1, 3));
		} catch (Exception e) {
		}
		if(tipCB.getSelectedItem()!=null) {
			filteri.add(RowFilter.regexFilter("(?i)" + ((TipKozmetickogTretmana)tipCB.getSelectedItem()).getNaziv()));
		}
		if(!pretragaNaziv.getText().trim().equals("")) {
			filteri.add(RowFilter.regexFilter("(?i)" + pretragaNaziv.getText().trim()));
		}
		try {
			int vreme = Integer.parseInt(vremeOd.getText().trim());
			filteri.add(RowFilter.numberFilter(ComparisonType.AFTER, vreme-1, 2));
		}catch(Exception e) {
			
		}
		try {
			int vreme = Integer.parseInt(vremeDo.getText().trim());
			filteri.add(RowFilter.numberFilter(ComparisonType.BEFORE, vreme+1, 2));
		}catch(Exception e) {
			
		}
	    RowFilter<Object,Object> konacniFilteri = RowFilter.andFilter(filteri);
	    sortiranjeTabela.setRowFilter(konacniFilteri);
	}
	
	public void osveziPodatke() {
		ZakazanTretmanMenadzerModel sm = (ZakazanTretmanMenadzerModel)this.table.getModel();
		sm.fireTableDataChanged();
	}
	
	class izdvojKozmeticare implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			try {
				kozmeticariCB.removeAllItems();
				KozmetickiTretman kt1 =(KozmetickiTretman)table.getValueAt(table.getSelectedRow(), 0);
				HashMap<String, Kozmeticar> slobodniKozmeticari = mz.obuceniKozmeticariZaTretman(kt1.getTipTretmana().getSifra());
				for(Kozmeticar k: slobodniKozmeticari.values()) {
					kozmeticariCB.addItem(k);
				}
				kozmeticariCB.setSelectedIndex(0);
				if(k == null) {
						k = (Klijent) mks.getMenadzerOsoba().nadjiOsobu(klijentText.getText());
						if(k != null) {
							korisnikValidacija.setText("");
							klijentText.setEditable(false);
						}
						else {
							korisnikValidacija.setText("Niste uneli korisnkičko ime klijenta");
						}

				}
				cenaKlijent.setText(((Double)mz.odrediCenuKozmetickogTretmana(k, kt1)).toString());
			}catch (Exception ex) {
			}
			
		}
	}
	
	public void Izgasi() {
		dispose();
	}
}
