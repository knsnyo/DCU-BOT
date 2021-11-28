package src;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Record {
	// variable to sending at program
	protected String[] data;
	protected String str_stt = " ";
	private String kakao_stt_url = "https://kakaoi-newtone-openapi.kakao.com/v1/recognize";
	private String rest_api_key = "c01abba110b4e11f5b7257ff54237910";

	protected boolean running;
	protected ByteArrayOutputStream out;
	protected AudioFormat format;
	protected AudioInputStream ais;
	
	
	public String changeVoiceToText() {
		try {
			// URL connect
			URL url = new URL(kakao_stt_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// send by POST
			connection.setRequestMethod("POST");

			// add header
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestProperty("Authorization", "KakaoAK " + rest_api_key);

			// set for send data
			connection.setDoOutput(true);

			// set ConnectTimeOut and ReadTimeOut
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);

			// load voice file
			File file = new File(".\\res\\voice\\voice.wav");
			FileInputStream fis = new FileInputStream(file);

			// change file to byteArray for send data
			byte[] bytes = fis.readAllBytes();

			// send data
			OutputStream os = connection.getOutputStream();
			os.write(bytes);
			os.flush();

			// receive data
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String input_line;

			// search final result
			while ((input_line = br.readLine()) != null) {
				// System.out.println(input_line);
				if (input_line.contains("finalResult")) {
					break;
				}
			}
			// split data for finding final result
			data = input_line.split("\"");

			// check final result at console
			//System.out.println("결과 문자열: " + data[7]);
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			return "인식이 되지 않았습니다.";
		}
		return data[7];
	}

	public void recordVoice() {
		// record your voice
		try {
			// set format
			format = getFormat();

			// build TargetDataLine in format
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);

			// open line and input/output to data
			line.open(format);
			line.start();

			// set record thread
			Runnable runner = new Runnable() {
				// set buffer in format
				int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
				byte buffer[] = new byte[bufferSize];

				// function of thread
				public void run() {
					out = new ByteArrayOutputStream();
					running = true;
					try {
						while (running) {
							int count = line.read(buffer, 0, buffer.length);
							if (count > 0) {
								out.write(buffer, 0, count);
							}
						}
						// close line and block input/output to data
						out.close();
						line.close();
					} catch (IOException e) {
						System.err.println("1. Recording Error: " + e);
						System.exit(-1);
					}
				}
			};
			// run record thread
			Thread recordThread = new Thread(runner);
			recordThread.start();
		} catch (LineUnavailableException e) {
			System.err.println("2. Line unavailable: " + e);
			System.exit(-2);
		}
	}

	public void saveVoiceToFile() {
		// saving voice to wave file
		try {
			// make file to save voice
			File voice_file = new File(".\\res\\voice\\voice.wav");

			// change voice file to byte array for saving file
			final byte voice_file_byte[] = out.toByteArray();
			final ByteArrayInputStream bais = new ByteArrayInputStream(voice_file_byte);

			// save bais to ais according to format
			ais = new AudioInputStream(bais, format, voice_file_byte.length / format.getFrameSize());

			// save voice at voice.wav
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, voice_file);
		} catch (IOException e) {
			System.err.println("파일 저장의 문제:  " + e);
			System.exit(-1);
		}
	}

	public AudioFormat getFormat() {
		// setting audio file to use KAKAO STT API
		float sampleRate = 16000.0F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public String getStr_stt() {
		return str_stt;
	}
	public void setStr_stt() {
		str_stt=changeVoiceToText();
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
