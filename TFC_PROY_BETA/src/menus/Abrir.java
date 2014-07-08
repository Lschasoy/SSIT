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
	
}