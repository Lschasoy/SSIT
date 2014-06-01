package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import Images.Image;
import Images.ImageInfo;

public class Mensajes {
	
	private String msg;
	
	public String getWelcome (){
		 return msg = "\n\n\t\tUNIVERSIDAD DE LA LAGUNA        \n"
		           + "\tE.T.S DE INGENIERIA INFORMATICA        \n"
		           + "\tTFG - GRADO EN INGENIERIA INFORMATICA  \n"		           
		           + "\tAUTOR : LEONARDO SIVERIO CHASOY        \n";		        
		
	}
	
	public String getVersion(){
		return msg = "\t$ > TFG - sImage beta v.8.0";
				
	}
	
	public JTextArea initMsg(JTextArea panelCMD){
		
		
	
		panelCMD.setText(getWelcome() +"\n   "+ getVersion());
	    panelCMD.setFont(new Font("Consolas", Font.PLAIN, 11));
	    panelCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
	    panelCMD.setLineWrap(true);
	    panelCMD.setColumns(10);
	    panelCMD.setRows(10);
	    
	    return panelCMD;
	}
	
	public String msgOperacion(int opcion, String file, BufferedImage img){
		
	  
	  String infoImage = " $Tipo = " + img.getType() + " Alto = " + img.getHeight() + " Ancho = " + img.getWidth() +'\n';
	  				     			  
	  switch(opcion){
          case 0:
        	  return msg = " $Run .. [load image]: path: " + file +'\n'+  infoImage;	                            
          case 1:
        	  return msg = " $Run .. [Save image]: path: " + file +'\n'+  infoImage;             
          case 2:
        	  return msg = " $Run .. [Zoom Plus]: path: " + file +'\n'+  infoImage;
          case 3:
        	  return msg = " $Run .. [Zoom Minus]: path: " + file +'\n'+ infoImage;
          case 4:
        	  return msg = " $Run .. [Rotar Dch]: path: " + file +'\n'+  infoImage;        	  
          case 5:
        	  return msg = " $Run .. [Rotar Izq]: path: " + file +'\n'+  infoImage;
          case 6:	  
        	  return msg = " $Run .. [Deshacer]: path: " + file +'\n'+ infoImage;
          case 7:	  
        	  return msg = " $Run .. [Deshacer Todo]: path: " + file +'\n'+ infoImage;	  
      }		
	   
	  return "[Error] msgOperacion";
		
	}
	
	//=================================================================================================
	public JTextArea showInfoInit(JTextArea panelCMD){
		
		
		panelCMD.setBounds(10, 510, 700, 60);
		panelCMD.setText("\t\t >> INFORMACION DE LA IMAGEN  << \n");
	    panelCMD.setFont(new Font("Consolas", Font.PLAIN, 11));
	    panelCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
	    panelCMD.setLineWrap(true);
	    panelCMD.setColumns(10);
	    panelCMD.setRows(10);
	    
	    return panelCMD;
	}
	
}