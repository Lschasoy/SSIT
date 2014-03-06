import javax.swing.JFrame;

public class Proyecto{

 
 public static void main(String[] args) {
  javax.swing.SwingUtilities.invokeLater(new Runnable() {
   public void run() {
     Ventana win = new Ventana();
     
     win.setTitle("Proyecto fin de grado");
	 win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	 win.setResizable(false);
	 win.setSize( 1224, 720 );	
	 win.setLocationRelativeTo( null );
     win.setVisible(true);
     
   }
  });
 }
 
}
