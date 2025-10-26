//**************************************************************************************************
package jade;
//**************************************************************************************************

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import jade.gui.Canvas;
import jade.input.Input;
import jade.input.JOGLInput;
import jade.render.JOGLRenderer;
import jade.render.Renderer;
import jade.scene.Scene;
import lombok.Getter;

//**************************************************************************************************
public class Platform {

	//==============================================================================================
	private GLWindow window;
	@Getter private Input input;
	private Renderer renderer;
	//==============================================================================================

	//==============================================================================================
	public Platform() {
		
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		window = GLWindow.create(glCapabilities);
		window.setTitle("GEM Application Window");
		window.setSize(1024, 768);
		window.setMaximized(true, true);

		JOGLInput input = new JOGLInput();
		this.input = input;
		window.addKeyListener(input);
		window.addMouseListener(input);
		
		JOGLRenderer renderer = new JOGLRenderer();
		this.renderer = renderer;
		window.addGLEventListener(renderer);
		
	}
	//==============================================================================================

	//==============================================================================================
	public void setScene(Scene scene) {
		renderer.setScene(scene);
	}
	//==============================================================================================

	//==============================================================================================
	public void setCanvas(Canvas canvas) {
		input.setCanvas(canvas);
		renderer.setCanvas(canvas);
	}
	//==============================================================================================
	
	//==============================================================================================
	public boolean isPointerVisible() {
		return window.isPointerVisible();
	}
	//==============================================================================================
	
	//==============================================================================================
	public void setPointerVisible(boolean visible) {
		if (visible) {
			int cx = window.getSurfaceWidth() / 2;
			int cy = window.getSurfaceHeight() / 2;
			window.warpPointer(cx, cy);
		}
		window.setPointerVisible(visible);
	}
	//==============================================================================================

	//==============================================================================================
	public boolean isFullScreen() {
		return window.isFullscreen();
	}
	//==============================================================================================

	//==============================================================================================
	public void setFullScreen(boolean fullScreen) {
		window.setFullscreen(fullScreen);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void init() {
		window.setVisible(true);
	}
	//==============================================================================================

	//==============================================================================================
	public void update() {
		window.display();
	}
	//==============================================================================================

	//==============================================================================================
	public void done() {
		window.setVisible(false);
		window.destroy();
	}
	//==============================================================================================
	
}
//**************************************************************************************************
