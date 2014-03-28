import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import java.awt.FlowLayout;


public class PopWindows {
	
	static final int rCanal = 0, gCanal = 1, bCanal = 2, xCanal = 3, yCanal = 4;
	

	private JFrame win2;
	private JPanel iPanel, gPanel, cPanel;
	private Color color;

	private Histograma ObjHistograma;
    private int[][] histograma;
    private DibujarGrafica ObjDibujaHisto;
    
	/** Pop Windows **/
   	public PopWindows() {
   		
   		try {
   			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
        
     	// ************ Initializar ****************************
   	    //CREAMOS EL HISTOGRAMAS
        ObjHistograma=new Histograma();
        //DIBUJAMOS LOS HISTOGRAMAS
        ObjDibujaHisto=new DibujarGrafica();
        
        //*********** Init Ventana ******************************
		
		win2 = new JFrame();		
		win2.setTitle("TFG - sImage beta v.3.0");
		win2.getContentPane().setBackground(Color.LIGHT_GRAY);
	
		win2.setSize(800, 416);
		win2.setResizable(false);
		 
		cPanel = new JPanel();
		cPanel.setLayout(null);
		
		cPanel.add(imagePanel());
		cPanel.add(histoPanel());
										
		win2.getContentPane().add(cPanel,BorderLayout.CENTER);
		
					
	}
   	
   	public JPanel histoPanel(){
   		
   		gPanel = new JPanel();		
   		gPanel.setSize(234, 230);
   		gPanel.setLocation(550, 5);
		gPanel.setBackground(Color.LIGHT_GRAY);
		gPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
		return gPanel;
   		
   	}
   	public JPanel imagePanel(){
   		
   		iPanel = new JPanel();
		iPanel.setBackground(Color.WHITE);
		
		
		final JPanel pScroll = new JPanel();
		pScroll.setBounds(5, 5, 540, 372);			
		pScroll.setLayout(null);
	    
		final JScrollPane scroll = new JScrollPane(iPanel);	
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(5, 5, 520, 356);		
		pScroll.add(scroll);				
	
		return pScroll; 
   	}
  
   	
   	
   	/*************************************************/
	 public final void gCanalR(BufferedImage image) {
	    								
			BufferedImage salida = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());;
			
			 for (int x = 0; x < image.getWidth(); x++) {
				    for (int y = 0; y < image.getHeight(); y++) {
				    	Color c = new Color(image.getRGB(x, y));
				    	int r = c.getRed();
				    	int g = c.getGreen();
				    	int b = c.getBlue();
				    	salida.setRGB(x, y, new Color(r, b, g).getRGB());
				    }	
			 }	    
							
		mostrar(iPanel, salida);		
		
        histograma=ObjHistograma.histograma(image);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[rCanal], 0, histogramaCanal, 0, histograma[rCanal].length);
	    //Dibujamos en el panel
	    ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.red, image);
	 }	
	 //====================================================================================================
	 public final void gCanalY(BufferedImage image){
		 
			
		  BufferedImage salida = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());					   
				
		   for (int x=0; x <  image.getWidth(); x++){
				for (int y=0;y < image.getHeight(); y++){
					//Obtiene el color
					color =new Color(image.getRGB(x, y));
					//Calcula la media de tonalidades
					int med=(color.getRed()+color.getGreen()+color.getBlue())/3;
					//Almacena el color en la imagen destino
					salida.setRGB(x, y, new Color(med,med,med).getRGB());
				}
			}
							
		mostrar(iPanel, salida);
		
		
        histograma=ObjHistograma.histograma(image);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[yCanal], 0, histogramaCanal, 0, histograma[yCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.black, image);
		      		        		  		        		  		 		 
	 }
	 //====================================================================================================
	 public final void gCanalG(BufferedImage image){
		 
		 BufferedImage salida = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());;
		 
		 for (int x = 0; x < image.getWidth(); x++) {
			    for (int y = 0; y < image.getHeight(); y++) {
			    	Color c = new Color(image.getRGB(x, y));
			    	int r = c.getRed();
			    	int g = c.getGreen();
			    	int b = c.getBlue();
			    	salida.setRGB(x, y, new Color(g, b, r).getRGB());
			    }	
		 }	    		
							
		mostrar(iPanel, salida);
		
		
        histograma=ObjHistograma.histograma(image);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[yCanal], 0, histogramaCanal, 0, histograma[yCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.black, image);
		      		        		  		        		  		 		 
	 }
	 //====================================================================================================
	 public final void gCanalB(BufferedImage image){
		 
		 BufferedImage salida = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());;
		 
		 for (int x = 0; x < image.getWidth(); x++) {
			    for (int y = 0; y < image.getHeight(); y++) {
			    	Color c = new Color(image.getRGB(x, y));
			    	int r = c.getRed();
			    	int g = c.getGreen();
			    	int b = c.getBlue();
			    	salida.setRGB(x, y, new Color(b, g, r).getRGB());
			    }	
		 }	    		
							
		mostrar(iPanel, salida);
		
		
        histograma=ObjHistograma.histograma(image);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[yCanal], 0, histogramaCanal, 0, histograma[yCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.black, image);
		      		        		  		        		  		 		 
	 }
	 /*************************************************/
	 private void mostrar(JPanel panel, BufferedImage image){
			
			panel.removeAll();
			panel.repaint();
					
			ImageIcon ico = new ImageIcon(image);				
			JLabel label = new JLabel (ico);													
			panel.add(label);		
					
		    win2.setVisible(true);	
		}

}
