package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


//동영상 재생 클래스
public class PlayVideo extends JPanel {
	String file;
	
	public PlayVideo(String str){
		file=str;
	}
	public void setFile(String file) {
		this.file=file;
	}
	public void play() {
		JFrame f = new JFrame();
		JFrame OutFrame = new JFrame();
		f.setLayout(null);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setVisible(true);

		Canvas c = new Canvas();
		c.setSize(f.getWidth(), f.getHeight());
		c.setBackground(Color.black);
		JPanel p = new JPanel();
		p.setSize(c.getWidth(), c.getHeight());
		p.add(c);
		f.add(p);
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), ".\\vlc");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		MediaPlayerFactory mpf = new MediaPlayerFactory();
		EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer();
		

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}
		});

		JButton btn = new JButton("나가기");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				OutFrame.dispose();
				
			}
		});
		
		OutFrame.setUndecorated(true);
		OutFrame.setAlwaysOnTop(true);
		OutFrame.setSize(100,100);
		OutFrame.setLocation(0,0);
		OutFrame.add(btn);
		OutFrame.setVisible(true);
	}
}
