//************************************************************************************************
package core.input;
//************************************************************************************************

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.api.IInputHandler;
import core.event.EventManager;
import core.event.GameEvent;

//************************************************************************************************
public class InputMapper implements IInputHandler {

	//============================================================================================
	public enum Context {
		NONE,
		GUI,
		GAME
	}
	//============================================================================================

	//============================================================================================
	private Context context = Context.NONE;
	private Map<Context, List<IInputMapping>> mappingMap = new HashMap<>(); 
	//============================================================================================
	
	//============================================================================================
	private Set<InputEvent.Axis> allButtons     = EnumSet.noneOf(InputEvent.Axis.class);
	private Set<InputEvent.Axis> pressedButtons = EnumSet.noneOf(InputEvent.Axis.class); 
	//============================================================================================

	//============================================================================================
	private EventManager eventManager = null;
	//============================================================================================
	
	//============================================================================================
	public InputMapper() {

		for (var axis : InputEvent.Axis.values()) {
			if (axis.name().startsWith("KB_")) allButtons.add(axis);
			if (axis.name().contains("_BTN"))  allButtons.add(axis);
			if (axis.name().contains("_HUD"))  allButtons.add(axis);
		}
		
	}
	//============================================================================================

	//============================================================================================
	public void init(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	//============================================================================================
	
	//============================================================================================
	public void addMapping(
			Context context,
			IInputMapping mapping) {
		var list = mappingMap.get(context);
		if (list == null) {
			list = new ArrayList<IInputMapping>();
			mappingMap.put(context, list);
		}
		if (!list.contains(mapping)) { 
			list.add(mapping);
		}
	}
	//============================================================================================

	//============================================================================================
	public void removeMapping(
			Context context,
			IInputMapping mapping) {
		var list = mappingMap.get(context);
		if (list != null) {
			list.remove(mapping);
		}
	}
	//============================================================================================
	
	//============================================================================================
	public Context getContext() {
		return context; 
	}
	//============================================================================================

	//============================================================================================
	public void setContext(Context context) {
		this.context = context; 
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void onInput(InputEvent event) {

		// set button states
		if (allButtons.contains(event.axis)) {
			if (event.value == InputEvent.VALUE_PRESSED) {
				pressedButtons.add(event.axis);
			}
			else if (event.value == InputEvent.VALUE_RELEASED) {
				pressedButtons.remove(event.axis);
			}
		}
		
		boolean isButton = allButtons.contains(event.axis);
		var contextMappings = mappingMap.get(context);
		if (contextMappings != null) {
			for (var mapping : contextMappings) {
				if (mapping.matches(pressedButtons, event)) {
					var ident = mapping.getIdent();
					var target = mapping.getTarget();
					switch (target) {
					case ACTION:
						if (event.value == InputEvent.VALUE_PRESSED) {
							fireAction(ident);
						}
						break;
					case CHANNEL:
						if (isButton) {
							if (
								(event.value == InputEvent.VALUE_PRESSED) ||
								(event.value == InputEvent.VALUE_RELEASED)
								) {
								fireChannel(ident, event.value);
							}
						} else {
							fireChannel(ident, event.value);
						}
						break;
					default:
						// do nothing;
						break;
					}
				}
			}
		}
		
		if (
			isButton &&
			(event.value == InputEvent.VALUE_TYPED) &&
			(event.character != '\0')
		) {
			fireText(event.character);
		}
		
	}
	//============================================================================================

	//============================================================================================
	private void fireAction(String actionName) {
		if (eventManager == null) return;
		var event = eventManager.createEvent();
		event.type = GameEvent.Type.ACTION;
		event.text = actionName;
		eventManager.postEvent(event);
	}
	//============================================================================================

	//============================================================================================
	private void fireChannel(String channelName, float value) {
		if (eventManager == null) return;
		var event = eventManager.createEvent();
		event.type  = GameEvent.Type.CHANNEL;
		event.text  = channelName;
		event.value = value;
		eventManager.postEvent(event);
	}
	//============================================================================================

	//============================================================================================
	private void fireText(char character) {
		if (eventManager == null) return;
		var event = eventManager.createEvent();
		event.type  = GameEvent.Type.TEXT;
		event.text  = "" + character;
		eventManager.postEvent(event);
	}
	//============================================================================================
	
}
//************************************************************************************************
