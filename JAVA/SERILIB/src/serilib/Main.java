//**************************************************************************************************
package serilib;
//**************************************************************************************************

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;

//**************************************************************************************************
public class Main {

	//==============================================================================================
	public static void main(String[] args) throws Exception {

		String portName = "ttyUSB0"; // oder "COM3" unter Windows

		SerialPort port = SerialPort.getCommPort(portName);
		port.setBaudRate(9600);
		port.setNumDataBits(8);
		port.setNumStopBits(SerialPort.ONE_STOP_BIT);
		port.setParity(SerialPort.NO_PARITY);
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 0);
		
		// robuster
		//port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);


		System.out.println("Öffne Port: " + port.getSystemPortName());

		port.clearDTR(); // deaktiviert DTR → verhindert Reset
		port.clearRTS(); // deaktiviert RTS → verhindert Reset noch sicherer.
		
		if (!port.openPort()) {
			System.out.println("Konnte Port nicht öffnen.");
			return;
		}
		

		System.out.println("Port geöffnet. Warte auf Daten...");

		InputStream in = port.getInputStream();
		byte[] buffer = new byte[1024];

		while (true) {
			int len = in.read(buffer);
			if (len > 0) {
				String data = new String(buffer, 0, len);
				System.out.print(data); // kein println → Zeilen vom Arduino bleiben erhalten
			}
		}
	}
	//==============================================================================================

}
//**************************************************************************************************
