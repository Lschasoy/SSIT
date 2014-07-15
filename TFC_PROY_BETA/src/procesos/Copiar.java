package procesos;

import java.awt.Point;

import images.Image;
import images.Tracer;
import main.MainWindow;

public class Copiar {
	
	public static void run(){
		run("Copia de ");
	}
	
	public static void run(String prefijo) {
		final Image image = MainWindow.getCurrentImage();
		Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), image, prefijo);
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				newImage.img.setRGB(x, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		//MainWindow.insertAndListenImage(newImage);
		Tracer.insert("Copiar", "Copia", newImage.img);
	}

}