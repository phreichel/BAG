//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.action.ActionMap;
import jade.entity.MotionController;
import jade.input.Input;
import jade.input.Trigger;
import lombok.Setter;

//**************************************************************************************************
public class ExampleActions extends ActionMap {

	//==============================================================================================
	@Setter private Application application = null;
	@Setter private MotionController motionController = null;
	//==============================================================================================

	//==============================================================================================
	public ExampleActions() {
		register();
	}
	//==============================================================================================

	//==============================================================================================
	protected void register() {
		this.add("QUIT", true, this::quitAction);
		this.add("TOGGLE_GUI", true, this::toggleGUIAction);
		this.add("TOGGLE_FULLSCREEN", true, this::toggleFullScreenAction);
		this.add("START_STRAFE_LEFT", true, this::startStrafeLeftAction);
		this.add("STOP_STRAFE_LEFT", true, this::stopStrafeLeftAction);
		this.add("START_STRAFE_RIGHT", true, this::startStrafeRightAction);
		this.add("STOP_STRAFE_RIGHT", true, this::stopStrafeRightAction);
		this.add("START_STRAFE_FORWARD", true, this::startStrafeForwardAction);
		this.add("STOP_STRAFE_FORWARD", true, this::stopStrafeForwardAction);
		this.add("START_STRAFE_BACKWARD", true, this::startStrafeBackwardAction);
		this.add("STOP_STRAFE_BACKWARD", true, this::stopStrafeBackwardAction);
		this.add("START_STRAFE_UP", true, this::startStrafeUpAction);
		this.add("STOP_STRAFE_UP", true, this::stopStrafeUpAction);
		this.add("START_STRAFE_DOWN", true, this::startStrafeDownAction);
		this.add("STOP_STRAFE_DOWN", true, this::stopStrafeDownAction);
		this.add("START_PITCH_DOWN", true, this::startPitchDownAction);
		this.add("STOP_PITCH_DOWN", true, this::stopPitchDownAction);
		this.add("START_PITCH_UP", true, this::startPitchUpAction);
		this.add("STOP_PITCH_UP", true, this::stopPitchUpAction);
		this.add("START_YAW_LEFT", true, this::startYawLeftAction);
		this.add("STOP_YAW_LEFT", true, this::stopYawLeftAction);
		this.add("START_YAW_RIGHT", true, this::startYawRightAction);
		this.add("STOP_YAW_RIGHT", true, this::stopYawRightAction);
		this.add("START_ROLL_LEFT", true, this::startRollLeftAction);
		this.add("STOP_ROLL_LEFT", true, this::stopRollLeftAction);
		this.add("START_ROLL_RIGHT", true, this::startRollRightAction);
		this.add("STOP_ROLL_RIGHT", true, this::stopRollRightAction);
	}
	//==============================================================================================

	//==============================================================================================
	public void quitAction(Trigger trigger, Input input) {
		application.doQuit();
	}
	//==============================================================================================

	//==============================================================================================
	public void toggleGUIAction(Trigger trigger, Input input) {
		application.toggleGUIVisiblity();
	}
	//==============================================================================================

	//==============================================================================================
	public void toggleFullScreenAction(Trigger trigger, Input input) {
		Platform platform = application.getPlatform();
		boolean state = !platform.isFullScreen();
		platform.setFullScreen(state);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void startStrafeLeftAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRight(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopStrafeLeftAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRight(1f);
		}
	}
	//==============================================================================================
	
	//==============================================================================================
	public void startStrafeRightAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRight(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopStrafeRightAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRight(-1f);
		}
	}
	//==============================================================================================
	
	//==============================================================================================
	public void startStrafeForwardAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleForward(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopStrafeForwardAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleForward(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startStrafeBackwardAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleForward(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopStrafeBackwardAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleForward(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startStrafeUpAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleUp(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopStrafeUpAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleUp(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startStrafeDownAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleUp(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopStrafeDownAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleUp(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startPitchUpAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottlePitchUp(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopPitchUpAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottlePitchUp(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startPitchDownAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottlePitchUp(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopPitchDownAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottlePitchUp(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startYawLeftAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleYawLeft(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopYawLeftAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleYawLeft(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startYawRightAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleYawLeft(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopYawRightAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleYawLeft(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startRollLeftAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRollLeft(1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopRollLeftAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRollLeft(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void startRollRightAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRollLeft(-1f);
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void stopRollRightAction(Trigger trigger, Input input) {
		if (motionController != null) {
			motionController.addThrottleRollLeft(1f);
		}
	}
	//==============================================================================================
	
}
//**************************************************************************************************
