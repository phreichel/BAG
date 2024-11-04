//*************************************************************************************************
package jlib.platform;
//*************************************************************************************************

import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

//*************************************************************************************************
class OpenGLGraphics implements Graphics {

	//============================================================================================
	private GLAutoDrawable drawable;
	private GL2 api;
	private GLU glu;
	//============================================================================================

	//============================================================================================
	public void init(GLAutoDrawable drawable) {

		this.drawable = drawable;
		api = drawable.getGL().getGL2();
		glu = GLU.createGLU(api);
		
		api.glClearColor(.7f, .7f, .9f, 1f);
		
		api.glEnable(GL2.GL_TEXTURE);
		
		api.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_NICEST);
		api.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
		api.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
		api.glHint(GL2.GL_TEXTURE_COMPRESSION_HINT, GL2.GL_NICEST);
		
		// transparenz
		api.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		
	}
	//============================================================================================

	//============================================================================================
	public void frameBegin() {
		api.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	}
	//============================================================================================

	//============================================================================================
	public void sceneMode() {

		api.glEnable(GL2.GL_LIGHTING);
		api.glEnable(GL2.GL_DEPTH_TEST);
		api.glEnable(GL2.GL_CULL_FACE);
		
		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		float aspect = (float) drawable.getSurfaceWidth() / (float) drawable.getSurfaceHeight(); 
		glu.gluPerspective(70f, aspect, .4f, 1000f + .4f);
		
		api.glMatrixMode(GL2.GL_MODELVIEW);
		api.glLoadIdentity();
		
	}
	//=============================================================================================
	
	//============================================================================================
	public void surfaceMode() {

		api.glDisable(GL2.GL_LIGHTING);		
		api.glDisable(GL2.GL_DEPTH_TEST);
		api.glDisable(GL2.GL_CULL_FACE);
		
		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		float left   = -1f;
		float right  = drawable.getSurfaceWidth()-1;
		float bottom = drawable.getSurfaceHeight();
		float top    = 0f;
		glu.gluOrtho2D(left, right, bottom, top);
		
		api.glMatrixMode(GL2.GL_MODELVIEW);
		api.glLoadIdentity();
		
	}
	//=============================================================================================

	//============================================================================================
	private Color4f lineColor = new Color4f(); 
	private Color4f fillColor = new Color4f(); 
	//============================================================================================

	//============================================================================================
	public float width() {
		return drawable.getSurfaceWidth();
	}
	//============================================================================================

	//============================================================================================
	public float height() {
		return drawable.getSurfaceHeight();
	}
	//============================================================================================
	
	//=============================================================================================
	public Color4f lineColor() {
		return lineColor;
	}
	//=============================================================================================

	//=============================================================================================
	public void lineColor(float r, float g, float b) {
		lineColor.set(r, g, b, 1f);
	}
	//=============================================================================================

	//=============================================================================================
	public void lineColor(float r, float g, float b, float a) {
		lineColor.set(r, g, b, a);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Color4f fillColor() {
		return lineColor;
	}
	//=============================================================================================

	//=============================================================================================
	public void fillColor(float r, float g, float b) {
		fillColor.set(r, g, b, 1f);
	}
	//=============================================================================================

	//=============================================================================================
	public void fillColor(float r, float g, float b, float a) {
		fillColor.set(r, g, b, a);
	}
	//=============================================================================================

	//=============================================================================================
	private void applyLineColor() {
		float r = lineColor.x;
		float g = lineColor.y;
		float b = lineColor.z;
		float a = lineColor.w;
		if (a < 1f) {
			api.glEnable(GL2.GL_BLEND);
			api.glColor4f(r, g, b, a);
		} else {
			api.glDisable(GL2.GL_BLEND);
			api.glColor4f(r, g, b, a);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void applyFillColor() {
		float r = fillColor.x;
		float g = fillColor.y;
		float b = fillColor.z;
		float a = fillColor.w;
		if (a < 1f) {
			api.glEnable(GL2.GL_BLEND);
			api.glColor4f(r, g, b, a);
		} else {
			api.glDisable(GL2.GL_BLEND);
			api.glColor4f(r, g, b, a);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void push(float dx, float dy) {
		api.glPushMatrix();
		api.glTranslatef(dx, dy, 0f);
	}
	//=============================================================================================

	//=============================================================================================
	public void pop() {
		api.glPopMatrix();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void lineStrip(float ... xycoords) {
		
		applyLineColor();

		api.glBegin(GL2.GL_LINE_STRIP);
		for (int i=0; i<xycoords.length; i+=2) {
			float x = xycoords[i+0];
			float y = xycoords[i+1];
			api.glVertex2f(x, y);
		}
		api.glEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void lineLoop(float ... xycoords) {
		
		applyLineColor();

		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<xycoords.length; i+=2) {
			float x = xycoords[i+0];
			float y = xycoords[i+1];
			api.glVertex2f(x, y);
		}
		api.glEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void lineLoopFilled(float ... xycoords) {
		
		applyFillColor();

		api.glBegin(GL2.GL_TRIANGLE_FAN);
		for (int i=0; i<xycoords.length; i+=2) {
			float x = xycoords[i+0];
			float y = xycoords[i+1];
			api.glVertex2f(x, y);
		}
		api.glEnd();
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void box(float x1, float y1, float x2, float y2) {
		
		applyLineColor();

		api.glBegin(GL2.GL_LINE_LOOP);
		api.glVertex2f(x1, y1);
		api.glVertex2f(x2, y1);
		api.glVertex2f(x2, y2);
		api.glVertex2f(x1, y2);
		api.glEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void boxFilled(float x1, float y1, float x2, float y2) {

		applyFillColor();
		
		api.glBegin(GL2.GL_QUADS);
		api.glVertex2f(x1, y1);
		api.glVertex2f(x2, y1);
		api.glVertex2f(x2, y2);
		api.glVertex2f(x1, y2);
		api.glEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void oval(float x1, float y1, float x2, float y2) {

		float rx = (x2 - x1) * .5f;
		float ry = (y2 - y1) * .5f;

		float cx = x1 + rx;
		float cy = y1 + ry;
		
		applyLineColor();
		
		int count = (int) Math.rint((rx + ry) * 2f);
		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<count; i++) {
			
			double a = i * ((2 * Math.PI) / count);
			float px = cx + rx * (float) Math.cos(a);
			float py = cy + ry * (float) Math.sin(a);
			api.glVertex2f(px, py);
			
		}
		api.glEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void ovalFilled(float x1, float y1, float x2, float y2) {

		float rx = (x2 - x1) * .5f;
		float ry = (y2 - y1) * .5f;

		float cx = x1 + rx;
		float cy = y1 + ry;
		
		applyFillColor();

		int count = (int) Math.rint((rx + ry) * 2f);
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		api.glVertex2f(cx, cy);
		for (int i=0; i<=count; i++) {
			
			double a = i * ((2 * Math.PI) / count);
			float px = cx + rx * (float) Math.cos(a);
			float py = cy + ry * (float) Math.sin(a);
			api.glVertex2f(px, py);
			
		}
		api.glEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	private Map<String, TextRenderer> fontMap = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public void textFontLoad(String name, String style) {
		Font font = Font.decode(style);
		TextRenderer renderer = new TextRenderer(font, true);
		fontMap.put(name, renderer);
	}
	//=============================================================================================

	//=============================================================================================
	public void text(String font, float x, float y, String text) {
		TextRenderer renderer = fontMap.get(font);
		if (renderer != null) {

			float r = lineColor.x;
			float g = lineColor.y;
			float b = lineColor.z;
			float a = lineColor.w;
			renderer.setColor(r, g, b, a);
			
			var bounds = renderer.getBounds(text);
			float tx = (float) bounds.getX();
			float ty = (float) bounds.getY();
			
			api.glPushMatrix();
			renderer.begin3DRendering();
			api.glTranslatef(x, y, 0f);
			api.glScalef(1f, -1f, 1f);
			renderer.draw3D(text, tx, ty, 0f, 1f);
			renderer.end3DRendering();
			api.glPopMatrix();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private Map<String, Texture> textureMap = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public void textureLoad(String name, File path) {
		try {
			Texture texture = TextureIO.newTexture(path, true);
			textureMap.put(name, texture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void textureApply(String name, float x, float y) {
		textureApply(name, x, y, 1f);
	}
	//=============================================================================================

	//=============================================================================================
	public void textureApply(String name, float x, float y, float scale) {
		textureApply(name, x, y, scale, scale);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void textureApply(String name, float x, float y, float scalex, float scaley) {
		Texture texture = textureMap.get(name);
		if (texture != null) {
			float w = scalex * texture.getWidth();
			float h = scaley * texture.getHeight();
			applyFillColor();
			texture.bind(api);
			texture.enable(api);
			api.glBegin(GL2.GL_QUADS);
			api.glTexCoord2f(0, 1);
			api.glVertex2f(x, y);
			api.glTexCoord2f(1, 1);
			api.glVertex2f(x+w, y);
			api.glTexCoord2f(1, 0);
			api.glVertex2f(x+w, y+h);
			api.glTexCoord2f(0, 0);
			api.glVertex2f(x, y+h);
			api.glEnd();
			texture.disable(api);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void textureApply(
			String name,
			float x1, float y1,
			float x2, float y2,
			float tx1, float ty1,
			float tx2, float ty2) {
		Texture texture = textureMap.get(name);
		if (texture != null) {
			applyFillColor();
			texture.bind(api);
			texture.enable(api);
			api.glBegin(GL2.GL_QUADS);
			api.glTexCoord2f(tx1, 1f-ty1);
			api.glVertex2f(x1, y1);
			api.glTexCoord2f(tx2, 1f-ty1);
			api.glVertex2f(x2, y1);
			api.glTexCoord2f(tx2, 1f-ty2);
			api.glVertex2f(x2, y2);
			api.glTexCoord2f(tx1, 1f-ty2);
			api.glVertex2f(x1, y2);
			api.glEnd();
			texture.disable(api);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f textureSize(String name, Vector2f size) {
		Texture texture = textureMap.get(name);
		if (size != null && texture != null) {
			float w = texture.getWidth();
			float h = texture.getHeight();
			size.set(w, h);
		}
		return size;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
