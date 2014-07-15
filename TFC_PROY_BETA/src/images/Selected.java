package images;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Selected extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BufferedImage imgOut;
	private static ImageIcon icon2;
	private static JLabel lb;


	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(int width,  int height, Point po, BufferedImage img) {
		try {
			String title = "Captura "+width + " x " + height;			
			imgOut = new BufferedImage(width, height, img.getType());
			imgOut.setData(img.getData());
			
			lb = new JLabel();
			icon2 = new ImageIcon(imgOut);
		//============================================//
			System.out.println(imgOut.toString());
			pintar(width,  height, po,img);
			
			Selected dialog = new Selected();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	
			dialog.setTitle(title);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//========================================================================================	
	public Selected() {
		System.out.println("Selected Image");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			contentPanel.add(lb);
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));			
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
				JButton okButton = new JButton("Close");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(EXIT_ON_CLOSE);
					}
				});
				
				buttonPane.add(okButton);				
			}
		}// end of content
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnImageMenu = new JMenu("Image Menu");
				mnImageMenu.setIcon(new ImageIcon(Selected.class.getResource("/icons/16/editcopy.png")));
				menuBar.add(mnImageMenu);
				{
					JMenuItem mntmAlejar = new JMenuItem("alejar");
					mntmAlejar.setIcon(new ImageIcon(Selected.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
					mnImageMenu.add(mntmAlejar);
				}
			}
		}
	}
//========================================================================================
    public static void pintar(int width,  int heigth, Point po, BufferedImage img){   
    	
    	for (int i = 0; i < width; i++){		    	
			for(int j = 0; j < heigth; j++){				
				int posX = po.x + i;
				int posY = po.y + j;
				Color color = new Color(img.getRGB(posX, posY));
				imgOut.setRGB(i, j, color.getRGB());
		    }
		}
		    	
    	//icon2 = new ImageIcon(imgOut.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH));
    	icon2.setImage(imgOut);
    	lb.setIcon(icon2);
    }//end Pintar
}// end Selected
