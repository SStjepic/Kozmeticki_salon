package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.RowSorterListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.ChartPart;

import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerZakazivanja;
import model.BilansSalonaModel;
import model.KlijentModel;
import model.RadKozmeticaraModel;
import model.RealizacijaTretmanaModel;
import model.RealizacijaZakazanihTretmanaModel;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import grafici.KozmeticariGrafik;
import gui.ZakazivanjeTretmanaDialog.izdvojKozmeticare;
import korisnici.Kozmeticar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IzvestajiDialog extends JDialog {

	private MenadzerKozmetickogSalona mks;
	private MenadzerZakazivanja mz;
	private JFrame roditelj;
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	
	private JButton prikazi;
	private JComboBox tipIzvestaja;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JDateChooser danOd;
	private JDateChooser danDo;
	private JButton btnNewButton;

	public IzvestajiDialog(MenadzerKozmetickogSalona mks, JFrame roditelj) {
		this.mks = mks;
		this.mz =  mks.getMenadzerZakazivanja();
		this.roditelj = roditelj;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 1025, 330);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][80.00,grow][145.00,grow][95.00,grow][178.00,grow]", "[grow][grow][][][][][][][][][]"));
		{
			JLabel lblNewLabel = new JLabel("Izvestaj");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel, "cell 1 0,alignx right");
		}
		{
			tipIzvestaja = new JComboBox();
			tipIzvestaja.addItem("Izveštaj o radu kozmetičara");
			tipIzvestaja.addItem("Izveštaj o realizaciji tretmanima");
			tipIzvestaja.addItem("Izveštaj o kozmetičkim tretmanima");
			tipIzvestaja.addItem("Prikaži prihod i rashod");
			tipIzvestaja.addItem("Prikaži klijente sa karticom lojalnosti");
			tipIzvestaja.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(tipIzvestaja.getSelectedIndex() == 4 ||tipIzvestaja.getSelectedIndex() == -1 ) {
						prikazi.setEnabled(true);
						danDo.setEnabled(false);
						danOd.setEnabled(false);
					}
					else {
						danDo.setEnabled(true);
						danOd.setEnabled(true);
					}
					
				}
			});
			contentPanel.add(tipIzvestaja, "cell 2 0,growx");
		}
		{
			lblNewLabel_1 = new JLabel("Od dana:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_1, "cell 3 0,alignx right");
		}
		{
			danOd = new JDateChooser();
			danOd.getDateEditor().addPropertyChangeListener("date", evt->{
				omoguciPrikaz();
			});
			contentPanel.add(danOd, "cell 4 0,grow");
		}
		{
			lblNewLabel_2 = new JLabel("Do dana:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_2, "cell 3 1,alignx right");
		}
		{
			danDo = new JDateChooser();
			danDo.getDateEditor().addPropertyChangeListener("date", evt->{
				omoguciPrikaz();
			});
			
			contentPanel.add(danDo, "cell 4 1,grow");
		}
		{
			prikazi = new JButton("Prikaži");
			prikazi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(prikazi.isEnabled()) {
						danDo.setEnabled(false);
						danOd.setEnabled(false);
						LocalDate odDana = null, doDana = null;
						if(tipIzvestaja.getSelectedIndex() != 4) {
							odDana = danOd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							doDana = danDo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						}
						
						if(tipIzvestaja.getSelectedIndex() == 0) {

							table.setModel(new RadKozmeticaraModel(mks, odDana, doDana));
							podesiTabelu();
							sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + "")); 
							prikazi.setEnabled(false);
							tipIzvestaja.setSelectedIndex(-1);
						}
						else if(tipIzvestaja.getSelectedIndex() == 1) {
							
							table.setModel(new RealizacijaZakazanihTretmanaModel(mks, odDana, doDana));
							podesiTabelu();
							sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + "")); 
							prikazi.setEnabled(false);
							tipIzvestaja.setSelectedIndex(-1);
						}
						else if(tipIzvestaja.getSelectedIndex() == 2) {
							
							table.setModel(new RealizacijaTretmanaModel(mks, odDana, doDana));
							podesiTabelu();
							sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + "")); 
							prikazi.setEnabled(false);
							tipIzvestaja.setSelectedIndex(-1);
						}else if(tipIzvestaja.getSelectedIndex() == 3) {
							
							table.setModel(new BilansSalonaModel(mz, odDana, doDana));
							podesiTabelu();
							sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + "")); 
							prikazi.setEnabled(false);
							tipIzvestaja.setSelectedIndex(-1);
						}else if(tipIzvestaja.getSelectedIndex() == 4) {
							
							table.setModel(new KlijentModel(mks.getMenadzerOsoba()));
							podesiTabelu();
							sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + "DA", 7)); 
							prikazi.setEnabled(false);
							tipIzvestaja.setSelectedIndex(-1);
						}
					}
				}
			});
			prikazi.setEnabled(false);
			contentPanel.add(prikazi, "flowx,cell 2 2,growx");
		}
		{
			table = new JTable();
			table.getTableHeader().setReorderingAllowed(false);
			JTableHeader header = table.getTableHeader();
			sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(sortiranjeTabela);
			JScrollPane scrollPane = new JScrollPane(table);
			table.setEnabled(false);
			contentPanel.add(scrollPane, "cell 1 3 4 8,grow");
		}
		{
			btnNewButton = new JButton("Odustani");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();				
				}
			});
			contentPanel.add(btnNewButton, "cell 2 2,growx");
		}
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void omoguciPrikaz() {
		if((danOd.getDate()!= null && danDo.getDate()!=null)) {
			prikazi.setEnabled(true);
		}
		
	}
	
	public void podesiTabelu() {
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
//		sortiranjeTabela =  new TableRowSorter<AbstractTableModel>();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());

	}

}
