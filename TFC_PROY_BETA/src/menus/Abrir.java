package menus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;


import javax.swing.JOptionPane;

import Images.ImageFilter;
import Images.Tracer;
import main.MainWindow;

public class Abrir {
	
	public static void run() {
		

		MainWindow.chooser.setFileFilter(new ImageFilter());
		int returnValue = MainWindow.chooser.showOpenDialog(MainWindow.miVentana);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = MainWindow.chooser.getSelectedFile();
			try {
				BufferedImage img = ImageIO.read(file);
				Tracer.insert(file.getName(), img);								
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Hubo error durante la carga", "Abrir informacion", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	/**
	 * @return: load image 
	 */
	public static void load(File file){
		
		try {
			if (file.isFile()){			   
			   Tracer.insert(file,ImageIO.read(file));			   			  
			   
			}else{ //--> Cargamos un direc
				File []list = file.listFiles();
				ImageFilter imgFiltro= new ImageFilter();	
		    	 for (int i = 0; i < list.length; i++ ){
		    		if (imgFiltro.accept(list[i]) && (list[i].isFile()))
		    			Tracer.insert(list[i],ImageIO.read(list[i]));		    		    		    		
		    	 }	
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "[Error] No Image", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	} // end of load
}// class end