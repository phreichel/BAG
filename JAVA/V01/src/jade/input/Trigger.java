//**************************************************************************************************
package jade.input;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import jade.action.Action;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************

//**************************************************************************************************
public class Trigger {

	//==============================================================================================
	@Getter @Setter private Key triggerKey = null;
	@Getter @Setter private Button triggerButton = null;
	@Getter @Setter private Flank triggerFlank = Flank.PRESSED;
	@Getter private Set<Key> modifierKeys = EnumSet.noneOf(Key.class);
	@Getter private Set<Button> modifierButtons = EnumSet.noneOf(Button.class);
	@Getter @Setter private Action action = null;
	//==============================================================================================

	//==============================================================================================
	public Trigger() {
		super();
	}
	//==============================================================================================
	
	//==============================================================================================
	public Trigger(
			Key triggerKey,
			Flank triggerFlank,
			Collection<Key> modifierKeys,
			Collection<Button> modifierButtons,
			Action action) {
		super();
		this.triggerKey = triggerKey;
		this.triggerFlank = triggerFlank;
		this.modifierKeys.addAll(modifierKeys);
		this.modifierButtons.addAll(modifierButtons);
		this.action = action;
	}
	//==============================================================================================

	//==============================================================================================
	public Trigger(
			Button triggerButton,
			Flank triggerFlank,
			Collection<Key> modifierKeys,
			Collection<Button> modifierButtons,
			Action action) {
		super();
		this.triggerButton = triggerButton;
		this.triggerFlank = triggerFlank;
		this.modifierKeys.addAll(modifierKeys);
		this.modifierButtons.addAll(modifierButtons);
		this.action = action;
	}
	//==============================================================================================
	
	//==============================================================================================
	public Trigger addModifier(Key modifierKey) {
		modifierKeys.add(modifierKey);
		return this;
	}
	//==============================================================================================
	
	//==============================================================================================
	public Trigger addModifier(Button modifierButton) {
		modifierButtons.add(modifierButton);
		return this;
	}
	//==============================================================================================
	
	//==============================================================================================
	public void test(Input input, Key key, Flank flank) {
		test(input, key, null, flank);
	}
	//==============================================================================================

	//==============================================================================================
	public void test(Input input, Button button, Flank flank) {
		test(input, null, button, flank);
	}
	//==============================================================================================
	
	//==============================================================================================
	private void test(Input input, Key key, Button button, Flank flank) {
		if (
				action != null &&
				input != null &&
				flank != null &&
				flank.equals(triggerFlank) &&
				(
					key != null && triggerKey != null && key.equals(triggerKey) ||
					button != null && triggerButton != null && button.equals(triggerButton)
				) &&
				input.getPointer().getButtons().containsAll(modifierButtons) &&
				input.getKeyboard().getKeys().containsAll(modifierKeys)
			) {
			action.perform(this, input);
		}
	}
	//==============================================================================================

}
//**************************************************************************************************
