//************************************************************************************************
package core.platform;
//************************************************************************************************

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

import core.api.ICanvas;
import core.api.IInputHandler;
import core.asset.Asset;
import core.event.EventManager;
import core.input.raw.InputAxis;
import core.input.raw.InputEvent;

//************************************************************************************************
public class Platform implements IPlatform, GLEventListener, IGraphics, KeyListener, MouseListener, WindowListener {

	//============================================================================================
	private interface AssetHandler {
		public void handle(Asset asset);
	}
	//============================================================================================
	
	//============================================================================================
	private GLWindow glWindow;
	//============================================================================================

	//============================================================================================
	private Map<String, Asset>        assetMap = new HashMap<>();
	private Set<String>               graphicsAssetTypeSet = new HashSet<>();
	private Map<String, AssetHandler> assetTypeHandlerMap  = new HashMap<>();
	private Map<String,TextRenderer>  fontAssetTypeMap     = new HashMap<>();
	//============================================================================================

	//============================================================================================
	private List<IInputHandler> inputHandlerList = new ArrayList<>();
	private List<ICanvas> canvasList = new ArrayList<>();
	//============================================================================================

	//============================================================================================
	private List<InputEvent> input  = new ArrayList<>();
	private List<InputEvent> output = new ArrayList<>();
	private List<InputEvent> pool   = new ArrayList<>();
	//============================================================================================

	//============================================================================================
	private EventManager eventManager = null;
	//============================================================================================
	
