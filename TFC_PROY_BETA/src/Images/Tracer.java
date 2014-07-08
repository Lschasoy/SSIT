package Images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import main.Info;
import main.MainWindow;


public class Tracer {
	
    // Guardamos solo seis
	private static ArrayList<Image> pila = new ArrayList<Image>();
	private static int head = -1;
	
	//============================================================================
    public static void insert(File file, BufferedImage img) {
    	    	
    	head++;
    	if (file.exists())
    		pila.add(new Image(file, img , true, MainWindow.canales));
    	else
    		pila.add(new Image(file, img , false, MainWindow.canales));
    	
  		Info.msg("",file.getName(), img);  	
  		MainWindow.mostrar(pila.get(head));  		  		
  	}
    
    public static void change(String name, BufferedImage img){
    	MainWindow.getCurrentImage();
    	MainWindow.removeCurrentImage();
    	insert(name, img);    	
    }
    
    public static void insert(String name, BufferedImage img) {
    	head++;    	
  		pila.add(new Image(null, img , false, MainWindow.canales));
  		Info.msg(name,pila.get(head).name, img);  	
  		MainWindow.mostrar(pila.get(head));  
  		System.out.println("Insertado en tracer: "+ head);
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