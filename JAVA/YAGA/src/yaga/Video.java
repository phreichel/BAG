//*****************************************************************************
package yaga;
//*****************************************************************************

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

//*****************************************************************************
public class Video implements G2D, G3D, GLEventListener {

	//=========================================================================
	private GLWindow window;
	private GL2 api;
	private GLU glu;
	//=========================================================================

	//=========================================================================
	private Map<String, File> loadMap;
	private Set<String> destroySet;
	private Map<String, Texture> textureMap;
	//=========================================================================

	//=========================================================================
	private List<Video2D> video2d;
	//=========================================================================
	
	//=========================================================================
	public Video(GLWindow window) {
		loadMap = new HashMap<>();
		destroySet = new HashSet<>();
		textureMap = new HashMap<>();
		video2d = new ArrayList<>();
		this.window = window;
		this.window.setTitle("YAGA Canvas");
		this.window.setSize(800, 600);
		this.window.setMaximized(true, true);
		this.window.addGLEventListener(this);
	}
	//=========================================================================

	//=========================================================================
	public void load(Video2D v) {
		video2d.add(v);
		var assets = v.assets();
		register(assets);
	}
	//=========================================================================

	//=========================================================================
	public void unload(Video2D v) {
		if (video2d.remove(v)) {
			var assets = v.assets();
			remove(assets.keySet());
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void register(Map<String, File> textures) {
		for (var textureName : textures.keySet()) {
			var textureFile = textures.get(textureName);
			register(textureName, textureFile);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void register(String textureName, File textureFile) {
		loadMap.put(textureName, textureFile);
	}
	//=========================================================================

	//=========================================================================
	public void remove(String ...  textureNames) {
		for (var name : textureNames) {
			remove(name);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void remove(Collection<String> textureNames) {
		for (var name : textureNames) {
			remove(name);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void remove(String textureName) {
		destroySet.add(textureName);
	}
	//=========================================================================
	
	//=========================================================================
	public void init() {
		this.window.setVisible(true);
	}
	//=========================================================================
	
	//=========================================================================
	public void update() {
		window.display();
	}
	//=========================================================================

	//=========================================================================
	public void done() {
		this.window.setVisible(false);
	}
	//=========================================================================

	//=========================================================================
	public void init(GLAutoDrawable drawable) {
		
		api = drawable.getGL().getGL2();
		api.glHint(GL2.GL_POINT_SMOOTH, GL2.GL_NICEST);
		api.glHint(GL2.GL_LINE_SMOOTH, GL2.GL_NICEST);
		api.glHint(GL2.GL_POLYGON_SMOOTH, GL2.GL_NICEST);
		
	}
	//=========================================================================

	//=========================================================================
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	//=========================================================================
	
	//=========================================================================
	public void display(GLAutoDrawable drawable) {

		api = drawable.getGL().getGL2();
		glu = GLU.createGLU(api); 
		
		for (var textureName : loadMap.keySet()) {
			var textureFile = loadMap.get(textureName);
			try {
				var texture = TextureIO.newTexture(textureFile, true);
				textureMap.put(textureName, texture);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loadMap.clear();
		
		for (var textureName : destroySet) {
			var texture = textureMap.remove(textureName);
			texture.destroy(api);
		}
		destroySet.clear();
		
		int   width  = window.getWidth();
		int   height = window.getHeight();
		float aspect = (float) width / (float) height;
		
		api.glEnable(GL2.GL_DEPTH_TEST);
		api.glEnable(GL2.GL_COLOR_MATERIAL);
		api.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		api.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		//glu.gluPerspective(fovy, aspect, znear, zfar);
		glu.gluPerspective(60, aspect, .1f, 1000f);

		api.glMatrixMode(GL2.GL_MODELVIEW);
		api.glLoadIdentity();

		scene(this);

		api.glDisable(GL2.GL_DEPTH_TEST);
		api.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		
		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		glu.gluOrtho2D(-1, width, height, -1);
		
		api.glMatrixMode(GL2.GL_MODELVIEW);
		api.glLoadIdentity();
		
		surface(this);
		
	}
	//=========================================================================

	//=========================================================================
	public void dispose(GLAutoDrawable drawable) {}
	//=========================================================================

	//=========================================================================
	protected void scene(G3D g) {}
	//=========================================================================

	//=========================================================================
	protected void surface(G2D g) {
		for (var v : video2d) {
			v.render(g);
		}
	}
	//=========================================================================

	//=========================================================================
	public int width() {
		return window.getWidth();
	}
	//=========================================================================

	//=========================================================================
	public int height() {
		return window.getHeight();
	}
	//=========================================================================
	
	//=========================================================================
	public void push() {
		api.glPushMatrix();
	}
	//=========================================================================

	//=========================================================================
	public void pop() {
		api.glPopMatrix();
	}
	//=========================================================================

	//=========================================================================
	public void translate(float dx, float dy) {
		api.glTranslatef(dx, dy, 0f);
	}
	//=========================================================================

	//=========================================================================
	public void rotate(float a) {
		api.glRotatef(a, 0, 0, 1);
	}
	//=========================================================================

	//=========================================================================
	public void scale(float s) {
		api.glScalef(s, s, 1);
	}
	//=========================================================================

	//=========================================================================
	public void scale(float sx, float sy) {
		api.glScalef(sx, sy, 1);
	}
	//=========================================================================
	
	//=========================================================================
	public void color(float r, float g, float b) {
		color(r, g, b, 1f);
	}
	//=========================================================================
	
	//=========================================================================
	public void color(float r, float g, float b, float a) {
		api.glColor4f(r, g, b, a);
	}
	//=========================================================================

	//=========================================================================
	public void line(float x1, float y1, float x2, float y2) {
		api.glBegin(GL2.GL_LINES);
		api.glVertex2f(x1, y1);
		api.glVertex2f(x2, y2);
		api.glEnd();
	}
	//=========================================================================

	//=========================================================================
	public void box(boolean fill, float x1, float y1, float x2, float y2) {
		var mode = fill ? GL2.GL_QUADS : GL2.GL_LINE_LOOP;  
		api.glBegin(mode);
		api.glVertex2f(x1, y2);
		api.glVertex2f(x2, y2);
		api.glVertex2f(x2, y1);
		api.glVertex2f(x1, y1);
		api.glEnd();
	}
	//=========================================================================
	
	//=========================================================================
	public void polyline(boolean closed, float ... coords) {
		var mode = closed ? GL2.GL_LINE_LOOP : GL2.GL_LINE_STRIP;
		api.glBegin(mode);
		for (int i=0; i<coords.length/2; i++) {
			var x = coords[i*2+0];
			var y = coords[i*2+1];
			api.glVertex2f(x, y);
		}
		api.glEnd();
	}
	//=========================================================================

	//=========================================================================
	public void polyfill(float ... coords) {		
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		for (int i=0; i<coords.length/2; i++) {
			var x = coords[i*2+0];
			var y = coords[i*2+1];
			api.glVertex2f(x, y);
		}
		var x = coords[0];
		var y = coords[1];
		api.glVertex2f(x, y);
		api.glEnd();
	}
	//=========================================================================
	
	//=========================================================================
	public void imagebox(String name, float x1, float y1, float x2, float y2) {
		
		var texture = textureMap.get(name);
		if (texture != null) {
			texture.enable(api);
		}
		
		api.glBegin(GL2.GL_QUADS);
		
		api.glTexCoord2f(0, 0);
		api.glVertex2f(x1, y2);
		
		api.glTexCoord2f(1, 0);
		api.glVertex2f(x2, y2);
		
		api.glTexCoord2f(1, 1);
		api.glVertex2f(x2, y1);
		
		api.glTexCoord2f(0, 1);
		api.glVertex2f(x1, y1);
		
		api.glEnd();

		if (texture != null) {
			texture.disable(api);
		}
		
	}
	//=========================================================================

	//=========================================================================
	public void imagefill(String name, float ... coords) {
		
		var texture = textureMap.get(name);
		if (texture != null) {
			texture.enable(api);
		}
		
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		
		for (int i=0; i<coords.length/4; i++) {
			var x  = coords[i*4+0];
			var y  = coords[i*4+1];
			var tx = coords[i*4+2];
			var ty = coords[i*4+3];
			api.glTexCoord2f(tx, ty);
			api.glVertex2f(x, y);
		}
		
		var x  = coords[0];
		var y  = coords[1];
		var tx = coords[2];
		var ty = coords[3];
		api.glTexCoord2f(tx, ty);
		api.glVertex2f(x, y);
		
		api.glEnd();
		
		if (texture != null) {
			texture.disable(api);
		}
		
	}
	//=========================================================================
	
}
//*****************************************************************************
