package procesos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;


import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.entity.ChartEntity;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
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
	private BufferedImage newImg;
	private CategoryPlot plot;
	

	
	
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
                int columna = getColumna(e);
                if (( columna != -1) && HistoInfo.clicked(columna, histograma[columna], colorBarras)){ // Se ha clicked dos veces                    	                 	  		            		            	 		        			
		        	  pintar(columna,newImg,colorBarras);		        			  		        			 		        		        		 		       	      
		              Tracer.insert("*image", newImg); // Generar una nueva imagen 
                  }      
                                         
            }
            
			@Override
			public void chartMouseMoved(ChartMouseEvent event) {
		     /* Funcion para capturar la posicion del raton sobre el Histograma */
				int columna = getColumna(event);
				if (columna != -1)
					HistoInfo.posXY(String.valueOf(columna), String.valueOf(histograma[columna]),columna, colorBarras);
			}	
        });   
         
        jpHisto.validate();        
        
    }
 //==========================================================================================================
    // Funcion: Captura una barra del histograma 
    public int getColumna(ChartMouseEvent event){

        ChartEntity entity = event.getEntity();
        if (entity != null) {
        	String cad = entity.toString();		            
        	if (((cad.indexOf("[") + 1 ) != -1) && (cad.indexOf("]") != -1)){ 
            	return Integer.parseInt(cad.substring(cad.indexOf("[") + 1, cad.indexOf("]")));            	  
            	
        	}	
        }
		return -1;        			 	
   }
    	
    
 //==========================================================================================================        
    public static BufferedImage clona(BufferedImage imagen){
    	BufferedImage copia = new BufferedImage (imagen.getWidth(),imagen.getHeight(),imagen.getType());
    	copia.setData(imagen.getData());
    	return copia;
    }
    public void pintar (int c1, final BufferedImage image, final Color colorBarras){
    	    	       
    	int valor_en_canal = 0;
    	
         for (int u = 0; u < image.getWidth(); u++){
         	for (int v = 0; v < image.getHeight(); v++){
         		
         		 Color c2 = new Color(image.getRGB(u, v));
         		 int r = c2.getRed();
         		 int b = c2.getBlue();
         		 int g = c2.getGreen();
         		 // ============ canal ======== 
	     		 if (colorBarras.equals(Color.RED))                 	            	   
                      valor_en_canal = r;  	            
	        	 if (colorBarras.equals(Color.GREEN))                	            	   
	        		 valor_en_canal = g;
	        	 if (colorBarras.equals(Color.BLUE))                	            	   
	        		 valor_en_canal = b;
	        	  
         		          	  	 
                 if (c1 == valor_en_canal){              
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