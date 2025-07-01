//*********************************************************************************************************************
package spark.platform;
//*********************************************************************************************************************

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

import spark.math.MathUtil;

//*********************************************************************************************************************
class GLGraphics implements Graphics {

	//=================================================================================================================
	private GLAutoDrawable drawable;
	private GL2            api;
	private GLU            glu;
	//=================================================================================================================

	//=================================================================================================================
	private float colorRed   = 0f;
	private float colorGreen = 0f;
	private float colorBlue  = 0f;
	private float colorAlpha = 1f;
	//=================================================================================================================

	//=================================================================================================================
	private String font = null;
	private Map<String, TextRenderer> fonts = new HashMap<>(); 
	//=================================================================================================================
	
	//=================================================================================================================
	public GLGraphics(GLAutoDrawable drawable) {
		this.drawable = drawable;
		this.api = this.drawable.getGL().getGL2();
		this.glu = GLU.createGLU(api);
	}
	//=================================================================================================================

	//=================================================================================================================
	void begin() {
		setupQuality();
		setupBlending();
		setupColor();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void setup2D() {
		api.glDisable(GL2.GL_LIGHTING);
		api.glDisable(GL2.GL_DEPTH_TEST);
		setupBlending();
		setupProjection();
		setupTransformation();
	}
	//=================================================================================================================

	//=================================================================================================================
	private void setupQuality() {
		api.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_NICEST);
		api.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
		api.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
	}
	//=================================================================================================================

