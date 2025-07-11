//************************************************************************************************
package core.input;
//************************************************************************************************

import java.util.Set;

//************************************************************************************************
public interface IInputMapping {

	//============================================================================================
	public enum Target {
		NONE,
		ACTION,
		CHANNEL
	}
	//============================================================================================
	
	//============================================================================================
	public Target  getTarget();
	public String  getIdent();
	public boolean matches(Set<InputEvent.Axis> axisStates, InputEvent event);
	//============================================================================================

	//============================================================================================
	// Actual matching to Channel or Action is done in InputMapper
	// Action : if matches and value is 1f (PRESSED) : -> Action is triggered 
	// Channel: if matches -> if Analog Axis: value is directly passed in
	//                     -> if Button Axit: value PRESSED is passed as 1f (HIGH)
	//                                        value RELEASED is passed as 0f (LOW)
	//                                        other values (TYPED etc) are ignored
	//============================================================================================
	
}
//************************************************************************************************
