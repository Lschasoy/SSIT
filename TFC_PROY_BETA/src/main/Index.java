package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import Images.ImageFilter;

import java.io.File;


import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.FlowLayout;




public class Index extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int FULL_PROGRESS = 1000;
	
	private JTextArea digStart;	
	private JProgressBar progressBar;
	
	private String welcomedigStart = " Segemtation Tools: Es una interfaz de usuario java"
			                +" para la segmentacion de imagenes de forma fácil, "
			                +"implementa funciones de matlab para generar las imagenes"
			                + "segmentadas"; 
	private String versiondigStart = "Version 1.0 - Primera revisión";
	
	private String aboutdigStart = "\n  Segmentation Tools: 1.0\n"
			                  +"  Autor: Ing Informatico Lschasoy\n"
			                  +"  Last Review: Mayo - 2014";
	 
	private File [] files = {}; 
	/**
	 * Launch the application.
	 */
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
	    
	    progressBar = new JProgressBar();
	    progressBar.setValue(0);		
		progressBar.setMaximum(FULL_PROGRESS);
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
					    	   
		setTitle("Segmentation Tools - ULL - TFG");
		setBounds(0, 0, 400, 250);
		
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelSup(0,0,400,25), BorderLayout.NORTH);
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		getContentPane().add(panel, BorderLayout.CENTER);
		
	    digStart = new JTextArea();
	    panel.add(digStart);
	    digStart.setRows(4);
	    digStart.setWrapStyleWord(true);
	    digStart.setLineWrap(true);
	    digStart.setEditable(false);
	    digStart.setBackground(Color.WHITE);	    
	    digStart.setBounds(15,30,300,220);
	    digStart.setText("Segemtation Tools: Es una interfaz de usuario java para la segmentacion de imagenes de forma f\u00E1cil, implementa funciones de MATLAB para generar las imagenes segmentadas\r\n\r\n  Version 1.0 - Primera revisi\u00F3n");
								 	
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
				
		JMenu menuFile = new JMenu("File");
		
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Load Image");
		cargarImagen.setIcon(new ImageIcon("image\\open.png", "LOAD"));
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			   System.out.println("Load single Image: ");
			   JFileChooser fc = new JFileChooser();
			   fc.setFileFilter(new ImageFilter());
			   int returnVal = fc.showOpenDialog(fc);
			   // ==> Inicializar BarProgress ===			   
		       if (returnVal == JFileChooser.APPROVE_OPTION){
		    	    progressBar.setVisible(true);
		    	    MainWindow.main(fc.getSelectedFile(), digStart, progressBar);	
		    		dispose();	    	    		        		     				             		    	    		             		        		      
		       }
			   
			  // => FIN 	
			}
		});
		menuFile.add(cargarImagen); 
		
		JMenuItem exitApp = new JMenuItem("EXIT");		
		exitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("EXIT Image: ");				   
			}
		});
		menuFile.add(exitApp);
		
		return menuFile;
	}
 //==========================================================================================
  private JMenu menuHelp(){
	  
	  JMenu menuHelp = new JMenu("Help");
		//==================== Load Image ===================================
		final JMenuItem welcome = new JMenuItem("Welcome");		
		welcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("Welcome ");					
				digStart.setText(aboutdigStart);
				getContentPane().add(digStart,BorderLayout.CENTER);
				setVisible(true);
			}
		});
		menuHelp.add(welcome); 
		
		JMenuItem about = new JMenuItem("About");		
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("About us ");				
				digStart.setText(welcomedigStart+"\n\n"+versiondigStart);
				getContentPane().add(digStart,BorderLayout.CENTER);
				setVisible(true);
				
			}
		});
		menuHelp.add(about);
	  
	  return menuHelp;
  }
//==========================================================================================
  private JMenu menuBatch(){
	  
	  JMenu menuBatch = new JMenu("Batch");
		//==================== Load Image ===================================
		JMenuItem loadDir = new JMenuItem("Load Directory");		
		loadDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("load a Directory ");	
				
				 JFileChooser fc = new JFileChooser();				 			
				 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 
				 int returnVal = fc.showOpenDialog(fc);				 
			     if (returnVal == JFileChooser.APPROVE_OPTION){			    	
			    	 MainWindow.main(fc.getSelectedFile(), digStart, progressBar);  
		        	 dispose();
			     }	 
			    				 				 			    
			}	 			
		});
		menuBatch.add(loadDir); 

		JMenuItem segDir = new JMenuItem("Segments a Directory");		
		segDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("Segments a Directory ");	
				
				 JFileChooser fc = new JFileChooser();				 			
				 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 
				 int returnVal = fc.showOpenDialog(fc);				 
			     if (returnVal == JFileChooser.APPROVE_OPTION){			    	
			    	 MainWindow.main(fc.getSelectedFile(),digStart, progressBar); 			    	 
		        	 dispose();
			     }	 
			    				 				 			    
			}	 			
		});
		menuBatch.add(segDir);
	  return menuBatch;
  }
}
