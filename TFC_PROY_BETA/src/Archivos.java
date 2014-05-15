import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTable;



public class Archivos {
	
	private static JFileChooser fc;
	private static String nameImage;
	
//======================================================================================================================
	public static void setImageName (String nameI){ nameImage = nameI;}	
	public String getImageName  ()         { return nameImage; }
	public JFileChooser getFile ()         {return fc;}

//======================================================================================================================
	
	public File loadImage(int row, int col, JTable tablaMenuImage, MyTableModel modelo) throws IOException{
		
		
		if ( row >= 0 && col >= 0 ) 
		{   
		//si celda contiene imagen
		   if( modelo.getValueAt(row, col) != null ){		    
		      return new File( modelo.getValueAt(row, col).toString() );		       
		      	
		    }                             
        }
	    return null;
	}

    /**
     * @param tablaMenuImage
     * @param modelo: Es una matrix que contiene la imagenes 
     * @return La imagen cargada
     */
	public static File loadFile(JTable tablaMenuImage, MyTableModel modelo){
		
		 	
		 fc = new JFileChooser();
		 int returnVal = fc.showOpenDialog(fc);
         if (returnVal == JFileChooser.APPROVE_OPTION){
        	 File imagen = fc.getSelectedFile();
        	 
        	 
        	 setImageName(imagen.getName());        	 
        	 
        	 int fila = modelo.getRowCount();//cantidad de filas
			 int columna = modelo.getColumnCount();//cantidad de columnas
			 boolean ok = true;
			 //recorre todo el TableModel buscando una celda vacia para agregar la imagen
			 for ( int i=0; i< fila; i++ ){
			     if( ok ) {
			       for( int j=0; j < columna; j++ ) {
			    	   
			           if( modelo.getValueAt(i, j) == null ) {        		        	  
			               modelo.setValueAt( imagen.getAbsolutePath() , i, j );           		               
			               tablaMenuImage.repaint();        		                      		              
			               ok = false;        		               
			               return imagen;
			           }
			        }
			  }			                 		 
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
