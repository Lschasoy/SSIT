package matlab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.image.BufferedImage;

import matlabToJavaSC.*;

import javax.swing.JProgressBar;

import com.mathworks.toolbox.javabuilder.Images;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;


public class ColorSpace extends JDialog {

	private BufferedImage imgHsv;
    private String cmd = null;
	
 
	
   //========================================================================================
	public BufferedImage toImgHsv(Espacios esp, String path, JTextArea panelCMD) {
		try {
		    cmd = "\nRun ColorSpace ...... param: Espacio, String, JTextArea";				
            Object[] result = esp.convertRgb2hsv(2, path);
            cmd += "\nFinalizado convertRgb2hsv ................ ok";
			imgHsv = getBufferedImageFromDeployedComponent(result[1]);
			cmd += "\nFinalizado getBufferedImageFromDeplyedComponent";
			kill(result); // -> Kill result							
			cmd += "\nkill of tmp Object ..................";
			
		} catch (MWException e) {		
			cmd = "\nRun ColorSpace: [ERROR] \n";
			panelCMD.append(cmd);
		}		
		cmd += "\n.... End of ColorSpace .................. ok\n";
		panelCMD.append(cmd);
		return imgHsv;
    }
	   //========================================================================================
   	public BufferedImage toImglab(Espacios esp, String path, JTextArea panelCMD) {
   		try {
   		    cmd = "\nRun ColorSpace ...... param: Espacio, String, JTextArea";				
               Object[] result = esp.convertRgb2lab(2, path);
               cmd += "\nFinalizado convertRgb2hsv ................ ok";
   			imgHsv = getBufferedImageFromDeployedComponent(result[1]);
   			cmd += "\nFinalizado getBufferedImageFromDeplyedComponent";
   			kill(result); // -> Kill result							
   			cmd += "\nkill of tmp Object ..................";
   			
   		} catch (MWException e) {		
   			cmd = "\nRun ColorSpace: [ERROR] \n";
   			panelCMD.append(cmd);
   		}		
   		cmd += "\n.... End of ColorSpace .................. ok\n";
   		panelCMD.append(cmd);
   		return imgHsv;
       }
    //========================================================================================
   	public BufferedImage toImgYCbCr(Espacios esp, String path, JTextArea panelCMD) {
   		try {
   		    cmd = "\nRun ColorSpace ...... param: Espacio, String, JTextArea";				
               Object[] result = esp.convertRgb2ycbcr(2, path);
               cmd += "\nFinalizado convertRgb2hsv ................ ok";
   			imgHsv = getBufferedImageFromDeployedComponent(result[1]);
   			cmd += "\nFinalizado getBufferedImageFromDeplyedComponent";
   			kill(result); // -> Kill result							
   			cmd += "\nkill of tmp Object ..................";
   			
   		} catch (MWException e) {		
   			cmd = "\nRun ColorSpace: [ERROR] \n";
   			panelCMD.append(cmd);
   		}		
   		cmd += "\n.... End of ColorSpace .................. ok\n";
   		panelCMD.append(cmd);
   		return imgHsv;
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