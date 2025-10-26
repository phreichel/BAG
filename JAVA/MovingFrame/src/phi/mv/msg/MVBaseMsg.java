//*************************************************************************************************
package phi.mv.msg;
//*************************************************************************************************

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import javax.net.SocketFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import phi.mv.data.MVConfiguration;
import phi.mv.ui.MVControlPanel;
import phi.mv.ui.MVControlPanel.MVDirection;

//*************************************************************************************************
public class MVBaseMsg {

	//=============================================================================================
	private static ObjectMapper objectMapper = new ObjectMapper();
	//=============================================================================================

	//=============================================================================================
	private Socket socket;
	private BufferedReader is;
	private BufferedWriter os;
	//=============================================================================================

	//=============================================================================================
	private Queue<MVMsgMove> moveQueue = new LinkedList<>();
	private Thread           moveThread;
	private boolean          moveThreadStop = true;
	//=============================================================================================
	
	//=============================================================================================
	public void open(String host, int port) {
		
		SocketFactory factory = SocketFactory.getDefault();
		try {
			socket = factory.createSocket(host, port);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		moveQueue.clear();
		moveThreadStop = false;
		moveThread = new Thread(this::runQueue);
		moveThread.start();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void close() {

		try {
			os.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			is.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		socket = null;
		is = null;
		os = null;
		
		moveThreadStop = true;
		while (true) {
			try {
				moveThread.join();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		moveQueue.clear();
		
	}
	//=============================================================================================

	//=============================================================================================
	protected void message(String request) {
		MVBaseMsg.message(os, is, request);
	}
	//=============================================================================================

	//=============================================================================================
	public void ping() {
		
		MVMsgPing ping = new MVMsgPing("HELO");
		String data = "";
		
		try {
			data = objectMapper.writeValueAsString(ping);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		message(data);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void single(MVControlPanel.MVMotor motor, MVControlPanel.MVDirection direction, int steps) {
		
		if (direction.equals(MVDirection.RETRACT)) steps = -steps;
		MVMsgSingle single = new MVMsgSingle(motor, steps);

		String data = "";
		try {
			data = objectMapper.writeValueAsString(single);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		message(data);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void moveBy(float length, float angle) {
		
		if (MVMsgManager.msg == null) return;
		
		float dx = (float) Math.cos(angle) * length;
		float dy = (float) Math.sin(angle) * length;
		
		float ox = MVConfiguration.data.headPosition.x;
		float oy = MVConfiguration.data.headPosition.y;
		
		float tx = ox + dx;
		float ty = oy + dy;

		moveTo(tx, ty);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void moveTo(float tx, float ty) {

		float ox = MVConfiguration.data.headPosition.x;
		float oy = MVConfiguration.data.headPosition.y;
		
		MVConfiguration.data.headPosition.x = tx;
		MVConfiguration.data.headPosition.y = ty;
		MVConfiguration.store();
		
		float oax = ox - MVConfiguration.data.pivots.a.x;
		float oay = oy - MVConfiguration.data.pivots.a.y;
		float oa  = (float) Math.sqrt(oax * oax + oay * oay);

		float obx = ox - MVConfiguration.data.pivots.b.x;
		float oby = oy - MVConfiguration.data.pivots.b.y;
		float ob  = (float) Math.sqrt(obx * obx + oby * oby);

		float ocx = ox - MVConfiguration.data.pivots.c.x;
		float ocy = oy - MVConfiguration.data.pivots.c.y;
		float oc  = (float) Math.sqrt(ocx * ocx + ocy * ocy);

		float odx = ox - MVConfiguration.data.pivots.d.x;
		float ody = oy - MVConfiguration.data.pivots.d.y;
		float od  = (float) Math.sqrt(odx * odx + ody * ody);

		float tax = tx - MVConfiguration.data.pivots.a.x;
		float tay = ty - MVConfiguration.data.pivots.a.y;
		float ta  = (float) Math.sqrt(tax * tax + tay * tay);

		float tbx = tx - MVConfiguration.data.pivots.b.x;
		float tby = ty - MVConfiguration.data.pivots.b.y;
		float tb  = (float) Math.sqrt(tbx * tbx + tby * tby);

		float tcx = tx - MVConfiguration.data.pivots.c.x;
		float tcy = ty - MVConfiguration.data.pivots.c.y;
		float tc  = (float) Math.sqrt(tcx * tcx + tcy * tcy);

		float tdx = tx - MVConfiguration.data.pivots.d.x;
		float tdy = ty - MVConfiguration.data.pivots.d.y;
		float td  = (float) Math.sqrt(tdx * tdx + tdy * tdy);

		float dlta = ta - oa;
		float dltb = tb - ob;
		float dltc = tc - oc;
		float dltd = td - od;
		
		float CIRCUM  = (float) (2.0 * Math.PI * MVConfiguration.data.rotation.rotationRadius);
		float STEPLEN = MVConfiguration.data.rotation.stepsPerRotation / CIRCUM;
		
		float cda = dlta * STEPLEN;
		float cdb = dltb * STEPLEN;
		float cdc = dltc * STEPLEN;
		float cdd = dltd * STEPLEN;
		
		int stpa = (int) Math.rint(cda);
		int stpb = (int) Math.rint(cdb);
		int stpc = (int) Math.rint(cdc);
		int stpd = (int) Math.rint(cdd);
		
		move(stpa, stpb, stpc, stpd);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void move(int a, int b, int c, int d) {
		
		MVMsgMove move = new MVMsgMove(a, b, c, d);
		synchronized (moveQueue) {
			moveQueue.offer(move);
		}

	}
	//=============================================================================================

	//=============================================================================================
	private void runQueue() {
		System.out.println("Move Thread Started");
		while (!moveThreadStop) {
			move();
		}
		System.out.println("Move Thread Stopped");
	}
	//=============================================================================================
	
	//=============================================================================================
	private void move() {

		MVMsgMove move = null;
		
		synchronized (moveQueue) {
			move = moveQueue.poll();
		}
		
		if (move != null) {

			String data = "";
			try {
				data = objectMapper.writeValueAsString(move);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			message(data);
			
			try {
				is.read();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void disconnect() {
		
		MVMsgDisconnect disconnect = new MVMsgDisconnect();
		String data = "";
		
		try {
			data = objectMapper.writeValueAsString(disconnect);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		message(data);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void reset() {
		
		MVMsgReset reset = new MVMsgReset();
		String data = "";
		
		try {
			data = objectMapper.writeValueAsString(reset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		message(data);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	protected static void message(BufferedWriter os, BufferedReader is, String request) {
		try {
			os.write(request);
			os.newLine();
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
