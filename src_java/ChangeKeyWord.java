package src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ChangeKeyWord extends JDialog{
	// input
	private JTextField tf = new JTextField(10);
	// update button
	private JButton btn_ok = new JButton("변경");
	
	public ChangeKeyWord(JFrame frame, String title){
		// setting
		super(frame, title, true);
		this.setLayout(new FlowLayout());
		setSize(200,150);
		
		// add
		this.add(tf);
		this.add(btn_ok);
		
		// btn_ok listener
		btn_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
	}
	
	String getInput(){
		// blank
		if(0 == tf.getText().length()) { return null;}
		else return tf.getText();
	}
}
