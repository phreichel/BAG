//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import javax.vecmath.Vector2f;
import gameengine.client.Surface;
import gameengine.widget.Widget;

//*****************************************************************************
public class RootLayout extends Layout {

	//=========================================================================
	public void calcMinExtent(Widget widget, Surface surface) {
		if (!isDirty()) return;
		var minExtent = new Vector2f(surface.getWidth(), surface.getHeight());
		widget.setMinExtent(minExtent);
	}
	//=========================================================================

	//=========================================================================
	public void validate(Widget widget, Surface surface) {}
	//=========================================================================

	//=========================================================================
	public void layout(Widget widget, Surface surface) {
		var loc = widget.getLocation();
		var ext = widget.getExtent(); 
		for (var child : widget.getChildren()) {
			child.setLocation(loc);
			child.setExtent(ext);
		}
	}
	//=========================================================================
	
}
//*****************************************************************************
