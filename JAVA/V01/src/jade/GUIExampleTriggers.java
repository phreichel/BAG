//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.action.ActionMap;
import jade.input.Flank;
import jade.input.Key;
import jade.input.TriggerList;

//**************************************************************************************************
public class GUIExampleTriggers extends TriggerList {

	//==============================================================================================
	public GUIExampleTriggers(ActionMap actions) {
		register(actions);
	}
	//==============================================================================================

	//==============================================================================================
	private void register(ActionMap actions) {
		//add(Key.ESCAPE, Flank.PRESSED, null, null, actions.get("QUIT"));
		add(Key.ESCAPE, Flank.PRESSED, null, null, actions.get("TOGGLE_GUI"));
		add(Key.F12, Flank.PRESSED, null, null, actions.get("TOGGLE_FULLSCREEN"));
	}
	//==============================================================================================
	
}
//**************************************************************************************************
