
import java.awt.EventQueue;

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
import java.io.IOException;

import javax.swing.JTable;


import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainWindow {
	
	private JFrame miVentana;
	private ImagePanel actPanel;
	private JPanel centralPanel, Ipanel, Spanel, canalR, canalG, canalB, canalX, canalY;
	
	public  BufferedImage originalImage, finalImage;
    public  Image oImage;  
		
	private float xScaleFactor = 1, yScaleFactor = 1, degree;
	private JTextArea panelCMD;
	private JTable tablaMenuImage;
	
	
	private MyTableModel modelo;
	private Mensajes msgs;
	private Archivos arc;
	
	 
    private Histograma ObjHistograma;
    private int[][] histograma;
    private DibujarGrafica ObjDibujaHisto;
    private PopWindows popUp;
        
	
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
		
		ImagePanel.setRoiListener(); // --> Importar ImagenPanel 
		
	// ===============> Inicializar wind <=============================	
		miVentana = new JFrame();		
		miVentana.setTitle(msgs.getVersion());
		miVentana.getContentPane().setBackground(Color.LIGHT_GRAY);
		miVentana.setSize(1250, 650);
	// ================> Inicializar ventanas <=========================			
		miVentana.getContentPane().add(panelSup(), BorderLayout.NORTH);
		miVentana.getContentPane().add(panelCentral(),BorderLayout.CENTER);	
	}

	
	
	public JPanel panelMenuImage (){
		
		 JPanel pMenuImage = new JPanel();				 
		 pMenuImage.setBounds(720, 495, 500, 110);
		 		
		 
		 
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
		    		
		    		//msg = "\n$ > [ImageMenu] : Seleccionado image [" + row + col +"]" ; 
					//panelCMD.setText(msg);
					try {
						Image imag = arc.loadImage(row, col, tablaMenuImage,  modelo);						
						mostrar(imag);
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
	    centralPanel = new JPanel();
	    centralPanel.setLayout(null);
		
	    //=========> Imagen Central <===========
		
			
		
		//===  Inicializar Commnad Line ===
		panelCMD = new JTextArea();				
	    centralPanel.add(msgs.initMsg(panelCMD));
	    
	    // ======= Menu de Algoritmos =============================
	      
		Ipanel = new JPanel();
		//initPanel(1110, 415, 130, 180, Ipanel, centralPanel);
		
		JLabel lblAlgortimoDeSegmentacion = new JLabel("Algortimo de segmentacion");
		lblAlgortimoDeSegmentacion.setBounds(700, 410, 130, 14);
		//centralPanel.add(lblAlgortimoDeSegmentacion);
	    
	    centralPanel.add(panelMenuImage());	
		
		
		/****** Inicializar canales *************/
		canalR = new JPanel();
		initPanel(970, 5, 250, 145, canalR, centralPanel );				
		canalG = new JPanel();
		initPanel(970, 150, 250, 145, canalG, centralPanel);		
		canalB = new JPanel();
		initPanel(720, 5, 250, 145, canalB, centralPanel);		
		// canalX =  new JPanel(); --> No se usaba "Canal Alfa "
		// initPanel(390, 415, 285, 140, canalX, centralPanel);	
		
		canalY = new JPanel();
		initPanel(720, 150, 250, 145, canalY, centralPanel);
		
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
												    				   				  				  				   
				   Image img = arc.loadFile(tablaMenuImage, modelo);				   	
				   mostrar(img);					    
				   
				  //  panelCMD.setText(msgs.msgOperacion(0,arc.getImageName(), originalImage));				    				 							   				    				    				            				  			  	
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
				mostrar(oImage);
						
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
				  mostrar(oImage);				  			
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
				
				 mostrar(oImage);
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
				 mostrar(oImage);
			}
		});		
		
		Spanel.add(girarDCH);
		
		JLabel lblBack = new JLabel(" Editar");
		Spanel.add(lblBack);
		
		//==================== DESHACER =================================== 
		JButton desHacer = new JButton ("Deshacer");
		desHacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				    oImage =  arc.loadImage(0, 0, tablaMenuImage, modelo);
					mostrar(oImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String msg = "Pos X" + arc.getX() + "Pos Y" + arc.getY(); 
				panelCMD.setText(msgs.msgOperacion(6,msg, originalImage));	
				
			}
		});			
		
		Spanel.add(desHacer);
        // ==================== DESHACER TODO ===================================
		JButton goTo = new JButton ("Deshacer todo");
		goTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				    oImage = arc.loadImage(0, 0, tablaMenuImage, modelo);
					mostrar(oImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
	 	    	    popUp.gCanalR(originalImage); 	    	     	    	
	 	    	}
	 	 });
		Spanel.add(iCanalR);
	 //================ BOTON  G ===================================	
		JButton iCanalG = new JButton("Green");
		iCanalG.addMouseListener(new MouseAdapter() {
	 	    	@Override
	 	    	public void mouseClicked(MouseEvent arg0) { 
	 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
	 	    	    popUp.gCanalG(originalImage); 	    	     	    	
	 	    	}
	 	});
		Spanel.add(iCanalG);
	 //================ BOTON B  ===================================	
	    JButton iCanalB = new JButton("Blue");
	    iCanalB.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 
 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
 	    	    popUp.gCanalB(originalImage); 	    	     	    	
 	    	}
  	    });
	    Spanel.add(iCanalB);
 	 //================ BOTON X ===================================
 	    JButton iCanalX = new JButton("Black"); 	
 	    iCanalX.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) { 	
	    		popUp = new PopWindows(tablaMenuImage,  modelo);
	    	    popUp.gCanalX(originalImage); 	    	     	    	
	    	}
	    });
 	   Spanel.add(iCanalX);
	    //================ BOTON Y ===================================
	    JButton iCanalY = new JButton("Gray");
	    iCanalY.addMouseListener(new MouseAdapter() {
 	    	@Override
 	    	public void mouseClicked(MouseEvent arg0) { 	    		
 	    		popUp = new PopWindows(tablaMenuImage,  modelo);
 	    	    popUp.gCanalY(originalImage); 	    	     	    	
 	    	}
 	    });
	    Spanel.add(iCanalY);
	
		return Spanel;
	}
	
	private void mostrar(Image img2){
		
		actPanel = new ImagePanel(img2);
	    	
		final JPanel pScroll = new JPanel();
		pScroll.setBounds(10, 10, 700, 500);			
		pScroll.setLayout(null);
	    
		final JScrollPane scroll = new JScrollPane(actPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 700, 500);		
		pScroll.add(scroll);
									
		centralPanel.add(pScroll);	
						 
		try {
			Graficar (img2.toBufferedImage());
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
	                   // ObjDibujaHisto.crearHistograma(histogramaCanal, canalX, Color.black, image, actPanel, panelCMD);
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