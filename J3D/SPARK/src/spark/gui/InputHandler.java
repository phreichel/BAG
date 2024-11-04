//*********************************************************************************************************************
package spark.gui;
//*********************************************************************************************************************

import spark.platform.Action;
import spark.platform.Axis;
import spark.platform.Toggle;

//*********************************************************************************************************************
public interface InputHandler {

	//=================================================================================================================
	public void handle(InputContext context);
	//=================================================================================================================

	//=================================================================================================================
	public static void DEFAULT(InputContext context) {
		HOVER(context);
		ARM(context);
		DEACTIVATE(context);
		ACTIVATE(context);
		UNARM(context);
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void GROUP(InputContext context) {
		var group = context.component;
		var container = (Container) group.property(Property.CHILDREN);
		container.handleInput(context);
		DEFAULT(context);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public static void HOVER(InputContext context) {
		if (!context.topmost) {
			float xPointer = context.input.axis(Axis.POINTER_X);
			float yPointer = context.input.axis(Axis.POINTER_Y);
			boolean pointerInside =
				context.component.state(State.DISPLAYED) &&
				context.xOffset <= xPointer &&
				xPointer <= context.xOffset + context.component.width() &&
				context.yOffset <= yPointer &&
				yPointer <= context.yOffset + context.component.height();
			if (pointerInside) {
				context.topmost = true;
				context.component.state(State.HOVERED, true);
			} else {
				context.component.state(State.HOVERED, false);
			}
		} else {
			context.component.state(State.HOVERED, false);
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void ARM(InputContext context) {
		if (!context.component.state(State.HOVERED)) return;
		if (!context.input.action().equals(Action.TOGGLE_ARMED)) return;
		if (!context.input.toggle().equals(Toggle.POINTER_BUTTON_01)) return;
		context.component.state(State.ARMED, true);
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void UNARM(InputContext context) {
		if (!context.component.state(State.ARMED)) return;
		if (!context.input.action().equals(Action.TOGGLE_RELEASED)) return;
		if (!context.input.toggle().equals(Toggle.POINTER_BUTTON_01)) return;
		context.component.state(State.ARMED, false);
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void ACTIVATE(InputContext context) {
		if (!context.component.state(State.ARMED)) return;
		if (!context.input.action().equals(Action.TOGGLE_RELEASED)) return;
		if (!context.input.toggle().equals(Toggle.POINTER_BUTTON_01)) return;
		float xPointer = context.input.axis(Axis.POINTER_X);
		float yPointer = context.input.axis(Axis.POINTER_Y);
		boolean pointerInside =
			context.component.state(State.DISPLAYED) &&
			context.xOffset <= xPointer &&
			xPointer <= context.xOffset + context.component.width() &&
			context.yOffset <= yPointer &&
			yPointer <= context.yOffset + context.component.height();
		if (!pointerInside) return;
		context.component.state(State.ACTIVATED, true);
		context.component.trigger();
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void DEACTIVATE(InputContext context) {
		if (!context.component.state(State.ACTIVATED)) return;
		context.component.state(State.ACTIVATED, false);
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
