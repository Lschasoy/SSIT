import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JPanel;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.border.BevelBorder;


import java.awt.FlowLayout;
import java.awt.Component;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.io.IOException;
import javax.swing.JTable;
import java.awt.Font;



public class Interfaz {

	private String msg = "                 UNIVERSIDAD DE LA LAGUNA                    \n"
			           + "   ESCUELA TECNICA SUPERIOR DE INGENIERIA INFORMATICA        \n"
			           + "   TRABAJO FIN DE GRADO - GRADO EN INGENIERIA INFORMATICA    \n"
			           + "   TITULO: GUI para operaciones de segmentación de imágenes  \n"
			           + "   AUTOR : LEONARDO SIVERIO CHASOY                           \n"
			           + "   Verion beta: 1.0                                          \n";
	
	
	private JFrame miVentana;
	private JPanel Npanel, ImagActPanel, panelHisto, centralPanel;
	private JPanel canalR, canalG, canalB, canalX, canalY;
	
	public  BufferedImage originalImage, finalImage;
		
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private Histograma histo;
	private JTable tablaMenuImage;
	
	
	private MyTableModel modelo = new MyTableModel();
	private Tools tools = new Tools();
	private Archivos arc = new Archivos();
	
	/******************* MAIN *****************************/	 
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

	/** Create the application. */
	public Interfaz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		miVentana = new JFrame();		
		miVentana.setTitle("TFG - sImage beta v.2.0");
		miVentana.getContentPane().setBackground(Color.LIGHT_GRAY);
		miVentana.setSize(1264, 720);
				    
		
		miVentana.getContentPane().add(panelSup(), BorderLayout.NORTH);
		miVentana.getContentPane().add(panelCentral(),BorderLayout.CENTER);								
				
	}
	
