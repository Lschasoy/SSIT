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
	private double angle = 0, degree = 0;
	
	int neww, newh;

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
	  
	  JButton aceptar = new JButton(new AbstractAction("Aceptar") {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			  rot.decreaseRotated();
		  }
	  });
 
      JPanel BotonesPanel = new JPanel();
      BotonesPanel.setLayout(new GridLayout(1,3));
      BotonesPanel.add(plus);
      BotonesPanel.add(minus);
      BotonesPanel.add(aceptar);
      
     
	  this.setLayout(new BorderLayout());
	  this.add(rot, BorderLayout.CENTER);
	  this.add(BotonesPanel, BorderLayout.SOUTH);

  }

 
public class Rotar extends JPanel {

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
    }
}	  