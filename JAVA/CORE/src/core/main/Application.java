//************************************************************************************************
package core.main;
//************************************************************************************************

import core.api.IApplication;
import core.asset.Asset;
import core.event.EventManager;
import core.event.GameEvent;
import core.input.InputMapper;
import core.platform.IGraphics;
import core.platform.IPlatform;
import core.platform.Platform;

//************************************************************************************************
public class Application implements IApplication {

	//============================================================================================
	private InputMapper  inputHandler = new InputMapper();
	private IPlatform    platform     = new Platform();
	private EventManager eventManager = new EventManager();
	//============================================================================================
	
	//============================================================================================
	@Override
	public void run () {
		
		inputHandler.init(eventManager);
		
		platform.init();
		platform.setTitle("PETERCHENS MONDFAHRT");

		Asset fontSystem = new Asset("system", "Font", "code", "Arial-PLAIN-20");
		Asset fontPlain = new Asset("plain", "Font", "code", "Arial-PLAIN-20");
		Asset fontBold  = new Asset("bold", "Font", "code", "Arial-BOLD-20");
		
		platform.addAsset(fontSystem);
		platform.addAsset(fontBold);
		platform.addAsset(fontPlain);
		
		platform.addInputHandler(inputHandler);
		platform.addCanvas(this::onPaint);
		
		eventManager.registerEventTypeClass(GameEvent.Type.class);
		eventManager.register(GameEvent.Type.ACTION,  this::handleAction);
		eventManager.register(GameEvent.Type.CHANNEL, this::handleChannel);
		eventManager.register(GameEvent.Type.TEXT,    this::handleText);
		
		while (true)  {
			platform.updateInputs();
			eventManager.update();
			platform.updateGraphics();
			Thread.yield();
		}
		
	}
	//============================================================================================

	//============================================================================================
	private void onPaint(IGraphics g) {
		g.setColor(1, 0, 0);
	}
	//============================================================================================

	//============================================================================================
	private void handleAction(GameEvent event) {
		
	}
	//============================================================================================

	//============================================================================================
	private void handleChannel(GameEvent event) {
		
	}
	//============================================================================================

	//============================================================================================
	private void handleText(GameEvent event) {
		
	}
	//============================================================================================
	
}
//************************************************************************************************
