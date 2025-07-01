//*****************************************************************************

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//*****************************************************************************
public class Main {

	//=========================================================================
	private Main() {}
	//=========================================================================

	//=========================================================================
	private DatagramSocket newSocket() {
		try {
			var port    = 1000;
			byte[] address = new byte[] { (byte) 192, (byte) 168, (byte) 178, (byte) 45}; 
			var socket = new DatagramSocket();
			socket.connect(InetAddress.getByAddress(address), port);
			return socket;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//=========================================================================

	//=========================================================================
	private String formatJsonMove(
			int id,
			long duration,
			int a, int b, int c, int d) {
		return String.format(
			"{\"id\":%s,\"cmd\":\"move\",\"duration\":%s,\"values\":[%s,%s,%s,%s]}",
			id, duration, a, b, c, d
		);		
	}
	//=========================================================================

	//=========================================================================
	private String formatJsonMoveRel(
			int id,
			long duration,
			float a, float b, float c, float d) {
		return String.format(
			"{\"id\":%s,\"cmd\":\"move_rel\",\"duration\":%s,\"values\":[%s,%s,%s,%s]}",
			id, duration, a, b, c, d
		);		
	}
	//=========================================================================
	
	//=========================================================================
	private int idgen = 0;
	//=========================================================================

	//=========================================================================
	private void submit_move_rel(long duration, float a, float b, float c, float d) {
		try {
			var id = idgen++;
			var json = formatJsonMoveRel(id, duration, a, b, c, d);
			System.out.println("==> JSON: " + json);
			var raw  = json.getBytes();
			var packet = new DatagramPacket(raw, raw.length);
			var socket = newSocket();
			socket.send(packet);
			Thread.sleep(50);
			socket.send(packet);
			Thread.sleep(50);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//=========================================================================

	//=========================================================================
	private void submit_move(long duration, int a, int b, int c, int d) {
		try {
			var id = idgen++;
			var json = formatJsonMove(id, duration, a, b, c, d);
			System.out.println("==> JSON: " + json);
			var raw  = json.getBytes();
			var packet = new DatagramPacket(raw, raw.length);
			var socket = newSocket();
			socket.send(packet);
			Thread.sleep(50);
			socket.send(packet);
			Thread.sleep(50);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//=========================================================================

	//=========================================================================
	private void run() {
		try {
			var reader = new InputStreamReader(System.in);
			var buffer = new BufferedReader(reader);		
			while (true) {
				System.out.print(">>");
				var line = buffer.readLine();
				if (line != null) {
					var parts = line.split(" +");
					if (parts[0].equals("move")) {
						var duration = Long.parseLong(parts[1]);
						var a = Integer.parseInt(parts[2]);
						var b = Integer.parseInt(parts[3]);
						var c = Integer.parseInt(parts[4]);
						var d = Integer.parseInt(parts[5]);
						System.out.printf("==> MOVE - duration: %s, a:%s, b:%s, c:%s, d:%s", duration, a, b, c, d);
						System.out.println();
						submit_move(duration, a, b, c, d);
					} if (parts[0].equals("move_rel")) {
						var duration = Long.parseLong(parts[1]);
						var a = Float.parseFloat(parts[2]);
						var b = Float.parseFloat(parts[3]);
						var c = Float.parseFloat(parts[4]);
						var d = Float.parseFloat(parts[5]);
						System.out.printf("==> MOVE REL - duration: %s, a:%s, b:%s, c:%s, d:%s", duration, a, b, c, d);
						System.out.println();
						submit_move_rel(duration, a, b, c, d);
					} else if (parts[0].equals("l") || parts[0].equals("linear")) {
						var duration = Long.parseLong(parts[1]);
						var speed = Integer.parseInt(parts[2]);
						System.out.printf("==> MOVE - duration: %s, a:%s, b:%s, c:%s, d:%s", duration, speed, speed, speed, speed);
						System.out.println();
						submit_move(duration, speed, speed, speed, speed);
					} else if (parts[0].equals("r") || parts[0].equals("rotation")) {
						var duration = Long.parseLong(parts[1]);
						var speed = Integer.parseInt(parts[2]);
						System.out.printf("==> MOVE - duration: %s, a:%s, b:%s, c:%s, d:%s", duration, speed, -speed, speed, -speed);
						System.out.println();
						submit_move(duration, speed, -speed, speed, -speed);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	//=========================================================================

}
//*****************************************************************************
