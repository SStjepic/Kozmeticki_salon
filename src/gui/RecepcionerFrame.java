package gui;

import java.awt.Color;
import java.awt.EventQueue;

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

import gui.KlijentFrame.omoguciOtkazivanje;
import gui.ZakazivanjeTretmanaDialog.izdvojKozmetickeTretmane;
import korisnici.Kozmeticar;
import korisnici.Recepcioner;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerZakazivanja;
import model.ZakazanTretmanKlijentModel;
import model.ZakazanTretmanMenadzerModel;
import model.ZakazanTretmanRecepcionerModel;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;
import usluge.ZakazanTretman;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;

public class RecepcionerFrame extends JFrame {
	
	private MenadzerKozmetickogSalona mks;
	private MenadzerZakazivanja mz;
	private MenadzerKozmetickiTretmani mkt;
	
	private JPanel contentPane;
	private JTable table;
	private JComboBox tipCB;
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private JButton otkazaoKlijent, otkazaoSalon;
	private JTextField pretragaNaziv;
	private JTextField cenaOd, cenaDo;
	private JButton dugmeIzmeni;
	private JDateChooser datum;
	

	/**
	 * Create the frame.
	 */
	public RecepcionerFrame(MenadzerKozmetickogSalona mks, Recepcioner r) {
		this.mks = mks;
		this.mz = mks.getMenadzerZakazivanja();
		this.mkt = mks.getMenadzerTretmana();
		setTitle(mks.getKs() .getNazivKozmetickogSalona()+ " Korisnik: "+r.getIme() + " " + r.getPrezime());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][][][][grow][][grow][]", "[][][][][grow][][][][grow][]"));
		
		JLabel lblNewLabel_4 = new JLabel("Opcije");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel_4, "cell 1 0,alignx center");
		
		JLabel lblNewLabel = new JLabel("Filtrirajte tretmane");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel, "cell 6 0 3 1,alignx center");
		
		JButton btnNewButton = new JButton("Zakaži tretman");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ZakazivanjeTretmanaDialog ztd = new ZakazivanjeTretmanaDialog(mks, null, RecepcionerFrame.this);
			}
		});
		contentPane.add(btnNewButton, "cell 1 1,growx");
		
		JLabel lblNewLabel_1 = new JLabel("Tip tretmana");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_1, "cell 5 1,alignx right");
		
		tipCB = new JComboBox();
		for(TipKozmetickogTretmana tkt: mkt.getSviTipovi()) {
			tipCB.addItem(tkt);
		}
		tipCB.addItemListener(new izdvojKozmetickeTretmane());
		tipCB.setSelectedIndex(-1);
		contentPane.add(tipCB, "cell 7 1 2 1,growx");
		
		JButton btnNewButton_1 = new JButton("Svi zakazani tretmani");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + MenadzerKozmetickiTretmani.status.ZAKAZAN.toString()));
			}
		});
		
		JButton btnNewButton_2 = new JButton("Svi tretmani");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + ""));
			}
		});
		contentPane.add(btnNewButton_2, "cell 1 2,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Cena od/do");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_2, "cell 5 2,alignx right");
		
		cenaOd = new JTextField();
		contentPane.add(cenaOd, "cell 7 2,growx");
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
		
		cenaDo = new JTextField();
		contentPane.add(cenaDo, "cell 8 2,growx");
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
		contentPane.add(btnNewButton_1, "cell 1 3,growx");
		
		table = new JTable(new ZakazanTretmanMenadzerModel(mz));
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addListSelectionListener(new omoguciOtkazivanje());
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
		
		JLabel labelaNaziv = new JLabel("Naziv tretmana");
		labelaNaziv.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(labelaNaziv, "cell 5 3,alignx right");
		
		
		pretragaNaziv = new JTextField();
		contentPane.add(pretragaNaziv, "cell 7 3 2 1,growx");
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
		
		JLabel lblNewLabel_3 = new JLabel("Datum");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_3, "cell 5 4,alignx right");
		
		datum = new JDateChooser();
		datum.getDateEditor().addPropertyChangeListener("date", evt->{
			sortirajPrikaz();
		});
		contentPane.add(datum, "cell 7 4 2 1,grow");
		table.setRowSorter(sortiranjeTabela);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "cell 0 8 9 1,grow");
		
		dugmeIzmeni = new JButton("Izmeni tretman");
		dugmeIzmeni.setEnabled(false);
		dugmeIzmeni.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ZakazanTretman zt = (ZakazanTretman)table.getValueAt(table.getSelectedRow(), 1);
				IzmenaZakazanogTretmanaDialog izt = new IzmenaZakazanogTretmanaDialog(zt, mks, RecepcionerFrame.this);
			}
		});
		contentPane.add(dugmeIzmeni, "cell 1 4,growx");
		
		otkazaoSalon = new JButton("Otkazao salon");
		otkazaoSalon.setEnabled(false);
		otkazaoSalon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mz.promeniStatusZakazanogTretmana((ZakazanTretman)table.getValueAt(table.getSelectedRow(), 1), MenadzerKozmetickiTretmani.status.OTKAZAO_SALON.toString());
				osveziPodatke();
			}
		});
		
		otkazaoKlijent = new JButton("Otkazao klijent");
		otkazaoKlijent.setEnabled(false);
		otkazaoKlijent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mz.promeniStatusZakazanogTretmana((ZakazanTretman)table.getValueAt(table.getSelectedRow(), 1), MenadzerKozmetickiTretmani.status.OTKAZAO_KLIJENT.toString());
				osveziPodatke();
			}
		});
		contentPane.add(otkazaoKlijent, "flowx,cell 1 5,growx");
		contentPane.add(otkazaoSalon, "cell 1 5,growx");


		
		JButton btnNewButton_4 = new JButton("Odjavi se");
		contentPane.add(btnNewButton_4, "cell 8 9,growx");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RecepcionerFrame.this.dispose();
				LoginDialog ld = new LoginDialog(mks);
			}
		});
		this.setLocationRelativeTo(null);
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
	
	class omoguciOtkazivanje implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(table.getSelectedRow() != -1) {
				ZakazanTretman zt = (ZakazanTretman)table.getValueAt(table.getSelectedRow(), 1);
				if(zt.getTermin().isAfter(LocalDateTime.now()) && zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.toString()) ) {
					otkazaoSalon.setEnabled(true);
					otkazaoKlijent.setEnabled(true);
					dugmeIzmeni.setEnabled(true);
				}
				else {
					otkazaoSalon.setEnabled(false);
					otkazaoKlijent.setEnabled(false);
					dugmeIzmeni.setEnabled(false);
					
				}
			}
				
		}
	}
	
	public void sortirajPrikaz() {
		List<RowFilter<Object,Object>> filteri = new ArrayList<RowFilter<Object,Object>>(5);
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
			LocalDate dan = datum.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			filteri.add(RowFilter.regexFilter("" + dan.toString()));
		} catch (Exception e) {
		}
	    RowFilter<Object,Object> konacniFilteri = RowFilter.andFilter(filteri);
	    sortiranjeTabela.setRowFilter(konacniFilteri);
	}
	
	public void osveziPodatke() {
		ZakazanTretmanMenadzerModel sm = (ZakazanTretmanMenadzerModel)this.table.getModel();
		sm.fireTableDataChanged();
	}
}
