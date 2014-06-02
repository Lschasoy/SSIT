package main;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
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

import javax.swing.border.BevelBorder;

import java.io.File;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.xnap.commons.gui.CloseableTabbedPane;

import Images.Image;
import Images.ImageFilter;
import Images.ImagePanel;

import com.mathworks.toolbox.javabuilder.MWException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import matlab.*;
import matlabToJavaSC.Espacios;
import matlabToJavaSC.Segmentacion;

import javax.swing.ImageIcon;

import procesos.Canales;
import procesos.DibujarGrafica;
import procesos.FormSegment;
import procesos.Tools;



public class MainWindow {
	
	private JFrame miVentana;

	private JPanel infoShow, canalR, canalG, canalB,  canalY;
	private JPanel [] canales = {null, null, null, null};  
	public static CloseableTabbedPane jTP;
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private JTable tablaMenuImage;
	
	private static Mensajes msgs;
	private Archivos arc;
	 
   
    private Canales jdCanales;
    private Image oImage;
    private Espacios esp;
    private Segmentacion fun;
    private Info info;
    private DibujarGrafica dg;
    
   
	/******************* MAIN 
	 * @param file *****************************/	 
	public static void main(final File file) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainWindow window = new MainWindow(file);
					window.miVentana.setResizable(false);
					window.miVentana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** Create the application. 
	 * @param file 
	 * @throws MWException */
	public MainWindow(File file) throws MWException {
		
		msgs = new Mensajes();
		arc = new Archivos();
		jdCanales = new Canales();
		esp = new Espacios();
		fun = new Segmentacion();
				
		jTP = new CloseableTabbedPane();
		info = new Info();	
		dg = new DibujarGrafica();
		
	// ===============> Inicializar wind <=============================	
		miVentana = new JFrame();		
		miVentana.setTitle("TFG - sImage beta v.4.0");
		miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
		miVentana.getContentPane().setLayout(new GridLayout(1,2));			
		miVentana.getContentPane().add(panelCentral());		
		miVentana.getContentPane().add(jTP);		
		miVentana.setSize(1140, 680); //->Notocar		
		
			
		
	 // ==> Fin de inizializacion, se crea la imagen 
		try {
			if (file.isFile()){
			   Image newImg = new Image(file,ImageIO.read(file),true);
			   panelCMD.setText(msgs.msgOperacion(0,newImg.name, newImg.img));			   
			   mostrar(newImg);
			}else{
				File []list = file.listFiles();
				ImageFilter imgFiltro= new ImageFilter();	// -> Filtros	
		    	 for (int i = 0; i < list.length; i++ ){
		    		if (imgFiltro.accept(list[i]) && (list[i].isFile())){
		    			System.out.println("Load image batch: "+  list[i]);
		    		    Image newImg = new Image(file,ImageIO.read(list[i]),true);
		 			    panelCMD.setText(msgs.msgOperacion(0,newImg.name, newImg.img));			   
		 			    mostrar(newImg);
		    		}
		    	 }	
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "[Error] No Image", "Error", JOptionPane.ERROR_MESSAGE);
		}
	  		  
	}
	 			
