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
import Images.Tracer;

import com.mathworks.toolbox.javabuilder.MWException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import matlab.*;
import matlabToJavaSC.Espacios;
import matlabToJavaSC.Segmentacion;

import javax.swing.ImageIcon;

import procesos.Canales;
import procesos.HistoInfo;
import procesos.Tools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainWindow {
	
	private static JFrame miVentana;

	public static JPanel [] canales = {new JPanel(), new JPanel(), new JPanel(), new JPanel()};  
	public static CloseableTabbedPane jTP;
	
	private Canales jdCanales;
    private Image oImage;
    private Espacios esp;
    private Segmentacion fun;
 
    private Info info;
    private HistoInfo hInfo;
    private ColorSpace espColor;

	/******************* MAIN  *****************************/	 
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
		
		miVentana.setTitle("lsch Segementation v1.0");
		miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miVentana.setSize(1140, 680); //->Notocar	
		miVentana.getContentPane().setLayout(new BorderLayout());
			
		miVentana.getContentPane().add(panelSup(),BorderLayout.PAGE_START);	
		miVentana.getContentPane().add(panelTools(),BorderLayout.WEST);
		miVentana.getContentPane().add(jTP);
		
		JPanel divFooter = new JPanel(new BorderLayout());
		divFooter.add(hInfo, BorderLayout.NORTH);
		divFooter.add(pFooter(), BorderLayout.CENTER);
		miVentana.getContentPane().add(divFooter, BorderLayout.PAGE_END);		
		
		//miVentana.setResizable(false);
		miVentana.setVisible(true);
		
	}
//===================================================================================================================	
	public MainWindow(File file, JTextArea msg, JProgressBar progressBar) throws MWException {
		
		
		new Archivos(); progressBar.setValue(100);
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
		jTP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getCurrentImage().canales(canales);
			}
		});
		msg.append("  Instanciando pestañas: .......... ok\n");progressBar.setValue(600);
		info = new Info();		
		msg.append("  Instanciando clase de informacion: .......... ok\n");progressBar.setValue(700);
		hInfo = new HistoInfo();			
		msg.append("  Informacion de Histograma: .......... ok\n");progressBar.setValue(700);		
		miVentana = new JFrame(); initialize();
	    msg.append(" Configurando ventana principal : .......... ok\n");progressBar.setValue(900);	
		
		msg.append("  Load Imagen: .......... ok\n");
		try {
			if (file.isFile()){			   
			   Tracer.insert(file,ImageIO.read(file),true, canales);			   			  
			   
			}else{
				File []list = file.listFiles();
				ImageFilter imgFiltro= new ImageFilter();	// -> Filtros	
		    	 for (int i = 0; i < list.length; i++ ){
		    		if (imgFiltro.accept(list[i]) && (list[i].isFile())){
		    			System.out.println("Load image batch: "+  list[i]);
		    		    Image newImg = new Image(file,ImageIO.read(list[i]),true,canales);
		    		    //Info.msg(0,newImg.name, newImg.img);			   
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
					Tracer.insert("*ImageSegment", seg.getImgOut());
																					    
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
		scPanel.add(jdCanales.init(canales)); // --> Inicializo los canales 
		// =================== Espacio de Colores ====================================		
		cbSpaceColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(1) .toString())){			    	    				    				    				   
			    	
			    	Tracer.insert("*ImageHSV", espColor.toImgHsv(esp, getPath()));			    						
			    	scPanel.add(jdCanales.generarComoboBox("HSV", getCurrentImage().img));			    				        			    
			    }   
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(2) .toString())){
			    	
			    	Tracer.insert("*ImageLAB", espColor.toImglab(esp, getPath()));			    									    
			    	scPanel.add(jdCanales.generarComoboBox("LAB", getCurrentImage().img));			    				    	
			    }
			    if (cbSpaceColor.getSelectedItem().equals(cbSpaceColor.getItemAt(3) .toString())){
			    	System.out.println("YCbCr");			    				    				    				    
			    	
			    	Tracer.insert("*ImageLAB", espColor.toImgYCbCr(esp, getPath()));					    				    
			    	scPanel.add(jdCanales.generarComoboBox("YCbCr", getCurrentImage().img));			    	
			    }
			}
		});	
			
		scPanel.add(comboBox);	
		scPanel.add(cbSpaceColor);	
		
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
					oImage = getCurrentImage();
					removeCurrentImage();
					Image newImg = new Image(oImage.getFileCompleto(),Tools.Zoom_out(oImage.img),false, canales);
					//Info.msg(2,newImg.name, newImg.img);
					mostrar(newImg);																
				}
			});				
			
			barTools.add(ZoomPlus);
			//==================== ZOOM -- =================================== 
			JButton ZoomMinus = new JButton ();
			ZoomMinus.setIcon(new ImageIcon("image\\zOut.png", "Zoom --"));
			ZoomMinus.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oImage = getCurrentImage();				
					 Image newImg = new Image(getFile(),Tools.Zoom_in(oImage.img),false, canales);
					// Info.msg(3,newImg.name, newImg.img);
					 removeCurrentImage(); 
					 mostrar(newImg);								 			  
				}
			});				
			barTools.add(ZoomMinus);
			//==================== GIRAR IZQ =================================== 
			JButton girarIZQ = new JButton ();
			girarIZQ.setIcon(new ImageIcon("image\\gIn.png", "Girar Izq"));
			girarIZQ.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {						 
					 oImage = getCurrentImage();				
					 Image newImg = new Image(getFile(),Tools.rotarI(oImage.img),false, canales);
					// Info.msg(4,newImg.name, newImg.img);
					 removeCurrentImage(); 
					 mostrar(newImg);							 
				}
			});					
			barTools.add(girarIZQ);
	        // ==================== GIRAR DCH ===================================
			JButton girarDCH = new JButton ();
			girarDCH.setIcon(new ImageIcon("image\\gOut.png", "Girar Dch"));
			girarDCH.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {											  	
					oImage = getCurrentImage();				
					Image newImg = new Image(oImage.getFileCompleto(),Tools.rotarD(oImage.img),false, canales);
					//Info.msg(5,newImg.name, newImg.img);
					removeCurrentImage(); 
					mostrar(newImg);						 			 
				}
			});				
			barTools.add(girarDCH);
					
			//==================== DESHACER =================================== 
			JButton desHacer = new JButton ();
			desHacer.setIcon(new ImageIcon("image\\atras.png", " atras"));
			desHacer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("Pulsado el boton deshacer");
					if (getCurrentImage() != null){
						removeCurrentImage();
						Tracer.getLast();			 									
				}   }
			});			
			
			barTools.add(desHacer);
	        // ==================== DESHACER Todo ===================================
			JButton goTo = new JButton ();
			goTo.setIcon(new ImageIcon("image\\atras_all.png", " deshacer todo"));
			goTo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("Pulsado el boton deshacer todo");
					removeCurrentImage();
					mostrar(Tracer.getFirst());								
				}
			});
			barTools.add(goTo);	
			
			
			// ============== Refrescar ===================================
			JButton refrescar = new JButton();
			refrescar.setIcon(new ImageIcon("image\\refrescar.png", " arefrescar"));
			refrescar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {	
					System.out.println("Falta por hacer");
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
					File file = Archivos.loadFile();
					Tracer.insert(file,ImageIO.read(file),true, canales);
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Hubo error durante la carga", "Load error", JOptionPane.ERROR_MESSAGE);					
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