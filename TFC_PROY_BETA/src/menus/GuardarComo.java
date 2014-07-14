package menus;

import images.Image;
import images.Tracer;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.MainWindow;

public class GuardarComo {
	
	public static void run(){
		Image image = MainWindow.getCurrentImage();		
		int returnValue = MainWindow.chooser.showSaveDialog(MainWindow.miVentana);
		if (returnValue == JFileChooser.APPROVE_OPTION) {			
			try {
				ImageIO.write(image.img, "jpg", MainWindow.chooser.getSelectedFile());
				MainWindow.removeCurrentImage();
				Tracer.insert("Guardar como", MainWindow.chooser.getSelectedFile(), image.img);
			} catch (IOException e) {
				
	    		JOptionPane.showMessageDialog(null, "No se pudo guardar la imagen", "Msg Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}