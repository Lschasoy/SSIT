import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;



public class Archivos {

	public Archivos() {
		// TODO Auto-generated constructor stub
	}
	
	public BufferedImage loadFile(){
		
	
		 JFileChooser fc = new JFileChooser();
		 int returnVal = fc.showOpenDialog(fc);
         if (returnVal == JFileChooser.APPROVE_OPTION){
        	 File imagen = fc.getSelectedFile();
        	 
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

}
