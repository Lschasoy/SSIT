package procesos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryLabelEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

import Images.ImagePanel;
import Images.Tracer;
 

public class DibujarGrafica {
      /**
     * Crea un gráfico de barras y lo asigna al JPanel recibido
     * @param histograma histograma de frecuencias (int[256]).
     * @param jPanelHistograma JPanel donde el histograma será dibujado
     * @param colorBarras color de cuál será dibujado el histograma
     */

	private Histograma ObjHistograma;
	private int[][] histograma;
	private int count = 0, x0, y0, x, y;
	private Color c1;
	private BufferedImage newImg;
	private CategoryPlot plot;
	
	double  chartpx = 0.0, chartpy = 0.0;
	double chartY = 0.0, chartX = 0.0;
	
	
	public DibujarGrafica(){
		//CREAMOS EL HISTOGRAMAS
        ObjHistograma=new Histograma();
        
        
	}
		
	public void Graficar ( BufferedImage image, ImagePanel Panel, final JPanel [] canal){
		
		
        histograma=ObjHistograma.histograma(image);
        newImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                
        //DIBUJAMOS LOS HISTOGRAMAS        
        System.out.println("\n Ejecutando clase DibujarGrafica -> [Graficar]");
	      
	        for (int i = 0; i < 5; i++) {
	            //extraemos un canal del histograma 
	            int[] histogramaCanal=new int[256];
	            System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
	            //Dibujamos en el panel
	            switch(i){
	                case 0:
	                    crearHistograma(histogramaCanal, canal[0], Color.red, image, Panel);	                    
	                    break;
	                case 1:
	                    crearHistograma(histogramaCanal, canal[1], Color.green, image, Panel);
	                    break;
	                case 2:
	                    crearHistograma(histogramaCanal, canal[2], Color.blue, image, Panel);
	                    break;
	                case 3:
	                   // crearHistograma(histogramaCanal, canal[3], Color.black, image, actPanel, panelCMD);
	                    break;
	                case 4:
	                    crearHistograma(histogramaCanal, canal[3], Color.gray, image, Panel);
	                    break;
	            }
	        }
	}
	
    public void crearHistograma(final int[] histograma,final JPanel jpHisto,final Color colorBarras, final BufferedImage image, final JPanel jpImagen) {
    	    	         
    	//Creamos el dataSet y añadimos el histograma
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String serie = "Píxeles nº ";
        for (int i = 0; i < histograma.length; i++){
            dataset.addValue(histograma[i], serie, "[" + i +"] ");                  
        }
        //Creamos el chart
        final JFreeChart chart = ChartFactory.createBarChart(null, null, null,dataset, PlotOrientation.VERTICAL, false, true, false);                 
        final ChartPanel panel = new ChartPanel (chart, false); // --> lsch
							
        //Modificamos el diseño del chart
        plot = (CategoryPlot) chart.getPlot();
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, colorBarras);                
        
        chart.setAntiAlias(true);                        
        chart.setBackgroundPaint(new Color(255, 255, 255));
        jpHisto.removeAll();
        jpHisto.repaint();
        jpHisto.setLayout(new BorderLayout());
        jpHisto.add(panel); //-- > lsch    
        
        
        
        newImg = clona(image);
           
        panel.addChartMouseListener(new ChartMouseListener(){        	
            public void chartMouseClicked(ChartMouseEvent e){            	
            	count++;
            	x = e.getTrigger().getX(); 
                y = e.getTrigger().getY();            
                
                if ((count %2) == 0){
                  if (HistoInfo.clicked(newImg.getRGB(x0, y0), newImg.getRGB(x, y))){ // Se ha clicked dos veces                    	 
                	  if (x0 > x ) {int tmp = x; x = x0; x0 = tmp;}
                	  if (y0 > y ) {int tmp = y; y = y0; y0 = tmp;}                      
                	  
		              while (x0 <= x){
		            	while (y0 <= y){                			 
		        			 c1 = new Color(newImg.getRGB(x0, y0));                			 
		        			 pintar(c1,newImg,colorBarras);		        			  
		        			 y0++;
		        		 }        
		        		 x0++;
		       	     }   
		              Tracer.insert("*image", newImg);
                  } // end of if when clicked
               } // end of if when count == 2
               else{                                   
            	  x0 = x;
            	  y0 = y;   
            	  String punto = " Click on ("+ x0 +':'+ y0 +") ";
            	  HistoInfo.click(newImg.getRGB(x0, y0), punto, colorBarras);
               }                            
            }
            
			@Override
			public void chartMouseMoved(ChartMouseEvent event) {
		     /* Funcion para capturar la posicion del raton sobre el Histograma */			
			    int x = event.getTrigger().getX();
		        int y = event.getTrigger().getY();
		        ChartEntity entity = event.getEntity();
		        if (entity != null) {
		        	String cad = entity.toString();		            
		        	if (((cad.indexOf("[") + 1 ) != -1) && (cad.indexOf("]") != -1)){ 
		            	int pos = Integer.parseInt(cad.substring(cad.indexOf("[") + 1, cad.indexOf("]")));
		            	HistoInfo.posXY(String.valueOf(pos), String.valueOf(histograma[pos]),pos, colorBarras);  	
		            	System.out.println("Mouse Moved - Columna " + pos + "  Valor: " + histograma[pos] );
		        	}	
		        }
		        else {
		            System.out.println("Mouse moved: " + x + ", " + y + ": null entity.");
		        }
						 			
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
    public void pintar (Color c1, final BufferedImage image, final Color colorBarras){
    	    	       
    	
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
     	          } // end else
         	          
         	} // end for  
         	
         }// end for
    	
    }
    
    
    
}// end of class