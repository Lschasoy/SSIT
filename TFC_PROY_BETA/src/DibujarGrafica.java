import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
     * Crea un gráfico de barras y lo asigna al JPanel recibido
     * @param histograma histograma de frecuencias (int[256]).
     * @param jPanelHistograma JPanel donde el histograma será dibujado
     * @param colorBarras color de cuál será dibujado el histograma
     */

	private String msg;
	
    public void crearHistograma(int[] histograma,JPanel jpHisto,Color colorBarras, final BufferedImage image, final JPanel jpImagen, final JTextArea cmdLine) {
    	
    	 cmdLine.setText("\n$ > [Crear histograma]: name: " + histograma + '\n');
    	 
        //Creamos el dataSet y añadimos el histograma
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String serie = "Píxeles nº ";
        for (int i = 0; i < histograma.length; i++){
            dataset.addValue(histograma[i], serie, "[" + i +"] ");
         
        }
        //Creamos el chart
        final JFreeChart chart = ChartFactory.createBarChart("Frecuencia ", null, null,dataset, PlotOrientation.VERTICAL, true, true, false);
                    
        ChartPanel panel = new ChartPanel (chart); // --> lsch
		
		
        //Modificamos el diseño del chart
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, colorBarras);
        chart.setAntiAlias(true);
        
        
        chart.setBackgroundPaint(new Color(214, 217, 223)); 
        jpHisto.removeAll();
        jpHisto.repaint();
        jpHisto.setLayout(new java.awt.BorderLayout());
        jpHisto.add(panel); //-- > lsch
        
        final double Tolerancia = 50; // Tolerancia   
        
        panel.addChartMouseListener(new ChartMouseListener(){
            public void chartMouseClicked(ChartMouseEvent e){
            	int x = e.getTrigger().getX();
                int y = e.getTrigger().getY();
            
                Color c1 = new Color(image.getRGB(x, y));
                BufferedImage tmp = clona(image);
                
            	
                msg = "\n$ > Coordenada X:" + x + "Y:" +y + " Tolerancia: " + Tolerancia;
                msg += "\n$ > [Color] = R: " + c1.getRed() + " B: " + c1.getBlue() + " G: "+ c1.getGreen()  + '\n';
                cmdLine.append(msg); 
                
                for (int u = 0; u < image.getWidth(); u++){
                	for (int v = 0; v < image.getHeight(); v++){
                		
                		Color c2 = new Color(image.getRGB(u, v));
                		
                	    double d = Math.sqrt(Math.pow((c1.getRed() -  c2.getRed()),2) +
                	    		          Math.pow((c1.getGreen() - c2.getGreen()),2) +
                	    		          Math.pow((c1.getBlue() - c2.getBlue()),2));
                	    
                	        if ( d <= Tolerancia){// Pintar en color                 	        
	                	        image.setRGB(u, v, new Color(c2.getRed(),c2.getBlue(),c2.getGreen()).getRGB());
	                	        
                	        }   else{ // Colocar en grises
                	        	int med=(c2.getRed()+c2.getGreen()+c2.getBlue())/3;
            					//Almacena el color en la imagen destino
            					image.setRGB(u, v, new Color(med,med,med).getRGB());
                	        }
                	        jpImagen.repaint();     
                	}   
                	
                }
                 	
            }

			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Moved ");
				
			}
        });   
         
        jpHisto.validate();        
        
    }

  //==========================================================================================================        
    public static BufferedImage clona(BufferedImage imagen){
    	BufferedImage copia = new BufferedImage (imagen.getWidth(),imagen.getHeight(),imagen.getType());
    	copia.setData(imagen.getData());
    	return copia;
    }
    
}