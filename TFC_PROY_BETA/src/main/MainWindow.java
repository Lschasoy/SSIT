package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JPanel;

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

import matlab.*;
import matlabToJavaSC.Espacios;
import matlabToJavaSC.Segmentacion;
import menus.Menus;

import procesos.Canales;
import procesos.HistoInfo;
import tools.Menu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;
import java.awt.Font;


public class MainWindow {
	
	public static JFrame miVentana;

	public static JPanel [] canales = {new JPanel(), new JPanel(), new JPanel(), new JPanel()};  
	public static CloseableTabbedPane jTP;
	public static JFileChooser chooser;
	
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
					new MainWindow(file, digStart, progressBar);
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
		miVentana.getContentPane().add(Menu.getMenu(),BorderLayout.WEST);
		miVentana.getContentPane().add(jTP);				
		miVentana.getContentPane().add(pFooter(), BorderLayout.PAGE_END);		
		
		//miVentana.setResizable(false);
		miVentana.setVisible(true);
		
	}
//===================================================================================================================	
	public MainWindow(File file, JTextArea msg, JProgressBar progressBar) throws MWException {
		
		
		chooser =  new JFileChooser(); progressBar.setValue(100);
		msg.append("  Instanciando clase de manipulacion de archivo: .......... ok\n");progressBar.setValue(200);
		msg.append("  Instanciando Histogramas y canales: .......... ok\n");progressBar.setValue(300);
		esp = new Espacios();
		msg.append("  Instanciando Espacios de colores: .......... ok\n");progressBar.setValue(400);
		fun = new Segmentacion();
		msg.append("  Cargando lanzadores de funciones de segementacion: .......... ok\n");progressBar.setValue(500);
		espColor = new ColorSpace();
		msg.append("  Cargando funciones de espacio de color .......... ok\n");
		jTP = new CloseableTabbedPane(); 		
		jtpMouseClick();	// --> Mouse click pestaña	
		msg.append("  Instanciando las pestañas: .......... ok\n");progressBar.setValue(600);
		
		info = new Info();		
		msg.append("  Instanciando clase de informacion: .......... ok\n");progressBar.setValue(700);
		hInfo = new HistoInfo();			
		msg.append("  Informacion de Histograma: .......... ok\n");progressBar.setValue(700);		
		miVentana = new JFrame(); initialize();
	    msg.append(" Configurando ventana principal : .......... ok\n");progressBar.setValue(900);	
		
	    new Menus();
	    msg.append(" Inicializar todos los menus : .......... ok\n");
	    
		msg.append("  Load Imagen: .......... ok\n");
		try {
			if (file.isFile()){			   
			   Tracer.insert(file,ImageIO.read(file),true, canales);			   			  
			   
			}else{
				File []list = file.listFiles();
				ImageFilter imgFiltro= new ImageFilter();	// -> Filtros	
		    	 for (int i = 0; i < list.length; i++ ){
		    		if (imgFiltro.accept(list[i]) && (list[i].isFile())){		    			
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
		
		JPanel divFooter = new JPanel(new BorderLayout());
		
		JPanel divInfo = new JPanel();
		divInfo.setLayout(new GridLayout(1,2));
		
		divInfo.add(info);   // --> Informacion de la imagen
		divInfo.add(hInfo);  // --> Informacion del histograma
		
		divFooter.add(divInfo, BorderLayout.NORTH);
		
		
	    //====> Panel de la parte central <=============================== 			    	
	    final JPanel pFooter = new JPanel(new GridLayout(0,4));
	    pFooter.setMaximumSize(new Dimension(1100, 165));
	    pFooter.setPreferredSize(new Dimension(1100, 165));
	    pFooter.setMinimumSize(new Dimension(100, 165));	
	    
	    for (int i = 0; i < 4; i++) 
	    	pFooter.add(canales[i]);  // --> Añadiños los 4 canales	    		
	    
	    divFooter.add(pFooter, BorderLayout.CENTER);
	    
		return divFooter;
	}
	
	public JMenuBar panelSup(){
						
		
		JMenuBar barSup = new JMenuBar();					
		barSup.setFont(new Font("Segoe UI", Font.BOLD, 12));
		barSup.setMargin(new Insets(2, 0, 2, 0));
		
								
		// ========== Carga de todos los menus ==========================
        barSup.add(Menus.getMenu());           // ==> File Menu        
        barSup.add(FormSegment.getMenu(fun));  // ==> Segmetacion Menu
        barSup.add(espColor.getMenu(esp));     // ==> Color's Space Menu
        barSup.add(Canales.getMenu());     // ==> Color's Space Menu
           
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
	//========================================================================================================
	public static void jtpMouseClick (){
		jTP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getCurrentImage().canales(canales);
			}
		});		
	}
	//===========================================================================================================
	public static String getPath (){ return getCurrentImage().getFileOriginal().getAbsolutePath().toString(); }
	public static File getFile () {return getCurrentImage().getFileOriginal();}
	
	public static Image getCurrentImage() {return getImage(jTP.getSelectedIndex());}
	public static void removeCurrentImage() {jTP.remove(jTP.getSelectedIndex());}		
}