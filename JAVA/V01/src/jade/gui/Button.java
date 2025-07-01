//**************************************************************************************************
package jade.gui;
//**************************************************************************************************

import jade.render.Renderer;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class Button extends Widget {

	//==============================================================================================
	@Getter @Setter private Action action = null;
	//==============================================================================================

	//==============================================================================================
	protected void renderWidget(Renderer renderer) {
		if (getFlags().contains(Flag.ARMED)) {
			renderer.setColor(.9f, .5f, .8f);
		}
		else if (getFlags().contains(Flag.POINTED_AT)) {
			renderer.setColor(0, .5f, .8f);
		} else {
			renderer.setColor(0, 0, .4f);
		}		
		renderer.fillRectangle(0, 0, size.x, size.y);
		renderer.setColor(.5f, .5f, .9f);
		renderer.drawRectangle(0, 0, size.x, size.y);
	}
	//==============================================================================================
	
	//==============================================================================================
	protected void handleWidget(Event event) {

		if (
			getFlags().contains(Flag.POINTED_AT) &&
			event.getIdent().equals(Ident.POINTER_PRESSED) &&
			event.getButton().equals(jade.input.Button.BTN1)) {
			setFlag(Flag.ARMED, true);
		}

		else if (
			getFlags().contains(Flag.POINTED_AT) &&
			getFlags().contains(Flag.ARMED) &&
			event.getIdent().equals(Ident.POINTER_RELEASED) &&
			event.getButton().equals(jade.input.Button.BTN1)) {
			setFlag(Flag.ARMED, false);
			if (action != null) {
				action.perform(this, event);
			}
		}

		else if (
			getFlags().contains(Flag.ARMED) &&
			!event.getPointer().getButtons().contains(jade.input.Button.BTN1)) {
			setFlag(Flag.ARMED, false);
		}
		
	}
	//==============================================================================================
	
}
//**************************************************************************************************