	//============================================================================================
	public Platform( EventManager eventManager) {
		
		this.eventManager = eventManager;
		
		graphicsAssetTypeSet.add("Font");
		graphicsAssetTypeSet.add("Texture");
		graphicsAssetTypeSet.add("Mesh");
		
		assetTypeHandlerMap.put("Font", this::handleFont);
		assetTypeHandlerMap.put("Texture", this::handleTexture);
		assetTypeHandlerMap.put("Mesh", this::handleMesh);
		
		var glProfile = GLProfile.getDefault();
		var glCapabilities = new GLCapabilities(glProfile);
		glWindow = GLWindow.create(glCapabilities);
		
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void addAsset(Asset asset) {
		assert(asset != null);
		asset.setState(Asset.State.INIT);
		assetMap.put(asset.getIdent(), asset);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void removeAsset(Asset asset) {
		assert(asset != null);
		asset.setState(Asset.State.DONE);
	}
	//============================================================================================

	//============================================================================================
	public void addInputHandler(IInputHandler handler) {
		if (!inputHandlerList.contains(handler)) {
			inputHandlerList.add(handler);
		}
	}
	//============================================================================================

	//============================================================================================
	public void removeInputHandler(IInputHandler handler) {
		inputHandlerList.remove(handler);
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void addCanvas(ICanvas canvas) {
		if (!canvasList.contains(canvas)) {
			canvasList.add(canvas);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void removeCanvas(ICanvas canvas) {
		canvasList.remove(canvas);
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void init() {		
		var dimension = Toolkit.getDefaultToolkit().getScreenSize();		
		glWindow.setSize(
			(int) dimension.getWidth(),
			(int) dimension.getHeight());
		glWindow.setTitle("CORE");
		glWindow.setMaximized(true, true);
		glWindow.addGLEventListener(this);
		glWindow.addKeyListener(this);
		glWindow.addMouseListener(this);
		glWindow.addWindowListener(this);
		glWindow.setDefaultCloseOperation(WindowClosingMode.DO_NOTHING_ON_CLOSE);
		glWindow.setVisible(true);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void done() {
		glWindow.setVisible(false);
		glWindow.destroy();
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void updateInputs() {
		
		var active = input;
		input = output;
		output = active;
		
		for (var event : active) {
			for (var handler : inputHandlerList) {
				handler.onInput(event);
			}
			free(event);
		}
		active.clear();
		
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void updateGraphics() {
		glWindow.display();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void init(GLAutoDrawable drawable) {
		
		var api = glWindow.getGL().getGL2();
		
		api.glDisable(GL2.GL_BLEND);
		api.glBlendEquation(GL2.GL_FUNC_ADD);
		api.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		
		api.glEnable(GL2.GL_COLOR_MATERIAL);
		api.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		
		api.glClearColor(0,  0,  1, 1);
		
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		var event = eventManager.createEvent();
		event.type = PlatformEventType.RESIZE;
		event.data = new float[] { width, height }; 
		eventManager.postEvent(event);
	}
	//============================================================================================
	
	//============================================================================================
	private static final String[] ClassMarker = new String[] {};
	@Override
	public void display(GLAutoDrawable drawable) {

		var api = glWindow.getGL().getGL2();		
		var glu = GLU.createGLU(api);
				
		for (var key : assetMap.keySet().toArray(ClassMarker)) {
			var asset = assetMap.get(key);
			switch (asset.getState()) {
			case INIT:
				if (graphicsAssetTypeSet.contains(asset.getType())) {
					var handler = assetTypeHandlerMap.get(asset.getType());
					if (handler != null) handler.handle(asset);
					asset.setState(Asset.State.ACTIVE);
				}
				break;
			case DONE:
				if (graphicsAssetTypeSet.contains(asset.getType())) {
					var handler = assetTypeHandlerMap.get(asset.getType());
					if (handler != null) handler.handle(asset);
					asset.setState(Asset.State.NONE);
					assetMap.remove(key);
				}
				break;
			default: break;
			}
		}

		api.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		api.glMatrixMode(GL2.GL_PROJECTION);
		api.glLoadIdentity();
		float left   = 0f;
		float right  = glWindow.getSurfaceWidth();
		float bottom = 0f;
		float top    = glWindow.getSurfaceHeight();
		glu.gluOrtho2D(left, right, bottom, top);
		
		
		api.glMatrixMode(GL2.GL_MODELVIEW);
		api.glLoadIdentity();

		api.glDisable(GL2.GL_BLEND);
		api.glDisable(GL2.GL_DEPTH_TEST);
		api.glDisable(GL2.GL_LIGHTING);

		api.glColor3f(0, 0, 0);
		
		for (var canvas : canvasList) {
			canvas.onPaint(this);
		}
		
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
	//============================================================================================

	//============================================================================================
	public String getTitle() {
		return glWindow.getTitle();
	}
	//============================================================================================

	//============================================================================================
	public void setTitle(String title) {
		glWindow.setTitle(title);
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public int getWidth() {
		return glWindow.getWidth();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public int getHeight() {
		return glWindow.getHeight();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void setColor(Color3f color) {
		var api = glWindow.getGL().getGL2();
		api.glDisable(GL2.GL_BLEND);
		api.glColor3f(color.x, color.y, color.z);
		for (var key : fontAssetTypeMap.keySet()) {
			TextRenderer textRenderer = fontAssetTypeMap.get(key);
			textRenderer.setColor(color.x, color.y, color.z, 1);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void setColor(Color4f color) {
		var api = glWindow.getGL().getGL2();
		api.glEnable(GL2.GL_BLEND);
		api.glColor4f(color.x, color.y, color.z, color.w);
		for (var key : fontAssetTypeMap.keySet()) {
			TextRenderer textRenderer = fontAssetTypeMap.get(key);
			textRenderer.setColor(color.x, color.y, color.z, color.w);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void setColor(float r, float g, float b) {
		var api = glWindow.getGL().getGL2();
		api.glDisable(GL2.GL_BLEND);
		api.glColor3f(r, g, b);
		for (var key : fontAssetTypeMap.keySet()) {
			TextRenderer textRenderer = fontAssetTypeMap.get(key);
			textRenderer.setColor(r, g, b, 1);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void setColor(float r, float g, float b, float a) {
		var api = glWindow.getGL().getGL2();
		api.glEnable(GL2.GL_BLEND);
		api.glColor4f(r, g, b, a);
		for (var key : fontAssetTypeMap.keySet()) {
			TextRenderer textRenderer = fontAssetTypeMap.get(key);
			textRenderer.setColor(r, g, b, a);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void push() {
		var api = glWindow.getGL().getGL2();
		api.glPushMatrix();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void pop() {
		var api = glWindow.getGL().getGL2();
		api.glPopMatrix();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void translate(float dx, float dy) {
		var api = glWindow.getGL().getGL2();
		api.glTranslatef(dx, dy, 0);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void rotate(float a) {
		var api = glWindow.getGL().getGL2();
		api.glRotatef(a, 0, 0, 1);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void drawPoints(float ... coords) {
		var api = glWindow.getGL().getGL2();
		assert(coords.length >= 2);
		assert(coords.length % 2 == 0);
		api.glBegin(GL2.GL_POINTS);
		for (int i=0; i<coords.length/2; i++)
			api.glVertex2fv(coords, i*2);
		api.glEnd();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void drawPolyline(float ... coords) {
		var api = glWindow.getGL().getGL2();
		assert(coords.length >= 4);
		assert(coords.length % 2 == 0);
		api.glBegin(GL2.GL_LINE_STRIP);
		for (int i=0; i<coords.length/2; i++)
			api.glVertex2fv(coords, i*2);
		api.glEnd();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void drawClosedPolyline(float ... coords) {
		var api = glWindow.getGL().getGL2();
		assert(coords.length >= 4);
		assert(coords.length % 2 == 0);
		api.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<coords.length/2; i++)
			api.glVertex2fv(coords, i*2);
		api.glEnd();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void drawPolygon(float ... coords) {
		var api = glWindow.getGL().getGL2();
		assert(coords.length >= 4);
		assert(coords.length % 2 == 0);
		api.glBegin(GL2.GL_TRIANGLE_FAN);
		for (int i=0; i<coords.length/2; i++)
			api.glVertex2fv(coords, i*2);
		api.glEnd();
	}
	//============================================================================================

	//============================================================================================
	private TextRenderer usedRenderer = null;
	//============================================================================================
	
	//============================================================================================
	@Override
	public void startTextRaw(String font) {
		
		assert(usedRenderer == null);
		
		TextRenderer textRenderer = fontAssetTypeMap.get(font);
		if (textRenderer == null) textRenderer = fontAssetTypeMap.get("system");
		
		usedRenderer = textRenderer; 
		usedRenderer.begin3DRendering();
		
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void drawTextRaw(String text, float x, float y) {

		assert(usedRenderer != null);

		usedRenderer.draw3D(text, x, y, 0, 1); 
		
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void endTextRaw() {
		
		assert(usedRenderer != null);
		
		usedRenderer.end3DRendering();		
		usedRenderer = null;
		
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void drawText(String font, String text, float x, float y) {

		assert(usedRenderer == null);
		
		TextRenderer textRenderer = fontAssetTypeMap.get(font);
		if (textRenderer == null) textRenderer = fontAssetTypeMap.get("system");
		
		textRenderer.begin3DRendering();
		textRenderer.draw3D(text, x, y, 0, 1); 
		textRenderer.end3DRendering();
		
	}
	//============================================================================================

	//============================================================================================
	public TextProbe probeText(String font, String text, TextProbe probe) {
		
		TextRenderer textRenderer = fontAssetTypeMap.get(font);
		if (textRenderer == null) textRenderer = fontAssetTypeMap.get("system");
		var rect = textRenderer.getBounds(text);
		if (probe == null) probe = new TextProbe();
		probe.width = (float) rect.getWidth();
		probe.height = (float) rect.getHeight();
		probe.descent = (float) rect.getY();
		probe.ascent = probe.height - probe.descent;

		var frc = textRenderer.getFontRenderContext();
		var nativeFont = textRenderer.getFont();
		var gv = nativeFont.createGlyphVector(frc, text);
		var start = gv.getGlyphPosition(0);
		var end = gv.getGlyphPosition(gv.getNumGlyphs());
		float advance = (float)(end.getX() - start.getX());		
		probe.advance = advance;
		
		return probe;
		
	}
	//============================================================================================
	
	//============================================================================================
	private void handleFont(Asset asset) {

		switch (asset.getState()) {
		
			case INIT -> {
				var code = asset.getProperty("code");
				Font font = Font.decode(code);
				TextRenderer renderer = new TextRenderer(font, true);
				renderer.setSmoothing(false);
				renderer.setUseVertexArrays(true);
				
				fontAssetTypeMap.put(asset.getIdent(), renderer);
			}
	
			case DONE -> {
				TextRenderer renderer = fontAssetTypeMap.remove(asset.getIdent());
				if (renderer != null) {
					renderer.dispose();
				}
			}
		
			default -> {}
			
		}
		
	}
	//============================================================================================

	//============================================================================================
	private void handleTexture(Asset asset) {
		
	}
	//============================================================================================
	
	//============================================================================================
	private void handleMesh(Asset asset) {
		
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseClicked(MouseEvent e) {
		var axis = InputAxis.NONE;
		switch (e.getButton()) {
			case 1: axis = InputAxis.PT_BTN1; break;
			case 2: axis = InputAxis.PT_BTN2; break;
			case 3: axis = InputAxis.PT_BTN3; break;
			case 4: axis = InputAxis.PT_BTN4; break;
			case 5: axis = InputAxis.PT_BTN5; break;
			case 6: axis = InputAxis.PT_BTN6; break;
			case 7: axis = InputAxis.PT_BTN7; break;
			case 8: axis = InputAxis.PT_BTN8; break;
			default: break; // do nothing
		}
		if (!axis.equals(InputAxis.NONE)) {
			var event = alloc();
			event.set(axis, InputEvent.VALUE_TYPED);
			input.add(event);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseEntered(MouseEvent e) {}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseExited(MouseEvent e) {}
	//============================================================================================

	//============================================================================================
	@Override
	public void mousePressed(MouseEvent e) {
		var axis = InputAxis.NONE;
		switch (e.getButton()) {
			case 1: axis = InputAxis.PT_BTN1; break;
			case 2: axis = InputAxis.PT_BTN2; break;
			case 3: axis = InputAxis.PT_BTN3; break;
			case 4: axis = InputAxis.PT_BTN4; break;
			case 5: axis = InputAxis.PT_BTN5; break;
			case 6: axis = InputAxis.PT_BTN6; break;
			case 7: axis = InputAxis.PT_BTN7; break;
			case 8: axis = InputAxis.PT_BTN8; break;
			default: break; // do nothing
		}
		if (!axis.equals(InputAxis.NONE)) {
			var event = alloc();
			event.set(axis, InputEvent.VALUE_PRESSED);
			input.add(event);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseReleased(MouseEvent e) {
		var axis = InputAxis.NONE;
		switch (e.getButton()) {
			case 1: axis = InputAxis.PT_BTN1; break;
			case 2: axis = InputAxis.PT_BTN2; break;
			case 3: axis = InputAxis.PT_BTN3; break;
			case 4: axis = InputAxis.PT_BTN4; break;
			case 5: axis = InputAxis.PT_BTN5; break;
			case 6: axis = InputAxis.PT_BTN6; break;
			case 7: axis = InputAxis.PT_BTN7; break;
			case 8: axis = InputAxis.PT_BTN8; break;
			default: break; // do nothing
		}
		if (!axis.equals(InputAxis.NONE)) {
			var event = alloc();
			event.set(axis, InputEvent.VALUE_RELEASED);
			input.add(event);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseMoved(MouseEvent e) {
		{
			var event = alloc();
			event.set(InputAxis.PT_X, e.getX());
			input.add(event);
		}{
			var event = alloc();
			event.set(InputAxis.PT_Y, e.getY());
			input.add(event);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseDragged(MouseEvent e) {}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		var event = alloc();
		event.set(InputAxis.PT_Z, e.getRotation()[1] * e.getRotationScale() );
		input.add(event);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void keyPressed(KeyEvent e) {
		var axis = map(e.getKeyCode()); 
		if (axis.equals(InputAxis.NONE) && e.isPrintableKey()) {
			axis = InputAxis.KB_UNKNOWN;
		}
		if (!axis.equals(InputAxis.NONE)) {
			if (!e.isAutoRepeat()) {
				var event = alloc();
				var character = '\0';
				if (e.isPrintableKey()) character = e.getKeyChar();
				event.set(axis, InputEvent.VALUE_PRESSED, character);
				input.add(event);
			}
			if (e.isPrintableKey()) {
				var event = alloc();
				var character = e.getKeyChar();
				event.set(axis, InputEvent.VALUE_TYPED, character);
				input.add(event);			
			}
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void keyReleased(KeyEvent e) {
		var axis = map(e.getKeyCode());
		if (axis.equals(InputAxis.NONE) && e.isPrintableKey()) {
			axis = InputAxis.KB_UNKNOWN;
		}
		if (!axis.equals(InputAxis.NONE)) {
			if (!e.isAutoRepeat()) {
				var event = alloc();
				var character = '\0';
				if (e.isPrintableKey()) character = e.getKeyChar();
				event.set(axis, InputEvent.VALUE_RELEASED, character);
				input.add(event);
			}
		}
	}
	//============================================================================================

	//============================================================================================
	private InputAxis map(int vKey) {
		var axis = InputAxis.NONE;
		switch (vKey) {
			case KeyEvent.VK_F1:           axis = InputAxis.KB_F1; break;
			case KeyEvent.VK_F2:           axis = InputAxis.KB_F2; break;
			case KeyEvent.VK_F3:           axis = InputAxis.KB_F3; break;
			case KeyEvent.VK_F4:           axis = InputAxis.KB_F4; break;
			case KeyEvent.VK_F5:           axis = InputAxis.KB_F5; break;
			case KeyEvent.VK_F6:           axis = InputAxis.KB_F6; break;
			case KeyEvent.VK_F7:           axis = InputAxis.KB_F7; break;
			case KeyEvent.VK_F8:           axis = InputAxis.KB_F8; break;
			case KeyEvent.VK_F9:           axis = InputAxis.KB_F9; break;
			case KeyEvent.VK_F10:          axis = InputAxis.KB_F10; break;
			case KeyEvent.VK_F11:          axis = InputAxis.KB_F11; break;
			case KeyEvent.VK_F12:          axis = InputAxis.KB_F12; break;
			case KeyEvent.VK_0:            axis = InputAxis.KB_0; break;
			case KeyEvent.VK_1:            axis = InputAxis.KB_1; break;
			case KeyEvent.VK_2:            axis = InputAxis.KB_2; break;
			case KeyEvent.VK_3:            axis = InputAxis.KB_3; break;
			case KeyEvent.VK_4:            axis = InputAxis.KB_4; break;
			case KeyEvent.VK_5:            axis = InputAxis.KB_5; break;
			case KeyEvent.VK_6:            axis = InputAxis.KB_6; break;
			case KeyEvent.VK_7:            axis = InputAxis.KB_7; break;
			case KeyEvent.VK_8:            axis = InputAxis.KB_8; break;
			case KeyEvent.VK_9:            axis = InputAxis.KB_9; break;
			case KeyEvent.VK_A:            axis = InputAxis.KB_A; break;
			case KeyEvent.VK_B:            axis = InputAxis.KB_B; break;
			case KeyEvent.VK_C:            axis = InputAxis.KB_C; break;
			case KeyEvent.VK_D:            axis = InputAxis.KB_D; break;
			case KeyEvent.VK_E:            axis = InputAxis.KB_E; break;
			case KeyEvent.VK_F:            axis = InputAxis.KB_F; break;
			case KeyEvent.VK_G:            axis = InputAxis.KB_G; break;
			case KeyEvent.VK_H:            axis = InputAxis.KB_H; break;
			case KeyEvent.VK_I:            axis = InputAxis.KB_I; break;
			case KeyEvent.VK_J:            axis = InputAxis.KB_J; break;
			case KeyEvent.VK_K:            axis = InputAxis.KB_K; break;
			case KeyEvent.VK_L:            axis = InputAxis.KB_L; break;
			case KeyEvent.VK_M:            axis = InputAxis.KB_M; break;
			case KeyEvent.VK_N:            axis = InputAxis.KB_N; break;
			case KeyEvent.VK_O:            axis = InputAxis.KB_O; break;
			case KeyEvent.VK_P:            axis = InputAxis.KB_P; break;
			case KeyEvent.VK_Q:            axis = InputAxis.KB_Q; break;
			case KeyEvent.VK_R:            axis = InputAxis.KB_R; break;
			case KeyEvent.VK_S:            axis = InputAxis.KB_S; break;
			case KeyEvent.VK_T:            axis = InputAxis.KB_T; break;
			case KeyEvent.VK_U:            axis = InputAxis.KB_U; break;
			case KeyEvent.VK_V:            axis = InputAxis.KB_V; break;
			case KeyEvent.VK_W:            axis = InputAxis.KB_W; break;
			case KeyEvent.VK_X:            axis = InputAxis.KB_X; break;
			case KeyEvent.VK_Y:            axis = InputAxis.KB_Y; break;
			case KeyEvent.VK_Z:            axis = InputAxis.KB_Z; break;
			case KeyEvent.VK_SPACE:        axis = InputAxis.KB_SPACE; break;
			case KeyEvent.VK_ESCAPE:       axis = InputAxis.KB_ESCAPE; break;
			case KeyEvent.VK_NUMBER_SIGN:  axis = InputAxis.KB_HASH; break;  // without effect?
			case KeyEvent.VK_PLUS:         axis = InputAxis.KB_PLUS; break;  // without effect?
			case KeyEvent.VK_MINUS:        axis = InputAxis.KB_MINUS; break;
			case KeyEvent.VK_COMMA:        axis = InputAxis.KB_COMMA; break;
			case KeyEvent.VK_PERIOD:       axis = InputAxis.KB_PERIOD; break;
			//case KeyEvent.VK_LESS:         axis = InputAxis.KB_LESS; break;  // without effect?
			case KeyEvent.VK_PRINTSCREEN:  axis = InputAxis.KB_PRINT; break; // without effect?
			case KeyEvent.VK_SCROLL_LOCK:  axis = InputAxis.KB_SCROLL; break;
			case KeyEvent.VK_PAUSE:        axis = InputAxis.KB_PAUSE; break;
			case KeyEvent.VK_BACK_SPACE:   axis = InputAxis.KB_BACKSPACE; break;
			case KeyEvent.VK_TAB:          axis = InputAxis.KB_TAB; break;
			case KeyEvent.VK_ENTER:        axis = InputAxis.KB_ENTER; break;
			case KeyEvent.VK_CAPS_LOCK:    axis = InputAxis.KB_CAPS_LOCK; break;
			case KeyEvent.VK_SHIFT:        axis = InputAxis.KB_SHIFT; break;
			case KeyEvent.VK_CONTROL:      axis = InputAxis.KB_CONTROL; break;
			case KeyEvent.VK_ALT:          axis = InputAxis.KB_ALT; break;
			// case KeyEvent.VK_ALT_GRAPH: axis = InputAxis.KB_ALT_GRAPH; break;  // causes problems
			// case KeyEvent.VK_WINDOWS: axis = InputAxis.KB_SYSTEM; break; // causes problems
			case KeyEvent.VK_CONTEXT_MENU: axis = InputAxis.KB_CONTEXT_MENU; break;
			case KeyEvent.VK_INSERT:       axis = InputAxis.KB_INSERT; break;
			case KeyEvent.VK_DELETE:       axis = InputAxis.KB_DELETE; break;
			case KeyEvent.VK_HOME:         axis = InputAxis.KB_POS1; break;
			case KeyEvent.VK_END:          axis = InputAxis.KB_END; break;
			case KeyEvent.VK_PAGE_UP:      axis = InputAxis.KB_PAGE_UP; break;
			case KeyEvent.VK_PAGE_DOWN:    axis = InputAxis.KB_PAGE_DOWN; break;
			case KeyEvent.VK_UP:           axis = InputAxis.KB_UP; break;
			case KeyEvent.VK_DOWN:         axis = InputAxis.KB_DOWN; break;
			case KeyEvent.VK_LEFT:         axis = InputAxis.KB_LEFT; break;
			case KeyEvent.VK_RIGHT:        axis = InputAxis.KB_RIGHT; break;
			case KeyEvent.VK_NUM_LOCK:     axis = InputAxis.KB_NUM_LOCK; break;
			case KeyEvent.VK_MULTIPLY:     axis = InputAxis.KB_MULTIPLY; break;
			case KeyEvent.VK_DIVIDE:       axis = InputAxis.KB_DIVIDE; break;
			case KeyEvent.VK_ADD:          axis = InputAxis.KB_ADD; break;
			case KeyEvent.VK_SUBTRACT:     axis = InputAxis.KB_SUBTRACT; break;
			case KeyEvent.VK_DECIMAL:      axis = InputAxis.KB_DECIMAL; break;
			case KeyEvent.VK_NUMPAD0:      axis = InputAxis.KB_NP0; break;
			case KeyEvent.VK_NUMPAD1:      axis = InputAxis.KB_NP1; break;
			case KeyEvent.VK_NUMPAD2:      axis = InputAxis.KB_NP2; break;
			case KeyEvent.VK_NUMPAD3:      axis = InputAxis.KB_NP3; break;
			case KeyEvent.VK_NUMPAD4:      axis = InputAxis.KB_NP4; break;
			case KeyEvent.VK_NUMPAD5:      axis = InputAxis.KB_NP5; break;
			case KeyEvent.VK_NUMPAD6:      axis = InputAxis.KB_NP6; break;
			case KeyEvent.VK_NUMPAD7:      axis = InputAxis.KB_NP7; break;
			case KeyEvent.VK_NUMPAD8:      axis = InputAxis.KB_NP8; break;
			case KeyEvent.VK_NUMPAD9:      axis = InputAxis.KB_NP9; break;
			default: break;
		}
		return axis;
	}
	//============================================================================================
	
	//============================================================================================
	private InputEvent alloc() {
		InputEvent event = null;
		try {
			event = pool.removeFirst();
		} catch (NoSuchElementException e) {
			event = new InputEvent();
		}
		return event;
	}
	//============================================================================================

	//============================================================================================
	private void free(InputEvent event ) {
		event.clear();
		pool.add(event);
	}
	//============================================================================================

	//============================================================================================
	@Override public void windowResized(WindowEvent e) {}
	@Override public void windowMoved(WindowEvent e) {}
	@Override public void windowDestroyNotify(WindowEvent e) {
		var event = eventManager.createEvent();
		event.type = PlatformEventType.TERMINATE;
		eventManager.postEvent(event);
	}
	@Override public void windowDestroyed(WindowEvent e) {}
	@Override public void windowGainedFocus(WindowEvent e) {}
	@Override public void windowLostFocus(WindowEvent e) {}
	@Override public void windowRepaint(WindowUpdateEvent e) {}

}
//************************************************************************************************
