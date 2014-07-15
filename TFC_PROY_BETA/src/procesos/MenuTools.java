package procesos;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class MenuTools extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	
	public static JPanel getMenu(){
		
	JPanel barTools = new JPanel();	
	barTools.setLayout(new GridLayout(9,1));	
	barTools.setBounds(0, 25, 562, 25);
	
  // ==================== Seleccionar ===================================
		JButton select = new JButton ();
		select.setIcon(new ImageIcon(MenuTools.class.getResource("/com/mathworks/toolbox/javabuilder/webfigures/resources/javascript/mathworks/webfigures/images/panMode_Down_Over.png")));
		select.setText("Select");		
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {											  	
				System.out.println("Seleccionar");						 			 
			}
		});				
		barTools.add(select);
	
   //==================== Escalar ===================================
		JButton escalar = new JButton ();
		escalar.setIcon(new ImageIcon(MenuTools.class.getResource("/com/mathworks/toolbox/javabuilder/webfigures/resources/javascript/mathworks/webfigures/images/Minimize_Over.png")));
		escalar.setText("Escalar");	    
		escalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Escalar.run(); 																
			}
		});				
		
		barTools.add(escalar);
	
		//==================== ROTAR =================================== 
		JButton rotar = new JButton ();
		rotar.setIcon(new ImageIcon(MenuTools.class.getResource("/com/mathworks/toolbox/javabuilder/webfigures/resources/javascript/mathworks/webfigures/images/rotateMode_Over.png")));
		rotar.setText("  Rotar ");
		rotar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rotar.run();										
			}
		});					
		barTools.add(rotar);
		
		// ============== Ajuste Brillo y Contraste ===================================
		JButton ajuste = new JButton();
		ajuste.setIcon(new ImageIcon(MenuTools.class.getResource("/icons/16/palette_color.png")));
		ajuste.setText(" Ajustes ");
		ajuste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				BrilloContraste.run();
			}
		});
		barTools.add(ajuste);
							
		//==================== CORTAR =================================== 
		JButton cortar = new JButton ();
		cortar.setIcon(new ImageIcon(MenuTools.class.getResource("/icons/16/editcut.png")));
		cortar.setText(" Cortar");
		cortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Pulsado el boton cortar");			
			}	
		});			
		
		barTools.add(cortar);
						
		//==================== COPIAR =================================== 
		JButton copiar = new JButton ();
		copiar.setIcon(new ImageIcon(MenuTools.class.getResource("/icons/16/editcopy.png")));
		copiar.setText(" Copiar");
		copiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Copiar.run();			
			}	
		});			
		
		barTools.add(copiar);
       								
		// ============== ESPEJO HORIZONTAL  ===================================
		JButton espejoH = new JButton();
		espejoH.setIcon(new ImageIcon(MenuTools.class.getResource("/icons/16/editclear.png")));
		espejoH.setText("E.Horizontal");
		espejoH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				EspejoHorizontal.run();
			}
		});
		barTools.add(espejoH);
		
		// ============== ESPEJO VERTICAL  ===================================
		JButton espejoV = new JButton();
		
		espejoV.setText("Esp.Vertical");
		espejoV.setIcon(new ImageIcon(MenuTools.class.getResource("/icons/16/editclear.png")));
		espejoV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				EspejoVertical.run();
			}
		});
		barTools.add(espejoV);
		
		// ============== LOGARITMO EXPONICIAL  ===================================
		JButton logExp = new JButton();
		logExp.setIcon(new ImageIcon(MenuTools.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		logExp.setText("LogExp img");
		logExp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				LogaritmicoExponencial.run();
			}
		});
		barTools.add(logExp);
		
	   return barTools;		
				
	} // fin de getMenu();
} // fin de la clase
