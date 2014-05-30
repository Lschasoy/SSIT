import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;


public class Canales{

	private final JPanel contentPanel = new JPanel();
	private String[] rgb = {"Canal R", "Canal G", "Canal B"};
	private String[] hsv = {"Canal H", "Canal S", "Canal V"};
	private String[] lab = {"Canal L", "Canal A", "Canal B"};
	private String[] YCbCr = {"Canal C", "Canal Y", "Canal K"};
	private BufferedImage imgOut; 
	
	public BufferedImage getImgOut() {
		return imgOut;
	}

   //====================================================================================================
	public JComboBox generarComoboBox(String espacio, final BufferedImage img){
	    System.out.println("GeneradorComboBox: "+ espacio);
		final JComboBox cbCanales = new JComboBox();				
		cbCanales.setBounds(305, 0, 150, 25);
		
		switch(espacio){
			case "HSV": cbCanales.setModel(new DefaultComboBoxModel(hsv));
				       break;
			case "LAB": cbCanales.setModel(new DefaultComboBoxModel(lab));
			           break;
			case "YCbCr": cbCanales.setModel(new DefaultComboBoxModel(YCbCr));
				break;
			default: cbCanales.setModel(new DefaultComboBoxModel(rgb));
			     break;
		}
		
		// =================== Espacio de Colores ====================================		
		cbCanales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    if (cbCanales.getSelectedItem().equals(cbCanales.getItemAt(0) .toString())){
			    	System.out.println("Canal_1");
			        gCanalR(img);
		    	 
			    }   
			    if (cbCanales.getSelectedItem().equals(cbCanales.getItemAt(1) .toString())){
			    	System.out.println("Canal_2");
			    	gCanalG(img);
			    }
			    if (cbCanales.getSelectedItem().equals(cbCanales.getItemAt(2) .toString())){
			    	System.out.println("Canal_3");
			    	gCanalB(img);		
			    	
			    }
			}
		});	
	    return cbCanales;
	}
	
	 public final void gCanalB(BufferedImage image){
		 
		 imgOut = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());;
		 
		 for (int x = 0; x < image.getWidth(); x++) {
			    for (int y = 0; y < image.getHeight(); y++) {
			    	Color c = new Color(image.getRGB(x, y));			    	
			    	int b = c.getBlue();
			    	imgOut.setRGB(x, y, new Color(b, b, b).getRGB());	
			    }	
		 }	    		
									
	 }
	 //====================================================================================================
	 public final void gCanalG(BufferedImage image){		 
		 imgOut = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());;
		 
		 for (int x = 0; x < image.getWidth(); x++) {
			    for (int y = 0; y < image.getHeight(); y++) {
			    	Color c = new Color(image.getRGB(x, y));			    	
			    	int g = c.getGreen();			    	
			    	imgOut.setRGB(x, y, new Color(g, g, g).getRGB());
			    }	
		 }	    		
							
	  }
	//====================================================================================================
	 public final void gCanalR(BufferedImage image) {
		
		imgOut = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());;
		
		 for (int x = 0; x < image.getWidth(); x++) {
			    for (int y = 0; y < image.getHeight(); y++) {
			    	Color c = new Color(image.getRGB(x, y));				    		
			    	int r = c.getRed();			    	
			    	imgOut.setRGB(x, y, new Color(r, r, r).getRGB());	               				    
			    }	
		 }	    						
		
	 }		
	 //====================================================================================================	
	 public final void gCanalY(BufferedImage image){		 
					  
		  imgOut = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());					   
				
		   for (int x=0; x <  image.getWidth(); x++){
				for (int y=0;y < image.getHeight(); y++){
					//Obtiene el color
					Color color = new Color(image.getRGB(x, y));
					//Calcula la media de tonalidades
					int med=(color.getRed()+color.getGreen()+color.getBlue())/3;
					//Almacena el color en la imagen destino
					imgOut.setRGB(x, y, new Color(med,med,med).getRGB());
				}
			}
							
	 }
	

}
