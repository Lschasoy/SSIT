import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

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
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;


public class Interfaz {

	private String msg = "                 UNIVERSIDAD DE LA LAGUNA                             \n"
			           + "   ESCUELA TECNICA SUPERIOR DE INGENIERIA INFORMATICA                 \n"
			           + "   TRABAJO FIN DE GRADO - GRADO EN INGENIERIA INFORMATICA             \n"
			           + "   TITULO: GUI para la ejecución de algoritmos de segmentación de imágenes  \n"
			           + "   AUTOR : LEONARDO SIVERIO CHASOY                                    \n"
			           + "   Verion beta: 1.0                                                   \n";
	
	
	private JFrame miVentana;
	private JPanel Npanel, ImagActPanel, panelHisto, centralPanel, panelMenuImage;
	private JButton seleccionar, girarIZQ, girarDCH, ZoomPlus, ZoomMinus, recortar;
	public  BufferedImage originalImage, finalImage;
		
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private Histograma histo;
	private JTextArea textArea;


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
		miVentana.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Leo\\git\\TFC_PRO\\TFC_PROY_BETA\\image\\icon.ico"));
		miVentana.setTitle("TFG - sImage beta v.10");
		miVentana.getContentPane().setBackground(Color.LIGHT_GRAY);
		miVentana.setSize(1064, 700);
				    
		
		miVentana.getContentPane().add(panelSup(), BorderLayout.NORTH);
		miVentana.getContentPane().add(panelCentral(),BorderLayout.CENTER);								
				
	}
	public JPanel panelCentral (){
		
		ImagActPanel = new JPanel();			
		ImagActPanel.setBounds(250, 10, 790, 400);
		ImagActPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		ImagActPanel.setBackground(Color.LIGHT_GRAY);
		ImagActPanel.setAutoscrolls(true);
		
		panelCMD = new JTextArea(msg);
		panelCMD.setLineWrap(true);
		panelCMD.setColumns(10);
		panelCMD.setRows(10);
		panelCMD.setBounds(555, 415, 485, 180);
		panelCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panelCMD.setBackground(Color.LIGHT_GRAY);
		
		panelMenuImage = new JPanel();		
		panelMenuImage.setBounds(10, 415, 230, 180);
		panelMenuImage.setBackground(Color.LIGHT_GRAY);
		panelMenuImage.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panelMenuImage.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		panelHisto =  new JPanel();
		panelHisto.setBounds(250, 415, 300, 180);
		panelHisto.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panelHisto.setBackground(Color.LIGHT_GRAY);
		panelHisto.setAutoscrolls(true);
	
		
		centralPanel = new JPanel();
		centralPanel.setLayout(null);
		centralPanel.add(ImagActPanel);					
	    centralPanel.add(panelHisto);
	    centralPanel.add(panelCMD);
	    centralPanel.add(panelMenuImage);
	    
		
		
	
		centralPanel.add(panelIzq());
		
		return centralPanel;
	}
	public JPanel panelSup(){
		
		JPanel Spanel = new JPanel();
		Spanel.setLayout(new BoxLayout(Spanel, BoxLayout.Y_AXIS));
		Spanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		Spanel.setBackground(Color.white);
		Spanel.add(panelMenu());
		
		JSeparator separator = new JSeparator();
		Spanel.add(separator);
		Spanel.add(panelTool());
				
		return Spanel;
	}
	
	public JPanel panelIzq(){
		JPanel Ipanel = new JPanel();
		Ipanel.setBackground(Color.LIGHT_GRAY);
		Ipanel.setBounds(10, 10, 230, 400);
		Ipanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		Ipanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
		textArea = new JTextArea("Algortimos de segmentacion");
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setFont(new Font("Lucida Fax", Font.PLAIN, 14));
		Ipanel.add(textArea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Algoritmo_1", "Algoritmo_2", "Algoritmo_3", "Algoritmo_4"}));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		Ipanel.add(comboBox);
						
		return Ipanel;
		
	}	
	/*************************************************/
	public JMenuBar panelMenu(){

		
	
		JMenuBar menuBar = new JMenuBar();
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JMenu mnArchivos = new JMenu("Archivos");
		mnArchivos.setBounds(0, 0, 20, 1064);
		menuBar.add(mnArchivos);
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Carga imagen");
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    
				    ImagActPanel.removeAll();
				    Archivos arc = new Archivos();
				    originalImage = arc.loadFile();
				    msg = "$ > [load image] salida" + originalImage; 					
				    mostrar(ImagActPanel, originalImage);	
				    
				    histo = new Histograma();
				    finalImage = histo.generarGrafica(originalImage);
				    msg += "$ > [load image] Generando Histograma";
				    mostrar(panelHisto, finalImage);
				    
				    finalImage = ZoomPanel.generarIcono(originalImage);
				    mostrar(panelMenuImage, finalImage);
				    msg += "$ > [load image] Generando Icono";
				    
				    panelCMD.setText(msg);
					
			}
		});
		mnArchivos.add(cargarImagen);
		//==================== Save Image ===================================
		JMenuItem salvarImagen = new JMenuItem("Salvar imagen");
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 msg = "$ > [save image] salida" + originalImage; 
				 panelCMD.setText(msg);   
				    				    				    			
			}
		});
		mnArchivos.add(salvarImagen);	

		return menuBar;
	}
	/*************************************************/
	public JPanel panelTool(){

		Npanel = new JPanel();
		Npanel.setAlignmentY(Component.TOP_ALIGNMENT);
		Npanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		Npanel.setLayout(new BoxLayout(Npanel, BoxLayout.X_AXIS));
		Npanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
		Npanel.setBackground(Color.WHITE);

		seleccionar = new JButton("Seleccionar");		
		seleccionar.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(seleccionar);

		//==================== ZOOM ++ ===================================
		ZoomPlus = new JButton ("ZOOM ++");
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xScaleFactor += 0.1;
				yScaleFactor += 0.1;				  	
				ImagActPanel.removeAll();
				finalImage = ZoomPanel.Zoom(originalImage, yScaleFactor, xScaleFactor );	
				msg = "$ > [ZOOM ++] salida" + finalImage; 
				panelCMD.setText(msg);
				mostrar(ImagActPanel, finalImage);
				
				histo = new Histograma();
			    finalImage = histo.generarGrafica(originalImage);
			    mostrar(panelHisto, finalImage);
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
				  ImagActPanel.removeAll();
				  finalImage = ZoomPanel.Zoom(originalImage, yScaleFactor, xScaleFactor);
				  msg = "$ > [ZOOM --] salida" + finalImage; 
				  panelCMD.setText(msg);
				  mostrar(ImagActPanel, finalImage);
				  
				  histo = new Histograma();
				  finalImage = histo.generarGrafica(originalImage);
				  mostrar(panelHisto, finalImage);
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
				 msg = "$ > [GIRAR IZQ] salida" + finalImage; 
				 panelCMD.setText(msg);
				 ImagActPanel.removeAll();
				 mostrar(ImagActPanel, finalImage);
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
				 msg = "$ > [GIRAR DCH] salida" + finalImage; 
				 panelCMD.setText(msg);
				 ImagActPanel.removeAll();
				 mostrar(ImagActPanel, finalImage);
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

