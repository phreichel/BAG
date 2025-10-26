//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import javax.vecmath.Vector2f;
import gameengine.client.Surface;
import gameengine.widget.Widget;

//*****************************************************************************
public class Layout {

	//=========================================================================
	private boolean dirty = true;
	//=========================================================================

	//=========================================================================
	public boolean isDirty() {
		return dirty;
	}
	//=========================================================================

	//=========================================================================
	public void setDirty() {
		this.dirty = true;
	}
	//=========================================================================
	
	//=========================================================================
	public void apply(Widget widget, Surface surface) {
		if (isDirty()) {
			validate(widget, surface);
			calcMinExtent(widget, surface);
			for (var child : widget.getChildren()) {
				child.getLayout().apply(child, surface);
			}
			layout(widget, surface);
		}
		this.dirty = false;
	}
	//=========================================================================

	//=========================================================================
	public void calcMinExtent(Widget widget, Surface surface) {
		if (!isDirty()) return;
		var minExtent = new Vector2f(widget.getMinExtent());
		for (var child : widget.getChildren()) {
			child.getLayout().calcMinExtent(child, surface);
			var childMinExtent = child.getMinExtent();
			minExtent.x = Math.max(minExtent.x, childMinExtent.x);
			minExtent.y = Math.max(minExtent.y, childMinExtent.y);
		}
		widget.setMinExtent(minExtent);
	}
	//=========================================================================

	//=========================================================================
	public void validate(Widget widget, Surface surface) {}
	public void layout(Widget widget, Surface surface) {}
	//=========================================================================
	
}
//*****************************************************************************
