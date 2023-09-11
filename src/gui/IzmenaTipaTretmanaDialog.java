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
	private JButton potvrdi;
	private JTextField stariNaziv;

	public IzmenaTipaTretmanaDialog(MenadzerKozmetickiTretmani mkt, JFrame roditelj, TipKozmetickogTretmana tkt) {
		this.mkt = mkt;
		this.roditelj = roditelj;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 509, 214);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][97.00,grow][100.00][109.00]", "[][][][][][][]"));
		{
			JLabel lblNewLabel_2 = new JLabel("Tip tretmana");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_2, "cell 1 0,alignx trailing");
		}
		{
			stariNaziv = new JTextField();
			stariNaziv.setEditable(false);
			contentPanel.add(stariNaziv, "cell 2 0 2 1,growx");
			stariNaziv.setColumns(10);
			stariNaziv.setText(tkt.getNaziv());
		}
		{
			JLabel lblNewLabel = new JLabel("Šifra tipa tretmana");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel, "cell 1 1,alignx trailing");
		}
		{
			sifra = new JTextField();
			contentPanel.add(sifra, "cell 2 1 2 1,growx");
			sifra.setColumns(10);
			sifra.setEditable(false);
			Integer s = tkt.getSifra();
			sifra.setText(s.toString());
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
			naziv.setText(tkt.getNaziv());
		}
		{
			potvrdi = new JButton("Potvrdi");
			potvrdi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String noviNaziv = naziv.getText();
					if(!noviNaziv.equals(tkt.getNaziv()) && mkt.proveriJedinstvenostTipaTretmana(noviNaziv)) {
						mkt.azurirajTipKozmetickogTretmana(tkt.getSifra(), noviNaziv);
						((MenadzerFrame)roditelj).osveziPodatke();
						dispose();
					}
					else {
						validacija.setText("Novi naziv tipa tretmana postoji u sistemu");
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
		
		
		
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
