package src;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	ImageIcon img;
	public ImagePanel() {
		img=new ImageIcon(".\\res\\image\\btn_yes.png");
	}
	public ImagePanel(String s) {
		img=new ImageIcon(s);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
