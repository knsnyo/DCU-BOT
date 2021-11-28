package src;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ImageButton extends JButton{
	ImageIcon img;
	public ImageButton() {
		img=new ImageIcon(".\\res\\image\\btn_yes.png");
	}
	public ImageButton(String s) {
		img=new ImageIcon(s);
	}
	public void setIcon(String s) {
		img=new ImageIcon(s);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
