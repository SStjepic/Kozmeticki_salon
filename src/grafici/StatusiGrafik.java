package grafici;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.AxesChartStyler.TextAlignment;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.toedter.calendar.JDateChooser;

import menadzeri.MenadzerZakazivanja;

public class StatusiGrafik extends JDialog{
	private MenadzerZakazivanja mz;
	private HashMap<String, Integer> izvestaj;
	
	public StatusiGrafik(HashMap<String, Integer> izvestaj) {
		this.izvestaj = izvestaj;
		Random r = new Random();
		PieChart grafik  = new PieChartBuilder().width(800).height(600).title("Status kozmetičkih tretmana u poslednjih 30 dana").build();
		Color[] boje  = new Color[izvestaj.values().size()];
		int brojac = 0;
		for(String s: izvestaj.keySet()) {
			grafik.addSeries(s, izvestaj.get(s));
			boje[brojac] = new Color(r.nextInt(150),r.nextInt(150),r.nextInt(255) );
			brojac+=1;
		}
		grafik.getStyler().setSeriesColors(boje);
		JPanel grafikPanel = new XChartPanel<>(grafik);
		getContentPane().add(grafikPanel);
		setSize(600,600);
		setLocationRelativeTo(null);
		setVisible(true);

	}
}
