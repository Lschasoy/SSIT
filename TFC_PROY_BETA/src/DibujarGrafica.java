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
	
    public void crearHistograma(int[] histograma,JPanel jpHisto,final Color colorBarras, final BufferedImage image, final JPanel jpImagen, final JTextArea cmdLine) {
    	
    	 cmdLine.append("$Run .. [Crear histograma]: name: " + colorBarras.toString() + '\n');
    	 
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
        
        
        
        panel.addChartMouseListener(new ChartMouseListener(){
            public void chartMouseClicked(ChartMouseEvent e){
            	int x = e.getTrigger().getX();
                int y = e.getTrigger().getY();
            
                Color c1 = new Color(image.getRGB(x, y));
                BufferedImage tmp = clona(image);
                
                
                msg = "$(Pulsar Histo)  x:" + x + "y:" + y ;
                msg += " Color[r= " + c1.getRed()  + " g= "+ c1.getGreen()  + ",b= " + c1.getBlue() + "]\n";
                cmdLine.append(msg); 
                
                for (int u = 0; u < image.getWidth(); u++){
                	for (int v = 0; v < image.getHeight(); v++){
                		
                		Color c2 = new Color(image.getRGB(u, v));
                		                	                    	    
                	        if ( c1.getRGB() == c2.getRGB()){// Pintar en color                 	        
	                	        image.setRGB(u, v, new Color(0,255,0).getRGB()); // Pintar de verde
	                	        
                	        }   else{ // Colocar en grises
                	        	   if (colorBarras.equals(Color.RED))                	            	   
                	            	   image.setRGB(u, v, new Color(c2.getRed(),0,0).getRGB());
                	        	   if (colorBarras.equals(Color.GREEN))                	            	   
                	            	   image.setRGB(u, v, new Color(0,c2.getGreen(),0).getRGB());   
                	        	   if (colorBarras.equals(Color.BLUE))                	            	   
                	            	   image.setRGB(u, v, new Color(0,0,c2.getBlue()).getRGB());
                	        	   if (colorBarras.equals(Color.GRAY)){
                	        		   int med = (int)((c2.getRed()+c2.getGreen()+c2.getBlue())/3);
                	        		   image.setRGB(u, v, new Color(med,med,med).getRGB());
                	        	   }                	        	               					
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