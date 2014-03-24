import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;


public class Histograma{
	
    private int SIZE = 256;
    // Red, Green, Blue   
    private int NUMBER_OF_COLOURS = 3;

    public final int RED = 0;
    public final int GREEN = 1;
    public final int BLUE = 2;

    private int[][] colourBins;
 
    private int maxY;

 // ===============================================================
    public Histograma() {
        colourBins = new int[NUMBER_OF_COLOURS][];

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            colourBins[i] = new int[SIZE];
        }

    }
 // ===============================================================
    public BufferedImage generarGrafica(Image img){
    	
    	BufferedImage bi = toBufferedImage(img);
         
         // Reset all the bins
        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
           for (int j = 0; j < SIZE; j++) {
                colourBins[i][j] = 0;
           }
        }

        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color c = new Color(bi.getRGB(x, y));

                colourBins[RED][c.getRed()]++;
                colourBins[GREEN][c.getGreen()]++;
                colourBins[BLUE][c.getBlue()]++;
            }
        }

        maxY = 0;

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (maxY < colourBins[i][j]) {
                    maxY = colourBins[i][j];
                }
            }
        }
        return grafica (bi);
    }

 // ===============================================================
    public  BufferedImage grafica(BufferedImage image) {
       
            int newW = 280, newH = 150;
    	    GraphicsConfiguration gc = getDefaultConfiguration();
            BufferedImage result = gc.createCompatibleImage(newW, newH, Transparency.TRANSLUCENT);
            Graphics2D g2 = result.createGraphics();
            
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(5, 15, newW, newH);
            g2.setStroke(new BasicStroke(2));
            int xInterval = (int) ((double)newW / ((double)SIZE+1));

            g2.setColor(Color.black);
         
            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {

                // Set the graph
                if (i == RED) {
                    g2.setColor(Color.red);
                } else if (i == GREEN) {
                    g2.setColor(Color.GREEN);
                } else if (i == BLUE) {
                    g2.setColor(Color.blue);
                }

                // draw the graph for the spesific colour.
                for (int j = 0; j < SIZE - 1 ; j++) {
                    int value = (int) (((double)colourBins[i][j] / (double)maxY) * newH);
                    int value2 = (int) (((double)colourBins[i][j+1] / (double)maxY) * newH);
                    g2.drawLine(j * xInterval, newH - value, (j+1)*xInterval, newH - value2);
                }
            }       
  			  
  		  g2.dispose();
         return result;   
    }  
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