//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import gameengine.client.Surface;
import gameengine.widget.MenuItem;
import gameengine.widget.Widget;

//*****************************************************************************
public class MenuLayout extends ListLayout {

	//=========================================================================
	public MenuLayout(Direction direction) {
		super(direction);
	}
	//=========================================================================
	
	//=========================================================================
	public void validate(Widget widget, Surface surface) {
		var children = widget.getChildren();
		for (var child : children) {
			if (!(child instanceof MenuItem)) {
				throw new RuntimeException("Only MenuItem is allowed as child");
			}
		}
	}
	//=========================================================================

}
//*****************************************************************************
