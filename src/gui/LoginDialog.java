package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import korisnici.Klijent;
import korisnici.Kozmeticar;
import korisnici.Menadzer;
import korisnici.Osoba;
import korisnici.Recepcioner;
import menadzeri.MenadzerKozmetickogSalona;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;

public class LoginDialog extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	private MenadzerKozmetickogSalona mks;
	private JLabel korisnickoImeValidacija, lozinkaValidacija;

	/**
	 * Create the dialog.
	 */
	public LoginDialog(MenadzerKozmetickogSalona mks) {
		this.mks = mks;
		
		setTitle("Uloguj se");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][][][][]", "[][][][][][][]"));
		{
			JLabel lblNewLabel_2 = new JLabel("DOBRODOŠLI");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			getContentPane().add(lblNewLabel_2, "cell 3 0,alignx center");
		}
		{
			JLabel lblNewLabel = new JLabel("Korisnicko ime: ");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			getContentPane().add(lblNewLabel, "cell 1 1 2 1,alignx right");
		}
		{
			textField = new JTextField();
			getContentPane().add(textField, "flowx,cell 3 1,alignx leading");
			textField.setColumns(20);
		}
		{
			korisnickoImeValidacija = new JLabel("");
			getContentPane().add(korisnickoImeValidacija, "cell 4 1");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Lozinka:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
			getContentPane().add(lblNewLabel_1, "cell 1 2 2 1,alignx right");
		}
		{
			textField_1 = new JTextField();
			getContentPane().add(textField_1, "flowx,cell 3 2,alignx leading");
			textField_1.setColumns(20);
		}
		{
			{
				lozinkaValidacija = new JLabel("");
				getContentPane().add(lozinkaValidacija, "cell 4 2");
			}
		}
		{
		}
		JButton btnNewButton_1 = new JButton("Izlaz");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		{
			JButton btnNewButton_2 = new JButton("Registruj se");
			btnNewButton_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
					RegistracijaDialog rd = new RegistracijaDialog(mks, "klijent",null);
					dispose();
					
				}
			});
			JButton btnNewButton = new JButton("Potvrdi");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(validacijaLogina()) {
						String korisnickoIme = textField.getText();
						String lozinka = textField_1.getText();
						Osoba o = mks.getMenadzerOsoba().proveriLogin(korisnickoIme, lozinka);
						if(o != null) {
							if(o instanceof Klijent){
								KlijentFrame kf = new KlijentFrame(mks, (Klijent) o);
							}
							else if(o instanceof Menadzer){
								MenadzerFrame mf = new MenadzerFrame(mks, (Menadzer) o);
								
							}
							else if(o instanceof Kozmeticar){
								KozmeticarFrame km = new KozmeticarFrame(mks, (Kozmeticar) o);
							}
							else if(o instanceof Recepcioner){
								RecepcionerFrame rf = new RecepcionerFrame(mks, (Recepcioner) o);
							};
							setVisible(false);
							dispose();
						}
						else {
							korisnickoImeValidacija.setText("Uneto korisnicko ime ili lozinka nisu validni");
						}
					}
					
					
					
				}
				
			});
			getContentPane().add(btnNewButton, "cell 3 4,alignx leading,grow");
			getContentPane().add(btnNewButton_2, "cell 3 5,grow");
		}
		getContentPane().add(btnNewButton_1, "cell 3 6,alignx leading,grow");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public boolean validacijaLogina() {
		boolean sveOk = true;
		if( textField.getText().equals("")) {
			korisnickoImeValidacija.setText("Korisnicko ime nije validno");
			sveOk =false;
		}
		else {
			korisnickoImeValidacija.setText("");
		}
		if(textField_1.getText().equals("")) {
			lozinkaValidacija.setText("Lozinka nije validna");
			sveOk=false;
		}
		else {
			lozinkaValidacija.setText("");
		}
		return sveOk;
	}

}
