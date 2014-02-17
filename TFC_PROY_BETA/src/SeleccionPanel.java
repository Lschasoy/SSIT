import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class SeleccionPanel extends JPanel {

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private BufferedImage image;
        private Rectangle selection;


        public SeleccionPanel(JPanel myframe) throws AWTException {
        	BufferedImage image = new Robot().createScreenCapture( 
        			   new Rectangle( myframe.getX(), myframe.getY(), 
        			                  myframe.getWidth(), myframe.getHeight() ) );

            MouseAdapter handler = new MouseAdapter() {
            	@Override
                public void mousePressed(MouseEvent e) {
                    selection = new Rectangle(e.getPoint());
                    repaint();
                }
            	@Override
                public void mouseDragged(MouseEvent e) {
                    Point p = e.getPoint();
                    int width = Math.max(selection.x - e.getX(), e.getX() - selection.x);
                    int height = Math.max(selection.y - e.getY(), e.getY() - selection.y);
                    selection.setSize(width, height);
                    repaint();
                }
            };

            addMouseListener(handler);
            addMouseMotionListener(handler);
        }

		@Override
        public Dimension getPreferredSize() {
            return image == null ? super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(image, WIDTH, 0, this);
                if (selection != null) {
                    g2d.setColor(new Color(225, 225, 255, 128));
                    g2d.fill(selection);
                    g2d.setColor(Color.GRAY);
                    g2d.draw(selection);
                }
                g2d.dispose();
            }
        }


    public static Rectangle getVirtualBounds() {

        Rectangle bounds = new Rectangle(0, 0, 0, 0);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice lstGDs[] = ge.getScreenDevices();
        for (GraphicsDevice gd : lstGDs) {
            bounds.add(gd.getDefaultConfiguration().getBounds());
        }

        return bounds;

    }

}