package procesos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import matlab.Funciones;
import matlabToJavaSC.*;

import javax.swing.JProgressBar;


@SuppressWarnings("serial")
public class FormSegment extends JDialog {
	public FormSegment() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		
	
	}

	private final JPanel contentPanel = new JPanel();
	private JTextField jt1, jt2, jt3;
	private Object[] result= {null, null, null};
	private BufferedImage imgOut;
	private JProgressBar progressBar;
	private JTextArea traza;

	/**
	 * Launch the application.
	 */
	public static void main(FormSegment dialog, String path, Segmentacion fun) {
		try {
			dialog.Run(path,fun);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 //=============================================================================================
		// ====================== Getter and Setter =========================== 
		public Object[] getResult() {
			return result;
		}
		public void setResult(Object[] result) {
			this.result = result;
		}
		public BufferedImage getImgOut(){
			return imgOut;
		}
	//=============================================================================================		

	/**
	 * Create the dialog.
	 */
	public void Run(final String path, final Segmentacion fun) {
		setBounds(100, 100, 550, 300);
		getContentPane().setLayout(new BorderLayout());
		
		JLabel lblParametrosFuncionDe = new JLabel("Parametros: funcion de segementacion");
		lblParametrosFuncionDe.setVerticalAlignment(SwingConstants.BOTTOM);
		lblParametrosFuncionDe.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblParametrosFuncionDe, BorderLayout.NORTH);
		
		contentPanel.setLayout(null);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						result[0] = Double.parseDouble(jt1.getText()); 						
						result[1] = Double.parseDouble(jt2.getText());						
						result[2] = Integer.parseInt(jt3.getText());
						traza.setText("Run Segmentation with: " + result[0].toString()+' '+result[1].toString()+' '+result[2].toString());						
					    Funciones segm = new Funciones();
					    segm.start();					    
					    synchronized(segm){					    	
				            try{			
				            	System.out.println("Run Segementation");
				                segm.wait();
				                segm.run(fun, path, result, progressBar, traza);				                
				            }catch(InterruptedException e){
				                e.printStackTrace();
				            }
				            
					    }
					    imgOut = segm.getImgOut();
					    dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			setParam();
		}
	}

//===============================================================================================	
	public void setParam (){
		JPanel pParam = new JPanel ();
		pParam.setBounds(5, 5, 500, 25);
		GridLayout gl_pParam = new GridLayout(1,6);
		gl_pParam.setHgap(20);
		pParam.setLayout(gl_pParam);
		
		JLabel lb1 = new JLabel("Espacial");
		jt1 = new JTextField(3);
		jt1.setHorizontalAlignment(SwingConstants.RIGHT);
		jt1.setText("0");
		pParam.add(lb1);
		pParam.add(jt1);
		
		JLabel lb2 = new JLabel("Color");
		jt2 = new JTextField(3);
		jt2.setText("0");
		jt2.setHorizontalAlignment(SwingConstants.RIGHT);
		pParam.add(lb2);
		pParam.add(jt2);
		
		JLabel lb3 = new JLabel("Min region");
		jt3 = new JTextField(3);
		jt3.setText("0");
		jt3.setHorizontalAlignment(SwingConstants.RIGHT);
		pParam.add(lb3);
		pParam.add(jt3);
		
		traza = new JTextArea("Metodo de Segmentacion - MATLAB ");
		traza.setBounds(25, 30, 500, 150);
						
		contentPanel.add(pParam);	
		contentPanel.add(traza);
	}

}
