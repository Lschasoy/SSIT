package matlab;

import java.awt.image.BufferedImage;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import com.mathworks.toolbox.javabuilder.Images;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import matlabToJavaSC.Segmentacion;

public class Funciones extends Thread{
    
    private Object[] result = null; 
	private BufferedImage imgOut;
	
	
	
    public BufferedImage getImgOut() {
		return imgOut;
	}
	
//========================================================================================
   public void run (Segmentacion seg, String path, Object[] param, JProgressBar progressBar, JTextArea traza) {

	   traza.append("\nrun segmentation of " + path + "\n");
	   
	   try {
		    synchronized(this){
		    	result = seg.segment(2, path, param, "ms");
		        notify();
	        }       
            traza.append("\tfinish segmentation: ........  ok\n");
            
			imgOut = getBufferedImageFromDeployedComponent(result[0]);			
		    traza.append("\tfinish getBufferedImageFromDeployedComponent: ........  ok\n");
		    
			kill(result); // -> Kill result							
			traza.append("\tfinish Kill tmp result: ........  ok\n");
				
		}catch (MWException e) {		
   			System.err.println("[Error]: run segmentation");
   		}	
    }
//========================================================================================
 	public void kill (Object[] result){
 		
        for (int i = 0; i < result.length; i++) {
 			MWNumericArray.disposeArray(result[i]);
 		}
 	}
 //========================================================================================  
   public static BufferedImage getBufferedImageFromDeployedComponent(Object result)
	{
	        BufferedImage resImg = Images.renderArrayData((MWNumericArray)result);
	        return resImg;
	}
}
