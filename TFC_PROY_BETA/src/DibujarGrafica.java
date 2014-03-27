import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
 

public class DibujarGrafica {
      /**
     * Crea un gráfico de barras y lo asigna al JPanel recibido
     * @param histograma histograma de frecuencias (int[256]).
     * @param jPanelHistograma JPanel donde el histograma será dibujado
     * @param colorBarras color de cuál será dibujado el histograma
     */
    public void crearHistograma(int[] histograma,JPanel jPanelHistograma,Color colorBarras, BufferedImage image) {
        //Creamos el dataSet y añadimos el histograma
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String serie = "Numero de píxels";
        for (int i = 0; i < histograma.length; i++){
            dataset.addValue(histograma[i], serie, "" + i);
        }
        //Creamos el chart
        JFreeChart chart = ChartFactory.createBarChart("Frecuencia ", null, null,
                                    dataset, PlotOrientation.VERTICAL, true, true, false);
        //Modificamos el diseño del chart
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, colorBarras);
        chart.setAntiAlias(true);
        chart.setBackgroundPaint(new Color(214, 217, 223)); 
        jPanelHistograma.removeAll();
        jPanelHistograma.repaint();
        jPanelHistograma.setLayout(new java.awt.BorderLayout());
        jPanelHistograma.add(new ChartPanel(chart));
        jPanelHistograma.validate();
        
    }
//====================================================================================================================    
    public final void winRGB(BufferedImage image) {
    	
		JFrame miVentana = new JFrame();		
		miVentana.setTitle("TFG - sImage beta v.3.0");
		miVentana.getContentPane().setBackground(Color.LIGHT_GRAY);
		miVentana.setSize(image.getWidth(), image.getHeight());
		
		JPanel panel = new JPanel();
		BufferedImage salida = image;
		
		for (int x=0; x <  image.getWidth(); x++){
			for (int y=0;y < image.getHeight(); y++){
				//Obtiene el color
				Color c1=new Color(image.getRGB(x, y));
				//Calcula la media de tonalidades
				int med=(c1.getRed()+c1.getGreen()+c1.getBlue())/3;
				//Almacena el color en la imagen destino
				salida.setRGB(x, y, new Color(med,med,med).getRGB());
			}
		}
		
		
		ImageIcon ico = new ImageIcon(salida);				
		JLabel label = new JLabel (ico);													
		panel.add(label);
		
	    miVentana.add(panel);		
		miVentana.setVisible(true);
				    													
	}
}