	public JPanel panelCentral (){
		
		
	    //====> Panel de la parte central <=============================== 			    	
	    infoShow = new JPanel();	    
	    infoShow.setBorder(null);
	    infoShow.setLayout(null);
	
	    infoShow.add(panelSup()); 	
	    infoShow.add(panelTools());
	    infoShow.add(panelCombos());
	    
	    
		/****** Inicializar canales *************/
	    canalR = new JPanel(); /*******/ canales[0] = canalR;
		initPanel(275, 90, 270, 140, canalR, infoShow);		
		canalG = new JPanel(); /*******/ canales[1] = canalG; 
		initPanel(275, 230, 270, 140, canalG, infoShow);		
		canalB = new JPanel(); /*******/canales[2] = canalB;
		initPanel(5, 90, 270, 140, canalB, infoShow);						
		canalY = new JPanel(); /*******/ canales[3] = canalY;
		initPanel(5, 230, 270, 140, canalY, infoShow);		
		
	    infoShow.add(info);
	    	 	  
	    //===  Inicializar Commnad Line ===
		panelCMD = new JTextArea();				
		JScrollPane scroll = new JScrollPane(msgs.initMsg(panelCMD));
		scroll.setBounds(5, 450, 540, 190); // Size Y =  680
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
	    infoShow.add(scroll);	   
	    	    	    	     	    	    		 	    	    				
		return infoShow;
	}
	/**
	 * @param: scPanel: contiene los ComboBox
	 * @return: cpParam: Panel que contienen los ComboBox y los JPanel de parametros 
	 */    
	public JPanel panelCombos(){
	
										
 		final JPanel scPanel = new JPanel();
 		scPanel.setBounds(0, 50, 545, 25);
		scPanel.setLayout(null);
									
		// =================== Segmentacion ====================================
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(5, 0, 150, 25);	
		String [] algoritmos = {"Algoritmos", "Segementacion_1", "Segementacion_2"};
		comboBox.setModel(new DefaultComboBoxModel(algoritmos));
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    if (comboBox.getSelectedItem().equals(comboBox.getItemAt(1) .toString())){
			    	System.out.println("Segementacion_1");
					String path = getCurrentImage().getFileOriginal().getAbsolutePath().toString();
					FormSegment seg = new FormSegment();
					FormSegment.main(seg, path, fun);
														
					Image img = new Image(null,seg.getImgOut(),true);					
				    mostrar(img);
											     
			    }
			    if (comboBox.getSelectedItem().equals(comboBox.getItemAt(2) .toString())){
			    	System.out.println("Segementacion_2");				        			
			    }	
			 
			}
		});				
		
		final JComboBox cbSpaceColor = new JComboBox();		
		cbSpaceColor.setBounds(155, 0, 150, 25);		
		String[] spacecolor = {"RGB", "HSV", "LAB", "YCbCr"};
		cbSpaceColor.setModel(new DefaultComboBoxModel(spacecolor));		
			
		// =================== Espacio de Colores ====================================		
		cbSpaceColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(1) .toString())){
			    	System.out.println("HSV");
			    	String path = getCurrentImage().getFileOriginal().getAbsolutePath().toString();
			    	ColorSpace espColor = new ColorSpace();
			    				    	
			    	Image img = new Image(getCurrentImage().getFileOriginal(),espColor.toImgHsv(esp, path, panelCMD),true);																			
					mostrar(img);			    	
			    	scPanel.add(jdCanales.generarComoboBox("HSV", getCurrentImage().img));
			    	
			        			    	
			    }   
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(2) .toString())){
			    	System.out.println("LAB");
			    	String path = getCurrentImage().getFileOriginal().getAbsolutePath().toString();
			    	ColorSpace espColor = new ColorSpace();
			    	
			    	
			    	Image img = new Image(getCurrentImage().getFileOriginal(),espColor.toImglab(esp, path, panelCMD),true);																			
					mostrar(img);			    	
			    	scPanel.add(jdCanales.generarComoboBox("LAB", getCurrentImage().img));			    				    	
			    }
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(3) .toString())){
			    	System.out.println("YCbCr");
			    	String path = getCurrentImage().getFileOriginal().getAbsolutePath().toString();
			    	ColorSpace espColor = new ColorSpace();
			    	
			    	
			    	Image img = new Image(getCurrentImage().getFileOriginal(),espColor.toImgYCbCr(esp, path, panelCMD),true);																			
					mostrar(img);			    	
			    	scPanel.add(jdCanales.generarComoboBox("YCbCr", getCurrentImage().img));
			    	
			    }
			}
		});	
		
		scPanel.add(comboBox);	
		scPanel.add(cbSpaceColor);			
		
		return scPanel;
	}
	
	public JPanel panelTools (){
		
		JPanel barTools = new JPanel();	
		barTools.setLayout(new GridLayout(1,8));	
		barTools.setBounds(0, 25, 562, 25);
		
	//==================== ZOOM ++ ===================================
			JButton ZoomPlus = new JButton ();
			ZoomPlus.setIcon(new ImageIcon("image\\zIn.png", "Zoom ++"));
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
			
			barTools.add(ZoomPlus);
			//==================== ZOOM -- =================================== 
			JButton ZoomMinus = new JButton ();
			ZoomMinus.setIcon(new ImageIcon("image\\zOut.png", "Zoom --"));
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
			barTools.add(ZoomMinus);
			//==================== GIRAR IZQ =================================== 
			JButton girarIZQ = new JButton ();
			girarIZQ.setIcon(new ImageIcon("image\\gIn.png", "Girar Izq"));
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
			barTools.add(girarIZQ);
	        // ==================== GIRAR DCH ===================================
			JButton girarDCH = new JButton ();
			girarDCH.setIcon(new ImageIcon("image\\gOut.png", "Girar Dch"));
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
			barTools.add(girarDCH);
					
			//==================== DESHACER =================================== 
			JButton desHacer = new JButton ();
			desHacer.setIcon(new ImageIcon("image\\atras.png", " atras"));
			desHacer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {				
					mostrar(oImage);			 									
				}
			});			
			
			barTools.add(desHacer);
	        // ==================== DESHACER TODO ===================================
			JButton goTo = new JButton ();
			goTo.setIcon(new ImageIcon("image\\atras_all.png", " deshacer todo"));
			goTo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrar(oImage);
					panelCMD.setText(msgs.msgOperacion(7,arc.getImageName(), oImage.img));								
				}
			});
			barTools.add(goTo);	
			
			
			// ============== Refrescar ===================================
			JButton refrescar = new JButton();
			refrescar.setIcon(new ImageIcon("image\\refrescar.png", " arefrescar"));
			refrescar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {	
					oImage = getCurrentImage();	
					removeCurrentImage();						
					mostrar(oImage);
				}
			});
			barTools.add(refrescar); 
		return barTools;	
		
	}

	public JMenuBar panelSup(){
						
		
		JMenuBar barSup = new JMenuBar();	
		barSup.setBounds(0, 0, 562, 25);
		
		JMenu menuFile = new JMenu("File");
		menuFile.setHorizontalAlignment(SwingConstants.LEFT);
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Load Image");
		cargarImagen.setIcon(new ImageIcon("image\\open.png", "LOAD"));
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				try {
					File pathFile = Archivos.loadFile();
					System.out.println("Load Image: " + pathFile.toString());
					Image img = new Image(pathFile,ImageIO.read(pathFile),true);										
					panelCMD.setText(msgs.msgOperacion(0,pathFile.getAbsolutePath(), img.img));						
					mostrar(img);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro al cargar", "Load Error", JOptionPane.ERROR_MESSAGE);					
				}				   
			}
		});
		menuFile.add(cargarImagen); 
					
		//==================== Save Image ===================================
		JMenuItem salvarImagen = new JMenuItem("Save Image");
	 	salvarImagen.setIcon(new ImageIcon("image\\save.png", "SAVE"));
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 Archivos arc = new Archivos();
			     arc.saveFile();
			  //   panelCMD.append(msgs.msgOperacion(1,arc.getImageName(), oImage.img));				    				    				    			
			}
		});		
		menuFile.add(salvarImagen); // -> Fin menuFile
		
        barSup.add(menuFile);
		return barSup;
	}
	//========================================================================================================
	private final void mostrar(Image img2){
				   	    
	    JScrollPane scroll = new JScrollPane();
	    scroll.setViewportView(img2.panel);
		jTP.addTab(" ", scroll);		
		jTP.setEnabledAt(0, true);
		changeImageTitle(img2);
		
		try {
			dg.Graficar (img2.toBufferedImage(), img2.panel, canales);
		}catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Dibujar Histograma", "Error", JOptionPane.ERROR_MESSAGE);
	    } 
		
	    miVentana.setVisible(true);	
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
	
	public static void changeImageTitle(Image image) {
		String title = image.name;
		if (!image.saved) {
			title = "* " + title;
		}
		jTP.setTitleAt(jTP.getSelectedIndex(), title);
	}
}