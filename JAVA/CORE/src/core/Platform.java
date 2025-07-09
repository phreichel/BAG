//************************************************************************************************
package core;
//************************************************************************************************

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

//************************************************************************************************
public class Platform implements IPlatform, GLEventListener, IGraphics {

	//============================================================================================
	private interface AssetHandler {
		public void handle(Asset asset);
	}
	//============================================================================================
	
	//============================================================================================
	private GLWindow glWindow;
	private Map<String, Asset>        assetMap = new HashMap<>();
	private Set<String>               graphicsAssetTypeSet = new HashSet<>();
	private Map<String, AssetHandler> assetTypeHandlerMap  = new HashMap<>();
	private Map<String,TextRenderer>  fontAssetTypeMap     = new HashMap<>();
	private List<ICanvas> canvasList = new ArrayList<>();
	//============================================================================================

	//============================================================================================
	public Platform() {
		
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
		glWindow.setVisible(true);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void update() {
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
		// TODO Auto-generated method stub
		
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
	public void push() {
		var api = glWindow.getGL().getGL2();
		api.glPushMatrix();
	}
	//============================================================================================

	//============================================================================================
	public void pop() {
		var api = glWindow.getGL().getGL2();
		api.glPopMatrix();
	}
	//============================================================================================

	//============================================================================================
	public void translate(float dx, float dy) {
		var api = glWindow.getGL().getGL2();
		api.glTranslatef(dx, dy, 0);
	}
	//============================================================================================

	//============================================================================================
	public void rotate(float a) {
		var api = glWindow.getGL().getGL2();
		api.glRotatef(a, 0, 0, 1);
	}
	//============================================================================================

	//============================================================================================
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
	public void drawText(String font, String text, float x, float y) {
		
		TextRenderer textRenderer = fontAssetTypeMap.get(font);
		if (textRenderer == null) textRenderer = fontAssetTypeMap.get("system");
		
		textRenderer.begin3DRendering();
		textRenderer.draw3D(text, x, y, 0, 1); 
		textRenderer.end3DRendering();
		
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
}
//************************************************************************************************
