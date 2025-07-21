//************************************************************************************************
package core.input;
//************************************************************************************************

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

//************************************************************************************************
public class InputState {

	//============================================================================================
	private final Set<InputAxis>        axisStateSet = EnumSet.noneOf(InputAxis.class); 
	private final Map<InputAxis, Float> axisValueMap = new EnumMap<>(InputAxis.class); 
	private final Set<InputAxis>        axisStateSetReadonly = Collections.unmodifiableSet(axisStateSet); 
	private final Map<InputAxis, Float> axisValueMapReadonly = Collections.unmodifiableMap(axisValueMap); 
	//============================================================================================

	//============================================================================================
	public void clear() {
		axisStateSet.clear();
		axisValueMap.clear();
	}
	//============================================================================================

	//============================================================================================
	public boolean isPressed(InputAxis axis) {
		return axisStateSet.contains(axis);
	}
	//============================================================================================

	//============================================================================================
	public float getValue(InputAxis axis) {
		var value = axisValueMap.get(axis);
		if (value == null) value = 0f;
		return value;
	}
	//============================================================================================

	//============================================================================================
	public Set<InputAxis> getStates() {
		return axisStateSetReadonly;
	}
	//============================================================================================

	//============================================================================================
	public Map<InputAxis,Float> getValues() {
		return axisValueMapReadonly;
	}
	//============================================================================================
	
	//============================================================================================
	public void update(InputEvent e) {
		axisValueMap.put(e.axis, e.value);
		if (e.value == InputEvent.VALUE_PRESSED) {
			axisStateSet.add(e.axis);
		} else if (e.value != InputEvent.VALUE_TYPED) {
			axisStateSet.remove(e.axis);
		}
	}
	//============================================================================================
	
}
//************************************************************************************************
