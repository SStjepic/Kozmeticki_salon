package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import gui.ZakazivanjeTretmanaDialog.izdvojKozmeticare;
import korisnici.Klijent;
import korisnici.Kozmeticar;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerZakazivanja;
import model.KozmetickiTretmanModel;
import model.ZakazanTretmanKlijentModel;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.ZakazanTretman;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.HashMap;

public class KlijentFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private MenadzerZakazivanja mz;
	private Klijent k;
	private MenadzerKozmetickogSalona mks;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JLabel lblNewLabel;
	private JTextField potrosioText;
	private JLabel lblNewLabel_1;
	private JTextField karticaText;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	
	public KlijentFrame(MenadzerKozmetickogSalona mks, Klijent k) {
		this.mks = mks;
		this.mz = mks.getMenadzerZakazivanja();
		this.k = k;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 682, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle(mks.getKs() .getNazivKozmetickogSalona()+ " Korisnik: "+k.getIme() + " " + k.getPrezime());

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][]", "[][][][][][][][][][][][][][][]"));
				

				
		table = new JTable(new ZakazanTretmanKlijentModel(mz, k));
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addListSelectionListener(new omoguciOtkazivanje());
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(sortiranjeTabela);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "cell 0 8 2 5,grow");
		
		lblNewLabel_2 = new JLabel("Moji tretmani");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel_2, "cell 0 7 2 1,alignx center,aligny baseline");
		
		lblNewLabel = new JLabel("Ukupna potrošnja: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel, "flowx,cell 0 0");
		
		potrosioText = new JTextField();
		potrosioText.setEditable(false);
		contentPane.add(potrosioText, "cell 0 0");
		potrosioText.setColumns(10);
		potrosioText.setText(potrosioTekst());
		
		lblNewLabel_1 = new JLabel("Kartica lojalnosti: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_1, "cell 0 0");
		
		karticaText = new JTextField();
		karticaText.setEditable(false);
		karticaText.setColumns(10);
		karticaText.setText(karticaTekst());
		contentPane.add(karticaText, "cell 0 0");
		
		btnNewButton_5 = new JButton("Svi moji tretmani");
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + ""));
			}
		});
		contentPane.add(btnNewButton_5, "flowx,cell 0 2,grow");
		
		btnNewButton = new JButton("Moji zakazani tretmnai");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + MenadzerKozmetickiTretmani.status.ZAKAZAN.toString()));
			}
		});
		contentPane.add(btnNewButton, "cell 0 2,grow");
		
		btnNewButton_1 = new JButton("Moji otkazani tretmani");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + MenadzerKozmetickiTretmani.status.OTKAZAO_KLIJENT.toString()));
			}
		});
		contentPane.add(btnNewButton_1, "cell 0 2,grow");
		
		btnNewButton_2 = new JButton("Moji izvrseni tretmani");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + MenadzerKozmetickiTretmani.status.IZVRSEN.toString()));
			}
		});
		contentPane.add(btnNewButton_2, "cell 0 2 2 1,grow");
		
		btnNewButton_3 = new JButton("Zakazi tretman");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ZakazivanjeTretmanaDialog ztd = new ZakazivanjeTretmanaDialog(mks, k, KlijentFrame.this);
			}
		});
		contentPane.add(btnNewButton_3, "flowx,cell 0 5,alignx left,grow");
		
		btnNewButton_4 = new JButton("Otkazi tetman");
		btnNewButton_4.setEnabled(false);
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnNewButton_4.isEnabled()) {
					mz.promeniStatusZakazanogTretmana((ZakazanTretman)table.getValueAt(table.getSelectedRow(), 0), MenadzerKozmetickiTretmani.status.OTKAZAO_KLIJENT.toString());
					osveziPodatke();
				}
			}
		});
		btnNewButton_4.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(btnNewButton_4, "cell 0 5,alignx right,grow");
		
		btnNewButton_6 = new JButton("Odjavi se");
		btnNewButton_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				KlijentFrame.this.dispose();
				LoginDialog ld = new LoginDialog(mks);
			}
		});
		contentPane.add(btnNewButton_6, "cell 0 14,alignx right");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	public String karticaTekst() {
		if(k.getKarticaLojalnosti()) {
			return "DA";
		}
		else {
			return "NE";
		}
	}
	public String potrosioTekst() {
		Double potrosnja = k.getPotrosio();
		return potrosnja.toString();
	}
	
	public void osveziPodatke() {
		ZakazanTretmanKlijentModel sm = (ZakazanTretmanKlijentModel)this.table.getModel();
		sm.fireTableDataChanged();
		potrosioText.setText(potrosioTekst());
	}
	
	class omoguciOtkazivanje implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(table.getSelectedRow() != -1) {
				ZakazanTretman zt = (ZakazanTretman)table.getValueAt(table.getSelectedRow(), 0);
				if(zt.getTermin().isAfter(LocalDateTime.now()) && zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.toString()) ) {
					btnNewButton_4.setEnabled(true);
				}
				else {
					btnNewButton_4.setEnabled(false);
					
				}
			}
				
		}
	}
}