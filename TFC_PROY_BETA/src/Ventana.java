import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Ventana extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel Npanel, ImagActPanel, ImagFinalPanel, centralPanel;
	private JButton seleccionar, girar, escalar, recortar, cargar, salvar, salir;
		
	public BufferedImage originalImage;
	private int returnVal;
	

	
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
		
		
		escalar = new JButton ("Escalar");
		escalar.addActionListener(this);
		escalar.setMaximumSize(seleccionar.getMaximumSize());
		
		girar = new JButton ("Girar");
		girar.addActionListener(this);
		girar.setMaximumSize(seleccionar.getMaximumSize());
		
		recortar = new JButton ("Recortar");
		recortar.addActionListener(this);
		recortar.setMaximumSize(seleccionar.getMaximumSize());

		Npanel.add(seleccionar);
		Npanel.add(escalar);
		Npanel.add(girar);
		Npanel.add(recortar);
		
		return Npanel;
	}
	
	/*************************************************/
	public void actionPerformed(ActionEvent e) {
		  if( e.getSource() == seleccionar){
			  
				 System.out.println("Seleccionar Imagen");
			
			  }
			  if( e.getSource() == escalar){
				  System.out.println("Escalar Imagen");		
				  ZoomPanel zoom = new ZoomPanel(originalImage);
				  JFrame frame = new JFrame("Zoom a imagen");
				  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
				  
				  frame.add(zoom);
				  frame.setPreferredSize(new Dimension(680,420));
				 
				  frame.pack();
				  frame.setVisible(true);
				  setLocationRelativeTo( null );
			  }
			  if( e.getSource() == girar){
				  System.out.println("Girar Imagen");
			  }
			  if( e.getSource() == recortar){
				  System.out.println("Recortar Imagen");
			  }
			  if( e.getSource() == cargar){
					 JFileChooser fc = new JFileChooser();
					 returnVal = fc.showOpenDialog(this);
	                 if (returnVal == JFileChooser.APPROVE_OPTION){
	                	 File imagen = fc.getSelectedFile();
	                	 ImagActPanel.removeAll();
	                	 try {	                		
							originalImage = ImageIO.read(imagen);
							ImageIcon ico = new ImageIcon(originalImage);
							JLabel label = new JLabel (ico);													
							ImagActPanel.add(label);							
						    setVisible(true);
						    
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							//cargar imagen vacia
						}
	   
	                 }
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
		
		
		setTitle("Proyecto fin de grado");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize( 1024, 680 );
		setLocationRelativeTo( null );				
		
		centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(1,2));
		
		ImagActPanel = new JPanel();			
		ImagActPanel.setBackground(Color.WHITE);
		ImagActPanel.setAutoscrolls(true);
	
		
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