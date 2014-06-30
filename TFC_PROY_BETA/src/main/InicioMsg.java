package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class InicioMsg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea msg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Ejecutando main InicioMsg");
			InicioMsg dialog = new InicioMsg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InicioMsg() {
		setBounds(100, 100, 449, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		msg = new JTextArea();
		msg.setColumns(50);
		msg.setTabSize(0);
		msg.setRows(10);
		contentPanel.add(msg);
	}
    
	public void put(String cad){
		
		msg.append(cad);
	}
	
	public void end(){
	  dispose();
	}
}
