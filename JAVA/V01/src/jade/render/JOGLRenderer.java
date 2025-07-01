//**************************************************************************************************
package jade.render;
//**************************************************************************************************

import javax.vecmath.Matrix4f;
import javax.vecmath.Point2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import jade.gui.Canvas;
import jade.scene.Scene;
import lombok.Setter;

//**************************************************************************************************
public class JOGLRenderer implements GLEventListener, Renderer {

	//==============================================================================================
	@Setter private Scene scene = null;
	@Setter private Canvas canvas = null;
	//==============================================================================================

	//==============================================================================================
	private GLAutoDrawable window = null;
	private GL2 glApi = null;
	private GLU gluApi = null;
	//==============================================================================================

	//==============================================================================================
	private void setup(GLAutoDrawable drawable) {
		window = drawable;
		glApi = drawable.getGL().getGL2();
		gluApi = GLU.createGLU(glApi);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void init(GLAutoDrawable drawable) {
		setup(drawable);
		glApi.glClearColor(0f,0f,0f, 1f);
		glApi.glHint(GL2.GL_FOG_HINT, GL2.GL_NICEST);
		glApi.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_NICEST);
		glApi.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
		glApi.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
		glApi.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		glApi.glHint(GL2.GL_GENERATE_MIPMAP_HINT, GL2.GL_NICEST);
		glApi.glEnable(GL2.GL_LIGHT0);
		glApi.glEnable(GL2.GL_COLOR_MATERIAL);
		glApi.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		glApi.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	}
	//==============================================================================================

