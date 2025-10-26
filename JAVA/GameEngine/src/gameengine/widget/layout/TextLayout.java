//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import javax.vecmath.Vector2f;
import gameengine.client.Surface;
import gameengine.widget.Text;
import gameengine.widget.Widget;

//*****************************************************************************
public class TextLayout extends Layout {

	//=========================================================================
	@Override
	public void calcMinExtent(Widget widget, Surface surface) {
		Text text  = (Text) widget;
		var padding = widget.getPadding();
		var  scale = surface.text_scale(text.getFont(), 1f, text.getText());
		widget.setMinExtent(
			new Vector2f(
				padding.left + scale.x + padding.right,
				padding.bottom + scale.y + padding.top
			)
		);
	}
	//=========================================================================
	
	//=========================================================================
	@Override
	public void layout(Widget widget, Surface surface) {}
	//=========================================================================
	
}
//*****************************************************************************
