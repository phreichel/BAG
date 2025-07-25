//************************************************************************************************
package core.input.mapping;
//************************************************************************************************

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import core.input.raw.InputAxis;
import core.input.raw.InputEvent;

//************************************************************************************************
public class ActionMapping implements IInputMapping {

	//============================================================================================
	private IInputAction         onPress   = null;
	private IInputAction         onRelease = null;
	private IInputAction         onType    = null;
	//============================================================================================

	//============================================================================================
	private InputAxis            trigger   = InputAxis.NONE;
	private final Set<InputAxis> modifiers = EnumSet.noneOf(InputAxis.class);
	//============================================================================================

	//============================================================================================
	public ActionMapping(
		IInputAction onPress,
		IInputAction onRelease,
		IInputAction onType,
		InputAxis trigger,
		InputAxis ... modifiers) {
		this.onPress   = onPress;
		this.onRelease = onRelease;
		this.onType    = onType;
		this.trigger   = trigger;
		this.modifiers.addAll(Arrays.asList(modifiers));
	}
	//============================================================================================

	//============================================================================================
	private boolean pressed = false;
	//============================================================================================
	
	//============================================================================================
	@Override
	public void update(InputEvent event, InputState state) {
		if (
			!pressed &&
			event.axis.equals(trigger) &&
			event.value == InputEvent.VALUE_PRESSED &&
			state.getStates().containsAll(modifiers)
		) {
			if (onPress != null) {
				onPress.perform(event, state);
			}
			pressed = true;
		}
		if (
			pressed &&
			onType != null &&			
			event.axis.equals(trigger) &&
			event.value == InputEvent.VALUE_TYPED &&
			state.getStates().containsAll(modifiers)
		) {
			onType.perform(event, state);
		}
		if (
			pressed &&
			(event.axis.equals(trigger) || this.modifiers.contains(event.axis)) &&
			event.value == InputEvent.VALUE_RELEASED
		) {
			if (onRelease != null) {
				onRelease.perform(event, state);
			}
			pressed = false;
		}
	}
	//============================================================================================
	
}
//************************************************************************************************
