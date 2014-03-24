import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class ImageRenderer extends DefaultTableCellRenderer{

  private JLabel lb = new JLabel();
  private ImageIcon icon = new ImageIcon( getClass().getResource("default.png"), "Imagen por Defecto"); 
  private Map iconos = new HashMap() ;
  

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                 boolean hasFocus, int row, int column)
  {
        lb.setText((String) value);
        File fichero;
        //
        if( value !=null )
        {
            fichero = new File( value.toString() );
            //comprueba que fichero exista
            if( fichero.exists() )
            {
                //busca la imagen en el MAP
                if( ya_existe( value.toString() ) )
                {
                    //si ya existe, extrae la imagen del MAP
                    lb.setIcon( getIcono( value.toString() ) );
                }
                else //No existe
                {
                //Agrega la imagen al map
                iconos.put(value.toString(), bToIcon(fichero) );
                //extrae y muestra
                lb.setIcon( getIcono( value.toString() ) );    
                }

            }
            else //si no existe, muestra imagen por default
            {
                lb.setIcon(icon);
            }        
        }
        else
        {
            lb.setIcon(icon);
        }
    return lb;
  }

 /**
 * Comprueba que una imagen ya exista en memoria
 * @param String key identificador 
 */
 private boolean ya_existe( String key )
 {
    Iterator it = iconos.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry e = (Map.Entry)it.next();        
        if( e.getKey().equals(key) )
            return true;
    }
    return false;
 }

 /**
 * Extrae una imagen del MAP dado su KEY
 * @param String key identificador unico
 * @return ImageIcon
 */
 private ImageIcon getIcono( String key )
 {
    ImageIcon imageIcon = icon;
    Iterator it = iconos.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry e = (Map.Entry)it.next();        
        if( e.getKey().equals(key) )
        {
           imageIcon = (ImageIcon) e.getValue();
           break;
        }            
    }
    return imageIcon;
 }

 /**
 * Dado la ruta de un archivo de imagen, carga este en un ImageIcon y retorna
 * @param File fichero
 */
private ImageIcon bToIcon( File fichero )
{        
    ImageIcon imageIcon = new ImageIcon( fichero.getAbsolutePath() );
    return imageIcon;
}

}//--> fin clase