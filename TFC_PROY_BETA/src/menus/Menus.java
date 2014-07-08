package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Menus{
	

   /**
    * @return Menu de fichero
    */
   public static JMenu getMenu(){
		 
	 JMenu menu = new JMenu("File");
	 JSeparator sep = new JSeparator(); 
		menu.setHorizontalAlignment(SwingConstants.LEFT);
		//==================== Load Image ===================================
		JMenuItem cargarImagen = new JMenuItem("Load Image");
		cargarImagen.setIcon(new ImageIcon("image\\open.png", "LOAD"));
		cargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								   				   				   				 
			    Abrir.run();			   
			}
		});
		menu.add(cargarImagen); 
		
		menu.add(sep);			
		//==================== Save Image ===================================
		JMenuItem salvarImagen = new JMenuItem("Save Image");
	 	salvarImagen.setIcon(new ImageIcon("image\\save.png", "SAVE"));
		salvarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				 
				Guardar.run();		    			    				    				    		
			}
		});		
		menu.add(salvarImagen); // -> save menu
		
		//==================== Save como Image ===================================
		JMenuItem sComoImagen = new JMenuItem("Save como");
	 	//sComoImagen.setIcon(new ImageIcon("image\\save.png", "SAVE"));
		sComoImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				 
				GuardarComo.run();		    			    				    				    		
			}
		});		
		menu.add(sComoImagen); // -> Save como menu
		
		menu.add(sep);
		//==================== Save como Image ===================================
		JMenuItem exitApp = new JMenuItem("Exit");
	 	//exitApp.setIcon(new ImageIcon("image\\save.png", "SAVE"));
		exitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				 
				System.exit(0);		    			    				    				    		
			}
		});		
		menu.add(exitApp); // -> Exit
		
	 return menu;
   }
      
}