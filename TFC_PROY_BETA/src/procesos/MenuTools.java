package procesos;

import java.awt.Color;
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
	

	
   //==================== Escalar ===================================
		JButton escalar = new JButton ();
		escalar.setIcon(new ImageIcon(MenuTools.class.getResource("/escalar.png")));
		escalar.setBackground(Color.WHITE);
		escalar.setText("Escalar");	    
		escalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Escalar.run(); 																
			}
		});				
		
		barTools.add(escalar);
	
		//==================== ROTAR =================================== 
		JButton rotar = new JButton ();
		rotar.setIcon(new ImageIcon(MenuTools.class.getResource("/rotar.png")));
		rotar.setText("  Rotar ");
		rotar.setBackground(Color.WHITE);
		rotar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rotar.run();										
			}
		});					
		barTools.add(rotar);
		
		// ============== Ajuste Brillo y Contraste ===================================
		JButton ajuste = new JButton();
		ajuste.setIcon(new ImageIcon(MenuTools.class.getResource("/brillo.png")));
		ajuste.setText(" Ajustes ");
		ajuste.setBackground(Color.WHITE);
		ajuste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				BrilloContraste.run();
			}
		});
		barTools.add(ajuste);
							
		//==================== CORTAR =================================== 
		JButton cortar = new JButton ();
		cortar.setIcon(new ImageIcon(MenuTools.class.getResource("/cortar.png")));
		cortar.setText(" Cortar ");
		cortar.setBackground(Color.WHITE);
		cortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Pulsado el boton cortar");			
			}	
		});			
		
		barTools.add(cortar);	
		// ==================== MUESTREO ===================================
		JButton muestra = new JButton ();
		muestra.setIcon(new ImageIcon(MenuTools.class.getResource("/muestra.png")));
		muestra.setText("Muestrear");	
		muestra.setBackground(Color.WHITE);
		muestra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {											  	
				Muestrear.run();					 			 
			}
		});				
		barTools.add(muestra);
						
		//==================== Generar Ruido =================================== 
		JButton ruido = new JButton ();
		ruido.setIcon(new ImageIcon(MenuTools.class.getResource("/copy.png")));
		ruido.setText("Apli. Ruido");
		ruido.setBackground(Color.WHITE);
		ruido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ruido.run();		
			}	
		});			
		
		barTools.add(ruido);
		
       								
		// ============== ESPEJO HORIZONTAL  ===================================
		JButton espejoH = new JButton();
		espejoH.setIcon(new ImageIcon(MenuTools.class.getResource("/esp_h.png")));
		espejoH.setText("E.Horizontal");
		espejoH.setBackground(Color.WHITE);
		espejoH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				EspejoHorizontal.run();
			}
		});
		barTools.add(espejoH);
		
		// ============== ESPEJO VERTICAL  ===================================
		JButton espejoV = new JButton();
		
		espejoV.setText("Esp.Vertical");
		espejoV.setIcon(new ImageIcon(MenuTools.class.getResource("/esp_v.png")));
		espejoV.setBackground(Color.WHITE);
		espejoV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				EspejoVertical.run();
			}
		});
		barTools.add(espejoV);
		
		// ============== LOGARITMO EXPONICIAL  ===================================
		JButton logExp = new JButton();
		logExp.setIcon(new ImageIcon(MenuTools.class.getResource("/histo.png")));
		logExp.setText("LogExp img");
		logExp.setBackground(Color.WHITE);
		logExp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				LogaritmicoExponencial.run();
			}
		});
		barTools.add(logExp);
		
	   return barTools;		
				
	} // fin de getMenu();
} // fin de la clase
