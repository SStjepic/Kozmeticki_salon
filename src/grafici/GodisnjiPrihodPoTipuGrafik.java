package grafici;

import java.awt.Color;
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

public class GodisnjiPrihodPoTipuGrafik extends JDialog {
	private HashMap<String, List<Double>> izvestaj;
	
	public GodisnjiPrihodPoTipuGrafik(HashMap<String, List<Double>> izvestaj, List<Double> prihod) {
		this.izvestaj = izvestaj;
		Random r = new Random();
		CategoryChart grafik = new CategoryChartBuilder().width(800).height(600).title("Godišnji prihod po tipu tretmana").xAxisTitle("Meseci").yAxisTitle("Prihod").build();
		 
		 grafik.getStyler().setLegendPosition(LegendPosition.OutsideS);
		 grafik.getStyler().setYAxisLabelAlignment(TextAlignment.Centre);
		 grafik.getStyler().setYAxisLabelAlignment(TextAlignment.Centre);
		 grafik.getStyler().setYAxisDecimalPattern("###,###.## RSD");
		 grafik.getStyler().setPlotMargin(4);
		 grafik.getStyler().setPlotContentSize(.95);
		 grafik.getStyler().setDatePattern("MMM.yyyy");
		 grafik.getStyler().setLocale(Locale.ROOT);
		 

		 
		 
		Color[] boje  = new Color[izvestaj.values().size()+1];
		List<Date> meseci = napraviMesece();
		int brojac = 0;
		for(String s: izvestaj.keySet()) {
			grafik.addSeries(s,meseci, izvestaj.get(s));
			boje[brojac] = new Color(r.nextInt(100),r.nextInt(150),r.nextInt(255) );
			brojac+=1;
		}
		boje[brojac] = new Color(r.nextInt(100),r.nextInt(150),r.nextInt(255) );
		grafik.addSeries("Ukupno",meseci, prihod);
		grafik.getStyler().setSeriesColors(boje);
		JPanel grafikPanel = new XChartPanel<>(grafik);
		getContentPane().add(grafikPanel);
		setSize(500,500);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public List<Date> napraviMesece() {
		List<Date> meseci = new ArrayList<>();
		int mesec = LocalDate.now().getMonthValue();
		DateFormat sdf = new SimpleDateFormat("MM.yyyy");
	    Date datum = null;
	    for (int i = 0; i < 12; i++) {
	    	int trenutni = (mesec+i)%12 + 1;
	    	String godina = LocalDate.now().toString().split("-")[0];
	    	if(trenutni>LocalDate.now().getMonthValue()) {
	    		godina = LocalDate.now().minusYears(1).toString().split("-")[0];
	    	}
	      try {
	    	  datum = sdf.parse(trenutni + "."+godina);
	      } catch (ParseException e) {
	      }
	      meseci.add(datum);
	    }
		return meseci;
	}

}
