import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class Mensajes {
	
	private String msg;
	
	public String getWelcome (){
		 return msg = "\n\n  UNIVERSIDAD DE LA LAGUNA           \n"
		           + "   E.T.S DE INGENIERIA INFORMATICA        \n"
		           + "   TFG - GRADO EN INGENIERIA INFORMATICA  \n"		           
		           + "   AUTOR : LEONARDO SIVERIO CHASOY        \n";
		        
		
	}
	
	public String getVersion(){
		return msg = "$ > TFG - sImage beta v.4.0";
				
	}
	
	public JTextArea initMsg(JTextArea panelCMD){
		
		
		panelCMD.setBounds(5, 295, 500, 200);
		panelCMD.setText(getWelcome() +"\n   "+ getVersion());
	    panelCMD.setFont(new Font("Consolas", Font.PLAIN, 11));
	    panelCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
	    panelCMD.setLineWrap(true);
	    panelCMD.setColumns(10);
	    panelCMD.setRows(10);
	    
	    return panelCMD;
	}
	
	public String msgOperacion(int opcion, String file, BufferedImage img){
		
	  String infoImage = " Type = " + img.getType() + " Height = " + img.getHeight() + " Width = " + img.getWidth() +'\n';	  
	  				     			  
	  switch(opcion){
          case 0:
        	  return msg = "$Run .. [load image]: Name: " + file +  infoImage;	                            
          case 1:
        	  return msg = "$Run .. [Save image]: Name: " + file +  infoImage;             
          case 2:
        	  return msg = "$Run .. [Zoom Plus]: Name: " + file +  infoImage;
          case 3:
        	  return msg = "$Run .. [Zoom Minus]: Name: " + file + infoImage;
          case 4:
        	  return msg = "$Run .. [Rotar Dch]: Name: " + file +  infoImage;        	  
          case 5:
        	  return msg = "$Run .. [Rotar Izq]: Name: " + file +  infoImage;
          case 6:	  
        	  return msg = "$Run .. [Deshacer]: File name: " + file + infoImage;
          case 7:	  
        	  return msg = "$Run .. [Deshacer Todo]: File name: " + file + infoImage;	  
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
	
	public String showInfo(Image image){
		
		ImageInfo info = image.getInfo();
		
		String infoRGB = " RANGO DE VALORES RGB: ";	   	   
		infoRGB += " r = [" + info.minR + ", " + info.maxR + "]";
		infoRGB += ", g = [" + info.minG + ", " + info.maxG + "]";
		infoRGB += ", b = [" + info.minB + ", " + info.maxB + "]\n";
		
		String infoTam = " DIMENSIONES: ";		
		infoTam += " Ancho = [" + image.widthRoi() + "], Alto = " + image.heightRoi()+ "]\n";
		
		String infoCol = " VALORES:     ";
		infoCol += " Brillo = [" + info.brillo;
		infoCol += "], Contraste =  " + info.contraste;
		infoCol += "], Entrop\u00EDa =  " + Math.rint(info.entropia * 1000) / 1000 + "]\n";
		
		return  infoRGB + infoCol + infoTam ;
	}

}