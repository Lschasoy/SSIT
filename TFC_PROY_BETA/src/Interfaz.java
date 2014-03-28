import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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
import java.awt.GridLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import java.io.IOException;
import javax.swing.JTable;


import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Interfaz {
	
	private JFrame miVentana;
	private JPanel Npanel, ImagActPanel, centralPanel, Ipanel;
	private JPanel canalR, canalG, canalB, canalX, canalY;
	
	public  BufferedImage originalImage, finalImage;
		
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	
	private JTable tablaMenuImage;
	
	
	private MyTableModel modelo;
	private Mensajes msgs;
	private Archivos arc;
	
	 
    private Histograma ObjHistograma;
    private int[][] histograma;
    private DibujarGrafica ObjDibujaHisto;
	
	/******************* MAIN *****************************/	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
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
		miVentana.setTitle("TFG - sImage beta v.3.0");
		miVentana.getContentPane().setBackground(Color.LIGHT_GRAY);
		miVentana.setSize(1264, 720);
		
		msgs = new Mensajes();
		modelo = new MyTableModel();
		arc = new Archivos();
				    
		
		miVentana.getContentPane().add(panelSup(), BorderLayout.NORTH);
		miVentana.getContentPane().add(panelCentral(),BorderLayout.CENTER);											
	}
	

	
	
	public JPanel panelMenuImage (){
		
		 JPanel pMenuImage = new JPanel();		
		 pMenuImage.setBounds(10, 215, 235, 190);
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
		    		
		    		//msg = "\n$ > [ImageMenu] : Seleccionado image [" + row + col +"]" ; 
					//panelCMD.setText(msg);
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
		
	//  Panel de la parte central 
		
	    centralPanel = new JPanel();
	    centralPanel.setLayout(null);
		
	    // === Imagen Central ===
		ImagActPanel = new JPanel();						
		ImagActPanel.setBackground(Color.WHITE);
		ImagActPanel.setBorder(UIManager.getBorder("Button.border"));
		
		
		final JPanel pScroll = new JPanel();
		pScroll.setBounds(255, 10, 720, 394);			
		pScroll.setLayout(null);
	    
		final JScrollPane scroll = new JScrollPane(ImagActPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 720, 394);		
		pScroll.add(scroll);				
		
		
		centralPanel.add(pScroll);			
		
		//===  Inicializar Commnad Line ===
		panelCMD = new JTextArea();		
	    centralPanel.add(msgs.initMsg(panelCMD));
	      
		Ipanel = new JPanel();
		initPanel(10, 10, 235, 200, Ipanel, centralPanel);
	    
	    centralPanel.add(panelMenuImage());	    						   	   	    		
		
		/****** Inicializar canales *************/
		canalR = new JPanel();
		initPanel(985, 10, 250, 190, canalR, centralPanel );				
		canalG = new JPanel();
		initPanel(985, 210, 250, 190, canalG, centralPanel);		
		canalB = new JPanel();
		initPanel(985, 415, 250, 190, canalB, centralPanel);		
		canalX =  new JPanel();
		initPanel(390, 415, 285, 190, canalX, centralPanel);		
		canalY = new JPanel();
		initPanel(685, 415, 290, 190, canalY, centralPanel);
		
		//=== Funciones de los canales 
		panelIzq();
			
		
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
	
	public void panelIzq(){
	    
	    GridLayout gl_pCanales = new GridLayout(2,2);
	    gl_pCanales.setHgap(2);
	    JPanel pCanales = new JPanel(gl_pCanales);
	    				
			
		 JButton iCanalR = new JButton("Red");
		 iCanalR.addMouseListener(new MouseAdapter() {
	 	    	@Override
	 	    	public void mouseClicked(MouseEvent arg0) { 	    		
	 	    		PopWindows popUp = new PopWindows();
	 	    	    popUp.gCanalR(originalImage); 	    	     	    	
	 	    	}
	 	 });
		pCanales.add(iCanalR);
		
		JButton iCanalG = new JButton("Green");
		iCanalG.addMouseListener(new MouseAdapter() {
	 	    	@Override
	 	    	public void mouseClicked(MouseEvent arg0) { 	    		
	 	    		PopWindows popUp = new PopWindows();
	 	    	    popUp.gCanalG(originalImage); 	    	     	    	
	 	    	}
	 	});
	    pCanales.add(iCanalG);
		
	    JButton iCanalB = new JButton("Blue");
	    iCanalB.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 	    		
 	    		PopWindows popUp = new PopWindows();
 	    	    popUp.gCanalG(originalImage); 	    	     	    	
 	    	}
  	    });
 	    pCanales.add(iCanalB);
 	    
 	    JButton iCanalX = new JButton("Black"); 	    
	    pCanales.add(iCanalX);
	    
	    JButton iCanalY = new JButton("Gray");
	    iCanalY.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 	    		
 	    		PopWindows popUp = new PopWindows();
 	    	    popUp.gCanalY(originalImage); 	    	     	    	
 	    	}
 	    });
 	    pCanales.add(iCanalY);
	
		Ipanel.add(pCanales);					
		
		JLabel lblCanales = new JLabel("   Canales");
		lblCanales.setEnabled(false);
		pCanales.add(lblCanales);
		
	}	
	
	 
	public JMenuBar panelMenu(){
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JMenu mnArchivos = new JMenu("Archivos");
		mnArchivos.setBounds(0, 0, 20, 10);
		menuBar.add(mnArchivos);
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Carga imagen");
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    				   				  				  				    
				    originalImage = arc.loadFile(tablaMenuImage, modelo);
				    panelCMD.setText(msgs.msgOperacion(0,arc.getImageName(), originalImage.toString()));
				    mostrar(ImagActPanel, originalImage);	
							   				    				    				            				  			  			
			}
		});
		mnArchivos.add(cargarImagen);
		//==================== Save Image ===================================
		JMenuItem salvarImagen = new JMenuItem("Salvar imagen");
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 Archivos arc = new Archivos();
			     arc.saveFile();
			     panelCMD.setText(msgs.msgOperacion(1,arc.getImageName(), originalImage.toString()));				    				    				    			
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
				
				finalImage = Tools.Zoom(originalImage, yScaleFactor, xScaleFactor );	
				panelCMD.setText(msgs.msgOperacion(2,arc.getImageName(), finalImage.toString()));
				mostrar(ImagActPanel, finalImage);
						
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
				  
				  finalImage = Tools.Zoom(originalImage, yScaleFactor, xScaleFactor );
				  panelCMD.setText(msgs.msgOperacion(3,arc.getImageName(), finalImage.toString()));
				  mostrar(ImagActPanel, finalImage);				  			
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
				 panelCMD.setText(msgs.msgOperacion(4,arc.getImageName(), finalImage.toString()));
				
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
				 panelCMD.setText(msgs.msgOperacion(5,arc.getImageName(), finalImage.toString()));				
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
		
		try {
			Graficar (image);
		}catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "No se pudo cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
	    }
		
	    miVentana.setVisible(true);	
	}
	
	private void Graficar ( BufferedImage image){
		
						
		//CREAMOS EL HISTOGRAMAS
        ObjHistograma=new Histograma();
        histograma=ObjHistograma.histograma(image);
        //DIBUJAMOS LOS HISTOGRAMAS
        ObjDibujaHisto=new DibujarGrafica();		
	      
	        for (int i = 0; i < 5; i++) {
	            //extraemos un canal del histograma 
	            int[] histogramaCanal=new int[256];
	            System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
	            //Dibujamos en el panel
	            switch(i){
	                case 0:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalR, Color.red, image);	 
	                   
	                    break;
	                case 1:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalG, Color.green, image);
	                    break;
	                case 2:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalB, Color.blue, image);
	                    break;
	                case 3:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalX, Color.black, image);
	                    break;
	                case 4:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalY, Color.gray, image);
	                    break;
	            }
	        }
	}
//========================================================================================================	
	public void initPanel(int x, int y, int tamX,int tamY, JPanel panel_In, JPanel panel_Out){
		
		panel_In.setBounds(x, y, tamX, tamY);
		panel_In.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panel_In.setBackground(Color.LIGHT_GRAY);
		
		panel_Out.add(panel_In);
	}
}

