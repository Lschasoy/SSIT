import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTable;



public class Archivos {
	
	private JFileChooser fc;
	private String nameImage;
	private int coordX = 0, coordY = 0;
	
//======================================================================================================================
	public void setImageName (String nameI){ nameImage = nameI;}	
	public String getImageName  ()         { return nameImage; }
	public JFileChooser getFile ()         {return fc;}
	
	public int getX() {return coordX;}
	public int getY() {return coordY;}
//======================================================================================================================
	
	public BufferedImage loadImage(int row, int col, JTable tablaMenuImage, MyTableModel modelo) throws IOException{
		
		
		if ( row >= 0 && col >= 0 ) 
		{   
		//si celda contiene imagen
		   if( modelo.getValueAt(row, col) != null ){
		      //obtiene la ruta que corresponde a la celda donde se hizo el clic
		      File imagen = new File( modelo.getValueAt(row, col).toString() );
	       	 
		      return ImageIO.read(imagen);		
		    }                             
        }
	    return null;
	}

//======================================================================================================================	
	public BufferedImage loadFile(JTable tablaMenuImage, MyTableModel modelo){
		
	
		 fc = new JFileChooser();
		 int returnVal = fc.showOpenDialog(fc);
         if (returnVal == JFileChooser.APPROVE_OPTION){
        	 File imagen = fc.getSelectedFile();
        	 setImageName(imagen.getName());
        	 
        	 
        	 
        	 try {
        		 int fila = modelo.getRowCount();//cantidad de filas
        		 int columna = modelo.getColumnCount();//cantidad de columnas
        		 boolean ok = true;
        		 //recorre todo el TableModel buscando una celda vacia para agregar la imagen
        		 for ( int i=0; i< fila; i++ ){
        		     if( ok ) {
                       for( int j=0; j < columna; j++ ) {
                    	   
        		           if( modelo.getValueAt(i, j) == null ) {
        		        	   coordX = i; coordY = j;
        		               modelo.setValueAt( imagen.getAbsolutePath() , i, j );   
        		               
        		               tablaMenuImage.repaint();
        		               ok = false;
        		               return ImageIO.read(imagen);
        		           }
        		        }
        		  }
        		  else {
        		      break;
        	      }
        		                      		 
        	   }			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//cargar imagen vacia
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
