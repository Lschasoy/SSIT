package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

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
import procesos.Tools;
import java.awt.FlowLayout;


public class MainWindow {
	
	private static JFrame miVentana;

	private static JPanel [] canales = {new JPanel(), new JPanel(), new JPanel(), new JPanel()};  
	public static CloseableTabbedPane jTP;
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private static Mensajes msgs;
	private Archivos arc;
	 
   
    private Canales jdCanales;
    private Image oImage;
    private Espacios esp;
    private Segmentacion fun;
    private static DibujarGrafica dg;
    private Info info;
    private ColorSpace espColor;

	/******************* MAIN 
	 * @param file *****************************/	 
	public static void main(final File file, final JTextArea digStart, final JProgressBar progressBar) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");					
					MainWindow window = new MainWindow(file, digStart, progressBar);
					progressBar.setValue(1000);
					digStart.append("  Generando ventana principal: .......... FIN");					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
//===================================================================================================================
	/**name: initialize 
	 * descrip: Configuracion y distribucion de la pantalla principal
	 */
	public void initialize(){
		
		miVentana.setTitle("TFG - sImage beta v.4.0");
		miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miVentana.setSize(1140, 680); //->Notocar	
		miVentana.getContentPane().setLayout(new BorderLayout());
			
		miVentana.getContentPane().add(panelSup(),BorderLayout.PAGE_START);	
		miVentana.getContentPane().add(panelTools(),BorderLayout.WEST);
		miVentana.getContentPane().add(jTP);
		miVentana.getContentPane().add(pFooter(), BorderLayout.PAGE_END);		
		
		//miVentana.setResizable(false);
		miVentana.setVisible(true);
		
	}
//===================================================================================================================	
	public MainWindow(File file, JTextArea msg, JProgressBar progressBar) throws MWException {
		
		msgs = new Mensajes();		
		msg.append("  Instanciando Command Line : .......... ok\n"); progressBar.setValue(100);
		arc = new Archivos();
		msg.append("  Instanciando clase de manipulacion de archivo: .......... ok\n");progressBar.setValue(200);
		jdCanales = new Canales();
		msg.append("  Instanciando Histogramas y canales: .......... ok\n");progressBar.setValue(300);
		esp = new Espacios();
		msg.append("  Instanciando Espacios de colores: .......... ok\n");progressBar.setValue(400);
		fun = new Segmentacion();
		msg.append("  Cargando lanzadores de funciones de segementacion: .......... ok\n");progressBar.setValue(500);
		espColor = new ColorSpace();
		msg.append("  Cargando funciones de espacio de color .......... ok\n");
		jTP = new CloseableTabbedPane();		
		msg.append("  Instanciando pestañas: .......... ok\n");progressBar.setValue(600);
		info = new Info();		
		msg.append("  Instanciando clase de informacion: .......... ok\n");progressBar.setValue(700);
		dg = new DibujarGrafica();
		msg.append("  Instanciando graficas: .......... ok\n");progressBar.setValue(800);			
		miVentana = new JFrame(); initialize();
	    msg.append(" Configurando ventana principal : .......... ok\n");progressBar.setValue(900);	
						
			
		msg.append("  Load Imagen: .......... ok\n");
		try {
			if (file.isFile()){
			   Image newImg = new Image(file,ImageIO.read(file),true);
			   info.msg(0,newImg.name, newImg.img);			   
			   mostrar(newImg);
			}else{
				File []list = file.listFiles();
				ImageFilter imgFiltro= new ImageFilter();	// -> Filtros	
		    	 for (int i = 0; i < list.length; i++ ){
		    		if (imgFiltro.accept(list[i]) && (list[i].isFile())){
		    			System.out.println("Load image batch: "+  list[i]);
		    		    Image newImg = new Image(file,ImageIO.read(list[i]),true);
		    		    info.msg(0,newImg.name, newImg.img);			   
		 			    mostrar(newImg);
		    		}
		    	 }	
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "[Error] No Image", "Error", JOptionPane.ERROR_MESSAGE);
		}
	  		  
	}
	 			
