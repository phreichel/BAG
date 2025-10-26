//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.action.ActionMap;
import jade.input.Flank;
import jade.input.Key;
import jade.input.TriggerList;

//**************************************************************************************************
public class ExampleTriggers extends TriggerList {

	//==============================================================================================
	public ExampleTriggers(ActionMap actions) {
		register(actions);
	}
	//==============================================================================================

	//==============================================================================================
	protected void register(ActionMap actions) {
		add(Key.ESCAPE, Flank.PRESSED, null, null, actions.get("QUIT"));
		//add(Key.ESCAPE, Flank.PRESSED, null, null, actions.get("TOGGLE_GUI"));
		add(Key.F12, Flank.PRESSED, null, null, actions.get("TOGGLE_FULLSCREEN"));
		add(Key.A, Flank.PRESSED, null, null, actions.get("START_STRAFE_LEFT"));
		add(Key.A, Flank.RELEASED, null, null, actions.get("STOP_STRAFE_LEFT"));
		add(Key.D, Flank.PRESSED, null, null, actions.get("START_STRAFE_RIGHT"));
		add(Key.D, Flank.RELEASED, null, null, actions.get("STOP_STRAFE_RIGHT"));
		add(Key.W, Flank.PRESSED, null, null, actions.get("START_STRAFE_FORWARD"));
		add(Key.W, Flank.RELEASED, null, null, actions.get("STOP_STRAFE_FORWARD"));
		add(Key.S, Flank.PRESSED, null, null, actions.get("START_STRAFE_BACKWARD"));
		add(Key.S, Flank.RELEASED, null, null, actions.get("STOP_STRAFE_BACKWARD"));
		add(Key.SHIFT, Flank.PRESSED, null, null, actions.get("START_STRAFE_UP"));
		add(Key.SHIFT, Flank.RELEASED, null, null, actions.get("STOP_STRAFE_UP"));
		add(Key.CONTROL, Flank.PRESSED, null, null, actions.get("START_STRAFE_DOWN"));
		add(Key.CONTROL, Flank.RELEASED, null, null, actions.get("STOP_STRAFE_DOWN"));
		add(Key.UP, Flank.PRESSED, null, null, actions.get("START_PITCH_DOWN"));
		add(Key.UP, Flank.RELEASED, null, null, actions.get("STOP_PITCH_DOWN"));
		add(Key.DOWN, Flank.PRESSED, null, null, actions.get("START_PITCH_UP"));
		add(Key.DOWN, Flank.RELEASED, null, null, actions.get("STOP_PITCH_UP"));
		add(Key.LEFT, Flank.PRESSED, null, null, actions.get("START_YAW_LEFT"));
		add(Key.LEFT, Flank.RELEASED, null, null, actions.get("STOP_YAW_LEFT"));
		add(Key.RIGHT, Flank.PRESSED, null, null, actions.get("START_YAW_RIGHT"));
		add(Key.RIGHT, Flank.RELEASED, null, null, actions.get("STOP_YAW_RIGHT"));
		add(Key.Q, Flank.PRESSED, null, null, actions.get("START_ROLL_LEFT"));
		add(Key.Q, Flank.RELEASED, null, null, actions.get("STOP_ROLL_LEFT"));
		add(Key.E, Flank.PRESSED, null, null, actions.get("START_ROLL_RIGHT"));
		add(Key.E, Flank.RELEASED, null, null, actions.get("STOP_ROLL_RIGHT"));
	}
	//==============================================================================================
	
}
//**************************************************************************************************
