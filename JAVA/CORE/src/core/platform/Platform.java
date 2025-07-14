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
import core.input.EventType;
import core.input.InputEvent;

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
		event.type = EventType.RESIZE;
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
		/*
		{
			var event = alloc();
			event.set(InputEvent.Axis.PT_X, e.getX());
			inputList.add(event);
		}{
			var event = alloc();
			event.set(InputEvent.Axis.PT_Y, e.getY());
			inputList.add(event);
		}
		*/
		{
			var axis = InputEvent.Axis.NONE;
			switch (e.getButton()) {
				case 1: axis = InputEvent.Axis.PT_BTN1; break;
				case 2: axis = InputEvent.Axis.PT_BTN2; break;
				case 3: axis = InputEvent.Axis.PT_BTN3; break;
				case 4: axis = InputEvent.Axis.PT_BTN4; break;
				case 5: axis = InputEvent.Axis.PT_BTN5; break;
				case 6: axis = InputEvent.Axis.PT_BTN6; break;
				case 7: axis = InputEvent.Axis.PT_BTN7; break;
				case 8: axis = InputEvent.Axis.PT_BTN8; break;
				default: break; // do nothing
			}
			if (!axis.equals(InputEvent.Axis.NONE)) {
				var event = alloc();
				event.set(axis, InputEvent.VALUE_TYPED);
				input.add(event);
			}
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
		/*
		{
			var event = alloc();
			event.set(InputEvent.Axis.PT_X, e.getX());
			inputList.add(event);
		}{
			var event = alloc();
			event.set(InputEvent.Axis.PT_Y, e.getY());
			inputList.add(event);
		}
		*/
		{
			var axis = InputEvent.Axis.NONE;
			switch (e.getButton()) {
				case 1: axis = InputEvent.Axis.PT_BTN1; break;
				case 2: axis = InputEvent.Axis.PT_BTN2; break;
				case 3: axis = InputEvent.Axis.PT_BTN3; break;
				case 4: axis = InputEvent.Axis.PT_BTN4; break;
				case 5: axis = InputEvent.Axis.PT_BTN5; break;
				case 6: axis = InputEvent.Axis.PT_BTN6; break;
				case 7: axis = InputEvent.Axis.PT_BTN7; break;
				case 8: axis = InputEvent.Axis.PT_BTN8; break;
				default: break; // do nothing
			}
			if (!axis.equals(InputEvent.Axis.NONE)) {
				var event = alloc();
				event.set(axis, InputEvent.VALUE_PRESSED);
				input.add(event);
			}
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseReleased(MouseEvent e) {
		/*
		{
			var event = alloc();
			event.set(InputEvent.Axis.PT_X, e.getX());
			inputList.add(event);
		}{
			var event = alloc();
			event.set(InputEvent.Axis.PT_Y, e.getY());
			inputList.add(event);
		}
		*/
		{
			var axis = InputEvent.Axis.NONE;
			switch (e.getButton()) {
				case 1: axis = InputEvent.Axis.PT_BTN1; break;
				case 2: axis = InputEvent.Axis.PT_BTN2; break;
				case 3: axis = InputEvent.Axis.PT_BTN3; break;
				case 4: axis = InputEvent.Axis.PT_BTN4; break;
				case 5: axis = InputEvent.Axis.PT_BTN5; break;
				case 6: axis = InputEvent.Axis.PT_BTN6; break;
				case 7: axis = InputEvent.Axis.PT_BTN7; break;
				case 8: axis = InputEvent.Axis.PT_BTN8; break;
				default: break; // do nothing
			}
			if (!axis.equals(InputEvent.Axis.NONE)) {
				var event = alloc();
				event.set(axis, InputEvent.VALUE_RELEASED);
				input.add(event);
			}
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseMoved(MouseEvent e) {
		{
			var event = alloc();
			event.set(InputEvent.Axis.PT_X, e.getX());
			input.add(event);
		}{
			var event = alloc();
			event.set(InputEvent.Axis.PT_Y, e.getY());
			input.add(event);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseDragged(MouseEvent e) {
		/*
		{
			var event = alloc();
			event.set(InputEvent.Axis.PT_X, e.getX());
			inputList.add(event);
		}{
			var event = alloc();
			event.set(InputEvent.Axis.PT_Y, e.getY());
			inputList.add(event);
		}
		*/
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		var event = alloc();
		event.set(InputEvent.Axis.PT_Z, e.getRotation()[1] * e.getRotationScale() );
		input.add(event);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void keyPressed(KeyEvent e) {
		var axis = map(e.getKeyCode()); 
		if (axis.equals(InputEvent.Axis.NONE) && e.isPrintableKey()) {
			axis = InputEvent.Axis.KB_UNKNOWN;
		}
		if (!axis.equals(InputEvent.Axis.NONE)) {
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
		if (axis.equals(InputEvent.Axis.NONE) && e.isPrintableKey()) {
			axis = InputEvent.Axis.KB_UNKNOWN;
		}
		if (!axis.equals(InputEvent.Axis.NONE)) {
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
	private InputEvent.Axis map(int vKey) {
		var axis = InputEvent.Axis.NONE;
		switch (vKey) {
			case KeyEvent.VK_F1:           axis = InputEvent.Axis.KB_F1; break;
			case KeyEvent.VK_F2:           axis = InputEvent.Axis.KB_F2; break;
			case KeyEvent.VK_F3:           axis = InputEvent.Axis.KB_F3; break;
			case KeyEvent.VK_F4:           axis = InputEvent.Axis.KB_F4; break;
			case KeyEvent.VK_F5:           axis = InputEvent.Axis.KB_F5; break;
			case KeyEvent.VK_F6:           axis = InputEvent.Axis.KB_F6; break;
			case KeyEvent.VK_F7:           axis = InputEvent.Axis.KB_F7; break;
			case KeyEvent.VK_F8:           axis = InputEvent.Axis.KB_F8; break;
			case KeyEvent.VK_F9:           axis = InputEvent.Axis.KB_F9; break;
			case KeyEvent.VK_F10:          axis = InputEvent.Axis.KB_F10; break;
			case KeyEvent.VK_F11:          axis = InputEvent.Axis.KB_F11; break;
			case KeyEvent.VK_F12:          axis = InputEvent.Axis.KB_F12; break;
			case KeyEvent.VK_0:            axis = InputEvent.Axis.KB_0; break;
			case KeyEvent.VK_1:            axis = InputEvent.Axis.KB_1; break;
			case KeyEvent.VK_2:            axis = InputEvent.Axis.KB_2; break;
			case KeyEvent.VK_3:            axis = InputEvent.Axis.KB_3; break;
			case KeyEvent.VK_4:            axis = InputEvent.Axis.KB_4; break;
			case KeyEvent.VK_5:            axis = InputEvent.Axis.KB_5; break;
			case KeyEvent.VK_6:            axis = InputEvent.Axis.KB_6; break;
			case KeyEvent.VK_7:            axis = InputEvent.Axis.KB_7; break;
			case KeyEvent.VK_8:            axis = InputEvent.Axis.KB_8; break;
			case KeyEvent.VK_9:            axis = InputEvent.Axis.KB_9; break;
			case KeyEvent.VK_A:            axis = InputEvent.Axis.KB_A; break;
			case KeyEvent.VK_B:            axis = InputEvent.Axis.KB_B; break;
			case KeyEvent.VK_C:            axis = InputEvent.Axis.KB_C; break;
			case KeyEvent.VK_D:            axis = InputEvent.Axis.KB_D; break;
			case KeyEvent.VK_E:            axis = InputEvent.Axis.KB_E; break;
			case KeyEvent.VK_F:            axis = InputEvent.Axis.KB_F; break;
			case KeyEvent.VK_G:            axis = InputEvent.Axis.KB_G; break;
			case KeyEvent.VK_H:            axis = InputEvent.Axis.KB_H; break;
			case KeyEvent.VK_I:            axis = InputEvent.Axis.KB_I; break;
			case KeyEvent.VK_J:            axis = InputEvent.Axis.KB_J; break;
			case KeyEvent.VK_K:            axis = InputEvent.Axis.KB_K; break;
			case KeyEvent.VK_L:            axis = InputEvent.Axis.KB_L; break;
			case KeyEvent.VK_M:            axis = InputEvent.Axis.KB_M; break;
			case KeyEvent.VK_N:            axis = InputEvent.Axis.KB_N; break;
			case KeyEvent.VK_O:            axis = InputEvent.Axis.KB_O; break;
			case KeyEvent.VK_P:            axis = InputEvent.Axis.KB_P; break;
			case KeyEvent.VK_Q:            axis = InputEvent.Axis.KB_Q; break;
			case KeyEvent.VK_R:            axis = InputEvent.Axis.KB_R; break;
			case KeyEvent.VK_S:            axis = InputEvent.Axis.KB_S; break;
			case KeyEvent.VK_T:            axis = InputEvent.Axis.KB_T; break;
			case KeyEvent.VK_U:            axis = InputEvent.Axis.KB_U; break;
			case KeyEvent.VK_V:            axis = InputEvent.Axis.KB_V; break;
			case KeyEvent.VK_W:            axis = InputEvent.Axis.KB_W; break;
			case KeyEvent.VK_X:            axis = InputEvent.Axis.KB_X; break;
			case KeyEvent.VK_Y:            axis = InputEvent.Axis.KB_Y; break;
			case KeyEvent.VK_Z:            axis = InputEvent.Axis.KB_Z; break;
			case KeyEvent.VK_SPACE:        axis = InputEvent.Axis.KB_SPACE; break;
			case KeyEvent.VK_ESCAPE:       axis = InputEvent.Axis.KB_ESCAPE; break;
			case KeyEvent.VK_NUMBER_SIGN:  axis = InputEvent.Axis.KB_HASH; break;  // without effect?
			case KeyEvent.VK_PLUS:         axis = InputEvent.Axis.KB_PLUS; break;  // without effect?
			case KeyEvent.VK_MINUS:        axis = InputEvent.Axis.KB_MINUS; break;
			case KeyEvent.VK_COMMA:        axis = InputEvent.Axis.KB_COMMA; break;
			case KeyEvent.VK_PERIOD:       axis = InputEvent.Axis.KB_PERIOD; break;
			//case KeyEvent.VK_LESS:         axis = InputEvent.Axis.KB_LESS; break;  // without effect?
			case KeyEvent.VK_PRINTSCREEN:  axis = InputEvent.Axis.KB_PRINT; break; // without effect?
			case KeyEvent.VK_SCROLL_LOCK:  axis = InputEvent.Axis.KB_SCROLL; break;
			case KeyEvent.VK_PAUSE:        axis = InputEvent.Axis.KB_PAUSE; break;
			case KeyEvent.VK_BACK_SPACE:   axis = InputEvent.Axis.KB_BACKSPACE; break;
			case KeyEvent.VK_TAB:          axis = InputEvent.Axis.KB_TAB; break;
			case KeyEvent.VK_ENTER:        axis = InputEvent.Axis.KB_ENTER; break;
			case KeyEvent.VK_CAPS_LOCK:    axis = InputEvent.Axis.KB_CAPS_LOCK; break;
			case KeyEvent.VK_SHIFT:        axis = InputEvent.Axis.KB_SHIFT; break;
			case KeyEvent.VK_CONTROL:      axis = InputEvent.Axis.KB_CONTROL; break;
			case KeyEvent.VK_ALT:          axis = InputEvent.Axis.KB_ALT; break;
			// case KeyEvent.VK_ALT_GRAPH: axis = InputEvent.Axis.KB_ALT_GRAPH; break;  // causes problems
			// case KeyEvent.VK_WINDOWS: axis = InputEvent.Axis.KB_SYSTEM; break; // causes problems
			case KeyEvent.VK_CONTEXT_MENU: axis = InputEvent.Axis.KB_CONTEXT_MENU; break;
			case KeyEvent.VK_INSERT:       axis = InputEvent.Axis.KB_INSERT; break;
			case KeyEvent.VK_DELETE:       axis = InputEvent.Axis.KB_DELETE; break;
			case KeyEvent.VK_HOME:         axis = InputEvent.Axis.KB_POS1; break;
			case KeyEvent.VK_END:          axis = InputEvent.Axis.KB_END; break;
			case KeyEvent.VK_PAGE_UP:      axis = InputEvent.Axis.KB_PAGE_UP; break;
			case KeyEvent.VK_PAGE_DOWN:    axis = InputEvent.Axis.KB_PAGE_DOWN; break;
			case KeyEvent.VK_UP:           axis = InputEvent.Axis.KB_UP; break;
			case KeyEvent.VK_DOWN:         axis = InputEvent.Axis.KB_DOWN; break;
			case KeyEvent.VK_LEFT:         axis = InputEvent.Axis.KB_LEFT; break;
			case KeyEvent.VK_RIGHT:        axis = InputEvent.Axis.KB_RIGHT; break;
			case KeyEvent.VK_NUM_LOCK:     axis = InputEvent.Axis.KB_NUM_LOCK; break;
			case KeyEvent.VK_MULTIPLY:     axis = InputEvent.Axis.KB_MULTIPLY; break;
			case KeyEvent.VK_DIVIDE:       axis = InputEvent.Axis.KB_DIVIDE; break;
			case KeyEvent.VK_ADD:          axis = InputEvent.Axis.KB_ADD; break;
			case KeyEvent.VK_SUBTRACT:     axis = InputEvent.Axis.KB_SUBTRACT; break;
			case KeyEvent.VK_DECIMAL:      axis = InputEvent.Axis.KB_DECIMAL; break;
			case KeyEvent.VK_NUMPAD0:      axis = InputEvent.Axis.KB_NP0; break;
			case KeyEvent.VK_NUMPAD1:      axis = InputEvent.Axis.KB_NP1; break;
			case KeyEvent.VK_NUMPAD2:      axis = InputEvent.Axis.KB_NP2; break;
			case KeyEvent.VK_NUMPAD3:      axis = InputEvent.Axis.KB_NP3; break;
			case KeyEvent.VK_NUMPAD4:      axis = InputEvent.Axis.KB_NP4; break;
			case KeyEvent.VK_NUMPAD5:      axis = InputEvent.Axis.KB_NP5; break;
			case KeyEvent.VK_NUMPAD6:      axis = InputEvent.Axis.KB_NP6; break;
			case KeyEvent.VK_NUMPAD7:      axis = InputEvent.Axis.KB_NP7; break;
			case KeyEvent.VK_NUMPAD8:      axis = InputEvent.Axis.KB_NP8; break;
			case KeyEvent.VK_NUMPAD9:      axis = InputEvent.Axis.KB_NP9; break;
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
		event.type = EventType.TERMINATE;
		eventManager.postEvent(event);
	}
	@Override public void windowDestroyed(WindowEvent e) {}
	@Override public void windowGainedFocus(WindowEvent e) {}
	@Override public void windowLostFocus(WindowEvent e) {}
	@Override public void windowRepaint(WindowUpdateEvent e) {}

}
//************************************************************************************************
