import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;


public class PopWindows {
	
	static final int rCanal = 0, gCanal = 1, bCanal = 2, xCanal = 3, yCanal = 4;
	

	private JFrame win2;
	private JPanel iPanel, gPanel, cPanel;
	private Color color;
	private JTextArea cmd;

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
		
		cmd = new JTextArea("$ > Command Line \n");
		cmd.setFont(new Font("Consolas", Font.PLAIN, 12));
		cmd.setEditable(false);
		cmd.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		cmd.setBackground(Color.LIGHT_GRAY);
		cmd.setBounds(550, 202, 234, 175);
		cPanel.add(cmd);
		
					
	}
   	
   	public JPanel histoPanel(){
   		
   		gPanel = new JPanel();		
   		
   		gPanel.setSize(234, 185);
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
			    	salida.setRGB(x, y, new Color(r, 255 - g, 255 -b).getRGB());	               				    
			    }	
		 }	    
		cmd.append(salida.toString() + '\n');				
		mostrar(iPanel, salida);		
		
        histograma=ObjHistograma.histograma(salida);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    
	    System.arraycopy(histograma[rCanal], 0, histogramaCanal, 0, histograma[rCanal].length);
	    cmd.append(histograma[rCanal].toString() + '\n');
	    //Dibujamos en el panel
	    ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.red, salida, iPanel, cmd);
	    
	    
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
			    	salida.setRGB(x, y, new Color(255-r, g, 255-b).getRGB());
			    }	
		 }	    		
							
		mostrar(iPanel, salida);
		
		
        histograma=ObjHistograma.histograma(salida);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[gCanal], 0, histogramaCanal, 0, histograma[gCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.green, salida, iPanel, cmd);
		      		        		  		        		  		 		 
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
			    	salida.setRGB(x, y, new Color(255 -r, 255 -g, b).getRGB());	
			    }	
		 }	    		
							
		mostrar(iPanel, salida);		
		
        histograma=ObjHistograma.histograma(salida);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[bCanal], 0, histogramaCanal, 0, histograma[bCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.blue, salida, iPanel, cmd);
		      		        		  		        		  		 		 
	 }
	 //====================================================================================================
	 public final void gCanalX(BufferedImage image){
		 
			
		  BufferedImage salida = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());					   
				
		   for (int x=0; x <  image.getWidth(); x++){
				for (int y=0;y < image.getHeight(); y++){
					Color c = new Color(image.getRGB(x, y));
					int r = c.getRed();
			    	int g = c.getGreen();
			    	int b = c.getBlue();		
			    	salida.setRGB(x, y, new Color(255 -r, 255 -g, 255-b).getRGB());	
			 
				}			
			}
				
							
		mostrar(iPanel, salida);
		
		
        histograma=ObjHistograma.histograma(salida);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[xCanal], 0, histogramaCanal, 0, histograma[xCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.black, salida, iPanel, cmd);
	   	
	   	  
	 }

	 /*  Name:gCanalY
	  *  Descrip: Calculamos la media 
	  *  Return: image   */
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
		
		
        histograma=ObjHistograma.histograma(salida);  		
        //extraemos un canal del histograma 
	    int[] histogramaCanal=new int[256];
	    System.arraycopy(histograma[yCanal], 0, histogramaCanal, 0, histograma[yCanal].length);
	    //Dibujamos en el panel
	   	ObjDibujaHisto.crearHistograma(histogramaCanal, gPanel, Color.gray, salida, iPanel, cmd);
		      		        		  		        		  		 		 
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
