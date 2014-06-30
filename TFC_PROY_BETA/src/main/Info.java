package main;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Info extends JPanel {
	
	String[] etiquetas = {"OPER: ","","NAME: ","","IMAGE: ",""};
	JLabel[] labels = {null, null, null,null, null, null};
	
	
	Font font1 = new Font("Serif", Font.BOLD, 11);
	Font font2 = new Font("Serif", Font.PLAIN, 11);
//==========================================================================	
	public Info(){
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
//==========================================================================	
	public void msg(int opcion, String file, BufferedImage img){
		
		labels[1].setText("load");
		labels[3].setText(file);
		labels[5].setText("Tipo = " + img.getType() + " Alto = " + img.getHeight() + " Ancho = " + img.getWidth());
	
	}
}
