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
import core.gui.InputEventType;
import core.input.mapping.InputMapper;
import core.input.raw.InputAxis;
import core.input.virtual.DeviceData;
import core.input.virtual.VirtualDevice;
import core.input.virtual.VirtualEventType;
import core.input.virtual.VirtualInput;
import core.input.virtual.VirtualInputAction;
import core.input.virtual.VirtualInputAxis;
import core.input.virtual.VirtualInputText;
import core.input.virtual.VirtualOutputAction;
import core.input.virtual.VirtualOutputText;
import core.platform.IGraphics;
import core.platform.IPlatform;
import core.platform.Platform;
import core.platform.PlatformEventType;
import core.platform.TextProbe;

//************************************************************************************************
public class Application implements IApplication {

	//============================================================================================
	private static final long ONE_SECOND_NS   = 1_000_000_000L;
	private static final long EVENT_PERIOD    = ONE_SECOND_NS / 100L;
	private static final long VIRTUAL_PERIOD  = ONE_SECOND_NS / 100L;
	private static final long GRAPHICS_PERIOD = ONE_SECOND_NS / 20L;
	private static final long GUI_PERIOD      = ONE_SECOND_NS / 20L;
	//============================================================================================
	
	//============================================================================================
	private Clock        clock        = new Clock();
	private GuiManager   guiManager   = new GuiManager();
	private InputMapper  inputMapper  = new InputMapper();
	private VirtualInput virtualInput = new VirtualInput();
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
		
		platform.init();
		platform.setTitle("PETERCHENS MONDFAHRT");

		Asset fontSystem = new Asset("system", "Font", "code", "Arial-PLAIN-20");
		Asset fontPlain = new Asset("plain", "Font", "code", "Bauhaus 93-PLAIN-20");
		Asset fontBold  = new Asset("bold", "Font", "code", "Arial-BOLD-20");
		
		platform.addAsset(fontSystem);
		platform.addAsset(fontBold);
		platform.addAsset(fontPlain);
		
		platform.addInputHandler(inputMapper::onInput);
		platform.addCanvas(this::onPaint);
		platform.addCanvas(guiManager);
		
		var root = guiManager.getRoot();

		var layer = guiManager.createLayer();
		root.addLayer(layer);

		var label = guiManager.createLabel("Hello World!");
		label.setLocation(500, 500);
		layer.addComponent(label);
		
		eventManager.registerEventTypeClass(PlatformEventType.class);
		eventManager.registerEventTypeClass(InputEventType.class);
		eventManager.registerEventTypeClass(VirtualEventType.class);
		
		var textInputMapping  = new VirtualInputText("text-input");
		var textOutputMapping = new VirtualOutputText("text-output", textInputMapping, eventManager);
		inputMapper.addMapping(textInputMapping);
		virtualInput.addText(textInputMapping);
		virtualInput.addText(textOutputMapping);
		
		var termActionInputMapping  = new VirtualInputAction("terminate-input", "TERMINATE", InputAxis.KB_ESCAPE);		
		var termActionOutputMapping = new VirtualOutputAction("terminate-outout", termActionInputMapping, eventManager);
		inputMapper.addMapping(termActionInputMapping);
		virtualInput.addAction(termActionInputMapping);
		virtualInput.addAction(termActionOutputMapping);
		
		var pointerXAxis = new VirtualInputAxis("pointer-x", InputAxis.PT_X);
		var pointerYAxis = new VirtualInputAxis("pointer-y", InputAxis.PT_Y);
		inputMapper.addMapping(pointerXAxis);
		inputMapper.addMapping(pointerYAxis);
		var pointerDevice = new VirtualDevice("pointer", eventManager);
		pointerDevice.add("x", pointerXAxis);
		pointerDevice.add("y", pointerYAxis);
		virtualInput.addAxis(pointerXAxis);
		virtualInput.addAxis(pointerYAxis);
		virtualInput.addDevice(pointerDevice);
		
		eventManager.register(VirtualEventType.TEXT, this::handleText);
		eventManager.register(VirtualEventType.ACTION, this::handleAction);
		eventManager.register(VirtualEventType.DEVICE, this::handleDevice);
		eventManager.register(PlatformEventType.TERMINATE, this::handleTerminate);
		eventManager.register(PlatformEventType.RESIZE, guiManager);
		
		clock.add(VIRTUAL_PERIOD, virtualInput::update);
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
			te.type = PlatformEventType.TERMINATE;
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
	private void handleDevice(GameEvent event) {
		String devName = event.text;
		var devX = event.data(DeviceData.class).getAxes().get("x");
		var devY = event.data(DeviceData.class).getAxes().get("y");
		String devLog = String.format("%s: (%s, %s)", devName, devX, devY);
		System.out.println(devLog);
	}
	//============================================================================================
	
	//============================================================================================
	private void handleTerminate(GameEvent event) {
		terminated = true;
	}
	//============================================================================================

}
//************************************************************************************************
