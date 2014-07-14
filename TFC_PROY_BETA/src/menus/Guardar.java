package menus;

import images.Image;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import main.MainWindow;

public class Guardar {
	
	// Bugs: solo guardamos en formato jpg -- Buscar soluciones
	public static void run() {		
		Image image = MainWindow.getCurrentImage();
		System.out.println(image.saved);
		if (image.saved == false){				
			GuardarComo.run();
		}	
		else{
			try {
				ImageIO.write(image.img, "jpg", image.getFileCompleto());
				image.saved = true;
				MainWindow.changeImageTitle(image);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No se pudo guardar la imagen", "Msg Error", JOptionPane.ERROR_MESSAGE);
			}
		}	
	}

}