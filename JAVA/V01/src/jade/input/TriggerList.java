//**************************************************************************************************
package jade.input;
//**************************************************************************************************

import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import jade.action.Action;

//**************************************************************************************************
public class TriggerList {

	//==============================================================================================
	private List<Trigger> triggers = new LinkedList<>();
	//==============================================================================================

	//==============================================================================================
	public List<Trigger> get() {
		return triggers;
	}
	//==============================================================================================
	
	//==============================================================================================
	public TriggerList add(Trigger trigger) {
		triggers.add(trigger);
		return this;
	}
	//==============================================================================================

	//==============================================================================================
	public TriggerList add(
			Key triggerKey,
			Flank triggerFlank,
			Collection<Key> modifierKeys,
			Collection<Button> modifierButtons,
			Action action) {
		if (triggerFlank == null) triggerFlank = Flank.PRESSED;
		if (modifierKeys == null) modifierKeys = EnumSet.noneOf(Key.class);
		if (modifierButtons == null) modifierButtons = EnumSet.noneOf(Button.class);
		triggers.add(new Trigger(triggerKey, triggerFlank, modifierKeys, modifierButtons, action));
		return this;
	}
	//==============================================================================================
	
	//==============================================================================================
	public TriggerList add(
			Button triggerButton,
			Flank triggerFlank,
			Collection<Key> modifierKeys,
			Collection<Button> modifierButtons,
			Action action) {
		if (triggerFlank == null) triggerFlank = Flank.PRESSED;
		if (modifierKeys == null) modifierKeys = EnumSet.noneOf(Key.class);
		if (modifierButtons == null) modifierButtons = EnumSet.noneOf(Button.class);
		triggers.add(new Trigger(triggerButton, triggerFlank, modifierKeys, modifierButtons, action));
		return this;
	}
	//==============================================================================================

	//==============================================================================================
	public void test(Input input, Key key, Flank flank) {
		for (Trigger trigger : triggers) {
			trigger.test(input, key, flank);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void test(Input input, Button button, Flank flank) {
		for (Trigger trigger : triggers) {
			trigger.test(input, button, flank);
		}
	}
	//==============================================================================================
	
}
//**************************************************************************************************
