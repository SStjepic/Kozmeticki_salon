package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.ZakazivanjeTretmanaDialog.izdvojKozmetickeTretmane;
import menadzeri.MenadzerKozmetickiTretmani;
import model.ZakazanTretmanMenadzerModel;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class IzmenaTipaTretmanaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField naziv;
	private JTextField sifra;
	private MenadzerKozmetickiTretmani mkt;
	private JFrame roditelj;
	private TipKozmetickogTretmana tkt;
	private JLabel validacija;
	private JComboBox comboBox;
	private JButton potvrdi;
	private JButton btnNewButton;

	public IzmenaTipaTretmanaDialog(MenadzerKozmetickiTretmani mkt, JFrame roditelj) {
		this.mkt = mkt;
		this.roditelj = roditelj;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 509, 214);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][97.00,grow][100.00][109.00]", "[][][][][][][]"));
		{
			JLabel lblNewLabel_2 = new JLabel("Tipovi tretmana");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_2, "cell 1 0,alignx trailing");
		}
		{
			comboBox = new JComboBox();
			for(TipKozmetickogTretmana tt: mkt.getSviTipovi()) {
				comboBox.addItem(tt);
			}
			comboBox.setSelectedIndex(-1);
			contentPanel.add(comboBox, "cell 2 0 2 1,growx");
			comboBox.addItemListener(new zapocniIzmenu());
		}
		{
			JLabel lblNewLabel = new JLabel("Šifra tipatretmana");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel, "cell 1 1,alignx trailing");
		}
		{
			sifra = new JTextField();
			contentPanel.add(sifra, "cell 2 1 2 1,growx");
			sifra.setColumns(10);
			sifra.setEditable(false);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Naziv");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_1, "cell 1 2,alignx trailing");
		}
		{
			naziv = new JTextField();
			contentPanel.add(naziv, "cell 2 2 2 1,growx");
			naziv.setColumns(10);
		}
		{
			potvrdi = new JButton("Potvrdi");
			potvrdi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						TipKozmetickogTretmana tkt = (TipKozmetickogTretmana) comboBox.getSelectedItem();
						if((mkt.proveriJedinstvenostKozmetickogTretmana(naziv.getText().trim())||naziv.getText().trim().equals(tkt.getNaziv())) && !naziv.getText().trim().equals("")) {
							validacija.setText("");
							mkt.azurirajTipKozmetickogTretmana(tkt.getSifra(), naziv.getText().trim());
							((MenadzerFrame)roditelj).osveziPodatke();
							potvrdi.setEnabled(false);
							comboBox.setEditable(true);
						}
						else {
							validacija.setText("Naziv je prazan string ili već postoji u sistemu");
						}
					} catch (Exception e2) {
					}

				}
			});
			{
				validacija = new JLabel("");
				contentPanel.add(validacija, "cell 4 2");
			}
			contentPanel.add(potvrdi, "cell 2 5,growx");
		}
		{
			JButton btnNewButton_1 = new JButton("Odustani");
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
				}
			});
			contentPanel.add(btnNewButton_1, "cell 3 5,growx");
		}
		{
			btnNewButton = new JButton("Obriši");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						TipKozmetickogTretmana tkt = (TipKozmetickogTretmana) comboBox.getSelectedItem();
						mkt.obrisiTipKozmetickogTretmana(tkt.getSifra());
						((MenadzerFrame)roditelj).osveziPodatke();
						dispose();
					} catch (Exception e2) {
					}

				}
			});
			contentPanel.add(btnNewButton, "cell 4 5,growx");
		}
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	class zapocniIzmenu implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Integer sifraTipa = ((TipKozmetickogTretmana)comboBox.getSelectedItem()).getSifra();
			sifra.setText(sifraTipa.toString());
			naziv.setText(((TipKozmetickogTretmana)comboBox.getSelectedItem()).getNaziv());
			potvrdi.setEnabled(true);
			comboBox.setEditable(false);
		}
	}
}
