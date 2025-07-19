//************************************************************************************************
package core.main;
//************************************************************************************************

import java.util.ArrayList;
import java.util.List;

import core.api.IApplication;
import core.asset.Asset;
import core.clock.Clock;
import core.event.EventManager;
import core.event.GameEvent;
import core.gui.GuiManager;
import core.input.ChordMapping;
import core.input.EventType;
import core.input.InputMapper;
import core.input.IInputMapping.Target;
import core.input.InputEvent;
import core.platform.IGraphics;
import core.platform.IPlatform;
import core.platform.Platform;
import core.platform.TextProbe;

//************************************************************************************************
public class Application implements IApplication {

	//============================================================================================
	private static final long ONE_SECOND_NS   = 1_000_000_000L;
	private static final long EVENT_PERIOD    = ONE_SECOND_NS / 100L;
	private static final long GRAPHICS_PERIOD = ONE_SECOND_NS / 20L;
	private static final long GUI_PERIOD      = ONE_SECOND_NS / 20L;
	//============================================================================================
	
	//============================================================================================
	private Clock        clock        = new Clock();
	private GuiManager   guiManager   = new GuiManager();
	private InputMapper  inputHandler = new InputMapper();
	private EventManager eventManager = new EventManager();
	private IPlatform    platform     = new Platform(eventManager);
	//============================================================================================

	//============================================================================================
	private boolean  terminated   = false;
	//============================================================================================

	//============================================================================================
	private static class Stroke {

		long   t;
		String s;
		
		public Stroke(String s) {
			this(System.currentTimeMillis(), s);
		}
		
		public Stroke(long t, String s) {
			this.t = t;
			this.s = s;
		}
		
		public float fade(long now, long millis) {
			return (float) (now-t) / (float) millis;
		}

		public boolean expired(long now, long millis) {
			return (now - t) > millis;
		}
		
	}
	//============================================================================================

	//============================================================================================
	private List<Stroke> strokes = new ArrayList<>();
	//============================================================================================
	
	//============================================================================================
	@Override
	public void run () {
		
		inputHandler.init(eventManager);
		inputHandler.addMapping(InputMapper.Context.NONE, new ChordMapping("TERMINATE", Target.ACTION, InputEvent.Axis.KB_ESCAPE));
		
		platform.init();
		platform.setTitle("PETERCHENS MONDFAHRT");

		Asset fontSystem = new Asset("system", "Font", "code", "Arial-PLAIN-20");
		Asset fontPlain = new Asset("plain", "Font", "code", "Bauhaus 93-PLAIN-20");
		Asset fontBold  = new Asset("bold", "Font", "code", "Arial-BOLD-20");
		
		platform.addAsset(fontSystem);
		platform.addAsset(fontBold);
		platform.addAsset(fontPlain);
		
		platform.addInputHandler(inputHandler);
		platform.addCanvas(this::onPaint);
		platform.addCanvas(guiManager);
		
		var root = guiManager.getRoot();

		var layer = guiManager.createLayer();
		root.addLayer(layer);

		var label = guiManager.createLabel("Hello World!");
		label.setFont("plain");
		label.setLocation(500, 500);
		layer.addComponent(label);
		
		eventManager.registerEventTypeClass(EventType.class);
		eventManager.register(EventType.TEXT, this::handleText);
		eventManager.register(EventType.ACTION, this::handleAction);
		eventManager.register(EventType.TERMINATE, this::handleTerminate);
		eventManager.register(EventType.RESIZE, guiManager);
		
		clock.add(EVENT_PERIOD, this::updateEvents);
		clock.add(GUI_PERIOD, guiManager);
		clock.add(GRAPHICS_PERIOD, this::updateOutput);
		clock.start();
		
		while (!terminated)  {
			clock.update();
			Thread.yield();
		}
		
		platform.done();
		
	}
	//============================================================================================

	//============================================================================================
	private void updateEvents(int nFrames, long periodNs) {
		platform.updateInputs();
		eventManager.update();
	}
	//============================================================================================

	//============================================================================================
	private void updateOutput(int nFrames, long periodNs) {
		platform.updateGraphics();
	}
	//============================================================================================
	
	//============================================================================================
	private void onPaint(IGraphics g) {
		
		long now = System.currentTimeMillis();

		float x = 10f;
		float y = g.getHeight() - 50;
		
		var probe = new TextProbe();
		g.startTextRaw("plain");
		
		for (int i=0; i<strokes.size(); i++) {
			
			var stroke = strokes.get(i);
			
			if (stroke.expired(now, 50000)) {
				strokes.remove(i);
				i--;
			} else if (stroke.s.equals("\r")) {
				x = 10f;
				y -= 30f;
			} else if (stroke.s.equals("\b")) {
				strokes.remove(i);
				if (i > 0) {
					strokes.remove(i-1);
					i--;
				}
			} else if (stroke.s.equals("\t")) {
				int n = ((int) x) / 100;
				x = (n+1) * 100;
			} else {
				
				float a = 1f - Math.min(1f, stroke.fade(now, 50000));
				g.setColor(1f, .5f, 0f, a);
				g.drawTextRaw(stroke.s, x, y);
				
				probe = g.probeText("bold", stroke.s, probe);
				x += probe.advance;				
				if (x >= 1000f) {
					x = 10f;
					y -= 30f;
				}
				
			}
		}

		g.endTextRaw();
		
	}
	//============================================================================================

	//============================================================================================
	private void handleAction(GameEvent event) {
		if (event.text.equals("TERMINATE")) {
			var te = eventManager.createEvent();
			te.type = EventType.TERMINATE;
			eventManager.postEvent(te);
		}
	}
	//============================================================================================
	
	//============================================================================================
	private void handleText(GameEvent event) {
		strokes.add(new Stroke(event.text));
	}
	//============================================================================================

	//============================================================================================
	private void handleTerminate(GameEvent event) {
		terminated = true;
	}
	//============================================================================================

}
//************************************************************************************************
