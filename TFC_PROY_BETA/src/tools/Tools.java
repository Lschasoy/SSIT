package tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage; 
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.Info;
  
  /* NAME: TRABAJO DE FIN DE GRADO
   * AUTOR: LEONARDO SIVERIO CHASOY
   * DESCRIPCION: CLASE Tools: 
   */
@SuppressWarnings("serial")
public class Tools extends JPanel {
	
	private static float degree = 0;	
	
	
	
	// =================== ROTAR IMAGEN =======================================================================
	   public static BufferedImage rotarD(BufferedImage img){
		   degree -= 90;
		   Info.msgOut("grados", String.valueOf(degree));			   
		   double angle = Math.toRadians(degree);
		   return rotar(img,angle);
	   }
	   
	   public static BufferedImage rotarI(BufferedImage img){
		   degree += 90;
		   Info.msgOut(" Grados", String.valueOf(degree));		   
		   double angle = Math.toRadians(degree);
		   return rotar(img,angle);
	   }
		   	 
	    public static BufferedImage rotar(BufferedImage image, double angle) {
	    	
	        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	        int w = image.getWidth(), h = image.getHeight();
	        
	        int neww = (int)Math.floor(w *cos+h* sin), newh = (int)Math.floor(h* cos+w *sin);
	        
	        GraphicsConfiguration gc = getDefaultConfiguration();
	        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	        Graphics2D g = result.createGraphics();
	        
	        g.translate((neww-w)/2, (newh-h)/2);
	        g.rotate(angle, w/2, h/2);
	        g.drawRenderedImage(image, null);
	        g.dispose();
	        return result;
	    }
	    
	 // =================== ZOOM IMAGEN =======================================================================
	
	  public static BufferedImage zoom(BufferedImage originalImage, float xScaleFactor, float yScaleFactor) {
		  
		  int newW = (int) (originalImage.getWidth() * xScaleFactor);
		  int newH = (int) (originalImage.getHeight() * yScaleFactor);
		
		  GraphicsConfiguration gc = getDefaultConfiguration();
	      BufferedImage result = gc.createCompatibleImage(newW, newH, Transparency.TRANSLUCENT);
	      Graphics2D g2 = result.createGraphics();
	        		 
		  g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		  g2.clearRect(0, 0, newW, newH);
		  g2.drawImage(originalImage, 0, 0, newW, newH, null);		  
		  g2.dispose();
		return result;		
	  }	  	 
	  
	  
// =================== Default Configuracion  ==================================================================
	  
	  public static GraphicsConfiguration getDefaultConfiguration() {
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice gd = ge.getDefaultScreenDevice();
	        return gd.getDefaultConfiguration();
	    }
	 
	    // http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html
	    // An Image object cannot be converted to a BufferedImage object.
	    // The closest equivalent is to create a buffered image and then draw the image on the buffered image.
	    // This example defines a method that does this.
	 
	    // This method returns a buffered image with the contents of an image
	    public static BufferedImage toBufferedImage(Image img) {
	        if (img instanceof BufferedImage) {
	            return (BufferedImage)img;
	        }
	 
	        // This code ensures that all the pixels in the image are loaded
	        img = new ImageIcon(img).getImage();
	 
	        // Determine if the image has transparent pixels; for this method's
	        // implementation, see e661 Determining If an Image Has Transparent Pixels
	        boolean hasAlpha = hasAlpha(img);
	 
	        // Create a buffered image with a format that's compatible with the screen
	        BufferedImage bimage = null;
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        try {
	            // Determine the type of transparency of the new buffered image
	            int transparency = Transparency.OPAQUE;
	            if (hasAlpha) {
	                transparency = Transparency.BITMASK;
	            }
	 
	            // Create the buffered image
	            GraphicsDevice gs = ge.getDefaultScreenDevice();
	            GraphicsConfiguration gc = gs.getDefaultConfiguration();
	            bimage = gc.createCompatibleImage(
	                img.getWidth(null), img.getHeight(null), transparency);
	        } catch (HeadlessException e) {
	            // The system does not have a screen
	        }
	 
	        if (bimage == null) {
	            // Create a buffered image using the default color model
	            int type = BufferedImage.TYPE_INT_RGB;
	            if (hasAlpha) {
	                type = BufferedImage.TYPE_INT_ARGB;
	            }
	            bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), type);
	        }
	 
	        // Copy image to buffered image
	        Graphics g = bimage.createGraphics();
	 
	        // Paint the image onto the buffered image
	        g.drawImage(img, 0, 0, null);
	        g.dispose();
	 
	        return bimage;
	    }
	 
	    // http://www.exampledepot.com/egs/java.awt.image/HasAlpha.html
	    // This method returns true if the specified image has transparent pixels
	    public static boolean hasAlpha(Image image) {
	        // If buffered image, the color model is readily available
	        if (image instanceof BufferedImage) {
	            BufferedImage bimage = (BufferedImage)image;
	            return bimage.getColorModel().hasAlpha();
	        }
	 
	        // Use a pixel grabber to retrieve the image's color model;
	        // grabbing a single pixel is usually sufficient
	         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
	        try {
	            pg.grabPixels();
	        } catch (InterruptedException e) {
	        }
	 
	        // Get the image's color model
	        ColorModel cm = pg.getColorModel();
	        return cm.hasAlpha();
	    }	
 
}