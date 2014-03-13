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


public class Interfaz {

	private JFrame miVentana;
	private JPanel Npanel, ImagActPanel, ImagFinalPanel, centralPanel;
	private JButton seleccionar, girarIZQ, girarDCH, ZoomPlus, ZoomMinus, recortar;
	private JButton cargar, salvar, salir;
	public  BufferedImage originalImage, finalImage;
	
	private float xScaleFactor = 1, yScaleFactor = 1;

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
		ImagActPanel.setBackground(Color.WHITE);
		ImagActPanel.setAutoscrolls(true);
		
		centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(1,2));

		ImagFinalPanel =  new JPanel();
		ImagFinalPanel.setBackground(Color.BLACK);
		ImagFinalPanel.setAutoscrolls(true);
		
		centralPanel.add(ImagActPanel);					
	    centralPanel.add(ImagFinalPanel); 
		
		miVentana.getContentPane().add(Panel_SUP(), BorderLayout.NORTH);
		miVentana.getContentPane().add(Panel_IZQ(), BorderLayout.SOUTH);
		miVentana.getContentPane().add(centralPanel,BorderLayout.CENTER);
				
	}
	
	/*************************************************/
	public JPanel Panel_SUP(){

		Npanel = new JPanel();
		Npanel.setLayout(new BoxLayout(Npanel, BoxLayout.X_AXIS));
		Npanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 5, 5));
		Npanel.setBackground(Color.LIGHT_GRAY);

		cargar = new JButton("LOAD");
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

		Npanel.add(cargar);	
		Npanel.add(salvar);	

		return Npanel;
	}
	/*************************************************/
	public JPanel Panel_IZQ(){

		Npanel = new JPanel();
		Npanel.setLayout(new BoxLayout(Npanel, BoxLayout.X_AXIS));
		Npanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 25, 25));

		Npanel.setBackground(Color.LIGHT_GRAY);

		seleccionar = new JButton("Seleccionar");
		
		seleccionar.setMaximumSize(seleccionar.getMaximumSize());


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

		girarIZQ = new JButton ("Girar IZQ");
		
		girarIZQ.setMaximumSize(seleccionar.getMaximumSize());

		girarDCH = new JButton ("Girar DCH");
		
		girarDCH.setMaximumSize(seleccionar.getMaximumSize());

		
		recortar = new JButton ("Recortar");
		
		recortar.setMaximumSize(seleccionar.getMaximumSize());

		Npanel.add(seleccionar);
		Npanel.add(ZoomMinus);
		Npanel.add(ZoomPlus);
		Npanel.add(girarDCH);
		Npanel.add(girarIZQ);
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