	//==============================================================================================
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		setup(drawable);
		if (canvas != null) {
			canvas.setSize(width, height);
		}
	}
	//==============================================================================================
	
	//==============================================================================================
	public void display(GLAutoDrawable drawable) {

		setup(drawable);
		
		glApi.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		if (scene != null) {
			scene.render(this);
		}
		
		if (canvas != null) {
			canvas.render(this);
		}
		
	}
	//==============================================================================================

	//==============================================================================================
	public void dispose(GLAutoDrawable drawable) {
		setup(drawable);
	}
	//==============================================================================================

	//==============================================================================================
	public void sceneInit() {
		glApi.glEnable(GL2.GL_DEPTH_TEST);
		glApi.glEnable(GL2.GL_LIGHTING);
		glApi.glEnable(GL2.GL_CULL_FACE);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void pushTransform(Matrix4f tx) {
		glApi.glPushMatrix();
		float[] buffer = fillMatrixBuffer(tx, matrixBuffer, 0);
		glApi.glMultMatrixf(buffer, 0);
	}
	//==============================================================================================

	//==============================================================================================
	public void popTransform() {
		glApi.glPopMatrix();
	}
	//==============================================================================================

	//==============================================================================================
	public void cameraProjection(float fovy, float near, float far) {
		float aspect = (float) window.getSurfaceWidth() / (float) window.getSurfaceHeight();
		glApi.glMatrixMode(GL2.GL_PROJECTION);
		glApi.glLoadIdentity();
		gluApi.gluPerspective(fovy, aspect, near, far);
		glApi.glMatrixMode(GL2.GL_MODELVIEW);
		glApi.glLoadIdentity();
	}
	//==============================================================================================

	//==============================================================================================
	public void cameraTransform(Matrix4f mx) {
		Matrix4f buffer = new Matrix4f(mx);
		Vector3f position = new Vector3f(buffer.m03, buffer.m13, buffer.m23);
		position.negate();
		buffer.m03 = 0f;
		buffer.m13 = 0f;
		buffer.m23 = 0f;
		buffer.transpose();
		buffer.transform(position);
		buffer.m03 = position.x;
		buffer.m13 = position.y;
		buffer.m23 = position.z;
		float[] bufferArray = fillMatrixBuffer(buffer, matrixBuffer, 0);
		glApi.glMatrixMode(GL2.GL_MODELVIEW);
		glApi.glLoadIdentity();
		glApi.glLoadMatrixf(bufferArray, 0);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void drawSphere(float radius) {
		GLUquadric glq = gluApi.gluNewQuadric();		
		gluApi.gluSphere(glq, radius, 32, 32);
		gluApi.gluDeleteQuadric(glq);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void drawBox(Vector3f size) {
		
		float dx = size.x / 2f;
		float dy = size.y / 2f;
		float dz = size.z / 2f;
		
		glApi.glBegin(GL2.GL_QUADS);
		
		glApi.glNormal3f(0, 0, 1);
		glApi.glVertex3f(-dx, -dy, +dz);
		glApi.glVertex3f(+dx, -dy, +dz);
		glApi.glVertex3f(+dx, +dy, +dz);
		glApi.glVertex3f(-dx, +dy, +dz);

		glApi.glNormal3f(0, 0,-1);
		glApi.glVertex3f(+dx, -dy, -dz);
		glApi.glVertex3f(-dx, -dy, -dz);
		glApi.glVertex3f(-dx, +dy, -dz);
		glApi.glVertex3f(+dx, +dy, -dz);

		glApi.glNormal3f(0, 1, 0);
		glApi.glVertex3f(-dx, +dy, +dz);
		glApi.glVertex3f(+dx, +dy, +dz);
		glApi.glVertex3f(+dx, +dy, -dz);
		glApi.glVertex3f(-dx, +dy, -dz);

		glApi.glNormal3f(0,-1, 0);
		glApi.glVertex3f(+dx, -dy, +dz);
		glApi.glVertex3f(-dx, -dy, +dz);
		glApi.glVertex3f(-dx, -dy, -dz);
		glApi.glVertex3f(+dx, -dy, -dz);

		glApi.glNormal3f(1, 0, 0);
		glApi.glVertex3f(+dx, -dy, +dz);
		glApi.glVertex3f(+dx, -dy, -dz);
		glApi.glVertex3f(+dx, +dy, -dz);
		glApi.glVertex3f(+dx, +dy, +dz);

		glApi.glNormal3f(-1, 0, 0);
		glApi.glVertex3f(-dx, -dy, -dz);
		glApi.glVertex3f(-dx, -dy, +dz);
		glApi.glVertex3f(-dx, +dy, +dz);
		glApi.glVertex3f(-dx, +dy, -dz);
		
		glApi.glEnd();
	}
	//==============================================================================================
	
	//==============================================================================================
	public void canvasInit() {
		glApi.glDisable(GL2.GL_DEPTH_TEST);
		glApi.glDisable(GL2.GL_LIGHTING);
		glApi.glDisable(GL2.GL_CULL_FACE);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void canvasProjection() {
		float w = window.getSurfaceWidth();
		float h = window.getSurfaceHeight();
		glApi.glMatrixMode(GL2.GL_PROJECTION);
		glApi.glLoadIdentity();
		gluApi.gluOrtho2D(-1f, w, h+1f, 0f);
		glApi.glMatrixMode(GL2.GL_MODELVIEW);
		glApi.glLoadIdentity();
	}
	//==============================================================================================
	
	//==============================================================================================
	public void pushTranslate(Point2f position) {
		glApi.glPushMatrix();
		glApi.glTranslatef(position.x, position.y, 0f);
	}
	//==============================================================================================

	//==============================================================================================
	public void popTranslate() {
		glApi.glPopMatrix();
	}
	//==============================================================================================

	//==============================================================================================
	public void drawRectangle(float x, float y, float w, float h) {
		glApi.glBegin(GL2.GL_LINE_LOOP);
		glApi.glNormal3f(0f, 0f, 1f);
		glApi.glVertex2f(x+0, y+0);
		glApi.glVertex2f(x+w, y+0);
		glApi.glVertex2f(x+w, y+h);
		glApi.glVertex2f(x+0, y+h);
		glApi.glEnd();
	}
	//==============================================================================================
	
	//==============================================================================================
	public void fillRectangle(float x, float y, float w, float h) {
		glApi.glBegin(GL2.GL_QUADS);
		glApi.glNormal3f(0f, 0f, 1f);
		glApi.glVertex2f(x+0, y+0);
		glApi.glVertex2f(x+w, y+0);
		glApi.glVertex2f(x+w, y+h);
		glApi.glVertex2f(x+0, y+h);
		glApi.glEnd();
	}
	//==============================================================================================
	
	//==============================================================================================
	public void setColor(float r, float g, float b) {
		glApi.glDisable(GL2.GL_BLEND);
		glApi.glColor3f(r, g, b);
	}
	//==============================================================================================

	//==============================================================================================
	public void setColor(float r, float g, float b, float a) {
		glApi.glEnable(GL2.GL_BLEND);
		glApi.glColor4f(r, g, b, a);
	}
	//==============================================================================================
	
	//==============================================================================================
	private float[] matrixBuffer = new float[16];
	//==============================================================================================
	
	//==============================================================================================
	private static float[] fillMatrixBuffer(Matrix4f mx, float[] target, int offset) {
		target[offset+ 0] = mx.m00;
		target[offset+ 1] = mx.m10;
		target[offset+ 2] = mx.m20;
		target[offset+ 3] = mx.m30;
		target[offset+ 4] = mx.m01;
		target[offset+ 5] = mx.m11;
		target[offset+ 6] = mx.m21;
		target[offset+ 7] = mx.m31;
		target[offset+ 8] = mx.m02;
		target[offset+ 9] = mx.m12;
		target[offset+10] = mx.m22;
		target[offset+11] = mx.m32;
		target[offset+12] = mx.m03;
		target[offset+13] = mx.m13;
		target[offset+14] = mx.m23;
		target[offset+15] = mx.m33;
		return target;
	}
	//==============================================================================================
	
}
//**************************************************************************************************
