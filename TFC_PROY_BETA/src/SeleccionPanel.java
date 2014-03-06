import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class SeleccionPanel extends JPanel implements MouseMotionListener, MouseListener{
	
    
	private static final long serialVersionUID = 1L;
		Rectangle selection;
        Point anchor;
        public SeleccionPanel(){   
        	System.out.print("SeleccionPanel: "+this);
            
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selection!=null){
                Graphics2D g2d = (Graphics2D)g;
                g2d.draw(selection);
            }
        }
        public void mousePressed(MouseEvent e) {
            anchor = e.getPoint();
            selection = new Rectangle(anchor);
        }
        public void mouseDragged(MouseEvent e) {
            selection.setBounds( (int)Math.min(anchor.x,e.getX()), (int)Math.min(anchor.y,e.getY()),
                    (int)Math.abs(e.getX()-anchor.x), (int)Math.abs(e.getY()-anchor.y));
            repaint();
        }
        public void mouseReleased(MouseEvent e) {
            selection = null;
            repaint();
        }
        // unused
        public void mouseMoved(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
  
}