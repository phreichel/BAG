//*************************************************************************************************
package rbr;
//*************************************************************************************************

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//*************************************************************************************************
public class Main implements AWTEventListener {

	//=============================================================================================
	private Frame frame;
	//=============================================================================================
	
	//=============================================================================================
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	//=============================================================================================

	//=============================================================================================
	boolean connected = false;
	boolean terminated = false;
	boolean triggered = false;
	private int m1 = 0;
	private int m2 = 0;
	//=============================================================================================
	
	//=============================================================================================
	private void init()throws Exception {
		
		frame = new Frame();
		frame.setSize(800, 600);
		frame.setVisible(true);
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
		System.out.println("EVENTS.");
		
		setMotors(m1, m2);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void connect() throws Exception {
		if (!connected) {
			socket = new Socket("192.168.178.79", 80);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("CONNECTED.");
			connected = true;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void setMotors(int m1, int m2) {
		try {
			connect();
			String cmd = String.format("[%s,%s]%n", m1, m2);
			System.out.println(cmd);
			writer.append(cmd);
			writer.flush();
		} catch (Exception e) {
			connected = false;
			System.out.println("DISCONNECT");
			try {
				done();
			} catch (Exception x) {}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void done()throws Exception {
		reader.close();
		writer.close();
		socket.close();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void run() throws Exception {
		init();
		while (!terminated) {
			update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		done();
		frame.dispose();
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.run();
	}
	//=============================================================================================

	//=============================================================================================
	public void eventDispatched(AWTEvent event) {

		KeyEvent e = (KeyEvent) event;
		
		int keyEvent = e.getID();
		int keyCode  = e.getKeyCode();
		
		switch (keyCode) {
			case KeyEvent.VK_X:
				terminated = true;
				break;
			case KeyEvent.VK_UP:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m1 = 200;
					m2 = 200;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m1 = 0;
					m2 = 0;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m1 = -200;
					m2 = -200;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m1 = 0;
					m2 = 0;
				}
				break;
			case KeyEvent.VK_LEFT:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m1 =  200;
					m2 = -200;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m1 = 0;
					m2 = 0;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m1 = -200;
					m2 =  200;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m1 = 0;
					m2 = 0;
				}
				break;
			case KeyEvent.VK_Q:
				if (keyEvent == KeyEvent.KEY_PRESSED) {					
					triggered = true;
					m1 = 255;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m1 = 0;
				}
				break;
			case KeyEvent.VK_A:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m1 = -255;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m1 = 0;
				}
				break;
			case KeyEvent.VK_E:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m2 = 255;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m2 = 0;
				}
				break;
			case KeyEvent.VK_D:
				if (keyEvent == KeyEvent.KEY_PRESSED) {
					triggered = true;
					m2 = -255;
				}
				else if (keyEvent == KeyEvent.KEY_RELEASED) {
					triggered = true;
					m2 = 0;
				}
				break;
			case KeyEvent.VK_S:
				triggered = true;
				m1 = 0;
				m2 = 0;
				break;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void update() {
		if (triggered) {
			triggered = false;
			setMotors(m1, m2);
		}
	}
	//=============================================================================================

}
//*************************************************************************************************