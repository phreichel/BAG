//*************************************************************************************************
package bot;
//*************************************************************************************************

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.jogamp.newt.event.MouseEvent;

//*************************************************************************************************
public class MapCanvas extends JPanel implements MouseListener, MouseMotionListener {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private double offset = 0;
	private double scale  = 1;
	private Map<Integer, Integer> measures = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public MapCanvas() {
		this.setPreferredSize(new Dimension(1920, 1080));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.RED);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	//=============================================================================================

	//=============================================================================================
	public void update(Map<Integer, Integer> measures) {
		this.measures.clear();
		synchronized (measures) {
			this.measures.putAll(measures);
		}
		this.invalidate();
		this.repaint(10);
	}
	//=============================================================================================

	//=============================================================================================
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0,  0, this.getWidth(), this.getHeight());
		int ox = this.getWidth() / 2;
		int oy = this.getHeight() / 2;
		g.setColor(Color.RED);
		g.fillOval(ox-2, oy-2, 5, 5);
		List<Integer> sorted = new ArrayList<>(measures.keySet());
		sorted.sort((a, b) -> a.compareTo(b));
		g.setColor(getForeground());
		int prevx = -1;
		int prevy = -1;
		for (int i=0; i<=sorted.size(); i++) {
			var a = sorted.get(i%sorted.size());
			var r = Math.toRadians(a+offset);
			var d = filtered(a, 5);
			int x = (int) Math.rint(ox + Math.cos(r) * d * scale);
			int y = (int) Math.rint(oy + Math.sin(r) * d * scale);
			if (prevx != -1) {
				g.drawLine(prevx, prevy, x, y);
			}
			prevx = x;
			prevy = y;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private int filtered(int a, int delta) {
		var c = 0;
		var d = 0;
		for (var aa=a-delta; aa<=a+delta; aa++) {
			var ma = (aa + 360) % 360;
			var m = measures.get(ma);
			if (m != null) {
				c++;
				d += m;
			}
		}
		return d / c;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean editMode = false;
	private int     oldMouseX = 0;
	private int     oldMouseY = 0;
	//=============================================================================================
	
	//=============================================================================================
	public void mouseClicked(java.awt.event.MouseEvent e) {}
	//=============================================================================================

	//=============================================================================================
	public void mousePressed(java.awt.event.MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			editMode = true;
			oldMouseX = e.getXOnScreen();
			oldMouseY = e.getYOnScreen();
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseReleased(java.awt.event.MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			editMode = false;
			oldMouseX = 0;
			oldMouseY = 0;
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseEntered(java.awt.event.MouseEvent e) {}
	public void mouseExited(java.awt.event.MouseEvent e) {}
	//=============================================================================================

	//=============================================================================================
	public void mouseDragged(java.awt.event.MouseEvent e) {
		if (editMode) {
			
			int x = e.getXOnScreen();
			int y = e.getYOnScreen();
			int dx = x - oldMouseX;
			int dy = y - oldMouseY;
			oldMouseX = x;
			oldMouseY = y;
			
			offset += 0.1   * dx;
			scale  += 0.001 * dy;
			
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseMoved(java.awt.event.MouseEvent e) {}
	//=============================================================================================
	
}
//*************************************************************************************************

