package procesos;

import java.awt.Point;

import images.Image;
import images.Tracer;
import main.MainWindow;

public class EspejoHorizontal {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), image, "Espejo horizontal de ");
		Point src = image.topLeftRoi();
		for (int x = 0; x < newImage.widthRoi(); x++){
			for (int y = 0; y < newImage.heightRoi(); y++){
				newImage.img.setRGB(newImage.widthRoi() - x - 1, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		Tracer.insert("Espejo horizontal", "new", newImage.img);
		//MainWindow.insertAndListenImage(newImage);
	}

}