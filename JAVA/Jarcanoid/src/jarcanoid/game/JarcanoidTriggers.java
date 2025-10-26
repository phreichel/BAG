//**************************************************************************************************
package jarcanoid.game;

import jade.ExampleTriggers;
import jade.action.ActionMap;
import jade.input.Flank;
import jade.input.Key;

//**************************************************************************************************

//**************************************************************************************************
public class JarcanoidTriggers extends ExampleTriggers {

	//==============================================================================================
	public JarcanoidTriggers(ActionMap actions) {
		super(actions);
	}
	//==============================================================================================

	//==============================================================================================
	protected void register(ActionMap actions) {
		super.register(actions);
		add(Key.DELETE, Flank.PRESSED, null, null, actions.get("SLIDER_LEFT_START"));
		add(Key.DELETE, Flank.RELEASED, null, null, actions.get("SLIDER_LEFT_STOP"));
		add(Key.PAGE_DOWN, Flank.PRESSED, null, null, actions.get("SLIDER_RIGHT_START"));
		add(Key.PAGE_DOWN, Flank.RELEASED, null, null, actions.get("SLIDER_RIGHT_STOP"));
	}
	//==============================================================================================

}
//**************************************************************************************************
