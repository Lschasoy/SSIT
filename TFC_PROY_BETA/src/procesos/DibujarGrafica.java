package procesos;
import java.awt.Color;
import java.awt.image.BufferedImage;


import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
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

	private String msg;
	private int count = 0, x0 = 0, y0 = 0;
	private Color c1;
	
    public void crearHistograma(int[] histograma,JPanel jpHisto,final Color colorBarras, final BufferedImage image, final JPanel jpImagen, final JTextArea cmdLine) {
    	    	 
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
        
        
        chart.setBackgroundPaint(new Color(214, 217, 0)); 
        jpHisto.removeAll();
        jpHisto.repaint();
        jpHisto.setLayout(new java.awt.BorderLayout());
        jpHisto.add(panel); //-- > lsch
        
        
        panel.addChartMouseListener(new ChartMouseListener(){        	
            public void chartMouseClicked(ChartMouseEvent e){
            	// Histograma seleccionado
            	
            	count++;
            	int x = e.getTrigger().getX();
                int y = e.getTrigger().getY();            
                                                                                                                               
                 if ((count %2) == 0){
                	 msg += "$[Clicked in Histo]  (x0:" + x + ",y0:"+y+") ... ";
                	 msg += "p0: ("+x0+','+y0+")" + "p1: (" +x+','+y+") \n"; 
                	 cmdLine.append(msg);
                	 if (x0 > x ) {int tmp = x; x = x0; x0 = tmp;}
                	 if (y0 > y ) {int tmp = y; y = y0; y0 = tmp;}                	 
                	 while (x0 <= x){
                		 while (y0 <= y){                			 
                			 c1 = new Color(image.getRGB(x0, y0));                			 
                			 pintar(c1,jpImagen,image,colorBarras);
                			 jpImagen.repaint(); 
                			 y0++;
                		 }        
                		 x0++;
               	     }
                	                                 		                    		                   
                 }else{
                    msg = ">[clicked in Histo] (x0:" + x + ",y0:"+y+") ...... Pulse el otro punto\n";
                    cmdLine.append(msg);
                	x0 = x;
                	y0 = y;
                	
                 }
                               
            }
            
			@Override
			public void chartMouseMoved(ChartMouseEvent e) {
		     /* Funcion para capturar la posicion del raton sobre el Histograma */
				
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
    public void pintar (Color c1, final JPanel jpImagen, final BufferedImage image, final Color colorBarras){
    	    	       
    	
         for (int u = 0; u < image.getWidth(); u++){
         	for (int v = 0; v < image.getHeight(); v++){
         		
         		 Color c2 = new Color(image.getRGB(u, v));
         		 int r = c2.getRed();
         		 int b = c2.getBlue();
         		 int g = c2.getGreen();
         		          	  	 
                 if (c1.getRGB() == c2.getRGB()){         	    	
             	     image.setRGB(u, v, new Color(0,255,0).getRGB()); // Pintar de verde
             	        
         	     }else{ // Colocar en grises
     	        	   if (colorBarras.equals(Color.RED))                	            	   
     	            	   image.setRGB(u, v, new Color(r,r,r).getRGB());
     	        	   if (colorBarras.equals(Color.GREEN))                	            	   
     	            	   image.setRGB(u, v, new Color(g,g,g).getRGB());   
     	        	   if (colorBarras.equals(Color.BLUE))                	            	   
     	            	   image.setRGB(u, v, new Color(b,b,b).getRGB());
     	        	   if (colorBarras.equals(Color.GRAY)){
     	        		   int med = (int)((r + g + b)/3);
     	        		   image.setRGB(u, v, new Color(med,med,med).getRGB());
     	        	   }                	        	               					
     	          }
         	          
         	}   
         	
         }
    	
    }
    
    
    
}