	public JPanel pFooter(){				
	    //====> Panel de la parte central <=============================== 			    	
	    final JPanel pFooter = new JPanel(new GridLayout(0,4));
	    pFooter.setMaximumSize(new Dimension(1100, 165));
	    pFooter.setPreferredSize(new Dimension(1100, 165));
	    pFooter.setMinimumSize(new Dimension(100, 165));	
	    
	    for (int i = 0; i < 4; i++) 
	    	pFooter.add(canales[i]);	    			    
	    	    	 	  	  	   	    	    	    	     	    	    		 	    	    		
		return pFooter;
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
		comboBox.setBounds(5, 0, 150, 20);	
		String [] algoritmos = {"Algoritmos", "ms", "srm"};
		comboBox.setModel(new DefaultComboBoxModel(algoritmos));
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    if (comboBox.getSelectedItem().equals(comboBox.getItemAt(1) .toString())){
			    	System.out.println("Combox seleccion: ms");
					
					FormSegment seg = new FormSegment();
					FormSegment.main(seg, getPath(), fun);
														
					Image img = new Image(null,seg.getImgOut(),false);					
				    mostrar(img);											     
			    }
			    if (comboBox.getSelectedItem().equals(comboBox.getItemAt(2) .toString())){
			    	System.out.println("Segementacion_2");				        			
			    }				 
			}
		});				
		
		final JComboBox cbSpaceColor = new JComboBox();		
		cbSpaceColor.setBounds(155, 0, 150, 20);		
		String[] spacecolor = {"RGB", "HSV", "LAB", "YCbCr"};
		cbSpaceColor.setModel(new DefaultComboBoxModel(spacecolor));		
		scPanel.add(jdCanales.init()); // --> Inicializo los canales 
		// =================== Espacio de Colores ====================================		
		cbSpaceColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(1) .toString())){
			    	System.out.println("HSV");			    				    				    				    
			    	Image img = new Image(getFile(),espColor.toImgHsv(esp, getPath()),true);																			
					mostrar(img);			    	
			    	scPanel.add(jdCanales.generarComoboBox("HSV", getCurrentImage().img));			    				        			    
			    }   
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(2) .toString())){
			    	System.out.println("LAB");			    				    				    				   
			    	Image img = new Image(getFile(),espColor.toImglab(esp, getPath()),true);																			
					mostrar(img);			    	
			    	scPanel.add(jdCanales.generarComoboBox("LAB", getCurrentImage().img));			    				    	
			    }
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(3) .toString())){
			    	System.out.println("YCbCr");			    				    				    				    
			    	Image img = new Image(getFile(),espColor.toImgYCbCr(esp, getPath()),true);																			
					mostrar(img);			    	
			    	scPanel.add(jdCanales.generarComoboBox("YCbCr", getCurrentImage().img));			    	
			    }
			}
		});	
			
		scPanel.add(comboBox);	
		scPanel.add(cbSpaceColor);	
		
		//============ Panel de informacion ===============
		FlowLayout flowLayout = (FlowLayout) info.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		info.setSize(646, 20);
		info.setLocation(474, -2);
		scPanel.add(info);
		return scPanel;
	}
	
	public JPanel panelTools (){
		
		JPanel barTools = new JPanel();	
		barTools.setLayout(new GridLayout(8,1));	
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
		barSup.setLayout(new GridLayout(2,1));
		
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
					Image newImg = new Image(pathFile,ImageIO.read(pathFile),true);										
					info.msg(0,newImg.name, newImg.img);					
					mostrar(newImg);
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
        barSup.add(panelCombos());
		return barSup;
	}
	//========================================================================================================
	public static void mostrar(Image img2){
				   	    
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
    /*  Funciones de jTP */
	public static Image getImage(int tabIndex) {
		return ((ImagePanel) ((JScrollPane) jTP.getComponentAt(tabIndex)).getViewport().getView()).image;
	}
	//========================================================================================================	
	public static void changeImageTitle(Image image) {
		String title = image.name;
		if (!image.saved) {
			title = "* " + title;
		}
		jTP.setTitleAt(jTP.getSelectedIndex(), title);
	}
	//===========================================================================================================
	public static String getPath (){ return getCurrentImage().getFileOriginal().getAbsolutePath().toString(); }
	public static File getFile () {return getCurrentImage().getFileOriginal();}
	
	public static Image getCurrentImage() {return getImage(jTP.getSelectedIndex());}
	public static void removeCurrentImage() {jTP.remove(jTP.getSelectedIndex());}
	
	
}