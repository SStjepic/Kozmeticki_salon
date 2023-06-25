package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;

public class IzmenaKozmetickogTretmanaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private MenadzerKozmetickiTretmani mkt;
	private JTextField naziv;
	private JTextField cena;
	private JTextField vreme;
	private JComboBox tip;
	private JFrame roditelj;
	private JLabel tipTretmanaValidacija, nazivValidacija, cenaValidacija, vremeValidacija;
	private KozmetickiTretman kt;

	public IzmenaKozmetickogTretmanaDialog(MenadzerKozmetickogSalona mks, JFrame roditelj, KozmetickiTretman kt) {
		this.mkt = mks.getMenadzerTretmana();
		this.roditelj = roditelj;
		this.kt = kt;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 650, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][][]", "[][][][][][][]"));
		{
			JLabel lblNewLabel = new JLabel("Tip kozmetičkog tretmana");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
		}
		{
			tip = new JComboBox();
			tip.setEditable(true);
			tip.setMaximumRowCount(10);
			
			for(TipKozmetickogTretmana tkt: mkt.getSviTipovi()) {
				tip.addItem(tkt);
			}
			tip.setSelectedIndex(0);
			contentPanel.add(tip, "cell 1 0,growx");
		}
		{
			tipTretmanaValidacija = new JLabel("");
			contentPanel.add(tipTretmanaValidacija, "cell 2 0");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Naziv");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		}
		{
			naziv = new JTextField();
			contentPanel.add(naziv, "cell 1 1,growx");
			naziv.setColumns(10);
		}
		{
			nazivValidacija = new JLabel("");
			contentPanel.add(nazivValidacija, "cell 2 1");
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Cena");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_2, "cell 0 2,alignx trailing");
		}
		{
			cena = new JTextField();
			contentPanel.add(cena, "cell 1 2,growx");
			cena.setColumns(10);
		}
		{
			cenaValidacija = new JLabel("");
			contentPanel.add(cenaValidacija, "cell 2 2");
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Vreme trajanja u minutima");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel_3, "cell 0 3,alignx trailing");
		}
		{
			vreme = new JTextField();
			contentPanel.add(vreme, "cell 1 3 1 1,growx");
			vreme.setColumns(10);
		}
		{
			JButton btnNewButton = new JButton("Potvrdi");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(ValidirajFormu()) {
						izmeniTretman();
						mks.upisiID();
						((MenadzerFrame)roditelj).osveziPodatke();
					}

				}
			});
			{
				vremeValidacija = new JLabel("");
				contentPanel.add(vremeValidacija, "cell 2 3");
			}
			contentPanel.add(btnNewButton, "flowx,cell 1 5");
		}
		{
			JButton btnNewButton_1 = new JButton("Odustani");
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
				}
			});
			contentPanel.add(btnNewButton_1, "cell 1 5");
		}
		popuniFormu();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	public boolean ValidirajFormu() {
		boolean sveOk = true;
		String nazivKT = naziv.getText();
		if(nazivKT.equals("")) {
			sveOk = false;
			nazivValidacija.setText("Niste uneli validan naziv");
		}
		else if(!mkt.proveriJedinstvenostKozmetickogTretmana(nazivKT) && !nazivKT.equals(kt.getNaziv())) {
			sveOk = false;
			nazivValidacija.setText("Niste uneli validan naziv. Naziv vec postoji");
		}
		String cenaKT = cena.getText();
		if(cenaKT.equals("")) {
			sveOk = false;
			cenaValidacija.setText("Niste uneli validnu cenu");
		}
		try {
			Double.parseDouble(cenaKT);
			cenaValidacija.setText("");
		} catch (Exception e) {
			sveOk = false;
			cenaValidacija.setText("Niste uneli validnu cenu");
		}
		String vremeKT = vreme.getText().toString();
		if(vremeKT.equals("")) {
			sveOk = false;
			vremeValidacija.setText("Niste uneli validno vreme");
		}
		try {
			Integer.parseInt(vremeKT);
			vremeValidacija.setText("");
		} catch (Exception e) {
			sveOk = false;
			vremeValidacija.setText("Niste uneli validno vreme");
		}
		try {
			int vremeMinuti = Integer.parseInt(vremeKT);
			vremeValidacija.setText("");
		} catch (Exception e) {
			sveOk = false;
			vremeValidacija.setText("Niste uneli validno vreme. Proverite format");
		}
		try {
			String izabranTip = (String) tip.getEditor().getItem();
			if(izabranTip.equals("")) {
				sveOk = false;
				vremeValidacija.setText("Niste uneli validan tip tretmana");
			}
			else if(!mkt.proveriJedinstvenostTipaTretmana(izabranTip)) {
				sveOk = false;
				vremeValidacija.setText("Niste uneli validan tip tretmana. Takav tip vec postoji.");
			}
			else {
				vremeValidacija.setText("");
			}
		}catch (Exception e) {
			TipKozmetickogTretmana tkt = (TipKozmetickogTretmana) tip.getSelectedItem();
			if(tkt == null) {
				sveOk = false;
				vremeValidacija.setText("Niste izabrali validan tip");
			}
		}
		
		return sveOk;
	}
	public void izmeniTretman() {
		TipKozmetickogTretmana tkt;
		String nazivKT = naziv.getText();
		double cenaKT = Double.parseDouble(cena.getText());
		String izabranTip = "";
		int vremeMinuti = Integer.parseInt(vreme.getText());
		LocalTime vremeIzvrsavanja = LocalTime.of(vremeMinuti/60, vremeMinuti%60);
		try {
			izabranTip = (String) tip.getEditor().getItem();
		} catch (Exception e) {
			izabranTip = ((TipKozmetickogTretmana) tip.getSelectedItem()).getNaziv();
		}
		
		if(mkt.nadjiTipTretmana(izabranTip) == null) {
			tkt= mkt.dodajTipTretmana(izabranTip);
		}
		else {
			tkt = mkt.nadjiTipTretmana(izabranTip);
		}
		mkt.azurirajKozmetickiTretman(kt.getidKozmetickogTretmana(), nazivKT, cenaKT, vremeIzvrsavanja, tkt);
		setVisible(false);
		dispose();
	}
	
	public void popuniFormu() {
		cena.setText(((Double)mkt.cenovnikUsluga().nadjiCenuKozmetickogTretmana(kt)).toString());
		naziv.setText(kt.getNaziv());
		LocalTime vremeStaro = kt.getVremeTrajanja();
		Integer minutiStari = vremeStaro.getHour()*60+vremeStaro.getMinute();
		vreme.setText(minutiStari.toString());
		tip.setSelectedItem(kt.getTipTretmana());
	}

}
