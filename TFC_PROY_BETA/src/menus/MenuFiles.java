package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import procesos.MenuTools;

public class MenuFiles{
	

   /**
    * @return Menu de fichero
    */
   public static JMenu getMenu(){
		 
	 JMenu menu = new JMenu("File");
	 menu.setIcon(new ImageIcon(MenuFiles.class.getResource("/home.png")));
	 
		menu.setHorizontalAlignment(SwingConstants.LEFT);
		//==================== Load Image ===================================
		JMenuItem load = new JMenuItem("Load Image");	
		load.setIcon(new ImageIcon(MenuTools.class.getResource("/open.png")));
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
			    Abrir.run();			   
			}
		});
		menu.add(load); 
		
		menu.add(new JSeparator());			
		//==================== Save Image ===================================
		JMenuItem save = new JMenuItem("Save Image");
		save.setIcon(new ImageIcon(MenuTools.class.getResource("/save.png")));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				 
				Guardar.run();		    			    				    				    		
			}
		});		
		menu.add(save); // -> save menu
		
		//==================== Save como Image ===================================
		JMenuItem sComoImagen = new JMenuItem("Save como");
	 	sComoImagen.setIcon(new ImageIcon(MenuTools.class.getResource("/guardar_como.png")));
		sComoImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				 
				GuardarComo.run();		    			    				    				    		
			}
		});		
		menu.add(sComoImagen); // -> Save como menu
		
		menu.add(new JSeparator());
		//==================== Save como Image ===================================
		JMenuItem exitApp = new JMenuItem("Exit");
	 	exitApp.setIcon(new ImageIcon(MenuTools.class.getResource("/exit.png")));
		exitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				 
				System.exit(0);		    			    				    				    		
			}
		});		
		menu.add(exitApp); // -> Exit
		
	 return menu;
   }
      
}