import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
 

public class DibujarGrafica {
      /**
     * Crea un gr�fico de barras y lo asigna al JPanel recibido
     * @param histograma histograma de frecuencias (int[256]).
     * @param jPanelHistograma JPanel donde el histograma ser� dibujado
     * @param colorBarras color de cu�l ser� dibujado el histograma
     */
    public void crearHistograma(int[] histograma,JPanel jPanelHistograma,Color colorBarras, BufferedImage image) {
        //Creamos el dataSet y a�adimos el histograma
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String serie = "Numero de p�xels";
        for (int i = 0; i < histograma.length; i++){
            dataset.addValue(histograma[i], serie, "" + i);
         
        }
        //Creamos el chart
        final JFreeChart chart = ChartFactory.createBarChart("Frecuencia ", null, null,dataset, PlotOrientation.VERTICAL, true, true, false);
                    
        ChartPanel panel = new ChartPanel (chart); // --> lsch
		
		
        //Modificamos el dise�o del chart
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, colorBarras);
        chart.setAntiAlias(true);
        
        
        chart.setBackgroundPaint(new Color(214, 217, 223)); 
        jPanelHistograma.removeAll();
        jPanelHistograma.repaint();
        jPanelHistograma.setLayout(new java.awt.BorderLayout());
        jPanelHistograma.add(panel); //-- > lsch
        
        panel.addChartMouseListener(new ChartMouseListener(){
            public void chartMouseClicked(ChartMouseEvent e){
            	int x = e.getTrigger().getX();
                int y = e.getTrigger().getY();

                System.out.println("X :" + x + " Y : " + y); 
            	
            }

			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Moved ");
				
			}
        });   
        
        jPanelHistograma.validate();
        
    }
 

}