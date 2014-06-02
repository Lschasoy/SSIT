package main;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Info extends JPanel {
		
	public Info(){
				
		setLayout(new GridLayout(1,2));
		setBounds(10, 370, 520, 69);
			
		JLabel l1 = new JLabel("PATH");
		add(l1);
	}
}
