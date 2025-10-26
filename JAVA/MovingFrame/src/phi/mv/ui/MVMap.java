//*************************************************************************************************
package phi.mv.ui;
//*************************************************************************************************

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import phi.mv.data.MVConfiguration;
import phi.mv.msg.MVMsgManager;

//*************************************************************************************************
public class MVMap extends JPanel implements MouseListener, MouseMotionListener  {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private static final int MARGIN = 15;
	//=============================================================================================

	//=============================================================================================
	private float scale;
	private float minx, miny, maxx, maxy;
	private int range, basey;
	//=============================================================================================

	//=============================================================================================
	private List<Integer> preX = new ArrayList<>();
	private List<Integer> preY = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	private MVConfigurationPanel cfg;
	//=============================================================================================


	//=============================================================================================
	private JButton preBack  = new JButton("BACK");
	private JButton preClear = new JButton("CLEAR");
	private JButton preExec  = new JButton("EXEC");
	//=============================================================================================
	
	
	//=============================================================================================
	public MVMap() {
		
		Dimension dimMap = new Dimension(600, 600);
		Dimension dimButton = new Dimension(80, 20);

		preBack.setLocation(15, 15);
		preBack.setSize(dimButton);
		preBack.addActionListener(this::backAction);
		
		preClear.setLocation(15, 40);
		preClear.setSize(dimButton);
		preClear.addActionListener(this::clearAction);

		preExec.setLocation(15, 65);
		preExec.setSize(dimButton);
		preExec.addActionListener(this::execAction);
		
		this.setPreferredSize(dimMap);
		this.setSize(dimMap);
		
		this.setOpaque(true);
		this.setBackground(Color.BLUE);
		this.setForeground(Color.ORANGE);
		this.setLayout(null);

		this.add(preBack);
		this.add(preClear);
		this.add(preExec);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void inject(MVConfigurationPanel cfg) {
		this.cfg = cfg;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void rescale() {

		range = Math.min(this.getWidth(), this.getHeight()) - MARGIN * 2;
		
		MVConfiguration cfg = MVConfiguration.data;

		minx = Float.POSITIVE_INFINITY; 
		minx = Math.min(minx, cfg.pivots.a.x);
		minx = Math.min(minx, cfg.pivots.b.x);
		minx = Math.min(minx, cfg.pivots.c.x);
		minx = Math.min(minx, cfg.pivots.d.x);

		maxx = Float.NEGATIVE_INFINITY; 
		maxx = Math.max(maxx, cfg.pivots.a.x);
		maxx = Math.max(maxx, cfg.pivots.b.x);
		maxx = Math.max(maxx, cfg.pivots.c.x);
		maxx = Math.max(maxx, cfg.pivots.d.x);
		
		miny = Float.POSITIVE_INFINITY; 
		miny = Math.min(miny, cfg.pivots.a.y);
		miny = Math.min(miny, cfg.pivots.b.y);
		miny = Math.min(miny, cfg.pivots.c.y);
		miny = Math.min(miny, cfg.pivots.d.y);

		maxy = Float.NEGATIVE_INFINITY; 
		maxy = Math.max(maxy, cfg.pivots.a.y);
		maxy = Math.max(maxy, cfg.pivots.b.y);
		maxy = Math.max(maxy, cfg.pivots.c.y);
		maxy = Math.max(maxy, cfg.pivots.d.y);
		
		float spanx = maxx - minx; 
		float spany = maxy - miny;
		float span = Math.max(spanx, spany);
		scale = range / span;

		basey = range + MARGIN;
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void paint(Graphics g) {

		setOpaque(true);
		super.paint(g);
		
		rescale();
		
		MVConfiguration cfg = MVConfiguration.data;
		
		float ax = MARGIN + (cfg.pivots.a.x - minx) * scale;
		float ay = basey  - (cfg.pivots.a.y - miny) * scale;

		float bx = MARGIN + (cfg.pivots.b.x - minx) * scale;
		float by = basey  - (cfg.pivots.b.y - miny) * scale;

		float cx = MARGIN + (cfg.pivots.c.x - minx) * scale;
		float cy = basey  - (cfg.pivots.c.y - miny) * scale;

		float dx = MARGIN + (cfg.pivots.d.x - minx) * scale;
		float dy = basey  - (cfg.pivots.d.y - miny) * scale;

		float hx = MARGIN + (cfg.headPosition.x - minx) * scale;
		float hy = basey  - (cfg.headPosition.y - miny) * scale;
		int r    = (int) (cfg.headRadius * scale);

		g.setColor(Color.BLUE.darker().darker());
		g.fillRect(5, 5, range + 2 * MARGIN - 10, range + 2 * MARGIN - 10);
		
		g.setColor(Color.GREEN);
		g.drawLine((int) hx, (int) hy, (int) ax, (int) ay);
		g.drawLine((int) hx, (int) hy, (int) bx, (int) by);
		g.drawLine((int) hx, (int) hy, (int) cx, (int) cy);
		g.drawLine((int) hx, (int) hy, (int) dx, (int) dy);
		
		g.setColor(Color.ORANGE);
		g.fillOval((int) ax - 3, (int) ay - 3, 7, 7);
		g.fillOval((int) bx - 3, (int) by - 3, 7, 7);
		g.fillOval((int) cx - 3, (int) cy - 3, 7, 7);
		g.fillOval((int) dx - 3, (int) dy - 3, 7, 7);
		g.drawString("A", (int) ax + 7, (int) ay + 5);
		g.drawString("B", (int) bx + 7, (int) by + 5);
		g.drawString("C", (int) cx + 7, (int) cy + 5);
		g.drawString("D", (int) dx + 7, (int) dy + 5);

		g.setColor(Color.RED);
		g.drawOval((int) hx - r, (int) hy - r, 2*r, 2*r);
		g.fillOval((int) hx - 3, (int) hy - 3, 7, 7);
		g.drawString("Hd", (int) hx + 7, (int) hy + 5);

		g.setColor(Color.GRAY);
		int xbefore = (int) hx; 
		int ybefore = (int) hy;
		for (int i=0; i<preX.size(); i++) {
			int xnow = preX.get(i);
			int ynow = preY.get(i);
			g.drawLine(xbefore, ybefore, xnow, ynow);
			xbefore = xnow;
			ybefore = ynow;
		}

		setOpaque(false);
		super.paint(g);
		
	}
	//=============================================================================================

	//=============================================================================================
	private int prevX = 0;
	private int prevY = 0;
	//=============================================================================================
	
	//=============================================================================================
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		if (e.getButton() == MouseEvent.BUTTON3) {
			
			rescale();
			
			float tx = (float) Math.rint((x - MARGIN) / scale + minx);
			float ty = (float) Math.rint((basey - y) / scale + miny);
			
			if (MVMsgManager.msg != null) {
				MVMsgManager.msg.moveTo(tx, ty);
				cfg.loadConfig();
			}
			
		}
		/*
		else if (e.getButton() == MouseEvent.BUTTON1) {

			preX.add(x);
			preY.add(y);
			
			prevX = x;
			prevY = y;
			
			repaint();
			
		}
		*/
  
	}
	//=============================================================================================

	//=============================================================================================
	public void clearAction(ActionEvent e) {

		preX.clear();
		preY.clear();
		cfg.loadConfig();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void backAction(ActionEvent e) {
		if (preX.size() > 0) {
			preX.remove(preX.size()-1);
			preY.remove(preY.size()-1);
			cfg.loadConfig();
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void execAction(ActionEvent e) {
		if ((preX.size() > 0) && (MVMsgManager.msg != null)) {
			
			rescale();
			
			float orgx = MVConfiguration.data.headPosition.x;
			float orgy = MVConfiguration.data.headPosition.y;
			
			for (int i=0; i<preX.size(); i++) {
				
				int x = preX.get(i);
				int y = preY.get(i);
				
				float tx = (float) Math.rint((x - MARGIN) / scale + minx);
				float ty = (float) Math.rint((basey - y) / scale + miny);
				
				float dx = tx-orgx;
				float dy = ty-orgy;
				
				float length = (float) Math.sqrt(dx*dx + dy*dy);
				
				float splice = 10f;
				while (splice < length) {
					float scale = splice / length;
					MVMsgManager.msg.moveTo(orgx + dx * scale, orgy + dy * scale);
					splice += 10f;
				}
				
				MVMsgManager.msg.moveTo(tx, ty);

				orgx = tx;
				orgy = ty;
				
			}
			
			preX.clear();
			preY.clear();
			
			cfg.loadConfig();
			
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void mouseEntered(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseExited(MouseEvent e) {
		this.setCursor(Cursor.getDefaultCursor());
	}
	//=============================================================================================
	
	//=============================================================================================
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		prevX = x;
		prevY = y;

		if (e.getButton() == MouseEvent.BUTTON1) {
			
			preX.add(x);
			preY.add(y);

			prevX = x;
			prevY = y;
			
			repaint();
			
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseDragged(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		float dx = (x-prevX);
		float dy = (y-prevY);
		float dlSqr = dx*dx + dy*dy;
		if (dlSqr <= (5*5)) return;
		
		if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
	
			preX.add(x);
			preY.add(y);

			prevX = x;
			prevY = y;
			
			repaint();
			
		}

	}
	//=============================================================================================
	
	//=============================================================================================
	public void mouseReleased(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		if ((x == prevX) && (y == prevY)) return;
		
		if (e.getButton() == MouseEvent.BUTTON1) {
	
			preX.add(x);
			preY.add(y);

			prevX = x;
			prevY = y;
			
			repaint();
			
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseMoved(MouseEvent e) {}
	//=============================================================================================

			
}
//*************************************************************************************************

