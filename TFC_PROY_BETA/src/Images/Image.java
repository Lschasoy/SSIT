package Images;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {

	private File file;
	private String prefijo;
	public String name;
	
	public BufferedImage img;
	public ImagePanel panel;
	public boolean saved;
	// Atributos para guardar la información de la imagen
	public String format;
	private long pixelsOut;
	
	
	
    
	public  BufferedImage toBufferedImage() {
        return img;
	}
	
	public Image(File file,  BufferedImage img, boolean saved) {
		
		System.err.print("Error Const Image");
		this.file = file;
		this.img = img;
		//this.format = ImageFilter.getExtension(file);
		this.name = file.getName();
		this.saved = saved;
		panel = new ImagePanel(this);
		this.pixelsOut = 0;
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
		this.pixelsOut = pixOut;
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
