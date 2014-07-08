package tools;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.MainWindow;
import Images.Image;

public class Menu extends JPanel{

	private static Image oImage;
	
	public static JPanel getMenu(){
		
	JPanel barTools = new JPanel();	
	barTools.setLayout(new GridLayout(8,1));	
	barTools.setBounds(0, 25, 562, 25);
	
//==================== ZOOM ++ ===================================
		JButton ZoomPlus = new JButton ();
		ZoomPlus.setIcon(new ImageIcon("image\\zIn.png", "Zoom ++"));
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oImage = MainWindow.getCurrentImage();
				MainWindow.removeCurrentImage();
				//Image newImg = new Image(oImage.getFileCompleto(),Tools.Zoom_out(oImage.img),false, canales);
				//Info.msg(2,newImg.name, newImg.img);
				//mostrar(newImg);																
			}
		});				
		
		barTools.add(ZoomPlus);
		//==================== ZOOM -- =================================== 
		JButton ZoomMinus = new JButton ();
		ZoomMinus.setIcon(new ImageIcon("image\\zOut.png", "Zoom --"));
		ZoomMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//oImage = getCurrentImage();				
				// Image newImg = new Image(getFile(),Tools.Zoom_in(oImage.img),false, canales);
				// Info.msg(3,newImg.name, newImg.img);
				// removeCurrentImage(); 
				// mostrar(newImg);								 			  
			}
		});				
		barTools.add(ZoomMinus);
		//==================== GIRAR IZQ =================================== 
		JButton girarIZQ = new JButton ();
		girarIZQ.setIcon(new ImageIcon("image\\gIn.png", "Girar Izq"));
		girarIZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {						 
				 //oImage = getCurrentImage();				
				 //Image newImg = new Image(getFile(),Tools.rotarI(oImage.img),false, canales);
				// Info.msg(4,newImg.name, newImg.img);
				 //removeCurrentImage(); 
				 //mostrar(newImg);							 
			}
		});					
		barTools.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		JButton girarDCH = new JButton ();
		girarDCH.setIcon(new ImageIcon("image\\gOut.png", "Girar Dch"));
		girarDCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {											  	
				//oImage = getCurrentImage();				
				//Image newImg = new Image(oImage.getFileCompleto(),Tools.rotarD(oImage.img),false, canales);
				//Info.msg(5,newImg.name, newImg.img);
				//removeCurrentImage(); 
				//mostrar(newImg);						 			 
			}
		});				
		barTools.add(girarDCH);
				
		//==================== DESHACER =================================== 
		JButton desHacer = new JButton ();
		desHacer.setIcon(new ImageIcon("image\\atras.png", " atras"));
		desHacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Pulsado el boton deshacer");
			/*	if (getCurrentImage() != null){
					removeCurrentImage();
					Tracer.getLast();			 												  
				}*/
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
