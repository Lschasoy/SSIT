import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;



public class Archivos {
	
	private JFileChooser fc;
	private String nameImage;
	
	
	public void setImageName (String nameI){
		nameImage = nameI;
	}
	
	public String getImageName () { return nameImage; }

//=================================================================================	
	public BufferedImage loadFile(){
		
	
		 fc = new JFileChooser();
		 int returnVal = fc.showOpenDialog(fc);
         if (returnVal == JFileChooser.APPROVE_OPTION){
        	 File imagen = fc.getSelectedFile();
        	 setImageName(imagen.getName());
        	 try {	                		
				return ImageIO.read(imagen);
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//cargar imagen vacia
			}

         }
		return null;
	}
//=================================================================================	
	public void saveFile(){
		
		fc = new JFileChooser();
		fc.setDialogTitle("Specify a file to save");   
		 
		int userSelection = fc.showSaveDialog(fc);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fc.getSelectedFile();
		  
		}
	}

}
