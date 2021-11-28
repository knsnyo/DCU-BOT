package src;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import com.pi4j.io.gpio.*;

//메인 화면 클래스

public class GUI extends JFrame {
	// *시작 1~9번까지 재생할 비디오 파일의 경로(String으로 파일 경로 저장)
	String intro_file = "./res/video/dcu_guide.mp4";
	String file1 = "./res/video/디쿠 가이드 로봇.mp4";
	String file2 = "./res/video/원데이바이오텍.mp4";
	String file3 = "./res/video/video1.mp4";
	String file4 = "./res/video/video2.mp4";
	String file5 = "./res/video/video1.mp4";
	String file6 = "./res/video/video2.mp4";
	String file7 = "./res/video/video1.mp4";
	String file8 = "./res/video/video2.mp4";
	String file9 = "./res/video/video1.mp4";
	String file10 = "./res/video/video1.mp4";
	String file11 = "./res/video/video1.mp4";
	String file12 = "./res/video/video1.mp4";
	String file13 = "./res/video/video1.mp4";
	String file14 = "./res/video/video1.mp4";
	String file15 = "./res/video/video1.mp4";
	String file16 = "./res/video/video1.mp4";
	String file17 = "./res/video/video1.mp4";
	String file18 = "./res/video/video1.mp4";
	String file19 = "./res/video/video1.mp4";
	String file20 = "./res/video/video1.mp4";
	String file21 = "./res/video/video1.mp4";
	String file22 = "./res/video/video1.mp4";
	String file23 = "./res/video/video1.mp4";
	String file24 = "./res/video/video1.mp4";	
	// *끝 1~9번까지 재생할 비디오 파일(String으로 파일 경로 저장)

	// *시작 재생파일을 재생할 PlayVideo 클래스를 1~9번까지 각각 생성
	PlayVideo video_1 = new PlayVideo(file1);
	PlayVideo video_2 = new PlayVideo(file2);
	PlayVideo video_3 = new PlayVideo(file3);
	PlayVideo video_4 = new PlayVideo(file4);
	PlayVideo video_5 = new PlayVideo(file5);
	PlayVideo video_6 = new PlayVideo(file6);
	PlayVideo video_7 = new PlayVideo(file7);
	PlayVideo video_8 = new PlayVideo(file8);
	PlayVideo video_9 = new PlayVideo(file9);
	PlayVideo video_10 = new PlayVideo(file1);
	PlayVideo video_11 = new PlayVideo(file2);
	PlayVideo video_12 = new PlayVideo(file2);
	PlayVideo video_13 = new PlayVideo(file3);
	PlayVideo video_14 = new PlayVideo(file4);
	PlayVideo video_15 = new PlayVideo(file5);
	PlayVideo video_16 = new PlayVideo(file6);
	PlayVideo video_17 = new PlayVideo(file7);
	PlayVideo video_18 = new PlayVideo(file8);
	PlayVideo video_19 = new PlayVideo(file9);
	// *끝 재생파일을 재생할 PlayVideo 클래스를 1~9번까지 각각 생성
	
	Dimension res=Toolkit.getDefaultToolkit().getScreenSize();
	Container c = getContentPane(); //JFrame에 컨탠트팬 생성
	MainScreen main = new MainScreen(); //메인 페이지 생성자
	Admin admin = new Admin(); //관리자 페이지 생성자
	static SocketTest sk = new SocketTest();
	static String message;
	static EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent(){
		public void finished(MediaPlayer mediaPlayer){
			int result = JOptionPane.showConfirmDialog(null, "부스위치까지 안내를 원하시면 '예'를, 메인메뉴로 돌아가고 싶으시면 '아니요'를 누른 후, '메인메뉴'버튼을 눌러주세요 ", "Confirm", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				message = "이동해";
				sk.socket(message);
			}
			else if (result == JOptionPane.CLOSED_OPTION) {
				message = "리셋";
				sk.socket(message);
			}
			else{
				message = "리셋";
				sk.socket(message);
			}

		}
	};
	GpioPinDigitalOutput sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
	GpioPinDigitalInput sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01,PinPullResistance.PULL_DOWN);
	GpioPinDigitalOutput led_pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);
	final static GpioController gpio = GpioFactory.getInstance();
	boolean tf = true;


	// VideoPlayer vp1=new VideoPlayer(file1);

	Record rec = new Record(); // 음성 인식(STT) 기능을 실행할 Record클래스 생성

	static Color background = new Color(0, 32, 96); // 페이지의 배경색 지정(아마 남색...)


	public GUI() { // GUI public클래스의 생성자
		// System.out.println("해상도 : " + res.width + " x " + res.height); //가로(1536),
		// 세로(864)란다...
		setTitle("Display Contents");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c.setLayout(new BorderLayout());

		c.add(main, BorderLayout.CENTER); // 메인 페이지를 BorderLayout 가운데에 배치->MainScreen클래스 가서 어떻게 구성되어있는지 확인

		// 사이즈 최대로
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		
		new Thread_run().start();
		
		component.mediaPlayer().controls().setRepeat(false);
		component.mediaPlayer().media().play(intro_file);
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				led_pin.low();
				sensorTriggerPin.low();
			}
		});
		
		
		
	}

	// 행사 목록 화면 이동 메소드
	public void gotoSelectscreen() {
		JPanel panel = new SelectContent();
		setContentPane(panel); // contentPane.add(panel);
		validate();
	}

	// 관리자 페이지 화면 이동 메소드
	public void goAdmin() {
		setContentPane(admin);
		validate();
	}

	// 메인(처음)화면 이동 메소드
	public void goMain() {
		setContentPane(main);
		validate();
	}

	// 관리자페이지에서 영상 설정 페이지 화면 이동 메소드
	public void goSetContent() {
		JPanel panel = new SetContent();
		setContentPane(panel);
		validate();
	}


