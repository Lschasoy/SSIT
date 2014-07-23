package images;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.Info;
import main.MainWindow;



@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener{
	
	
	
	public Image image;
	public BufferedImage img, imgOut;
	
	private Dimension imgSize;	
	private int border = 9;	
	private Point begin = null, inicio, fin;
	private Point end = null;
	private Point offset;
	private boolean roiPainted = false, nDrP;
		
	public static Listener listener = Listener.ROI;	
	
		
	//========================================================================
	private enum Listener{ ROI(), PERFIL();}
	//========================================================================
	public static void setRoiListener(){listener = Listener.ROI;}
	//========================================================================	
	public static void setPerfilListener(){listener = Listener.PERFIL;}
	//========================================================================
	
	
	public ImagePanel(Image image) {
				
		this.image = image;
		this.img = image.img;
		imgSize = new Dimension(img.getWidth(null) + border * 2 + 1, img.getHeight(null) + border * 2 + 1);
		setPreferredSize(imgSize);
		setMinimumSize(imgSize);
		setMaximumSize(imgSize);
		setSize(imgSize);
		offset = new Point(0, 0);
		addMouseListener(this);		
		addMouseMotionListener(this);	
		
		 
	}
	//========================================================================
	public void paint(Graphics g) {
		
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		offset.x = (size.width - imgSize.width) / 2;
		offset.y = (size.height - imgSize.height) / 2;
		g.drawImage(img, offset.x + border + 1, offset.y + border + 1, null);
		g.drawRect(offset.x, offset.y, imgSize.width, imgSize.height);
		roiPainted = false;
		if (listener == Listener.ROI && validRoi()) {
			
			roiPainted = true;
			Point topLeft = getTopLeftRoi();
			Point bottomRight = getBottomRightRoi();
			g.setColor(new Color(0xCCCCCC));
			g.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
			g.setColor(new Color(0x99000000, true));
			g.fillRect(topLeft.x + 1, topLeft.y + 1, bottomRight.x - topLeft.x - 1, bottomRight.y - topLeft.y - 1);
		}
		if (listener == Listener.PERFIL && validRecta()) {
			System.out.println("Paint igual listener " + listener.toString());
			roiPainted = true;
			g.setColor(Color.WHITE);
			g.drawLine(begin.x, begin.y, end.x, end.y);
		}
		
	}
	//========================================================================
	public Point getCoordinate(int x, int y) {
		Point coord = new Point(x - offset.x - border - 1, y - offset.y - border - 1);
		if (coord.x < 0 || coord.y < 0 || coord.x >= img.getWidth() || coord.y >= img.getHeight()) {
			return null;
		}
		return coord;
	}
	//========================================================================
	public Point bringCloser(Point p) {
		
		Point coord = new Point(p.x - offset.x - border - 1, p.y - offset.y - border - 1);
		if (coord.x < 0) { p.x -= coord.x; }
		if (coord.y < 0) { p.y -= coord.y; }
		
		if (coord.x >= img.getWidth()) { p.x -= coord.x - img.getWidth() + 1; }
		if (coord.y >= img.getHeight()){ p.y -= coord.y - img.getHeight()+ 1; }
		return p;
	}
	
	//========================================================================
	public Point outFrom(Point p) {
		
		Point coord = new Point(p.x + offset.x + border + 1, p.y + offset.y + border + 1);
				
		if (coord.x >= img.getWidth()) { p.x -= coord.x - img.getWidth() + 1; }
		if (coord.y >= img.getHeight()){ p.y -= coord.y - img.getHeight()+ 1; }
		return p;
	}
	//========================================================================
	public Point getTopLeftRoi() {
		if (begin == null) { return null; }
		
		Point topLeft = new Point(begin);		
		if (end.x < begin.x) { topLeft.x = end.x; }
		if (end.y < begin.y) { topLeft.y = end.y; }
		return topLeft;
	}
	//========================================================================
	public Point getBottomRightRoi() {
		if (end == null) { return null; }
		
		Point bottomRight = new Point(end);
		if (end.x < begin.x) { bottomRight.x = begin.x; }
		if (end.y < begin.y) { bottomRight.y = begin.y; }
		return bottomRight;
	}
	//========================================================================	
	public boolean validRoi() {
		if (listener == Listener.ROI && begin != null && begin.x != end.x && begin.y != end.y) {
			return true;
		}
		return false;
	}
	//========================================================================
	public boolean validRecta() {
		if (listener == Listener.PERFIL && begin != null && !begin.equals(end)) {
			return true;
		}
		return false;
	}
	
	/** ********************************************************************************************
     *       ------------------------ MenuUpPop ---------------------------
     * ********************************************************************************************/
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					System.out.println("Aqui ----> Colocar codigo de corte P");
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					System.out.println("Aqui ----> Colocar codigo de corte R");
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
				
				
			}
		});
	}
	
	public void PopClickListener(MouseEvent e){
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		JMenu menu = new JMenu("Editar");
		
		JMenuItem mnCortar = new JMenuItem("Cortar");
		mnCortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {									
				System.out.println("Editar ----> Cortar");
				
				SubImage subImg = new SubImage();
				subImg.start();
				synchronized(subImg){					
				   
				   try {
					   if ((inicio != null) && (fin != null)){
						  // --> llamada a subImage  
					      subImg.cortar(inicio, fin, MainWindow.getCurrentImage().img);
					      subImg.wait();
					   }
				   } catch (InterruptedException e) {e.printStackTrace();}
				} // end Synchro
				
			}
		});
		menu.add(mnCortar);
	 
		popupMenu.add(menu);
		nDrP = false;
	}
    /** ********************************************************************************************
     *       ------------------------ MOUSE FUNCTION ---------------------------
     * *********************************************************************************************/
		//========================================================================
		@Override
		public void mouseClicked(MouseEvent e) {									
			inicio = getBottomRightRoi();
			fin = getTopLeftRoi();
									
		    PopClickListener(e);
			
		    if (nDrP) repaint();			    		    
			begin = null;
			
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (listener == Listener.ROI)   { mouseDraggedRoi(e); }
			if (listener == Listener.PERFIL){ mouseDraggedPerfil(e);}
		}
		//========================================================================
		@Override
		public void mousePressed(MouseEvent e) {
			if (listener == Listener.ROI)   { mousePressedRoi(e); }
			if (listener == Listener.PERFIL){ mousePressedPerfil(e);}
		}
		//========================================================================
		@Override
		public void mouseReleased(MouseEvent e) {
			if (listener == Listener.ROI)   { mouseReleasedRoi(e);}
			if (listener == Listener.PERFIL){ mouseReleasedPerfil(e);}
		}
	
		//========================================================================
		@Override
		public void mouseEntered(MouseEvent e) {//System.out.println("mouseEntered");			
		}
		//========================================================================
		@Override
		public void mouseExited(MouseEvent e) {
			
			//System.out.println("mouseExited");			
		}
		//========================================================================	
		@Override
		public void mouseMoved(MouseEvent e) {					
            Point po = getCoordinate(e.getX(), e.getY());
            if (po != null)
			    Info.posXY(" Image [x: " + String.valueOf(po.x),", y: " + String.valueOf(po.y)+"]");
		}

		//========================================================================
		public void mousePressedPerfil(MouseEvent e) {
			if (getCoordinate(e.getX(), e.getY()) != null){		
				System.out.println("mousePressedPerfil");
				begin = e.getPoint();
				end = begin;
			}
		}
		//========================================================================		
		public void mouseReleasedPerfil(MouseEvent e) {
			if (!validRecta()) {			
				begin = null;
			}
			/*else{
				Perfil.nuevaRecta(image, getCoordinate(begin.x, begin.y), getCoordinate(end.x, end.y));
			}*/
		}
		//========================================================================	
		public void mouseDraggedPerfil(MouseEvent e) {
			// --> bringCloser (funcion acercar)
			end = bringCloser(e.getPoint());		
			if (validRecta() || roiPainted) {						
				repaint();
			}
		}
		//========================================================================			
		public void mouseDraggedRoi(MouseEvent e) {
			//--> Pintar la zona seleccionada
			end = bringCloser(e.getPoint());
			if (validRoi() || roiPainted) {			
				repaint();
			}
		}
		//========================================================================		
		public void mousePressedRoi(MouseEvent e) {
			//--> Cuando pinto un rectangulo --> Doy las coordenadas	
			begin = bringCloser(e.getPoint());			
			//end = begin;				
		}
		//========================================================================	
		public void mouseReleasedRoi(MouseEvent e) {
			if(!validRoi()) {			
				//begin = null;			
				repaint();
			}
		}	
}//end image class
