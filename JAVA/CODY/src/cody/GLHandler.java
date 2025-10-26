//*****************************************************************************
package cody;
//*****************************************************************************

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

//*****************************************************************************
public class GLHandler implements GLEventListener {

	//=========================================================================
	public void init(GLAutoDrawable drawable) {
		GL2 api = drawable.getGL().getGL2();
		api.glClearColor(1, 1, 0, 1);
	}
	//=========================================================================

	//=========================================================================
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}
	//=========================================================================
	
	//=========================================================================
	public void display(GLAutoDrawable drawable) {
		GL2 api = drawable.getGL().getGL2();
		api.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	}
	//=========================================================================


	//=========================================================================
	public void dispose(GLAutoDrawable drawable) {
	}
	//=========================================================================
	
}
//*****************************************************************************
