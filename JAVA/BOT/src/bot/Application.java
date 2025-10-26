//*************************************************************************************************
package bot;
//*************************************************************************************************

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import com.fazecast.jSerialComm.SerialPort;

//*************************************************************************************************
public class Application {

	//=============================================================================================
	private static final String ARDUINO_PORT_NAME = "Arduino Uno (COM3)";
	//=============================================================================================

	//=============================================================================================
	private Thread thread = null;
	private Map<Integer, Integer> measures = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private void runListener() {
		SerialPort port = null;
		var ports = SerialPort.getCommPorts();
		for (var p : ports) {
			if (p.getDescriptivePortName().equals(ARDUINO_PORT_NAME)) {
				port = p;
			}
		}
		if (port == null)
			System.err.println("Could not find port " + ARDUINO_PORT_NAME);
		else {
			System.out.println("OPENING PORT");
			int cnt = 0;
			while (!port.openPort(10)) {
				cnt++;
				System.out.print(".");
				if (cnt == 80) {
					System.out.println();
					cnt = 0;
				}
			}
			System.out.println("PORT OPENED");
			var istream = port.getInputStream();
			String cmd = "";
			while (true) {
				try {
					if (istream.available() > 0) {
						int res = istream.read();
						char c = (char) res;
						cmd += c;
						if (c == '\n') {
							cmd = cmd.trim();
							System.out.println(cmd);
							var parts = cmd.split(" ");
							if (parts.length == 4) {
								int a = (Integer.parseInt(parts[1])) % 360;
								int d = Integer.parseInt(parts[3]);
								synchronized (measures) {
									Integer prev = measures.get(a);
									if      (prev == null) measures.put(a, d);
									else if (prev == 1182) measures.put(a, d);
									else if (d == 1182)    measures.put(a, prev);
									else                   measures.put(a, (prev + d) / 2);
								}
							}
							cmd = "";
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void run() {
		thread = new Thread(this::runListener);
		thread.setDaemon(true);
		thread.start();
		JFrame   frame  = new JFrame("See It!");
		MapCanvas canvas = new MapCanvas();
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		while (frame.isVisible()) {
			canvas.update(measures);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
