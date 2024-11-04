//*************************************************************************************************
package jlib.platform;
//*************************************************************************************************

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import jlib.application.Application;
import jlib.gui.GUI;

//*************************************************************************************************
public class Platform {

	//=============================================================================================
	private GLWindow window;
	//=============================================================================================
	
	//=============================================================================================
	private InputEventHandler  inputEventHandler;
	private WindowEventHandler windowEventHandler;
	private OpenGLEventHandler glEventHandler;
	//=============================================================================================
	
	//=============================================================================================
	public Platform(Application app, GUI gui) {
		inputEventHandler = new InputEventHandler(app);
		windowEventHandler = new WindowEventHandler(app);
		glEventHandler = new OpenGLEventHandler(gui);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init() {
		var glProfile = GLProfile.getDefault();
		var glCapabilities = new GLCapabilities(glProfile);
		window = GLWindow.create(glCapabilities);
		window.setTitle("JEB");
		window.setSize(800, 600);
		window.setDefaultCloseOperation(WindowClosingMode.DO_NOTHING_ON_CLOSE);
		window.addKeyListener(inputEventHandler);
		window.addMouseListener(inputEventHandler);
		window.addWindowListener(windowEventHandler);
		window.addGLEventListener(glEventHandler);
		window.setVisible(true);
	}
	//=============================================================================================

	//=============================================================================================
	public void update() {
		window.display();
	}
	//=============================================================================================

	//=============================================================================================
	public void done() {
		window.setVisible(false);
		window.destroy();
	}
	//=============================================================================================

	//=============================================================================================
	public void title(String title) {
		window.setTitle(title);
	}
	//=============================================================================================

	//=============================================================================================
	public String title() {
		return window.getTitle();
	}
	//=============================================================================================
	
}
//*************************************************************************************************

