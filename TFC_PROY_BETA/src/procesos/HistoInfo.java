package procesos;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HistoInfo extends JPanel{

					
	private static String[] etiquetas = {"Color: ", "", "Columna:", "", "Valor:", "", " Canal", ""};
	private static JLabel[] labels = {null, null, null,null, null, null, null, null};
	

	private static Font font1 = new Font("Serif", Font.BOLD, 11);	
	private static Font font2 = new Font("Serif", Font.PLAIN, 11);
	
	private static BufferedImage img;
	private static ImageIcon ico;
	
	//==========================================================================	
	public HistoInfo(){
		
		 this.setBackground(Color.WHITE);
		 img = new BufferedImage(15,15, BufferedImage.TYPE_INT_RGB); 
		 ico = new ImageIcon();
		 ico.setImage(img);
		 
		
		// ========= Inicializamos ==================
		for (int i = 0; i < etiquetas.length; i++)
			labels[i] = new JLabel(etiquetas[i]);
		
	    // ====== Cargamos en el JPanel =========== 									
		for (int i = 0; i < labels.length; i++){
			if ((i % 2) == 0)
				labels[i].setFont(font2);
			else
				labels[i].setFont(font1);
			add(labels[i]);
		}
		
	}
	
	public static void posXY(String msg1, String msg2, int color, Color colorBarras){
		
		labels[3].setText(msg1);
		labels[5].setText(msg2);		
		
		for (int i = 0; i < img.getHeight(); i++){
			for (int j = 0; j < img.getWidth(); j++){
				if (colorBarras.getRed() != 0) img.setRGB(i, j, new Color(color,0,0).getRGB()); 
				if (colorBarras.getGreen() != 0) img.setRGB(i, j, new Color(0,color,0).getRGB());
				if (colorBarras.getBlue() != 0) img.setRGB(i, j, new Color(0,0,color).getRGB());
				
			}
		}
		labels[1].setIcon(ico);
	}
	public static boolean clicked(int columna, int valor,  Color colorBarras){
		
		if (colorBarras.getRed() != 0) labels[7].setText("RED"); 
		if (colorBarras.getGreen() != 0) labels[7].setText("GREEN");
		if (colorBarras.getBlue() != 0) labels[7].setText("BLUE");
				
		labels[etiquetas.length -2].setText(null);
		labels[etiquetas.length -1].setIcon(null);									
		
		String cad = " Resaltar el color " + columna + " del canal " + labels[7].getText() 
				   + " con " + valor + " pixel en la imagen actual"; 
		 
		int n = JOptionPane.showConfirmDialog(null,cad, "¿Desea colorear la imagen?", JOptionPane.YES_NO_OPTION);
		if (n == 0) return true;
		else return false;			
						
	}
	
}
