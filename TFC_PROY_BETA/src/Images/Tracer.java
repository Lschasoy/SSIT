package Images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JPanel;

import main.Info;
import main.MainWindow;


public class Tracer {
	
    // Guardamos solo seis
	private static ArrayList<Image> pila = new ArrayList();
	private static int head = -1;
	
	//============================================================================
    public static void insert(File file, BufferedImage img, boolean save, JPanel[] canales) {
    	head++;
  		pila.add(new Image(file, img , save, canales));
  		Info.msg("",file.getName(), img);  	
  		MainWindow.mostrar(pila.get(head));
  		
  		
  	}
    public static void insert(String name, BufferedImage img) {
    	head++;
    	File file = new File(name);
  		pila.add(new Image(file, img , true, MainWindow.canales));
  		Info.msg(name,pila.get(head).name, img);  	
  		MainWindow.mostrar(pila.get(head));  		  		
  	}
    public static void  getLast() {     	    	
    	MainWindow.mostrar(pila.get(head));
  	}
    public static Image getFirst() {
    	head = 0;
    	return pila.get(head);
  	}
    public static Image top() {
    	return pila.get(head); 
    }
	//============================================================================
}