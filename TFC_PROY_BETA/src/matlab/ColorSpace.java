package matlab;

import images.Tracer;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import main.MainWindow;
import matlabToJavaSC.*;
import channel.Canales;

import com.mathworks.toolbox.javabuilder.Images;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;


@SuppressWarnings("serial")
public class ColorSpace extends JDialog {

	private BufferedImage imgOut;        
    /**
     * @return: Menu  
     */
    
    public JMenu getMenu(final Espacios espacios){
    	
      JMenu menu = new JMenu("Color's Space");
		
		JMenuItem rgb = new JMenuItem("HSV");		
		rgb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
			if (MainWindow.getFile().exists()){
		    		Tracer.insert("toImgHsv", "ImageHSV", toImgHsv(espacios, MainWindow.getPath()));			    						
		    	    Canales.setCanal("HSV");
		        }else{ 
		    		String cad = "Debe guardar la imagen " + MainWindow.getPath() + " antes de cambiar el espacio de color";
		    		JOptionPane.showMessageDialog(null, cad, "Informacion", JOptionPane.ERROR_MESSAGE);
		    	}		   
			}
		});		
		menu.add(rgb); 
		
		
		JMenuItem lab = new JMenuItem("LAB");		
		lab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				if (MainWindow.getFile().exists()){
		    		Tracer.insert("toImgLab","ImageLab", toImgLab(espacios, MainWindow.getPath()));			    						
		    	    Canales.setCanal("LAB");
		        }else{ 
		    		String cad = "Debe guardar la imagen " + MainWindow.getPath() + " antes de cambiar el espacio de color";
		    		JOptionPane.showMessageDialog(null, cad, "Informacion", JOptionPane.ERROR_MESSAGE);
		    	}		   
				  
			}
		});		
		menu.add(lab);
		
		JMenuItem yCbCr = new JMenuItem("YCbCr");		
		yCbCr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				if (MainWindow.getFile().exists()){
		    		Tracer.insert("toImgYCbCr","ImageYCbCr", toImgYCbCr(espacios, MainWindow.getPath()));			    						
		    	    Canales.setCanal("YCbCr");
		        }else{ 
		    		String cad = "Debe guardar la imagen " + MainWindow.getPath() + " antes de cambiar el espacio de color";
		    		JOptionPane.showMessageDialog(null, cad, "Informacion", JOptionPane.ERROR_MESSAGE);
		    	}		   			   
			}
		});		
		menu.add(yCbCr);
    	
		return menu; 
    }
	
 
	
 //========================================================================================
	public BufferedImage toImgHsv(Espacios esp, String pathString) {
		Object[] result;
		try {
			result = esp.convertRgb2hsv(2, pathString);					 
			imgOut = getBufferedImageFromDeployedComponent(result[1]);		
			kill(result); // -> Kill result							
		
			
		} catch (MWException e) {		
			String cad = "ha ocurrido un error durante la generacion Imagen Hsv";
    		JOptionPane.showMessageDialog(null, cad, "Error toImgHsv", JOptionPane.ERROR_MESSAGE);
			
		}		
		
		return imgOut;
    }
	   //========================================================================================
   	public BufferedImage toImgLab(Espacios esp, String path) {
   		try {   					
            Object[] result = esp.convertRgb2lab(2, path);        
   			imgOut = getBufferedImageFromDeployedComponent(result[1]);
   			kill(result); // -> Kill result							   			
   			
   		} catch (MWException e) {		
   			String cad = "ha ocurrido un error durante la generacion Imagen Lab";
    		JOptionPane.showMessageDialog(null, cad, "Error toImgLab", JOptionPane.ERROR_MESSAGE);
   			
   		}		
   		
   		return imgOut;
       }
    //========================================================================================
   	public BufferedImage toImgYCbCr(Espacios esp, String path) {
   		try {   		    			
            Object[] result = esp.convertRgb2ycbcr(2, path);            
   			imgOut = getBufferedImageFromDeployedComponent(result[1]);
   			kill(result); // -> Kill result							   			   			
   		} catch (MWException e) {		
   			String cad = "ha ocurrido un error durante la generacion Imagen YCbCr";
    		JOptionPane.showMessageDialog(null, cad, "Error toImgYCbCr", JOptionPane.ERROR_MESSAGE);   			
   		}		   		
   		return imgOut;
       }	
   //========================================================================================
	public void kill (Object[] result){
		
       for (int i = 0; i < result.length; i++) {
			MWNumericArray.disposeArray(result[i]);
		}
	}

//========================================================================================	
	public static BufferedImage getBufferedImageFromDeployedComponent(Object result)
	{
	        BufferedImage resImg = Images.renderArrayData((MWNumericArray)result);
	        return resImg;
	}

}// fin de la clase