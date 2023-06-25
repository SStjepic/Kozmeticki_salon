package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import menadzeri.MenadzerKozmetickogSalona;
import model.KozmeticarModel;
import model.RadKozmeticaraModel;
import model.RealizacijaTretmanaModel;
import model.RealizacijaZakazanihTretmanaModel;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import com.toedter.calendar.JDateChooser;

import gui.ZakazivanjeTretmanaDialog.izdvojKozmetickeTretmane;

import javax.swing.JTable;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class DodelaBonusaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private MenadzerKozmetickogSalona mks;
	private JTextField granica;
	private JTable table;
	private JButton dodeli;
	private JDateChooser danOd, danDo;
	private JComboBox kriterijum; 
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private JTextField bonus;
	private JLabel lblNewLabel_1;
	private JFrame roditelj;
	private JLabel kriterijumText;
	private JButton btnNewButton;

	public DodelaBonusaDialog(MenadzerKozmetickogSalona mks, JFrame roditelj) {
		this.mks= mks;
		this.roditelj = roditelj;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 628, 375);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[172.00,grow][234.00,grow][230.00,grow]", "[][][][][][][][grow]"));
		{
			JLabel lblNewLabel = new JLabel("Izaberite kriterijum");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
		}
		{
			kriterijum = new JComboBox();
			kriterijum.addItem("Bonus za prihod");
			kriterijum.addItem("Bonus za odradjene tretmane");
			kriterijum.addItemListener(new promeniText());
			kriterijum.setSelectedIndex(-1);
			contentPanel.add(kriterijum, "cell 1 0,growx");
		}
		{
			kriterijumText = new JLabel("");
			kriterijumText.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(kriterijumText, "cell 0 1,alignx trailing");
		}
		{
			granica = new JTextField();
			contentPanel.add(granica, "cell 1 1,growx");
			granica.setColumns(10);
		}
		{
			table = new JTable();
			table.getTableHeader().setReorderingAllowed(false);
			JTableHeader header = table.getTableHeader();
			sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
			{
				dodeli = new JButton("Dodeli bonus");
				dodeli.setEnabled(false);
				dodeli.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(dodeli.isEnabled()) {
							LocalDate odDana = danOd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							LocalDate doDana = danDo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							if(kriterijum.getSelectedIndex() == 0) {
								mks.getMenadzerZakazivanja().dodeliBonusKozmeticarima(Double.parseDouble(granica.getText()),Double.parseDouble(bonus.getText()) , odDana, doDana);
								table.setModel(new KozmeticarModel(mks.getMenadzerOsoba(), mks.getMenadzerTretmana()));
								podesiTabelu();
								dodeli.setEnabled(false);
								kriterijum.setSelectedIndex(-1);
								((MenadzerFrame)roditelj).osveziPodatke();
							}
							else if(kriterijum.getSelectedIndex() == 1) {
								mks.getMenadzerZakazivanja().dodeliBonusKozmeticarima(Integer.parseInt (granica.getText()),Double.parseDouble(bonus.getText()) , odDana, doDana);
								table.setModel(new KozmeticarModel(mks.getMenadzerOsoba(), mks.getMenadzerTretmana()));
								podesiTabelu();
								dodeli.setEnabled(false);
								kriterijum.setSelectedIndex(-1);
								((MenadzerFrame)roditelj).osveziPodatke();
							}
						}
					}
				});
				{
				danDo = new JDateChooser();
				danDo.getDateEditor().addPropertyChangeListener("date", evt->{
					omoguciDedelu();
				});
				{
				danOd = new JDateChooser();
				danOd.getDateEditor().addPropertyChangeListener("date", evt->{
					omoguciDedelu();
				});
				{
					lblNewLabel_1 = new JLabel("Bonus");
					lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
					contentPanel.add(lblNewLabel_1, "cell 0 2,alignx trailing");
				}
				{
					bonus = new JTextField();
					contentPanel.add(bonus, "cell 1 2,growx");
					bonus.setColumns(10);
				}
				{
					JLabel lblNewLabel_2 = new JLabel("Datum od");
					lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
					contentPanel.add(lblNewLabel_2, "cell 0 3,alignx trailing");
				}
				contentPanel.add(danOd, "cell 1 3,grow");
				}
				{
					JLabel lblNewLabel_3 = new JLabel("Datum do");
					lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
					contentPanel.add(lblNewLabel_3, "cell 0 5,alignx right");
				}
				contentPanel.add(danDo, "cell 1 5,grow");
				}
				contentPanel.add(dodeli, "flowx,cell 1 6,growx");
			}
			table.setRowSorter(sortiranjeTabela);
			JScrollPane scrollPane = new JScrollPane(table);
			table.setEnabled(false);
			contentPanel.add(scrollPane, "cell 0 7 3 1,grow");
		}
		{
			btnNewButton = new JButton("Izađi");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
				}
			});
			contentPanel.add(btnNewButton, "cell 1 6,growx");
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void omoguciDedelu() {
		if(danOd.getDate()!= null && danDo.getDate()!=null) {
			dodeli.setEnabled(true);
		}
	}
	
	public void podesiTabelu() {
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());

	}
	class promeniText implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if(kriterijum.getSelectedIndex() == 0) {
				kriterijumText.setText("Ostvaren prihod");
			}else if(kriterijum.getSelectedIndex() == 1) {
				kriterijumText.setText("Izvršeno tretmana");
			}
		}
	}

}
