//************************************************************************************************
package core.gui;
//************************************************************************************************

import core.platform.IGraphics;

//************************************************************************************************
public class LabelLayout implements ILayout {

	//============================================================================================
	@Override
	public void preserveState(IWidget widget) {}
	//============================================================================================

	//============================================================================================
	@Override
	public void updateLayout(IWidget widget, IGraphics graphics) {
		var style   = widget.getStyle();
		var font    = style.getFont("text");
		var borderInsets = style.getInsets("border");
		var paddingInsets = style.getInsets("padding");
		var _widget = (Label) widget;
		var text    = _widget.getText();
		var probe   = graphics.probeText(font, text, null);
		widget._setOuterExtent(
			probe.width + borderInsets.horz() + paddingInsets.horz(),
			probe.height + borderInsets.vert() + paddingInsets.vert());
	}
	//============================================================================================

}
//************************************************************************************************