//--------------------------------------------------------------------------------------------------------------------
	// 영상 설정 페이지에서 1번 행사 내용 영상 설정 클래스
	class OpenActionListener1 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener1() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file1 = chooser.getSelectedFile().getPath();
		}
	}

	// 영상 설정 페이지에서 2번 행사 내용 영상 설정 클래스
	class OpenActionListener2 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener2() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file2 = chooser.getSelectedFile().getPath();
			video_2.setFile(file2);
		}
	}

	// 영상 설정 페이지에서 3번 행사 내용 영상 설정 클래스
	class OpenActionListener3 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener3() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file3 = chooser.getSelectedFile().getPath();
			video_3.setFile(file3);
		}
	}

	// 영상 설정 페이지에서 4번 행사 내용 영상 설정 클래스
	class OpenActionListener4 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener4() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file4 = chooser.getSelectedFile().getPath();
			video_4.setFile(file4);
		}
	}

	// 영상 설정 페이지에서 5번 행사 내용 영상 설정 클래스
	class OpenActionListener5 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener5() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file5 = chooser.getSelectedFile().getPath();
			video_5.setFile(file5);
		}
	}

	// 영상 설정 페이지에서 6번 행사 내용 영상 설정 클래스
	class OpenActionListener6 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener6() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file6 = chooser.getSelectedFile().getPath();
			video_6.setFile(file6);
		}
	}

	// 영상 설정 페이지에서 7번 행사 내용 영상 설정 클래스
	class OpenActionListener7 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener7() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file7 = chooser.getSelectedFile().getPath();
			video_7.setFile(file7);
		}
	}

	// 영상 설정 페이지에서 8번 행사 내용 영상 설정 클래스
	class OpenActionListener8 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener8() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file8 = chooser.getSelectedFile().getPath();
			video_8.setFile(file8);
		}
	}

	// 영상 설정 페이지에서 9번 행사 내용 영상 설정 클래스
	class OpenActionListener9 implements ActionListener {
		private JFileChooser chooser;

		public OpenActionListener9() {
			chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI & MP4 Videos", "avi", "mp4");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			file9 = chooser.getSelectedFile().getPath();
			video_9.setFile(file9);
		}
	}
