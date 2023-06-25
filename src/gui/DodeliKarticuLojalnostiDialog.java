package gui;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import menadzeri.MenadzerOsoba;
import model.KlijentModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DodeliKarticuLojalnostiDialog extends JDialog {
	private JTextField textField;
	private JTable table;
	private JLabel iznosValidacija;
	
	private MenadzerOsoba mo;
	protected TableRowSorter<AbstractTableModel> sortiranjeTabela = new TableRowSorter<AbstractTableModel>();
	private JButton btnNewButton_1;
	private JFrame roditelj;

	public DodeliKarticuLojalnostiDialog(MenadzerOsoba mo, JFrame roditelj) {
		this.mo = mo;
		this.roditelj = roditelj;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 743, 258);
		getContentPane().setLayout(new MigLayout("", "[][221.00][][186.00][139.00]", "[][][129.00][-145.00]"));
		{
			JLabel lblNewLabel = new JLabel("Minimalna potrošnja");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			getContentPane().add(lblNewLabel, "cell 0 0,alignx right");
		}
		{
			textField = new JTextField();
			getContentPane().add(textField, "cell 1 0,growx");
			textField.setColumns(10);
		}
		{
			{
				iznosValidacija = new JLabel("");
				getContentPane().add(iznosValidacija, "flowx,cell 2 0 2 1");
			}
		}
		JButton btnNewButton = new JButton("Dodeli");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					mo.dodeliKarticuLojalnosti(Double.parseDouble(textField.getText()));
					table.setModel(new KlijentModel(mo));
					table.getTableHeader().setReorderingAllowed(false);
					JTableHeader header = table.getTableHeader();
					sortiranjeTabela.setModel((AbstractTableModel) table.getModel());
					table.setEnabled(false);
					table.setRowSorter(sortiranjeTabela);
					
					sortiranjeTabela.setRowFilter(RowFilter.regexFilter("(?i)" + "DA",7));
					KlijentModel sm = (KlijentModel)DodeliKarticuLojalnostiDialog.this.table.getModel();
					sm.fireTableDataChanged();
				} catch (Exception e2) {
					iznosValidacija.setText("Niste uneli validnu granicu");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		getContentPane().add(btnNewButton, "cell 1 1,growx");
		{
			table = new JTable();
			JScrollPane scrollPane = new JScrollPane(table);
			getContentPane().add(scrollPane, "cell 0 2 5 1,grow");
		}
		{
			btnNewButton_1 = new JButton("Nazad");
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					((MenadzerFrame)roditelj).osveziPodatke();
					dispose();
				}
			});
			getContentPane().add(btnNewButton_1, "cell 3 0,growx");

		}
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

}
