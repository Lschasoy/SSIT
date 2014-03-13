import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Ventana extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JPanel Npanel, ImagActPanel, ImagFinalPanel, centralPanel;
	private JButton seleccionar, girarIZQ, girarDCH, ZoomPlus, ZoomMinus, recortar;
	private JButton cargar, salvar, salir;

	public  BufferedImage originalImage, finalImage;
	
	public  double degree = 45;

	final ZoomPanel zoom = new ZoomPanel();
	private float xScaleFactor = 1;
	private float yScaleFactor = 1;

	Rectangle selection;
    Point anchor;
    


	/*************************************************/
	public JPanel Panel_SUP(){

		Npanel = new JPanel();
		Npanel.setLayout(new BoxLayout(Npanel, BoxLayout.X_AXIS));
		Npanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 5, 5));
		Npanel.setBackground(Color.LIGHT_GRAY);

		cargar = new JButton("LOAD");
	    cargar.addActionListener( this);
		cargar.setMaximumSize(cargar.getMaximumSize());


		salvar = new JButton("SAVE");		
		salvar.addActionListener( this);
		salvar.setMaximumSize(salvar.getMaximumSize());

		Npanel.add(cargar);	
		Npanel.add(salvar);	

		return Npanel;
	}
	/*************************************************/
	public JPanel Panel_IZQ(){

		Npanel = new JPanel();
		Npanel.setLayout(new BoxLayout(Npanel, BoxLayout.X_AXIS));
		Npanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 25, 25));

		Npanel.setBackground(Color.LIGHT_GRAY);

		seleccionar = new JButton("Seleccionar");
		seleccionar.addActionListener(this);
		seleccionar.setMaximumSize(seleccionar.getMaximumSize());


		ZoomPlus = new JButton ("ZOOM ++");
		ZoomPlus.addActionListener(this);
		ZoomPlus.setMaximumSize(seleccionar.getMaximumSize());

		ZoomMinus = new JButton ("ZOOM --");
		ZoomMinus.addActionListener(this);
		ZoomMinus.setMaximumSize(seleccionar.getMaximumSize());

		girarIZQ = new JButton ("Girar IZQ");
		girarIZQ.addActionListener(this);
		girarIZQ.setMaximumSize(seleccionar.getMaximumSize());

		girarDCH = new JButton ("Girar DCH");
		girarDCH.addActionListener(this);
		girarDCH.setMaximumSize(seleccionar.getMaximumSize());


		recortar = new JButton ("Recortar");
		recortar.addActionListener(this);
		recortar.setMaximumSize(seleccionar.getMaximumSize());

		Npanel.add(seleccionar);
		Npanel.add(ZoomMinus);
		Npanel.add(ZoomPlus);
		Npanel.add(girarDCH);
		Npanel.add(girarIZQ);
		Npanel.add(recortar);

		return Npanel;
	}

	/*************************************************/
	public void actionPerformed(ActionEvent e) {

		  if( e.getSource() == seleccionar){
			  System.out.println("Girar Imagen");

		  }
		  if( e.getSource() == ZoomMinus){
			  System.out.println("Zoom ++ Imagen");

			      xScaleFactor -= 0.1;
				  yScaleFactor -= 0.1;

				  ImagFinalPanel.removeAll();
				  setVisible(true);

				  finalImage = ZoomPanel.Zoom(originalImage, yScaleFactor, xScaleFactor);


				  ImageIcon ico = new ImageIcon(finalImage);
				  JLabel label = new JLabel (ico);													
				  ImagFinalPanel.add(label);
				  setVisible(true);

			  }

		  if( e.getSource() == ZoomPlus){
			  System.out.println("Zoom ++ Imagen");

			      xScaleFactor += 0.1;
				  yScaleFactor += 0.1;

				  ImagFinalPanel.removeAll();
				  finalImage = ZoomPanel.Zoom(originalImage, yScaleFactor, xScaleFactor );


				  ImageIcon ico = new ImageIcon(finalImage);
				  JLabel label = new JLabel (ico);													
				  ImagFinalPanel.add(label);
				  setVisible(true);

			  }
			  if( e.getSource() == girarIZQ){


				  System.out.println("Girar Imagen");
				  degree -= 45;				 
				  RotatedPanel rot = new RotatedPanel();			
				  finalImage = rot.Rotar(originalImage, degree);

				  ImagFinalPanel.removeAll();
				  ImageIcon ico = new ImageIcon(finalImage);
				  JLabel label = new JLabel (ico);													
				  ImagFinalPanel.add(label);										             			      			   			      
				  setVisible(true);

			  }

             if( e.getSource() == girarDCH){


				  System.out.println("Girar Imagen");
				  degree += 45;				
				  RotatedPanel rot = new RotatedPanel();			
				  finalImage = rot.Rotar(originalImage, degree);				  
				  ImagFinalPanel.removeAll();													  
				  ImageIcon ico = new ImageIcon(finalImage);
				  JLabel label = new JLabel (ico);													
				  ImagFinalPanel.add(label);									             			      
				  setVisible(true);				
			  }

			  if( e.getSource() == recortar){
				  System.out.println("Recortar Imagen");
			  }
			  if( e.getSource() == cargar){

				    ImagActPanel.removeAll();
				    Archivos arc = new Archivos();
				    originalImage = arc.loadFile();
				    // Aqui para cargar la imagen para los Algoritmo


					ImageIcon ico = new ImageIcon(originalImage);				
					JLabel label = new JLabel (ico);													
					ImagActPanel.add(label);							
				    setVisible(true);

			  }
			  if( e.getSource() == salvar){
				  System.out.println("Salvar Imagen");

			  }
			  if( e.getSource() == salir){
				  System.exit(0);
			  }
	}

	/*************************************************/
	public Ventana (){

		ImagActPanel = new JPanel();			
		ImagActPanel.setBackground(Color.WHITE);
		ImagActPanel.setAutoscrolls(true);

		centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(1,2));

		ImagFinalPanel =  new JPanel();
		ImagFinalPanel.setBackground(Color.BLACK);
		ImagFinalPanel.setAutoscrolls(true);

		centralPanel.add(ImagActPanel);					
	    centralPanel.add(ImagFinalPanel); 

	    add(Panel_SUP(), BorderLayout.NORTH);
	    add(Panel_IZQ(), BorderLayout.SOUTH);
	    add(centralPanel,BorderLayout.CENTER);

	}	
}