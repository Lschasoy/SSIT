package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JTextPane;

import Images.ImageFilter;

import java.io.File;



public class Index extends JDialog {

	
	private JLabel img_bg;
	private JTextPane Msg;
	private String welcomeMsg = " Segemtation Tools: Es una interfaz de usuario java"
			                +" para la segmentacion de imagenes de forma fácil, "
			                +"implementa funciones de matlab para generar las imagenes"
			                + "segmentadas"; 
	private String versionMsg = "Version 1.0 - Primera revisión";
	
	private String aboutMsg = "\n  Segmentation Tools: 1.0\n"
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
	     Msg = new JTextPane();
		
		setTitle("Segmentation Tools - ULL - TFG");
		getContentPane().setBackground(UIManager.getColor("Button.light"));
		setBounds(100, 100, 300, 200);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelSup(0,0,500,25), BorderLayout.NORTH);	
		
		img_bg = new JLabel();
		img_bg.setIcon(new ImageIcon("image\\bg_index.jpg", "index"));
		
		
		getContentPane().add(img_bg, BorderLayout.CENTER);
		
		
		
	}
	
	public JMenuBar panelSup(int x, int y, int width, int high){
						
		
		JMenuBar barSup = new JMenuBar();	
		barSup.setBounds(0, 0, 562, 25);
		
		 			
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
		       if (returnVal == JFileChooser.APPROVE_OPTION){		    	     
		        	 MainWindow.main(fc.getSelectedFile());  
		        	 dispose();
		       }
			   
				
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
				getContentPane().remove(img_bg);
				Msg.setText(aboutMsg);
				getContentPane().add(Msg,BorderLayout.CENTER);
				setVisible(true);
			}
		});
		menuHelp.add(welcome); 
		
		JMenuItem about = new JMenuItem("About");		
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
				System.out.println("About us ");
				getContentPane().remove(img_bg);
				Msg.setText(welcomeMsg+"\n\n"+versionMsg);
				getContentPane().add(Msg,BorderLayout.CENTER);
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
			    	 MainWindow.main(fc.getSelectedFile());  
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
			    	 MainWindow.main(fc.getSelectedFile());  
		        	 dispose();
			     }	 
			    				 				 			    
			}	 			
		});
		menuBatch.add(segDir);
	  return menuBatch;
  }
}
