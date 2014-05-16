import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import javax.swing.JPanel;

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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.xnap.commons.gui.CloseableTabbedPane;
import java.awt.Component;


public class MainWindow {
	
	private JFrame miVentana;

	private JPanel infoShow, Spanel, canalR, canalG, canalB,  canalY;
	  
	public static CloseableTabbedPane jTP;
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private static JTextArea panelInfo;
	private JTable tablaMenuImage;
	
	
	private MyTableModel modelo;
	private static Mensajes msgs;
	private Archivos arc;
	 
    private Histograma ObjHistograma;
    private int[][] histograma;
    private DibujarGrafica ObjDibujaHisto;
    private PopWindows popUp;
        
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
		jTP = new CloseableTabbedPane();
		
		
		ImagePanel.setRoiListener(); // --> Importar ImagenPanel 
		
	// ===============> Inicializar wind <=============================	
		miVentana = new JFrame();		
		miVentana.setTitle("TFG - sImage beta v.4.0");
		miVentana.getContentPane().setBackground(Color.GRAY);
		miVentana.getContentPane().setLayout(new BoxLayout(miVentana.getContentPane(), BoxLayout.Y_AXIS));
		miVentana.getContentPane().add(panelSup()); // --> Parte Superior
		miVentana.getContentPane().add(panelCentral());		
		miVentana.pack();		
		miVentana.setSize(1140, 650); //->Notocar
		
	// ================> Inicializar ventanas <=========================			
		
	}
	
	public JPanel panelMenuImage (){
		
		 JPanel pMenuImage = new JPanel();				 
		 pMenuImage.setBounds(5, 505, 500, 110);
		 				 		 
		 // Inicializacion Table Model
		 String [] nombreColumnas = {"image", "image", "image","image", "image", "image","image"};
		 Object [][] datosFila = {{null,null,null, null,null,null,null}};
		
		 tablaMenuImage = new JTable();		 
		 
		 modelo.setDataVector(datosFila, nombreColumnas);
		 tablaMenuImage.setModel(modelo);
		 tablaMenuImage.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		 		 
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
		
		JPanel central = new JPanel();
		central.setLayout(new GridLayout(1,2));
		
	    //====> Panel de la parte central <=============================== 			    	
	    infoShow = new JPanel();
	    infoShow.setLayout(null);
	
		/****** Inicializar canales *************/
		canalR = new JPanel();
		initPanel(255, 5, 250, 145, canalR, infoShow);				
		canalG = new JPanel();
		initPanel(255, 150, 250, 145, canalG, infoShow);		
		canalB = new JPanel();
		initPanel(5, 5, 250, 145, canalB, infoShow);						
		canalY = new JPanel();
		initPanel(5, 150, 250, 145, canalY, infoShow);
		
		JLabel lblMenuImagen = new JLabel("Menu de Imagenes");
		lblMenuImagen.setBounds(5, 495, 130, 14);
		infoShow.add(lblMenuImagen);
	    infoShow.add(panelMenuImage());
	    	     	    
	    //===  Inicializar Commnad Line ===
		panelCMD = new JTextArea();				
	    infoShow.add(msgs.initMsg(panelCMD));
	    
	    central.add(infoShow); // -> Cargo la informacion
	    central.add(jTP); // -> Cargo las pestañas
	    	     	    	    		 	    	    				 	
		return central;
	}
	

	public JPanel panelSup(){
		
		Spanel = new JPanel();
		Spanel.setLayout(new BoxLayout(Spanel, BoxLayout.X_AXIS));
		
		
		JLabel lblMenu = new JLabel(" Menu ");
		Spanel.add(lblMenu);
		//==================== Load Image ===================================
		JButton cargarImagen = new JButton("Load Image");
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								   				   				   				  
					try {
						File pathFile = Archivos.loadFile(tablaMenuImage, modelo);
						Image img = new Image(pathFile,ImageIO.read(pathFile),true);
					    System.out.println("Salida: "+ img);
						panelCMD.setText(msgs.msgOperacion(0,img.toString(), img.img));						
						mostrar(img);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Erro al cargar", "Load Error", JOptionPane.ERROR_MESSAGE);	
					
					}				   
			}
		});
		Spanel.add(cargarImagen);
					
		//==================== Save Image ===================================
		JButton salvarImagen = new JButton("Save Image");
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 Archivos arc = new Archivos();
			     arc.saveFile();
			  //   panelCMD.append(msgs.msgOperacion(1,arc.getImageName(), oImage.img));				    				    				    			
			}
		});
		Spanel.add(salvarImagen);

		//==================== ZOOM ++ ===================================
		JButton ZoomPlus = new JButton ("ZOOM ++");
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
		
		JLabel lblTools = new JLabel(" Tools");
		Spanel.add(lblTools);
		
		Spanel.add(ZoomPlus);
		//==================== ZOOM -- =================================== 
		JButton ZoomMinus = new JButton ("ZOOM --");
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
		
		Spanel.add(ZoomMinus);
		//==================== GIRAR IZQ =================================== 
		JButton girarIZQ = new JButton ("Girar IZQ");
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
		
		Spanel.add(girarIZQ);
        // ==================== GIRAR DCH ===================================
		JButton girarDCH = new JButton ("Girar DCH");
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
		
		Spanel.add(girarDCH);
		
		JLabel lblBack = new JLabel(" Editar");
		Spanel.add(lblBack);
		
		//==================== DESHACER =================================== 
		JButton desHacer = new JButton ("Deshacer");
		desHacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				mostrar(oImage);			 									
			}
		});			
		
		Spanel.add(desHacer);
        // ==================== DESHACER TODO ===================================
		JButton goTo = new JButton ("Deshacer todo");
		goTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrar(oImage);
				panelCMD.setText(msgs.msgOperacion(7,arc.getImageName(), oImage.img));
								 
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
	 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
	 	    	    popUp.gCanalR(oImage.img); 	    	     	    	
	 	    	}
	 	 });
		Spanel.add(iCanalR);
	 //================ BOTON  G ===================================	
		JButton iCanalG = new JButton("Green");
		iCanalG.addMouseListener(new MouseAdapter() {
	 	    	@Override
	 	    	public void mouseClicked(MouseEvent arg0) { 
	 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
	 	    	    popUp.gCanalG(oImage.img); 	    	     	    	
	 	    	}
	 	});
		Spanel.add(iCanalG);
	 //================ BOTON B  ===================================	
	    JButton iCanalB = new JButton("Blue");
	    iCanalB.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 
 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
 	    	    popUp.gCanalB(oImage.img); 	    	     	    	
 	    	}
  	    });
	    Spanel.add(iCanalB);
 	 //================ BOTON X ===================================
 	    JButton iCanalX = new JButton("Black"); 	
 	    iCanalX.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) { 	
	    		popUp = new PopWindows(tablaMenuImage,  modelo);
	    	    popUp.gCanalX(oImage.img); 	    	     	    	
	    	}
	    });
 	   Spanel.add(iCanalX);
	    //================ BOTON Y ===================================
	    JButton iCanalY = new JButton("Gray");
	    iCanalY.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 	    		
 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
 	    	    popUp.gCanalY(oImage.img); 	    	     	    	
 	    	}
 	    });
	    Spanel.add(iCanalY);
	
		return Spanel;
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