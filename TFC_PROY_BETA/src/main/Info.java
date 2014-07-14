package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Info extends JPanel {
	
	private static String[] etiquetas = {"OPER: ","","NAME: ","","IMAGE: ","", " ",""};
	private static JLabel[] labels = {null, null, null,null, null, null, null, null};
	

	private static Font font1 = new Font("Serif", Font.BOLD, 11);	
	private static Font font2 = new Font("Serif", Font.PLAIN, 11);
	//==========================================================================	
	public Info(){
		this.setBackground(Color.WHITE);
		// ========= Inicializamos ==================
		for (int i = 0; i < etiquetas.length; i++)
			labels[i] = new JLabel(etiquetas[i]);
		
	    // ====== Cargamos en el JPanel =========== 									
		for (int i = 0; i < labels.length; i++){
			if ((i % 2) == 0)
				labels[i].setFont(font2);
			else
				labels[i].setFont(font1);
			
			labels[etiquetas.length -2].setFont(font1); // --> Para Pox
			add(labels[i]);
		}
	}
//==========================================================================	
	public static void msg(String msg, String file, BufferedImage img){
		
		labels[1].setText(msg);
		labels[3].setText(file);
		labels[5].setText("Tipo = " + img.getType() + " Alto = " + img.getHeight() + " Ancho = " + img.getWidth());					
	}
	public static void posXY(String msg1, String msg2){
		labels[etiquetas.length -2].setText(msg1);
		labels[etiquetas.length -1].setText(msg2);
	}
	public static void msgOut(String msg1, String msg2){
		labels[etiquetas.length -2].setFont(font1);
		labels[etiquetas.length -2].setBackground(Color.BLACK);
		labels[etiquetas.length -2].setText(msg1);
		labels[etiquetas.length -1].setText(msg2);
	}
	public static void msgError(String msg1, String msg2){
		
		labels[etiquetas.length -2].setText(msg1);
		labels[etiquetas.length -2].setBackground(Color.RED);
		labels[etiquetas.length -1].setText(msg2);
	}
	
}
