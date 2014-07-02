package procesos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Images.Image;
import main.Info;
import main.MainWindow;


public class Canales{

	@SuppressWarnings("rawtypes")
	private JComboBox cbCanales;
	
	private String[] rgb = {"Canales", "canal_R", "canal_G", "canal_B"};
	private String[] hsv = {"Canales", "canal_H", "canal_S", "canal_V"};
	private String[] lab = {"Canales", "canal_L", "canal_A", "canal_B"};
	private String[] YCbCr = {"Canales", "canal_C", "canal_Y", "canal_K"};
	private BufferedImage imgOut; 
	
	private File tmpFile;
	
	
//====================================================================================================
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox init(final JPanel [] canales){
		System.out.println("Clases Canales - Constructor");
		cbCanales = new JComboBox();				
		cbCanales.setBounds(305, 0, 150, 20);
		cbCanales.setModel(new DefaultComboBoxModel(rgb));		
		// =========================== Espacio de Colores ====================================		
		cbCanales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    if (cbCanales.getSelectedItem().equals(cbCanales.getItemAt(1) .toString())){
			    	System.out.println("Generando canal: "+ cbCanales.getItemAt(1) .toString());
			         gCanalR(MainWindow.getCurrentImage().img);			         
			         			         			        						 
					 tmpFile = new File ("\\tmp", cbCanales.getItemAt(1) .toString());					  
					 Image newImg = new Image(tmpFile,imgOut,true, canales);
					 Info.msg(8,newImg.name, newImg.img);
					 MainWindow.mostrar(newImg);
					 Info.msgOut(" Canales ", cbCanales.getItemAt(1) .toString());			       
			    }   
			    if (cbCanales.getSelectedItem().equals(cbCanales.getItemAt(2) .toString())){
			    	System.out.println("Generando canal: "+ cbCanales.getItemAt(2) .toString());
			         gCanalG(MainWindow.getCurrentImage().img);			         			         			         			        						
					 tmpFile = new File ("\\tmp", cbCanales.getItemAt(2) .toString());					 
					 Image newImg = new Image(tmpFile,imgOut,true, canales);
					 Info.msg(8,newImg.name, newImg.img);
					 MainWindow.mostrar(newImg);
					 Info.msgOut(" Canales ", cbCanales.getItemAt(2) .toString());
			    }
			    if (cbCanales.getSelectedItem().equals(cbCanales.getItemAt(3) .toString())){
			    	System.out.println("Generando canal: "+ cbCanales.getItemAt(3) .toString());
			         gCanalB(MainWindow.getCurrentImage().img);			         			         			         			        						
					 tmpFile = new File ("\\tmp", cbCanales.getItemAt(3) .toString());					  
					 Image newImg = new Image(tmpFile,imgOut,true, canales);
					 Info.msg(8,newImg.name, newImg.img);
					 MainWindow.mostrar(newImg);
					 Info.msgOut(" Canales ", cbCanales.getItemAt(3) .toString());
			    }
			}
		});	
		
		return cbCanales;
	}

//====================================================================================================
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox generarComoboBox(String espacio, final BufferedImage img){
	    System.out.println("GeneradorComboBox: "+ espacio);
				
		switch(espacio){
			case "RGB": cbCanales.setModel(new DefaultComboBoxModel(rgb));
					   break;	
			case "HSV": cbCanales.setModel(new DefaultComboBoxModel(hsv));
				       break;
			case "LAB": cbCanales.setModel(new DefaultComboBoxModel(lab));
			           break;
			case "YCbCr": cbCanales.setModel(new DefaultComboBoxModel(YCbCr));
					   break;			
		}		
	    return cbCanales;
	}
//================================================================================================	
	 public final void gCanalB(BufferedImage image){
		 System.out.println("Pintando en el canal B");
		 imgOut = new BufferedImage(image.getWidth(), image.getHeight(),image.getType());
		 
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
		 System.out.println("Pintando en el canal G");
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
		System.out.println("Pintando en el canal R");
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
		 System.out.println("Pintando en el canal Y");					  
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
