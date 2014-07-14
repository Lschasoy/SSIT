package channel;

import images.Tracer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import main.Info;
import main.MainWindow;


public class Canales{

	
	
	private static String[] rgb = { "canal_R", "canal_G", "canal_B"};
	private static String[] hsv = { "canal_H", "canal_S", "canal_V"};
	private static String[] lab = { "canal_L", "canal_A", "canal_B"};
	private static String[] YCbCr = {"canal_C", "canal_Y", "canal_K"};
	
	private static BufferedImage imgOut;
	private static String[] act = rgb;
	
	public static JMenu getMenu(){
				 
		JMenu menu = new JMenu("Canales");						
		// ============ CANAL 0 =====================================
		JMenuItem c0 = new JMenuItem("Canal 1");		
		c0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Generando canal: "+ act[0]);
		        gCanalR(MainWindow.getCurrentImage().img);			         			         			         			        						 					 					  
				Tracer.insert("Channel paint",act[0], imgOut);
			    Info.msgOut(" Canales ",act[0]);			    		  
			}
		});
		menu.add(c0);
		
		// ============ CANAL 1 =====================================
		JMenuItem c1 = new JMenuItem("Canal 2");		
		c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Generando canal: "+ act[1]);
		        gCanalG(MainWindow.getCurrentImage().img);			         			         			         			        						 					 					  
				Tracer.insert("Channel paint",act[1], imgOut);
			    Info.msgOut(" Canales ",act[1]);			    		  
			}
		});
		menu.add(c1);
		// ============ CANAL 2 =====================================
		JMenuItem c2 = new JMenuItem("Canal 3");		
		c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Generando canal: "+ act[2]);
		        gCanalB(MainWindow.getCurrentImage().img);			         			         			         			        						 					 					  
				Tracer.insert("Channel paint",act[2], imgOut);
			    Info.msgOut(" Canales ",act[2]);			    		  
			}
		});
		menu.add(c2);			
			
		return menu;	
	}		
//====================================================================================================	
	public static void setCanal(String espacio){
	    System.out.println("Canales: Fijar espacio de color: "+ espacio);
				
		switch(espacio){
			case "RGB": act = rgb;
					   break;	
			case "HSV": act = hsv;
				       break;
			case "LAB": act = lab;
			           break;
			case "YCbCr": act = YCbCr; 
					   break;			
		}	
		
	}
//================================================================================================	
	 public final static void gCanalB(BufferedImage image){
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
	 public final static void gCanalG(BufferedImage image){
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
	 public final static void gCanalR(BufferedImage image) {
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
