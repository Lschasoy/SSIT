package main;
import com.mathworks.toolbox.javabuilder.MWException;
import images.ImageFilter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;


public class Index extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int FULL_PROGRESS = 1000;
	
	private JTextArea digStart;	
	
	private String welcomedigStart = "SSIT: Es una gui java para la segmentacion de imagenes de forma fácil,\n"
			                		+" implementa funciones de Matlab para generar las imagenes segmentadas. \n"
			                		+"\nLa interfaz implemeta el algoritmo de segmentacion Mean-Shift el cual \n"
			                		+ "genera una imagen segmentada con una alta calidad y a diferencia de \n"
			                		+ "los algoritmos de probabilidad y el k-Mean es mucho mas sencillo\n"
			                		+"\n¿Qué es Mean Shift ?\n"
			                		+" Mean Shift es una técnica no paramétrica para el análisis de un conjunto \n"
			                		+"de puntos d-dimensionales en un espacio de características que obtiene \n"
			                		+"un máximo local de la función de densidad estimada para la muestra. ";
	
	private String versiondigStart = "Version 1.0 - Primera revisión";
	
	private String aboutdigStart = "\n=============================================================\n"
			                       +" Proyecto: Simple Segmentation Image Tools (SSIT 1.0)\n"
			                  	   +" Autor: Ing Inf. Leonardo Siverio\n"
			                  	   +" Last Review: Mayo - 2014\n"
	                               +" Version: 1.0\n"
			                  	   +" Repositorio: github\\Lschasoy\\TFG\n"
	                               +"=============================================================\n";
	 
	JLabel lb_bg, lb_cmd;
	JPanel panel;
	
	
	public static void main(String[] args) {
		try {
			Index dialog = new Index();			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setResizable(false);			
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Index() {
		setTitle("Segmentation Tools - ULL - TFG");
		setBounds(0, 0, 465, 248);
		setBackground(Color.BLACK);
		
		
		lb_bg = new JLabel();
		lb_bg.setHorizontalAlignment(SwingConstants.CENTER);
		lb_bg.setIcon(new ImageIcon(Index.class.getResource("/bg_index.jpg")));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelSup(0,0,400,25), BorderLayout.NORTH);
		
		panel = new JPanel(new GridLayout(2,1));
		panel.setBackground(Color.BLACK);
		JScrollPane scroll = new JScrollPane();			
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportView(panel);
		
		getContentPane().add(scroll, BorderLayout.CENTER);
		
	    digStart = new JTextArea();	    
	    digStart.setWrapStyleWord(true);
	    digStart.setLineWrap(true);
	    digStart.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	    digStart.setForeground(Color.GREEN);
	    digStart.setBackground(Color.BLACK);
	    digStart.setColumns(50);	    
	    digStart.setEditable(false);
	   	    
	    panel.add(lb_bg);
	    panel.add(digStart);
								 	
	}
	
	public JMenuBar panelSup(int x, int y, int width, int hight){
								
		JMenuBar barSup = new JMenuBar();	
		barSup.setBounds(x, y, width, hight);
				 		
        barSup.add(menuFile());
        barSup.add(menuBatch());
        barSup.add(menuHelp());
		return barSup;
	}
	
	private JMenu menuFile(){
				
		JMenu menuFile = new JMenu("Archivo");		
		
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Cargar Imagen");
	    cargarImagen.setIcon(new ImageIcon(Index.class.getResource("/open.png")));
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			   
			   final JFileChooser fc = new JFileChooser();
			   fc.setFileFilter(new ImageFilter());
			   int returnVal = fc.showOpenDialog(fc);
			   // ==> Inicializar BarProgress ===			   
		       if (returnVal == JFileChooser.APPROVE_OPTION){
		    	    digStart.setText(null);		    	    
		    	    repaint();
		    	    // Note: Need it, to use "setLookAndFeel"
		    	    EventQueue.invokeLater(new Runnable() {
		    			public void run() {
		    				try {					
		    					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					    	    MainWindow hilo = new MainWindow();
					    	    hilo.start();
					    	 // Note: Need it, to wait for "run" and can do "dispose"
					    	    synchronized(hilo){	
						    	    try {			    	    	
										hilo.run(fc.getSelectedFile(), digStart);
										hilo.wait();
									} catch (MWException | InterruptedException e1) {							
										e1.printStackTrace();
									}	
					    	    }   
					    	    dispose();	    	    		        		     				             		    	    		             		        		      
		    				}catch (Exception e) { e.printStackTrace();}
		    			}
		    	    });					   
			   	
		       } //end of APPROVE_OPTION if
			}   
		});
		menuFile.add(cargarImagen); 
		
		final JMenuItem exitApp = new JMenuItem("Salir");
		exitApp.setIcon(new ImageIcon(Index.class.getResource("/exit.png")));
		exitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
			
				System.exit(0);
			}
		});
		menuFile.add(exitApp);
		
		return menuFile;
	}
 //==========================================================================================
  private JMenu menuHelp(){
	  
	  JMenu menuHelp = new JMenu("Ayuda");
		//==================== Load Image ===================================
		final JMenuItem welcome = new JMenuItem("Bienvenido");		
		welcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				panel.remove(lb_bg);			
	    	    repaint();					    	  
	    	    digStart.setText(welcomedigStart+"\n\n"+versiondigStart);
	    	    
			}
		});
		menuHelp.add(welcome); 
		
		JMenuItem about = new JMenuItem("Informacion");		
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				panel.remove(lb_bg);			
	    	    repaint();							
				digStart.setText(aboutdigStart);				
			}
		});
		menuHelp.add(about);
	  
	  return menuHelp;
  }
//==========================================================================================
  private JMenu menuBatch(){
	  
	  JMenu menuBatch = new JMenu("Por lote");
		//==================== Load Image ===================================
		JMenuItem loadDir = new JMenuItem("Cargar");		
		loadDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
					
				
				 final JFileChooser fc = new JFileChooser();				 			
				 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);				 
				 int returnVal = fc.showOpenDialog(fc);				 
			     if (returnVal == JFileChooser.APPROVE_OPTION){	
			    	    digStart.setText(null);
			    	    panel.remove(lb_bg);	    	    
			    	    repaint();
			    	    // Note: Need it, to use "setLookAndFeel"
			    	    EventQueue.invokeLater(new Runnable() {
			    			public void run() {
			    				try {					
			    					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
						    	    MainWindow hilo = new MainWindow();
						    	    hilo.start();
						    	 // Note: Need it, to wait for "run" and can do "dispose"
						    	    synchronized(hilo){	
							    	    try {			    	    	
											hilo.run(fc.getSelectedFile(), digStart);
											hilo.wait();
										} catch (MWException | InterruptedException e1) {							
											e1.printStackTrace();
										}	
						    	    }   
						    	    dispose();	    	    		        		     				             		    	    		             		        		      
			    				}catch (Exception e) { e.printStackTrace();}
			    			}
			    	    });					   
				   	
			       } //end of APPROVE_OPTION if	 
			    				      			    				 				 			   
			}	 			
		});
		menuBatch.add(loadDir); 

		JMenuItem segDir = new JMenuItem("Segmentar");		
		segDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("Segments a Directory ");									
			    				 				 			    
			}	 			
		});
		menuBatch.add(segDir);
	  return menuBatch;
  }
}
