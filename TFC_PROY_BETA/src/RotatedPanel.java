import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;



public class RotatedPanel extends JPanel {
	
	private BufferedImage originalImage;
	
	private static final long serialVersionUID = 1L;

	public  RotatedPanel(BufferedImage Imagen){
		
		originalImage = Imagen;
		
		final Rotar rot = new Rotar();
		JButton plus = new JButton(new AbstractAction("Girar +45") { 
			@Override
		  public void actionPerformed(ActionEvent e) {
			  rot.increaseRotated();
		  }
	  });
	  JButton minus = new JButton(new AbstractAction("Girar -45") {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			  rot.decreaseRotated();
		  }
	  });
 
      JPanel BotonesPanel = new JPanel();
      BotonesPanel.setLayout(new GridLayout(1,2));
      BotonesPanel.add(plus);
      BotonesPanel.add(minus);
      
	  this.setLayout(new BorderLayout());
	  this.add(rot, BorderLayout.CENTER);
	  this.add(BotonesPanel, BorderLayout.SOUTH);
	  
  }

 
public class Rotar extends JPanel {
	
	  private double angle = 1, degree;


	  public void increaseRotated() {
		  degree += 45.0;		  
		  angle = Math.toRadians(degree);
		  repaint();
	  }

	  public void decreaseRotated() {
		  degree -= 45.0;		  
		  angle = Math.toRadians(degree);
		  repaint();
	  }

     
    public void paintComponent(Graphics g) {
    	
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = originalImage.getWidth(), h = originalImage.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.translate((neww-w)/2, (newh-h)/2);
        g2.rotate(angle, w/2, h/2);
        g2.clearRect(0, 0, getWidth(), getHeight());
		g2.drawImage(originalImage, 0, 0, neww, newh, null);
        g2.dispose();
    }
 
    // http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html
    // An Image object cannot be converted to a BufferedImage object.
    // The closest equivalent is to create a buffered image and then draw the image on the buffered image.
    // This example defines a method that does this.
 
    // This method returns a buffered image with the contents of an image
    
}
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
 
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
 
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);
 
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
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
 
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
 
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
 
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
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

