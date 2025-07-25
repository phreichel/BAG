//************************************************************************************************
package core.input.mapping;
//************************************************************************************************

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import core.input.raw.InputAxis;
import core.input.raw.InputEvent;

//************************************************************************************************
public class AxisMapping implements IInputMapping {

	//============================================================================================
	private IInputAction onValue = null;
	private IInputAction onLoss  = null;
	//============================================================================================

	//============================================================================================
	private InputAxis            axis      = InputAxis.NONE;
	private final Set<InputAxis> modifiers = EnumSet.noneOf(InputAxis.class);
	//============================================================================================

	//============================================================================================
	public AxisMapping(
		IInputAction onValue,
		IInputAction onLoss,
		InputAxis axis,
		InputAxis ... modifiers) {
		this.onValue = onValue;
		this.onLoss  = onLoss;
		this.axis    = axis;
		this.modifiers.addAll(Arrays.asList(modifiers));
	}
	//============================================================================================

	//============================================================================================
	private boolean active = false;
	//============================================================================================
	
	//============================================================================================
	@Override
	public void update(InputEvent event, InputState state) {
		if (
			event.axis.equals(axis) &&
			state.getStates().containsAll(modifiers)
		) {
			if (onValue != null) {
				onValue.perform(event, state);
			}
			active = true;
		}
		if (
			active &&
			event.axis.equals(axis) &&
			!state.getStates().containsAll(modifiers)
		) {
			if (onLoss != null) {
				onLoss.perform(event, state);
			}
			active = false;
		}
	}
	//============================================================================================
	
}
//************************************************************************************************
