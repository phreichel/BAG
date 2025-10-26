//************************************************************************************************
package core.gui;
//************************************************************************************************

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color4f;

//************************************************************************************************
public class Style {

	//============================================================================================
	public static final Style DEFAULT_STYLE = new Style("default");
	//============================================================================================
	
	//============================================================================================
	private static final Color4f  DEFAULT_COLOR   = new Color4f();
	private static final String   DEFAULT_FONT    = "system";
	private static final String   DEFAULT_TEXTURE = "system";
	private static final Insets4f DEFAULT_INSETS  = new Insets4f();
	//============================================================================================
	
	//============================================================================================
	private final String ident;
	private final Style  parent;
	//============================================================================================
	
	//============================================================================================
	private final Map<String, Color4f>   colorMap   = new HashMap<>();
	private final Map<String, String>    fontMap    = new HashMap<>();
	private final Map<String, String>    textureMap = new HashMap<>();
	private final Map<String, String>    textMap    = new HashMap<>();
	private final Map<String, Insets4f>  insetsMap = new HashMap<>();
	//============================================================================================

	//============================================================================================
	private final Map<String, Color4f>   colorMapReadonly   = Collections.unmodifiableMap(colorMap);
	private final Map<String, String>    fontMapReadonly    = Collections.unmodifiableMap(fontMap);
	private final Map<String, String>    textureMapReadonly = Collections.unmodifiableMap(textureMap);
	private final Map<String, String>    textMapReadonly    = Collections.unmodifiableMap(textMap);
	private final Map<String, Insets4f>  insetsMapReadonly = Collections.unmodifiableMap(insetsMap);
	//============================================================================================

	//============================================================================================
	public Style(String ident) {
		this(ident, null);
	}
	//============================================================================================

	//============================================================================================
	public Style(String ident, Style parent) {
		this.ident  = ident;
		this.parent = parent;
	}
	//============================================================================================

	//============================================================================================
	public Style derive(String ident) {
		return new Style(ident, this);
	}
	//============================================================================================
	
	//============================================================================================
	public String getIdent() {
		return this.ident;
	}
	//============================================================================================

	//============================================================================================
	public Style getParent() {
		return this.parent;
	}
	//============================================================================================
	
	//============================================================================================
	public Map<String, Color4f> getColors() {
		return this.colorMapReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Map<String, String> getFonts() {
		return this.fontMapReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Map<String, String> getTextures() {
		return this.textureMapReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Map<String, String> getStrings() {
		return this.textMapReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Map<String, Insets4f> getInsets() {
		return this.insetsMapReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Color4f getColor(String ident) {
		var item = this.colorMap.get(ident);
		if (item == null && parent != null) {
			item = parent.getColor(ident);
		}
		if (item == null) {
			item = DEFAULT_COLOR;
		}
		return item;
	}
	//============================================================================================

	//============================================================================================
	public String getText(String ident) {
		var item = this.textMap.get(ident);
		if (item == null && parent != null) {
			item = parent.getText(ident);
		}
		if (item == null) {
			item = ident;
		}
		return item;
	}
	//============================================================================================

	//============================================================================================
	public String getFont(String ident) {
		var item = this.fontMap.get(ident);
		if (item == null && parent != null) {
			item = parent.getFont(ident);
		}
		if (item == null) {
			item = DEFAULT_FONT;
		}
		return item;
	}
	//============================================================================================

	//============================================================================================
	public String getTexture(String ident) {
		var item = this.textureMap.get(ident);
		if (item == null && parent != null) {
			item = parent.getTexture(ident);
		}
		if (item == null) {
			item = DEFAULT_TEXTURE;
		}
		return item;
	}
	//============================================================================================

	//============================================================================================
	public Insets4f getInsets(String ident) {
		var item = this.insetsMap.get(ident);
		if (item == null && parent != null) {
			item = parent.getInsets(ident);
		}
		if (item == null) {
			item = DEFAULT_INSETS;
		}
		return item;
	}
	//============================================================================================

	//============================================================================================
	public void _setColor(String name, Color4f color) {
		this.colorMap.put(name, color);
	}
	//============================================================================================

	//============================================================================================
	public void _setColor(String name, float r, float g, float b) {
		_setColor(name, new Color4f(r, g, b, 1));
	}
	//============================================================================================
	
	//============================================================================================
	public void _setColor(String name, float r, float g, float b, float a) {
		_setColor(name, new Color4f(r, g, b, a));
	}
	//============================================================================================
	
	//============================================================================================
	public void _setText(String name, String text) {
		this.textMap.put(name, text);
	}
	//============================================================================================

	//============================================================================================
	public void _setFont(String name, String font) {
		this.fontMap.put(name, font);
	}
	//============================================================================================

	//============================================================================================
	public void _setTexture(String name, String texture) {
		this.textureMap.put(name, texture);
	}
	//============================================================================================

	//============================================================================================
	public void _setInsets(String name, Insets4f insets) {
		this.insetsMap.put(name, insets);
	}
	//============================================================================================

	//============================================================================================
	public void _setInsets(String name, float bottom, float top, float left, float right) {
		_setInsets(name, new Insets4f(bottom, top, left, right));
	}
	//============================================================================================
	
}
//************************************************************************************************