	//=================================================================================================================
	private void setupBlending() {
		api.glEnable(GL2.GL_BLEND);
		api.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	private void setupProjection() {
		int width = drawable.getSurfaceWidth();
		int height = drawable.getSurfaceHeight();
		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		glu.gluOrtho2D(0, width, height, 0);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	private void setupTransformation() {
		api.glMatrixMode(GL2.GL_MODELVIEW);
		api.glLoadIdentity();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void push() {
		api.glPushMatrix();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void push(float dx, float dy) {
		api.glPushMatrix();
		api.glTranslatef(dx, dy, 0f);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void pop() {
		api.glPopMatrix();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	private void setupColor() {
		api.glEnable(GL2.GL_COLOR_MATERIAL);
		api.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
	}
	//=================================================================================================================

	//=================================================================================================================
	//TODO: proper error handling
	public float colorChannel(ColorChannel channel) {
		switch (channel) {
			case RED -> { return colorRed; }
			case GREEN -> { return colorGreen; }
			case BLUE -> { return colorBlue; }
			case ALPHA -> { return colorAlpha; }
			default -> { return -1f; }
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	public void color(float r, float g, float b) {
		colorRed = r;
		colorGreen = g;
		colorBlue = b;
		colorAlpha = 1f;
		setupColor();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void color(float r, float g, float b, float a) {
		colorRed = r;
		colorGreen = g;
		colorBlue = b;
		colorAlpha = a;
		setupColor();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void materialAmbient(float r, float g, float b, float a) {
		float[] color = new float[] { r, g, b, a };
		api.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, color, 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void materialDiffuse(float r, float g, float b, float a) {
		float[] color = new float[] { r, g, b, a };
		api.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, color, 0);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void materialEmission(float r, float g, float b, float a) {
		float[] color = new float[] { r, g, b, a };
		api.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, color, 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void materialSpecular(float r, float g, float b, float a) {
		float[] color = new float[] { r, g, b, a };
		api.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, color, 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void materialShininess(float s) {
		api.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, s);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	//TODO: proper error handling
	public void drawLine(float ... coords) {
		api.glBegin(GL2.GL_LINE_STRIP);
		for (int i=0; i<coords.length;) {
			float x = coords[i++];
			float y = coords[i++];
			api.glVertex2f(x, y);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	//TODO: proper error handling
	public void drawClosedLine(float ... coords) {
		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<coords.length;) {
			float x = coords[i++];
			float y = coords[i++];
			api.glVertex2f(x, y);
		}
		api.glEnd();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void drawBox(float x1, float y1, float x2, float y2) {
		drawClosedLine(x1, y1, x2, y1, x2, y2, x1, y2);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void drawCircle(float x, float y, float radius, int segments) {
		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<segments; i++) {
			double angle = Math.toRadians( i * (360.0 / segments) );
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			float sx = (float) (x + radius * cos); 
			float sy = (float) (y + radius * sin);
			api.glVertex2f(sx, sy);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void drawCircleArc(float x, float y, float radius, float start, float end, int segments, boolean pie) {
		api.glBegin(GL2.GL_LINE_STRIP);
		if (pie) api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double angle = Math.toRadians( start + i * ((end-start) / segments) );
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			float sx = (float) (x + radius * cos); 
			float sy = (float) (y + radius * sin);
			api.glVertex2f(sx, sy);
		}
		if (pie) api.glVertex2f(x, y);
		api.glEnd();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void drawOval(float x1, float y1, float x2, float y2, int segments) {
		float rh = (x2-x1) * .5f;
		float rv = (y2-y1) * .5f;
		float x = x1 + rh;
		float y = y1 + rv;
		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<segments; i++) {
			double angle = Math.toRadians( i * (360.0 / segments) );
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			float sx = (float) (x + rh * cos); 
			float sy = (float) (y + rv * sin);
			api.glVertex2f(sx, sy);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void drawOval(float x, float y, float rh, float rv, float angle, int segments) {
		double aa = Math.toRadians(angle);
		double asin = Math.sin(aa);
		double acos = Math.cos(aa);
		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<segments; i++) {
			double a = Math.toRadians( i * (360.0 / segments) );
			double sin = Math.sin(a);
			double cos = Math.cos(a);
			double sx = rh * cos; 
			double sy = rv * sin;
			float ax = (float) (x + sx * acos - sy * asin);
			float ay = (float) (y + sx * asin + sy * acos);
			api.glVertex2f(ax, ay);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void drawOvalArc(float x, float y, float rh, float rv, float angle, float start, float end, int segments, boolean pie) {
		double aa = Math.toRadians(angle);
		double asin = Math.sin(aa);
		double acos = Math.cos(aa);
		api.glBegin(GL2.GL_LINE_STRIP);
		if (pie) api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double a = Math.toRadians( start + i * ((end - start) / segments) );
			double sin = Math.sin(a);
			double cos = Math.cos(a);
			double sx = rh * cos; 
			double sy = rv * sin;
			float ax = (float) (x + sx * acos - sy * asin);
			float ay = (float) (y + sx * asin + sy * acos);
			api.glVertex2f(ax, ay);
		}
		if (pie) api.glVertex2f(x, y);
		api.glEnd();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	//TODO: proper error handling
	public void fillPolygon(float ... coords) {
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		for (int i=0; i<coords.length;) {
			float x = coords[i++];
			float y = coords[i++];
			api.glVertex2f(x, y);
		}
		float xe = coords[0];
		float ye = coords[1];
		api.glVertex2f(xe, ye);
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void fillBox(float x1, float y1, float x2, float y2) {
		api.glBegin(GL2.GL_QUADS);
		api.glVertex2f(x1, y1);
		api.glVertex2f(x2, y1);
		api.glVertex2f(x2, y2);
		api.glVertex2f(x1, y2);
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void fillCircle(float x, float y, float radius, int segments) {
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double angle = Math.toRadians( i * (360.0 / segments) );
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			float sx = (float) (x + radius * cos); 
			float sy = (float) (y + radius * sin);
			api.glVertex2f(sx, sy);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void fillCircleArc(float x, float y, float radius, float start, float end, int segments) {
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double angle = Math.toRadians( start + i * ((end-start) / segments) );
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			float sx = (float) (x + radius * cos); 
			float sy = (float) (y + radius * sin);
			api.glVertex2f(sx, sy);
		}
		api.glEnd();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void fillOval(float x1, float y1, float x2, float y2, int segments) {
		float rh = (x2-x1) * .5f;
		float rv = (y2-y1) * .5f;
		float x = x1 + rh;
		float y = y1 + rv;
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double angle = Math.toRadians( i * (360.0 / segments) );
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			float sx = (float) (x + rh * cos); 
			float sy = (float) (y + rv * sin);
			api.glVertex2f(sx, sy);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void fillOval(float x, float y, float rh, float rv, float angle, int segments) {
		double aa = Math.toRadians(angle);
		double asin = Math.sin(aa);
		double acos = Math.cos(aa);
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double a = Math.toRadians( i * (360.0 / segments) );
			double sin = Math.sin(a);
			double cos = Math.cos(a);
			double sx = rh * cos; 
			double sy = rv * sin;
			float ax = (float) (x + sx * acos - sy * asin);
			float ay = (float) (y + sx * asin + sy * acos);
			api.glVertex2f(ax, ay);
		}
		api.glEnd();
	}
	//=================================================================================================================

	//=================================================================================================================
	public void fillOvalArc(float x, float y, float rh, float rv, float angle, float start, float end, int segments) {
		double aa = Math.toRadians(angle);
		double asin = Math.sin(aa);
		double acos = Math.cos(aa);
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		api.glVertex2f(x, y);
		for (int i=0; i<=segments; i++) {
			double a = Math.toRadians( start + i * ((end - start) / segments) );
			double sin = Math.sin(a);
			double cos = Math.cos(a);
			double sx = rh * cos; 
			double sy = rv * sin;
			float ax = (float) (x + sx * acos - sy * asin);
			float ay = (float) (y + sx * asin + sy * acos);
			api.glVertex2f(ax, ay);
		}
		api.glEnd();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public String font() {
		return this.font;
	}
	//=================================================================================================================

	//=================================================================================================================
	public void font(String name) {
		this.font = name;
	}
	//=================================================================================================================
	public void fontCreate(String name, String style) {
		var textFont = Font.decode(style);
		TextRenderer textRenderer = new TextRenderer(textFont, true);
		textRenderer.setSmoothing(true);
		this.fontDestroy(name);
		this.fonts.put(name, textRenderer);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void fontDestroy(String name) {
		var oldFont = this.fonts.remove(name);
		if (oldFont != null) oldFont.dispose();
	}
	//=================================================================================================================

	//=================================================================================================================
	public TextSize textSize(String text) {
		var textFont = fonts.get(this.font);
		if (textFont == null) return null;
		var bounds = textFont.getFont().getStringBounds(text, textFont.getFontRenderContext());
		var width = (float) bounds.getWidth();
		var height = (float) (bounds.getHeight());
		var baseline = (float) (bounds.getHeight()+bounds.getY());
		var textSize = new TextSize(this.font, text, width, height, baseline);
		return textSize;
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void text(float x, float y, String text) {
		var textFont = fonts.get(this.font);
		if (textFont == null) return;
		textFont.setColor(
			colorRed, 
			colorGreen,
			colorBlue,
			colorAlpha);
		api.glScalef(1, -1, 1);
		textFont.begin3DRendering();
		textFont.draw3D(text, x, y, 0f, 1f);
		textFont.end3DRendering();
		api.glScalef(1, -1, 1);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void setup3D() {
		api.glDisable(GL2.GL_LIGHTING);
		api.glDisable(GL2.GL_LIGHT0);
		api.glDisable(GL2.GL_BLEND);
		//api.glEnable(GL2.GL_LIGHTING);
		//api.glEnable(GL2.GL_LIGHT0);
		api.glEnable(GL2.GL_DEPTH_TEST);
		setup3DProjection();
		setupTransformation();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	private void setup3DProjection() {
		int width  = drawable.getSurfaceWidth();
		int height = drawable.getSurfaceHeight();
		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		float near = 0.5f;
		float far  = 2000f;
		float aspect = (float) width / (float) height;
		glu.gluPerspective(50f, aspect, near, far);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void place3DCamera(
		Vector3f location,
		Matrix3f orientation) {
		
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();

        viewMatrix.m00 = orientation.m00;
        viewMatrix.m01 = orientation.m01;
        viewMatrix.m02 = orientation.m02;

        viewMatrix.m10 = orientation.m10;
        viewMatrix.m11 = orientation.m11;
        viewMatrix.m12 = orientation.m12;

        viewMatrix.m20 = orientation.m20;
        viewMatrix.m21 = orientation.m21;
        viewMatrix.m22 = orientation.m22;

        Vector3f negLocation = new Vector3f(location);
        negLocation.negate();
        
        viewMatrix.m03 = negLocation.x * orientation.m00 + negLocation.y * orientation.m01 + negLocation.z * orientation.m02;
        viewMatrix.m13 = negLocation.x * orientation.m10 + negLocation.y * orientation.m11 + negLocation.z * orientation.m12;
        viewMatrix.m23 = negLocation.x * orientation.m20 + negLocation.y * orientation.m21 + negLocation.z * orientation.m22;
        
        var viewArray = MathUtil.copyMatrixToArray(new float[16], viewMatrix);
		
		api.glMultMatrixf(viewArray, 0);
		
	}
	//=================================================================================================================

	//=================================================================================================================
	public void draw3DPoint(float x, float y, float z) {
		api.glBegin(GL2.GL_POINTS);
		api.glVertex3f(x, y, z);
		api.glEnd();
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void draw3DBox(
			float x1, float y1, float z1,
			float x2, float y2, float z2) {
		api.glBegin(GL2.GL_QUADS);

		api.glNormal3f(0, 0, -1);
		api.glVertex3f(x1, y1, z1);
		api.glVertex3f(x1, y2, z1);
		api.glVertex3f(x2, y2, z1);
		api.glVertex3f(x2, y1, z1);

		api.glNormal3f(0, 0, 1);
		api.glVertex3f(x1, y1, z2);
		api.glVertex3f(x2, y1, z2);
		api.glVertex3f(x2, y2, z2);
		api.glVertex3f(x1, y2, z2);

		api.glNormal3f(-1, 0, 0);
		api.glVertex3f(x1, y1, z1);
		api.glVertex3f(x1, y2, z1);
		api.glVertex3f(x1, y2, z2);
		api.glVertex3f(x1, y1, z2);

		api.glNormal3f(1, 0, 0);
		api.glVertex3f(x2, y1, z1);
		api.glVertex3f(x2, y1, z2);
		api.glVertex3f(x2, y2, z2);
		api.glVertex3f(x2, y2, z1);

		api.glNormal3f(0, -1, 0);
		api.glVertex3f(x1, y1, z1);
		api.glVertex3f(x2, y1, z1);
		api.glVertex3f(x2, y1, z2);
		api.glVertex3f(x1, y1, z2);

		api.glNormal3f(0, 1, 0);
		api.glVertex3f(x1, y2, z1);
		api.glVertex3f(x1, y2, z2);
		api.glVertex3f(x2, y2, z2);
		api.glVertex3f(x2, y2, z1);
		
		api.glEnd();
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
