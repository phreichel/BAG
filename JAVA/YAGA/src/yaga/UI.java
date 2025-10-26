//*****************************************************************************
package yaga;
//*****************************************************************************

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//*****************************************************************************
public class UI implements Video2D {

	//=========================================================================
	static class Star {
		public Star() {
			x  = (float) Math.random(); 
			y  = (float) Math.random();
			a  = (float) (Math.random() * 360);
			s  = (float) (Math.random() * .75 + .25);			
			t  = (float) (Math.random() * 30);
			vy = (float) ((Math.random() * .75 + .25) * .1);
			va = (float) ((Math.random()-.5) * 2 * 180);
		}
		public float x;
		public float y;
		public float a;
		public float s;
		public float t;
		public float vy;
		public float va;
	}
	//=========================================================================
	
	//=========================================================================
	private Map<String, File> assets;
	private List<Star> stars = new ArrayList<>();
	//=========================================================================

	//=========================================================================
	public UI() {
		assets = new HashMap<>();
	}
	//=========================================================================
	
	//=========================================================================
	public Map<String, File> assets() {
		return assets;
	}
	//=========================================================================

	//=========================================================================
	public void update(float dT) {
		for (int i=0; i<stars.size(); i++) {
			Star s = stars.get(i);
			s.y += dT * s.vy;
			s.x += dT * (Math.random()-.5) * .1;
			s.vy += .00001f;
			if (s.y >= 1f) {
				s.y = 1f;
				s.vy *= -.1f;
			}
			s.a += dT * s.va;
			s.t -= dT;
			if (s.t <= 0f) {
				stars.remove(i--);
			}
		}
		float r = Math.round(Math.random() * 10);		
		for (int i=0; i<r; i++) {
			if (stars.size() < 100000)
				stars.add(new Star());
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void render(G2D g) {
		g.color(1, 1, 1);
		for (int i=0; i<stars.size(); i++) {
			Star s = stars.get(i);
			star(g, s.x, s.y, s.s,s.a);
		}
	}
	//=========================================================================

	//=========================================================================
	private void star(G2D g, float x, float y, float s, float a) {
		x *= g.width();
		y *= g.height();
		s *= g.width() * .01f;
		float s2 = s*.5f;
		float s3 = s*.4f;
		float c = (float) (Math.random()*.5) + .5f;
		g.color(c, c, 1);
		g.push();
		g.translate(x, y);
		g.rotate(a);
		g.line(-s2,   0, +s2,   0);
		g.line(  0, -s2,   0, +s2);
		g.line(-s3, -s3, +s3, +s3);
		g.line(-s3, +s3, +s3, -s3);
		g.pop();
	}
	//=========================================================================
	
}
//*****************************************************************************
