//************************************************************************************************
package core.gui;
//************************************************************************************************

import javax.vecmath.Vector2f;

import core.event.GameEvent;
import core.platform.IGraphics;

//************************************************************************************************
public class Label extends WidgetBase {

	//============================================================================================
	public static final String STYLE_IDENT = "gui.label";
	//============================================================================================
	
	//============================================================================================
	private String text = "Label";
	//============================================================================================

	//============================================================================================
	public Label(GuiManager guiManager) {
		super(guiManager);
		this.styleIdent = STYLE_IDENT;
		this._setLayout(new LabelLayout());
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void onPaint(IGraphics graphics) {
		var style = getStyle();
		var borderInsets = style.getInsets("border");
		var paddingInsets = style.getInsets("padding");
		float x = borderInsets.left + paddingInsets.left; 
		float y = borderInsets.bottom + paddingInsets.bottom;
		var w = getOuterExtent().x; 
		var h = getOuterExtent().y;
		
		var backColor = style.getColor("background");
		graphics.setColor(backColor);
		graphics.drawPolygon(
				0f, 0f,
				w,  0f,
				w,  h,
				0,  h);
		
		var borderColor = style.getColor("border");
		graphics.setColor(borderColor);
		graphics.drawClosedPolyline(
			0f, 0f,
			w,  0f,
			w,  h,
			0,  h);

		var textColor = style.getColor("text");
		var textFont  = style.getFont("text");
		graphics.setColor(textColor);
		graphics.drawText(textFont, text, x, y);
		
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void onGameEvent(GameEvent e) {}
	//============================================================================================

	//============================================================================================
	public String getText() {
		return this.text;
	}
	//============================================================================================
	
	//============================================================================================
	public void setText(String text) {
		this.text = text;
		this._setLayoutDirty(true);
	}
	//============================================================================================

	//============================================================================================
	public void setLocation(float x, float y) {
		this._setLocation(x, y);
	}
	//============================================================================================

	//============================================================================================
	public void setLocation(Vector2f location) {
		this._setLocation(location);
	}
	//============================================================================================
	
}
//************************************************************************************************
