package grafici;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.AxesChartStyler.TextAlignment;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;

public class GodisnjiPrihod extends JDialog {

private HashMap<String, List<Double>> izvestaj;
	
	public GodisnjiPrihod(HashMap<String, List<Double>> izvestaj, List<Double> prihod) {
		this.izvestaj = izvestaj;
		Random r = new Random();
	    XYChart grafik = new XYChartBuilder().width(800).height(600).title("Godišnji prihod po tipu tretmana").xAxisTitle("Meseci").yAxisTitle("Prihod").build();

	    grafik.getStyler().setChartBackgroundColor(Color.WHITE);
	    grafik.getStyler().setChartTitleBoxVisible(true);
	    grafik.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
	    grafik.getStyler().setPlotGridLinesVisible(false);

	    grafik.getStyler().setAxisTickPadding(20);

	    grafik.getStyler().setAxisTickMarkLength(15);

	    grafik.getStyler().setPlotMargin(20);

	    grafik.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
	    grafik.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
	    grafik.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    grafik.getStyler().setLegendSeriesLineLength(12);
	    grafik.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
	    grafik.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
	    grafik.getStyler().setDatePattern("MMM.yyyy");
	    grafik.getStyler().setDecimalPattern("###,###.## RSD");
	    grafik.getStyler().setLocale(Locale.ROOT);
		 

		 
		 
		Color[] boje  = new Color[izvestaj.values().size()+1];
		List<Date> meseci = napraviMesece();
		int brojac = 0;
		for(String s: izvestaj.keySet()) {
			grafik.addSeries(s,meseci, izvestaj.get(s));
			boje[brojac] = new Color(r.nextInt(250),r.nextInt(250),r.nextInt(250) );
			brojac+=1;
		}
		boje[brojac] = new Color(r.nextInt(250),r.nextInt(250),r.nextInt(250) );
		grafik.addSeries("Ukupno",meseci, prihod);
		grafik.getStyler().setSeriesColors(boje);
		JPanel grafikPanel = new XChartPanel<>(grafik);
		getContentPane().add(grafikPanel);
		setSize(1000,500);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public List<Date> napraviMesece() {
		List<Date> meseci = new ArrayList<>();
		DateFormat sdf = new SimpleDateFormat("MM.yyyy");
	    Date datum = null;
	    LocalDate trenutnaGodina = LocalDate.now().plusMonths(1);
	    for (int i = 0; i < 12; i++) {
	    	String trenutniMesec = trenutnaGodina.toString().split("-")[1];
	    	String godina = trenutnaGodina.toString().split("-")[0];
	      try {
	    	  datum = sdf.parse(trenutniMesec + "."+godina);
	      } catch (ParseException e) {
	      }
	      meseci.add(datum);
	      trenutnaGodina = trenutnaGodina.minusMonths(1);
	    }
		return meseci;
	}
}
