package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerOsoba.sprema;
import net.miginfocom.swing.MigLayout;
import usluge.TipKozmetickogTretmana;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class RegistracijaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField korisnickoImeText;
	private JTextField lozinkaText;
	private MenadzerKozmetickogSalona mks;
	private JTextField prezimeText;
	private JTextField imeText;
	private JTextField polText;
	private JTextField brojTelefonaText;
	private JTextField adresaText;
	private JTextField plata;
	private JComboBox sprema;
	private JCheckBox[] checkboxes;
	private JLabel imeValidacija, prezimeValidacija, korisnickoImeValidacija, lozinkaValidacija, polValidacija, brojTelefonaValidacija, adresaValidacija, plataValidacija, spremaValidacija, tipoviValidacija;

	/**
	 * Create the dialog.
	 */
	public RegistracijaDialog(MenadzerKozmetickogSalona mks, String korisnik, JFrame roditelj) {
		this.mks = mks;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 600, 500);
		getContentPane().setLayout(new MigLayout("", "[][][][]", "[][][][][][][][][][][][][][][]"));
		{
			{
				JLabel lblNewLabel_2 = new JLabel("Ime:");
				getContentPane().add(lblNewLabel_2, "cell 1 0");
			}
			{
				imeText = new JTextField();
				getContentPane().add(imeText, "cell 2 0,growx");
				imeText.setColumns(20);
			}
			{
				imeValidacija = new JLabel("");
				getContentPane().add(imeValidacija, "cell 3 0");
			}
			{
				JLabel lblNewLabel_3 = new JLabel("Prezime:");
				getContentPane().add(lblNewLabel_3, "cell 1 1");
			}
			{
				prezimeText = new JTextField();
				getContentPane().add(prezimeText, "cell 2 1,growx");
				prezimeText.setColumns(20);
			}
			{
				prezimeValidacija = new JLabel("");
				getContentPane().add(prezimeValidacija, "cell 3 1");
			}
			{
				JLabel lblNewLabel = new JLabel("Korisnicko ime: ");
				getContentPane().add(lblNewLabel, "cell 1 2");
			}
			{
				korisnickoImeText = new JTextField();
				getContentPane().add(korisnickoImeText, "cell 2 2,growx");
				korisnickoImeText.setColumns(20);
			}
			{
				korisnickoImeValidacija = new JLabel("");
				getContentPane().add(korisnickoImeValidacija, "cell 3 2");
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Lozinka:");
				getContentPane().add(lblNewLabel_1, "cell 1 3");
			}
			{
				lozinkaText = new JTextField();
				getContentPane().add(lozinkaText, "cell 2 3,growx");
				lozinkaText.setColumns(20);
			}
			{
				lozinkaValidacija = new JLabel("");
				getContentPane().add(lozinkaValidacija, "cell 3 3");
			}
			{
				JLabel lblNewLabel_4 = new JLabel("Pol:");
				getContentPane().add(lblNewLabel_4, "cell 1 4");
			}
			{
				polText = new JTextField();
				getContentPane().add(polText, "cell 2 4,growx");
				polText.setColumns(20);
			}
			{
				polValidacija = new JLabel("");
				getContentPane().add(polValidacija, "cell 3 4");
			}
			{
				JLabel lblNewLabel_5 = new JLabel("Broj telefona:");
				getContentPane().add(lblNewLabel_5, "cell 1 5");
			}
			{
				brojTelefonaText = new JTextField();
				getContentPane().add(brojTelefonaText, "cell 2 5,growx");
				brojTelefonaText.setColumns(20);
			}
			{
				brojTelefonaValidacija = new JLabel("");
				getContentPane().add(brojTelefonaValidacija, "cell 3 5");
			}
			{
				JLabel lblNewLabel_6 = new JLabel("Adresa:");
				getContentPane().add(lblNewLabel_6, "cell 1 6");
			}
		}
		{
			adresaText = new JTextField();
			getContentPane().add(adresaText, "cell 2 6,growx");
			adresaText.setColumns(20);
		}
		{
			adresaValidacija = new JLabel("");
			getContentPane().add(adresaValidacija, "cell 3 6");
		}
		if(!korisnik.equals("klijent")){
			{
				JLabel lblNewLabel_7 = new JLabel("Stručna sprema:");
				getContentPane().add(lblNewLabel_7, "cell 1 7,alignx left");
			}
			{

				String[] lista = new String[3];
				int index = 0;
				for(MenadzerOsoba.sprema s: MenadzerOsoba.sprema.values()) {
					lista[index] = s.name();
					index++;
				}
				sprema = new JComboBox(lista);
				sprema.setMaximumRowCount(3);
				sprema.setSelectedIndex(-1);
				getContentPane().add(sprema, "cell 2 7,growx");
			}
			{
				spremaValidacija = new JLabel("");
				getContentPane().add(spremaValidacija, "cell 3 7");
			}
			
			{
				JLabel lblNewLabel_8 = new JLabel("Platna osova:");
				getContentPane().add(lblNewLabel_8, "cell 1 8,alignx left");
			}
			{
				plata = new JTextField();
				getContentPane().add(plata, "cell 2 8,growx");
				plata.setColumns(20);
			}
			{
				plataValidacija = new JLabel("");
				getContentPane().add(plataValidacija, "cell 3 8");
			}
		}
		if(korisnik.equals("kozmeticar")) {
			
			JLabel lblNewLabel_8 = new JLabel("Tip tretmana:");
			getContentPane().add(lblNewLabel_8, "cell 1 9,alignx left");
			checkboxes = new JCheckBox[mks.getMenadzerTretmana().getSviTipovi().size()];
			int i = 0;
			int poljey = 9;
			int poljex = 2;
			for(TipKozmetickogTretmana tkt: mks.getMenadzerTretmana().getSviTipovi()) {
				checkboxes[i] = new JCheckBox(tkt.getNaziv());
				getContentPane().add(checkboxes[i], "cell " + poljex +" " + poljey);
				i++;
				poljey++;
				if(i % 4 == 0) {
					poljex++;
					poljey = 9;
				}
			}
			{
				tipoviValidacija = new JLabel("");
				getContentPane().add(tipoviValidacija, "cell 3 9");
			}
		}
		
		{

			JButton btnNewButton = new JButton("Potvrdi");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Registruj(korisnik);
					if(roditelj instanceof MenadzerFrame) {
						((MenadzerFrame)roditelj).osveziPodatke();
					}
				}
				
			});
			getContentPane().add(btnNewButton, "cell 1 13,alignx leading");
		}
		JButton btnNewButton_1 = new JButton("Odustani");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				if(roditelj == null) {
					LoginDialog ld = new LoginDialog(mks);
				}
			}
		});
		getContentPane().add(btnNewButton_1, "cell 2 13");
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public ArrayList<Integer> odabraniTipovi() {
		ArrayList<Integer> listaTKT = new ArrayList<Integer>();
		for(int i = 0;i<checkboxes.length;i++) {
			if(checkboxes[i].isSelected()) {
				TipKozmetickogTretmana tkt = mks.getMenadzerTretmana().nadjiTipTretmana(checkboxes[i].getText());
				listaTKT.add(tkt.getSifra());
			}
		}
		return listaTKT;
	}
	
	public boolean validirajFormu(String korisnik) {
		boolean uRedu = true;
		String ime = imeText.getText();
		if(ime.equals("")) {
			uRedu = false;
			imeValidacija.setText("Niste uneli ime");
		}
		else {
			imeValidacija.setText("");
		}
		String prezime = prezimeText.getText();
		if(prezime.equals("")) {
			uRedu = false;
			prezimeValidacija.setText("Niste uneli prezime");
		}
		else {
			prezimeValidacija.setText("");
		}
		String korisnickoIme = korisnickoImeText.getText();
		if(korisnickoIme.equals("") || !mks.getMenadzerOsoba().jeJedinstvenoKorisnickoIme(korisnickoIme)) {
			uRedu = false;
			korisnickoImeValidacija.setText("Korisnicko ime je zauzeto ili ga niste uneli");
		}
		else {
			korisnickoImeValidacija.setText("");
		}
		String lozinka = lozinkaText.getText();
		if(lozinka.equals("")) {
			uRedu = false;	
			lozinkaValidacija.setText("Niste uneli lozinku");
		}
		else {
			lozinkaValidacija.setText("");
		}
		String pol = polText.getText();
		if(pol.equals("")) {
			uRedu = false;
			polValidacija.setText("Niste uneli pol");
		}
		else {
			polValidacija.setText("");
		}
		String brojTelefona = brojTelefonaText.getText();
		if(brojTelefona.equals("")) {
			uRedu = false;
			brojTelefonaValidacija.setText("Niste uneli broj telefona");
		}
		else {
			try {
				Integer.parseInt(brojTelefona);
				brojTelefonaValidacija.setText("");
			} catch (Exception e) {
				uRedu = false;
				brojTelefonaValidacija.setText("Niste uneli broj telefona");
			}
		}
		String adresa = adresaText.getText();
		if(adresa.equals("")) {
			uRedu = false;
			adresaValidacija.setText("Niste uneli adresu stanovanja");
		}
		else {
			adresaValidacija.setText("");
		}
		if(!korisnik.equals("klijent")) {
			String osnova = plata.getText();
			double platnaOsnova = 0;
			try {
				platnaOsnova = Double.parseDouble(osnova);
				plataValidacija.setText("");
			}catch (Exception e) {
				uRedu = false;
				plataValidacija.setText("Niste uneli platnu osnovu");
			}
			Object o = sprema.getSelectedItem();
			if(o == null) {
				uRedu = false;
				spremaValidacija.setText("Niste odabrali strucnu spremu");
			}
			else {
				spremaValidacija.setText("");
			}
		}
		if(korisnik.equals("kozmeticar")) {
			ArrayList<Integer> listaTKT = odabraniTipovi();
			if(listaTKT.isEmpty()) {
				uRedu = false;
				tipoviValidacija.setText("Niste odabrali tipove tretmana kozmeticara");
			}
			else {
				tipoviValidacija.setText("");
			}
		}
		return uRedu;
	}
	public void Registruj(String korisnik) {
		boolean sveOk = validirajFormu(korisnik);
		if(sveOk) {
			String ime = imeText.getText();
			String prezime = prezimeText.getText();
			String korisnickoIme = korisnickoImeText.getText();
			String lozinka = lozinkaText.getText();
			String pol = polText.getText();
			String brojTelefona = brojTelefonaText.getText();
			String adresa = adresaText.getText();
			if(korisnik.equals("klijent")) {
					
					mks.getMenadzerOsoba().registrujKlijenta(ime, prezime, pol, brojTelefona, adresa, korisnickoIme, lozinka);
			}
			else {
				String osnova = plata.getText();
				String strucnaSprema = (String) sprema.getSelectedItem();
				if(korisnik.equals("menadzer")) {
					mks.getMenadzerOsoba().registrujMenadzera(ime, prezime, pol, brojTelefona, adresa, korisnickoIme, lozinka, strucnaSprema, 0, Double.parseDouble(osnova));
				}
				else if(korisnik.equals("recepcioner")) {
					mks.getMenadzerOsoba().registrujRecepcionera(ime, prezime, pol, brojTelefona, adresa, korisnickoIme, lozinka, strucnaSprema,0, Double.parseDouble(osnova));
				}
				else if(korisnik.equals("kozmeticar")) {
					ArrayList<Integer> obucenZaTipove = odabraniTipovi();
					mks.getMenadzerOsoba().registrujKozmeticara(ime, prezime, pol, brojTelefona, adresa, korisnickoIme, lozinka, strucnaSprema, 0, Double.parseDouble(osnova), obucenZaTipove);
				}
			}
			
			setVisible(false);
			dispose();
		}
	}
}
