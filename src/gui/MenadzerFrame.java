package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;

import grafici.GodisnjiPrihodPoTipuGrafik;
import grafici.KozmeticariGrafik;
import grafici.StatusiGrafik;
import gui.ZakazivanjeTretmanaDialog.izdvojKozmetickeTretmane;
import korisnici.Klijent;
import korisnici.Kozmeticar;
import korisnici.Menadzer;
import korisnici.Recepcioner;
import menadzeri.MenadzerKozmetickiTretmani;
import menadzeri.MenadzerKozmetickogSalona;
import menadzeri.MenadzerOsoba;
import menadzeri.MenadzerZakazivanja;
import model.KlijentModel;
import model.KozmeticarModel;
import model.KozmetickiTretmanMenadzerModel;
import model.MenadzerModel;
import model.RecepcionerModel;
import model.ZakazanTretmanMenadzerModel;
import net.miginfocom.swing.MigLayout;
import usluge.KozmetickiTretman;
import usluge.TipKozmetickogTretmana;
import usluge.ZakazanTretman;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenadzerFrame extends JFrame {

	private MenadzerKozmetickogSalona mks;
	private MenadzerKozmetickiTretmani mkt;
	private MenadzerOsoba mo;
	private MenadzerZakazivanja mz;
	
	private JLabel naslovTabele;
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JTable table;
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private JButton dugmeIzvestaj, dugmeTip;
	private JButton btnNewButton_6;
	private JButton btnNewButton_8;
	private JLabel rashod;
	private JComboBox prikazCB,statistikaCB;
	private JLabel lblNewLabel_3;
	private JButton dodaj;
	private JButton izmeni;
	private JButton obrisi;
	private JLabel lblNewLabel;
	private JButton btnNewButton;

	public MenadzerFrame(MenadzerKozmetickogSalona mks, Menadzer m) {
		this.mks = mks;
		this.mo = mks.getMenadzerOsoba();
		this.mkt = mks.getMenadzerTretmana();
		this.mz = mks.getMenadzerZakazivanja();
		
		setTitle(mks.getKs() .getNazivKozmetickogSalona()+ " Korisnik: "+ m.getIme() + " " + m.getPrezime());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][98.00,grow][grow][120.00,grow][106.00,grow][86.00,grow][grow][grow][grow]", "[][][][][][][][][][][][]"));
		
		lblNewLabel_3 = new JLabel("Prikaži");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel_3, "cell 1 1,alignx right");
		
		prikazCB = new JComboBox();
		prikazCB.addItem("Klijenti");
		prikazCB.addItem("Kozmetičari");
		prikazCB.addItem("Recepcioneri");
		prikazCB.addItem("Menadžeri");
		prikazCB.addItem("Kozmetički tretmani");
		prikazCB.addItem("Zakazani tretmani");
		prikazCB.setSelectedIndex(-1);
		prikazCB.addItemListener(new podesiDugmice());
		contentPane.add(prikazCB, "cell 2 1,growx");
		
		dodaj = new JButton("Dodaj");
		contentPane.add(dodaj, "cell 3 1,growx");
		
		izmeni = new JButton("Izmeni");
		contentPane.add(izmeni, "cell 4 1,growx");
		
		obrisi = new JButton("Obriši");
		contentPane.add(obrisi, "cell 5 1,growx");
		
		btnNewButton_6 = new JButton("Isplati platu");
		btnNewButton_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rashod.setText("Rashod: "+mo.isplatiPlate().toString());
				mo.upisiPodatke();
			}
		});
		contentPane.add(btnNewButton_6, "cell 6 1,growx");
		
		btnNewButton_8 = new JButton("Dodeli karticu lojalnosti");
		btnNewButton_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DodeliKarticuLojalnostiDialog dkd = new DodeliKarticuLojalnostiDialog(mo, MenadzerFrame.this);
			}
		});
		contentPane.add(btnNewButton_8, "cell 7 1,growx");
		
		JButton btnNewButton_7 = new JButton("Dodeli bonus");
		btnNewButton_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DodelaBonusaDialog dbd = new DodelaBonusaDialog(mks, MenadzerFrame.this);
			}
		});
		contentPane.add(btnNewButton_7, "cell 8 1,growx");
		
		dugmeTip = new JButton("Izmeni Tip Tretmana");
		dugmeTip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				IzmenaTipaTretmanaDialog iztip = new IzmenaTipaTretmanaDialog(mkt, MenadzerFrame.this);
			}
		});
		
		JButton btnNewButton_12 = new JButton("Prikaži izveštaj");
		btnNewButton_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				IzvestajiDialog id = new IzvestajiDialog(mks,MenadzerFrame.this);
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Izveštaji");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel_2, "cell 0 2,alignx center");
		
		lblNewLabel = new JLabel("Statistika");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblNewLabel, "cell 1 2,alignx center");
		contentPane.add(btnNewButton_12, "cell 0 5,growx");
		
		statistikaCB = new JComboBox();

		statistikaCB.addItem("Prihod za poslednjih 12 meseci");
		statistikaCB.addItem("Angažovanje kozmetičara");
		statistikaCB.addItem("Statistika statusa");
		statistikaCB.setSelectedIndex(-1);
		statistikaCB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(statistikaCB.getSelectedIndex() == 0) {
					SwingUtilities.invokeLater(() -> {
						HashMap<String, List<Double>> spisak = mz.godisnjiPrihodPoTipuPoMesecu();
						 List<Double> prihod = mz.godisnjiPrihodPoMesecima();
						GodisnjiPrihodPoTipuGrafik grafik  = new GodisnjiPrihodPoTipuGrafik(spisak,prihod);
				    });
				}
				else if(statistikaCB.getSelectedIndex() == 1) {
					SwingUtilities.invokeLater(() -> {
						HashMap<String, Integer> spisak = mz.angazovanjePoKozmeticaruZaPoslednjiMesec();
						
						KozmeticariGrafik kg = new KozmeticariGrafik(spisak);
					    });
				}
				else if(statistikaCB.getSelectedIndex() == 2) {
					SwingUtilities.invokeLater(() -> {
						LocalDate danas = LocalDate.now();
						HashMap<String, Integer> spisak = mz.realizacijaKozmetickihTretmana(danas.minusMonths(1), danas);
						StatusiGrafik kg = new StatusiGrafik(spisak);
				    });
				}
				
			}
		});
		contentPane.add(statistikaCB, "cell 1 5,growx");
		contentPane.add(dugmeTip, "cell 2 5,growx");
		
		naslovTabele = new JLabel("Odaberi prikaz");
		naslovTabele.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(naslovTabele, "cell 4 7,alignx center");
		table = new JTable();
		table.setRowSorter(sortiranjeTabela);
		scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, "cell 0 8 9 1,grow");
		
		btnNewButton = new JButton("Odjavi se");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				LoginDialog ld = new LoginDialog(mks);
			}
		});
		contentPane.add(btnNewButton, "cell 8 9,growx");
		
		rashod = new JLabel("");
		contentPane.add(rashod, "cell 7 9,alignx center");
		podesiTabelu();
		
		obrisi.setEnabled(false);
		izmeni.setEnabled(false);
		dodaj.setEnabled(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void osveziPodatke() {
		if(this.table.getModel() instanceof KozmetickiTretmanMenadzerModel) {
			KozmetickiTretmanMenadzerModel sm = (KozmetickiTretmanMenadzerModel)this.table.getModel();
			sm.fireTableDataChanged();
		}
		else if(this.table.getModel() instanceof ZakazanTretmanMenadzerModel) {
			ZakazanTretmanMenadzerModel sm = (ZakazanTretmanMenadzerModel)this.table.getModel();
			sm.fireTableDataChanged();
		}
		else if(this.table.getModel() instanceof KlijentModel) {
			KlijentModel sm = (KlijentModel)this.table.getModel();
			sm.fireTableDataChanged();
		}
		else if(this.table.getModel() instanceof RecepcionerModel) {
			RecepcionerModel sm = (RecepcionerModel)this.table.getModel();
			sm.fireTableDataChanged();
		}
		else if(this.table.getModel() instanceof MenadzerModel) {
			MenadzerModel sm = (MenadzerModel)this.table.getModel();
			sm.fireTableDataChanged();
		}
		else if(this.table.getModel() instanceof KozmeticarModel) {
			KozmeticarModel sm = (KozmeticarModel)this.table.getModel();
			sm.fireTableDataChanged();
		}
	}
	class omoguciDugmice implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			obrisi.setEnabled(false);
			izmeni.setEnabled(false);
			dodaj.setEnabled(false);
			if(table.getSelectedRow()!=-1) {
				
				if(!(MenadzerFrame.this.table.getModel() instanceof ZakazanTretmanMenadzerModel)) {
					obrisi.setEnabled(true);
					izmeni.setEnabled(true);
					dodaj.setEnabled(true);
				}
				else if(MenadzerFrame.this.table.getModel() instanceof ZakazanTretmanMenadzerModel) {
					dodaj.setEnabled(true);
					if(((String)table.getValueAt(table.getSelectedRow(), 7)).equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.toString())) {
						izmeni.setEnabled(true);
					}

				}
			}	
		}
		
	}
	class podesiDugmice implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if(obrisi.getMouseListeners().length >1) {
				obrisi.removeMouseListener(obrisi.getMouseListeners()[1]);
			}
			if(izmeni.getMouseListeners().length >1) {
				izmeni.removeMouseListener(izmeni.getMouseListeners()[1]);
			}
			if(dodaj.getMouseListeners().length >1) {
				dodaj.removeMouseListener(dodaj.getMouseListeners()[1]);
			}
			obrisi.setEnabled(false);
			dodaj.setEnabled(true);
			izmeni.setEnabled(false);
				if(prikazCB.getSelectedIndex() == 0) {
					
					table.setModel(new KlijentModel(mo));
					podesiTabelu();
					osveziPodatke();
					naslovTabele.setText("Svi Klijenti");

					obrisi.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(obrisi.isEnabled()) {
								mo.obrisiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
								osveziPodatke();
							}
						}
					});
					izmeni.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(izmeni.isEnabled()) {
								try {
									Klijent k = (Klijent) mo.nadjiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
									IzmenaOsobaDialog iod = new IzmenaOsobaDialog(mks, k, MenadzerFrame.this);
								}catch(Exception ex) {
								}
							}

						}
					});
					dodaj.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(dodaj.isEnabled()) {
								RegistracijaDialog rd = new RegistracijaDialog(mks, "klijent",MenadzerFrame.this);
							}
						}
					});
				}
				else if(prikazCB.getSelectedIndex() == 1) {
					table.setModel(new KozmeticarModel(mo, mkt));
					podesiTabelu();
					osveziPodatke();
					naslovTabele.setText("Svi Kozmetičari");
					
					obrisi.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(obrisi.isEnabled()) {
								mo.obrisiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
								osveziPodatke();
							}

						}
					});
					izmeni.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(izmeni.isEnabled()) {
								try {
									Kozmeticar k = (Kozmeticar) mo.nadjiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
									IzmenaOsobaDialog iod = new IzmenaOsobaDialog(mks, k, MenadzerFrame.this);
								}catch(Exception ex) {
								}
							}

						}
					});
					dodaj.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(dodaj.isEnabled()) {
								RegistracijaDialog rd = new RegistracijaDialog(mks, "kozmeticar",MenadzerFrame.this);
							}

						}
					});

					
				}else if(prikazCB.getSelectedIndex() == 2) {
					table.setModel(new RecepcionerModel(mo));
					podesiTabelu();
					osveziPodatke();
					naslovTabele.setText("Svi Recepcioneri");
					
					obrisi.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseClicked(MouseEvent e) {
							if(obrisi.isEnabled()) {
								mo.obrisiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
								osveziPodatke();
							}

						}
					});
					izmeni.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(izmeni.isEnabled()) {
								try {
									Recepcioner r = (Recepcioner) mo.nadjiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
									IzmenaOsobaDialog iod = new IzmenaOsobaDialog(mks, r, MenadzerFrame.this);
								}catch(Exception ex) {
								}
							}

						}
					});
					dodaj.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(dodaj.isEnabled()) {
								RegistracijaDialog rd = new RegistracijaDialog(mks, "recepcioner",MenadzerFrame.this);
							}

						}
					});
					
				}else if(prikazCB.getSelectedIndex() == 3) {
					table.setModel(new MenadzerModel(mo));
					podesiTabelu();
					osveziPodatke();
					naslovTabele.setText("Svi Menadžeri");
					
					obrisi.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(obrisi.isEnabled()) {
								mo.obrisiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
								osveziPodatke();
							}

						}
					});
					izmeni.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(izmeni.isEnabled()) {
								try {
									Menadzer m = (Menadzer) mo.nadjiOsobu((String)table.getValueAt(table.getSelectedRow(), 2));
									IzmenaOsobaDialog iod = new IzmenaOsobaDialog(mks, m, MenadzerFrame.this);
								}catch(Exception ex) {
								}
							}

						}
					});
					dodaj.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(dodaj.isEnabled()) {
								RegistracijaDialog rd = new RegistracijaDialog(mks, "menadzer",MenadzerFrame.this);
							}

						}
					});
					
				}else if(prikazCB.getSelectedIndex() == 4) {
					table.setModel(new KozmetickiTretmanMenadzerModel(mkt));
					podesiTabelu();
					osveziPodatke();
					naslovTabele.setText("Svi Tretmani");
					
					obrisi.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(obrisi.isEnabled()) {
								mkt.obrisiKozmetickiTretman((int)table.getValueAt(table.getSelectedRow(), 0));
								osveziPodatke();
							}

						}
					});
					izmeni.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(izmeni.isEnabled()) {
								IzmenaKozmetickogTretmanaDialog izmena = new IzmenaKozmetickogTretmanaDialog(mks, MenadzerFrame.this, (KozmetickiTretman)table.getValueAt(table.getSelectedRow(),1));
							}

						}
					});
					dodaj.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(dodaj.isEnabled()) {
								KreiranjeKozmetickogTretmanaDialog ktd = new KreiranjeKozmetickogTretmanaDialog(mks, MenadzerFrame.this);
							}

						}
					});
					
				}else if(prikazCB.getSelectedIndex() == 5) {
					table.setModel(new ZakazanTretmanMenadzerModel(mz));
					podesiTabelu();
					osveziPodatke();
					naslovTabele.setText("Svi Zakazani Tretmani");
					izmeni.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(izmeni.isEnabled()) {
								try {
									if(((String)table.getValueAt(table.getSelectedRow(), 7)).equals(MenadzerKozmetickiTretmani.status.ZAKAZAN.toString())) {
										ZakazanTretman zt = (ZakazanTretman)table.getValueAt(table.getSelectedRow(), 1);
										IzmenaZakazanogTretmanaDialog izt = new IzmenaZakazanogTretmanaDialog(zt, mks, MenadzerFrame.this);
									}

								}catch(Exception ex) {
								}
							}
						}
					});
					dodaj.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(dodaj.isEnabled()) {
								ZakazivanjeTretmanaDialog ztd = new ZakazivanjeTretmanaDialog(mks, null, MenadzerFrame.this);
							}

						}
					});
				}
			}
		}
	public void podesiTabelu() {
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addListSelectionListener(new omoguciDugmice());
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader header = table.getTableHeader();
		sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
	}
	
	public void nacrtajGrafik(SwingWrapper<PieChart> grafik) {
		grafik.displayChart();
	}


}
