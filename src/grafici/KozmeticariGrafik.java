package grafici;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import com.toedter.calendar.JDateChooser;

import menadzeri.MenadzerZakazivanja;

public class KozmeticariGrafik extends JDialog  {
	private MenadzerZakazivanja mz;
	private HashMap<String, Integer> izvestaj;
	
	public KozmeticariGrafik(HashMap<String, Integer> izvestaj) {
		this.izvestaj = izvestaj;
		Random r = new Random();
		PieChart grafik  = new PieChartBuilder().width(800).height(600).title("Opterećenje kozmetičata u poslednjih 30 dana").build();
		Color[] boje  = new Color[izvestaj.values().size()];
		int brojac = 0;
		for(String s: izvestaj.keySet()) {
			grafik.addSeries(s, izvestaj.get(s));
			boje[brojac] = new Color(r.nextInt(250),r.nextInt(250),r.nextInt(250) );
			brojac+=1;
		}
		grafik.getStyler().setSeriesColors(boje);
		JPanel grafikPanel = new XChartPanel<>(grafik);
		getContentPane().add(grafikPanel);
		setSize(500,500);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
}
