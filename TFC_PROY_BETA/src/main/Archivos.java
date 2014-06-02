package main;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTable;




public class Archivos {
	
	private static JFileChooser fc;
	private static String nameImage;
	
//======================================================================================================================
	
	public String getImageName  ()         { return nameImage; }
	public JFileChooser getFile ()         {return fc;}



    /**
     * @param tablaMenuImage
     * @param modelo: Es una matrix que contiene la imagenes 
     * @return La imagen cargada
     */
	public static File loadFile(){
		
		 File imagen = null;	
		 fc = new JFileChooser();
		 int returnVal = fc.showOpenDialog(fc);
         if (returnVal == JFileChooser.APPROVE_OPTION){
        	imagen = fc.getSelectedFile();        	         	        	         	
         }
		return imagen;
	}
//=================================================================================	
	public void saveFile(){
		
		fc = new JFileChooser();
		fc.setDialogTitle("Specify a file to save");   
		 
		int userSelection = fc.showSaveDialog(fc);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fc.getSelectedFile();
		  
		}
	}

}
