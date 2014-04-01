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
import java.util.Vector;

import javax.swing.JTable;


import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;




import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;



public class Interfaz {
	
	private JFrame miVentana;
	private JPanel actPanel, centralPanel, Ipanel, Spanel, canalR, canalG, canalB, canalX, canalY;
	
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
						mostrar(actPanel,arc.loadImage(row, col, tablaMenuImage,  modelo));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "[Error] No Image", "Error", JOptionPane.ERROR_MESSAGE);
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
		actPanel = new JPanel();						
		actPanel.setBackground(Color.WHITE);
		actPanel.setBorder(UIManager.getBorder("Button.border"));
		
		
		final JPanel pScroll = new JPanel();
		pScroll.setBounds(255, 10, 720, 394);			
		pScroll.setLayout(null);
	    
		final JScrollPane scroll = new JScrollPane(actPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 720, 394);		
		pScroll.add(scroll);				
		
		centralPanel.add(pScroll);	
	
		
		
		
		//===  Inicializar Commnad Line ===
		panelCMD = new JTextArea();		
	    centralPanel.add(msgs.initMsg(panelCMD));
	    
	    // ======= Menu de Algoritmos =============================
	      
		Ipanel = new JPanel();
		initPanel(10, 20, 235, 180, Ipanel, centralPanel);
		
		JLabel lblAlgortimoDeSegmentacion = new JLabel("Algortimo de segmentacion");
		lblAlgortimoDeSegmentacion.setBounds(10, 6, 180, 14);
		centralPanel.add(lblAlgortimoDeSegmentacion);
	    
	    centralPanel.add(panelMenuImage());	
		
		JLabel lblMenuDeImagenes = new JLabel("Menu de Imagenes");
		lblMenuDeImagenes.setBounds(10, 202, 150, 14);
		centralPanel.add(lblMenuDeImagenes);
		
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

			
		
		return centralPanel;
	}
	

	public JPanel panelSup(){
		
		Spanel = new JPanel();
		Spanel.setLayout(new BoxLayout(Spanel, BoxLayout.X_AXIS));
		Spanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		Spanel.setBackground(Color.white);
		
		JLabel lblMenu = new JLabel(" Menu ");
		Spanel.add(lblMenu);
		//==================== Load Image ===================================
		JButton cargarImagen = new JButton("Load Image");
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    				   				  				  				    
				    originalImage = arc.loadFile(tablaMenuImage, modelo);
				    mostrar(actPanel, originalImage);					    
				    panelCMD.setText(msgs.msgOperacion(0,arc.getImageName(), originalImage));
				    
				  
							   				    				    				            				  			  			
			}
		});
		Spanel.add(cargarImagen);
		
	
		
		//==================== Save Image ===================================
		JButton salvarImagen = new JButton("Save Image");
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 Archivos arc = new Archivos();
			     arc.saveFile();
			     panelCMD.setText(msgs.msgOperacion(1,arc.getImageName(), originalImage));				    				    				    			
			}
		});
		Spanel.add(salvarImagen);

		//==================== ZOOM ++ ===================================
		JButton ZoomPlus = new JButton ("ZOOM ++");
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xScaleFactor += 0.1;
				yScaleFactor += 0.1;				  	
				
				finalImage = Tools.Zoom(originalImage, yScaleFactor, xScaleFactor );	
				panelCMD.setText(msgs.msgOperacion(2,arc.getImageName(), finalImage));
				mostrar(actPanel, finalImage);
						
			}
		});		
		
		JLabel lblTools = new JLabel(" Tools");
		Spanel.add(lblTools);
		
		Spanel.add(ZoomPlus);
		//==================== ZOOM -- =================================== 
		JButton ZoomMinus = new JButton ("ZOOM --");
		ZoomMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  xScaleFactor -= 0.1;
				  yScaleFactor -= 0.1;			
				  
				  finalImage = Tools.Zoom(originalImage, yScaleFactor, xScaleFactor );
				  panelCMD.setText(msgs.msgOperacion(3,arc.getImageName(), finalImage));
				  mostrar(actPanel, finalImage);				  			
			}
		});		
		
		Spanel.add(ZoomMinus);
		//==================== GIRAR IZQ =================================== 
		JButton girarIZQ = new JButton ("Girar IZQ");
		girarIZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree += 45;				 
				 // RotatedPanel rot = new RotatedPanel();			
				 finalImage = Tools.Rotar(originalImage, degree);
				 panelCMD.setText(msgs.msgOperacion(4,arc.getImageName(), finalImage));
				
				 mostrar(actPanel, finalImage);
			}
		});			
		
		Spanel.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		JButton girarDCH = new JButton ("Girar DCH");
		girarDCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree -= 45;				 
				 // RotatedPanel rot = new RotatedPanel();			
				 finalImage = Tools.Rotar(originalImage, degree);
				 panelCMD.setText(msgs.msgOperacion(5,arc.getImageName(), finalImage));				
				 mostrar(actPanel, finalImage);
			}
		});		
		
		Spanel.add(girarDCH);
		
		JLabel lblBack = new JLabel(" Editar");
		Spanel.add(lblBack);
		
		//==================== DESHACER =================================== 
		JButton desHacer = new JButton ("Deshacer");
		desHacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				panelCMD.setText(msgs.msgOperacion(6,arc.getImageName(), originalImage));	
			  
			}
		});			
		
		Spanel.add(desHacer);
        // ==================== DESHACER TODO ===================================
		JButton goTo = new JButton ("Deshacer todo");
		goTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelCMD.setText(msgs.msgOperacion(7,arc.getImageName(), originalImage));
				 
			}
		});
		Spanel.add(goTo);	
		
		// =================== Filtros ====================================
		JLabel lblCanales = new JLabel(" Filtros ");
		Spanel.add(lblCanales);
		
	  //================ BOTON  R ===================================  							
		 JButton iCanalR = new JButton("Red");
		 iCanalR.addMouseListener(new MouseAdapter() {
	 	    	@Override
	 	    	public void mouseClicked(MouseEvent arg0) { 	    		
	 	    		PopWindows popUp = new PopWindows();
	 	    	    popUp.gCanalR(originalImage); 	    	     	    	
	 	    	}
	 	 });
		Spanel.add(iCanalR);
	 //================ BOTON  G ===================================	
		JButton iCanalG = new JButton("Green");
		iCanalG.addMouseListener(new MouseAdapter() {
	 	    	@Override
	 	    	public void mouseClicked(MouseEvent arg0) { 	    		
	 	    		PopWindows popUp = new PopWindows();
	 	    	    popUp.gCanalG(originalImage); 	    	     	    	
	 	    	}
	 	});
		Spanel.add(iCanalG);
	 //================ BOTON B  ===================================	
	    JButton iCanalB = new JButton("Blue");
	    iCanalB.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 	    		
 	    		PopWindows popUp = new PopWindows();
 	    	    popUp.gCanalB(originalImage); 	    	     	    	
 	    	}
  	    });
	    Spanel.add(iCanalB);
 	 //================ BOTON X ===================================
 	    JButton iCanalX = new JButton("Black"); 	
 	    iCanalX.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) { 	    		
	    		PopWindows popUp = new PopWindows();
	    	    popUp.gCanalX(originalImage); 	    	     	    	
	    	}
	    });
 	   Spanel.add(iCanalX);
	    //================ BOTON Y ===================================
	    JButton iCanalY = new JButton("Gray");
	    iCanalY.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 	    		
 	    		PopWindows popUp = new PopWindows();
 	    	    popUp.gCanalY(originalImage); 	    	     	    	
 	    	}
 	    });
	    Spanel.add(iCanalY);
	
		return Spanel;
	}
	
	private void mostrar(JPanel panel, BufferedImage image){
		
		panel.removeAll();
		panel.repaint();
				
		ImageIcon ico = new ImageIcon(image);				
		JLabel label = new JLabel (ico);													
		panel.add(label);		
	    // ***** Generar las graficas **** 	
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
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalR, Color.red, image, actPanel, panelCMD);	                    
	                    break;
	                case 1:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalG, Color.green, image, actPanel, panelCMD);
	                    break;
	                case 2:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalB, Color.blue, image, actPanel, panelCMD);
	                    break;
	                case 3:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalX, Color.black, image, actPanel, panelCMD);
	                    break;
	                case 4:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalY, Color.gray, image, actPanel, panelCMD);
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

