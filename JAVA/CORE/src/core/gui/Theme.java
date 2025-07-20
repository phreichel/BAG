//************************************************************************************************
package core.gui;
//************************************************************************************************

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//************************************************************************************************
public class Theme {

	//============================================================================================
	public static final Theme DEFAULT_THEME = createDefaultTheme();
	//============================================================================================

	//============================================================================================
	private static final Theme createDefaultTheme() {
		
		Style defaultStyle = new Style("default");
		defaultStyle._setColor("foreground", 0, 0, 0);
		defaultStyle._setColor("background", .8f, .8f, .8f);
		defaultStyle._setColor("border", 0, 0, 0);
		defaultStyle._setColor("text", 0, 0, 0);
		defaultStyle._setFont("text", "system");
		defaultStyle._setInsets("margin", 2, 2, 2, 2);
		defaultStyle._setInsets("padding", 2, 2, 2, 2);
		defaultStyle._setInsets("border", 1, 1, 1, 1);
		
		Style labelStyle = defaultStyle.derive(Label.STYLE_IDENT);
		
		Theme theme = new Theme("default");
		theme._setStyle(defaultStyle);
		theme._setStyle(labelStyle);
		return theme;
	}
	//============================================================================================
	
	//============================================================================================
	private final String ident;
	private final Theme  parent;
	//============================================================================================

	//============================================================================================
	private final Map<String, Style> styleMap = new HashMap<>();
	private final Map<String, Style> styleMapReadonly = Collections.unmodifiableMap(styleMap);
	//============================================================================================
	
	//============================================================================================
	public Theme(String ident) {
		this(ident, null);
	}
	//============================================================================================

	//============================================================================================
	public Theme(String ident, Theme parent) {
		this.ident = ident;
		this.parent = parent;
		if (this.parent != null) {
			for (var styleIdent : this.parent.getStyles().keySet()) {
				var parentStyle = this.parent.getStyle(styleIdent);
				var localStyle  = parentStyle.derive(styleIdent);
				this._setStyle(localStyle);
			}
		}
	}
	//============================================================================================

	//============================================================================================
	public Theme derive(String ident) {
		return new Theme(ident, this);
	}
	//============================================================================================
	
	//============================================================================================
	public String getIdent() {
		return this.ident;
	}
	//============================================================================================

	//============================================================================================
	public Theme getParent() {
		return this.parent;
	}
	//============================================================================================

	//============================================================================================
	public Map<String, Style> getStyles() {
		return this.styleMapReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Style getStyle(String ident) {
		var item = this.styleMap.get(ident);
		if (item == null && parent != null) {
			item = parent.getStyle(ident);
		}
		if (item == null) {
			return Style.DEFAULT_STYLE;
		}
		return item;
	}
	//============================================================================================
	
	//============================================================================================
	public void _setStyle(Style style) {
		this.styleMap.put(style.getIdent(), style);
	}
	//============================================================================================
	
}
//************************************************************************************************
