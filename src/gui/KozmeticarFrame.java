package gui;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import korisnici.Kozmeticar;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerZakazivanja;
import model.ZakazanTretmanRecepcionerModel;
import net.miginfocom.swing.MigLayout;
import usluge.ZakazanTretman;
import com.toedter.calendar.JDateChooser;

public class KozmeticarFrame extends JFrame {

	private MenadzerZakazivanja mz;
	
	private JDateChooser dateChooser;
	private JButton dugmeIzvrsi, dugmeNijeDosao, dugmeTermini;
	private JPanel contentPane;
	private JTable table;
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	
	
	public KozmeticarFrame(MenadzerKozmetickogSalona mks, Kozmeticar k) {
		this.mz = mks.getMenadzerZakazivanja();
		setTitle(mks.getKs() .getNazivKozmetickogSalona()+ " Korisnik: "+k.getIme() + " " + k.getPrezime());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][grow][][][grow][][grow][]", "[][grow][][][grow][][][grow][]"));
		
		JLabel lblNewLabel_4 = new JLabel("Opcije");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel_4, "cell 1 0,alignx center");
		
		JLabel lblNewLabel = new JLabel("Filtriraj zakazane tretmane");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel, "cell 3 0 4 1,alignx center");
		
		dugmeNijeDosao = new JButton("Klijent se nije pojavio");
		dugmeNijeDosao.setEnabled(false);
		dugmeNijeDosao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mz.promeniStatusZakazanogTretmana((ZakazanTretman)table.getValueAt(table.getSelectedRow(), 0), MenadzerKozmetickiTretmani.status.NIJE_SE_POJAVIO.toString());
				osveziPodatke();
			}
		});
		contentPane.add(dugmeNijeDosao, "cell 1 1,growx");
		
		dugmeTermini = new JButton("Moji tretmani");
		dugmeTermini.setEnabled(false);
		dugmeTermini.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String dan = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + dan));
			}
		});
		contentPane.add(dugmeTermini, "cell 3 1 4 1,growx");
		
		dateChooser = new JDateChooser();
		contentPane.add(dateChooser, "cell 7 1 2 1,growx");
		dateChooser.getDateEditor().addPropertyChangeListener("date", evt->{
			dugmeTermini.setEnabled(true);

		});
		
		table = new JTable(new ZakazanTretmanRecepcionerModel(mz, null));
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addListSelectionListener(new omoguciIzmenu());
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
		
		dugmeIzvrsi = new JButton("Izvrši tretman");
		dugmeIzvrsi.setEnabled(false);
		dugmeIzvrsi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mz.promeniStatusZakazanogTretmana((ZakazanTretman)table.getValueAt(table.getSelectedRow(), 0), MenadzerKozmetickiTretmani.status.IZVRSEN.toString());
				osveziPodatke();
			}
		});
		contentPane.add(dugmeIzvrsi, "cell 1 2,growx");
		
		JButton btnNewButton_2 = new JButton("Svi tretmani");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + ""));
				dateChooser.setDate(null);
				dugmeTermini.setEnabled(false);
			}
		});
		contentPane.add(btnNewButton_2, "cell 3 2 4 1,grow");


		table.setRowSorter(sortiranjeTabela);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "cell 0 7 9 1,grow");
		
		JButton btnNewButton_4 = new JButton("Odjavi se");
		contentPane.add(btnNewButton_4, "cell 8 8,growx");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				KozmeticarFrame.this.dispose();
				LoginDialog ld = new LoginDialog(mks);
			}
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	class omoguciIzmenu implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(table.getSelectedRow() != -1) {
				ZakazanTretman zt = (ZakazanTretman)table.getValueAt(table.getSelectedRow(), 0);
				if(zt.getTermin().isBefore(LocalDateTime.now()) && zt.getStatusTretmana().equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.toString()) ) {
					dugmeIzvrsi.setEnabled(true);
					dugmeNijeDosao.setEnabled(true);
				}
				else {
					dugmeIzvrsi.setEnabled(false);
					dugmeNijeDosao.setEnabled(false);
					
				}
			}
				
		}
	}
	
	public void osveziPodatke() {
		ZakazanTretmanRecepcionerModel sm = (ZakazanTretmanRecepcionerModel)this.table.getModel();
		sm.fireTableDataChanged();
	}

}
