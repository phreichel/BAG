//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import javax.vecmath.Vector2f;
import gameengine.client.Surface;
import gameengine.widget.Menu;
import gameengine.widget.Widget;

//*****************************************************************************
public class MenuItemLayout extends TextLayout {

	//=========================================================================
	public void validate(Widget widget, Surface surface) {
		var children = widget.getChildren();
		if (children.size() == 1) {
			var child = children.get(0);
			if (!(child instanceof Menu)) {
				throw new RuntimeException("Only Menu is allowed as child");
			}
		} else if (children.size() > 1) {
			throw new RuntimeException("Too many Children");
		}
	}
	//=========================================================================

	//=========================================================================
	public void layout(Widget widget, Surface surface) {
		var children = widget.getChildren();
		if (children.size() == 1) {
			var child = children.get(0);
			var loc = new Vector2f(widget.getExtent());
			var parent = (Menu) widget.getParent();			
			if (parent == null || ((MenuLayout)parent.getLayout()).getDirection().equals(ListLayout.Direction.VERTICAL)) {
				loc.y -= child.getExtent().y;
			} else {
				loc.y = -child.getExtent().y;
				loc.x = 0f;
			}
			child.setLocation(loc);
		}
	}
	//=========================================================================
	
}
//*****************************************************************************
