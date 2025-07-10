//************************************************************************************************
package core;
//************************************************************************************************

import java.util.EnumSet;
import java.util.Set;

//************************************************************************************************
public class InputMapper implements IInputHandler {

	//============================================================================================
	public enum Context {
		NONE,
		GUI,
		TEXT
	}
	//============================================================================================

	//============================================================================================
	private Context context = Context.NONE;
	//============================================================================================
	
	//============================================================================================
	private Set<InputEvent.Axis> allButtons     = EnumSet.noneOf(InputEvent.Axis.class); 
	private Set<InputEvent.Axis> allAnalogs     = EnumSet.noneOf(InputEvent.Axis.class); 
	private Set<InputEvent.Axis> pressedButtons = EnumSet.noneOf(InputEvent.Axis.class); 
	//============================================================================================

	//============================================================================================
	public InputMapper() {

		for (var axis : InputEvent.Axis.values()) {
			if (axis.name().startsWith("KB_")) allButtons.add(axis);
			if (axis.name().contains("_BTN"))  allButtons.add(axis);
			if (axis.name().contains("_HUD"))  allButtons.add(axis);
		}
		
		allAnalogs.add(InputEvent.Axis.PT_X);
		allAnalogs.add(InputEvent.Axis.PT_Y);
		allAnalogs.add(InputEvent.Axis.PT_Z);
		for (var axis : InputEvent.Axis.values()) {
			if (axis.name().contains("_AS")) allAnalogs.add(axis);
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
		
		// set analog axis history
		if (allAnalogs.contains(event.axis)) {
			// do some history gathering ..
		}
		
	}
	//============================================================================================

}
//************************************************************************************************