//--------------------------------------------------------------------------------------------------------------------


	// 관리자 페이지의 시작 화면 클래스
	class Admin extends JPanel {		// *시작 위쪽 패널에 넣을 컴포넌트 생성
		ImageButton btn_admin = new ImageButton("./res/image/btn_admin.png"); // 오른쪽에 관리자페이지 버튼의 이미지로 학교 로고 이미지를 사용
		//ImagePanel pa_mainTitle = new ImagePanel("./res/image/pa_mainTitle.png"); // 중간에 제목 패널로 학교 입시박람회 글귀 이미지를 사용
		JLabel pa_mainTitle = new JLabel("파일 적용 X");
		ImageButton btn_mic = new ImageButton("./res/image/btn_micOff.png"); // 왼쪽에 음성인식 버튼으로 처음엔 마이크꺼진 이미지를 사용(클릭시
																				// 마이크켜진 이미지로 바꿈)
		ImageButton btn_change_title = new ImageButton("./res/image/btn_setTitle.png");
		
		// *끝 위쪽 패널에 넣을 컴포넌트 생성

		JPanel pa_north = new JPanel(); // 컨테이너 BorderLayout 위쪽에 넣을 패널 생성(여기에 방금선언한 컴포넌트 넣기)
		JPanel pa_center = new JPanel(); // 컨테이너 BorderLayout 중간에 넣을 패널 생성(여기에 아마 버튼 넣을듯)
		JPanel pa_center_center = new JPanel();
		JPanel pa_center_north = new JPanel();
		JPanel pa_center_south = new JPanel();
		JPanel pa_center_west = new JPanel();
		JPanel pa_center_east = new JPanel();
		
		ImageButton btn_inputVideo=new ImageButton("./res/image/btn_inputVideo.PNG");
		ImageButton btn_returnMain=new ImageButton("./res/image/btn_returnMain.png");
		
		ChangeKeyWord ck; // dialog
		String str_keyword; // text

		// MainScreen클래스 생성자
		public Admin() {
			pa_mainTitle.setHorizontalAlignment(JLabel.CENTER);
			pa_mainTitle.setFont(new Font("Gothic", Font.BOLD, 80));
			pa_mainTitle.setForeground(Color.WHITE);
			
			String path = "./res/setting/title.txt";
			
			try(BufferedReader br = new BufferedReader(new FileReader(path))){
				//System.out.println("Hi");
				String line = br.readLine();
				StringBuilder sb = new StringBuilder();
				
				while(null != line){
					sb.append(line).append("\n");
					line = br.readLine();
				}
				
				String fileAsString = sb.toString();
				//System.out.println(fileAsString);
				pa_mainTitle.setText(fileAsString);
			}catch(IOException e){
				//System.out.println("load ERROR");
			}
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
			this.setBackground(background); // 패널 배경색은 남색으로

			pa_north.setBackground(background); // 위쪽 패널 배경색도 남색으로
			pa_center.setBackground(background); // 중간 패널 배경색도 남색으로
			pa_center_center.setBackground(background);

			pa_center_north.setBackground(background);
			// pa_center_north.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_south.setBackground(background);
			pa_center_south.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_west.setBackground(background);
			pa_center_west.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_east.setBackground(background);
			pa_center_east.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_north.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로

			this.add(pa_north, BorderLayout.NORTH); // 전체 패널 BorderLayout 위쪽에는 생성한 pa_north패널 부착
			pa_north.add(btn_mic, BorderLayout.WEST); // pa_north 서쪽에는 음성인식 버튼 부착
			pa_north.add(pa_mainTitle, BorderLayout.CENTER); // pa_north 가운데에는 타이틀 패널 부착
			pa_north.add(btn_admin, BorderLayout.EAST); // pa_north 동쪽에는 관리자페이지 이동 버튼 부착

			btn_mic.setPreferredSize(new Dimension(res.height / 7, res.height / 6));
			pa_mainTitle.setPreferredSize(new Dimension(1300, res.height / 6));
			btn_admin.setPreferredSize(new Dimension(res.height / 7, res.height / 6));

			this.add(pa_center, BorderLayout.CENTER);
			pa_center.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로
			pa_center.add(pa_center_center, BorderLayout.CENTER);
			pa_center.add(pa_center_north, BorderLayout.NORTH);
			pa_center.add(pa_center_south, BorderLayout.SOUTH);
			pa_center.add(pa_center_east, BorderLayout.EAST);
			pa_center.add(pa_center_west, BorderLayout.WEST);
			
			pa_center_center.add(btn_inputVideo);
			pa_center_center.add(btn_change_title);
			pa_center_center.add(btn_returnMain);
			
			btn_inputVideo.setPreferredSize(new Dimension(res.width/10,res.height/10));
			btn_change_title.setPreferredSize(new Dimension(res.width/10, res.height/10));
			btn_returnMain.setPreferredSize(new Dimension(res.width/10,res.height/10));
			
			btn_inputVideo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					goSetContent();
				}

			});

			btn_returnMain.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					goMain();
					component.mediaPlayer().controls().setRepeat(true);
					component.mediaPlayer().media().play(intro_file);
					try{
						new Thread_run().start();
					}catch(Exception eb){
						eb.printStackTrace();
					}
				}
			});
			
			ck = new ChangeKeyWord(new JFrame(), "변경");
			btn_change_title.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					ck.setVisible(true);
					
					String str_change_text = ck.getInput();
					
					if(null == str_change_text){}
					else{
						pa_mainTitle.setText(str_change_text);
						main.pa_mainTitle.setText(str_change_text);
						try{
							OutputStream fos = new FileOutputStream("./res/setting/title.txt");
							byte[] by = str_change_text.getBytes();
							fos.write(by);
						}catch(Exception e2){
							e2.getStackTrace();
						}
					}
				}
			});

			btn_admin.setBorderPainted(false);
			btn_admin.setFocusPainted(false);
			btn_mic.setBorderPainted(false);
			btn_mic.setFocusPainted(false);
			btn_mic.setEnabled(false);
		}
	}

	// 관리자 페이지의 행사내용 영상 설정 화면 구성 클래스
	class SetContent extends JPanel {
		ImageButton btn_admin = new ImageButton("./res/image/btn_admin.png"); // 오른쪽에 관리자페이지 버튼의 이미지로 학교 로고 이미지를 사용
		ImagePanel pa_mainTitle = new ImagePanel("./res/image/pa_mainTitle.png"); // 중간에 제목 패널로 학교 입시박람회 글귀 이미지를 사용
		ImageButton btn_mic = new ImageButton("./res/image/btn_micOff.png"); // 왼쪽에 음성인식 버튼으로 처음엔 마이크꺼진 이미지를 사용(클릭시
																				// 마이크켜진 이미지로 바꿈)
		// *끝 위쪽 패널에 넣을 컴포넌트 생성

		JPanel pa_north = new JPanel(); // 컨테이너 BorderLayout 위쪽에 넣을 패널 생성(여기에 방금선언한 컴포넌트 넣기)
		JPanel pa_center = new JPanel(); // 컨테이너 BorderLayout 중간에 넣을 패널 생성(여기에 아마 버튼 넣을듯)
		JPanel pa_center_center = new JPanel();
		JPanel pa_center_north = new JPanel();
		JPanel pa_center_south = new JPanel();
		JPanel pa_center_west = new JPanel();
		JPanel pa_center_east = new JPanel();
		
		ImageButton btn_returnMain=new ImageButton("./res/image/btn_returnMain.png");
		public SetContent() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
			this.setBackground(background); // 패널 배경색은 남색으로

			pa_north.setBackground(background); // 위쪽 패널 배경색도 남색으로
			pa_center.setBackground(background); // 중간 패널 배경색도 남색으로
			pa_center_center.setBackground(background);

			pa_center_north.setBackground(background);
			// pa_center_north.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_south.setBackground(background);
			pa_center_south.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_west.setBackground(background);
			pa_center_west.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_east.setBackground(background);
			pa_center_east.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_north.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로

			this.add(pa_north, BorderLayout.NORTH); // 전체 패널 BorderLayout 위쪽에는 생성한 pa_north패널 부착
			pa_north.add(btn_mic, BorderLayout.WEST); // pa_north 서쪽에는 음성인식 버튼 부착
			pa_north.add(pa_mainTitle, BorderLayout.CENTER); // pa_north 가운데에는 타이틀 패널 부착
			pa_north.add(btn_admin, BorderLayout.EAST); // pa_north 동쪽에는 관리자페이지 이동 버튼 부착

			btn_mic.setPreferredSize(new Dimension(res.height / 7, res.height / 6));
			pa_mainTitle.setPreferredSize(new Dimension(res.width/4, res.height / 6));
			btn_admin.setPreferredSize(new Dimension(res.height / 7, res.height / 6));

			this.add(pa_center, BorderLayout.CENTER);
			pa_center.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로
			pa_center.add(pa_center_center, BorderLayout.CENTER);
			pa_center.add(pa_center_north, BorderLayout.NORTH);
			pa_center.add(pa_center_south, BorderLayout.SOUTH);
			pa_center.add(pa_center_east, BorderLayout.EAST);
			pa_center.add(pa_center_west, BorderLayout.WEST);
			
			JButton btn1 = new JButton("1번 행사");
			btn1.setPreferredSize(new Dimension(170,80));
			btn1.addActionListener(new OpenActionListener1());
			
			JButton btn2 = new JButton("2번 행사");
			btn2.setPreferredSize(new Dimension(170,80));
			btn2.addActionListener(new OpenActionListener2());
			
			JButton btn3 = new JButton("3번 행사");
			btn3.setPreferredSize(new Dimension(170,80));
			btn3.addActionListener(new OpenActionListener3());
			
			JButton btn4 = new JButton("4번 행사");
			btn4.setPreferredSize(new Dimension(170,80));
			btn4.addActionListener(new OpenActionListener4());
			
			JButton btn5 = new JButton("5번 행사");
			btn5.setPreferredSize(new Dimension(170,80));
			btn5.addActionListener(new OpenActionListener5());
			
			JButton btn6 = new JButton("6번 행사");
			btn6.setPreferredSize(new Dimension(170,80));
			btn6.addActionListener(new OpenActionListener6());
			
			JButton btn7 = new JButton("7번 행사");
			btn7.setPreferredSize(new Dimension(170,80));
			btn7.addActionListener(new OpenActionListener7());
			
			JButton btn8 = new JButton("8번 행사");
			btn8.setPreferredSize(new Dimension(170,80));
			btn8.addActionListener(new OpenActionListener8());

			JButton btn9 = new JButton("9번 행사");
			btn9.setPreferredSize(new Dimension(170,80));
			btn9.addActionListener(new OpenActionListener9());
			
			JButton btn10 = new JButton("10번 행사");
			btn10.setPreferredSize(new Dimension(170,80));
			btn10.addActionListener(new OpenActionListener9());
			
			JButton btn11 = new JButton("11번 행사");
			btn11.setPreferredSize(new Dimension(170,80));
			btn11.addActionListener(new OpenActionListener9());
			
			JButton btn12 = new JButton("12번 행사");
			btn12.setPreferredSize(new Dimension(170,80));
			btn12.addActionListener(new OpenActionListener9());
			
			JButton btn13 = new JButton("13번 행사");
			btn13.setPreferredSize(new Dimension(170,80));
			btn13.addActionListener(new OpenActionListener9());
			
			JButton btn14 = new JButton("14번 행사");
			btn14.setPreferredSize(new Dimension(170,80));
			btn14.addActionListener(new OpenActionListener9());
			
			JButton btn15 = new JButton("15번 행사");
			btn15.setPreferredSize(new Dimension(170,80));
			btn15.addActionListener(new OpenActionListener9());
			
			JButton btn16 = new JButton("16번 행사");
			btn16.setPreferredSize(new Dimension(170,80));
			btn16.addActionListener(new OpenActionListener8());

			JButton btn17 = new JButton("17번 행사");
			btn17.setPreferredSize(new Dimension(170,80));
			btn17.addActionListener(new OpenActionListener9());
			
			JButton btn18 = new JButton("18번 행사");
			btn18.setPreferredSize(new Dimension(170,80));
			btn18.addActionListener(new OpenActionListener9());
			
			JButton btn19 = new JButton("19번 행사");
			btn19.setPreferredSize(new Dimension(170,80));
			btn19.addActionListener(new OpenActionListener9());
			
			JButton btn20 = new JButton("20번 행사");
			btn20.setPreferredSize(new Dimension(170,80));
			btn20.addActionListener(new OpenActionListener9());
			
			JButton btn21 = new JButton("21번 행사");
			btn21.setPreferredSize(new Dimension(170,80));
			btn21.addActionListener(new OpenActionListener9());
			
			JButton btn22 = new JButton("22번 행사");
			btn22.setPreferredSize(new Dimension(170,80));
			btn22.addActionListener(new OpenActionListener9());
			
			JButton btn23 = new JButton("23번 행사");
			btn23.setPreferredSize(new Dimension(170,80));
			btn23.addActionListener(new OpenActionListener9());
			
			JButton btn24 = new JButton("24번 행사");
			btn24.setPreferredSize(new Dimension(170,80));
			btn24.addActionListener(new OpenActionListener9());
			
			pa_center_center.add(btn1);
			pa_center_center.add(btn2);
			pa_center_center.add(btn3);
			pa_center_center.add(btn4);
			pa_center_center.add(btn5);
			pa_center_center.add(btn6);
			pa_center_center.add(btn7);
			pa_center_center.add(btn8);
			pa_center_center.add(btn9);
			pa_center_center.add(btn10);
			pa_center_center.add(btn11);
			pa_center_center.add(btn12);
			pa_center_center.add(btn13);
			pa_center_center.add(btn14);
			pa_center_center.add(btn15);
			pa_center_center.add(btn16);
			pa_center_center.add(btn17);
			pa_center_center.add(btn18);
			pa_center_center.add(btn19);
			pa_center_center.add(btn20);
			pa_center_center.add(btn21);
			pa_center_center.add(btn22);
			pa_center_center.add(btn23);
			pa_center_center.add(btn24);
			pa_center_center.add(btn_returnMain);
			btn_returnMain.setPreferredSize(new Dimension(170,80));
			
			btn_returnMain.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					goAdmin();
				}
			});

			btn_admin.setBorderPainted(false);
			btn_admin.setFocusPainted(false);
			btn_mic.setBorderPainted(false);
			btn_mic.setFocusPainted(false);
			btn_mic.setEnabled(false);
		}
	}

	
	// 행사 내용 목록 화면의 구성 클래스
	class SelectContent extends JPanel {
		public SelectContent() {
			JButton btn1 = new JButton("1번 행사");
			btn1.setLocation(20, 50);
			btn1.setSize(150, 80);
			btn1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_1.play();
				}
			});

			JButton btn2 = new JButton("2번 행사");
			btn2.setLocation(220, 50);
			btn2.setSize(150, 80);
			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_2.play();
				}
			});

			JButton btn3 = new JButton("3번 행사");
			btn3.setLocation(420, 50);
			btn3.setSize(150, 80);
			btn3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_3.play();
				}
			});

			JButton btn4 = new JButton("4번 행사");
			btn4.setLocation(20, 200);
			btn4.setSize(150, 80);
			btn4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_4.play();
				}
			});
			JButton btn5 = new JButton("5번 행사");
			btn5.setLocation(220, 200);
			btn5.setSize(150, 80);
			btn5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_5.play();
				}
			});
			JButton btn6 = new JButton("6번 행사");
			btn6.setLocation(420, 200);
			btn6.setSize(150, 80);
			btn6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_6.play();
				}
			});

			JButton btn7 = new JButton("7번 행사");
			btn7.setLocation(20, 350);
			btn7.setSize(150, 80);
			btn7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_7.play();
				}
			});

			JButton btn8 = new JButton("8번 행사");
			btn8.setLocation(220, 350);
			btn8.setSize(150, 80);
			btn8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_8.play();
				}
			});

			JButton btn9 = new JButton("9번 행사");
			btn9.setLocation(420, 350);
			btn9.setSize(150, 80);
			btn9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					video_9.play();
				}
			});

			JButton btnSearch = new JButton("메인화면");
			btnSearch.setLocation(600, 150);
			btnSearch.setSize(150, 80);
			btnSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					goMain();
				}
			});

			setLayout(null);
			add(btn1);
			add(btn2);
			add(btn3);
			add(btn4);
			add(btn5);
			add(btn6);
			add(btn7);
			add(btn8);
			add(btn9);
			add(btnSearch);
			setSize(600, 600);
			setVisible(true);
		}
	}
	

	class VideoPanel extends JPanel {
		public VideoPanel() {
			setLayout(new BorderLayout());
			add(component);	
		}
	}
	// 메인(시작) 화면 구성 클래스
	class MainScreen extends JPanel {
		// *시작 위쪽 패널에 넣을 컴포넌트 생성
		ImageButton btn_admin = new ImageButton("./res/image/btn_admin.png"); // 오른쪽에 관리자페이지 버튼의 이미지로 학교 로고 이미지를 사용
		//ImagePanel pa_mainTitle = new ImagePanel("./res/image/pa_mainTitle.png"); // 중간에 제목 패널로 학교 입시박람회 글귀 이미지를 사용
		public JLabel pa_mainTitle = new JLabel("파일 적용 x");
		public ImageButton btn_mic = new ImageButton("./res/image/btn_micOff.png"); // 왼쪽에 음성인식 버튼으로 처음엔 마이크꺼진 이미지를 사용(클릭시
																				// 마이크켜진 이미지로 바꿈)
		// *끝 위쪽 패널에 넣을 컴포넌트 생성

		JPanel pa_north = new JPanel(); // 컨테이너 BorderLayout 위쪽에 넣을 패널 생성(여기에 방금선언한 컴포넌트 넣기)
		JPanel pa_center = new JPanel(); // 컨테이너 BorderLayout 중간에 넣을 패널 생성(여기에 아마 동영상 재생창 넣을듯)
		VideoPanel pa_center_center = new VideoPanel();
		Audio_running pa_center_center2 = new Audio_running();
		JButton return_main = new JButton("메인메뉴");
		
		JPanel pa_center_north = new JPanel();
		JPanel pa_center_south = new JPanel();
		JPanel pa_center_west = new JPanel();
		JPanel pa_center_east = new JPanel();
		
		// MainScreen클래스 생성자
		public MainScreen() {
			pa_mainTitle.setHorizontalAlignment(JLabel.CENTER);
			pa_mainTitle.setFont(new Font("Gothic", Font.BOLD, 80));
			pa_mainTitle.setForeground(Color.WHITE);
			
			String path = "./res/setting/title.txt";
			
			try(BufferedReader br = new BufferedReader(new FileReader(path))){
				//System.out.println("Hi");
				String line = br.readLine();
				StringBuilder sb = new StringBuilder();
				
				while(null != line){
					sb.append(line).append("\n");
					line = br.readLine();
				}
				
				String fileAsString = sb.toString();
				//System.out.println(fileAsString);
				pa_mainTitle.setText(fileAsString);
			}catch(IOException e){
				//System.out.println("load ERROR");
			}
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
			this.setBackground(background); // 패널 배경색은 남색으로

			pa_north.setBackground(background); // 위쪽 패널 배경색도 남색으로
			pa_center.setBackground(background); // 중간 패널 배경색도 남색으로
			pa_center_center.setBackground(background);

			pa_center_north.setBackground(background);
			// pa_center_north.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_south.setBackground(background);
			pa_center_south.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_west.setBackground(background);
			pa_center_west.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_center_east.setBackground(background);
			pa_center_east.setPreferredSize(new Dimension(res.height / 20, res.height / 20));

			pa_north.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로

			this.add(pa_north, BorderLayout.NORTH); // 전체 패널 BorderLayout 위쪽에는 생성한 pa_north패널 부착
			pa_north.add(btn_mic, BorderLayout.WEST); // pa_north 서쪽에는 음성인식 버튼 부착
			pa_north.add(pa_mainTitle, BorderLayout.CENTER); // pa_north 가운데에는 타이틀 패널 부착
			pa_north.add(btn_admin, BorderLayout.EAST); // pa_north 동쪽에는 관리자페이지 이동 버튼 부착

			btn_mic.setPreferredSize(new Dimension(res.height / 7, res.height / 6));
			pa_mainTitle.setPreferredSize(new Dimension(res.width/4, res.height / 6));
			btn_admin.setPreferredSize(new Dimension(res.height / 7, res.height / 6));

			this.add(pa_center, BorderLayout.CENTER);
			pa_center.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로
			pa_center.add(pa_center_center, BorderLayout.CENTER);
			pa_center.add(pa_center_north, BorderLayout.NORTH);
			pa_center.add(pa_center_south, BorderLayout.SOUTH);
			pa_center.add(pa_center_east, BorderLayout.EAST);
			pa_center.add(pa_center_west, BorderLayout.WEST);

			btn_admin.setBorderPainted(false);
			btn_admin.setFocusPainted(false);
			btn_mic.setBorderPainted(false);
			btn_mic.setFocusPainted(false);

			btn_admin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					component.mediaPlayer().controls().stop();
					tf  = false;
					goAdmin();
				}

			});

			btn_mic.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Thread01().start();
					new Thread02().start();
				}
			});
			
			return_main.setBackground(background);
			return_main.setForeground(Color.white);
			return_main.setFont(new Font("Arial", Font.ITALIC, 20));
			return_main.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tf = true;
					main.btn_mic.setEnabled(true);
					main.btn_admin.setEnabled(true);
					main.pa_center.remove(main.pa_center_center2);
					main.btn_mic.setIcon("./res/image/btn_micOff.png");	
					main.pa_north.remove(main.return_main);
					main.pa_north.add(main.btn_admin, BorderLayout.EAST);
					main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
					main.pa_north.revalidate();
					main.pa_north.repaint();
					sk.socket("돌아가");
		
					led_pin.low();
					try{
						new Thread_run().start();
					}
					catch(Exception ed){
						ed.printStackTrace();
					}
					component.mediaPlayer().controls().setRepeat(true);
					component.mediaPlayer().media().play(intro_file);
				}
			});
		}
	}
	
	class Audio_running extends JPanel {
		JLabel label = new JLabel("<html><body style='text-align:center;'> 음성인식이 실행중입니다.<br/> 5초동안 원하시는 기능을 말씀해 주세요.<br/></body></html>");
		public Audio_running() {
			setBackground(background);
			
			label.setFont(new Font("Gothic", Font.ITALIC, 50));
			label.setForeground(Color.white);
			add(label);
		}
	}
	
	class Thread01 extends Thread {
		public void run() {
			sensorTriggerPin.low();
			led_pin.high();
			component.mediaPlayer().controls().stop();
			
			main.btn_mic.setIcon("./res/image/btn_micOn.png");
			main.btn_mic.setEnabled(false);
			main.btn_admin.setEnabled(false);
			main.pa_center.remove(main.pa_center_center);
			main.pa_center_center2.label.setText("<html><body style='text-align:center;'> 음성인식이 실행중입니다.<br/> 5초동안 원하시는 기능을 말씀해 주세요.<br/></body></html>");			
			main.pa_center.add(main.pa_center_center2, BorderLayout.CENTER);			
			main.pa_center.revalidate();
			main.pa_center.repaint();
			
			main.pa_north.remove(main.return_main);
			main.pa_north.add(main.btn_admin, BorderLayout.EAST);
			main.pa_north.revalidate();
			main.pa_north.repaint();
			
			tf  = false;
			if (rec.getRunning() == false) {
				rec.recordVoice(); // 디쿠 음성인식
			}
		}
		
	}
	
	class Thread02 extends Thread {
		public void run() {
			int LIMITED_TIME = 5;
			for (int i = LIMITED_TIME; i >= 0; i--) {
				System.out.println(i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {

				}
			}
			if (rec.getRunning() == true) {
				rec.setRunning(false);
				rec.saveVoiceToFile();
				rec.setStr_stt();
				//btn_mic.setIcon("./res/image/btn_micOff.png");
				//btn_mic.setEnabled(false);
				System.out.println(rec.getStr_stt());
				if(rec.getStr_stt().contains("보여줘")){
					if (rec.getStr_stt().contains("가이드 로봇")) {
					    led_pin.low();
					    main.btn_mic.setEnabled(true);
					    main.btn_mic.setIcon("./res/image/btn_micOff.png");
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
					
						main.pa_north.remove(main.btn_admin);
						main.pa_north.add(main.return_main, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();
					
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file1);
						
						sk.socket("디쿠 가이드 로봇");
									
						
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("바이오")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						main.pa_north.remove(main.btn_admin);
						main.pa_north.add(main.return_main, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();	
									
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file2);
						sk.socket("원데이 바이오");
						
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("3번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
						
						main.pa_north.remove(main.btn_admin);
						main.pa_north.add(main.return_main, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();
											
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file3);
						sk.socket("3번");
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("4번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file4);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("5번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file5);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("6번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file6);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("7번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");		
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
									
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file7);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("8번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file8);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("9번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file9);
						rec.str_stt = "";
					}
					else {
						// 디쿠가 못 알아먹음
						int result = JOptionPane.showConfirmDialog(null, "음성인식 결과가 없습니다. 다시하시겠습니까?", "Confirm",
								JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.CLOSED_OPTION) {
							tf = true;
							main.btn_mic.setEnabled(true);
							main.btn_admin.setEnabled(true);
							main.btn_mic.setIcon("./res/image/btn_micOff.png");	
							main.pa_center.remove(main.pa_center_center2);
							main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
							main.pa_center.revalidate();
							main.pa_center.repaint();
						
							main.pa_north.remove(main.return_main);
							main.pa_north.add(main.btn_admin, BorderLayout.EAST);
							main.pa_north.revalidate();
							main.pa_north.repaint();						
			
							led_pin.low();
							try{
								new Thread_run().start();
							}
							catch(Exception ed){
								ed.printStackTrace();
							}
							component.mediaPlayer().controls().setRepeat(true);
							component.mediaPlayer().media().play(intro_file);
						} else if (result == JOptionPane.YES_OPTION) {
							new Thread01().start();
							new Thread02().start();
						} else {
							tf = true;
							main.btn_mic.setEnabled(true);
							main.btn_admin.setEnabled(true);
							main.btn_mic.setIcon("./res/image/btn_micOff.png");	
							main.pa_center.remove(main.pa_center_center2);
							main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
							main.pa_center.revalidate();
							main.pa_center.repaint();
						
							main.pa_north.remove(main.return_main);
							main.pa_north.add(main.btn_admin, BorderLayout.EAST);
							main.pa_north.revalidate();
							main.pa_north.repaint();
												
							led_pin.low();
							try{
								new Thread_run().start();
							}
							catch(Exception ed){
								ed.printStackTrace();
							}
							component.mediaPlayer().controls().setRepeat(true);
							component.mediaPlayer().media().play(intro_file);
						}
					}
				}
				else if (rec.getStr_stt().contains("이동해")) {
					// 디쿠야 움직여
					if (rec.getStr_stt().contains("가이드")) {
					    led_pin.low();
					    main.btn_mic.setEnabled(true);
					    main.btn_mic.setIcon("./res/image/btn_micOff.png");
						main.pa_center_center2.label.setText("<html><body style='text-align:center;'> 목적지까지 안내해 드리겠습니다.<br/> 절 따라오세요.<br/></body></html>");
						main.pa_center.revalidate();
						main.pa_center.repaint();	
						
						
						main.pa_north.remove(main.btn_admin);
						main.pa_north.add(main.return_main, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();				
						
						sk.socket("디쿠 가이드로봇 이동");
						
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("바이오")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
							
						sk.socket("원데이 바이오 이동");			

						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("3번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
						
						main.pa_north.remove(main.btn_admin);
						main.pa_north.add(main.return_main, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();
											
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file3);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("4번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file4);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("5번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file5);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("6번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file6);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("7번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");		
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
									
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file7);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("8번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file8);
						rec.str_stt = "";
					} else if (rec.getStr_stt().contains("9번")) {
						led_pin.low();
						main.btn_mic.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();					
										
						component.mediaPlayer().controls().setRepeat(false);
						component.mediaPlayer().media().play(file9);
						rec.str_stt = "";
					}
					else {
						// 디쿠가 못 알아먹음
						int result = JOptionPane.showConfirmDialog(null, "음성인식 결과가 없습니다. 다시하시겠습니까?", "Confirm",
								JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.CLOSED_OPTION) {
							tf = true;
							main.btn_mic.setEnabled(true);
							main.btn_admin.setEnabled(true);
							main.btn_mic.setIcon("./res/image/btn_micOff.png");	
							main.pa_center.remove(main.pa_center_center2);
							main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
							main.pa_center.revalidate();
							main.pa_center.repaint();
						
							main.pa_north.remove(main.return_main);
							main.pa_north.add(main.btn_admin, BorderLayout.EAST);
							main.pa_north.revalidate();
							main.pa_north.repaint();						
			
							led_pin.low();
							try{
								new Thread_run().start();
							}
							catch(Exception ed){
								ed.printStackTrace();
							}
							component.mediaPlayer().controls().setRepeat(true);
							component.mediaPlayer().media().play(intro_file);
						} else if (result == JOptionPane.YES_OPTION) {
							new Thread01().start();
							new Thread02().start();
						} else {
							tf = true;
							main.btn_mic.setEnabled(true);
							main.btn_admin.setEnabled(true);
							main.btn_mic.setIcon("./res/image/btn_micOff.png");	
							main.pa_center.remove(main.pa_center_center2);
							main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
							main.pa_center.revalidate();
							main.pa_center.repaint();
						
							main.pa_north.remove(main.return_main);
							main.pa_north.add(main.btn_admin, BorderLayout.EAST);
							main.pa_north.revalidate();
							main.pa_north.repaint();
												
							led_pin.low();
							try{
								new Thread_run().start();
							}
							catch(Exception ed){
								ed.printStackTrace();
							}
							component.mediaPlayer().controls().setRepeat(true);
							component.mediaPlayer().media().play(intro_file);
						}
					}
				} else {
					// 디쿠가 못 알아먹음
					int result = JOptionPane.showConfirmDialog(null, "음성인식 결과가 없습니다. 다시하시겠습니까?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.CLOSED_OPTION) {
						tf = true;
						main.btn_mic.setEnabled(true);
						main.btn_admin.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
						
						main.pa_north.remove(main.return_main);
						main.pa_north.add(main.btn_admin, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();						
			
						led_pin.low();
						try{
							new Thread_run().start();
						}
						catch(Exception ed){
							ed.printStackTrace();
						}
						component.mediaPlayer().controls().setRepeat(true);
						component.mediaPlayer().media().play(intro_file);
					} else if (result == JOptionPane.YES_OPTION) {
						new Thread01().start();
						new Thread02().start();
					} else {
						tf = true;
						main.btn_mic.setEnabled(true);
						main.btn_admin.setEnabled(true);
						main.btn_mic.setIcon("./res/image/btn_micOff.png");	
						main.pa_center.remove(main.pa_center_center2);
						main.pa_center.add(main.pa_center_center, BorderLayout.CENTER);
						main.pa_center.revalidate();
						main.pa_center.repaint();
						
						main.pa_north.remove(main.return_main);
						main.pa_north.add(main.btn_admin, BorderLayout.EAST);
						main.pa_north.revalidate();
						main.pa_north.repaint();
												
						led_pin.low();
						try{
							new Thread_run().start();
						}
						catch(Exception ed){
							ed.printStackTrace();
						}
						component.mediaPlayer().controls().setRepeat(true);
						component.mediaPlayer().media().play(intro_file);
					}
				}
			}else {
				// rec.running()==false
			}
		}
	}
	
	class Thread_run extends Thread {
		
		public void run(){
			tf=true;
			System.out.println("Distance");
			while(tf) {
				try{
					sensorTriggerPin.low();
					Thread.sleep(500);
					sensorTriggerPin.high();
					Thread.sleep((long)0.01);
					sensorTriggerPin.low();
							
					while(sensorEchoPin.isLow()){}
					long startTime = System.nanoTime();
					while(sensorEchoPin.isHigh()){}
					long endTime=System.nanoTime();
					double distance = ((((endTime-startTime)/1e3)/2) / 29.1);
					System.out.println("Distance :"+distance + " cm");
					main.btn_admin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e){
								tf = false;
						}
					});
					if(distance < 50){
						new Thread01().start();
						new Thread02().start();
						break;
					}
					else{
						led_pin.low();		
					}
					Thread.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		new GUI();
	}
}



/*
 * class AudioScreen extends JPanel{ //*시작 위쪽 패널에 넣을 컴포넌트 생성 ImageButton
 * btn_admin = new ImageButton("./res/image/btn_admin.png"); //오른쪽에 관리자페이지 버튼의
 * 이미지로 학교 로고 이미지를 사용 ImagePanel pa_mainTitle = new
 * ImagePanel("./res/image/pa_mainTitle.png"); //중간에 제목 패널로 학교 입시박람회 글귀 이미지를 사용
 * ImageButton btn_mic = new ImageButton("./res/image/btn_micOn.png"); //왼쪽에
 * 음성인식 버튼으로 처음엔 마이크꺼진 이미지를 사용(클릭시 마이크켜진 이미지로 바꿈) ImageButton btn_micOn = new
 * ImageButton("./res/image/btn_micOn.png"); //왼쪽에 음성인식 버튼으로 처음엔 마이크꺼진 이미지를
 * 사용(클릭시 마이크켜진 이미지로 바꿈) //*끝 위쪽 패널에 넣을 컴포넌트 생성
 * 
 * 
 * JPanel pa_north = new JPanel(); //컨테이너 BorderLayout 위쪽에 넣을 패널 생성(여기에 방금선언한
 * 컴포넌트 넣기) JPanel pa_center = new JPanel(); //컨테이너 BorderLayout 중간에 넣을 패널
 * 생성(여기에 아마 동영상 재생창 넣을듯) JPanel pa_center_center = new JPanel(); JPanel
 * pa_center_north = new JPanel(); JPanel pa_center_south = new JPanel(); JPanel
 * pa_center_west = new JPanel(); JPanel pa_center_east = new JPanel(); public
 * AudioScreen() { setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLayout(new
 * BorderLayout()); this.setBackground(background); //패널 배경색은 남색으로
 * btn_mic.setEnabled(false);
 * 
 * pa_north.setBackground(background); //위쪽 패널 배경색도 남색으로
 * pa_center.setBackground(background); // 중간 패널 배경색도 남색으로
 * 
 * pa_center_north.setBackground(background);
 * pa_center_north.setPreferredSize(new Dimension(res.height/20,
 * res.height/20));
 * 
 * pa_center_south.setBackground(background);
 * pa_center_south.setPreferredSize(new Dimension(res.height/20,
 * res.height/20));
 * 
 * pa_center_west.setBackground(background); pa_center_west.setPreferredSize(new
 * Dimension(res.height/20, res.height/20));
 * 
 * pa_center_east.setBackground(background); pa_center_east.setPreferredSize(new
 * Dimension(res.height/20, res.height/20));
 * 
 * pa_north.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로
 * 
 * this.add(pa_north, BorderLayout.NORTH); //전체 패널 BorderLayout 위쪽에는 생성한
 * pa_north패널 부착 pa_north.add(btn_mic, BorderLayout.WEST); //pa_north 서쪽에는 음성인식
 * 버튼 부착 pa_north.add(pa_mainTitle, BorderLayout.CENTER); //pa_north 가운데에는 타이틀
 * 패널 부착 pa_north.add(btn_admin, BorderLayout.EAST); //pa_north 동쪽에는 관리자페이지 이동
 * 버튼 부착
 * 
 * btn_mic.setPreferredSize(new Dimension(res.height/7, res.height/7));
 * pa_mainTitle.setPreferredSize(new Dimension(1300, res.height/6));
 * btn_admin.setPreferredSize(new Dimension(res.height/7, res.height/7));
 * 
 * 
 * pa_center.setLayout(new BorderLayout()); // 위쪽 패널도 BorderLayout형식으로
 * pa_center.add(pa_center_center, BorderLayout.CENTER);
 * pa_center.add(pa_center_north, BorderLayout.NORTH);
 * pa_center.add(pa_center_south, BorderLayout.SOUTH);
 * pa_center.add(pa_center_east, BorderLayout.EAST);
 * pa_center.add(pa_center_west, BorderLayout.WEST);
 * 
 * this.add(pa_center, BorderLayout.CENTER);
 * 
 * btn_admin.setBorderPainted(false); btn_admin.setFocusPainted(false); }
 * 
 * }
 */
