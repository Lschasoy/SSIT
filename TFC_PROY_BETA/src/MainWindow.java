import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.border.BevelBorder;

import java.io.File;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.xnap.commons.gui.CloseableTabbedPane;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;






public class MainWindow {
	
	private JFrame miVentana;

	private JPanel infoShow, canalR, canalG, canalB,  canalY;
	  
	public static CloseableTabbedPane jTP;
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private JTable tablaMenuImage;
	private JLabel jlCanal, jlCmd, jlPath;
	
	private MyTableModel modelo;
	private static Mensajes msgs;
	private Archivos arc;
	 
    private Histograma ObjHistograma;
    private int[][] histograma;
    private DibujarGrafica ObjDibujaHisto;
    private Canales jdCanales;
 
        
    private Image oImage;
	/******************* MAIN *****************************/	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainWindow window = new MainWindow();
					window.miVentana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.gc(); // -> Elimina los objetos creados,para que no queden sombies
	}

	/** Create the application. */
	public MainWindow() {
		
		msgs = new Mensajes();
		modelo = new MyTableModel();
		arc = new Archivos();
		jdCanales = new Canales();
		
		jTP = new CloseableTabbedPane();
		jTP.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		jTP.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jTP.setBackground(new Color(135, 206, 235));
		
		
		ImagePanel.setRoiListener(); // --> Importar ImagenPanel 
		
	// ===============> Inicializar wind <=============================	
		miVentana = new JFrame();		
		miVentana.setTitle("TFG - sImage beta v.4.0");
		miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
		miVentana.getContentPane().setLayout(new GridLayout(1,2));			
		miVentana.getContentPane().add(panelCentral());		
		miVentana.getContentPane().add(jTP);		
		miVentana.setSize(1140, 680); //->Notocar
		
	// ================> Inicializar ventanas <=========================			
		
	}
	
	public JPanel panelMenuImage (){
		
		 JPanel pMenuImage = new JPanel();				 
		 FlowLayout flowLayout = (FlowLayout) pMenuImage.getLayout();
		 flowLayout.setAlignment(FlowLayout.LEFT);
		 pMenuImage.setBounds(10, 320, 520, 69);
		 				 		 
		 // Inicializacion Table Model
		 String [] nombreColumnas = {"image", "image", "image","image", "image", "image","image"};
		 Object [][] datosFila = {{null,null,null, null,null,null,null}};
		
		 tablaMenuImage = new JTable();		 
		 
		 modelo.setDataVector(datosFila, nombreColumnas);
		 tablaMenuImage.setModel(modelo);
		 tablaMenuImage.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.black, null, null, null));
		 		 
	     for (int i = 0; i < 7; i++)
	    	 tablaMenuImage.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());			  
		 tablaMenuImage.setRowHeight(60);
		 		 		
		    		    
		 tablaMenuImage.addMouseListener(new java.awt.event.MouseAdapter() {
		 @Override
		     public void mouseClicked(java.awt.event.MouseEvent evt) {
		  		int row = tablaMenuImage.rowAtPoint(evt.getPoint());
		    	int col = tablaMenuImage.columnAtPoint(evt.getPoint());
		    				    		
					try {												   	
						   File pathFile = arc.loadImage(row, col, tablaMenuImage,  modelo);
						   Image newImg = new Image(pathFile,ImageIO.read(pathFile),true);
						   panelCMD.setText(msgs.msgOperacion(0,arc.getImageName(), newImg.img));
						   removeCurrentImage(); 
						   mostrar(newImg);	
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "[Error] No Image", "Error", JOptionPane.ERROR_MESSAGE);
					}							    		
		    	}
		    });
		 pMenuImage.add(tablaMenuImage);
		 
		return pMenuImage;
	}
 				
	public JPanel panelCentral (){
		
		
	    //====> Panel de la parte central <=============================== 			    	
	    infoShow = new JPanel();
	    infoShow.setLayout(null);
	
	    infoShow.add(panelSup()); 
	    
		/****** Inicializar canales *************/
		canalR = new JPanel();
		initPanel(275, 30, 270, 145, canalR, infoShow);				
		canalG = new JPanel();
		initPanel(275, 175, 270, 145, canalG, infoShow);		
		canalB = new JPanel();
		initPanel(5, 30, 270, 145, canalB, infoShow);						
		canalY = new JPanel();
		initPanel(5, 175, 270, 145, canalY, infoShow);
		
		
	    infoShow.add(panelMenuImage());
	    
	    //--> Label's 
	    
	    jlCmd = new JLabel("Commad: ");
	    jlCmd.setBounds(20, 390, 250, 15); // Size Y =  680
	    infoShow.add(jlCmd);
	    
	    jlCanal = new JLabel("Espacio de Color: ");
	    jlCanal.setBounds(270, 390, 250, 15); // Size Y =  680
	    infoShow.add(jlCanal);
	    
	    jlPath = new JLabel("Path image: ");
	    jlPath.setBounds(20, 405, 300, 15); // Size Y =  680
	    infoShow.add(jlPath);
	    
	    //===  Inicializar Commnad Line ===
		panelCMD = new JTextArea();				
		JScrollPane scroll = new JScrollPane(msgs.initMsg(panelCMD));
		scroll.setBounds(5, 455, 540, 190); // Size Y =  680
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
	    infoShow.add(scroll);
	    	    	    	     	    	    		 	    	    				
		return infoShow;
	}
	

	public JMenuBar panelSup(){
						
		JMenuBar barSup = new JMenuBar ();				
		barSup.setBounds(0, 0, 545, 25);
		
		
		JMenu menuFile = new JMenu(" File ");	
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Load Image");
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								   				   				   				  
					try {
						File pathFile = Archivos.loadFile(tablaMenuImage, modelo);
						Image img = new Image(pathFile,ImageIO.read(pathFile),true);
						jlCmd.setText(jlCmd.getText() + "Load Image");
						jlPath.setText(jlPath.getText() + pathFile.getAbsolutePath());
						panelCMD.setText(msgs.msgOperacion(0,img.toString(), img.img));						
						mostrar(img);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Erro al cargar", "Load Error", JOptionPane.ERROR_MESSAGE);	
					
					}				   
			}
		});
		menuFile.add(cargarImagen);
					
		//==================== Save Image ===================================
		JMenuItem salvarImagen = new JMenuItem("Save Image");
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 Archivos arc = new Archivos();
			     arc.saveFile();
			  //   panelCMD.append(msgs.msgOperacion(1,arc.getImageName(), oImage.img));				    				    				    			
			}
		});
		menuFile.add(salvarImagen);
		
		//==================== EXIT ===================================
		JMenuItem exitApp = new JMenuItem("Exit");
		exitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);										    				    				    		
			}
		});
		menuFile.add(exitApp);

		barSup.add(menuFile); // -> Fin menuFile
		
		JMenu menuTool = new JMenu(" Tools ");
		//==================== ZOOM ++ ===================================
		JMenuItem ZoomPlus = new JMenuItem ("ZOOM ++");
		ZoomPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xScaleFactor += 0.1;
				yScaleFactor += 0.1;				  	
				oImage = getCurrentImage();
				Image img = new Image(oImage.getFileCompleto(),Tools.Zoom(oImage.toBufferedImage(), xScaleFactor,yScaleFactor),false);			   
				panelCMD.append(msgs.msgOperacion(4,arc.getImageName(), img.toBufferedImage()));
				removeCurrentImage(); 
				mostrar(img);				
			}
		});		
		
		
		menuTool.add(ZoomPlus);
		//==================== ZOOM -- =================================== 
		JMenuItem ZoomMinus = new JMenuItem ("ZOOM --");
		ZoomMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xScaleFactor -= 0.1; yScaleFactor -= 0.1;				  	
				oImage = getCurrentImage();
				Image img = new Image(oImage.getFileCompleto(),Tools.Zoom(oImage.toBufferedImage(), xScaleFactor,yScaleFactor),false);			   
				panelCMD.append(msgs.msgOperacion(4,arc.getImageName(), img.toBufferedImage()));
				removeCurrentImage(); 
				mostrar(img);		 			  		
			}
		});		
		
		menuTool.add(ZoomMinus);
		//==================== GIRAR IZQ =================================== 
		JMenuItem girarIZQ = new JMenuItem ("Girar IZQ");
		girarIZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 degree += 30;								  	
				 oImage = getCurrentImage();				
				 Image img = new Image(oImage.getFileCompleto(),Tools.Rotar(oImage.toBufferedImage(), degree),false);
				 panelCMD.append(msgs.msgOperacion(4,arc.getImageName(), img.toBufferedImage()));
				 removeCurrentImage(); 
				 mostrar(img);
						 
			}
		});			
		
		menuTool.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		JMenuItem girarDCH = new JMenuItem ("Girar DCH");
		girarDCH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				degree -= 30;								  	
				oImage = getCurrentImage();				
				Image img = new Image(oImage.getFileCompleto(),Tools.Rotar(oImage.toBufferedImage(), degree),false);
				panelCMD.append(msgs.msgOperacion(4,arc.getImageName(), img.toBufferedImage()));
				removeCurrentImage(); 
				mostrar(img);						 			 
			}
		});		
		
		menuTool.add(girarDCH);
		barSup.add(menuTool); // -> fin menuTool
		
		
		// =================== Espacio de Colores ====================================		
		JMenu menuColor = new JMenu("Space");
		barSup.add(menuColor);
		
		JMenuItem rgbColor = new JMenuItem("RGB");
		rgbColor.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent arg0) {
				jlCanal.setText(jlCanal.getText() + "RGB");
				jlCanal.setVisible(true);
			}
		});
		menuColor.add(rgbColor);
		
		JMenuItem cmykColor = new JMenuItem("CMYK");
		menuColor.add(cmykColor);
		
		JMenuItem hsvColor = new JMenuItem("HSV");
		menuColor.add(hsvColor);
		
		// =================== Canales ====================================
		JButton c1 = new JButton (" Canal 1");
		c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {					
			   jdCanales.gCanalR(getCurrentImage().img);			 									
			}
		});			
		
		barSup.add(c1); // -> Canal 1
		JButton c2 = new JButton (" Canal 2");
		c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				 jdCanales.gCanalG(getCurrentImage().img);			 									
			}
		});			
		
		barSup.add(c2); // -> Canal 2
		JButton c3 = new JButton (" Canal 3");
		c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				 jdCanales.gCanalB(getCurrentImage().img);			 									
			}
		});			
		
		barSup.add(c3); // -> Canal 3
		//==================== DESHACER =================================== 
		JButton desHacer = new JButton ("<");
		desHacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				mostrar(oImage);			 									
			}
		});			
		
		barSup.add(desHacer);
        // ==================== DESHACER TODO ===================================
		JButton goTo = new JButton ("<<");
		goTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrar(oImage);
				panelCMD.setText(msgs.msgOperacion(7,arc.getImageName(), oImage.img));								
			}
		});
		barSup.add(goTo);	
		
		
		// ============== Refrescar ===================================
		JButton refrescar = new JButton("F5");
		refrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				oImage = getCurrentImage();	
				removeCurrentImage();						
				mostrar(oImage);
			}
		});
		barSup.add(refrescar); 
			
		return barSup;
	}
	//========================================================================================================
	private final void mostrar(Image img2){
				   	    
	    JScrollPane scroll = new JScrollPane();
	    scroll.setViewportView(img2.panel);
		jTP.addTab(" Imagen: ", scroll);

		try {
			Graficar (img2.toBufferedImage(), img2.panel);
		}catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "No se pudo cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
	    } 
	    miVentana.setVisible(true);	
	}

	
	private void Graficar ( BufferedImage image, ImagePanel Panel){
								
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
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalR, Color.red, image, Panel, panelCMD);	                    
	                    break;
	                case 1:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalG, Color.green, image, Panel, panelCMD);
	                    break;
	                case 2:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalB, Color.blue, image, Panel, panelCMD);
	                    break;
	                case 3:
	                   // ObjDibujaHisto.crearHistograma(histogramaCanal, canalX, Color.black, image, actPanel, panelCMD);
	                    break;
	                case 4:
	                    ObjDibujaHisto.crearHistograma(histogramaCanal, canalY, Color.gray, image, Panel, panelCMD);
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
	
	//========================================================================================================
    /*  Funciones de jTP */
	public static Image getImage(int tabIndex) {
		return ((ImagePanel) ((JScrollPane) jTP.getComponentAt(tabIndex)).getViewport().getView()).image;
	}
	//========================================================================================================
	public static Image getCurrentImage() {
		return getImage(jTP.getSelectedIndex());
	}
	//========================================================================================================
	public static void removeCurrentImage() {
		jTP.remove(jTP.getSelectedIndex());
	}
}