package matlab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;

import matlabToJavaSC.*;

import javax.swing.JProgressBar;

import com.mathworks.toolbox.javabuilder.Images;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;


public class ColorSpace extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar = new JProgressBar(0,100);
	private BufferedImage imgHsv;
	private Espacios esp;
	
	public BufferedImage getImgHsv() {return imgHsv;}

	public ColorSpace(String path) {
		setBounds(250, 250, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{	
			
			contentPanel.add(progressBar);
			setVisible(true);
			progressBar.setValue(25);
			try {
				esp = new Espacios();
				System.out.println("ColorSpace: "+esp.toString());
                Object[] result = esp.convertRgb2hsv(2, path);
				progressBar.setValue(50);
				imgHsv = getBufferedImageFromDeployedComponent(result[1]);
				progressBar.setValue(75);				
			
				kill(result); // -> Kill result			
				progressBar.setValue(100);
				
			} catch (MWException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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