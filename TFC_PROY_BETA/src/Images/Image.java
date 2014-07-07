package Images;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import procesos.DibujarGrafica;

public class Image {

	private File file;
	private String prefijo;
	public String name;
	
	public BufferedImage img;
	public ImagePanel panel;
	public boolean saved;
	
	public String format;        // Atributos para guardar la información de la imagen
	private static DibujarGrafica dg;
	
    
	
	
	public Image(File file,  BufferedImage img, boolean saved, JPanel [] canales) {
		
		//System.out.println("$$$ Genenado la imagen: "+ file.getAbsolutePath());
		
		this.file = file;
		this.img = img;
		//this.format = ImageFilter.getExtension(file);
		if (saved)
			this.name = file.getName();
		this.saved = saved;
		panel = new ImagePanel(this);
		dg = new DibujarGrafica();   //Grafica asociada a cada imagen
		try {
			dg.Graficar (img, panel, canales);
		}catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Dibujar Histograma", "[constructor image]", JOptionPane.ERROR_MESSAGE);
	    }
		
		
	}
	// myFuncion: Repintar los canales
	public  void canales(JPanel [] canales){
		System.out.println("Pintando los canales de " + name);
		try {
			dg.Graficar (img, panel, canales);
		}catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Dibujar Histograma", "[constructor image]", JOptionPane.ERROR_MESSAGE);
	    }  		
	}
   		
    public boolean isIgual (Image img2){
    	System.out.println(this.name +"  " +img2.name);
    	if ((this != null) &&(this.name.equals(img2.name)) && (this.saved == img2.saved)) 
    			return true;
    	return false;
    }
    
    
	public File getFileCompleto(){
		return new File(file.getParent(), prefijo + file.getName());
	}
	
	public File getFileOriginal(){
		return file;
	}
	
	public void cambiarFile(File file) {
		if (file.getAbsolutePath().compareTo(getFileCompleto().getAbsolutePath()) == 0){
			return;
		}
		this.file = file;
		this.prefijo = "";
		this.format = ImageFilter.getExtension(file);
	}

	public Point topLeftRoi() {
		if (!panel.validRoi()) {
			return new Point(0, 0);
		} else {
			Point topLeft = panel.getTopLeftRoi();
			return panel.getCoordinate(topLeft.x, topLeft.y);
		}
	}

	public boolean validRoi(){
		return panel.validRoi();
	}
	
	public int widthRoi() {
		if (!panel.validRoi()) {
			return img.getWidth();
		} else {
			return panel.getBottomRightRoi().x - panel.getTopLeftRoi().x + 1;
		}
	}

	public int heightRoi() {
		if (!panel.validRoi()) {
			return img.getHeight();
		} else {
			return panel.getBottomRightRoi().y - panel.getTopLeftRoi().y + 1;
		}
	}
	
	public void setPixelsOut(long pixOut) {
	}
	
	/*public static Image crearImagen(int width, int height, File filename) {
		return new Image(filename, new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB), false);
	}*/
	

	public static String getString(int value) {
		return "R=" + red(value) + ", G=" + green(value) + ", B=" + blue(value);
	}

	public static int red(int value) {
		return ((0x0FF0000 & value) >> 16);
	}

	public static int green(int value) {
		return ((0x00FF00 & value) >> 8);
	}

	public static int blue(int value) {
		return (0x0FF & value);
	}

	public static double grey(int value) { // Modelo NTSC
		return (double)0.299 * red(value) + (double)0.587 * green(value) + (double)0.114 * blue(value);
	}

	public static int rgb(int r, int g, int b) {
		return (((0x0FF & r) << 16) | ((0x0FF & g) << 8) | (0x0FF & b));
	}

	public static int rgb(int grey) {
		return (((0x0FF & grey) << 16) | ((0x0FF & grey) << 8) | (0x0FF & grey));
	}
	
	public static int[] rgb2array(int value, int[] array){
		array[0] = red(value);
		array[1] = green(value);
		array[2] = blue(value);
		return array;
	}
	
	public static int array2rgb(int[] values){
		return rgb(values[0], values[1], values[2]);
	}
	
	public static int array2rgb(float[] values){
		return rgb(Math.round(values[0]), Math.round(values[1]), Math.round(values[2]));
	}

}
