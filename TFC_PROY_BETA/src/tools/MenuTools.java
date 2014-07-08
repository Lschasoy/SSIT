package tools;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.MainWindow;
import Images.Tracer;

public class MenuTools extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private static float xScaleFactor = 1;
	private static float yScaleFactor = 1;
	
	public static JPanel getMenu(){
		
	JPanel barTools = new JPanel();	
	barTools.setLayout(new GridLayout(8,1));	
	barTools.setBounds(0, 25, 562, 25);
	
//==================== ZOOM ++ ===================================
		JButton ZoomPlus = new JButton ();
		ZoomPlus.setIcon(new ImageIcon("image\\zIn.png", "Zoom ++"));
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage img = MainWindow.getCurrentImage().img;
				if ( (img.getHeight() < 3000) && (img.getWidth() < 3000)){
					xScaleFactor += 0.1;
					yScaleFactor += 0.1;				
				    Tracer.change("Alejar", Tools.zoom(img, xScaleFactor, yScaleFactor));
				} 																
			}
		});				
		
		barTools.add(ZoomPlus);
		//==================== ZOOM -- =================================== 
		JButton ZoomMinus = new JButton ();
		ZoomMinus.setIcon(new ImageIcon("image\\zOut.png", "Zoom --"));
		ZoomMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage img = MainWindow.getCurrentImage().img;
				if ( (img.getHeight() > 30) && (img.getWidth() > 30)){
					xScaleFactor -= 0.1;
					yScaleFactor -= 0.1;				
				    Tracer.change("Acercar", Tools.zoom(img, xScaleFactor, yScaleFactor));
				}    
			}
		});				
		barTools.add(ZoomMinus);
		//==================== GIRAR IZQ =================================== 
		JButton girarIZQ = new JButton ();
		girarIZQ.setIcon(new ImageIcon("image\\gIn.png", "Girar Izq"));
		girarIZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {						 
				Tracer.change("Girar Izq", Tools.rotarI(MainWindow.getCurrentImage().img));						 
			}
		});					
		barTools.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		JButton girarDCH = new JButton ();
		girarDCH.setIcon(new ImageIcon("image\\gOut.png", "Girar Dch"));
		girarDCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {											  	
				Tracer.change("Girar Dch", Tools.rotarD(MainWindow.getCurrentImage().img));						 			 
			}
		});				
		barTools.add(girarDCH);
				
		//==================== DESHACER =================================== 
		JButton desHacer = new JButton ();
		desHacer.setIcon(new ImageIcon("image\\atras.png", " atras"));
		desHacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Pulsado el boton deshacer");			
			}	
		});			
		
		barTools.add(desHacer);
        // ==================== DESHACER Todo ===================================
		JButton goTo = new JButton ();
		goTo.setIcon(new ImageIcon("image\\atras_all.png", " deshacer todo"));
		goTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Pulsado el boton deshacer todo");												
			}
		});
		barTools.add(goTo);	
		
		
		// ============== Refrescar ===================================
		JButton refrescar = new JButton();
		refrescar.setIcon(new ImageIcon("image\\refrescar.png", " arefrescar"));
		refrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				System.out.println("Falta por hacer");
			}
		});
		barTools.add(refrescar);
		
	   return barTools;		
				
	} // fin de getMenu();
} // fin de la clase
