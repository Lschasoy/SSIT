import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
 

public class ZoomPanel extends JPanel {

	private BufferedImage originalImage;

	private static final long serialVersionUID = 1L;

	public  ZoomPanel(BufferedImage Imagen){

		originalImage = Imagen;

		final Zoom zoom = new Zoom();
		JButton plus = new JButton(new AbstractAction("Zoom +") { 
			@Override
		  public void actionPerformed(ActionEvent e) {
			  zoom.increaseZoom();
		  }
	  });
	  JButton minus = new JButton(new AbstractAction("Zoom -") {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			  zoom.decreaseZoom();
		  }
	  });
 
      JPanel BotonesPanel = new JPanel();      
      
      BotonesPanel.setLayout(new GridLayout(1,2));
      BotonesPanel.add(plus);
      BotonesPanel.add(minus);
      
      
	  this.setLayout(new BorderLayout());
	  this.add(zoom, BorderLayout.CENTER);
	  this.add(BotonesPanel, BorderLayout.SOUTH);	  
	  
  }
 
  @SuppressWarnings("serial")
private class Zoom extends JPanel {
	  private float xScaleFactor = 1;
	  private float yScaleFactor = 1;
 
	  public void increaseZoom() {
		  xScaleFactor+= 0.1;
		  yScaleFactor+= 0.1;
		  repaint();
	  }
 
	  public void decreaseZoom() {
		  xScaleFactor-= 0.1;
		  yScaleFactor-= 0.1;
		  repaint();
	  }
 
 
	  @Override
	  public void paintComponent(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  int newW = (int) (originalImage.getWidth() * xScaleFactor);
		  int newH = (int) (originalImage.getHeight() * yScaleFactor);
		  g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		  g2.clearRect(0, 0, getWidth(), getHeight());
		  g2.drawImage(originalImage, 0, 0, newW, newH, null);
	  }
 
  }
 
}