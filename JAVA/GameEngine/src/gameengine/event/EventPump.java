//*****************************************************************************
package gameengine.event;
//*****************************************************************************

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.vecmath.Vector2f;

//*****************************************************************************
public class EventPump {

	//=========================================================================
	private Queue<Event> cache  = new LinkedList<>();
	private Queue<Event> input  = new LinkedList<>();
	private Queue<Event> output = new LinkedList<>();
	//=========================================================================

	//=========================================================================
	private Set<Key>    keysPressed     = EnumSet.noneOf(Key.class);
	private Set<Button> buttonsPressed  = EnumSet.noneOf(Button.class);
	private Vector2f    pointerLocation = new Vector2f();
	//=========================================================================
	
	//=========================================================================
	private List<EventSink> eventSinks = new ArrayList<>();
	//=========================================================================

	//=========================================================================
	public void register(EventSink sink) {
		eventSinks.add(sink);
	}
	//=========================================================================

	//=========================================================================
	public void unregister(EventSink sink) {
		eventSinks.remove(sink);
	}
	//=========================================================================

	//=========================================================================
	public Vector2f getPointerLocation() {
		return pointerLocation;
	}
	//=========================================================================

	//=========================================================================
	public Set<Key> getKeysPressed() {
		return keysPressed;
	}
	//=========================================================================

	//=========================================================================
	public Set<Button> getButtonsPressed() {
		return buttonsPressed;
	}
	//=========================================================================
	
	//=========================================================================
	public Event allocate() {
		var event = cache.poll();
		if (event == null) {
			event = new Event();
		}
		event.clear();
		return event;
	}
	//=========================================================================

	//=========================================================================
	public void submit(Event e) {
		if (e == null) return;
		e.fillDefaults(this);
		synchronized (this) {
			input.offer(e);
		}
	}
	//=========================================================================

	//=========================================================================
	public void update() {
		synchronized (this) {
			var tmp = input;
			input = output;
			output = tmp;
		}
		while (!output.isEmpty()) {
			var event = output.poll();
			for (var sink : eventSinks) {
				sink.handleEvent(event);
			}
			cache.offer(event);
		}
	}
	//=========================================================================

	//=========================================================================
	public void setKeyPressed(Key key) {
		this.keysPressed.add(key);
	}
	//=========================================================================

	//=========================================================================
	public void setKeyReleased(Key key) {
		this.keysPressed.remove(key);
	}
	//=========================================================================
	
	//=========================================================================
	public void setButtonPressed(Button button) {
		this.buttonsPressed.add(button);
	}
	//=========================================================================

	//=========================================================================
	public void setButtonReleased(Button button) {
		this.buttonsPressed.remove(button);
	}
	//=========================================================================

	//=========================================================================
	public void setPointerLocation(float x, float y) {
		this.pointerLocation.set(x, y);
	}
	//=========================================================================
	
}
//*****************************************************************************