public JPanel panelDch (){
		
		JPanel pDCH = new JPanel();
		
		pDCH.setBounds(985, 10, 254, 595);
		pDCH.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		pDCH.setBackground(Color.LIGHT_GRAY);
		pDCH.setLayout(null);
		
		canalR = new JPanel();
		canalR.setBounds(10, 10, 235, 185);
		canalR.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		canalR.setBackground(Color.LIGHT_GRAY);
		
		canalG = new JPanel();
		canalG.setBounds(10, 205, 235, 185);
		canalG.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		canalG.setBackground(Color.LIGHT_GRAY);
		
		canalB = new JPanel();
		canalB.setBounds(10, 400, 235, 185);
		canalB.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		canalB.setBackground(Color.LIGHT_GRAY);
		pDCH.setLayout(null);
		
		pDCH.add(canalR); pDCH.add(canalG); pDCH.add(canalB);
		
		return pDCH;
		
	}
	
	public JPanel panelMenuImage (){
		
		 JPanel pMenuImage = new JPanel();		
		 pMenuImage.setBounds(10, 415, 235, 190);
		 pMenuImage.setBackground(Color.LIGHT_GRAY);
		 pMenuImage.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		 pMenuImage.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		 
		 // Inicializacion Table Model
		 String [] nombreColumnas = {"Imagen", "Image", "Imagen"};
		 Object [][] datosFila = {{null,null,null},{null,null,null}, {null,null,null}};
		
		 tablaMenuImage = new JTable();
		 tablaMenuImage.setBorder(null);
		 tablaMenuImage.setBackground(Color.LIGHT_GRAY);
		 
		 modelo.setDataVector(datosFila, nombreColumnas);
		 tablaMenuImage.setModel(modelo);
		 		 
	     tablaMenuImage.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());	
		 tablaMenuImage.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
		 tablaMenuImage.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer()); 
		 tablaMenuImage.setRowHeight(60);
		 		 		
		    		    
		 tablaMenuImage.addMouseListener(new java.awt.event.MouseAdapter() {
		 @Override
		     public void mouseClicked(java.awt.event.MouseEvent evt) {
		  		int row = tablaMenuImage.rowAtPoint(evt.getPoint());
		    	int col = tablaMenuImage.columnAtPoint(evt.getPoint());
		    		
		    		msg = "\n$ > [ImageMenu] : Seleccionado image [" + row + col +"]" ; 
					panelCMD.setText(msg);
					try {
						mostrar(ImagActPanel,arc.loadImage(row, col, tablaMenuImage,  modelo));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		    		
		    	}
		    });
		 pMenuImage.add(tablaMenuImage);
		 
		return pMenuImage;
	}
	public JPanel panelCentral (){
		
		ImagActPanel = new JPanel();			
		ImagActPanel.setBounds(255, 10, 720, 400);
		ImagActPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		ImagActPanel.setBackground(Color.LIGHT_GRAY);
		ImagActPanel.setAutoscrolls(true);
		
		panelCMD = new JTextArea(msg);
		panelCMD.setFont(new Font("Consolas", Font.PLAIN, 11));
		panelCMD.setLineWrap(true);
		panelCMD.setColumns(10);
		panelCMD.setRows(10);
		panelCMD.setBounds(560, 415, 415, 190);
		panelCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panelCMD.setBackground(Color.LIGHT_GRAY);
		
		

		panelHisto =  new JPanel();
		panelHisto.setBounds(255, 415, 300, 190);
		panelHisto.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panelHisto.setBackground(Color.LIGHT_GRAY);
		panelHisto.setAutoscrolls(true);
		 JLabel lblHistogramaRgb = new JLabel("Histograma RGB");
		    panelHisto.add(lblHistogramaRgb);
		
		centralPanel = new JPanel();
		centralPanel.setLayout(null);
		
	   // =============== Panel de la parte central ===================================
		centralPanel.add(ImagActPanel);					
	    centralPanel.add(panelHisto);
	    
	    centralPanel.add(panelCMD);
	    centralPanel.add(panelMenuImage());	    						   	   	    
		centralPanel.add(panelIzq());	
		centralPanel.add(panelDch());
		
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
		Ipanel.setBounds(10, 10, 235, 400);
		Ipanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		Ipanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
	    JLabel lblAlgo = new JLabel("Algoritmos de Segmentacion");
		Ipanel.add(lblAlgo);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Algoritmo_1", "Algoritmo_2", "Algoritmo_3", "Algoritmo_4"}));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				msg += "\n$ > [Selector de Algoritmo] : Algoritmo" + (comboBox.getSelectedIndex() + 1); 
				panelCMD.setText(msg);				
				
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
				    				   				  				  
				    
				    originalImage = arc.loadFile(tablaMenuImage, modelo);				   
				    msg = "$ > [load image]: Name" + arc.getImageName();
				    msg += "\n$ > [return]:" + originalImage;				    				    
				    				    				    							    
				    mostrar(ImagActPanel, originalImage);	
				    
				    histo = new Histograma();
				    finalImage = histo.generarGrafica(originalImage);
				    msg += "$ > [load image] Generando Histograma";
				    mostrar(panelHisto, finalImage);
				    				    				            				  			  
					
			}
		});
		mnArchivos.add(cargarImagen);
		//==================== Save Image ===================================
		JMenuItem salvarImagen = new JMenuItem("Salvar imagen");
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 Archivos arc = new Archivos();
			     arc.saveFile();
				
				 msg = "$ > [save image] salida" + originalImage; 
				 panelCMD.setText(msg);   
				    				    				    			
			}
		});
		mnArchivos.add(salvarImagen);
		
	//==================== EXIT ===================================
		JMenuItem itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			    			
			}
		});
		mnArchivos.add(itemExit);

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

		JButton seleccionar = new JButton("Seleccionar");		
		seleccionar.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(seleccionar);

		//==================== ZOOM ++ ===================================
		JButton ZoomPlus = new JButton ("ZOOM ++");
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xScaleFactor += 0.1;
				yScaleFactor += 0.1;				  	
				ImagActPanel.removeAll();
				finalImage = Tools.Zoom(originalImage, yScaleFactor, xScaleFactor );	
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
		JButton ZoomMinus = new JButton ("ZOOM --");
		ZoomMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  xScaleFactor -= 0.1;
				  yScaleFactor -= 0.1;			
				  ImagActPanel.removeAll();
				  finalImage = Tools.Zoom(originalImage, yScaleFactor, xScaleFactor);
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
		JButton girarIZQ = new JButton ("Girar IZQ");
		girarIZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree += 45;				 
				 // RotatedPanel rot = new RotatedPanel();			
				 finalImage = Tools.Rotar(originalImage, degree);
				 msg = "$ > [GIRAR IZQ] salida" + finalImage; 
				 panelCMD.setText(msg);
				 ImagActPanel.removeAll();
				 mostrar(ImagActPanel, finalImage);
			}
		});			
		girarIZQ.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		JButton girarDCH = new JButton ("Girar DCH");
		girarDCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree -= 45;				 
				 // RotatedPanel rot = new RotatedPanel();			
				 finalImage = Tools.Rotar(originalImage, degree);
				 msg = "$ > [GIRAR DCH] salida" + finalImage; 
				 panelCMD.setText(msg);
				 ImagActPanel.removeAll();
				 mostrar(ImagActPanel, finalImage);
			}
		});		
		girarDCH.setMaximumSize(seleccionar.getMaximumSize());
		Npanel.add(girarDCH);
		
		JButton recortar = new JButton ("Recortar");		
		recortar.setMaximumSize(seleccionar.getMaximumSize());		
		Npanel.add(recortar);

		return Npanel;
	}
	
	private void mostrar(JPanel panel, BufferedImage image){
		
		panel.removeAll();
		panel.repaint();
				
		ImageIcon ico = new ImageIcon(image);				
		JLabel label = new JLabel (ico);													
		panel.add(label);							
	    miVentana.setVisible(true);	
	}
}

