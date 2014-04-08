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
		
		
		panelCMD.setBounds(720, 295, 500, 200);
		panelCMD.setText(getWelcome() +"\n   "+ getVersion());
	    panelCMD.setFont(new Font("Consolas", Font.PLAIN, 11));
	    panelCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
	    panelCMD.setLineWrap(true);
	    panelCMD.setColumns(10);
	    panelCMD.setRows(10);
	    
	    return panelCMD;
	}
	
	public String msgOperacion(int opcion, String file, BufferedImage image){
		
	  String infoImage = image.toString();
					     			  
	  switch(opcion){
          case 0:
        	  return msg = "$ > [load image]: File name: " + file
			   + 	"\n$ > [return]:" + infoImage;	                            
          case 1:
        	  return msg = "$ > [Save image]: File name: " + file
			   + 	"\n$ > [return]:" + infoImage;             
          case 2:
        	  return msg = "$ > [Zoom Plus]: File name: " + file
			   + 	"\n$ > [return]:" + infoImage;
          case 3:
        	  return msg = "$ > [Zoom Minus]: File name: " + file
			   + 	"\n$ > [return]:" + infoImage;
          case 4:
        	  return msg = "$ > [Rotar Dch]: File name: " + file
			   + 	"\n$ > [return]:" + infoImage;        	  
          case 5:
        	  return msg = "$ > [Rotar Izq]: File name: " + file
			   + 	"\n$ > [return]:" + infoImage;
          case 6:	  
        	  return msg = "$ > [Deshacer]: File name: " + file        			  
       			   + 	"\n$ > [return]:" + infoImage;
          case 7:	  
        	  return msg = "$ > [Deshacer Todo]: File name: " + file        			  
       			   + 	"\n$ > [return]:" + infoImage;	  
      }		
	   
	  return "[Error] msgOperacion";
		
	}
	
	
	

}