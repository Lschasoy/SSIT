 
public class Proyecto{

 
 public static void main(String[] args) {
  javax.swing.SwingUtilities.invokeLater(new Runnable() {
   public void run() {
     Ventana win = new Ventana();
     win.setVisible(true);
     
   }
  });
 }
 
}