//*****************************************************************************
package gameengine.widget;
//*****************************************************************************

import javax.vecmath.Vector2f;
import gameengine.client.Surface;
import gameengine.client.SurfaceHandler;
import gameengine.event.Event;
import gameengine.event.EventPump;
import gameengine.event.EventSink;

//*****************************************************************************
public class WidgetManager implements SurfaceHandler, EventSink {

	//=========================================================================
	private RootPane root;
	//=========================================================================

	//=========================================================================
	public WidgetManager(EventPump eventPump) {
		root = new RootPane(eventPump);
	}
	//=========================================================================
	
	//=========================================================================
	public RootPane getRoot() {
		return root;
	}
	//=========================================================================
	
	//=========================================================================
	public void init() {}
	//=========================================================================

	//=========================================================================
	public void update() {}
	//=========================================================================

	//=========================================================================
	public void done() {}
	//=========================================================================

	//=========================================================================
	@Override
	public void handleSurface(Surface surface) {
		if (surface.getContext() == Surface.CONTEXT_UPDATE) {
			Vector2f surfaceSize = new Vector2f(surface.getWidth(), surface.getHeight());
			root.setExtent(surfaceSize);
			root.handleSurface(surface);
		}
	}
	//=========================================================================
	
	//=========================================================================
	@Override
	public void handleEvent(Event event) {
		var context = new EventContext();
		context.event = event;
		context.localPointer = new Vector2f(event.getPointerLocation());
		root.handleEvent(context);
	}
	//=========================================================================

}
//*****************************************************************************
