import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Component;


public class Interfaz {

	private JFrame miVentana;
	private JPanel Npanel, ImagActPanel, ImagFinalPanel, centralPanel, panelSup;
	private JButton seleccionar, girarIZQ, girarDCH, ZoomPlus, ZoomMinus, recortar;
	private JButton cargar, salvar, salir;
	public  BufferedImage originalImage, finalImage;
	
	private float xScaleFactor = 1, yScaleFactor = 1, degree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.miVentana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interfaz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		miVentana = new JFrame();
		miVentana.getContentPane().setBackground(Color.LIGHT_GRAY);
		miVentana.setSize(1024, 480);
		
		ImagActPanel = new JPanel();			
		ImagActPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		ImagActPanel.setBackground(Color.LIGHT_GRAY);
		ImagActPanel.setAutoscrolls(true);
		
		centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(1,2));

		ImagFinalPanel =  new JPanel();
		ImagFinalPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), null, null, null), null));
		ImagFinalPanel.setBackground(Color.LIGHT_GRAY);
		ImagFinalPanel.setAutoscrolls(true);
		
		centralPanel.add(ImagActPanel);					
	    centralPanel.add(ImagFinalPanel); 
	    
	    panelSup = new JPanel();
		panelSup.setLayout(new BoxLayout(panelSup, BoxLayout.Y_AXIS));
		panelSup.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panelSup.setBackground(Color.white);
		panelSup.add(panelMenu());
		panelSup.add(panelTool());
		
	    
		
		miVentana.getContentPane().add(panelSup, BorderLayout.NORTH);		
		miVentana.getContentPane().add(centralPanel,BorderLayout.CENTER);
				
	}
	
	/*************************************************/
	public JPanel panelMenu(){

		JPanel Mpanel = new JPanel();
		Mpanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		Mpanel.setLayout(new BoxLayout(Mpanel, BoxLayout.X_AXIS));
		Mpanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 5, 5));
		Mpanel.setBackground(Color.LIGHT_GRAY);

		cargar = new JButton("LOAD");
		cargar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		cargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ImagActPanel.removeAll();
				    Archivos arc = new Archivos();
				    originalImage = arc.loadFile();
				    mostrar(ImagActPanel, originalImage);				    				    
					
			}
		});
	    
		cargar.setMaximumSize(cargar.getMaximumSize());


		salvar = new JButton("SAVE");		
		
		salvar.setMaximumSize(salvar.getMaximumSize());

		Mpanel.add(cargar);	
		Mpanel.add(salvar);	

		return Mpanel;
	}
	/*************************************************/
	public JPanel panelTool(){

		Npanel = new JPanel();
		Npanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		Npanel.setLayout(new BoxLayout(Npanel, BoxLayout.X_AXIS));
		Npanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 25, 25));
		Npanel.setBackground(Color.LIGHT_GRAY);

		seleccionar = new JButton("Seleccionar");		
		seleccionar.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(seleccionar);

		//==================== ZOOM ++ ===================================
		ZoomPlus = new JButton ("ZOOM ++");
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xScaleFactor += 0.1;
				yScaleFactor += 0.1;				  	
				ImagFinalPanel.removeAll();
				finalImage = ZoomPanel.Zoom(originalImage, yScaleFactor, xScaleFactor );
				mostrar(ImagFinalPanel, finalImage);
			}
		});		
		ZoomPlus.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(ZoomPlus);
		//==================== ZOOM -- =================================== 
		ZoomMinus = new JButton ("ZOOM --");
		ZoomMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  xScaleFactor -= 0.1;
				  yScaleFactor -= 0.1;			
				  ImagFinalPanel.removeAll();
				  finalImage = ZoomPanel.Zoom(originalImage, yScaleFactor, xScaleFactor);
				  mostrar(ImagFinalPanel, finalImage);
			}
		});		
		ZoomMinus.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(ZoomMinus);
		//==================== GIRAR IZQ =================================== 
		girarIZQ = new JButton ("Girar IZQ");
		girarIZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree += 45;				 
				 // RotatedPanel rot = new RotatedPanel();			
				 finalImage = RotatedPanel.Rotar(originalImage, degree);
				 ImagFinalPanel.removeAll();
				 mostrar(ImagFinalPanel, finalImage);
			}
		});			
		girarIZQ.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		girarDCH = new JButton ("Girar DCH");
		girarDCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree -= 45;				 
				 // RotatedPanel rot = new RotatedPanel();			
				 finalImage = RotatedPanel.Rotar(originalImage, degree);
				 ImagFinalPanel.removeAll();
				 mostrar(ImagFinalPanel, finalImage);
			}
		});		
		girarDCH.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(girarDCH);
		
		recortar = new JButton ("Recortar");		
		recortar.setMaximumSize(seleccionar.getMaximumSize());		
		Npanel.add(recortar);

		return Npanel;
	}
	
	private void mostrar(JPanel panel, BufferedImage image){
		
		ImageIcon ico = new ImageIcon(image);				
		JLabel label = new JLabel (ico);													
		panel.add(label);							
	    miVentana.setVisible(true);	
	}
	
}

