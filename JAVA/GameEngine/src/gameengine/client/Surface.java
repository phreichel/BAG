//*****************************************************************************
package gameengine.client;
//*****************************************************************************

import java.awt.Font;
import java.util.Map;

import javax.vecmath.Color3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;

//*****************************************************************************

//*****************************************************************************
public class Surface {

	//=========================================================================
	public static final int CONTEXT_NONE    = 0;
	public static final int CONTEXT_INIT    = 1;
	public static final int CONTEXT_DONE    = 2;
	public static final int CONTEXT_UPDATE  = 3;
	//=========================================================================
	
	//=========================================================================
	private int context;
	private GLAutoDrawable drawable;
	private GL2            gl2;
	private Map<String, TextRenderer> textRenderers;
	//=========================================================================

	//=========================================================================
	public Surface(int context, GLAutoDrawable drawable, Map<String, TextRenderer> textRenderers) {
		this.context = context;
		this.drawable = drawable;
		this.textRenderers = textRenderers;
		this.gl2 = this.drawable.getGL().getGL2();
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		gl2.glOrthof(-1f, drawable.getSurfaceWidth(), -1f, drawable.getSurfaceHeight(),-100, 100);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
	}
	//=========================================================================

	//=========================================================================
	public int getContext() {
		return context;
	}
	//=========================================================================

	//=========================================================================
	public float getWidth() {
		return drawable.getSurfaceWidth();
	}
	//=========================================================================

	//=========================================================================
	public float getHeight() {
		return drawable.getSurfaceHeight();
	}
	//=========================================================================

	//=========================================================================
	public float getAspect() {
		return 
			drawable.getSurfaceWidth() /
			drawable.getSurfaceHeight();
	}
	//=========================================================================

	//=========================================================================
	public void push(Vector2f translation) {
		gl2.glPushMatrix();
		gl2.glTranslatef(translation.x, translation.y, 0f);
	}
	//=========================================================================

	//=========================================================================
	public void push(float angle) {
		gl2.glPushMatrix();
		gl2.glRotatef(angle, 0, 0, 1);
	}
	//=========================================================================

	//=========================================================================
	public void pop() {
		gl2.glPopMatrix();
	}
	//=========================================================================

	//=========================================================================
	public void color(Color3f color) {
		gl2.glColor3f(color.x, color.y, color.z);
	}
	//=========================================================================
	
	//=========================================================================
	public void line(Vector2f ... coords) {
		gl2.glBegin(GL2.GL_LINE_STRIP);
		for (var coord : coords) {
			gl2.glVertex2f(coord.x, coord.y);
		}
		gl2.glEnd();
	}
	//=========================================================================

	//=========================================================================
	public void rect_line(Vector2f location, Vector2f extent) {
		var coords = new Vector2f[] {
			new Vector2f(location.x, location.y),
			new Vector2f(location.x + extent.x, location.y),
			new Vector2f(location.x + extent.x, location.y+ extent.y),
			new Vector2f(location.x, location.y+ extent.y),
			new Vector2f(location.x, location.y)
		};
		line(coords);
	}
	//=========================================================================
	
	//=========================================================================
	public void filled(Vector2f ... coords) {
		gl2.glBegin(GL2.GL_TRIANGLE_FAN);
		for (var coord : coords) {
			gl2.glVertex2f(coord.x, coord.y);
		}
		gl2.glEnd();
	}
	//=========================================================================

	//=========================================================================
	public void rect_filled(Vector2f location, Vector2f extent) {
		var coords = new Vector2f[] {
			new Vector2f(location.x, location.y),
			new Vector2f(location.x + extent.x, location.y),
			new Vector2f(location.x + extent.x, location.y+ extent.y),
			new Vector2f(location.x, location.y+ extent.y),
			new Vector2f(location.x, location.y)
		};
		filled(coords);
	}
	//=========================================================================
	
	//=========================================================================
	public void arc(Vector2f center, float radius, float astart, float astop) {
		var sc = (astop-astart)/360.0;
		var N = (int) Math.ceil(2f * radius * Math.PI * sc);
		gl2.glBegin(GL2.GL_LINE_STRIP);
		for (var i=0; i<=N; i++) {
			var a = Math.toRadians(astart) + ((double) i / (double) N) * 2 * Math.PI * sc;
			var s = (float) Math.sin(a);
			var c = (float) Math.cos(a);
			gl2.glVertex2f(center.x+c*radius, center.y+s*radius);
		}
		gl2.glEnd();
	}
	//=========================================================================
	
	//=========================================================================
	public void circle(Vector2f center, float radius) {
		arc(center, radius, 0, 360);
	}
	//=========================================================================

	//=========================================================================
	public void pie(Vector2f center, float radius, float astart, float astop) {
		var sc = (astop-astart)/360.0;
		var N = (int) Math.ceil(2f * radius * Math.PI * sc);
		gl2.glBegin(GL2.GL_LINE_STRIP);
		gl2.glVertex2f(center.x, center.y);
		for (var i=0; i<=N; i++) {
			var a = Math.toRadians(astart) + ((double) i / (double) N) * 2 * Math.PI * sc;
			var s = (float) Math.sin(a);
			var c = (float) Math.cos(a);
			gl2.glVertex2f(center.x+c*radius, center.y+s*radius);
		}
		gl2.glVertex2f(center.x, center.y);
		gl2.glEnd();
	}
	//=========================================================================

	//=========================================================================
	public void filled_pie(Vector2f center, float radius, float astart, float astop) {
		var sc = (astop-astart)/360.0;
		var N = (int) Math.ceil(2f * radius * Math.PI * sc);
		gl2.glBegin(GL2.GL_TRIANGLE_FAN);
		gl2.glVertex2f(center.x, center.y);
		for (var i=0; i<=N; i++) {
			var a = Math.toRadians(astart) + ((double) i / (double) N) * 2 * Math.PI * sc;
			var s = (float) Math.sin(a);
			var c = (float) Math.cos(a);
			gl2.glVertex2f(center.x+c*radius, center.y+s*radius);
		}
		gl2.glVertex2f(center.x, center.y);
		gl2.glEnd();
	}
	//=========================================================================
	
	//=========================================================================
	public void disk(Vector2f center, float radius) {
		filled_pie(center, radius, 0, 360);
	}
	//=========================================================================

	//=========================================================================
	public void font(String name, String code) {
		var tr = new TextRenderer(Font.decode(code), true);
		tr.setSmoothing(true);
		textRenderers.put(name, tr);
	}
	//=========================================================================

	//=========================================================================
	public Vector3f text_scale(String name, float scale, String text) {
		var tr = textRenderers.get(name);
		var r = tr.getBounds(text);
		var dim = new Vector3f(
			(float) r.getWidth(),
			(float) r.getHeight(),
			(float) (r.getHeight() + r.getY())
		);
		dim.scale(scale);
		return dim;
	}
	//=========================================================================
	
	//=========================================================================
	public void text(String name, Color3f color, float scale, Vector2f coord, String text) {
		var tr = textRenderers.get(name);
		tr.begin3DRendering();
		tr.setColor(color.x, color.y, color.z, 1f);
		tr.draw3D(text, coord.x, coord.y, 0f, scale);
		tr.end3DRendering();
	}
	//=========================================================================
	
}
//*****************************************************************************
