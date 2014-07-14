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
       				
		// ============== Brillo  ===================================
		JButton brillo = new JButton();
		brillo.setIcon(new ImageIcon(MenuTools.class.getResource("/icons/16/idea.png")));
		brillo.setText("Opacar");
		brillo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				System.out.println("Falta por hacer");
			}
		});
		barTools.add(brillo);
		
		
	   return barTools;		
				
	} // fin de getMenu();
} // fin de la clase
