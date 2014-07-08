package menus;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import Images.Image;
import main.MainWindow;

public class GuardarComo {
	
	public static void run(){
		Image image = MainWindow.getCurrentImage();
		MainWindow.chooser.setSelectedFile(image.getFileCompleto());
		int returnValue = MainWindow.chooser.showSaveDialog(MainWindow.miVentana);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			image.cambiarFile(MainWindow.chooser.getSelectedFile());
			try {
				ImageIO.write(image.img, "jpg", image.getFileCompleto());
				image.saved = true;
				MainWindow.changeImageTitle(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}