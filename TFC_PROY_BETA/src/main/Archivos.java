package main;
import java.awt.Image;
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
		
		Images.Image image = MainWindow.getCurrentImage();
		MainWindow.chooser.setSelectedFile(image.getFileCompleto());
		int returnValue = MainWindow.chooser.showSaveDialog(MainWindow.miVentana);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			image.cambiarFile(MainWindow.chooser.getSelectedFile());
			try {
				ImageIO.write(image.img, "jpg", image.getFileCompleto());
				image.saved = true;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
