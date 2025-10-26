//*****************************************************************************
package gameengine.client;
//*****************************************************************************

import gameengine.app.Application;
import gameengine.event.Event;
import gameengine.event.EventPump;
import gameengine.event.Ident;
import gameengine.widget.WidgetManager;

//*****************************************************************************
public class Client extends Application {

	//=========================================================================
	private EventPump eventPump;
	private Platform platform;
	private WidgetManager widgetManager;
	//=========================================================================

	//=========================================================================
	protected void init() {
		eventPump = new EventPump();
		eventPump.register(this::onSurfaceClose);
		eventPump.register(this::onQuitAction);
		eventPump.register(this::onTerminate);
		widgetManager = new WidgetManager(eventPump);		
		widgetManager.init();
		eventPump.register(widgetManager);
		platform = new Platform(eventPump);
		platform.addSurfaceHandler(widgetManager);
		platform.init();
	}
	//=========================================================================

	//=========================================================================
	protected void update() {
		eventPump.update();
		widgetManager.update();
		platform.update();
	}
	//=========================================================================

	//=========================================================================
	protected void done() {
		widgetManager.done();
		platform.done();
	}
	//=========================================================================

	//=========================================================================
	private void onSurfaceClose(Event event) {
		if (event.getIdent().equals(Ident.ON_PLATFORM_CLOSE)) {
			var newEvent = eventPump.allocate();
			newEvent.setCommand(Ident.ON_CLIENT_TERMINATE);
			eventPump.submit(newEvent);
		}
	}
	//=========================================================================

	//=========================================================================
	private void onQuitAction(Event event) {
		if (event.getIdent().equals(Ident.ON_ACTION) && event.getAction().equals("quit")) {
			var newEvent = eventPump.allocate();
			newEvent.setCommand(Ident.ON_CLIENT_TERMINATE);
			eventPump.submit(newEvent);
		}
	}
	//=========================================================================
	
	//=========================================================================
	private void onTerminate(Event event) {
		if (event.getIdent().equals(Ident.ON_CLIENT_TERMINATE)) {
			terminate();
		}
	}
	//=========================================================================
	
	//=========================================================================
	public static void main(String[] args) {
		var application = new Client();
		application.setup(args);
		application.run();
	}
	//=========================================================================
	
}
//*****************************************************************************
